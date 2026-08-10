# User Settings – Test Experience Report

**Date:** 2026-06-20 (last updated 2026-07-31)
**Tester:** Mazen Mohamed (m.mohamed@lamasatech.com)
**Company Under Test:** UK (Testing) at https://visipoint.uk
**Module:** User Settings (under Company Dashboard left navigation)

---

## Module Overview

User Settings is a left-nav section in the Visipoint Company Dashboard containing **6 sub-sections**:

1. User Types
2. Privacy Manager
3. Duplication Control Board
4. Attendance Modes
5. Custom Fields
6. Visit Permits

All sub-sections are accessible after expanding the "User Settings" nav item.

---

## 1. User Types (`/user-types`)

### Page Layout
- Title "User Types" with an **Add** button and a **?** (help) icon.
- Grid with columns: **Name**, **Registration Method** (filterable), **Grace Period**, **Actions**.
- **Columns** and **Export** toolbar buttons above the grid.
- Name column has a free-text **Search** field; Grace Period column has a **Search** field.

### Existing Records (pre-test)
| Name     | Registration Method                      | Grace Period |
|----------|------------------------------------------|--------------|
| Approval | Pre-registration with approval required  | 30 min       |
| Staff    | Pre-registered by admin                  | –            |
| Visitor  | Registration Allowed                     | –            |
| Walk-in  | Registration Not Required                | –            |

### Add Flow
- Clicking **Add** navigates to `/add-user-type`, a full page (not a modal).
- Left panel shows **Current User Types** (Staff, Visitor, Approval – scroll to see Walk-in) with a **New User Type** button in the top right corner.
- Right panel shows a **Create New User Type** form:
  - **Name** text field
  - **Registration Method** dropdown (5 options):
    1. Pre-registered only
    2. Pre-registration with approval required
    3. Registration allowed
    4. Pre-registered by admin
    5. Registration not required
- **Dynamic behavior**: Selecting "Registration allowed" reveals an extra section:
  - "Data Mandatory To Be Filled By User: ☐ Profile Photo"
- **Submit** and **Cancel** buttons at the bottom.

### Validation (Add Form)
- ⚠️ **Submitting with both Name and Registration Method empty shows NO validation errors** (see Bug Report).
- When Registration Method is selected, the form correctly reveals/hides the optional Profile Photo checkbox.

### Edit Flow
- **Select Actions** → **Edit** opens an inline modal with pre-filled data:
  - Name (editable, max 20 characters – validated with "The maximum length is 20 characters" message)
  - Registration Method dropdown (pre-filled)
  - Profile Photo checkbox (if applicable)
  - **Update** and **Cancel** buttons
- Update works correctly; success is implied by return to list.

### Delete Flow
- **Select Actions** → **Delete** shows a confirmation dialog:
  - "Are you sure you want to 'Delete' the user type [Name]?"
  - **Cancel** (dark blue) and **Delete** (red outline) buttons
- On confirm: "User type deleted successfully." green toast appears; record removed from list.

### Select Actions Options (per row)
1. Edit
2. Visit Permits
3. Delete (red text)

---

## 2. Privacy Manager (`/privacy-manager`)

### Page Layout
- Title "Privacy Manager" + **Last Update** timestamp (e.g., 2026-06-19 03:00:10).
- **Privacy Manager** toggle button (top right) – enables/disables the feature globally.
- Blue info banner: "Ensure all scans and data are synced to the cloud..." (dismissible with ✕).
- **Gear icon** (column visibility) + **Display 10** dropdown (page size).
- Grid columns: **User Type**, **Data Retention** (days + DELETE SURPLUS NOW), **Scans Retention** (days + DELETE SURPLUS NOW), **Delete Records Up To** (date picker + DELETE NOW), **Actions**.
- Rows for each user type: Approval, Staff, Visitor, Walk-in.

### Privacy Manager Toggle Button
- Opens a modal with an **ON/OFF toggle** and a password field ("Enter Password To Confirm Changes!").
- Requires password to change the global feature state.
- Confirm / Cancel buttons.

### Gear Icon (View List Settings)
- Opens modal with column visibility toggles:
  - Select All, User Type, Data Retention, Scans Retention, Delete Records Up To (all ON by default).
