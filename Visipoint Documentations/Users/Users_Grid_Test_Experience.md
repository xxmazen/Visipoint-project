# Users Grid — Test Experience & Knowledge Base

**Page URL:** `https://visipoint.uk/users`  
**Last Tested:** 2026-08-08  
**Tester:** Claude (automated browser testing via Chrome MCP)

## UPDATE (2026-08-08) — Smoke test on production (visipoint.uk), "UK (Testing)" tenant

**Scope:** P0/P1 smoke test only — grid load, display, search, filter, sort, pagination, Add/Edit/Delete User, Sign in/out modal, Select Actions dropdown, grid stability after actions. Not exhaustive.

**Result: PASS.** No functional smoke-test failures. All happy-path flows tested worked correctly:
- Grid loaded, 463 active users, columns/headers/avatars all rendered correctly.
- Search (First Name "Maged" → 1 exact match), User Type filter (Approval → 6 correct rows), Sort (First Name ascending, avoided the known Visipoint Passport sort bug), Pagination (page size 10 → 47 pages; page 2 nav correct; reset to 50) all worked.
- "Clear filters" correctly cleared both a column search box and a dropdown filter, restoring the full 463-item count — re-confirms the 2026-07-01 fix is still holding, and also on production specifically (most recent confirmations had been on QA/pre-live).
- **Add User full end-to-end create confirmed working** on production: created disposable user "SmokeTest QCUser" (Visitor type), all 3 wizard steps (Main Section → "Test"/"YTYT" custom fields → a **third** custom-fields page "Lion"/"T10") completed, item count went 463→464, custom field values (YTYT=R, Test=T, T10=Y) persisted and displayed correctly in the grid.
- Edit User modal opened correctly pre-populated with all saved data (User Type, First/Last Name, generated ID). Cancelled cleanly with no side effects.
- Delete User: confirmation modal showed the correct full name ("SmokeTest QCUser"), confirmed delete, user removed cleanly (item count back to 463), no spinner freeze — re-confirms 2026-07-01's fix still holding on production.
- Sign In/Sign Out modal (row action) opened correctly with Site/Area pickers.
- Select Actions dropdown confirmed to contain: Edit User, Visit Permits, Invite to Dashboard, Print QuickPass, Archive, Delete — matches 2026-07-15 documentation.
- Grid remained fully responsive after all Add/Edit/Delete actions, no stuck modals, no residual corrupted state.

