# Announcements Grid — Test Experience & Knowledge Base

**Page URL:** `https://visipoint.uk/announcements`  
**Last Tested:** 2026-08-08  
**Tester:** Claude (automated browser testing via Chrome MCP)

## UPDATE (2026-08-08) — Smoke test on production (visipoint.uk), "UK (Testing)" tenant

**Scope:** P0/P1 smoke test — grid load, display, search, filter, sort, pagination, Add/View/Edit/Delete, grid stability. Not exhaustive.

**Result: PASS.** No functional smoke-test failures.
- Grid loaded correctly with 1 pre-existing announcement, columns/headers visible.
- Title search (Title="Test" → 1 match; nonsense string → correctly "No records yet") and Status header filter (Active → correct match) both worked.
- Sort on Title column applied correctly (arrow indicator, "Clear filters" appeared).
- Pagination controls (page size buttons) accepted clicks without error; dataset too small (1-2 rows) to exercise multi-page navigation.
- **Add Announcement confirmed working end-to-end on production, on the correct "UK (Testing)" tenant** (previous 2026-08-01 production confirmation was on a different tenant, "UK (TESTING02)", after a session-redirect quirk) — created "QC Smoke Test Announcement" (Staff target, Immediately/Never), item count 1→2, appeared correctly in the grid.
- **View/Preview (eye icon) confirmed working** — modal rendered fully visible immediately with correct Title/User Type badge/body/Publish-Expiry-Created timestamps. The historic Bootstrap fade-transition bug remains fixed.
- **Edit icon not present on either row this session** (`canEdit` permission flag was false for both) — consistent with documented conditional visibility, not a bug. Full Edit-form-prefill retest still not done since 2026-07-15 (no `canEdit=true` row was available then either).
- **Delete confirmed working** — confirmation text read "Are you sure you want to 'Delete' this Announcement?" (does not name the announcement, unlike Users grid's delete modal — this matches the originally-documented content, not a regression), confirmed delete, item count back to 1, row gone, no lingering spinner.

**Automation-only notes (not product bugs):**
- **The viewport-shrink issue (documented in this file's "Automation Notes" section) reproduced again this session** — after a filter-popup interaction, `window.innerWidth`/`innerHeight` collapsed to 135×77, breaking coordinate-based clicks (all subsequent clicks silently missed). Fixed via `navigate()` + `resize_window(1920, 1080)` per the existing documented recovery steps. Still an automation-environment quirk, not a real end-user bug (real users don't script window resizes).
- Trash icon required several attempts across different techniques (real coordinate click at slightly-off coordinates, JS `.click()`, dispatched MouseEvent, vnode-click pattern) before the Delete modal actually opened — consistent with this file's already-documented flakiness for the eye/trash icon click handlers ("sometimes does nothing"). A precise real coordinate click, taken from a fresh screenshot right after window-size recovery, is what finally worked reliably.

**BUG-ANN-001 (HTTP 422 "server name invalid") did NOT reproduce on production's "UK (Testing)" tenant** — Add Announcement succeeded cleanly here, reinforcing that BUG-ANN-001 (filed as CL-17913) is scoped to the QA environment's "QA testing" entity rather than a platform-wide regression.

---

---

## Page Structure Overview

### Top Toolbar
- **Announcements** page title (breadcrumb)
- **Add** button (dark blue) — navigates to `/add-announcement` page (NOT a modal)

### Grid Toolbar (above column headers)
- **Columns** button — opens Column Chooser panel
- **Export** button (dropdown) — Excel, CSV, PDF options
- **Clear filters** button (red outline) — appears when any column filter, search, or sort is active

### Grid Columns (14 total, horizontal scroll required to see all)

| # | Column | Filter Type | Notes |
|---|--------|-------------|-------|
| 1 | Title | Text search box | |
| 2 | Created By | Text search box | |
| 3 | Created At | Text search box | |
| 4 | User type | Text search box | e.g. "Staff" |
| 5 | Area | Text search box | |
| 6 | Verbal | Header filter (≡) | Dropdown with unique values |
| 7 | Status | Header filter (≡) | Options: Active |
| 8 | Publish Time | Text search box | |
| 9 | Expiry Time | Text search box | |
| 10 | Urgent | Header filter (≡) | Options: Yes/No |
| 11 | Pinned | Header filter (≡) | Options: Yes/No |
| 12 | Last Edit By | Text search box | |
| 13 | Last Edit At | Text search box | |
| 14 | Actions | Eye + Trash icons | Scrolled far right |

### Pagination (bottom of grid)
- Page size options: **5, 10, 25, 50 (default), 100**
- Page navigation: numbered buttons + prev/next arrows
- Item counter: "Page X of Y (N items)"

---

## Feature Behavior

### Add Button
- Navigates to `/add-announcement` (full page, not a modal)
- Form fields (top to bottom):
  1. **Title** text input
  2. **Announcement body** — rich text editor (B, I, U, bullet list, text color, alignment, link, image, video buttons)
  3. **URL** (optional) text input
  4. **Urgent announcement** toggle (OFF by default)
  5. **Pinned announcement** toggle (OFF by default)
  6. **Announcement target** section:
     - User type dropdown
     - Area dropdown
  7. **Announcement publish** section:
     - "Immediately" radio (default)
     - "Custom date and time:" radio → shows Publish date + Publish time inputs
  8. **Announcement expiry** section:
     - "Never" radio (default)
     - "Custom date and time:" radio → shows Expiry date + Expiry time inputs
  9. **Add** submit button (dark blue)

### Columns Button → Column Chooser
- Opens a panel titled "Column Chooser" overlapping the grid on the left
- Lists all 14 columns with checkboxes (all checked by default)
- Uncheck a column to hide it from the grid
- Close: click the X icon in the panel header, or click outside the panel
- JS: `document.querySelector('.custom-columns').click()`

### Export Button → Dropdown Menu
- Opens a dropdown with 3 format options:
  - **Excel**
  - **CSV**
  - **PDF**
- The Export button is a DevExtreme `DropDownButton` element (`dx-dropdownbutton-action`)
- JS: `document.querySelector('.dx-dropdownbutton-action').click()`
- Close without exporting: `document.body.click()`

### Header Filters (≡ icon) — Verbal, Status, Urgent, Pinned
- Clicking the ≡ icon on a column header opens a filter popup with checkboxes for each unique value
- "Select All" checkbox selects/deselects all values
- Selecting values filters the grid immediately
- Close: press `Escape` or click anywhere outside the popup
- JS to find and click a specific column's filter icon:
```javascript
const filterIcons = Array.from(document.querySelectorAll('.dx-header-filter'));
for (const icon of filterIcons) {
  const cell = icon.closest('td, th');
  if (cell?.querySelector('.dx-datagrid-text-content')?.textContent?.trim() === 'Status') {
    icon.click(); break;
  }
}
// Replace 'Status' with 'Urgent', 'Pinned', or 'Verbal' for other columns
```

### Column Search Boxes (text inputs)
- Filters are "contains" type — real-time filtering as you type
- All text-based columns (Title, Created By, Created At, etc.) have search boxes in the filter row
- The filter row element: `.dx-datagrid-filter-row`
- JS to type in Title search: find `input` in the filter row at column index 0

### Column Sorting
- Click any column header to sort ascending (↑ indicator appears in header)
- Click again to sort descending (↓)
- Click a third time to remove sort
- Sorting triggers the "Clear filters" button to appear

### Clear Filters Button
- Appears when any column sort, filter, or search is active
- **Bug**: Clicking it does NOT clear text search inputs or column sort indicators
- **Bug**: The button remains visible even after clicking (doesn't disappear when no filters remain)
- This button appears to clear only DxDataGrid header filter selections (≡ popup choices), not text searches or sorts

### Pagination Size Buttons
- All five sizes work correctly: 5, 10, 25, 50, 100
- Active size has CSS class `dx-selection`
- JS: `Array.from(document.querySelectorAll('.dx-page-size')).find(b => b.textContent.trim() === '10').click()`

---

## Row Actions (Actions Column)

> The Actions column is the **last column** (far right) and requires horizontal scrolling to see.
> It shows 2 icons per row: an eye/view icon and a trash/delete icon.

### Eye Icon (View/Preview)
- Width=22px SVG; always shown (no permission condition)
- Opens **"Announcement Preview"** modal (`announcement-data-modal`)
- Modal content:
  - Title (bold)
  - Tags: Urgent (red badge), User Type (gray badge — e.g., "Staff")
  - Body text (announcement message)
  - Publish Time, Expiry Time
  - "Created At" timestamp + creator name
  - Two ❓ help icons (purpose unclear)
- Close: red X button (top right of modal) or:
  ```javascript
  root.$emit('bv::hide::modal', 'announcement-data-modal');
  ```

### Edit Icon (pencil, when canEdit=true)
- Navigates to `/add-announcement?id=<announcementId>`
- Opens the same Add Announcement form pre-filled with existing data

### Trash/Delete Icon
- Width=11px SVG; only shown when `announcementData.deleted === true` (permission flag from API)
- Opens **"Delete Announcement"** confirmation modal (`deleteAnnouncementConfirmModal`)
- Modal content:
  - Title: "Delete Announcement"
  - Body: 'Are you sure you want to **"Delete"** this Announcement?'
  - **Cancel** button (dark blue) — closes without deleting; grid refreshes, row remains
  - **Delete** button (red outline) — permanently deletes the announcement
- To click Cancel via JS:
  ```javascript
  Array.from(document.querySelector('.modal.show').querySelectorAll('button'))
    .find(b => b.textContent.trim() === 'Cancel').click();
  ```

---

## Critical Bug: Bootstrap Modal Fade Transition Failure

**Affects:** Both `announcement-data-modal` (eye icon) AND `deleteAnnouncementConfirmModal` (trash icon)

**What happens:**
1. User clicks eye or trash icon
2. `setAnnouncementData()` is called (Vuex action to set selected row data)
3. `bv::show::modal` event is emitted on root
4. BModal's `show()` method runs → `isVisible = true`
5. Modal is **rendered in the DOM** with `display: block` but **opacity: 0** (class `modal fade`)
6. Bootstrap-Vue's transition should add the `show` CSS class to trigger fade-in, but **it never does**
7. Modal is invisible — user sees only the backdrop darkening (partially)

**Impact:** Real users clicking either icon see nothing visual — the modals are present in DOM but invisible. This is a critical UX bug.

**Workaround for testing:**
```javascript
// After triggering the modal click:
const modalEl = document.querySelector('#announcement-data-modal___BV_modal_outer_ .modal');
// or for delete: document.querySelector('#deleteAnnouncementConfirmModal___BV_modal_outer_ .modal')
if (modalEl) {
  modalEl.classList.add('show');
  const backdrop = document.querySelector('.modal-backdrop.fade');
  if (backdrop) backdrop.classList.add('show');
}
```

---

## Automation Notes

### Scroll grid to see Actions column (far right)
```javascript
const containers = document.querySelectorAll('.dx-scrollable-container');
for (const c of containers) {
  if (c.scrollWidth > c.clientWidth) c.scrollLeft = c.scrollWidth;
}
```

### Scroll grid back to left (Title column)
```javascript
const containers = document.querySelectorAll('.dx-scrollable-container');
for (const c of containers) { if (c.scrollWidth > c.clientWidth) c.scrollLeft = 0; }
```

### Find ActionCell and trigger icon clicks via vnode
```javascript
const root = document.querySelector('.main-page-container').__vue__.$root;

// Find the ActionCell component
const dFlex = document.querySelector('.dx-data-row td:last-child .d-flex');
let el = dFlex;
for (let i = 0; i < 15; i++) {
  if (el?.__vue__) break;
  el = el?.parentElement;
}
const comp = el.__vue__; // action-cell component (outer wrapper)
const innerComp = comp.$children[0]; // announcementsActions (inner component with click handlers)

// Navigate to SVG children
const innerVn = innerComp._vnode;
const divChild = innerVn.children[0]; // div.d-flex
const svgChildren = divChild.children;
// svgChildren[0] = edit icon (if canEdit=true)
// svgChildren[1] = eye SVG (width=22, v-b-modal → announcement-data-modal)
// svgChildren[2] = trash SVG (width=11, v-b-modal → deleteAnnouncementConfirmModal)

const mockEv = { target: document.body, preventDefault: ()=>{}, stopPropagation: ()=>{} };

// Click eye icon:
svgChildren.find(c => c?.data?.attrs?.width == '22')?.data?.on?.click?.(mockEv);

// Click trash icon:
svgChildren.find(c => c?.data?.attrs?.width == '11')?.data?.on?.click?.(mockEv);
```

### Close any open modal
```javascript
root.$emit('bv::hide::modal', 'announcement-data-modal');
root.$emit('bv::hide::modal', 'deleteAnnouncementConfirmModal');
```

### Find announcements parent component
```javascript
let announcementsComp = null;
function findComp(comp, depth=0) {
  if (depth > 10) return;
  if ((comp.$options.name || '') === 'announcements') { announcementsComp = comp; return; }
  comp.$children?.forEach(c => findComp(c, depth+1));
}
findComp(root);
// Access: announcementsComp.$refs['deleteAnnouncementConfirmModal'] → BModal instance
```

### Viewport shrink issue
If the viewport shrinks to 618×158 (happens when filter popups interact with browser):
1. Call `resize_window(1920, 1080)`
2. If `window.innerWidth` still shows 618, navigate to the page and call resize again
3. Viewport should restore to ~1549×736 or ~1707×811

### CDP Freeze Pattern
Every button click (Export, Columns, Clear filters, column header click) triggers a ~30s CDP renderer freeze.  
Pattern:
1. Click or JS action
2. Screenshot → timeout error (30s)
3. Wait 10 seconds (`computer.wait(10)`)
4. Screenshot again — usually succeeds

---

## Bugs Found

| # | Bug | Severity | Confidence |
|---|-----|----------|------------|
| 1 | Eye icon: Announcement Preview modal invisible — Bootstrap `show` CSS class never added | Critical | High |
| 2 | Trash icon: Delete Announcement modal invisible — same Bootstrap `show` CSS class bug | Critical | High |
| 3 | "Clear filters" button does not clear text search column inputs | Major | High |
| 4 | "Clear filters" button does not disappear after all filters are cleared | Minor | High |

### Bug 1 & 2 — Bootstrap modal fade transition not completing
**Steps to reproduce:**
1. Go to `https://visipoint.uk/announcements`
2. Scroll right to see the Actions column
3. Click the eye icon (or trash icon) on any announcement row
4. Observe: dark backdrop may briefly appear but no modal content is visible

**Expected:** Modal dialog appears with content (Announcement Preview or Delete Confirmation)  
**Actual:** Modal is in the DOM (verifiable via DevTools: `document.querySelector('.modal')`) but has no `show` class — opacity: 0, invisible to the user

### Bug 3 — Clear filters does not clear search inputs
**Steps:**
1. Type anything in the Title search box
2. "Clear filters" button appears
3. Click "Clear filters"
4. Observe: Title search box still contains the typed text; grid still filtered

**Expected:** All active filters/searches are cleared  
**Actual:** Text in search boxes is NOT cleared; grid stays filtered

### Bug 4 — Clear filters button stays visible when nothing is filtered
**Steps:**
1. Apply a sort (click any column header)
2. Click "Clear filters" — sort is cleared (grid returns to default order)
3. Observe: "Clear filters" button is still visible even though no filters are active

**Expected:** "Clear filters" button disappears when no filters are active  
**Actual:** Button remains visible permanently once it has appeared

---

## UPDATE (2026-07-01) — Regression Retest

All 4 previously filed bugs are **CONFIRMED FIXED**:
- Bug 1 (eye icon preview modal invisible) — modal now renders fully visible with content.
- Bug 2 (trash icon delete-confirm modal invisible) — modal now renders fully visible.
- Bug 3 (Clear filters doesn't clear text search) — confirmed cleared.
- Bug 4 (Clear filters button stays visible) — button now disappears correctly.

The Bootstrap modal fade transition issue appears to have been resolved platform-wide (see Users module — same fix pattern observed there for its Delete modal).

Full details in `D:\Visipoint md files\Multi_Module_Testing_Report_2026-07-01.md`.

---

## What Was NOT Fully Tested

- Edit Announcement form — confirmed navigation to `/add-announcement?id=...` in the past; no `canEdit` row was available in the 2026-07-15 session to retest pre-fill
- Delete Announcement confirm flow — opened modal + tested Cancel; did NOT click Delete (to preserve test data)
- Export to Excel/CSV/PDF — confirmed options exist, did not trigger actual download
- Image / Video insert buttons in the rich text editor — likely opens a native OS file picker; not clicked
- Multiple rows in grid — only 1 row exists in this environment; pagination behavior with many rows still untested
- Add Announcement form — never submitted end-to-end (fields filled and verified individually, no test announcement created)

---

## Session Update — 2026-07-15 (Full Field/Button Walkthrough + API Performance)

**Full report:** `Announcements_Testing_Report_2026-07-15.md`

- **All 4 previously-filed bugs re-confirmed fixed**, including the two Critical Bootstrap modal-invisibility bugs (eye icon preview, trash icon delete-confirm) — both now render fully visible via a plain native click, no JS workaround or CDP freeze needed.
- **Verbal column filter tested** (previously untested) — works correctly.
- **Full Add Announcement form tested**, including previously-untested areas: rich text editor (Bold/Italic/Underline/color/alignment/link tool all work; image/video buttons not clicked — likely native file picker), custom Publish/Expiry date and time pickers (calendar + hour/minute/AM-PM scrollers, both work), Area target dropdown (expandable site/area tree).
- **NEW finding (not a bug):** the **"Verbal announcement" checkbox only appears when Announcement target = Area** (hidden under User type target) — not previously documented. Likely intentional (verbal announcements are read at a physical area/kiosk) but worth flagging to product if not already known.
- **API baselines captured** (host `api.visipoint.uk`): `announcements` (grid data) avg 741ms (n=3), `user-types` avg 422ms (n=3), `check_active_sessions` avg 393ms (n=3), `adminPreference` avg 385ms (n=3). No privacy-redacted timing entries this session.
- **No data was mutated:** Add form filled but never submitted; Delete modal opened and cancelled (only announcement in the environment).

---

## Session Update — 2026-08-01 (Full Coverage: Add + Delete End-to-End, Entity Change)

**Full report:** `Announcements_Testing_Report_2026-08-01.md`

**Environment note:** This session's dashboard login landed on **"UK (TESTING02)"**, not "UK (Testing)" used by every prior session. At session start, the `visipoint.uk` session had expired and redirected to the Passport product (`visipoint.me`) instead of the dashboard — had to click back into a company entity ("UK (Testing)" was clicked but the resulting dashboard session shows "UK (TESTING02)" in the sidebar, possibly a mapping/redirect quirk worth another look if it recurs). This tenant conveniently has **5 real pre-existing announcements** (vs. the single-row environment used in all prior sessions), which finally allowed proper multi-row Actions/pagination-adjacent testing. All findings below are against UK (TESTING02); re-verify against UK (Testing) in a future session if the two diverge.

**Previously-fixed bugs 1–4 (Bootstrap modal fade transition + Clear filters) all RE-CONFIRMED FIXED:**
- Eye icon → Announcement Preview modal renders fully visible immediately on a real click. Tested against a row with Urgent=Yes/Pinned=Yes.
- Trash icon → Delete Announcement modal renders fully visible on a real click (see automation gotcha below — this took several retries due to an automation-specific issue, not a product bug).
- Clear filters correctly clears the Title text search box and the button correctly disappears once no filters remain — both re-verified with a fresh "Test" search that filtered 5→2 rows, then cleared back to 5.

**NEW — Full Add Announcement flow tested end-to-end for the first time (previously only field-by-field, never submitted).** Filled Title, Announcement body (rich text), left Urgent/Pinned off, User type target = "Staff" (pre-filled default), Publish = Immediately, Expiry = Never, clicked Add. Result: green "Announcement created successfully." toast, redirected to the grid, new row appeared correctly at the top with the right Title/User type/Status=Active/Publish Time (matching submit time). **Works correctly — no bugs found in this flow.**

**NEW — Full Delete flow tested end-to-end for the first time on both paths (previously only Cancel, to preserve the single real row that existed).** Since a fresh test announcement was created specifically for this, it could safely be deleted: clicked trash icon → confirmed modal text was correct and referenced the right row (verified via DOM row-order check, not just visual proximity) → clicked Delete → row disappeared, grid count returned to the original 5. **Works correctly — no bugs found in this flow.**

**Observation, not filed as a bug:** the Announcement Preview modal (eye icon) shows an "Urgent" badge and a User Type badge (e.g. "Staff") when applicable, but does **not** show any "Pinned" indicator even when the announcement's Pinned column is "Yes" in the grid. Possibly intentional (Pinned may only affect display ordering/placement elsewhere, not worth a badge in a preview), but worth a quick product check since Urgent gets a badge and Pinned doesn't, despite both being toggle fields on the Add form.

**Automation gotchas learned this session (important for future sessions on this module):**
1. **The documented vnode-click-handler pattern (`svgChildren.find(...).data.on.click(mockEv)` with `mockEv.target = document.body`) does NOT open the eye/trash modals in this session** — tried twice, produced zero DOM effect (no modal element created at all, no loadpanel, nothing). A **real coordinate click** on the icon is required instead. Not fully understood why this regressed from working in earlier sessions — possibly a Vue/directive version change, possibly environment-specific (UK (TESTING02) vs UK (Testing)). If vnode-click stops working again, fall back to a real `computer` click on precise coordinates.
2. **Screenshot images are returned scaled down from the real viewport** — in this session `window.innerWidth`/`innerHeight` was 1920×842 (or 1960×860 at another point — it fluctuated) while screenshots came back at 1568×688. Clicking at coordinates read directly off a screenshot can miss small targets like the 11px-wide trash icon. Reliable pattern: get the real element's `getBoundingClientRect()` via JS, multiply by `(screenshotWidth / window.innerWidth)` and `(screenshotHeight / window.innerHeight)`, then click the scaled coordinate with the `computer` tool.
3. Once misfired, a stray `.dx-loadpanel` overlay can get stuck visible for 20+ seconds with no underlying network request (confirmed via `read_network_requests` — nothing in flight). This resolved itself eventually and was not reproducible on a clean isolated retry — likely triggered by firing two clicks in quick succession (closing one modal immediately followed by clicking a delete icon). Space out actions and verify each one's result before firing the next when working on this grid.

---

## Session Update — 2026-08-06 (QA environment, "QA testing" entity — BUG-ANN-001 confirmed, full Add-form field validation mapped)

**Full report:** `Announcements_Testing_Report_2026-08-06.md`

**Environment:** `qa.app.d.visipoint.dev`, logged in via `appqa.visipoint.me`, entity "QA testing" (0 pre-existing announcements — clean grid).

### BUG-ANN-001 (Critical/High) — Add Announcement always fails, HTTP 422 "The selected server name is invalid."
**Filed as [CL-17913](https://lamasatech.atlassian.net/browse/CL-17913)** — Sub-bug of CL-17095, assignee Moataz Khaled, priority High.

Every single Add Announcement submission on this entity fails — `POST https://qa.api.d.visipoint.dev/api/announcement` returns **HTTP 422** with error toast text **"The selected server name is invalid."** No announcement is ever created.

**Reproduced 4/4 attempts, across 2 separate page sessions**, isolating one variable at a time to rule out a specific-field cause:
1. Full form (Title, Body, URL="https://example.com", Urgent=on, Pinned=on, User type target, custom Publish/Expiry dates) → 422.
2. Same, URL field cleared to empty → still 422 (ruled out URL as cause).
3. Simplest possible valid form (Title, Body, User type target, Publish=Immediately, Expiry=Never, no URL, toggles off) → still 422.
4. Fresh page load, fresh minimal form, different Title/Body text → still 422.

**Not a form-validation bug** — the Add button correctly enables (client-side validation passes) before every attempt; the failure is purely server-side. The error text references a "server name," a concept never exposed anywhere in the Add Announcement UI — this reads as a backend misconfiguration (possibly entity/tenant-level: this "QA testing" entity is a device/kiosk "server" registration issue) rather than a real validation failure. Same *error shape* (cryptic 422, wording unrelated to any visible field) as CL-17855 (Survey submit, tested earlier the same day) — worth a shared-root-cause check, not confirmed.

**Not yet confirmed:** whether this is specific to the "QA testing" entity or affects the whole QA environment. A same-session comparison against a second QA entity (e.g. "QA testing02") was attempted but inconclusive — the company-entity picker on `appqa.visipoint.me/dashboard` has a redirect quirk (same pattern documented for `visipoint.uk` on 2026-08-01: clicking one entity row can land you on a different entity's dashboard) that made a clean isolated comparison unreliable in the time available. **Do not assume platform-wide** — file/escalate scoped to "QA testing" until a second entity is cleanly verified.

**Blocks downstream testing:** Edit, Delete, and row-Actions testing could not be completed this session since no announcement could ever be successfully created to act on (grid stayed empty throughout).

### Full Add-form field-level validation map (new, not previously documented this precisely)

Unlike the Add Visits module's platform-wide "silent submit on empty required fields" pattern, **the Add Announcement form has real client-side gating** — the Add button's DOM `disabled` attribute is `true` until requirements are met:
```javascript
Array.from(document.querySelectorAll('button')).find(b => b.textContent.trim() === 'Add')?.disabled
```
- **Title**: required (button disables when empty) — but **accepts whitespace-only input** (`"   "` still enables the button, no trim check). Minor gap, not filed as a bug.
- **Announcement body**: required.
- **Announcement target** (User type or Area, at least one value selected): required. User type field is a multi-select checkbox dropdown (not previously documented as multi-select).
- **URL**: labeled "(optional)" but has **genuine real-time inline format validation** — an invalid value (e.g. `not-a-valid-url`) shows a red border + "Invalid URL." message AND disables the Add button, even though the field itself can be left empty. This is one of the few properly-validated free-text fields across the whole app (same tier as Company Details' Subdomain field).
- **Publish date/time (Custom date and time)**: the calendar genuinely disables all past dates (can't even click them — no-op). For today's date specifically, the time picker also disables past hours/AM-PM combinations relative to the real current time (confirmed by clicking a greyed-out past hour and past date — value stays empty both times). For a future date, all 24 hours are selectable (a bold/highlighted hour is just the default suggestion, not a disabled-vs-enabled indicator — verified by clicking a non-bold hour and confirming it registers).
- **Expiry date/time (Custom date and time)**: enforces **Expiry ≥ Publish** as a real cross-field constraint, not just "≥ today" — confirmed by setting Publish to a future date (Aug 9) and observing the Expiry calendar's minimum-selectable date shift to match (days before Aug 9, including Aug 6-8, all become unclickable). Confirmed at the time level too: with Publish = 01:26 PM on Aug 9, the Expiry time picker on the same day disables the "AM" period entirely (click no-ops) and only allows hours/minutes at or after the Publish time. This is the most rigorously-implemented date/time validation found in any module tested so far.
- **Urgent / Pinned toggles**: cosmetic booleans, no validation, toggle freely.

### Automation gotcha (new)

**A plain `button.click()` via `javascript_tool` does NOT reliably trigger this form's real Vue submit handler** — confirmed via `read_network_requests`: the JS-dispatched click produced zero `POST /api/announcement` calls (page stayed put, no error, no loading state), while a real `computer` coordinate click on the same button immediately fired the POST. This is a different (but same-flavor) gotcha to the already-documented eye/trash-icon vnode-click issue — for the Add/Edit Announcement submit button specifically, always use a real coordinate click, not a JS `.click()` call.
