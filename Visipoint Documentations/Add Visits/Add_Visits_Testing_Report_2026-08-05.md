# Add Visits — Full Testing Report (Pre-live)

**Date:** 2026-08-05
**Environment:** Pre-live (`appprelive.visipoint.me` → `prelive.app.d.visipoint.dev`) — first-ever testing session on this server
**Entity:** "Custom field project" (Site10 / Area "field")
**Scope:** Initial smoke test, then a full valid/invalid scenario matrix per user request, including a root-cause re-investigation of a previously-tracked bug

---

## Summary

Full Add Visit flow works end-to-end on pre-live across a comprehensive valid/invalid scenario matrix. **BUG-AV-001 (new-visitor First/Last Name "truncation"), tracked as an open Medium bug since 2026-07-31, is RETRACTED** — confirmed intentional live-mirror/timing behavior, not data loss. One unconfirmed reliability observation logged (slow dashboard SSO load, self-resolved, not yet reproduced a second time). One minor, config-dependent observation (Age field accepts negative values). No other bugs found.

## Part 1 — Initial smoke pass

| # | Step | Result |
|---|------|--------|
| 1 | Select Site "Site10" + Area "field" | ✅ Pre-registration link note rendered correctly |
| 2 | Search visitor with a name matching no existing user (`PreliveSmokeTestVisitor`) | New-visitor mini-form expanded; First Name showed "PreliveSmoke" (12 of 24 typed chars) — logged at the time as "BUG-AV-001 reproduced," later retracted (see Part 2) |
| 3 | Manually fill Last Name ("Tester"), set User Type "Visitor" | ✅ |
| 4 | Set Visit Date "Today", Visit Time 08:58 PM (real time was 6:57 PM) | ✅ Accepted as valid future time |
| 5 | Submit → confirmation modal | ✅ Correct, non-corrupted text; no stray second modal this run |
| 6 | Confirm | ✅ "Created successfully" toast, form reset cleanly |
| 7 | Re-open form, search "PreliveSmoke" again | ✅ Correctly found existing user "PreliveSmoke Tester - Visitor" |
| 8 | Submit a second visit for the same visitor, same date (different time) | ✅ Correctly blocked: "An expected visit already exists, try a different date." |

## Part 2 — BUG-AV-001 root-cause re-investigation and retraction

The user stated this was "logic, not a bug" and flagged that a scenario had been missed. Re-tested with a methodology change: typed controlled multi-word names, then **waited 3-4 seconds and blurred the field before reading its value** (instead of reading immediately after typing, which every prior session had done).

| Test input | First Name result | Last Name result |
|---|---|---|
| `"Alexander Bright"` | "Alexander" (full 9 chars) | "Bri" (partial, 3 of 6 chars) |
| `"Alexandrina Brightwood"` | "Alexandrina" (full 11 chars) | "" (empty) |
| `"PreliveSmokeTestVisitor"` (single word) | "PreliveSmoke" (12 of 24 chars) | n/a (no space) |

**Conclusion:** First/Last Name mirror the search box's typed text live, and freeze at whatever text exists the instant an async "does this match an existing user" check fires. This is a **timing snapshot**, not a fixed-length truncation — it explains why prior sessions saw different "truncation lengths" (8, then "Smi"/3, then 12 chars): each session's automated typing paced differently relative to the debounce window. Last Name genuinely does populate (contradicting the original "never populates" claim). Fields stay fully editable before submit — nothing is lost from the user's perspective since they review/edit before clicking "Add Visit." **Retracted, not a bug**, per direct user confirmation plus this root-cause evidence.

## Part 3 — Full valid/invalid scenario matrix

| Scenario | Result |
|---|---|
| Visit Time before real current time (Today) | ✅ Blocked — inline "Visit time can't be before now.", no confirmation modal, no submission |
| Custom date in the past | ✅ Blocked at the calendar-widget level — past dates greyed out and unclickable, no way to select one |
| Empty form submit | ✅ Silent no-op — matches platform-wide silent-submit pattern |
| Duplicate visit (same visitor, same date) | ✅ Blocked — "An expected visit already exists, try a different date." |
| Existing-visitor search | ✅ Correctly finds and reuses existing record, no auto-fill/truncation behavior (only applies to no-match path) |
| New-visitor full-field valid submission (email, phone, custom fields, Tomorrow date) | ✅ Submitted successfully, confirmation modal showed correct full data |
| Invalid email format | ✅ Real-time inline error "Please enter valid email," clears once corrected |
| Invalid phone (letters/symbols) | ✅ Rejected entirely at input level (digits-only from first keystroke) |
| XSS payload in short-text custom field (`Test1`, maxLength=20) | ✅ Truncated at native HTML maxLength, no injection risk |
| SQLi payload in textarea custom field (`Name`, maxLength=255) | ✅ Accepted in full, rendered safely as plain text |
| Negative number in "Age" custom field (number-type) | ⚠️ Accepted with no min/range validation — minor, likely a per-field config gap, not filed |
| Custom date valid future submission (with Age, existing visitor) | ✅ Submitted successfully, confirmation modal correct |

## Other observations

- **Pre-live dashboard slow/hung SSO load (severity TBD, not yet confirmed reproducible)** — before reaching Add Visits, the initial Passport → Dashboard SSO handoff for this entity left the page blank for 2.5+ minutes (`document.readyState` stuck at `"loading"`, zero network activity, app never mounted) before self-resolving with no retry. Likely cause: synchronous/blocking `unpkg.com/leaflet` script tags ahead of the app bundle in page HTML. Logged for re-verification; not filed as a confirmed bug since it hasn't reproduced a second time.
- Stray-second-modal cosmetic bug (previously documented) did **not** reproduce this run — consistent with it being intermittent.
- No "(Europe/Moscow)" timezone label mismatch on this entity — correctly showed "(Africa/Cairo)".
- "Custom date" radio depends on Site+Area being selected first, same as Visitor Details — not a bug, just a dependency to remember when the form resets after a submission.
- Custom fields (Test1, Test2, Age, Age2, Name, smiley rating) all rendered and behaved correctly, consistent with the entity being purpose-built for custom field testing.

## Tracking updates made

- `Add Visits\Add_Visits_Test_Experience.md` — original 2026-07-31 BUG-AV-001 write-up marked retracted with corrected root-cause explanation; earlier same-day 2026-08-05 section corrected; full valid/invalid matrix appended.
- `visipoint-module-testing` skill — Add Visits section corrected (BUG-AV-001 removed, live-mirror mechanism documented), new notes added for past-date/past-time validation, custom-field XSS/SQLi/number behavior, and the Custom-date/Site+Area dependency.
- `Jira\Jira_Visipoint_Knowledge_Synthesis.md` — Section 4 (bug retracted, moved to confirmed-fixed-equivalent list), Section 6 (new business-logic rule #15), Section 7 (Add Visits row updated).
- Auto-memory `visipoint_known_bugs.md` and `visipoint_qa_feedback.md` updated with the retraction and the "missed scenario → re-investigate, don't just retract" lesson.