**New findings (not bugs, documentation gaps):**
1. **The Add User wizard has 3 steps, not 2 — a third page exists beyond "Main Section" and the "Test"/"YTYT" custom fields**, at least on this tenant ("UK (Testing)", production). It contains additional custom fields ("Lion" section with a "T10" Y/N checkbox group) and is also silently required (ADD stays disabled until it's filled, consistent with the existing "silently required custom fields" pattern). Update any automation assuming only 2 wizard steps.
2. **The "ID" field (with the "Generate" button) is silently required to enable the wizard's "Next" button on step 1** — confirmed by testing: with User Type + First Name + Last Name filled, "Next" stayed `disabled` until ID was populated (clicking "Generate" was sufficient). Not previously documented precisely — existing notes only covered the custom-fields-step gotcha, not this main-section field.
3. **Extreme initial page load observed once this session**: the very first navigation to `visipoint.uk/` (fresh tab, empty group) took roughly 5+ minutes to boot — `document.readyState` stayed `"loading"` with ~30-40 of ~180 static asset requests (webpack CSS/JS chunks) genuinely stuck `pending` for minutes at a time before slowly completing one-by-one, not a hard hang (a direct `fetch()` of one "stuck" chunk succeeded in 4s from a separate request, confirming the network itself was fine). A second fresh navigation immediately after (browser cache now warm) loaded in ~5-8 seconds. This resembles the already-documented "Pre-live dashboard — slow/hung SSO landing page" finding (Jira synthesis Section 4) but was observed here on **production's cold root load**, not a post-SSO pre-live redirect. Not filed as a bug — only observed once, self-resolved, and the cause (large number of small chunk requests) may just be this app's known heavy webpack bundle under cold-cache conditions. Worth a follow-up session specifically timing cold-cache loads if this recurs.

---

## UPDATE (2026-07-30) — Full re-test: new User Type filter bug, Visipoint Passport sort bug escalated, Add Label fix confirmed

Full report: `Users_Testing_Report_2026-07-30.md`.

1. **User Type column filter multi-select error — NOT REPRODUCIBLE ON RETEST, likely tied to bug #2.** Initially found: checking 2+ User Type boxes (e.g. Staff + Visitor) showed "An error occurred. Please try again later." with a client-side `TypeError: Cannot read properties of undefined (reading 'message')` in console. Retested later the same session (after clearing the Visipoint Passport sort corruption below via `clearSorting()`): ran the exact same Staff+Visitor combo twice more plus a fresh Walk-in+Approval combo — all three applied cleanly with correct results, no error. Working theory: the original failure was a downstream symptom of the corrupted grid/Vuex state from bug #2, not an independent User Type-specific defect. Don't file as standalone; re-verify if it recurs outside the context of a Passport-sort corruption.
2. **ESCALATED — Visipoint Passport sort bug is worse than previously documented.** It's not just a transient error: the broken state is stored in TWO places — a server-side `adminPreference` row AND a separate un-synced copy in the Vuex store persisted to `localStorage['vuex']` (`grid.tableHeaders.columns[n].sortOrder`). The client cache does not get corrected just because the server row is fixed. **The previously-documented workaround (click Active Users tab) did NOT work this session** — the grid stayed broken across tab toggles, hard reloads, and even a brand-new browser tab. Also saw a new symptom: pagination footer showed "Page 1 of 1 (-1 items)". There is no in-app self-service recovery for a user who hits this — only calling the grid's internal `clearSorting()` (not exposed as a UI button) fixed it in testing. Recommend raising this ticket's priority.
3. **Add Label Enter-key bug (from 2026-07-29 report) is FIXED.** Pressing Enter after typing a label now correctly creates the tag pill; previously it cleared the input.
4. Confirmed still working / unchanged: Delete confirmation shows correct username, "Clear filters" clears both dropdown and text filters, Delete has no spinner freeze, Sign in/out row action still never offers Check-out (bug #2 from 2026-07-29 still present, not retested to completion but modal behavior unchanged).
5. Minor new cosmetic notes: Archived Users tab pagination footer shows just "Page 1" with no "of X (N items)" (Active Users shows the full format); switching page size while viewing a page beyond the new total briefly shows an impossible "Page 10 of 5" label for ~2s before self-correcting.
6. Phone-wipe-on-edit bug (2026-07-29 finding) was **not retestable this session** — the test user used for the Edit flow had no phone number set. Still assume present until re-verified with a phone-bearing user.

## UPDATE (2026-07-29) — Full grid coverage: Add User, row/mass actions, sort bug

Full report: `Users_Testing_Report_2026-07-29.md`. Three new bugs found:

1. **Edit User silently wipes Phone Number on save** if the phone field isn't touched (it renders as a disabled field + "Change" button once a phone exists; the save payload appears to omit it, and the backend treats the omission as "clear"). Any routine edit (name, label, etc.) to a user with a phone number will delete it. Confirmed via reload + reopening Edit User.
2. **"Sign in/out" row action never offers Check-out.** The modal is identical every time regardless of current status — clicking it on an already-checked-in user just resets the Arrival Time on the existing Dashboard record instead of checking them out or offering to. No discoverable check-out path from the Users grid.
3. **Sorting by the "Visipoint Passport" column returns HTTP 400 and breaks the whole grid** (infinite spinner, error banner, sometimes literally renders `[object Object]`). Network: `GET /api/users/{id}?...&sort=[{"selector":"VisiPointPassport",...}]` → 400. The broken sort persists across reloads (grid state is saved server-side via `adminPreference`) and "Clear filters" does NOT fix it — only re-clicking the Active Users tab toggle resets it. High severity: a real user who sorts by this column could get stuck.

Also confirmed **working correctly**: Add User 2-step wizard (note: two custom fields "Test" and "YTYT" are silently required with no visible marker before the Add button enables — not a bug per app's silent-validation pattern, just easy to miss), Archive/Delete (proper confirmation dialogs), all column filters/sort(other columns)/pagination, all mass actions (Change User Type, Add Label, Add Visit Permit — correctly no-ops for pre-registered/Staff user types with a clear warning, Print QuickPass, mass Sign in/out with per-user temperature + status tracking). No mass Archive/Delete exists (row-only).

Minor cosmetic notes: Visit Permits grid doesn't refresh its Status/Action button in-place after Revoke (needs reload); Add Label's Enter key clears the input instead of creating the tag despite the UI saying "Press enter to create a tag" (clicking the suggestion works).

## UPDATE (2026-08-06) — Monkey testing pass on QA environment, "QA testing" entity (327 real users)

**Full report:** see the `visipoint-module-testing` skill's new Users section for the condensed version.

**Scope:** deliberate chaotic/rapid interaction testing (not systematic scenario coverage) — rapid clicking, garbage input, modal-stacking attempts, keyboard spam — aimed at finding crashes rather than logic bugs.

**Visipoint Passport sort bug — re-confirmed HIGH severity, now confirmed on QA too.** Sorting by this column produces "An error occurred. Please try again later." and corrupts grid state, exactly as documented on `visipoint.uk`. **New finding, contradicts the 2026-07-30 report:** this session, clicking "Clear filters" recovered the grid cleanly — verified genuine (not cosmetic) via a full page reload afterward, which loaded clean with no error banner and the correct 327-item count. The 2026-07-30 session found Clear filters did NOT work and only an internal `clearSorting()` call recovered it. Recovery behavior may differ by session/environment/how the corruption was triggered — don't assume Clear filters will always fix it, but it's worth trying first before assuming a hard-stuck state.

**No crashes found from chaos testing.** Specifically stress-tested and confirmed resilient (no error, no stuck state, no duplicate data):
- 6x rapid Active Users / Archived Users tab toggling in immediate succession.
- Combined rapid pagination clicks + page-size changes fired back-to-back (ended on page size 5, page 3 of 66 — 66×5=327, math checked out correctly).
- 4x rapid header select-all/deselect checkbox toggling (ended correctly unchecked after an even number of toggles).
- Selecting 50 rows then firing 3 different mass-action buttons (Change User Type, Add Label, Print QuickPass) in one immediate batch — only the FIRST modal opened; its backdrop correctly blocked the other two clicks from opening additional modals. No stacking, no crash.
- Escape-key spam (5x) on the Add User modal while it held unsaved typed data — modal did not close, data stayed intact. Not a bug; likely intentional protection against accidental data loss on a data-entry modal.
- Rapid triple-click on Add User's "Generate" (ID) button — only ever generated one ID (`VP-1Bgwyv9i` example); the button correctly disables itself (`disabled: true`) after first use, preventing a duplicate-ID race.
- A column search filter (First Name) accepted a 228-character garbage string (`<script>alert(1)</script>'; DROP TABLE users; --  🔥🔥🔥العربية_测试_A very...aaaa...`) with **no length cap and no character filtering** — the full string landed in the input's `.value` (confirmed via DOM read), was safely rendered as inert plain text (no XSS fired), and the grid correctly showed "No records yet" (0 items) with no crash.

**New Add User field-validation findings (not previously documented this precisely):**
- First Name and Last Name inputs have a real `maxLength=20` HTML attribute (confirmed via `input.maxLength` in DOM) — this safely truncates any payload, e.g. `<img src=x onerror=alert(1)>` (29 chars) got truncated to `<img src=x onerror=a` (20 chars) before it could even be a complete tag. No XSS risk here specifically because of the length cap, independent of any escaping.
- Email Address has genuine real-time inline validation: typing `not-an-email@@@` immediately shows a red border + "Please enter valid email." and this correctly blocks submission.
- Phone Number silently rejects all-non-digit input — typing `abcXYZ!!!` leaves the field completely empty (matches the digits-only enforcement pattern documented elsewhere in the app, e.g. Add Visits).
- The "ADD" submit button can stay silently disabled with First Name + Last Name + User Type all filled and no visible red error anywhere — this is the already-known "silently required custom fields" gotcha (some entities have hidden-until-scrolled custom fields like "Test"/"YTYT" that also gate the button). Confirmed this entity ("QA testing") also has this gotcha; a full valid-data Add User submission was not completed this session as a result — a genuine double-submit/duplicate-creation chaos test is still untested, carried over to a future session.

> Read this file before testing the Users grid again. It captures everything learned during testing — page structure, feature behavior, automation notes, and bugs found.

---

## Page Structure Overview

The Users grid is the main user management page. It has two tabs at the top:
- **Active Users** (default view)
- **Archived Users**

### Top Toolbar (above the grid)
- **Add** button — opens the Add User modal
- **Import** button — navigates to the Import Queue page

### Grid Toolbar (above column headers)
- **Clear filters** button (red outline) — appears when filters are active
- **N Users selected** counter — appears when rows are checked
- **Mass action buttons** (appear when rows are selected): Change User Type, Invite to Dashboard, Add Label, Add Visit Permit, Print QuickPass, Sign in/out

### Grid Columns
| Column | Type | Notes |
|--------|------|-------|
| Checkbox | Selection | Single/multi/all row selection |
| Image | Avatar | User initials avatar |
| First Name | Text + Search | Sortable, has search box below header |
| Last Name | Text + Search | Has search box below header |
| User Type | Text + Filter | Filter icon in header, dropdown filter |
| Dashboard Access | Text + Filter | Yes/No, filter icon in header |
| Email Address | Text + Search | Has search box below header |
| Phone Number | Text + Search | Has search box below header |
| Visipoint Passport | Text | Yes/No |

### Pagination (bottom of grid)
- Page size options: **5, 10, 25, 50 (default), 100**
- Page navigation: numbered buttons + prev/next arrows
- Item counter: "Page X of Y (N items)"

---

## Feature Behavior — What Was Learned

### Active / Archived Tabs
- Clicking **Archived Users** tab reloads the grid showing only archived users
- Clicking **Active Users** tab returns to the default view
- Both tabs work correctly

### Search Fields
- Each column with a search box filters in real time as you type
- Filters are **contains** type (e.g., "Ali" matches "Salil", "Dalila", "Alistair")
- Multiple column filters can be active simultaneously

### User Type Filter
- Clicking the filter icon on the User Type column header opens a dropdown
- Available types: Approval, Staff, Visitor, Walk-in
- Selecting a type filters the grid instantly

### Dashboard Access Filter
- Clicking the filter icon on the Dashboard Access column header opens a dropdown
- Options: Yes / No

### Clear Filters Button
- Appears with red outline when any filter is active
- **Known behavior to verify:** Does it reset ALL filters (column search + dropdown filters) or only dropdown filters?
- **Observed during testing:** Clicking it did NOT clear the First Name column search filter — item count remained unchanged

### Sorting
- Clicking a column header sorts ascending (↑), clicking again sorts descending (↓)
- Sort is applied via DxDataGrid — `columnOption('FirstName', 'sortOrder', 'asc')`

### Row Selection
- Individual row checkboxes for single selection
- Header checkbox: selects all rows on current page (50 rows if page size = 50)
- Selecting rows triggers the mass action toolbar
- `dxGrid.instance.selectAll()` selects all rows on current page
- `dxGrid.instance.deselectAll()` deselects all

### Pagination
- Page sizes all work: 5, 10, 25, 50, 100
- Page navigation (numbered buttons) works correctly
- Default page size is 50

---

## Row Actions — Select Actions Dropdown

Each row has a **Select Actions** dropdown. Options:

### Edit User
- Opens the **Add/Edit User modal** (`add-edit-user`)
- Pre-populated with the user's existing data
- Same form as Add User

### Archive
- Opens **Archive confirmation modal** (`userStatusToggleConfirmModal`)
- Shows the username in the message
- Has **Archive** and **Cancel** buttons
- Cancel works correctly
- Confirming Archive: moves user to Archived Users tab, grid refreshes

### Delete
- Opens **Delete confirmation modal** (`deleteActionModal`)
- **See Bug 2, 3, 4 below**

### Sign in/out
- Opens a **multi-step dialog** (2 steps):
  - **Step 1:** Site dropdown (pre-filled with current site), Area dropdown, Temperature unit toggle (°C/°F), Temperature slider (default 37°C), Notes text field — then **Next** button
  - **Step 2:** Print badge toggle, **Check in** and **Cancel** buttons
- **Note:** The dialog is wider than the viewport — some buttons may be outside the visible area. Use JS `.click()` to interact with them.
- **Note:** "Next" button on Step 1 may be outside viewport — use JS to click it

---

## Mass Action Buttons (toolbar when rows are selected)

### Change User Type
- Opens a modal with:
  - Confirmation message
  - **User Type** dropdown (vue-treeselect component) — options: Approval, Staff, Visitor, Walk-in
  - **Change** and **Cancel** buttons
- To open the dropdown: use `document.querySelector('.vue-treeselect').__vue__.openMenu()`
- Change button appears muted until a type is selected; turns dark blue when selected
- Cancel works correctly

### Invite to Dashboard
- **Navigates to a new page:** `https://visipoint.uk/invite-users-to-dashboard`
- Page shows all selected users with: User Role, Site, User Type dropdowns per row
- "All" bulk-set row at top
- Pre-validates: shows "Email address or phone number is required." in red for users missing contact info
- "Invite all" button at top
- **Note:** This navigates away from the Users page — use browser back or sidebar Users link to return

### Add Label
- Opens a modal with:
  - Confirmation message
  - **Label** tag-input field — type text then press **Enter** to create a tag (pill)
  - Add button is disabled (muted) until at least one tag is created; turns dark blue after
  - **Add** and **Cancel** buttons
- Cancel works correctly

### Add Visit Permit
- Opens a modal with:
  - Warning: "⚠️ Some selected user types are pre-registered by admin. Visit permits do not apply to them."
  - **Visit Permits** dropdown
  - Note: "Visit permits applied on the parent won't be automatically inherited to its children"
  - **Add** and **Cancel** buttons
- Cancel works correctly

### Print QuickPass
- Opens a modal with printer selection:
  - **Local printer** (selected by default)
  - **Online printer**
  - **Print** and **Cancel** buttons
- Cancel works correctly

### Sign in/out (bulk)
- Same multi-step dialog as single-user Sign in/out
- **Step 1:** Site pre-filled with current site, Area dropdown, Continue and Cancel buttons
- Works correctly

---

## Add User Modal

- Triggered by the **Add** button in the top toolbar
- Modal ID: `add-edit-user`
- Form fields include: First Name, Last Name, Email, Phone Number, User Type, etc.
- Has validation for required fields
- **Note:** There are multiple "Add" buttons on the page — when using JS `.click()`, make sure to target the button inside the modal, not the top-level Add button

---

## Import Button

- Navigates to: `https://visipoint.uk/users/import/queue`
- Leads to the Import Queue page for bulk user import

---

## Automation Notes (for future testing sessions)

### Getting the DxDataGrid instance
```javascript
const mainPage = document.querySelector('.main-page-container').__vue__;
const dxGrid = mainPage.$children.find(c => (c.$options.name || c.$options._componentTag || '') === 'DxDataGrid');
```

### Common grid operations
```javascript
dxGrid.instance.selectAll();                          // Select all on current page
dxGrid.instance.deselectAll();                        // Deselect all
dxGrid.instance.pageSize(50);                         // Set page size
dxGrid.instance.pageIndex(1);                         // Go to page 2 (0-based)
dxGrid.instance.totalCount();                         // Total item count
dxGrid.instance.getSelectedRowKeys().length;          // Count selected rows
dxGrid.instance.columnOption('FirstName', 'sortOrder', 'asc');  // Sort
dxGrid.instance.filter(['FirstName', 'contains', 'Ab']);        // Apply filter
dxGrid.instance.clearFilter();                        // Clear all filters
dxGrid.instance.columnOption('FirstName', 'filterValue', 'Ali'); // Column filter
```

### Opening modals
```javascript
mainPage.$root.$emit('bv::show::modal', 'userStatusToggleConfirmModal'); // Archive
mainPage.$root.$emit('bv::show::modal', 'deleteActionModal');            // Delete
mainPage.$root.$emit('bv::hide::modal', 'deleteActionModal');            // Close modal
```

### All known modal IDs on this page
`add-edit-user`, `deleteActionModal`, `userStatusToggleConfirmModal`, `addVisitPermitModal`, `rfidEnrollmentModal`, `changeUserType`, `change-user-type-massaction`, `revoke-from-dashboard`, `revoke-confirm-modal`, `passportActionFormModal`, `passportActionConfirmModal`, `addUserPermissionsModal`

### Opening vue-treeselect dropdown
```javascript
document.querySelector('.vue-treeselect').__vue__.openMenu();
```

### Clicking buttons safely (avoids targeting wrong "Add" button)
```javascript
// Always scope button search to the active modal
const modal = document.querySelector('.modal.show');
const btn = Array.from(modal.querySelectorAll('button')).find(b => b.textContent.trim() === 'Cancel');
btn.click();
```

### Tab ID used during testing
- Tab 1468056867 (`https://visipoint.uk/users`) — primary test tab
- **Note:** Tab IDs change between sessions — always use `tabs_context_mcp` to get fresh IDs

---

## Bugs Found (Pending Logic Review)

| # | Bug | Severity | Confidence |
|---|-----|----------|------------|
| 1 | Grid shows "-1 items" during refresh after archive | Minor | High |
| 2 | Delete modal: Username missing from confirmation message | Major | High |
| 3 | Delete modal: Loading spinner never resolves | Major | Medium — may require native click flow |
| 4 | Delete modal: Cannot dismiss while spinner is active | Major | Medium — may be intentional |
| 5 | Sign in/out: Back button non-functional on Step 2 | Minor | Medium — viewport clipping possible |
| 6 | Change User Type: No validation feedback on empty submit | Minor | Medium — button may be disabled silently |
| 7 | Add Visit Permit: No validation feedback on empty submit | Minor | Medium — button may be disabled silently |
| 8 | Clear filters: Does not clear active column search filters | Major | High |

> **Note on confidence:** Bugs marked Medium may be expected behavior that was misidentified due to limited knowledge of the application logic. These should be reviewed against product requirements before being filed.

---

## UPDATE (2026-07-01) — Regression Retest

- **Bug 2 (Delete modal missing username): CONFIRMED FIXED.** Confirmation now reads "Are you sure you want to 'Delete' Iiio Iiio?" with the actual user name populated.
- **Bug 8 (Clear filters doesn't clear text search): CONFIRMED FIXED.** First Name search filter and the Clear filters button itself both clear correctly.
- Bugs 1, 3, 4, 5, 6, 7 not retested this pass (lower severity / needed live data states not reproduced).

Full details in `D:\Visipoint md files\Multi_Module_Testing_Report_2026-07-01.md`.

---

## What Was NOT Fully Tested

- Confirm flow for Change User Type with a real type selected (cancelled to avoid data changes)
- Confirm flow for Add Visit Permit with a real permit selected
- Confirm flow for Print QuickPass (would send to a printer)
- Add Label with a real label submitted
- Import full flow (file upload and processing)
- Archived Users grid Activate/Delete confirm
- Invite to Dashboard "Invite all" (would send real emails/SMS)

---

## Session Update — 2026-07-15 (Full Field/Button Walkthrough + API Performance)

**Full report:** `Users_Testing_Report_2026-07-15.md`

- **Delete flow via native UI click confirmed working** (was previously only triggered via JS modal emit) — Bug 2 fix (username in confirmation) re-confirmed holding, no spinner freeze, Bugs 3/4 did not reproduce this session.
- **Full Add User form tested** (all fields, both wizard steps). The form is a **2-step wizard**: step 1 = Main Section (User Type, First/Last Name, Email, Phone, ID+Generate, RFID, Label), step 2 = dynamic Custom Fields (e.g. "Test", "YTYT" — matches grid custom-field columns).
- **RFID field — investigated, NOT a bug.** Initially looked like a bug (typing "RFIDTEST01" and pressing Enter silently discarded the value, confirmed via Vue state). User clarified the field is numeric-only by design; retested with digits-only ("1234567890") and the tag was created correctly. Non-numeric input is silently rejected with no error message — matches this app's general silent-validation pattern, not filed as a bug. **Lesson: test RFID/similar restricted-format fields with valid-format input before concluding a tag/save mechanism is broken.**
- **Row Select Actions dropdown is richer than documented:** now Edit User, Visit Permits, Invite to Dashboard, Print QuickPass, Archive, Delete (Sign in/out moved to its own button next to the dropdown).
- **Dashboard Access filter** now offers granular roles (No/Pending/Admin/Employee/Fire Warden/Employee with reporting), not just Yes/No.
- **New grid columns:** Pin Code, RFID, Document - Vaccine/P..., plus custom-field columns (Label, YTYT, Test). New toolbar buttons: Columns, Export.
- **Import is a full wizard** at `/users/import` (Excel template, unique-field matching, duplicate handling, optional photo ZIP) — richer than "navigates to Import Queue." Import Queue is a separate page (`/users/import/queue`) reached via a link.
- **API performance baselines captured** (host `api.visipoint.uk`): `data_users_grid` avg 522ms (n=5), `check_active_sessions` avg 514ms (n=5), `adminPreference` avg 647ms (n=3). The main paginated list endpoint (`GET /api/users/{id}?skip=&take=&filterBy=...`) showed high variance (100ms–5.2s) in redacted timing samples — flagged for follow-up with a dedicated network tool, not confirmed as a regression.
