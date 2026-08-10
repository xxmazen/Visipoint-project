# Survey Module — Full Regression Pass

**Platform:** visipoint.uk (UK (TESTING) Company Dashboard)
**Tester:** Claude Code (AI-assisted regression testing via Chrome automation)
**Test Date:** 2026-07-09
**Scope:** Fresh, independent valid/invalid scenario pass across all Survey module pages — Survey Builder, Survey Responses, Survey Overall, Survey Reports (+ Create Report, Compare Surveys), Survey Management (Journeys). Not scoped to retesting specific prior bugs, though several were incidentally re-confirmed.

---

## 1. Summary

| Area | Result |
|------|--------|
| Survey Builder — create/validate/activate/publish | ✅ All valid + invalid scenarios pass |
| Survey Builder — template creation (BAS) | ❌ **CL-17843 still broken** (confirmed again) |
| Survey Responses | ❌ **New bug found** — survey selector capped at 20/38 surveys |
| Survey Overall (all 4 tabs) | ✅ Pass, data reconciles |
| Survey Reports — Create Report / Compare Surveys | ✅ All valid + invalid scenarios pass |
| Survey Management — Journeys | ✅ Pass; one prior bug (CL-17689) now confirmed **fixed** |

All test data created during this session (5 test surveys, 1 report, 1 comparison, 1 journey) was deleted afterward — no leftover data in "UK (TESTING)".

---

## 2. Survey Builder

### Valid scenarios — all passed
- Create survey from scratch → editor opens with "No questions yet"
- Add a Star Rating question, fill title → Save as Draft → redirects to list, status = **Draft**
- Activate a survey with 1 valid question → confirmation dialog → status changes **Draft → Active**
- Share modal → Copy Link → button shows "Copied", toast "Public survey link copied to clipboard!"
- Column Chooser → unchecking "Responses" hides the column live; re-checking restores it

### Invalid scenarios — all correctly blocked
- Create Survey with empty name → inline error "Survey name is required"
- Create Survey with a duplicate name → inline error "Survey with this title already exists"
- Add Question with empty title → Save as Draft → inline error "Question title is required."
- Activate a survey with 0 questions → confirmation dialog → API returns 400 → toast: **"Cannot activate a survey with no questions. Add at least one question before activating."** (clear, actionable — confirms the CL-17671/CL-17673 fix from 2026-07-05 still holds)

### ❌ CL-17843 — still broken (re-confirmed, not fixed)
Created a survey from the **BAS** ("Brand Awareness Survey") template. The 5 template questions import correctly (confirms CL-17682 fix holds). Filled in all required fields (including the blank "Next button label" on the Comment question, a known friction point, not a bug). On **Save as a draft**:
- `POST https://api-survey.visipoint.me/api/v1/questions/survey/{id}/link` → **HTTP 422**, three times (retried)
- Toast shown: **"Failed to save survey: [object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object]"** — same unreadable format as originally reported 2026-07-05, still present as of 2026-07-06 ("Back to Development").
- **This confirms the ticket is still genuinely unfixed as of 2026-07-09.**

---

## 3. Survey Responses — ❌ New bug found

**[NEW] Survey selector sidebar hard-caps at 20 of 38 surveys — no scroll-load-more, no search box**

- Steps: Navigate to Survey Responses → observe the "SURVEYS" sidebar list → click "Select All" → scroll to the bottom of the list (verified with both JS `scrollTop` and a real mouse-wheel scroll)
- Expected: All 38 surveys are selectable (or a way to search/load more is provided)
- Actual: Exactly 20 surveys load and are selectable; "X selected" caps at 20; the list's scroll container reaches `scrollHeight - clientHeight` with no further items appearing; there is no search/filter input anywhere in the sidebar
- **Impact confirmed real, not cosmetic:** cross-checked against the Survey Overall page (which has no such cap) and found survey **"VBBBB"** has 2 real responses (matches the sitewide total). VBBBB is not among the first 20 (most-recently-edited) surveys, so **its responses are completely inaccessible from the Survey Responses page** — no way to select it, filter for it, or search for it.
- Widened the date filter to `01/01/2026–07/09/2026` and confirmed the 20 pre-selected (response-less) surveys still show "No responses found," consistent with the real data living outside the reachable set.
- **Recommend filing as a new bug** against Survey Module epic CL-16841: "Survey Responses page survey-selector is capped at 20 items with no pagination or search, making older/other surveys' responses unreachable."