- **Confirm** / **Cancel** buttons.

### Available Actions (per row)
1. **Edit** – Opens "Edit Data and Scans" modal:
   - Data Retention (numeric, days)
   - Scans Retention (numeric, days)
   - Password field (required to confirm changes)
   - **Confirm** / **Cancel** buttons
2. **Bulk Delete All Data & Scans** – Destructive bulk action.

### Inline Row Controls
- **DELETE SURPLUS NOW** (next to Data Retention and Scans Retention values) – immediate deletion trigger.
- **Select Date** calendar picker + **DELETE NOW** button – delete records up to a specific date.

---

## 3. Duplication Control Board (`/duplication-control-board`)

### Page Layout
- Title "Duplication Control Board" (no Add button – read-only board).
- Grid columns: **First Name**, **Last Name**, **User Type**, **ID**, **Email Address**, **RFID**, **Face ID**, **Reason**, **Actions**.
- Every column has an inline **Filter** text field (or dropdown for Reason).
- Currently shows **"No matching data.."** – no duplicate users flagged in this environment.

### Purpose
- Displays users that were flagged by the system as potential duplicates during registration or sync.
- Admin can review and take action via the Actions column.

### Filter Behavior
- All column filters are functional; typing in First Name filter to "Test" showed "No matching data.." (correct since no duplicates exist).

---

## 4. Attendance Modes (`/attendance-modes`)

### Page Layout
- Title "Attendance Modes" + **Add** button.
- **Columns** and **Export** toolbar.
- Tree-structured grid: parent attendance mode rows expand to show child **Sign in** / **Sign out** rows.
- Grid columns: **Name**, **Type**, **Integration Name**, **Working Days**, **From**, **To**, **Late Sign in**, **Early Sign out**, **Duration**, **Actions**.
- Search/filter fields per column.

### Existing Record
- Parent: "Attendance mode" – Integration, Wednesday, 01:00 PM → 10:00 PM
  - Child: "Sign in" – Sign in, Integration, Wednesday
  - Child: "Sign out" – Sign out, Integration, Wednesday

### Select Actions (Parent Row)
1. **Edit Mode** – navigates to `/add-Attendance-Mode?...` (full page)
2. **Add Reason** – (adds a reason code to the mode)

### Edit Mode Page
A comprehensive full-page form at `/add-Attendance-Mode`:
- **Mode Name** (text field)
- **Schedule Days** (multi-select tag input – e.g., Wednesday)
- ☐ Allow only to sign-in on the specified days
- **From** / **To** time pickers (HH:MM AM/PM)
- ☐ Prevent sign-in before the start time
- ☐ Prevent sign-in after the end time
- **Integration (Optional)** dropdown
- Late sign-in settings (minutes, conditional on checkbox)
- Early sign-out settings (minutes, conditional on checkbox)
- **On Time Attendance Code (Optional)** for sign-in (with note about default code)
- **Sign-out settings** section:
  - ☐ Allow early sign-out without a reason before end time by [N] Minutes
  - **On Time Attendance Code (Optional)** for sign-out
- **Edit** button at the bottom

> ⚠️ Note: URL parameters carry large JSON blobs of attendance code data; this is a potential data exposure concern if URLs are shared or logged.

### Business Logic — Scope of Application (confirmed by user 2026-08-10)
Attendance Modes only apply to a user type whose **Registration Method is "Pre-registered by admin"** (e.g. the default "Staff" user type). Any user type configured with that registration method is the one an attendance mode's sign-in/sign-out/late/early rules actually get applied to. User types with other registration methods (Registration allowed, Pre-registration with approval required, Pre-registered only, Registration not required) are out of scope for this feature. **When testing, verify against a "Pre-registered by admin" user type — seeing no effect on a Visitor/Walk-in/Approval-type user is expected, not a bug.**

