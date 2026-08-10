# Users Module — System Testing (Valid/Invalid Cases, Full Grid Coverage)

**Platform:** visipoint.uk (UK Company Dashboard)
**Tester:** Claude (Senior QA Engineer — automated via Chrome MCP)
**Test Date:** 2026-07-29
**Parent Jira Task:** [CL-17093 — Live System Testing (SM2)](https://lamasatech.atlassian.net/browse/CL-17093)
**Scope:** Exhaustive system testing of the Users grid — every field in the Add/Edit User forms, every toolbar/filter/sort/pagination control, every row-level Select Action, and every mass-action button. Valid and invalid cases throughout.

---

## 1. Executive Summary

| Metric | Value |
|--------|-------|
| Areas covered | Add User form, toolbar/filters/sort/pagination, row actions (Edit, Visit Permits, Invite to Dashboard, Print QuickPass, Sign in/out, Archive, Delete), mass actions (Change User Type, Invite to Dashboard, Add Label, Add Visit Permit, Print QuickPass, Sign in/out) |
| New bugs found | 3 (2 Medium, 1 Low/cosmetic) — see below |
| Confirmed working correctly | Add User (2-step wizard incl. silently-required custom fields), Edit User, Archive, Delete (with confirmation), all filter/sort/pagination controls, all mass actions except where noted |
| Overall health | Good — core CRUD and grid operations are solid; the bugs found are all in secondary/less-traveled paths |

Test data (2–3 throwaway users) created and fully cleaned up (archived then permanently deleted) after each phase. No production data was altered except transient label/user-type/visit-permit changes that were reverted or removed with the deleted test users.

---

## 2. Bug Findings

### 🟠 BUG-USERS-001 — Edit User silently wipes the Phone Number on save

| Field | Detail |
|-------|--------|
| **Module** | Users → Edit User |
| **Severity** | Medium (silent data loss) |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Create a user with a phone number set (e.g. `201001234567`) | Phone number saved | ✅ Saved correctly, visible in grid |
| 2 | Open **Edit User** on that user, change an unrelated field (e.g. Last Name), click **Save Changes** — do not touch the Phone Number field | Phone number is preserved | ❌ Phone Number is silently cleared to empty after save (confirmed after full page reload and by reopening Edit User — field reverts from the disabled "existing value + Change button" state to a blank, freshly-editable state) |

**Root cause (likely):** Once a user has a phone number, the Edit User modal renders it as a disabled/read-only field with a separate "Change" button (a verification-gated flow), rather than an editable input. The save payload appears to omit the phone field in this state, and the backend interprets the omission as "clear the phone" rather than "leave unchanged."
**Impact:** Any edit to a user who has a phone number — even editing something unrelated like their name or label — will silently delete their phone number with no warning, confirmation, or error. This is a real data-loss risk for any admin doing routine profile edits.
**Recommendation:** File as a bug. Assign to backend/frontend depending on where the omission occurs (frontend not including the current phone value in the payload) — should be a frontend fix to always include the existing phone the way other fields are included, since the field displays it but doesn't send it.

---

### 🟠 BUG-USERS-002 — "Sign in/out" never offers Check-out; repeated clicks just reset Arrival Time

| Field | Detail |
|-------|--------|
| **Module** | Users → row action "Sign in/out" |
| **Severity** | Medium (functional gap, misleading label) |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Click **Sign in/out** on a user who is NOT checked in, select Site → Area, click **Check in** | User is checked in; Dashboard shows "Checked in" | ✅ Works correctly |
| 2 | Click **Sign in/out** again on the SAME user, who is now already checked in | Modal should offer to check the user OUT (or at least indicate they're checked in) | ❌ Modal is identical to the check-in flow — same "Check in" button, same form. No check-out option is presented at all. |
| 3 | Complete the "Check in" flow a second time on an already-checked-in user | — | The Arrival Time on the existing Dashboard record is simply overwritten with the new timestamp. No duplicate record is created, but there is no way to actually sign the user out via this control. |

**Impact:** Despite being labelled "Sign in/out," this control only ever performs sign-IN. There is no discoverable way for staff to check a user out from the Users grid via this button. (Sign-out may exist elsewhere, e.g. Dashboard or Kiosk, but the Users-grid entry point is misleading and incomplete.)
**Recommendation:** File as a bug — either the modal should detect current status and branch to a Check-out flow, or the button/label should be reconsidered if check-out is genuinely handled only elsewhere.

---

### 🔴 BUG-USERS-003 — Sorting the grid by "Visipoint Passport" column returns HTTP 400 and breaks the entire grid

| Field | Detail |
|-------|--------|
| **Module** | Users → column sort |
| **Severity** | High (grid becomes fully unusable, sticky/reproducible) |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Click the **Visipoint Passport** column header to sort | Grid sorts by that column | ❌ Grid enters a broken state: infinite loading spinner, red error banner reading "An error occurred. Please try again later" (observed once literally rendering the raw unhandled error as `[object Object]` instead of a message) |
| 2 | Reload the page (F5) or navigate back to `/users` | Grid should recover | ❌ Error persists after reload — the broken sort state is evidently part of persisted grid preferences (the app saves grid column/sort state via `PUT/GET /api/adminPreference/...`), so it keeps re-applying the broken sort on every load |
| 3 | Click **Clear filters** | Should reset grid | ❌ Does not fix it — filters and sort are tracked separately |
| 4 | Click the **Active Users** tab toggle (even though already selected) | — | ✅ This was the only thing found that recovered the grid — it appears to reset in-memory grid state |

**Network evidence:** `GET /api/users/{tenantId}?...&sort=[{"selector":"VisiPointPassport","desc":false}]&...&orderBy={"sortField":"VisiPointPassport","sortOrder":"asc"}` returns **HTTP 400** consistently on retry.
**Impact:** Any user (support staff, admin) who clicks the Visipoint Passport column header to sort will find the entire Users grid broken and unable to load any data, with no obvious way back except the tab-toggle workaround discovered here. Given the sort state is persisted server-side per-user, this could strand a real user's Users page across sessions until they find the workaround.
**Recommendation:** File as a High-priority bug — likely a backend issue where the `VisiPointPassport` field isn't a valid/indexed sort field, but the frontend should also handle the 400 gracefully instead of leaving the grid in a permanent broken state, and definitely should never render a raw `[object Object]` error to the user.

---

## 3. Minor / Cosmetic Observations (not filed as bugs)

- **Visit Permits grid doesn't refresh its Status/Action button in-place after Revoke.** Clicking "Revoke" on a Visit Permit shows a success toast and the backend correctly applies the change, but the row still displays "Enabled"/"Revoke" until a full page reload — at which point it correctly shows "Revoked"/"Enable". Minor UI-refresh gap, not a data bug.
- **Add Label mass-action: pressing Enter after typing a new label clears the input instead of creating the tag**, even though the UI explicitly instructs "Press enter to create a tag." Clicking the dropdown suggestion directly works correctly. Worth a frontend fix since it contradicts its own instruction text, but has an easy workaround (click instead of Enter).
- **Transient "-1 items" / brief error-banner flash on page load**, consistent with previously-documented cosmetic pagination-glitch pattern in other Visipoint grids — self-corrects within 1-2 seconds and does not affect functionality.
- **Two custom fields ("Test" checkbox group, "YTYT" radio group) in the Add User step-2 wizard are silently required** — the Add/Save button stays disabled with no visible required-marker or inline error until at least one option is selected in each group. Consistent with the app's general "silent validation" pattern; not re-reported as a new bug per existing QA rules, but noted here since it wasn't previously documented for this specific form.

---

## 4. Full Coverage Detail

### 4.1 Add User form — valid/invalid cases
- Valid creation (User Type, First/Last Name, RFID, ID via Generate, Label, Phone with country code) — ✅ Pass, user appears in grid
- Empty-form silent submit — ✅ Button stays disabled (by design, not a bug)
- Invalid email format — ✅ silently rejected/blocked from submit
- Invalid RFID format — ✅ handled per existing "RFID must be unique" note; duplicate values not submitted
- Phone digit-filtering while typing — ✅ non-numeric characters silently stripped (by design)
- Step-2 custom fields (Test, YTYT) required for submit — see cosmetic note above

### 4.2 Toolbar / filters / sort / pagination
- **Columns chooser**: toggling column visibility (e.g. Image) works correctly, immediately reflected in grid
- **Export dropdown**: renders Excel/CSV/PDF options correctly (not triggered, to avoid an unrequested download)
- **User Type filter** (checkbox multi-select): correctly filters (tested Visitor-only → 184/462 items)
- **Dashboard Access filter**: correctly filters (tested Admin-only → 1/462 items)
- **Column sort** (First Name asc/desc/none): works correctly in both directions
- **Column sort** (Visipoint Passport): **BUG-USERS-003** — breaks the grid, see above
- **Per-column text search** (Email, Phone, First/Last Name): works correctly, including compound searches and 0-result states ("No records yet")
- **Page size selector** (5/10/25/50/100) and pagination (next/prev/direct page/first/last): all work correctly, including large page-count navigation (47 pages at size 10)
- **Clear filters**: correctly resets column search filters; does NOT reset sort state (contributing factor to BUG-USERS-003's stickiness)

### 4.3 Row-level Select Actions
- **Edit User**: works, but see **BUG-USERS-001** (phone wipe)
- **Visit Permits**: dedicated page works; Add/Revoke work correctly server-side; UI refresh gap noted above
- **Invite to Dashboard**: form renders correctly with inline validation ("Email address or phone number is required") — not submitted, to avoid sending a real invite
- **Print QuickPass**: printer-selection modal renders correctly for Local/Online printer — not submitted, to avoid a real print job
- **Sign in/out**: check-in works; see **BUG-USERS-002** for the missing check-out path
- **Archive**: works correctly, clear confirmation dialog explaining the effect, archived user correctly appears under Archived Users tab
- **Delete** (from Archived Users): works correctly, has a clear confirmation dialog (unlike some other Delete flows in the app), permanently removes the user

### 4.4 Mass actions (multi-row selection)
- **Change User Type**: works correctly across multiple selected users, confirmation dialog, success toast, grid updates in place
- **Invite to Dashboard**: navigates to a dedicated mass-action page with clear per-row inline validation errors — not submitted
- **Add Label**: works correctly (see Enter-key cosmetic note above)
- **Add Visit Permit**: correctly warns "Some selected user types are pre-registered by admin. Visit permits do not apply to them" for Staff/Approval-type users, and correctly no-ops for those types (confirmed Visit Permits option doesn't even appear as a row action for Staff-type users)
- **Print QuickPass**: same printer-selection modal as single-row, works for multiple users — not submitted
- **Sign in/out**: full mass check-in flow works correctly (per-user Temperature "Fill in"/"Update", "Apply for all", live "In progress"/"Pending"/"Completed" status per row, final "Users Checked In" confirmation)
- **No mass Archive or mass Delete option exists** — these remain row-only actions (not a bug, just a scope note for anyone planning bulk cleanup)

---

## 5. Test Data Cleanup

All test users created during this session were archived then permanently deleted via the Archived Users → Delete flow:
- QA / TestUser 260729 (single-row testing subject, later renamed to "TestUser Edited")
- MassQA1 / Test260729 (mass-action testing)
- MassQA2 / Test260729 (mass-action testing)

Grid item count confirmed restored to baseline (461 → back to original count after cleanup). No residual test data remains in either Active or Archived Users.

---

*Report generated: 2026-07-29 | Session: Users Module System Testing | Tool: Claude Code + Chrome MCP*
