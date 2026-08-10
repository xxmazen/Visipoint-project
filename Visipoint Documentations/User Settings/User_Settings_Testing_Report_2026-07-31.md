# User Settings — Full Smoke Test Report — 2026-07-31

**Module:** User Settings (6 sub-sections: User Types, Privacy Manager, Duplication Control Board, Attendance Modes, Custom Fields, Visit Permits)
**Tester:** Claude (automated browser testing via Chrome MCP)
**Scope:** Full smoke testing of all buttons/fields, valid and invalid scenarios, senior QC pass. Retested 2 previously-open bugs (BUG-002, BUG-003) and did fresh valid/invalid coverage on User Types, Attendance Modes, Custom Fields, Visit Permits, and Privacy Manager's password-gated Edit modal.

Read `User_Settings_Test_Experience.md` (last full pass 2026-07-15) and `User_Settings_Bug_Report.md` before this session.

---

## Summary of Findings

| # | Finding | Severity | Status |
|---|---------|----------|--------|
| 1 | Custom Fields: a newly-created section's field(s) never render as child rows in the list grid, even though the field data is genuinely saved correctly (confirmed via Edit) | Medium | New |
| 2 | Attendance Modes → Edit Mode: sensitive full JSON config exposed in the URL query string (BUG-002) | Medium | Still present, re-confirmed |
| 3 | User Types → "Visit Permits" action in row menu | — | **Resolved — not confusing.** Clicking it navigates to a clearly-labeled "{Type}'s Visit Permits" filtered sub-view with its own explanatory note. Original ambiguity (BUG-003) is resolved by direct observation. |
| 4 | Visit Permits → Delete confirmation dialog | — | **Correction — a confirmation dialog DOES exist now** ("Are you sure you want to 'Delete' this Visit Permit? By deleting this visit permit, it will be permanently removed from all linked user types and associated users." with Cancel/Delete). This contradicts the 2026-07-01 "confirmed intentional, no dialog" conclusion (BUG-004) — looks fixed since then. |

---

### BUG-005 — Custom Fields: new section's fields don't render in the list grid (Medium, new)

| Field | Detail |
|-------|--------|
| **Module** | User Settings → Custom Fields |
| **URL** | `https://visipoint.uk/custom-fields` |
| **Severity** | Medium |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Add new section ("QA Test Section", bound to User Profile, User Type: Visitor) with one field ("QA Field", type Date, Show on: Sign in) | Section created with the field saved | ✅ "Sections and custom fields deleted successfully" toast on later cleanup confirms save worked; Edit Section page correctly shows the field |
| 2 | View the Custom Fields list grid | New section shows as an expandable row with "QA Field" listed as a child row underneath, same as "Main Section" shows "Test" and "YTYT" | ❌ "QA Test Section" appears in the grid but with **no child field row** — looks like an empty section |
| 3 | Reload the page, click the expand/collapse arrow on the section row | Field row should appear | ❌ Still no child row, confirmed reproducible across reload and toggle |
| 4 | Open the section via Edit (pencil icon) | Field data should be present | ✅ "QA Field" (Date type, correct toggles) is genuinely there — confirms this is a display-only bug in the list grid, not data loss |

**Recommendation:** Fix the list grid's tree-expansion query/render for newly-created sections so their fields show as child rows, matching the existing "Main Section" behavior.

---

### BUG-002 retest — Attendance Modes URL data exposure (Medium, still present)

Created a fresh test Attendance Mode ("QA Test Mode") and opened Edit Mode. The URL still carries the full JSON-encoded configuration (setting IDs, labels, types, default values, timestamps, pivot data, UUIDs) as query parameters — several thousand characters long, identical pattern to the original 2026-06-20 finding, still present as of 2026-07-15 and now 2026-07-31. No change. See `User_Settings_Bug_Report.md` for full original description and recommendation (load by ID server-side instead of passing full config in the URL).

---

### BUG-003 resolved by direct testing — "Visit Permits" row action