### Business Logic — Time-Range Enforcement and Grace-Period Settings (confirmed by user 2026-08-10)
- A "Pre-registered by admin" user is expected to sign in **within the mode's configured From → To time range**, but this is gated by two independent checkboxes in the Edit Mode form (confirmed by user 2026-08-10):
  - **"Prevent sign-in before the start time"** — checked → sign-in before the From time is blocked with a validation message. Unchecked → sign-in before From is allowed.
  - **"Prevent sign-in after the end time"** — checked → sign-in after the To time has ended is blocked with a validation message. Unchecked → sign-in after To is allowed.
  - Test all 4 combinations (both checked / both unchecked / each checked alone) — the range restriction is opt-in per side, not always-on.
- **Late Sign-in grace window** — the Edit Mode form's sign-in section has an "Allow late sign-in without a reason after start time by [N] Minutes" checkbox. When checked (e.g. N = 5), the user can sign in **up to N minutes after the From time** without being required to give a reason. Past that N-minute window, a reason should be required (or the sign-in blocked, consistent with the validation-message rule above).
- **Early Sign-out grace window** — same logic in reverse, via "Allow early sign-out without a reason before end time by [N] Minutes" in the sign-out section. When checked (e.g. N = 5), the user can sign out **up to N minutes before the To time** without a reason. Earlier than that window should require a reason / be blocked.
- **Test boundary precisely**: exactly N minutes vs. N+1 minutes past/before the From/To time is where a real validation bug is most likely to show up — don't just test "well within" or "well outside" the grace window.

### Business Logic — Schedule Days + "Allow only to sign-in on the specified days" (confirmed by user 2026-08-10)
- **Schedule Days** (multi-select tag dropdown in Edit Mode) sets which days this attendance mode's rules apply to (e.g. only Wednesday).
- **"Allow only to sign-in on the specified days" checkbox:**
  - **Checked** → sign-in is restricted to the selected Schedule Days only; a sign-in attempt on any other day should be blocked with a validation message.
  - **Unchecked** → sign-in is allowed on any day, including days outside the selected Schedule Days — the day restriction is not enforced (time-range/late/early rules still apply on days that do match).
- Test both states explicitly: checkbox checked + attempt sign-in on a non-scheduled day (expect block), checkbox unchecked + same attempt (expect success).

### Business Logic — Area Login Mode Dependency (confirmed by user 2026-08-10)
Attendance Modes only apply to an **Area whose login mode is "Sign in/out."** An Area configured with "Check-in" login mode is entirely out of scope — none of the mode's rules (time range, late/early grace, schedule days) take effect there. This is a **precondition independent of the user-type scoping rule** above — both must hold (scoped "Pre-registered by admin" user type AND a Sign in/out-mode Area) for an Attendance Mode to actually apply. Check the target Area's login mode before concluding a configured mode "isn't working."

---

## 5. Custom Fields (`/custom-fields`)

### Page Layout
- Title "Custom fields" + **Add** (dropdown with two options) button.
- **Columns** and **Export** toolbar.
- Tree-structured grid: section rows expand to show field rows.
- Grid columns: **Name**, **Binded to**, **User Type**, **Field Type**, **Mandatory**, **Fillable by user**, **Printable on badge**, **Show on**, **Actions**.
- All columns have Search/filter fields.

### Add Button Dropdown
1. **Add to Main Section** – Opens the Edit Section page for the existing default section.
2. **Add new section** – Creates a new section.

### Edit Section Page (`/add-custom-field`)
- **Section Name** (editable text field)
- Note: "All custom fields below this section will be applied to all users types."
- **Section is binded to**: User Profile ● / Visit ○ (radio buttons)
- For each field in the section:
  - **Field Name** (text)
  - **Field Type** dropdown (e.g., Checkboxes, Radio buttons, Text, etc.)
  - **Options** fields (appear for Checkboxes/Radio types)
  - **Mandatory** toggle (ON/OFF)
  - **Fillable by user** toggle (ON/OFF)
  - **Show on** radio: Check in / Sign in / Sign out
  - ☐ This field should be filled during pre-registration
  - 🗑️ Delete button per field
- **Add Field** button (adds a new empty field block)
- **Edit** button to save the section

### Existing Section: Main Section
- Binded to: User Profile
- User Types: Staff – Visitor – Walk-in – Approval
- Fields:
  - "Test" – Checkboxes, Mandatory, Fillable by user, Sign in, Pre-registration required
  - "YTYT" – Radio buttons, Mandatory, Fillable by user, Sign in, Pre-registration required

