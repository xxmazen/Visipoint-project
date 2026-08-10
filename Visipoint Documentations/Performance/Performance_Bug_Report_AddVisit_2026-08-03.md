# Visipoint Add Visit Load Test — Bug Report

**Test date:** 2026-08-03
**Test type:** Load test — 1000 concurrent virtual users, 30s ramp-up, single account, Login → Add Visit (expected-visit) per user
**Tool:** Apache JMeter 5.6.3
**Test plan:** `D:\apache-jmeter-5.6.3\visipoint-project\visipoint-addvisit-load-test.jmx`
**Raw results:** `D:\apache-jmeter-5.6.3\visipoint-project\addvisit-results.jtl`
**Tester:** Claude (JMeter load test, built from a real captured Add Visit request; user executed the run)

---

## Summary Results

| Step | Success | Failed | Avg | P50 | P95 | P99 | Max |
|---|---|---|---|---|---|---|---|
| **Login** | 844/1000 (84.4%) | 156 (15.6%) | 32.7s | 24.2s | 86.1s | 103.3s | 195.9s |
| **Add Visit** | **0/1000 (0%)** | **1000 (100%)** | 5.8s | 0.4s | 25.7s | 53.8s | 231.7s |

---

### BUG-PERF-004 — Add Visit fails 100% of the time, same session-invalidation root cause as Logout (BUG-PERF-001) and Add User (BUG-PERF-003) — now confirmed on a THIRD independent endpoint

| Field | Detail |
|-------|--------|
| **Module** | Add Visits — Add Expected Visitor / Authentication session management |
| **Endpoint** | `POST https://api.visipoint.uk/api/expected-visit` (session via `POST https://api.visipoint.me/api/login`) |
| **Severity** | **High — same confirmed root cause, third independent confirmation** |
| **Status** | New — same bug as BUG-PERF-001 / BUG-PERF-003, different endpoint |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Log in, then immediately call `POST /api/expected-visit` with that session's token | Request should succeed using the freshly-obtained token | 0/1000 (0%) succeeded |
| 2 | Check failure breakdown | If load-related, failures should mostly be connection-level, not clean auth rejections | 523/1000 (52.3%) were clean `401 Unauthorized` — the same session-invalidation signature seen on Logout and Add User |

**Expected:** A freshly-obtained access token should work for adding an expected visit, the same as any other authenticated action.

**Actual:** 100% of Add Visit calls failed. Just over half (52.3%) were the now-familiar `401 Unauthorized` — this is the third distinct endpoint (after Logout and Add User) where the same session-token-invalidation bug produces a 100% or near-100% failure rate under concurrent same-account logins. This is no longer a pattern specific to one endpoint or code path — it's a property of the session/token system itself, and will affect **any** authenticated call made shortly after a competing login on the same account.

**Root cause:** Same as BUG-PERF-001/BUG-PERF-003 — see those reports for full detail. No new investigation needed here; this test simply adds a third confirmation point.

---

## Secondary/unresolved finding: a different failure signature this run — DNS resolution errors

Unlike the Add User runs (which showed clean 401s plus ordinary connection-refused/socket errors), **44.6% of Add Visit's failures (446/1000) were `UnknownHostException`** — a DNS resolution failure, not a connection or auth error. This wasn't seen in any of the three prior Add User runs.

| Failure type | Count | % of Add Visit failures |
|---|---|---|
| 401 Unauthorized (session bug) | 523 | 52.3% |
| UnknownHostException (DNS failure) | 446 | 44.6% |
| SocketException | 16 | 1.6% |
| HttpHostConnectException | 15 | 1.5% |

**This is flagged as unresolved, not attributed.** A DNS resolution failure partway through a test most often points to the **load-generating machine's own DNS resolver** being overwhelmed by the volume of concurrent connection attempts (each new connection potentially triggering a fresh DNS lookup), rather than a server-side issue — but this wasn't controlled for and shouldn't be assumed. It's also possible this reflects transient network conditions on the test machine unrelated to Visipoint at all. Recommend re-running this specific test to see whether the DNS failures reproduce consistently (pointing to a load-generator-side resource limit) or don't recur (pointing to a one-off network blip) before drawing any conclusion.

---

## Login performance (consistent with prior findings)

At 30s ramp-up, Login succeeded 84.4% of the time — in line with the 30s Add User run (79.9%) and the general pattern already established: Login degrades under a fast 30-second ramp-up due to connection contention, and (per the Add User test series) fully recovers to 100% success when given a 5-minute ramp-up instead. No new Login investigation needed; this is the same already-understood, already-resolved pacing issue.

---

## Recommendations

1. **No new backend investigation needed for the core bug** — this is the third confirmation of BUG-PERF-001/003's root cause (session token invalidation on concurrent same-account login). Treat all three (Logout, Add User, Add Visit) as symptoms of one bug to fix once.
2. **Re-run this specific test to check the DNS failure signature** — if `UnknownHostException` recurs consistently, it points to a load-generator-side capacity limit (worth noting for future large-scale tests from this machine); if it doesn't recur, it was likely a one-off and not worth further attention.
3. Same as prior reports: a distinct-accounts run would be needed to get real capacity data for Add Visit once the session bug is fixed, since it's currently masking all other results.

---

## Appendix: Test data cleanup note

This run attempted to create expected visits tagged `first_name=LOADTEST`, `last_name=LOADTEST_<threadNum>`, email pattern `loadtest+visit<threadNum>_XXXXX@lamasatech.com` — but since 100% of Add Visit calls failed, no test visit records were actually created. No cleanup needed.
