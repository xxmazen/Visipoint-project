# Survey Builder — `/link` API Performance Test Report

**Platform:** visipoint.uk (UK (TESTING) Company Dashboard)
**Endpoint under test:** `POST https://api-survey.visipoint.me/api/v1/questions/survey/{id}/link`
**Tester:** Claude Code (direct API calls via authenticated browser session — real JWT captured from the live app, not a synthetic token)
**Test Date:** 2026-07-05
**Method:** 60 sequential requests replaying the exact payload the app sends when linking one Multiple Choice question to a survey. Timed client-side per request (request → full response body received).

---

## 1. Bottom line

**Yes — there is an issue, but it's not a performance problem. It's a data-integrity bug: the endpoint is not idempotent.**

- Response **time** is acceptable and stable (no errors, no timeouts, no meaningful degradation across 60 calls).
- But every one of the 60 identical repeat calls returned **201 Created** and **inserted a new, separate question record** — nothing in the API rejects or deduplicates a repeat "link" call. Replaying the same request 60 times left the target survey with **61 duplicate question records** (1 real + 60 junk).
- This also reopens the earlier BAS/CCS/CSS/DPS/ESS/SCF/TTQ/WUS template 422 findings: since a *single* Multiple Choice question links successfully every time, the "Multiple Choice question type" theory from the earlier session is not the actual trigger. The 422s only ever occurred when the *original template's full question batch* (5–6 questions in one request, one of them Multiple Choice) was sent together — pointing at something in that specific multi-question payload (e.g. duplicate IDs/sort_order collisions in the template's pre-filled data), not the question type itself.

---

## 2. Performance results

| Metric | Value |
|---|---|
| Requests sent | 60 (sequential) |
| Status codes | 201 × 60 — **zero errors, zero 422s** |
| Min | 248.8 ms |
| Median | 380.2 ms |
| Average | 374.6 ms |
| P90 | 406.9 ms |
| P95 | 421.6 ms |
| P99 / Max | 610.1 ms |
| First-10 avg → Last-10 avg | 383.6 ms → 414.2 ms |

**Assessment:** ~250–420ms is on the slower side for a "link a question to a survey" write operation, but it held steady across 60 back-to-back calls — no sign of throttling, connection exhaustion, or growing latency as duplicate records accumulated. The single 610ms outlier was the last call in the sequence; not enough data to call it a trend.

---

## 3. The idempotency bug (new finding, higher priority than the raw timing numbers)

**What happened:** I captured the real request the Survey Builder sends via a fetch/XHR hook, then replayed the identical `POST /link` request (same headers, same JSON body, same survey ID) 60 times.

**Expected:** Either (a) the second and subsequent calls are no-ops / return the already-linked question, or (b) the API rejects duplicate link attempts with a 4xx.

**Actual:** All 60 calls succeeded and created 60 separate question rows. Confirmed via `GET /api/v1/surveys/{id}` afterward — question count was 61 (should have been 1).

**Why this matters beyond my test:** Any retry logic (frontend auto-retry, a user double-clicking "Save and publish", a flaky network causing the browser to resend, or a future fix that adds retry-on-422 for the batch bug above) will silently multiply questions on a survey with no error or warning. This is a data-integrity risk independent of the original bug hunt.

**Cleanup performed:** I deleted the polluted test survey immediately after discovering this (survey count back to 36 in the "UK (TESTING)" environment). No other surveys were affected.

---

## 4. Correction to the earlier template bug report

The `Survey_Templates_Bug_Report_2026-07-05.md` findings (8/19 templates fail with HTTP 422) are still accurate as *observed behavior* — those templates do fail to save. But the stated root cause (**"any Multiple Choice question causes it"**) should be revised:

- A lone Multiple Choice question, sent by itself, links and publishes successfully (proven above — 60/60 succeeded, and the real "Save and publish" click on that survey also succeeded and set it to Active).
- The 422 only reproduces with the **original template's full multi-question batch** sent together.
- **Recommend dev investigate:** the batch request body for one of the 8 failing templates (e.g., BAS) — likely a duplicate `sort_order`, duplicate option ID, or malformed field specific to that template's pre-filled Multiple Choice data, not the question type in general.

---

## 5. Recommendations

1. **File as two related but distinct issues** against Survey Module epic CL-16841:
   - **Bug A (existing, confirmed 2026-07-05):** 8 templates fail to save with HTTP 422 + unreadable error — root cause is in the multi-question batch payload, not the Multiple Choice type alone (this report corrects the earlier hypothesis).
   - **Bug B (new, this session):** `POST /questions/survey/{id}/link` is not idempotent — repeat/retry calls create duplicate question records with no validation or dedup. Medium-High severity: silent data corruption risk, not user-facing today but a landmine for future retry logic or double-clicks.
2. Do not build a retry-on-422 workaround for Bug A until Bug B is fixed, or every retry will multiply questions.

---

*Report generated by Claude Code — direct API performance testing via authenticated browser session (captured real JWT + request payload, replayed via fetch)*
