# Survey Builder — Template Library Regression Testing (All 19 Templates)

**Platform:** visipoint.uk (UK (TESTING) Company Dashboard)
**Tester:** Claude Code (AI-assisted browser testing via Claude-in-Chrome, JS-assisted for shadow-DOM interactions)
**Test Date:** 2026-07-05
**Scope:** Follow-up to `Survey_Testing_Report_2026-07-05.md` — user asked to test the remaining templates beyond 3PR. This session tested **all 19 templates** in the Survey Builder "Templates" tab.

---

## 1. Summary — New Confirmed Bug

**A new High-severity, reproducible bug was found:** any survey created from a template that includes a **Multiple Choice question** (internally: "Selection Mode: Single Select" or "Multi Select") **cannot be saved in any form** — not published, not even saved as a draft.

- The save request `POST https://api-survey.visipoint.me/api/v1/questions/survey/{id}/link` returns **HTTP 422** for every affected template, every time, on both "Save and publish" and "Save as a draft."
- The frontend does not surface the real validation error. Instead it shows:
  > **"Failed to save survey: [object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object]"**
- This is a **different, new defect** from the previously-fixed "Next button label" default-data gap noted in the earlier report — it reproduces even after every other field (including Next button label) is correctly filled in.
- **8 of 19 templates (42%) are completely unusable for creating a new survey** as a direct result.

---

## 2. Full Template Test Matrix

| # | Code | Template Name | Questions | Contains Multiple Choice? | Result |
|---|------|---------------|-----------|---------------------------|--------|
| 1 | 3PR | 360-Degree Performance Review | 6 | No | ✅ Published (after filling 2 blank "Next button label" fields — see prior report) |
| 2 | BAS | Brand Awareness Survey | 5 | **Yes** | ❌ **Failed — HTTP 422** |
| 3 | CSAT | CSAT — Customer Satisfaction | 2 | No | ✅ Published |
| 4 | CCS | Competitor Comparison Survey | 4 | **Yes** | ❌ **Failed — HTTP 422** |
| 5 | CSS | Customer Satisfaction Survey | 4 | **Yes** | ❌ **Failed — HTTP 422** |
| 6 | DPS | Demographic Profile Survey | 3 | **Yes** (all 3 questions) | ❌ **Failed — HTTP 422** |
| 7 | ESS | Employee Satisfaction Survey | 6 | **Yes** | ❌ **Failed — HTTP 422** |
| 8 | IBS | Industry Benchmark Survey | 5 | No | ✅ Published |
| 9 | NPS | Net Promoter Score (NPS) Survey | 2 | No | ✅ Published |
| 10 | PSS | Patient Satisfaction Survey | 6 | No | ✅ Published |
| 11 | PFS | Post-Event Feedback Survey | 6 | No | ✅ Published |
| 12 | PF | Post-Purchase Feedback | 3 | No | ✅ Published |
| 13 | PLF | Product Launch Feedback | 4 | No | ✅ Published |
| 14 | QPS | Quick Pulse Survey | 2 | No | ✅ Published |
| 15 | SCF | Student Course Feedback | 6 | **Yes** | ❌ **Failed — HTTP 422** |
| 16 | STFS | Support Ticket Follow-up Survey | 4 | No | ✅ Published |
| 17 | THC | Team Health Check | 5 | No | ✅ Published |
| 18 | TTQ | Team Trivia Quiz | 3 | **Yes** (Q1) | ❌ **Failed — HTTP 422** |
| 19 | WUS | Website Usability Survey | 5 | **Yes** (1 question) | ❌ **Failed — HTTP 422** |

**Result: 11 passed / 8 failed.** Every failure contained a Multiple Choice question; every pass did not. This correlation held across all 19 templates without exception.

---

## 3. Reproduction Steps (any failing template, e.g. BAS)

1. Survey Builder → Templates tab → pick a template with a Multiple Choice question (e.g. BAS, CCS, CSS, DPS, ESS, SCF, TTQ, WUS)
2. "Choose and customize" → give it a name → Create
3. Fill in any blank required fields (e.g. "Next button label" on Comment-type questions)
4. Click **"Save and publish"** (or **"Save as a draft"** — both fail identically)
5. **Expected:** Survey saves/publishes successfully, as it does with every other template
6. **Actual:** Toast shows "Failed to save survey: [object Object],[object Object]..." (×10). Network tab shows `POST /api/v1/questions/survey/{id}/link` → 422. Survey remains permanently stuck in Draft with 0 responses; every retry (draft or publish) fails the same way.

All question counts matched their template card's stated count in every case (e.g. BAS's card says "5 Questions" and 5/5 questions were correctly imported into the editor) — so the **question import itself is not broken**; the failure happens specifically at the save/link step, and specifically for the Multiple Choice question type.

---

## 4. Severity & Impact

- **Severity: High.** A permanently broken save action is a hard blocker, not a UX rough edge — the user loses all work on that survey (it can never be published or even saved as a draft) and has no path to recovery within the product.
- **Scope:** 8 of the 19 pre-built templates in the template library are affected. This is likely to affect any real user who happens to pick one of the templates that includes a Multiple Choice question (a very common survey question type), not just a synthetic edge case.
- **Compounding frontend bug:** even when this happens, the error message is unreadable (`[object Object]` repeated), so a real user gets zero information about what went wrong or how to fix it — this mirrors the class of bug previously fixed under CL-17673/17684 (generic/unhelpful error messaging), but is arguably worse since it exposes raw JS internals instead of even a generic message.

## 5. Suggested root cause area (for the dev team, not verified)

The consistent, exact correlation with "Multiple Choice" question type strongly suggests the bug is in how the Multiple Choice question's answer-options payload is serialized/validated by the backend's `/questions/survey/{id}/link` endpoint when the survey is newly created from a template (as opposed to a Multiple Choice question added manually in the builder, which was not tested here and may behave differently — worth a quick follow-up check).

---

## 6. Test Data Created

19 new survey records were created in the "UK (TESTING)" environment (11 published as Active, 8 stuck in Draft as a direct result of this bug). All are prefixed `QA <CODE> Template Test` for easy identification and cleanup.

---

## 7. Recommendation

File as a new **High-priority bug** against the Survey Module epic (CL-16841), distinct from the previously-fixed template bugs. Suggested title:

> **[Survey Builder] Surveys created from templates containing a Multiple Choice question fail to save (draft or publish) with HTTP 422 and an unreadable error message**

---

*Report generated by Claude Code — AI-assisted browser testing session (Claude-in-Chrome + JavaScript-assisted DOM interaction for shadow-DOM elements)*
