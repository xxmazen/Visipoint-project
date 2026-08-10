# Survey Module — Regression Testing Report (2026-08-04)

**Environment:** `qa.app.d.visipoint.dev`, entity "QA TESTING" (login via `appqa.visipoint.me`) — the same QA environment used for the CL-17863 Matrix question testing on 2026-08-03. This is a different environment from the historical baseline (`visipoint.uk`, "UK (Testing)" tenant) that most of Survey's prior testing history was built against. Any discrepancy vs. prior findings below may reflect an environment/build difference rather than a genuine fix or regression — flagged individually where relevant.

**Scope:** Full regression pass over all previously-tested Survey scenarios (per user request), to confirm old scenarios still work correctly. Covers Survey Builder (templates, conditions), Survey Responses, Survey Overview, Survey Reports (Comparison), and Survey Management (Journeys).

**Test survey created:** "Brand Awareness Survey - Regression 2026-08-03" (from the BAS template), left Active in this tenant.

---

## Summary Table

| Area | Bug/Behavior | Prior Status | Result Today | Verdict |
|---|---|---|---|---|
| Templates | CL-17671/CL-17673 (0-question templates, activation HTTP 400) | Fixed (2026-07-27) | Template created with all 5 questions pre-filled correctly | **Still fixed** |
| Templates | CL-17843 (Multiple Choice question → HTTP 422 on save) | Fixed (2026-07-29) | Published successfully with a Multiple Choice question, no error | **Still fixed** |
| Conditions | Multi-condition builder UI existence | Reported REMOVED (2026-07-31) | "+ Add Condition" present and fully functional — added a 2nd condition successfully | **CONTRADICTS 2026-07-31 finding — multi-condition is back** |
| Conditions | CL-17724 sub-bug: delete corrupts promoted condition's jump target | N/A (feature was reported gone) | Deleted condition 1; promoted condition 2 correctly retained its OWN target, not the deleted one's | **Not reproducible — real positive result** |
| Responses | 20-of-N survey cap on "Select All" | Open since 2026-07-09 | 20 of 102 surveys selected via "Select All" | **Still open** |
| Responses | CL-17685 (default filter silently excludes out-of-range responses) | Confirmed open (2026-07-31) | "No responses found" shown with no explanation while sidebar shows 31 responses for the same survey | **Still open** |
| Responses | Extreme past date range (e.g. year 2020) | Not previously tested | "Could not load responses. Please try again." hard error, not a graceful empty state | **New minor finding** |
| Overview | Overview/Metrics/Performance/Averages tabs | N/A | All 4 tabs load correctly with real data, no errors | **Working** |
| Comparison Report | CL-17845 (Unmatched Questions chip shows only ⓘ icon, no label) | Open, never retested (2026-07-06) | All chips show visible label (Q2, Q7, Language, etc.) + icon | **Not reproducible — likely fixed** |
| Comparison Report | CL-17846 (tooltip missing "code represents a" phrase) | Open, never retested (2026-07-06) | Tooltip copy fully rewritten: *"Q2" is a question that has no matching question in the other survey. It exists — it just has no pair to compare with.* — conveys the same meaning via different, clearer wording | **Not reproducible in original form — copy was redesigned, recommend re-scoping ticket rather than re-flagging verbatim** |
| Journeys | Survey column shows "-" | By design (2026-07-01) | Still shows "-" for all rows | **Confirmed still by design** |
| Journeys | CL-17683/17686 (literal "null" in description field) | Fixed (2026-07-01) | Description field shows real text ("feedback"), not "null" | **Still fixed** |

---

## Detail: Multi-Condition Builder Restoration (headline finding)

The 2026-07-31 report on `visipoint.uk` concluded the entire multi-condition builder UI (numbered conditions, "Add Condition" button, per-condition jump targets) had been removed, replaced by a single OR-able condition block per question. That finding drove the conclusion that CL-17724's two sub-bugs (delete-corrupts-promoted-condition, reorder-doesn't-auto-remove-broken-condition) were "no longer reproducible — feature redesigned, not fixed."

Today, in `qa.app.d.visipoint.dev`, the BAS template's Q2 shipped with 1 pre-existing (empty) condition, and clicking **"+ Add Condition"** successfully added a second, fully independent condition block — numbered 1 and 2, each with its own "If the answer is" / "Jump to" fields, with the expected mutual-exclusivity rule enforced (an answer already assigned to condition 1 shows greyed-out/disabled in condition 2's picker).

**Corruption bug retest:** configured Condition 1 = "Very Unlikely → Jump to Q5", Condition 2 = "Very Likely → Jump to Thank you". Deleted Condition 1 via its trash icon (a confirmation dialog appeared — "Delete condition... Are you sure?"). After deletion, the promoted condition (now #1) correctly showed **"Very Likely → Thank you"** — its own original values, not corrupted to inherit the deleted condition's "Q5" target. The original CL-17724 corruption bug did not reproduce.

**Interpretation — needs team input, not a closed case:** This could mean (a) the multi-condition feature was reintroduced/fixed between 2026-07-31 and now, or (b) this QA environment is simply running a build that never lost it, independent of what's live on `visipoint.uk`. Recommend re-verifying directly on `visipoint.uk` before treating CL-17724 as conclusively resolved — the two environments may not be in sync.

---

## Detail: CL-17685 Retest

Selected "CL-17724 Conditions QA Test" (31 responses per the sidebar). Set the Period filter to 01/01/2026–01/31/2026 (a range that excludes this survey's actual response dates, which cluster in late July/August 2026). Result: main panel showed "No responses found for the selected surveys and period." with a Refresh button — no explanation that responses exist outside the selected range — while the sidebar simultaneously continued showing "CL-17724 Conditions QA Test — 31 responses" right next to the empty-state panel. Identical contradiction to the 2026-07-31 finding. ~49 days open, no progress.

---

## New Finding: Invalid Date Range → Hard Error

Setting the Period filter to a very wide/early range (start date 07/06/2020, end 08/04/2026) produced **"Could not load responses. Please try again."** with a Retry button — a genuine load failure, not the graceful "no responses" empty state. Retry did not resolve it; narrowing the start date to 01/01/2026 loaded correctly (81 responses across 20 surveys). Root cause not investigated (could be an API range-validation rejection, or simply no data existing that far back combined with a poor error path). Minor — not a normal user workflow, but worth a note since the failure mode differs from the "graceful empty state" pattern used elsewhere in Survey Responses.

---

## Recommendations

1. **Re-verify the multi-condition builder state on `visipoint.uk` directly** before updating CL-17724's status — today's result was on a different environment (`qa.app.d.visipoint.dev`) than the 2026-07-31 finding.
2. **CL-17845 recommend closing** — chips now show labels correctly, full coverage would help but the sample here (11 unmatched question chips) all passed.
3. **CL-17846 recommend re-scoping or closing** — the tooltip copy was substantively rewritten; re-checking against the *current* intended copy (not the original AC's specific phrase) would clarify whether this should stay open.
4. **CL-17685 and the 20-survey cap remain the two most actionable open Survey bugs** — both re-confirmed with fresh, concrete repro evidence today.