---

## 4. Survey Overall — ✅ Pass

All 4 tabs tested with the default period:
- **Overview**: "Overall" row responses (2) reconciles with the sum of individual survey rows (VBBBB: 2, all others: 0) once scrolled to find VBBBB
- **Metrics**: per-survey CSAT/NPS trend cards render correctly
- **Performance**: "Best Performers" table correctly ranks VBBBB (2 responses, NPS 50, position 1)
- **Averages**: weekday × hour heatmap renders correctly for Overall and per-survey, values match Metrics tab

No issues found. Data is internally consistent across tabs.

---

## 5. Survey Reports

### Create Report — ✅ Pass (redesigned page, confirms 2026-07-01 fix holds)
The Create Report page has been redesigned since the original 2026-06-17 report: it now has Report Name, Source (survey multi-select + response source + response filters), Time Period, Output Sections, Delivery, and a live "Report Preview" side panel.
- Empty Report Name → Generate Report → scrolls to field, red border, inline error **"Please enter a report name before generating."** (CL-17687/17688 fix confirmed still working)
- Valid name + survey selected → Generate Report → "Report Ready!" modal → View Report → report view renders with question breakdown and a new **"AI Summary"** section (not previously documented) → PDF export triggers with no console errors
- Delete report → confirmation dialog "will be permanently removed" → deletes correctly

### Compare Surveys — ✅ Pass
- Select 2 surveys, set baseline, name the comparison → Save Comparison Report → "Comparison Saved!" modal → View Report
- Comparison view renders Overview/Deltas tabs, 7 metrics, Overall Winner banner — consistent with the CL-17699 tooltip-testing session structure from 2026-07-06
- Delete comparison report → same confirmation + delete flow as Create Report → works

---

## 6. Survey Management (Journeys)

### Valid scenario — ✅ Pass
- Create Journey with name + survey (jkjk) + site (UK (Testing)) → Save → toast **"Created successfully"**
- Edit Journey → Description field shows placeholder "Journey Description (Optional)", **not** literal "null" (CL-17683/17686 fix confirmed still holding)
- Added a description, clicked **Update** → toast **"Updated successfully"**

### ✅ CL-17689 now confirmed FIXED (previously undocumented)
The original report (BUG-FE-06 / CL-17689) said Update Journey redirected silently with no success toast. This session's Update action showed a clear green **"Updated successfully"** toast. This fix was not previously confirmed in any update log — recommend closing CL-17689 if still open in Jira.

### Invalid scenario — consistent with known platform pattern
- Create Journey with all fields empty → Save → **nothing happens**: no error, no highlight, no toast, page stays on the same form
- This matches the QA rule "silent submit on empty Create/Add/Save forms is by design across the app" — **not re-filed as a new bug**, but noting it remains unchanged from the original CL-17690 report if that ticket is still open.

- Delete Journey → confirmation dialog "Are you sure you want to Delete Journey" → toast **"Deleted successfully"** → works

---

## 7. Test Data Cleanup

All surveys, reports, comparisons, and journeys created during this session were deleted immediately after use:
- Surveys: `Regression_2026-07-09_A`, `Regression_2026-07-09_B_Empty`, `Regression_2026-07-09_BAS` (all deleted; count restored 38→38)
- Report: `Regression_Report_2026-07-09` (deleted)
- Comparison: `Regression_Compare_2026-07-09` (deleted)
- Journey: `Regression_Journey_2026-07-09` (deleted)

No leftover test data in the "UK (TESTING)" environment.

---

## 8. Recommended Follow-ups

1. **File new bug**: Survey Responses survey-selector capped at 20/38 surveys, no search/load-more — surveys with real response data can become permanently unreachable via the UI.
2. **CL-17843**: Still broken as of 2026-07-09 — no change since 2026-07-06 "Back to Development" status. Continue tracking.
3. **CL-17689**: Appears fixed (Update Journey now shows success toast) — verify and close if still open.
4. **CL-17690** (Create Journey no validation feedback): Unchanged behavior, but this matches the app's known "silent submit" pattern — low priority, consider whether it's worth fixing given the pattern is pervasive elsewhere.
