# Survey Module — Exploratory Testing Report

**Platform:** visipoint.uk (UK Company Dashboard)  
**Tester:** Claude Code (AI-assisted exploratory testing via Playwright MCP)  
**Test Date:** 2026-06-17  
**Parent Jira Task:** [CL-17093 — Live System Testing (SM2)](https://lamasatech.atlassian.net/browse/CL-17093)

---

## 1. Scope

Full exploratory browser testing of all Survey module pages on the Visipoint company dashboard. Every button, form submission, and data flow was exercised and verified via screenshots and console log analysis.

### Pages Covered

| Page | URL |
|------|-----|
| Survey Builder | `/survey-builder` |
| Survey Responses | `/survey-responses` |
| Survey Overall | `/survey-overall` |
| Survey Reports | `/survey-reports` |
| Create Report | `/create-report` |
| Create Comparison | `/create-comparison` |
| Survey Management (Journeys) | `/survey-journeys` |
| Add Survey Journey | `/add-survey-journey` |
| Update Survey Journey | `/update/survey-journey/{id}` |

---

## 2. Testing Methodology

- **Tool:** Playwright MCP browser instance (Chrome, maximized, no headless)
- **Approach:** Click every button, submit every form (empty and filled), observe network responses, capture screenshots at each step
- **Console monitoring:** `.playwright-mcp/console-*.log` files captured all browser errors, warnings, and network failures
- **Screenshot evidence:** 51+ screenshots saved to `D:\Visipoint\` project root
- **Retest cycle:** All reported bugs were retested at least once after initial discovery; one false positive (Save Draft/Publish feedback) was correctly removed after retest

### Key Test Actions Per Page

- Survey Builder: create from scratch, create from template, save as draft, save & publish, activate, share modal, column chooser
- Survey Responses: apply date filter, change survey selection, export to Excel
- Survey Overall: view charts, export to PDF and Excel
- Survey Reports: generate report (empty + configured), download PDF, download Excel, create comparison
- Survey Management: list journeys, add journey (empty + filled), edit journey, view user types

---

## 3. Confirmed Bugs

### 3.1 Backend Bugs — Assigned to Moataz Khaled

#### BUG-BE-01 · [CL-17682](https://lamasatech.atlassian.net/browse/CL-17682) · HIGH
**[Survey Builder] Surveys created from templates have no questions — activation returns HTTP 400**

- **Page:** Survey Builder → Templates tab
- **Steps:**
  1. Go to Survey Builder → TEMPLATES tab
  2. Select any template → "Create from Template" → enter name → confirm
  3. Open the newly created survey
  4. Attempt to activate it
- **Expected:** Survey is created with all template questions imported
- **Actual:** Survey created with 0 questions; activation returns HTTP 400
- **Console:** `Error activateing survey: AxiosError: Request failed with status code 400`
- **Root Cause:** Template creation API does not copy question records into the new survey
- **Jira:** CL-17682

---

#### BUG-BE-02 · [CL-17683](https://lamasatech.atlassian.net/browse/CL-17683) · MEDIUM
**[Survey Management] Journey API returns null for description field instead of empty string**

- **Page:** Survey Management → Edit Journey
- **Steps:**
  1. Survey Management → Select Actions → Edit Journey (on any journey with no description)
  2. Observe the Journey Description field
- **Expected:** Field is empty
- **Actual:** Field contains literal text `null`
- **Console Evidence:** `fetch {id: "8525ec99-...", name: "Survey", description: null, ...}`
- **Root Cause:** Backend stores and returns `null` for the description column instead of `""`
- **Jira:** CL-17683

---

### 3.2 Frontend Bugs — Assigned to Maram Badr

#### BUG-FE-01 · [CL-17684](https://lamasatech.atlassian.net/browse/CL-17684) · HIGH
**[Survey Builder] Activation error toast does not explain why activation failed**

- **Page:** Survey Builder
- **Steps:**
  1. Create a survey from a template (0 questions due to BUG-BE-01)
  2. Click Activate → Confirm
- **Expected:** Toast explains the reason: "Survey must have at least one question"
- **Actual:** Generic error toast with no explanation; confirmation dialog stays open after failure
- **Note:** Toast does appear but provides no actionable information to the user
- **Jira:** CL-17684

---

#### BUG-FE-02 · [CL-17685](https://lamasatech.atlassian.net/browse/CL-17685) · HIGH
**[Survey Responses] Default 30-day filter silently excludes responses outside the date range**

- **Page:** Survey Responses
- **Steps:**
  1. Go to Survey Responses
  2. Select "Lamasatech survey" (has 1 confirmed response from 13/05/2026)
  3. Apply Filters with the default date range (last 30 days, starting ~18/05/2026)
- **Expected:** Response appears OR a message says "X responses exist outside this date range"
- **Actual:** "No responses found for the selected surveys and period" — response silently excluded
- **Workaround:** Manually change start date to include 13/05/2026 → response appears correctly
- **Jira:** CL-17685

---

#### BUG-FE-03 · [CL-17686](https://lamasatech.atlassian.net/browse/CL-17686) · MEDIUM
**[Survey Management] Journey description field displays "null" instead of empty**

- **Page:** Survey Management → Edit Journey
- **Steps:** Same as BUG-BE-02 — the UI side of the same issue
- **Expected:** Empty description field
- **Actual:** Field pre-filled with literal text `null`
- **Root Cause:** Frontend binds the API `null` value directly without a null guard (`value={description ?? ""}`)
- **Jira:** CL-17686

---

#### BUG-FE-04 · [CL-17687](https://lamasatech.atlassian.net/browse/CL-17687) · MEDIUM
**[Survey Reports] Report name field has no validation — generates "Untitled Report"**

- **Page:** Survey Reports → Create Report
- **Steps:**
  1. Survey Reports → New Report
  2. Leave Report Name empty
  3. Click Generate Report
- **Expected:** Validation error — name is required
- **Actual:** Report created as "Untitled Report" permanently saved in the reports list
- **Jira:** CL-17687

---

#### BUG-FE-05 · [CL-17688](https://lamasatech.atlassian.net/browse/CL-17688) · MEDIUM
**[Survey Reports] Generate Report fires immediately with default config and no confirmation**

- **Page:** Survey Reports → Create Report
- **Steps:**
  1. Survey Reports → New Report
  2. Change nothing
  3. Click Generate Report
- **Expected:** Required fields validated or confirmation warning shown
- **Actual:** Report generated immediately with all defaults → 0 responses, 0 questions, no title
- **Jira:** CL-17688

---

#### BUG-FE-06 · [CL-17689](https://lamasatech.atlassian.net/browse/CL-17689) · MEDIUM
**[Survey Management] Update Journey shows no success notification after saving**

- **Page:** Survey Management → Edit Journey
- **Steps:**
  1. Survey Management → Select Actions → Edit Journey
  2. Make any change (or none) → click Update
- **Expected:** Success toast: "Journey updated successfully"
- **Actual:** Page silently redirects back to the journey list with no feedback
- **Jira:** CL-17689

---

#### BUG-FE-07 · [CL-17690](https://lamasatech.atlassian.net/browse/CL-17690) · MEDIUM
**[Survey Management] Create Journey shows no validation feedback when name is empty**

- **Page:** Survey Management → Create Journey
- **Steps:**
  1. Survey Management → Create Journey
  2. Leave Journey Name empty → click Save
- **Expected:** Inline validation error on the name field
- **Actual:** Nothing happens — no error, no highlight, no toast
- **Jira:** CL-17690

---

#### BUG-FE-08 · [CL-17691](https://lamasatech.atlassian.net/browse/CL-17691) · LOW
**[Survey Builder] Malformed SVG path error thrown on every page load**

- **Page:** All survey pages
- **Steps:** Navigate to any survey page → open DevTools Console
- **Expected:** No SVG errors
- **Actual:** `Error: <path> attribute d: Expected number, "… 2.27861 12.4233C2.16267 12.3648…"`
- **Source:** `https://visipoint.uk/js/chunk-vendors.a2e1fdb1.js:78`
- **Impact:** Broken/invisible icon on all pages; pollutes the console
- **Jira:** CL-17691

---

#### BUG-FE-09 · [CL-17692](https://lamasatech.atlassian.net/browse/CL-17692) · LOW
**[Survey Builder] DevExtreme W0019 license key missing warning**

- **Page:** Survey Builder
- **Steps:** Navigate to Survey Builder → open DevTools Console
- **Expected:** No license warnings
- **Actual:** `W0019 - DevExtreme: Unable to Locate a Valid License Key.`
- **Source:** `https://visipoint.uk/widgets/react-survey-widget.iife.js:175`
- **Impact:** Potential watermarks or restricted functionality in production; EULA compliance risk
- **Jira:** CL-17692

---

## 4. Bug Summary Table

| # | Jira | Title | Type | Priority | Assignee |
|---|------|-------|------|----------|----------|
| 1 | [CL-17682](https://lamasatech.atlassian.net/browse/CL-17682) | Template surveys created with no questions | Backend | High | Moataz Khaled |
| 2 | [CL-17683](https://lamasatech.atlassian.net/browse/CL-17683) | Journey API returns null for description | Backend | Medium | Moataz Khaled |
| 3 | [CL-17684](https://lamasatech.atlassian.net/browse/CL-17684) | Activation error toast gives no explanation | Frontend | High | Maram Badr |
| 4 | [CL-17685](https://lamasatech.atlassian.net/browse/CL-17685) | Default 30-day filter silently hides responses | Frontend | High | Maram Badr |
| 5 | [CL-17686](https://lamasatech.atlassian.net/browse/CL-17686) | Journey description shows "null" | Frontend | Medium | Maram Badr |
| 6 | [CL-17687](https://lamasatech.atlassian.net/browse/CL-17687) | Report name field has no validation | Frontend | Medium | Maram Badr |
| 7 | [CL-17688](https://lamasatech.atlassian.net/browse/CL-17688) | Generate Report fires with no config warning | Frontend | Medium | Maram Badr |
| 8 | [CL-17689](https://lamasatech.atlassian.net/browse/CL-17689) | No success notification after Update Journey | Frontend | Medium | Maram Badr |
| 9 | [CL-17690](https://lamasatech.atlassian.net/browse/CL-17690) | No validation on empty Journey Name | Frontend | Medium | Maram Badr |
| 10 | [CL-17691](https://lamasatech.atlassian.net/browse/CL-17691) | Malformed SVG path error on every page load | Frontend | Low | Maram Badr |
| 11 | [CL-17692](https://lamasatech.atlassian.net/browse/CL-17692) | DevExtreme W0019 license key missing | Frontend | Low | Maram Badr |

**Total: 11 bugs** — 2 Backend · 9 Frontend  
**High priority: 3** · **Medium priority: 6** · **Low priority: 2**

---

## 5. False Positives Removed

The following were initially suspected bugs but were confirmed working correctly after retesting:

| # | Initial Claim | Retest Result |
|---|--------------|---------------|
| Save as Draft | No feedback after save | Green "Survey saved to draft" toast appeared correctly. Removed. |
| Save & Publish | No feedback after save | Survey status changed Draft → Active correctly. Removed. |

---

## 6. Console Log Files

Captured during the session at `D:\Visipoint\.playwright-mcp\`:

| File | Session |
|------|---------|
| `console-2026-06-17T15-16-39-165Z.log` | Survey Builder (initial) |
| `console-2026-06-17T15-31-57-548Z.log` | Survey Management / Journeys |
| `console-2026-06-17T15-52-34-738Z.log` | Survey Builder (activation retest) |
| `console-2026-06-17T16-56-49-162Z.log` | Survey Builder (full session — all major errors) |
| `console-2026-06-17T17-06-05-196Z.log` | Create Report session |

Key errors logged:
- `Error: <path> attribute d: Expected number` — every session (BUG-FE-08)
- `W0019 - DevExtreme: Unable to Locate a Valid License Key` — Survey Builder sessions (BUG-FE-09)
- `Failed to load resource: status 401 @ api-survey.visipoint.me/api/v1/surveys` — page load race (not a bug)
- `Error activateing survey: AxiosError: Request failed with status code 400` — activation sessions (BUG-BE-01)

---

## 7. Screenshot Evidence

All screenshots saved to `D:\Visipoint\` project root.

| Screenshot | Content |
|-----------|---------|
| `36_survey_reports.png` | Survey Reports page |
| `37_create_report.png` | Create Report form |
| `38_report_period.png` | Report period selector |
| `39_generate_report_result.png` | Generated report modal |
| `40_report_pdf.png` | PDF download result |
| `41_report_xlsx.png` | Excel download result |
| `42_create_comparison.png` | Create Comparison page |
| `43_survey_journeys.png` | Survey Management list |
| `44_add_journey.png` | Add Journey form |
| `45_add_journey_filled.png` | Add Journey with data |
| `46_journey_created.png` | Journey created result |
| `47_edit_journey.png` | Edit Journey form (null description visible) |
| `48_update_journey_result.png` | After Update Journey (no toast) |
| `49_user_types.png` | User Types within journey |
| `50_survey_overall.png` | Survey Overall page |
| `51_columns_chooser.png` | Columns chooser modal |
| `retest_save_draft.png` | Save Draft toast (confirmed working) |
| `retest_save_publish.png` | Survey status → Active (confirmed working) |
| `retest_activate_confirm.png` | Activate confirmation dialog |
| `retest_activate_result.png` | After activation (400 error state) |
| `retest_activate_400_confirmed.png` | 400 error confirmed in console |
| `retest_activate_toast.png` | Generic error toast (no explanation) |
| `retest_update_journey.png` | After Update Journey redirect (no toast) |
| `retest_create_journey_save.png` | Save with empty name (no validation) |
| `retest_generate_report.png` | Report Ready modal (no name, no survey) |

---

## 8. Technical Notes

### React Survey Widget
- Embedded as `react-survey-widget.iife.js`
- Renders in its own **iframe context** — toasts from save/publish/activate actions render inside the widget iframe, not in the main document DOM
- This is why `MutationObserver` on `document.body` returns empty — it cannot observe the widget iframe's shadow DOM
- Consequence: automated screenshot capture of short-lived toasts (2–3 seconds) is unreliable; DOM state changes (e.g. status field value) are used as the ground truth instead

### DevExtreme
- Used for data grids in the Survey Builder
- License warning `W0019` appears on every load from `react-survey-widget.iife.js:175`
- Without a valid license key, DevExtreme may render watermarks or restrict grid functionality in production

### Survey API
- Base URL: `https://api-survey.visipoint.me/api/v1/surveys/`
- A transient `401` on page load (before auth token is ready) is expected and not a bug — the widget retries after the token is available
- The `400` on activation is a genuine backend error tied to surveys having 0 questions

---

*Report generated by Claude Code — AI-assisted exploratory testing session*  
*Jira Sub-bugs: CL-17682 through CL-17692 under parent CL-17093*

---

## UPDATE (2026-07-01) — Regression Retest

- **CL-17682 (BUG-BE-01, template surveys 0 questions): CONFIRMED FIXED.** A template-derived survey ("Template_Test_3PR_...") now has 6 imported questions and is Active.
- **CL-17687 (BUG-FE-04, no report name validation): CONFIRMED FIXED.** The Create Report page was redesigned; empty Report Name now shows inline error "Please enter a report name before generating." and blocks submission.
- **CL-17688 (BUG-FE-05, Generate Report fires with no warning): CONFIRMED FIXED** — same validation as above blocks it.
- **CL-17683 / CL-17686 (journey description shows "null"): CONFIRMED FIXED.** Edit Journey now shows an empty placeholder ("Journey Description (Optional)"), not literal "null" text.
- **NOT A BUG (reclassified 2026-07-01):** Survey Management journey list "Survey" column showing "-" for journey "J1" despite its Edit Journey form showing survey "Test342" assigned — confirmed intentional application logic, not a defect.

Full details in `D:\Visipoint md files\Multi_Module_Testing_Report_2026-07-01.md`.

---

## UPDATE (2026-07-05) — Valid/Invalid Scenario Testing Session

Full report: `Survey_Testing_Report_2026-07-05.md`.

- Re-confirmed CL-17671/CL-17682 (template surveys created with 0 questions) and CL-17673/CL-17684 (generic activation error toast) remain **fixed**. Activation of a 0-question survey now shows: "Cannot activate a survey with no questions. Add at least one question before activating."
- 5 valid scenarios (create from scratch, add question, publish, create from template, publish template survey) all passed.
- 5 invalid scenarios (empty survey name, duplicate survey name, activate with 0 questions, empty question title, empty required "Next button label") all correctly blocked with clear inline/toast validation messages.
- **No new bugs found.**
- **Observation (non-bug):** Template "3PR — 360-Degree Performance Review" ships with 2 Comment-type questions whose required "Next button label" field is blank by default — validation correctly blocks publish until filled, but adds friction to the template quick-start flow. Not filed as a bug; flagged for awareness only.

---

## UPDATE (2026-07-05, part 2) — All 19 Templates Tested — New High-Severity Bug Found

Full report: `Survey_Templates_Bug_Report_2026-07-05.md`.

Tested all 19 templates in the Templates tab (previously only 3PR had been tested). **Found a new, reproducible High-severity bug distinct from the "Next button label" observation above:**

- **8 of 19 templates (BAS, CCS, CSS, DPS, ESS, SCF, TTQ, WUS) cannot be saved in any form** — both "Save and publish" and "Save as a draft" fail every time with `POST /api/v1/questions/survey/{id}/link` → **HTTP 422**.
- The error toast is unreadable: **"Failed to save survey: [object Object],[object Object]..."** (×10) — no actionable information for the user.
- **100% correlation:** every failing template contains a **Multiple Choice** question (Selection Mode: Single/Multi Select); every one of the 11 passing templates (3PR, CSAT, IBS, NPS, PSS, PFS, PF, PLF, QPS, STFS, THC) does not.
- Question import itself is correct in all cases (counts match template cards) — the failure is specifically at the save/link step for Multiple Choice question data.

**Recommend filing as new bug** against Survey Module epic CL-16841: "Surveys created from templates containing a Multiple Choice question fail to save with HTTP 422 and an unreadable error message."

---

## UPDATE (2026-07-05, part 3) — API Performance Test Corrects Root Cause + Finds New Idempotency Bug

Full report: `Survey_Link_API_Performance_Report_2026-07-05.md`.

Ran a 60-request performance benchmark directly against `POST /api/v1/questions/survey/{id}/link` (captured real JWT + payload from the live app, replayed via fetch). Two findings:

1. **Performance is fine:** 60/60 requests succeeded (201), ~250-420ms typical, no errors, no degradation trend.
2. **New Medium-High bug — endpoint is not idempotent:** replaying the identical single-question payload 60 times created 60 duplicate question records (confirmed via GET — survey had 61 questions after, should have had 1). No validation/dedup on repeat "link" calls.
3. **Corrects the part-2 root-cause theory:** a lone Multiple Choice question links and publishes successfully every time. The 422 failures only reproduce with the *original template's full multi-question batch* sent together — so the trigger is something in that specific batch payload (e.g. duplicate sort_order/option IDs), not "Multiple Choice question type" in general as previously concluded.

Test survey polluted with 61 duplicate questions was deleted immediately after discovery (cleanup confirmed, survey count restored to 36).

---

## UPDATE (2026-07-06) — CL-17843 / CL-17844 Retest

Full report: `Survey_Builder_Bugs_2026-07-05.md` (Retest sections).

Backend dev (Moataz Khaled) marked both sub-bugs "Ready for Testing." Retested both:

- **CL-17844 (link endpoint not idempotent): CONFIRMED FIXED.** Captured a fresh real `/link` request and replayed it 10 times against the same survey — all 10 replays returned the same question ID/`created_at` as the original; only 1 question persisted after 11 total calls (previously 61 from 60 replays). Jira comment posted confirming the fix.
- **CL-17843 (8 templates fail with HTTP 422): STILL FAILING — NOT FIXED.** Recreated all 8 originally-affected templates (BAS, CCS, CSS, DPS, ESS, SCF, TTQ, WUS) from scratch; every one ended up stuck in Draft with 0 linked questions, identical to the original report (TTQ also reproduced the broken "[object Object]" toast). Ticket appears to have been moved to "Ready for Testing" prematurely, likely conflated with the CL-17844 fix. Jira comment posted with the full per-template breakdown; ticket transitioned back to **"Back to Development."**

All test surveys created during both retests (10 total for CL-17843, 1 for CL-17844) were deleted via API afterward — no leftover data in the "UK (TESTING)" environment.

---

## UPDATE (2026-07-06, part 2) — CL-17699 Tooltip Testing (Comparison Report)

**Environment:** `appqa.visipoint.me` (QA environment, entity "QA testing") — different from the "UK (TESTING)" environment used in all prior Survey Module sessions above.
**Scope:** 30 test cases (CL-17729–CL-17761) covering metric tooltips across the Survey Comparison Report's Overview, Deltas, and Questions tabs.

- Moved CL-17704 (Testing subtask) to **In Progress** before starting.
- **Overview tab (8/8 labels) and Deltas tab (7/7 column headers): all tooltip copy matches the approved AC text exactly**, word-for-word — metric wins, NPS Score, CSAT Score, Completion Rate, Response Volume, Average Score, Avg. Duration, Drop-off Rate, Baseline, Comparator, Δ Absolute, Δ Relative, Direction, Winner, Significant? all pass.
- **Questions tab section headers ("Matched Questions", "Unmatched Questions"): pass** — correct copy, underline + icon present.
- **2 new bugs found in the Unmatched Questions chips**, filed as Sub-bugs under CL-17699, assigned to Maram Badr (frontend dev on CL-17702), linked to their respective test cases:
  - [CL-17845](https://lamasatech.atlassian.net/browse/CL-17845) — chips render with only the ⓘ icon, no visible dotted-underlined label/code at all (violates AC10)
  - [CL-17846](https://lamasatech.atlassian.net/browse/CL-17846) — chip tooltip copy is missing the phrase "code represents a" (reads "This question has no matching question..." instead of "This code represents a question that has no matching question...", violates AC11)
  - Both bugs linked to each other — likely the same root cause (chip label dropped from markup, tooltip copy adjusted afterward to compensate but ended up diverging from spec).
- General tooltip behavior (single tooltip at a time, closes on tab switch, closes on mouse-away) confirmed working throughout.
- **Not fully verifiable this session:** CL-17759 (overflow on narrow screens) and CL-17760 (edge positioning) — browser window resize did not take effect in this environment, so a narrow viewport couldn't be forced. Recommend a manual pass to close these out. CL-17761 (rapid-hover flicker) not observed but not stress-tested either.
- CL-17704 (Testing) left **In Progress** pending the two new bugs being fixed and retested.

---

## UPDATE (2026-07-09) — Full Fresh Regression Pass (independent of prior findings)

Full report: `Survey_Testing_Report_2026-07-09.md`.

Covered every Survey module page (Builder, Responses, Overall, Reports, Create Report, Compare Surveys, Journeys) with fresh valid/invalid scenarios, not scoped to any specific prior bug.

- **New bug found:** Survey Responses page survey-selector sidebar hard-caps at 20 of 38 surveys, no scroll-load-more, no search box. Confirmed real impact: survey "VBBBB" (2 real responses, matches sitewide total) is not among the reachable first 20 — its responses cannot be viewed via this page at all. Recommend filing against CL-16841.
- **CL-17843 (8 templates fail to save with HTTP 422):** Re-confirmed still broken (tested BAS template) — same unreadable "[object Object]" toast, same 422 on `/link`. No change since 2026-07-06.
- **CL-17689 (no success toast after Update Journey): now confirmed FIXED** — "Updated successfully" toast now appears. Not previously documented as fixed in any update log.
- Re-confirmed still working: empty-name/duplicate-name/empty-question-title validation in Survey Builder, 0-question activation block with clear toast (CL-17671/17673), template question import (CL-17682), Create Report name validation (CL-17687/17688) on the redesigned Create Report page, journey description placeholder fix (CL-17683/17686).
- Create Journey with all fields empty still gives zero feedback — matches the platform-wide silent-submit pattern, not re-filed as new.
- Noted new UI additions not previously documented: Create Report page redesign (Response Filters, live Report Preview panel), "AI Summary" section on report view.
- All test data (5 surveys, 1 report, 1 comparison, 1 journey) cleaned up after use.

---

## UPDATE (2026-07-16) — CL-17724 Conditions/Skip Logic Feature — 32 Test Cases Executed

**Environment:** `appqa.visipoint.me` (QA environment, entity "QA testing") → `qa.app.d.visipoint.dev`.
**Story:** [CL-17724](https://lamasatech.atlassian.net/browse/CL-17724) — single-select question condition/skip-logic rules in Survey Builder.
**Scope:** All 32 linked Test Case sub-issues (CL-17765–CL-17796) covering: inline rendering, badge numbering, add/reorder conditions, answer-picker rules (order, disabling used answers, multi-select), jump-to-picker rules (forward-only, Thank You always available, reusable targets), runtime routing (top-to-bottom evaluation, default order, catch-all, skip), full-assignment edge cases, broken-reference handling, deletion renumbering, and validation.

Built a dedicated test survey ("CL-17724 Conditions QA Test") with a single-select Q1 (Yes/No/Maybe/Not sure) and 3–5 follow-up questions to exercise every scenario, both in the builder and live via the public survey link.

**Result: 30/32 PASSED, 2/32 FAILED.**

### New bugs found (filed as Bug sub-issues linked to CL-17724)
- **Condition not auto-removed when reordering breaks the forward-only constraint** (test case CL-17791). Expected: condition should be automatically removed once its jump target is reordered to before the current question. Actual: the condition survives with its jump-to field silently cleared and a "This condition is broken and needs to be fixed." warning — same treatment as a deleted jump-target question (CL-17789), not auto-removal. Blocks Save/Publish until manually fixed. Medium severity.
- **Deleting a condition corrupts the promoted condition's jump target** (test case CL-17792). Badge renumbering and the promoted condition's *answer* selection work correctly, but its **jump-to target silently gets overwritten with the deleted condition's old target** instead of retaining its own. Verified via DOM input-value inspection (not just visual) and confirmed it persists across re-navigation — this is real corrupted state, not a render glitch. High severity — silently misroutes respondents with no warning to the builder.

### Confirmed working (no issues)
All other 30 test cases passed as specified, including: inline Conditions section placement, badge=1 default / sequential increment, drag-and-drop reorder of conditions (requires real OS-level drag — synthetic JS `DragEvent` dispatch does **not** trigger it, see Technical Notes), answer-picker order matching question button order, used-answers permanently disabled across all subsequent conditions, jump-to picker correctly excludes current/previous questions and always includes Thank You, reusable jump targets never disabled, runtime top-to-bottom first-match routing, default-order fallback when no condition matches, single-condition catch-all with multiple answers, skip-question fires no condition, all-answers-disabled + Add Condition disabled once fully assigned, single-answer-button question caps at 1 condition, broken-reference detection + warning on deleted target question, deleting the last condition doesn't affect earlier ones, multi-answer selection allowed per condition, and empty jump-to blocked with an inline "Each condition must have a 'Jump to' destination." validation error.

### Technical notes for future sessions
- The Conditions drag-and-drop reorder (and the Question list reorder in the right-hand panel) use native HTML5 drag-and-drop (`draggable="true"`, `.cbl-block` / `.cbl-drag-handle` classes) — a real OS-level drag via the `computer` tool's `left_click_drag` action works, but synthetic `dispatchEvent(new DragEvent(...))` from JS does **not** trigger a reorder (Chrome ignores untrusted drag events for actual data transfer).
- If a survey has any language added under the Survey Builder's "Language" tab, the "Save and publish" / "Save as a draft" buttons move from the bottom of the Questions/Style tabs to the bottom of the Language tab only.
- Login flow quirk: after selecting a company entity from `appqa.visipoint.me/dashboard`, the SSO redirect to `qa.app.d.visipoint.dev` is genuinely slow (~15-20s, heavy webpack bundle with many per-route CSS chunks) — this is not a hang, just wait it out. A "Security Policy Updated" (mandatory 2FA) nag modal also appears on a short delay after the dashboard loads and will intercept the next click if not dismissed first.

---

## UPDATE (2026-07-29) — System + API Testing (Valid/Invalid), CL-17843 Confirmed Fixed

Full report: `Survey_Testing_Report_2026-07-29.md`.

- **CL-17843 (8 templates fail to save with HTTP 422): CONFIRMED FIXED.** Retested via the BAS template (one of the 8 originally-affected, containing the Multiple Choice question that triggered it) — published successfully with all 5 questions correctly linked. Last confirmed still-broken 2026-07-09; now fixed as of this session. Recommend closing the ticket.
- 2/2 UI valid scenarios passed (create-from-scratch + publish, create-from-template + publish), 4/4 UI invalid scenarios correctly blocked (empty survey name, duplicate survey name, empty question title, empty required Next-button-label).
- **New technique for future sessions — direct API testing without going through the UI:** the survey API (`api-survey.visipoint.me`) uses its own bearer token + tenant ID, stored separately from the main app's token at `localStorage.survey` (`{access_token, tenant_id}`) — NOT the same as `localStorage.user.data.access_token` (that one returns 401 against the survey API). Send `Authorization: Bearer <survey.access_token>` and `X-Tenant-Id: <survey.tenant_id>` headers with `fetch()` from the page's own JS context to call the API directly.
- 10 API-level valid/invalid test cases (list, get-by-id, create, delete, auth-missing, auth-garbage, not-found, empty-body validation, duplicate-title conflict, malformed `/link` payloads) all returned correct, well-structured responses — no new bugs. Notably, the duplicate-title rule (A8) and empty-body field validation (A7, A9, A10) are enforced server-side with clean FastAPI-style field-path error messages, not just client-side in the UI.
- All 4 test surveys created (3 via UI, 1 via direct API POST) were deleted via `DELETE /api/v1/surveys/{id}` afterward; survey count confirmed restored to baseline (42).

---

## UPDATE (2026-07-31) — CL-17685 Retest: STILL PRESENT, NOT FIXED

**Trigger:** CL-17685 ("default 30-day filter silently excludes responses outside the date range") had not been retested since the original 2026-06-17 report — flagged as a gap in the 2026-07-31 Jira synthesis pass. This session closed that gap.

**Environment note:** The live environment currently has essentially no real response data to test against — the Survey Builder dashboard's "Total Responses" stat and the survey API's `statistics.total_responses` both reported 3, but querying the actual `GET /api/v1/responses/by-survey/{id}/detailed` endpoint for all 42 existing surveys (with an unrestricted 2020–2026 date range) returned 0 responses for every single one. This 3-vs-0 mismatch is itself a minor data-integrity oddity (likely a stale/orphaned counter, possibly from deleted surveys/responses) — noted for awareness, not filed as a bug, and not the subject of this retest.

**Repro method (fresh data, since no existing response data was usable):**
1. Created a new survey ("CL-17685 Retest Survey") with one Yes/No question, published it.
2. Opened the public survey link in a separate tab and submitted a response ("Yes") — confirmed via the "Survey submitted successfully" toast and the Survey Builder dashboard's response count incrementing 3 → 4.
3. Went to Survey Responses, selected the new survey. With the **default period (last 30 days, includes today)**: response displayed correctly — 1 response, Completed, correct timestamp (17:04:48, Friday 31 July 2026).
4. Changed the period to **01/06/2026–30/06/2026** (a range that excludes the just-submitted response) and clicked Apply Filters.

**Result — bug reproduced:**
- Main panel: "No responses found for the selected surveys and period." — exactly the same generic message as the original 2026-06-17 report, still with **no indication that a response exists outside the selected range**.
- Notably, the **sidebar survey list still showed "1 responses" right next to the empty-state panel** — a visible, unexplained contradiction between the sidebar count and the main panel's "no responses" message, which makes the silent exclusion even more confusing than the original report described.

**Conclusion: CL-17685 is still open, not fixed, ~44 days after the original report (2026-06-17 → 2026-07-31).** No regression — same defect, same missing "responses exist outside this period" messaging that was requested in the original repro.

**Cleanup:** Test survey and its 1 response deleted via the UI's own Delete flow; survey count and total-responses counter confirmed restored to baseline (42 surveys, 3 responses).

---

## UPDATE (2026-07-31, part 2) — CL-17724 Retest Reveals Conditions Feature Was Redesigned

**Full report:** `Survey_Testing_Report_2026-07-31.md`.

**Trigger:** CL-17724's two open bugs (High: deleting a condition corrupts the promoted condition's jump target; Medium: reordering a jump target past the current question doesn't auto-remove the broken condition) hadn't been retested since found on 2026-07-16.

**What happened instead:** Built a fresh replica of the original CL-17724 test survey (single-select Q1 with 4 answers, 3 follow-up questions) to retest both bugs. Discovered the entire multi-condition builder UI is gone — no "Add Condition" button, no numbered condition list, no way to create more than one condition per question. Confirmed via shadow-DOM button enumeration, not just visual inspection. Only a single "If the answer is [multi-select OR] → Jump to [one target]" block remains per question.

**Both open bugs are about interactions between multiple conditions — neither scenario is reproducible anymore, because the underlying multi-condition capability doesn't exist.** This is not a "fixed" status — the feature was replaced, not repaired. **Needs product/dev team confirmation** on whether this was an intentional simplification (in which case CL-17724's two bug tickets should close as no-longer-applicable) or an accidental regression that silently removed a shipped feature (a much bigger issue). Flagged, not resolved — do not close the Jira tickets based on this session alone.

**What does still work:** the single condition that exists was tested end-to-end (set, published, and driven through a real public-link submission) — routing worked exactly as configured.

**Also re-confirmed still open:** Survey Responses sidebar 20-of-42-survey cap on "Select All" — no change since 2026-07-09.

Test survey deleted after testing; survey count restored to baseline (42).

---

## UPDATE (2026-08-03) — CL-17863 Matrix Question Type: Pre-Development Test Scenarios

**Source:** [CL-17863](https://lamasatech.atlassian.net/browse/CL-17863) — "[Survey][Survey Builder] [Matrix Question Type]: As a survey creator, I want to add a matrix question type where I can define rows and columns"
**Status as of 2026-08-03:** **To Do** — not yet built/deployed. Assignee: none. Priority: Medium.

**This is a pre-development test plan, not a test session** — the feature doesn't exist in the live app yet, so nothing below has been executed. These scenarios are derived directly from the story's acceptance criteria (AC1–AC21, plus one unnumbered note) so they're ready to run the moment this ships. Re-verify against the actual implementation before treating any of these as confirmed pass/fail — implementations sometimes diverge from the AC text.

### User story
As a survey creator, I want to define rows and columns in an empty grid so respondents can answer multiple sub-questions using the same answer scale in one compact question (structured comparative feedback without repeating the same scale multiple times).

### AC numbering gap — flag for the team
The story jumps from AC3 to AC6 (AC4/AC5 missing) and has no AC number for the single-select/multi-select toggle described in prose between AC20 and AC21. Worth asking the reporter whether AC4/AC5 were intentionally removed during refinement (in which case nothing is missing) or whether they describe scope this test plan should also cover but currently can't see.

### Valid Scenarios

| # | Scenario | AC | Expected |
|---|----------|-----|----------|
| V1 | Select "Matrix" as the question type on a new question | AC1 | Grid opens with exactly 2 empty columns and 1 empty row — no pre-filled labels or dummy data |
| V2 | Observe empty column/row fields | AC2 | Placeholder text shown: "Column 1", "Column 2", "Row 1 label" |
| V3 | Observe answer cells in the default (unlabeled) grid | AC3 | Radio buttons are visible and clickable immediately — no need to fill labels first to preview |
| V4 | Click "Add column" | AC7 | New empty column appended to the right of existing columns |
| V5 | Keep clicking "Add column" up to 8 total | AC8 | 8th column added successfully; "Add column" becomes visually disabled once at 8 |
| V6 | Hover over a column header (3+ columns present) | AC9 | Small × icon appears top-right of that header cell |
| V7 | Click the × icon on a column header | AC9 | That column is removed entirely |
| V8 | Reduce down to exactly 2 columns, hover over either header | AC10 | × icon no longer appears on either remaining column — floor of 2 enforced |
| V9 | Click into a column header label and type text | AC11 | Label is editable inline; placeholder is replaced by typed text |
| V10 | Click "Add row" | AC13 | New empty row appended below existing rows |
| V11 | Click "Add row" repeatedly (e.g. 20+ times) | AC14 | No cap enforced in this version; button remains active throughout |
| V12 | Hover over a row's label cell (2+ rows present) | AC15 | Small × icon appears on the right side of that row's label cell |
| V13 | Click the × icon on a row | AC15 | That row is removed entirely |
| V14 | Reduce down to exactly 1 row, hover over its label cell | AC16 | × icon no longer appears — floor of 1 row enforced |
| V15 | Click into a row label and type text | AC17 | Label is editable inline; placeholder replaced by typed text |
| V16 | Click a radio button in a row that already has a different cell selected in the same row | AC19 | New cell becomes selected; the previously-selected cell in that row is deselected (one answer per row) |
| V17 | Click an already-selected radio button a second time | AC20 | It deselects — row returns to unanswered state |
| V18 | Select answers in two different rows independently | AC18/19 (cross-row) | Each row's selection is independent — selecting in Row 2 does not affect Row 1's selection |
| V19 | Switch the question to "single select" mode | unnumbered note | All answer cells render as radio buttons (one answer per row) |
| V20 | Switch the question to "multi select" mode | unnumbered note | All answer cells render as checkboxes; multiple cells **within the same row** can be checked simultaneously |
| V21 | Add/remove rows and columns in combination, observing the counter after each action | AC21 | Counter below the grid always reflects the live, accurate row and column counts, e.g. "4 rows · 5 / 8 columns" |
| V22 | Fully configure a Matrix question (labels filled, rows/columns set) and Save/Publish the survey | implied end-to-end | Survey saves/publishes successfully with the Matrix question intact |
| V23 | View a published survey containing a Matrix question via the public respondent-facing link | implied end-to-end | Grid renders correctly for respondents, correct select mode (radio vs. checkbox) |
| V24 | Submit a real response to a Matrix question as a respondent, then check the recorded response data | implied end-to-end | Response is captured correctly per row (this is the actual purpose of the feature — worth extra scrutiny) |

### Invalid / Edge Scenarios

| # | Scenario | AC | Expected |
|---|----------|-----|----------|
| I1 | Attempt to remove a column when exactly 2 remain | AC10 | Blocked — × icon hidden, no way to go below 2 columns |
| I2 | Attempt to add a 9th column when 8 already exist | AC8 | Blocked — "Add column" disabled, no 9th column addable |
| I3 | Attempt to remove the last row when exactly 1 remains | AC16 | Blocked — × icon hidden, no way to go below 1 row |
| I4 | Leave column/row labels as placeholder-only (never typed) and attempt to Save/Publish | not specified in AC | **Unknown — needs verification.** Other Survey Builder question types block publish on empty required fields (e.g. "Question title is required."); check whether the same pattern applies here or whether blank labels are allowed to persist |
| I5 | Leave the Matrix question's own title/label blank and attempt to Save/Publish | platform pattern | Should follow the same "Question title is required." validation seen on every other question type — verify it's not skipped for Matrix specifically |
| I6 | Try to reach a 0-row state via any UI path | AC16 | Should be structurally unreachable (min 1 row always enforced) — confirm no edge case (e.g. rapid double-click) can bypass this |
| I7 | Select answers in several rows, then switch from single-select to multi-select (or vice versa) mid-configuration | **gap — not covered by any AC** | Undefined behavior — does it preserve existing selections, clear them, or produce an invalid mixed state (radio + checkbox)? Flag to the team if this isn't specified before build |
| I8 | Rapidly double-click "Add Column" or "Add Row" | not specified in AC | Should add exactly one column/row per intended click — check for race conditions producing duplicate or skipped rows/columns |
| I9 | Type an extremely long string into a column or row label | not specified in AC | Check for layout breakage (grid overflow) and whether any character limit/truncation is enforced — unspecified in the story, worth flagging as a gap |
| I10 | Attempt to remove a row/column using keyboard only (Tab + Enter, no mouse hover) | **accessibility gap — not covered by any AC** | The × icons in AC9/AC15 are described as hover-reveal only, which is a known accessibility risk for keyboard/screen-reader users. Worth flagging to the team before build, not just testing after — hover-only interaction patterns often need an explicit keyboard-accessible alternative |

### Notes for whoever picks this up for real testing
- AC8's "visually disabled" wording for the 8-column cap should be checked carefully — confirm the button is also *functionally* disabled (not just styled to look disabled while still clickable), a distinction worth testing explicitly given how many other Visipoint bugs have involved buttons that looked right but didn't actually block the action.
- Given the platform-wide "silent submit on empty forms" pattern documented elsewhere in this project (see the `visipoint-module-testing` skill's QA Rules table), don't assume I4/I5 are bugs if blank labels save silently — check whether that's consistent with the rest of the app's established (if debatable) UX pattern before filing.
- This question type is respondent-facing (V23/V24) — allocate real testing time to the public-survey-link rendering and response-capture path, not just the builder UI, since that's the actual value proposition of the feature.

---

## CORRECTION #2 (2026-08-03, same day) — BUG-MX-001 is NOT a bug, it's intentional design

**User correction (2026-08-03):** The Survey Builder's Matrix question grid is a static visual layout only — it is intentionally not interactive by clicking directly in the grid. The radio/checkbox controls are only meant to become interactive via the question's blue "Preview" button, which opens the live-interactive modal. What was reported below as BUG-MX-001 is expected application behavior, not a defect. **Do not re-report this.** See the `visipoint-module-testing` skill's QA Rules table and "Survey Builder — Matrix question type's answer cells are display-only in the grid, by design" note for the corrected guidance. The findings below are kept for historical record, with the verdict corrected inline.

---

## CORRECTION + ACTUAL TEST RESULTS (2026-08-03, same day) — CL-17863 Matrix Question Type IS LIVE

**The premise of the update above was wrong.** Despite Jira showing status "To Do" (unassigned, no resolution), the Matrix question type is fully implemented and selectable in the live Survey Builder as of today. Tested against `qa.app.d.visipoint.dev` (QA environment, entity "QA TESTING"), not the usual `visipoint.uk` production-style tenant — first time this project has tested against that environment. Test survey: "CL-17863 Matrix Question Test" (left as a Draft, not published, in that tenant).

### Result summary: 21/21 numbered ACs pass — no bugs found.

| AC(s) | Result | Detail |
|---|---|---|
| AC1 | ✅ Pass | New Matrix question opens with exactly 2 empty columns, 1 empty row, no pre-filled data |
| AC2 | ✅ Pass | Placeholders confirmed via DOM exactly as specified: "Column 1", "Column 2", "Row 1 label" |
| AC3 | ✅ Pass (corrected 2026-08-03) | Radio buttons in the builder's own grid are display-only by design — they're only meant to become interactive via the "Preview" modal, which works correctly. Originally mis-flagged as a bug (BUG-MX-001, now retracted) — see correction note above |
| AC6–AC8 | ✅ Pass | "+ Add column" always visible; appends to the right; hard caps at 8 with the button gaining a `disabled` class/attribute — verified a click at 8 columns is a true no-op (8→8), not just a visual style |
| AC9–AC10 | ✅ Pass | Hovering a column header reveals a red × (top-right); clicking removes it; at exactly 2 columns the remove button is removed from the DOM entirely (not just hidden), enforcing the floor |
| AC11 | ✅ Pass | Column labels editable inline; typed text replaces placeholder correctly |
| AC12–AC14 | ✅ Pass | "+ Add row" footer button always visible; appends below; added rows up to 13 with zero cap enforcement and the button never disabled, matching "no max in this version" |
| AC15–AC16 | ✅ Pass | Hovering a row's label cell reveals × on the right; removing down to exactly 1 row removes the button from the DOM entirely |
| AC17 | ✅ Pass | Row labels editable inline, same behavior as columns |
| AC18–AC20 | ✅ Pass (via live Preview, not the builder grid — see BUG-MX-001) | In the Preview modal: clicking a cell selects it and deselects any other selection in that row (AC19), clicking the selected cell again deselects it back to unanswered (AC20) |
| Unnumbered single/multi-select note | ✅ Pass | "Selection type" radio (Single choice / Multiple choice) instantly swaps all answer cells between radio buttons and checkboxes, in both the builder grid and Preview. In Multiple choice mode, confirmed multiple checkboxes **within the same row** can be checked simultaneously (e.g. both "Excellent" and "Poor" checked at once on Row 1) |
| AC21 | ✅ Pass | Counter matches the spec's exact format, e.g. observed "13 rows · 2 / 8 columns" and "1 row · 2 / 8 columns" — updates live on every add/remove |

### ~~BUG-MX-001~~ — RETRACTED (2026-08-03) — Builder grid's answer-cell controls are intentionally display-only, not a bug

| Field | Detail |
|-------|--------|
| **Module** | Survey → Survey Builder → Matrix question type |
| **Severity** | Medium |
| **Status** | New, found 2026-08-03 |

**AC3 says:** "The radio buttons inside the answer cells are visible and interactive in the default state so the creator can immediately preview."

**Actual:** In the Survey Builder's own question-editing grid, every answer cell control (`.mqe-radio-btn` in single-select mode, `.mqe-checkbox-btn` in multi-select mode) is rendered with a `disabled` CSS class and `pointer-events: none`, confirmed via computed style inspection — this is not a visual-only "disabled" look, clicks are genuinely swallowed. Verified by clicking directly on a cell in the grid: the radio stayed empty, no state change. This was true in the pristine default 2-col/1-row state, after adding a question title, and after resizing the grid to 8 columns/13 rows — the cells never become interactive anywhere in the builder.

**Distinction that matters:** The actual respondent-facing experience is unaffected. Opening the same question via the blue "Preview" button launches a separate modal where the identical grid **is** fully interactive — AC18/19/20 and the single/multi-select toggle all work correctly there (see table above). So this bug is scoped narrowly to the creator's in-builder-grid preview affordance that AC3 specifically calls out ("so the creator can immediately preview"), not to survey functionality or response capture.

**Repro:**
1. Survey Builder → Create Survey → Add questions → select Matrix
2. In the grid, click directly on any answer cell's radio (or checkbox, in Multiple choice mode)
3. Observe: no visual change, cell stays unselected

**Recommendation:** File in Jira against CL-17863 (or as a sub-note if the story hasn't shipped as "Done" yet, given it's still "To Do" in Jira despite being live in QA) before the story is closed out.

## UPDATE (2026-08-04) — Full regression pass over old Survey scenarios

Ran a full regression pass over previously-tested Survey areas (Builder templates, conditions, Responses, Overview, Comparison Reports, Journeys), against `qa.app.d.visipoint.dev` ("QA TESTING" entity — same environment as the CL-17863 testing above, different from the historical `visipoint.uk` baseline). Full detail in `Survey_Testing_Report_2026-08-04.md`.

**Headline finding:** the multi-condition builder ("+ Add Condition" producing multiple independent condition blocks per question) is present and functional here, **contradicting the 2026-07-31 finding on visipoint.uk that it had been removed**. Retested CL-17724's delete-corruption sub-bug with it: deleting a condition correctly preserved the promoted condition's own jump target (not corrupted) — the original bug does not reproduce. Recommend re-verifying on visipoint.uk directly before updating CL-17724's status, since the two environments may differ.

**Everything else, in brief:**
- Template creation/activation (CL-17671/17673/CL-17843): **still fixed**, published cleanly with a Multiple Choice question and no errors.
- CL-17685 (default filter silently excludes responses): **still open**, re-confirmed with fresh evidence (same contradiction: "No responses found" vs. sidebar showing 31 responses for the selected survey).
- Responses 20-of-102-survey "Select All" cap: **still open**.
- New minor finding: an extreme past date range (year 2020) triggers a hard "Could not load responses" error rather than a graceful empty state.
- Survey Overview (all 4 tabs): working correctly.
- CL-17845 (Comparison Report chip missing label): **not reproducible** — chips show labels correctly now, recommend closing.
- CL-17846 (chip tooltip missing specific phrase): tooltip copy was **fully rewritten** with different, clearer wording — recommend re-scoping rather than re-flagging verbatim.
- Journey Management: Survey column "-" still by design; CL-17683/17686 (null description) still fixed.

### Invalid/edge cases actually tested
- **I2 (9th column blocked):** confirmed — `disabled` button, click is a no-op
- **I3 (last row removal blocked):** confirmed — remove button removed from DOM at 1 row
- **I5 (empty question title blocks publish):** confirmed — clicking "Save and publish" with an empty title shows inline "Question title is required." and also outlines empty row-label fields in red, blocking publish. This means empty row labels are NOT allowed to silently persist to a published survey — a stricter behavior than the platform's usual silent-submit pattern, and worth knowing rather than assuming consistency.
- **I9 (long label text):** typed a 100+ character string into a column label — input field scrolls internally with no truncation/crash in the builder, and wraps cleanly across 2 lines in the live Preview with no layout breakage. Not a bug.
- Not tested this session (time-boxed): I1 (column-remove-at-2 direct attempt — inferred blocked from AC10's DOM-removal behavior, not separately forced), I4, I6, I7, I8, I10, and the full V22–V24 end-to-end publish → public-link respondent submission → response-data verification chain. Recommend a follow-up session for the public-link response-capture path specifically, since that's the feature's actual value proposition.