Clicked "Select Actions" → "Visit Permits" on the "Approval" user type row. It navigates to a breadcrumbed sub-page: `User Types / Approval's Visit Permits`, showing an empty grid (no permits yet for that type) with a clear note: "Users under this user type are allowed to access the entity even if they hold two overlapping permits." This is self-explanatory once actually used — the original concern (no tooltip, unclear purpose) doesn't hold up under direct testing. Reclassifying as resolved/not-actionable, not a UX bug.

**New observation (not a bug):** this filtered view's grid has an extra "Status" column that the main `/visit-permits` grid doesn't have. Not evaluated further — noted for awareness only.

---

### BUG-004 correction — Visit Permits Delete now shows a confirmation dialog

Created a test permit ("QA Test Permit"), clicked Delete. A confirmation modal appeared: "Delete Visit Permit — Are you sure you want to 'Delete' this Visit Permit? By deleting this visit permit, it will be permanently removed from all linked user types and associated users." with Cancel/Delete buttons — consistent with User Types' delete flow. This **contradicts** the 2026-07-01 note that concluded "no confirmation dialog, confirmed intentional by the team." Either the team's earlier confirmation was inaccurate, or this was fixed since 2026-07-01. Correcting the record: a confirmation dialog exists and works correctly.

---

## Full Coverage Checklist (this session)

### User Types
- Add form: empty-submit silently blocked (button stays enabled but does nothing — consistent with silent-submit pattern); over-20-character name correctly rejected with "The maximum length is 20 characters" inline error (validated on submit, not capped while typing — matches the Edit modal's existing validation, just previously undocumented for Add); valid submission ("QA Test Type") succeeds with toast; custom (non-default) types correctly show a Delete option, confirmation modal matches pattern, delete succeeds.
- "Visit Permits" row action: see BUG-003 above.

### Attendance Modes
- No existing records this session (data drift since 2026-07-15, not a bug) — created a fresh test mode to exercise the flow.
- Add form: empty-submit correctly blocked (button disabled); valid submission (name, schedule day, from/to time) succeeds with toast.
- Edit Mode: reproduces BUG-002 (URL data exposure), see above.
- Delete Mode: confirmation modal correct ("Are you sure you want to 'Delete' this attendance mode? By deleting this mode, all the related reasons will be deleted."), delete succeeds.

### Custom Fields
- Baseline (Main Section, Test, YTYT fields) unchanged from prior sessions.
- "Add" dropdown: both "Add to Main Section" and "Add new section" options confirmed present.
- Add new section: empty-submit silently blocked twice — first with a fully-empty form, then again after filling everything except "Show on" (confirms "Show on" is a required-but-unvalidated field, silent block, no inline error shown). Selecting "Show on" and resubmitting succeeds.
- Found BUG-005 (new section's field doesn't render in the grid) — see above.
- Cleanup: deleted the test section via its Delete icon, confirmation modal correct, restored to baseline.

### Visit Permits
- Add form: "Permit name is required" / "Start Date is required" validation re-confirmed working correctly.
- Valid submission succeeds with toast.
- Delete: see BUG-004 correction above.
- Cleanup: test permit deleted, restored to baseline (1 pre-existing permit, "BV").

### Privacy Manager
- Baseline layout re-confirmed (gear icon, Display size dropdown, per-row Data/Scans Retention with inline Delete Surplus Now, Delete Records Up To date picker + Delete Now, Available Actions dropdown).
- Available Actions → Edit: "Edit Data and Scans" modal confirmed correct (Data Retention, Scans Retention, Password fields).
- Tested an invalid password ("wrongpassword123") on Confirm — correctly rejected with a clear inline error "Current Password is Not Correct!", change blocked. Password-gate security control works as intended.
- Did not test with the real password (would apply an actual data-retention change) or any of the other destructive controls (Delete Surplus Now, Delete Now, Bulk Delete All Data & Scans, the global Privacy Manager ON/OFF toggle) — consistent with established caution around destructive actions without explicit sign-off.

### Duplication Control Board
- Not retested this session (confirmed working 2026-07-29, no changes expected on a read-only monitoring board with no duplicate data in this environment).

## Cleanup

All test records created this session (QA Test Type, QA Test Mode, QA Test Section + its field, QA Test Permit) were deleted via the UI's own delete flows. Verified each sub-section's grid returned to its documented baseline after cleanup.
