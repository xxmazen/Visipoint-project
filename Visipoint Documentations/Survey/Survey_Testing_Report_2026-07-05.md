# Survey Module — Valid & Invalid Scenario Testing Report

**Platform:** visipoint.uk (UK (TESTING) Company Dashboard)
**Tester:** Claude Code (AI-assisted browser testing via Claude-in-Chrome)
**Test Date:** 2026-07-05
**Reference:** Jira Survey Module epic [CL-16841](https://lamasatech.atlassian.net/browse/CL-16841) (39 stories, in progress — CL-16617 "Unified Survey Builder Controls"); prior findings in `Survey_Testing_Report.md` (2026-06-17, updated 2026-07-01)

---

## 1. Scope

Targeted regression + new exploratory pass on the **Survey Builder** create/publish/activate flow, covering both valid and invalid input scenarios, per prior Jira context (CL-16841 epic, previously logged bugs CL-17671–CL-17692).

## 2. Pre-Test Context (from Jira + memory)

- All 11 bugs originally logged against Survey Module (CL-17682–CL-17692) were confirmed **fixed** in the 2026-07-01 regression pass — zero open Survey bugs going into this session.
- Two bugs specifically relevant to this session's scope:
  - **CL-17671 / CL-17682** — Template-derived surveys created with 0 questions, activation returned HTTP 400 (Backend, High) — confirmed fixed 2026-07-01.
  - **CL-17673 / CL-17684** — Activation error toast gave no explanation (Frontend, High) — confirmed fixed 2026-07-01.
- This session re-verifies both fixes still hold and adds new valid/invalid scenario coverage.

---

## 3. Valid Scenarios — All Passed

| # | Scenario | Steps | Result |
|---|----------|-------|--------|
| V1 | Create survey from scratch | Create Survey → valid name "QA Test Survey - Valid Scenario" + description → Create | ✅ Survey created, redirected to editor, "No questions yet" state |
| V2 | Add a question | Add Questions → select Smiley Faces (Icons and Labels) → enter question title → Add | ✅ Question added, live preview panel on the right updated instantly |
| V3 | Save and Publish (valid) | Fill required question title → Save and publish | ✅ Redirected to survey list; status changed Draft → **Active** |
| V4 | Create survey from Template | Templates tab → "3PR — 360-Degree Performance Review" (6 questions) → Choose and customize → rename → Create | ✅ Survey created with all **6/6 questions imported correctly** (regression check — see §5) |
| V5 | Publish template-derived survey | Fill the two required "Next button label" fields flagged by validation → Save and publish | ✅ Status changed to **Active** |

## 4. Invalid Scenarios — All Correctly Blocked

| # | Scenario | Steps | Expected | Actual |
|---|----------|-------|----------|--------|
| I1 | Empty survey name | Create Survey → leave Survey Name blank → Create | Inline validation, no survey created | ✅ "Survey name is required" — red border, blocked |
| I2 | Duplicate survey name | Create Survey → name = "test" (existing survey) → Create | Inline validation, no duplicate created | ✅ "Survey with this title already exists" — blocked |
| I3 | Activate survey with 0 questions | Create valid survey, skip adding questions → row action → Activate → confirm | Explanatory error, no state change | ✅ Toast: **"Cannot activate a survey with no questions. Add at least one question before activating."** Survey remained in Draft. |
| I4 | Empty question title on publish | Add question, leave "Question" field blank → Save and publish | Inline validation, publish blocked | ✅ "Question title is required." — red border, blocked |
| I5 | Empty required "Next button label" (template Comment questions) | Publish template survey with two Comment-type questions left with blank "Next button label" | Inline validation, publish blocked | ✅ "Next button label is required." on each affected question, publish blocked until resolved |

---

## 5. Regression Confirmations (previously logged bugs)

| Jira | Original Issue | Retest Result |
|------|-----------------|---------------|
| [CL-17671](https://lamasatech.atlassian.net/browse/CL-17671) / [CL-17682](https://lamasatech.atlassian.net/browse/CL-17682) | Template-derived surveys created with 0 questions; activation returned HTTP 400 | **Still fixed.** 3PR template import produced all 6 questions; survey published and activated successfully with no errors. |
| [CL-17673](https://lamasatech.atlassian.net/browse/CL-17673) / [CL-17684](https://lamasatech.atlassian.net/browse/CL-17684) | Generic, unexplained activation error toast | **Still fixed.** Toast now reads: "Cannot activate a survey with no questions. Add at least one question before activating." |

No new defects found. No regressions detected.

---

## 6. Observation (not filed as a bug — flagging for awareness)

**Template "3PR — 360-Degree Performance Review" ships with required fields left blank on 2 of its 6 questions.**

- Questions 5 and 6 (both "Comment (open-ended)" type) each have a required **"Next button label"** field that is empty by default when the survey is created from the template.
- Attempting to publish without filling these fields is correctly blocked by validation (see I5) — so this is **not a functional defect** — but it does mean every user who starts from this specific template must discover and fill two extra required fields before they can publish, adding friction to the "quick start from template" value proposition.
- Recommend the Survey team either pre-populate a sensible default (e.g., "Next") in the template data, or make the field optional with a fallback default when blank.
- Did not check whether other templates (BAS, CSAT, CCS, CSS, DPS, ESS) have the same gap — out of scope for this session.

---

## 7. Test Data Created

Two new surveys were created in the "UK (TESTING)" environment during this session and left in **Active** status (test environment, not production):
- `QA Test Survey - Valid Scenario` — 1 question (Smiley Faces)
- `QA Template Test - 3PR` — 6 questions (from 3PR template)

---

## 8. Summary

| Category | Count |
|----------|-------|
| Valid scenarios tested | 5 / 5 passed |
| Invalid scenarios tested | 5 / 5 correctly blocked with clear validation |
| New bugs found | 0 |
| Regressions found | 0 |
| Previously-fixed bugs re-confirmed fixed | 2 (CL-17671/17682, CL-17673/17684) |
| Observations logged (non-bug) | 1 (template default data gap) |

**Overall assessment:** Survey Builder create → add question → publish → activate flow is functioning correctly across both happy-path and negative-path scenarios. Validation messaging is clear and specific throughout (name required, name uniqueness, question title required, field-level required, activation blocked with explanation) — a marked improvement over the generic/silent failures documented in the 2026-06-17 baseline report.

---

*Report generated by Claude Code — AI-assisted browser testing session (Claude-in-Chrome)*
*Screenshots were captured live throughout this session (create modal validation states, question builder validation, publish/activate results) and reviewed inline during testing; they are not persisted as standalone image files in this environment.*
