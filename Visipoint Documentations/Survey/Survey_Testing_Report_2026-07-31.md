# Survey Module — Smoke Test Report — 2026-07-31

**Tester:** Claude (automated browser testing via Chrome MCP)
**Scope:** Full smoke pass, prioritizing retest of open items from prior sessions: CL-17724 condition-logic bugs (High + Medium severity, unretested since 2026-07-16), Survey Responses sidebar 20-survey cap.

Read `Survey_Testing_Report.md` (master experience file) and the Jira synthesis Section 4 "Survey Module — Full Bug History" table before this session.

---

## Summary of Findings

| # | Finding | Severity | Status |
|---|---------|----------|--------|
| 1 | The Conditions feature has been fundamentally redesigned since 2026-07-16 — the multi-condition builder (numbered conditions, "Add Condition" button, per-condition jump targets) no longer exists anywhere in the Survey Builder UI | — | **Major observation, not a bug** — needs product-team confirmation this was intentional |
| 2 | CL-17724's two open bugs (condition-delete corrupting a promoted condition's jump target; condition-reorder not auto-removing a broken condition) | — | **No longer applicable/reproducible** — both bugs were about interactions between *multiple* conditions on one question, and that capability doesn't exist in the current UI |
| 3 | Survey Responses page's survey-selector sidebar still hard-caps at 20 of 42 surveys via "Select All," no scroll-load-more or search box | — | **Still present, re-confirmed** — unchanged since first found 2026-07-09 |

---

## Detail — Conditions feature redesign (the headline finding)

**What was tested:** Built a fresh test survey ("CL-17724 Retest 2026-07-31") replicating the original CL-17724 test setup — a single-select Q1 ("Are you satisfied?" with Yes/No/Maybe/Not sure) plus 3 follow-up questions (Follow-up A, B, C) — specifically to retest the two open condition bugs.

**What was found:** Q1's "Question condition:" section only ever shows **one** condition block: an "If the answer is" multi-select (which does support OR-ing multiple answers together into that single condition, e.g. selecting both "No" and "Yes") and one "Jump to" target. There is no "Add Condition" button, no numbered condition list, and no way to create a second, independently-targeted condition on the same question.

Confirmed via full DOM/shadow-DOM inspection (`Array.from(shadow.querySelectorAll('button'))`) — the button list for the question editor is: `Preview, Selection Mode, + Add Button, Save and publish, Save as a draft, Add Question` — no "Add Condition" anywhere.

This is a **different feature** from what CL-17724's 32 test cases and the two still-open bug reports were built against (which explicitly described "badge=1 default / sequential increment," drag-and-drop reordering of multiple conditions, and an "Add Condition button disabled when all answers are assigned"). None of that UI exists anymore.

**Consequence for the two open bugs:**
- *"Deleting a condition corrupts the promoted condition's jump target"* — requires 2+ conditions to exist so one can be "promoted" after another is deleted. Not reproducible; the scenario doesn't exist in the current UI.
- *"Condition not auto-removed when reordering breaks the forward-only constraint"* — requires reordering between multiple conditions. Same issue.

**What still works correctly:** the single condition that does exist was fully tested end-to-end — set "If answer is No → Jump to Follow-up C," published, and submitted a real response via the public link choosing "No." Routing worked exactly as configured: jumped straight to Follow-up C, correctly skipping Follow-up A and B.

**Recommendation:** This isn't something I can classify as "fixed" or "still broken" — the underlying feature was replaced, not repaired. Recommend flagging to the product/dev team to confirm whether the multi-condition builder was intentionally removed/simplified (in which case CL-17724's two bug tickets should be closed as no-longer-applicable) or whether this is itself a regression that removed a shipped feature (in which case it's a much bigger issue than the two original bugs). Do not close the tickets without that confirmation — this synthesis file will record it as "no longer applicable, needs product confirmation," not "fixed."

---

## Detail — Survey Responses sidebar 20-survey cap (re-confirmed)

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Survey Responses page, click "Select All" in the Surveys sidebar (42 total surveys exist) | All 42 surveys selected, or at minimum a way to reach the rest via scroll/search | Only **20 of 42** get selected — "20 selected" shown, no scroll-load-more, no search box in the sidebar |

Identical to the original 2026-07-09 finding — no change. A survey outside the reachable first 20 (alphabetically or by whatever the list's sort order is) still cannot have its responses viewed via this page. Not yet filed as a formal Jira ticket — recommend filing against epic CL-16841.

---

## Cleanup

Test survey "CL-17724 Retest 2026-07-31" (and its one test response) was deleted via the UI's own Delete flow. Survey count confirmed restored to baseline (42).
