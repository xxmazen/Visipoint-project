# Survey Module — System + API Testing (Valid/Invalid Cases)

**Platform:** visipoint.uk (UK Company Dashboard) + `api-survey.visipoint.me` (backend API)
**Tester:** Claude (Senior QA Engineer — automated via Chrome MCP + direct API calls)
**Test Date:** 2026-07-29
**Parent Jira Task:** [CL-17093 — Live System Testing (SM2)](https://lamasatech.atlassian.net/browse/CL-17093)
**Scope:** Fresh valid/invalid system (UI) testing of the Survey Builder create/publish flow, a regression re-check of the previously open CL-17843 bug, and direct API-level valid/invalid testing against the survey backend.

---

## 1. Executive Summary

| Metric | Value |
|--------|-------|
| UI test cases | 6 (2 valid, 4 invalid) |
| API test cases | 10 (3 valid, 7 invalid) |
| New bugs found | 0 |
| Bugs re-confirmed FIXED | 1 — **CL-17843** (8 templates failing HTTP 422 on save) |
| Overall health | Excellent — every validation path (UI and API) behaves correctly; API returns clean, well-structured errors |

Test data created (4 surveys) was deleted via API after testing; survey count confirmed restored to baseline (42).

---

## 2. UI System Testing — Survey Builder

### Valid cases

| # | Scenario | Result |
|---|----------|--------|
| V1 | Create survey from scratch ("Create your own survey"), add a Yes/No question with a title, Save and publish | ✅ Pass — "Survey created successfully!" then "Survey published successfully!"; status Active; survey count 42→43 |
| V2 | Create survey from the CSAT template, fill the required "Next button label" left blank by the template, Save and publish | ✅ Pass — published successfully; survey count 43→44 |

### Invalid cases

| # | Scenario | Expected | Actual |
|---|----------|----------|--------|
| I1 | Submit "Create New Survey" with empty Survey Name | Inline validation blocks it | ✅ "Survey name is required" — blocked |
| I2 | Submit "Create New Survey" with a name that already exists (used "Bus", an existing survey) | Inline validation blocks it | ✅ "Survey with this title already exists" — blocked |
| I3 | Add a question, leave the Question title empty, click Save and publish | Inline validation blocks it | ✅ "Question title is required." — blocked |
| I4 | On the CSAT template's second question, leave the required "Next button label" empty, click Save and publish | Inline validation blocks it | ✅ "Next button label is required." — blocked |

**Observation (not a bug):** Could not test "publish with 0 questions" as a standalone UI scenario — a brand-new survey shows only an "Add questions" call-to-action with no Save/Publish buttons at all until at least one question exists, so the 0-question-publish state is structurally unreachable from a fresh survey. (The historical version of this scenario, tested in earlier sessions, involved activating an already-existing 0-question survey created via the then-broken template flow — not reproducible the same way now that template creation works correctly.)

---

## 3. Regression Re-check — CL-17843

**Prior status (2026-07-06 / 2026-07-09):** 8 of 19 templates (BAS, CCS, CSS, DPS, ESS, SCF, TTQ, WUS) failed to save in any form — both "Save and publish" and "Save as a draft" returned HTTP 422 on `POST /api/v1/questions/survey/{id}/link`, with an unreadable `[object Object]` error toast. Confirmed still broken as recently as 2026-07-09.

**Retest (2026-07-29):** Created a fresh survey from the **BAS** template (one of the 8 originally-affected templates, containing the Multiple Choice question that was the suspected trigger). Filled the required "Next button label" and clicked "Save and publish."

**Result: ✅ FIXED.** "Survey published successfully!" — no error. Verified in the survey list (status Active) and by reopening the survey: all 5 template questions are present and correctly linked, including the Multiple Choice question ("Which words best describe our brand?") that previously triggered the 422.

**Full spot-check (follow-up, same day):** created from each of the remaining 7 originally-affected templates — CCS, CSS, DPS, ESS, SCF, TTQ, WUS — and confirmed every question imported and linked correctly immediately on creation, with no error toast:

| Template | Expected questions | Linked | Result |
|----------|--------------------:|-------:|--------|
| BAS | 5 | 5 | ✅ Fixed |
| CCS | 4 | 4 | ✅ Fixed |
| CSS | 4 | 4 | ✅ Fixed |
| DPS | 3 | 3 | ✅ Fixed |
| ESS | 6 | 6 | ✅ Fixed |
| SCF | 6 | 6 | ✅ Fixed |
| TTQ | 3 | 3 | ✅ Fixed (this template specifically reproduced the `[object Object]` toast historically — now clean) |
| WUS | 5 | 5 | ✅ Fixed |

**All 8 of the originally-affected templates are now confirmed fixed (8/8).** All 7 additional test surveys deleted via API afterward; survey count confirmed back to baseline (42).

**Recommend:** Close CL-17843 as fixed — full coverage achieved, no partial-fix risk remaining.

---

## 4. API Testing — `api-survey.visipoint.me`

Authenticated directly against the API using the survey-scoped bearer token and tenant ID the app itself uses (read from `localStorage.survey`), bypassing the UI entirely.

### Valid cases

| # | Request | Expected | Actual |
|---|---------|----------|--------|
| A1 | `GET /api/v1/surveys` (list, valid auth) | 200 with paginated survey list | ✅ 200 |
| A2 | `GET /api/v1/surveys/{validId}` | 200 with survey detail | ✅ 200 |
| A3 | `POST /api/v1/surveys` with valid `{title: "..."}` payload | 201, new survey created | ✅ 201, id returned |

### Invalid cases

| # | Request | Expected | Actual |
|---|---------|----------|--------|
| A4 | `GET /api/v1/surveys/{nonexistentId}` (well-formed but unused UUID) | 404 | ✅ 404 |
| A5 | `GET /api/v1/surveys` with no Authorization header | 401 | ✅ 401 |
| A6 | `GET /api/v1/surveys` with a garbage/malformed bearer token | 401 | ✅ 401 |
| A7 | `POST /api/v1/surveys` with an empty `{}` body | 422 with field-level error | ✅ 422 — `{"loc":["body","title"],"msg":"Field required"}` |
| A8 | `POST /api/v1/surveys` with a title that already exists (API-level, bypassing UI's client-side check) | 409 or equivalent conflict | ✅ 409 — `"Survey with this title already exists"` (confirms the duplicate-name rule is enforced server-side, not just client-side) |
| A9 | `POST /api/v1/questions/survey/{id}/link` with empty `{}` body | 422 with field-level error | ✅ 422 — `{"loc":["body","questions"],"msg":"Field required"}` |
| A10 | `POST /api/v1/questions/survey/{id}/link` with a malformed nested question object (missing `question_type`) | 422 with field-level error | ✅ 422 — `{"loc":["body","questions",0,"question_type"],"msg":"Field required"}` |

**No new bugs found.** Every invalid-input path returns a clean, correctly-scoped HTTP status with a structured, actionable error body (FastAPI-style field-path validation errors) — no 500s, no vague messages, no silent failures. This is a meaningfully higher bar than the historical `[object Object]` toast bug (CL-17843) and confirms that bug is specifically fixed, not just "no longer reproducible by coincidence."

**Not retested this session:** the `/link` endpoint idempotency fix (CL-17844, replaying an identical payload shouldn't create duplicates) — this was already exhaustively retested and confirmed fixed on 2026-07-06 (10/10 replays produced no duplicates). Not repeated here to keep scope focused on new valid/invalid coverage.

---

## 5. Test Data Cleanup

4 surveys created during this session were deleted via `DELETE /api/v1/surveys/{id}` (all returned 200):
- QA System Test 2026-07-29
- QA Template Test CSAT 2026-07-29
- QA Regression Test BAS 2026-07-29
- QA API Test Survey 2026-07-29 (API-only, never had a UI equivalent)

Survey count confirmed restored to baseline: 42.

---

*Report generated: 2026-07-29 | Session: Survey Module System + API Testing | Tool: Claude Code + Chrome MCP + direct fetch API calls*