---

## 6. Visit Permits (`/visit-permits`)

### Page Layout
- Title "Visit Permits" + **Add** button.
- **Columns** and **Export** toolbar.
- Grid columns: **Permit Name**, **Permit Description**, **Permit Validity**, **Actions**.
- Actions column uses **separate** Edit (dark blue) and Delete (red) buttons (not a dropdown like other sections).
- Pagination: 5, 10, 25, **50**, 100 per page; currently 1 item.

### Add Visit Permit Modal
Fields:
- **Permit name** * (required)
- **Permit description** (optional textarea)
- **Start Date** * (required, date picker)
- **End Date** (optional, date picker)
- **Start Time** (time picker)
- **End Time** (time picker)
- **Recurrence Settings (Optional)** accordion:
  - Days of month (calendar picker)
  - Days of week (calendar picker)
  - Months (calendar picker)
  - Years (calendar picker)

### Validation (Add Form)
- ✅ "Permit name is required" shown in red when empty.
- ✅ "Start Date is required" shown in red when empty.
- Fields highlighted with red border on validation error.

### Edit Visit Permit Modal
- Same structure as Add, but pre-fills all existing data.
- Button label changes to **Edit** (instead of "Add").

### Delete
- No dropdown – a direct red **Delete** button in the grid row.
- (No confirmation dialog observed – see Bug Report.)

---

## General UI Observations

| Feature | Behavior |
|---------|----------|
| Success toasts | Green toast at top-right ("User type created successfully.") |
| Error toasts | Not tested in all scenarios |
| Columns button | Opens column visibility panel (where present) |
| Export button | Provides data export (format not tested) |
| Pagination | Consistent: 5/10/25/50/100 per page, page number pills |
| Help icon (?) | Present on User Types Add button – unclear behavior (may open docs) |
| Responsive scroll | Horizontal scroll for wide grids (Attendance Modes, Custom Fields) |

---

## Logic Summary

- **User Types** define how users are registered and what grace period is allowed.
- **Privacy Manager** controls data/scan retention per user type with password-gated modifications.
- **Duplication Control Board** is a passive monitoring board – no data = no duplicates flagged.
- **Attendance Modes** configure sign-in/sign-out rules with time windows, late allowances, and integration codes.
- **Custom Fields** extend user profiles with additional data fields, supporting different field types and visibility rules.
- **Visit Permits** define time-bound access permits that can recur on defined schedules.

---

## UPDATE (2026-07-01) — Regression Retest

