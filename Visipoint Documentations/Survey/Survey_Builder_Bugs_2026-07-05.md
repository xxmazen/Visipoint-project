# Survey Builder — Bug Reports (2026-07-05)

**Module:** Survey Builder
**Platform:** visipoint.uk (Cloud Dashboard)
**Parent Jira Epic:** [CL-16841 — Survey Module](https://lamasatech.atlassian.net/browse/CL-16841)
**Tester:** Claude Code (AI-assisted browser + API testing)
**Test Date:** 2026-07-05

Two related bugs found this session. Full investigation detail and supporting data in:
- `Survey_Templates_Bug_Report_2026-07-05.md` (initial 19-template test matrix)
- `Survey_Link_API_Performance_Report_2026-07-05.md` (API performance test that corrected the root cause below)

---

### BUG-1 — Surveys created from certain templates fail to save (HTTP 422) with an unreadable error message

| Field | Detail |
|-------|--------|
| **Module** | Survey Builder |
| **URL** | `https://visipoint.uk/survey-builder` → Templates tab |
| **Severity** | High |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Templates tab → choose one of: BAS, CCS, CSS, DPS, ESS, SCF, TTQ, or WUS → "Choose and customize" → Create | Survey created with all template questions imported | ✅ Works — question count matches template card in every case |
| 2 | Fill in any blank required fields (e.g. "Next button label") | N/A | ✅ Works |
| 3 | Click "Save and publish" **or** "Save as a draft" | Survey saves/publishes, same as every other template | ❌ Both fail identically every time |

**Expected:** The survey saves (as draft or published), consistent with the other 11 templates tested (3PR, CSAT, IBS, NPS, PSS, PFS, PF, PLF, QPS, STFS, THC), which all worked correctly.

**Actual:** `POST /api/v1/questions/survey/{id}/link` returns **HTTP 422** every time, for both save actions. The survey is permanently stuck — 0 questions ever persist server-side, and there is no way to recover it from the UI. The error toast shown to the user is broken and unreadable:
> "Failed to save survey: [object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object]"

**Root cause note (important — corrects an earlier theory from this session):** It is **not** simply "any survey with a Multiple Choice question fails." A follow-up API test confirmed a single Multiple Choice question, submitted by itself, links and publishes successfully every time (60/60 test calls succeeded with 201). The 422 only reproduces when the **original template's full question batch** (5–6 questions submitted together in one request, one of them Multiple Choice) is sent as a single `/link` call. The actual trigger is most likely something specific to that batch payload — e.g. a duplicate `sort_order` or option ID inside the template's pre-filled Multiple Choice data — not the question type in general. **Recommend the dev investigating this compare the exact request body for a failing template (e.g. BAS) against a passing one (e.g. 3PR) rather than assuming the question type is at fault.**

**Affected templates (8 of 19):** BAS, CCS, CSS, DPS, ESS, SCF, TTQ, WUS
**Unaffected templates (11 of 19):** 3PR, CSAT, IBS, NPS, PSS, PFS, PF, PLF, QPS, STFS, THC

---

### BUG-2 — `POST /questions/survey/{id}/link` is not idempotent; repeated calls silently create duplicate question records

| Field | Detail |
|-------|--------|
| **Module** | Survey Builder (API) |
| **URL** | `POST https://api-survey.visipoint.me/api/v1/questions/survey/{id}/link` |
| **Severity** | Medium-High |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Capture the exact request the Survey Builder sends when linking one question to a survey (headers + body) | N/A | ✅ Captured via fetch/XHR hook on the live app |
| 2 | Replay the identical request 60 times in a row | Either a no-op after the first call, or a 4xx rejecting the duplicate | ❌ All 60 calls returned **201 Created** |
| 3 | `GET /api/v1/surveys/{id}` after the 60 replays | Survey should have 1 question | ❌ Survey had **61 question records** |

**Expected:** Calling `/link` again for a question that's already linked to a survey should either be a safe no-op or return a clear rejection (4xx) — it should never create additional duplicate rows.

**Actual:** The endpoint has no idempotency guard or duplicate check. Every repeat call inserts a brand-new question record with no error, no warning, and no dedup logic. Response time was consistent and fast (~250–420ms per call, no degradation across 60 calls) — this is purely a data-integrity gap, not a performance problem.

**Why this matters:** Any retry path — a user double-clicking "Save," a flaky network causing the browser to resend, or a future fix that adds retry-on-422 for BUG-1 above — will silently multiply questions on a survey with zero indication anything went wrong. This should be fixed **before** any retry logic is added on top of BUG-1's failure case, or every retry will make the duplication worse.

**Cleanup:** The test survey used for this reproduction accumulated 61 duplicate questions and was deleted immediately after the finding was confirmed. No other surveys in the "UK (TESTING)" environment were affected.

---

## Summary

| Bug | Severity | Scope | Suggested fix order |
|---|---|---|---|
| BUG-1: Template batch save fails (422 + broken error message) | High | 8/19 templates | Fix first — investigate the batch payload difference between a passing and failing template |
| BUG-2: `/link` endpoint not idempotent | Medium-High | Any repeated call to this endpoint | Fix before adding any retry logic to BUG-1, otherwise retries will compound duplicate data |

**Recommended Jira action:** File both as Sub-bugs under CL-16841, cross-referencing each other (BUG-2 blocks a naive retry-based fix for BUG-1).

**Filed to Jira (2026-07-05):** Both created as Sub-bugs under [CL-17093](https://lamasatech.atlassian.net/browse/CL-17093), assigned to Moataz Khaled, linked to each other via "Relates":
- BUG-1 → [CL-17843](https://lamasatech.atlassian.net/browse/CL-17843)
- BUG-2 → [CL-17844](https://lamasatech.atlassian.net/browse/CL-17844)

---

## Retest — 2026-07-06 (CL-17844)

Moataz marked CL-17844 "Ready for Testing." Retested by capturing a fresh real `/link` request (new test survey, 1 Smiley question) and replaying it 10 additional times against the same survey ID.

**Result: Fixed.** All 10 replays returned 201, but every response carried the **same question ID and the same `created_at`** as the original call — no new records were created. `GET /api/v1/surveys/{id}` confirmed only 1 question existed after 11 total identical `/link` calls (previously: 61 records from 60 replays). Test survey deleted via API after confirming the result.

**Side observation (not the reported bug, flagged for awareness only):** during normal editing (add question → fill title → Save and publish) the survey ended up with 2 question rows, same title/type, different IDs, ~2 minutes apart — looks like an autosave-vs-publish interaction on a different code path than the repeat-call scenario this ticket covers. Did not block sign-off; worth a quick separate look if it recurs.

Comment posted to [CL-17844](https://lamasatech.atlassian.net/browse/CL-17844) confirming the fix and requesting the team close it out.

---

## Retest — 2026-07-06 (CL-17843)

CL-17843 was also marked "Ready for Testing." Retested by recreating all 8 originally-affected templates (BAS, CCS, CSS, DPS, ESS, SCF, TTQ, WUS) from scratch and attempting "Save and publish" on each, verifying the result directly via `GET /api/v1/surveys/{id}`.

**Result: Not fixed — all 8 templates still fail identically.**

| Template | Result |
|---|---|
| BAS | ❌ draft, 0 questions |
| CCS | ❌ draft, 0 questions |
| CSS | ❌ draft, 0 questions |
| DPS | ❌ draft, 0 questions |
| ESS | ❌ draft, 0 questions |
| SCF | ❌ draft, 0 questions |
| TTQ | ❌ draft, 0 questions (broken "[object Object]" toast reproduced again) |
| WUS | ❌ draft, 0 questions |

Every test survey ended up permanently stuck in Draft with 0 linked questions — identical to the original report. This ticket looks like it was moved to "Ready for Testing" prematurely, possibly conflated with the separate CL-17844 idempotency fix (which *is* confirmed working).

Posted a comment to [CL-17843](https://lamasatech.atlassian.net/browse/CL-17843) with the full per-template breakdown and **transitioned the ticket to "Back to Development"**. All 10 test surveys created during this retest (including 2 renamed retries after duplicate-name validation blocked reuse of the same name) were deleted via API afterward — no leftover data.
