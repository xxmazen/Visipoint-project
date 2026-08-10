# Visipoint Performance Test — Bug Report

**Test date:** 2026-08-02
**Test type:** Load test — 1000 concurrent virtual users, 30s ramp-up, single account, Login → Logout per user
**Tool:** Apache JMeter 5.6.3
**Test plan:** `D:\apache-jmeter-5.6.3\visipoint-project\visipoint-load-test.jmx`
**Raw results:** `D:\apache-jmeter-5.6.3\visipoint-project\results.jtl` (raw, contains a duplicate-logging artifact — see Appendix) / `results-clean.csv` (deduplicated, used for all analysis below)
**Tester:** Claude (JMeter load test, built from a real captured login/logout request; user executed the run)

---

### BUG-PERF-001 — Concurrent logins from the same account invalidate each other's session, breaking Logout with 401

| Field | Detail |
|-------|--------|
| **Module** | Passport Account — Authentication / Session Management |
| **Endpoint** | `GET https://api.visipoint.me/api/logout` (session established via `POST https://api.visipoint.me/api/login`) |
| **Severity** | High |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Send 1000 concurrent `POST /api/login` requests for the **same account**, each ramped up over 30 seconds | Each login should succeed and return a valid, independently-usable `access_token` | 864/1000 (86.4%) returned `200 OK` with a token — server accepted the concurrent logins without rejecting them |
| 2 | Immediately after each login, send `GET /api/logout` using that thread's own `access_token` in the `Authorization: Bearer` header | Each logout should succeed (`200 OK`) and invalidate only that session | **881/1000 (88.1%) failed.** Of those, 869 returned a clean `401 Unauthorized` — the server explicitly rejected the token as invalid, not a timeout or connection error |
| 3 | Compare failure pattern against timing | If tokens were simply expired, failures would be random/timing-independent | Failure rate (88%) closely tracks the volume of concurrent logins on the same account, consistent with each new login invalidating a previous session's token before that thread could call logout |

**Expected:** Each of the 1000 simulated logins/sessions should be independent — logging in a second time (e.g., from another device or tab) should not silently invalidate a different active session for the same account, at least not one still in active use.

**Actual:** The overwhelming majority (88.1%) of logout attempts fail with `401 Unauthorized`, strongly indicating that concurrent logins to the same account are invalidating each other's tokens server-side, such that by the time a given thread calls logout, its token has already been superseded by a later login.

**Root cause (assessed, not yet confirmed against backend code):** The auth/session backend appears to allow only one active token per account (or otherwise invalidates prior tokens on new login) rather than maintaining independent, concurrently-valid sessions. This is a **session-management architecture issue**, not a load-capacity issue — it reproduces even though the *Login* calls themselves mostly succeeded cleanly (86.4% with `200 OK`, zero `401`s on Login itself). The system isn't struggling to process the logins; it is correctly processing each one and, as a side effect, disqualifying the previous session.

**Why this matters beyond the artificial load-test scenario:** This same mechanism would affect any real user who logs into the same account from two places in quick succession (e.g., two browser tabs, a phone and a laptop) — the first session's logout (or any authenticated call) could unexpectedly fail with 401 once the second login completes. Recommend verifying this against normal (non-load-test) multi-session usage, since this may be reproducible with as few as 2 concurrent logins, not just 1000.

---

### BUG-PERF-002 — Login response time degrades severely under concurrent load (median 22.9s, P99 152.5s, max ~5 min)

| Field | Detail |
|-------|--------|
| **Module** | Passport Account — Authentication |
| **Endpoint** | `POST https://api.visipoint.me/api/login` |
| **Severity** | Medium-High (see attribution caveat below — could be partially test-infrastructure-bound) |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Ramp up 1000 concurrent login requests over 30 seconds | Response times should stay reasonably close to baseline (low single-digit seconds) as concurrency increases, or degrade gracefully | Median (P50) response time: **22.9 seconds**. P95: 88.6s. P99: 152.5s. Max: **299.4 seconds** (~5 minutes) for a single login |
| 2 | Track throughput (completed requests/sec) over the life of the test | Throughput should stay roughly stable or degrade gracefully as load is sustained | Throughput **collapsed** over the run: ~26 req/s in the first ~30s window, falling to **0.5 req/s** in the final ~3.5-minute window — classic symptom of requests queuing faster than they drain |
| 3 | Check whether failures were server rejections or connection-level | If the server were explicitly rejecting excess load, failures would show as HTTP error codes (429, 503, etc.) | 136/1000 (13.6%) Login failures were **connection-level** (`HttpHostConnectException`, `SocketException`) — no HTTP error codes at all; the server never explicitly refused a login |

**Expected:** A login request should complete in roughly 1-2 seconds under normal conditions; under 1000-concurrent-user load, some degradation is expected but should not extend into tens of seconds to minutes for a majority of requests.

**Actual:** Response times ballooned by 1-2 orders of magnitude as load increased, with throughput visibly collapsing over the course of the test rather than stabilizing — consistent with request queuing/backlog building up faster than the system (client, server, or both) could drain it.

**Root cause — NOT fully isolated, attribution caveat:** I cannot conclusively separate two possible contributing causes from this single-machine test:
1. **Backend capacity limit** — the Visipoint auth backend genuinely struggling to process 1000 concurrent login requests (DB connection pool exhaustion, encryption/decryption CPU cost per login, etc.)
2. **Load-generator capacity limit** — a single machine originating 1000 concurrent HTTPS connections is itself resource-intensive (TLS handshake overhead, OS socket/file-descriptor limits, JMeter's default connection pool sizing), and some of the connection-level failures/slowdown could reflect the test client rather than the server

**To resolve the ambiguity:** re-run with either (a) a distributed load generator (multiple machines) to rule out client-side bottlenecks, or (b) server-side monitoring (CPU, DB connection pool usage, request queue depth) captured during the run to directly observe where time is being spent.

---

## Appendix: JMX listener duplication artifact (test-tooling issue, not a product bug)

The raw `results.jtl` file contains exactly 2x the real sample count (4000 rows instead of 2000) because the test plan had two listeners both configured to write results to a file named `results.jtl` — one embedded in the JMX ("View Results Tree"), one from the `-l results.jtl` CLI flag used to run it. The two listeners used slightly different column layouts, so interleaved rows in the raw file are misaligned/corrupted every other row. All analysis in this report used a filtered, deduplicated dataset (`results-clean.csv`, 1000 Login + 1000 Logout rows, verified against the live console summary counts). Future test plans should use only one results listener to avoid this.