- **User Types → Add: empty-form silent submit reclassified as NOT A BUG.** Confirmed intentional application logic (part of Visipoint's platform-wide silent-submit pattern for Create/Add/Save forms) — was previously filed as BUG-001, now closed. See `project_form_validation_logic.md` in memory.
- **Visit Permits → Delete: no confirmation dialog reclassified as NOT A BUG.** Confirmed intentional application logic — was previously filed as BUG-004 ("needs verification"), now closed. Only 1 permit record exists in the test environment; the Delete button itself was still not clicked to avoid unrecoverable data loss.
- Attendance Modes, Duplication Control Board, Privacy Manager, and Custom Fields were not retested this pass.

Full regression details across all modules: `D:\Visipoint md files\Multi_Module_Testing_Report_2026-07-01.md`.

---

## Session Update — 2026-07-15 (Full Field/Button Walkthrough + API Performance, all 6 sub-sections)

**Full report:** `User_Settings_Testing_Report_2026-07-15.md`

- **No new bugs.** All findings this session are previously-undocumented UI/fields, not defects.
- **User Types:** default records (Approval/Staff/Visitor/Walk-in) have **no Delete option** in Select Actions — only Edit + Visit Permits. New field found in both Edit and Add (for "Pre-registration with approval required"): **"Auto-approve visit requests" toggle** with explanatory note.
- **Attendance Modes:** parent row now also has a **"Delete Mode"** option; child rows (Sign in/Sign out) have their own **"Edit Reason" / "Delete Reason"** actions. The **Add Reason page** (`/add-reason`) was fully undocumented before — now documented (Reason Name, Presented reason, Reason Type, Attendance Code + From/To). URL data-exposure concern (large JSON blob in query string) re-confirmed still present.
- **Custom Fields:** Field Type dropdown has **8 options** (Checkboxes, Radio buttons, Text, Number, Date, Long text, Rating scale, Toggle button) — previously only a few were named as examples. Toggle button type has a different (simpler) config panel than Checkboxes/Radio (no Mandatory toggle, no Options).
- **Duplication Control Board:** Reason dropdown filter values confirmed: Full Name, User ID, RFID, Face ID, Email.
- **Visit Permits:** Days of week recurrence field confirmed as a real dropdown (Monday–Sunday); all validation and Edit/Add modal behavior re-confirmed matching docs.
- **Privacy Manager:** all destructive controls (DELETE SURPLUS NOW, DELETE NOW, Bulk Delete, password-gated Edit/toggle) opened/inspected but never confirmed.
- **API baselines captured** (host `api.visipoint.uk`): `check_active_sessions` avg 380ms (n=4), `adminPreference` avg 309ms (n=5), `user-types` avg 395ms (n=3, called cross-page). New endpoint noted: `cron_jobs` (n=1) on Privacy Manager, plausibly tied to scheduled retention-deletion jobs.

---

## Session Update — 2026-07-31 (Full Smoke Test, Valid/Invalid Coverage, Bug Retest)

**Full report:** `User_Settings_Testing_Report_2026-07-31.md`

- **NEW BUG (Medium): Custom Fields — a newly-created section's field(s) never show as child rows in the list grid**, even though the field data is genuinely saved (confirmed correct via Edit Section). Reproducible across page reload and expand/collapse toggle. Display-only bug, no data loss.
- **BUG-002 (Attendance Modes URL data exposure) — re-confirmed still present.** Same full JSON config blob in the URL on Edit Mode, no change since 2026-06-20/07-15.
- **BUG-003 (User Types → "Visit Permits" row action, unclear purpose) — resolved by direct testing, not a bug.** It navigates to a clearly breadcrumbed "{Type}'s Visit Permits" filtered view with its own explanatory note. Previously never actually confirmed/clicked through; now that it has been, the original ambiguity concern doesn't hold up.
- **BUG-004 (Visit Permits Delete, no confirmation dialog) — CORRECTION: a confirmation dialog DOES exist.** Tested by creating and deleting a real test permit — "Delete Visit Permit" modal appeared with Cancel/Delete and explanatory body text, consistent with User Types' delete flow. This contradicts the 2026-07-01 "confirmed intentional, no dialog" note — either that was inaccurate or this was fixed since. The confirmation dialog is present and working as of 2026-07-31.
- **User Types Add form — 20-character name limit re-confirmed, previously undocumented for Add (only known for Edit).** Validates on submit (not capped while typing), inline error "The maximum length is 20 characters" — matches the app's general validate-on-save pattern.
- **Privacy Manager password gate confirmed working correctly.** Tried an incorrect password on the Edit Data and Scans modal — clean inline rejection "Current Password is Not Correct!", change blocked. Did not test with the real password or any other destructive control (Delete Surplus Now, Delete Now, Bulk Delete, global toggle) — same caution as before.
- Attendance Modes and Visit Permits both had all their existing test data missing at session start (environment drift, not a bug) — fresh test records were created, exercised through full add/edit/delete cycles, and cleaned up.

---

## Session Update — 2026-08-06 (First pass on new **pre-live** server, all 6 sub-sections)

**Environment:** `prelive.app.d.visipoint.dev`, entity "Custom field project". No new bugs found. One previously-documented bug re-confirmed still present; one previously-documented bug investigated in depth and found NOT to reproduce (see correction below).

### User Types
Full CRUD cycle (Add → Edit → Delete) confirmed working end-to-end on a fresh test type. Confirmed matching prior docs: empty-submit silent no-op, 20-character name limit validates on submit (not while typing) with "The maximum length is 20 characters", "Registration allowed" correctly reveals the Profile Photo mandatory-data checkbox, default/seed types (e.g. "Approve01") have no Delete option (only Edit + Visit Permits) while user-created types get all three. "Approve01"'s Edit modal confirmed the "Auto-approve visit requests" toggle + explanatory note, matching docs exactly.

### Privacy Manager
Structure matches documented layout (toolbar note banner, gear icon column-visibility modal, per-row Available Actions with Edit / Bulk Delete All Data & Scans). Gear icon's "View List Settings" toggle confirmed working — turned off "Scans Retention" column, it disappeared from the grid; turned back on, it reappeared. **Did not enter any value into the password-confirmation field on the Edit Data and Scans modal** — per this session's standing policy of never typing into password/authentication-confirmation fields, even a deliberately-wrong test value, since the field's purpose is auth confirmation regardless of the value entered. Cancelled out of that modal without submitting.

### Duplication Control Board
Exact match to documented behavior — "No matching data.." with zero duplicates in this entity, all column filters accept text without breaking, Reason dropdown values confirmed identical (Full Name, User ID, RFID, Face ID, Email).

### Attendance Modes
Full CRUD cycle (Add → Edit-inspect → Delete) confirmed working on a fresh test record ("PreliveAttMode", Wednesday 05:00 AM-05:00 PM). Delete confirmation dialog includes a helpful extra line ("By deleting this mode, all the related reasons will be deleted") not previously documented — worth noting for future sessions. **BUG-002 (URL data-exposure) RE-CONFIRMED still present** — Edit Mode still puts a large JSON blob (full settings array with IDs, timestamps, pivot data) in the query string, unchanged from every prior session (2026-06-20 → 2026-08-06). Empty-submit on Add Mode is a silent no-op, matching platform pattern.

### Custom Fields — investigated the documented display bug in depth, does NOT reproduce as previously understood
Created a fresh section ("PreliveSection", bound to User Profile, User Type "Visitor") with one field ("PreliveField", Date type). **Gotcha discovered:** the section-level "User Type" field (top of the Add Section form) is a required, but silently-enforced, field — clicking "Add"/submit with it empty does nothing (no error shown), exactly the platform's silent-submit pattern, but easy to miss since every other required field on this form fails with the section save simply not going through with no visual cause. Select a User Type before submitting.

Once saved, the new section showed collapsed in the grid with a **▼ expand chevron identical to every other section's**. Clicking that chevron correctly expanded it to reveal "PreliveField" as a child row with all its data intact (Field Type "Date", Mandatory "Yes", Fillable by user "Yes", Show on "Sign in" — all correctly saved). **This means the 2026-07-31 finding ("a newly-created section's field(s) never show as child rows, reproducible across reload and expand/collapse toggle") does NOT reproduce here.** My own first attempt at reproducing it here initially *looked* like a repro too — after clicking the chevron, the screen didn't visibly change because the resulting child row was scrolled just below the visible viewport, and I nearly recorded a false-positive re-confirmation before scrolling down and finding the child row rendering exactly as expected. **Recommend re-verifying the original 2026-07-31 finding on `visipoint.uk` directly before concluding it's fixed everywhere** — this could be a genuine fix, an environment-specific difference, or (less likely, given the config-driven UI is presumably shared) a residual bug that only manifests under different conditions than tested here. Do not treat this as a confirmed fix or a confirmed still-open bug until cross-checked.

### Visit Permits
Full CRUD cycle (Add → Delete) confirmed working on a fresh test permit ("PrelivePermit"). Confirmed matching docs: "Permit name is required" / "Start Date is required" real inline validation on empty submit (this module does NOT follow the platform's usual silent-submit pattern), separate Edit/Delete buttons per row (not a dropdown), and — re-confirming the 2026-07-31 correction — **the Delete confirmation dialog IS present** ("Are you sure you want to 'Delete' this Visit Permit? By deleting this visit permit, it will be permanently removed from all linked user types and associated users."). New observation: the Start Date calendar picker disables/greys out past dates, same pattern documented for Add Visits' Custom Date picker.

**This entity ("Custom field project") had ZERO Visit Permits configured at session start** — which retroactively explains an earlier Quick Sign In finding from this same session: every Site/Area/User Type combination tested there showed "This user is not permitted to enter this area according to the journey," requiring a 20+ character justification note to override. With no permits configured for any user type, no one is ever "permitted" by default — that Quick Sign In behavior was correctly enforcing a real (if entity-specific) configuration state, not a bug.
