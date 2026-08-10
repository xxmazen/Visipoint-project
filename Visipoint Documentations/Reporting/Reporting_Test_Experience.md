# Reporting Module — Test Experience & Knowledge Base

**Module URL:** `https://visipoint.uk/reporting/`  
**Last Tested:** 2026-06-19  
**Tester:** Claude (automated browser testing via Chrome MCP)  
**Sections Tested:** All 8 sub-sections (History, Visit Summary, Timesheet, Users not on site, Track and Trace, Export List, Kiosk Logs, Print List)

> Read this file before testing the Reporting module again. It captures page structure, feature behavior, automation patterns, and bugs for each sub-section.

---

## General Notes

### CDP Renderer Freeze Pattern
Every button click on visipoint.uk causes a 30–45 second CDP renderer freeze. The pattern:
1. Click or JS action
2. Screenshot → times out with "renderer frozen" error
3. `wait(10)` seconds
4. Retry screenshot — usually succeeds

### DxDataGrid ("Clear filters" Systemic Bug)
All Reporting grids use DevExtreme DxDataGrid. The **"Clear filters"** button (red outline, appears in toolbar) has a systemic bug across ALL pages:
- Clicking "Clear filters" does NOT clear text search inputs in the filter row
- The button's visibility state does not properly track text input filter state — it can disappear while text filters are still active (e.g., after a header filter popup is opened/closed)
- This affects: Users not on site, Track and Trace, Export List, Kiosk Logs, Print List

---

## 1. History

**URL:** `https://visipoint.uk/reporting/history`

### Page Structure
- Date range filter at top (start date + end date)
- DxDataGrid with visitor history records
- Columns button + Export button (Excel/CSV/PDF)
- Text search inputs in filter row
- Header filter (≡) on applicable columns
- Pagination: 5, 10, 25, 50, 100

### Behavior
- Filter by date range to load history records
- All standard DxDataGrid features apply (column search, sorting, export)
- No bugs found during testing

---

## 2. Visit Summary

**URL:** `https://visipoint.uk/reporting/visit_summary` (underscore, not hyphen — corrected 2026-08-01, the hyphenated form 404s)

### Page Structure
- mx-datepicker dual-month range picker for date selection
- Site filter dropdown
- DxDataGrid with summarized visit data
- Columns button + Export button

### Behavior
- Select date range and site, then apply to see results
- Standard DxDataGrid features apply
- No bugs found during testing

---

## 3. Timesheet

**URL:** `https://visipoint.uk/reporting/timesheet`

### Page Structure
- **Filter form** (default view — not the grid):
  - Year dropdown (required)
  - Month dropdown (required)
  - Site dropdown (required)
  - User Type dropdown (required, but **disabled until Site is selected**)
  - Generate button (GlobalButton — disabled until all 4 fields filled)
- After clicking Generate → shows **pivot grid** with timesheet data

### Filter Form Behavior
- Year, Month, Site dropdowns are independent and always enabled
- User Type dropdown is **grayed out** with no label/tooltip explaining why — only becomes enabled after a Site is selected
- Generate button shows `disabled` class when any required field is empty
- Generate button becomes active only when Year + Month + Site + User Type are all selected

### Export Options
- Timesheet Export only shows: **Excel** (no CSV or PDF)
- This is different from all other Reporting pages which offer Excel + CSV + PDF

### JS Patterns
```javascript
// Check Generate button state
const btn = Array.from(document.querySelectorAll('button')).find(b => b.textContent.trim() === 'Generate');
btn.classList.contains('disabled'); // true when fields missing
```

### Bugs Found
- **UX Bug:** User Type dropdown appears disabled (grey) with no tooltip or label explaining it is dependent on Site selection. Users may believe the field is permanently disabled. See Bug Report #1.

---

## 4. Users Not on Site

**URL:** `https://visipoint.uk/reporting/users-not-on-site`

### Page Structure
- **Filter form** (top):
  - Date picker (required)
  - Site dropdown (required)
  - User Type dropdown
  - Sign In button (action, not filter — signs in a user directly)
  - Update data button
  - Generate button (disabled until Date + Site filled)
- After generating → DxDataGrid with users not on site
- **184 records** in the test environment

### Filter Form Behavior
- Sign In button: Triggers a sign-in action for the selected user directly from this page
- Update data button: Refreshes the data from the backend
- Generate button stays disabled until required fields are filled

### Grid Features (after generating)
- Columns: multiple user columns
- Columns button, Export dropdown (Excel/CSV/PDF)
- Text search inputs in filter row
- Header filters (≡) on applicable columns
- Pagination: 5, 10, 25, 50, 100

### "Clear filters" Bug
Confirmed on this page — text search inputs not cleared. See Bug Report #3.

### Performance Bug
- Export button on this page with 184 records caused a **60+ second browser renderer freeze** — the page became completely unresponsive. Navigation to next page even failed. Requires browser extension reconnect.
- See Bug Report #2.

---

## 5. Track and Trace

**URL:** `https://visipoint.uk/reporting/track-trace`

### Page Structure
- **User** dropdown (vue-multiselect — single select)
- **Date** range picker (mx-datepicker)
- **Filter** button
- DxDataGrid with trace records (initially hidden — shows only after Filter is clicked)
- Columns button + Export (Excel/CSV/PDF)

### Filter Behavior
- User only (no date) → Shows grid, "No data yet" in test env ✓
- Date only (no user) → Shows grid, "No data yet" in test env ✓
- Both user + date → Shows grid, "No data yet" in test env ✓
- **Neither user nor date** → Filter button does nothing — no validation feedback (Bug #4)

### Grid Columns (8 total)
| Column | Filter Type |
|--------|-------------|
| First Name | Text search |
| Last Name | Text search |
| User Type | Header filter (≡) |
| Input Type | Text search |
| Device | Text search |
| Site | Text search |
| Area | Text search |
| Date | Text search |

### Pagination
5, 10, 25, 50, 100 ✓

### JS Patterns
```javascript
// Find trackAndTrace Vue component to set filter data
const ms = document.querySelector('.multiselect');
let el = ms;
for (let i = 0; i < 20; i++) {
  el = el.parentElement;
  if (!el) break;
  if (el.__vue__ && el.__vue__.$options.name === 'trackAndTrace') {
    const comp = el.__vue__;
    // comp.user = selected user object
    // comp.date = ['2026-06-01', '2026-06-19']
    // comp.usersOptions = all available users
    break;
  }
}

// Select user via Vue multiselect
const faresOption = comp.usersOptions.find(o => o.fullName === 'Fares Test');
comp.user = faresOption;

// Clear user selection
comp.user = null;

// Clear date
comp.date = [];
```

### Bugs Found
- **Bug #4:** Filter button with no User and no Date selected → silently does nothing (no validation message, no toast, no error)
- "Clear filters" systemic bug confirmed here

---

## 6. Export List

**URL:** `https://visipoint.uk/reporting/export-list`

### Page Structure
- DxDataGrid listing past export records generated from any Reporting page
- Toolbar: **Columns** button + **Export** dropdown (Excel/CSV/PDF)
- No date range filter or generate form — loads all records directly

### Grid Columns (6 total)
| Column | Filter Type |
|--------|-------------|
| Requested By | Text search |
| Generation Date | Text search + Header filter (≡) |
| Expiry Date | Text search + Header filter (≡) |
| Grid | Header filter (≡) only |
| Type | Header filter (≡) only |
| Actions | Action icons (download/delete) |

### Behavior
- No records in test environment during testing
- Header filters for Grid and Type show "No data to display" when no records exist
- "Clear filters" systemic bug confirmed

### Pagination
Standard: 5, 10, 25, 50, 100 ✓

---

## 7. Kiosk Logs

**URL:** `https://visipoint.uk/reporting/kiosk-logs`

### Page Structure
- DxDataGrid with kiosk activity logs
- Toolbar: **Columns** button + **Export** dropdown (Excel/CSV/PDF)
- No date range filter — loads all records directly

### Grid Columns (7 total)
| Column | Filter Type |
|--------|-------------|
| Kiosk Name | Text search + Header filter (≡) |
| Requested By | Text search + Header filter (≡) |
| Date | Text search + Header filter (≡) |
| Type | Text search + Header filter (≡) |
| Status | Text search + Header filter (≡) |
| Reason of failure | Header filter (≡) only — **no text search input** |
| Actions | Action icons |

**Note:** "Reason of failure" has a header filter icon but no text search input in the filter row — inconsistent with the other columns.

### Behavior
- No data in test environment during testing
- "Clear filters" systemic bug confirmed

### Pagination — BUG
**Only 3 page size options: 5, 10, 50** — missing 25 and 100. All other Reporting pages use 5, 10, 25, 50, 100. See Bug Report #5.

---

## 8. Print List

**URL:** `https://visipoint.uk/reporting/printer`

### Page Structure
- DxDataGrid with print job records
- Toolbar: **Columns** button + **Export** dropdown (Excel/CSV/PDF)
- No date range filter — loads all records directly

### Grid Columns (7 total)
| Column | Filter Type |
|--------|-------------|
| User Name | Text search only |
| Printer | Header filter (≡) only |
| Type | Header filter (≡) only |
| Print Status | Header filter (≡) only |
| Expired Date | Header filter (≡) only |
| Executed Date | Header filter (≡) only |
| Actions | **Always empty — no icons rendered** |

**Note:** Only 1 text search input (User Name). All other columns use header filters only.  
**Note:** "Actions" column header exists in grid but contains no buttons, icons, or links for any row (`innerHTML: <!---->`). May be intentional for this read-only log.

### Test Data
1 record found: `trst gfhg | Kiosk | - | Print | 12/05/2026 - 03:58:46 PM | 12/05/2026 - 03:53:56 PM`

### Header Filter Behavior (with data)
- **Printer** → Shows "Kiosk" option ✓ (matches data row)
- **Print Status** → Shows "Print" option ✓ (matches data row)

### Pagination — BUG
**Page size options: 50, 100, 150, 200** — completely different from all other pages. No small sizes (5, 10, 25) available. Users with small datasets are forced to use 50 minimum. See Bug Report #6.

### "Clear filters" systemic bug confirmed

---

## Session Update — 2026-08-02 (Regression Pass, Tenant "UK (Testing)")

Quick full 8-page regression pass, one session after the 2026-08-01 full-coverage session. No new findings — every documented behavior re-confirmed exactly as-is, zero movement.

**Re-confirmed present (4th session running for the 3 pagination bugs, zero movement 2026-06-19 → 2026-07-15 → 2026-08-01 → 2026-08-02):**
- Kiosk Logs pagination: only 5/10/50, missing 25/100.
- Print List pagination: only 50/100/150/200, no small sizes. Actions column still empty for both records shown.
- Users not on site pagination: still 50/100/150/200. Dataset now at 185 records (was 184 on first freeze report, 6 on 2026-08-01) — deliberately did not trigger Export given the 100+ record freeze risk.
- Export List toolbar still has only "Columns", no Export button (confirmed via DOM query again) — 2nd session showing this, still not confirmed intentional vs. regression.
- Timesheet: User Type dropdown confirmed still disabled until Site is selected, then becomes enabled — re-verified interactively (selected UK (Testing) site, watched User Type unlock). Export still Excel-only.
- Track and Trace: re-confirmed selecting User only (no Date) does NOT show the grid — "Select a user and date range to show information." message persists until both are filled. Grid appeared correctly once both User (Jorden Wilde) and a date range were set (0 results for that user, as expected).
- History: loads fine, Temperature Unit °F/°C toggle still present, standard pagination.
- Visit Summary: loads fine, Site column header-filter still present (not a separate toolbar dropdown), standard pagination, date range picker works correctly.

**Tenant note:** this session logged in directly to "UK (Testing)" (not "UK (TESTING02)" like 2026-08-01) — no session-expiry redirect this time.

**Recommendation unchanged:** the three pagination bugs have now shown zero movement across 4 sessions — still worth escalating to a real ticket.

---

## Summary: Pagination Sizes Across Reporting Pages

| Page | Page Size Options | Status |
|------|-----------------|--------|
| History | 5, 10, 25, 50, 100 | ✓ Standard |
| Visit Summary | 5, 10, 25, 50, 100 | ✓ Standard |
| Timesheet | N/A (pivot grid) | — |
| Users not on site | **50, 100, 150, 200** (was standard as of 2026-06-19; changed by 2026-07-15) | ❌ Wrong set entirely (NEW as of 2026-07-15) |
| Track and Trace | 5, 10, 25, 50, 100 | ✓ Standard |
| Export List | 5, 10, 25, 50, 100 | ✓ Standard |
| Kiosk Logs | **5, 10, 50** | ❌ Missing 25, 100 (confirmed still present 2026-07-15) |
| Print List | **50, 100, 150, 200** | ❌ Wrong set entirely (confirmed still present 2026-07-15) |

---

## Summary: Export Options Across Reporting Pages

| Page | Export Options |
|------|--------------|
| History | Excel, CSV, PDF |
| Visit Summary | Excel, CSV, PDF |
| Timesheet | **Excel only** |
| Users not on site | Excel, CSV, PDF |
| Track and Trace | Excel, CSV, PDF |
| Export List | Excel, CSV, PDF |
| Kiosk Logs | Excel, CSV, PDF |
| Print List | Excel, CSV, PDF |

---

## UPDATE (2026-07-01) — Regression Retest

- **Systemic "Clear filters" bug (all DxDataGrid pages): CONFIRMED FIXED.** Verified on Export List — typing a text filter shows the "Clear filters" button; clicking it now clears the text input correctly AND the button disappears once no filters remain. Previously the button did neither.
- **Track and Trace — Filter button with no User/Date selected: CONFIRMED FIXED.** The page now shows an inline helper message, "Select a user and date range to show information.", both before and after clicking Filter with nothing selected — no more silent no-op.
- Timesheet's User Type dropdown greying out until a Site is selected is confirmed **intentional by-design behavior**, not a bug — do not re-flag.
- Kiosk Logs (5/10/50 page sizes) and Print List (50/100/150/200 page sizes) pagination inconsistencies were **not retested** this pass — still noted as a known cosmetic issue from the prior report; needs a dedicated follow-up if a full Reporting re-test is scheduled.
- Users not on site → Export performance freeze (60+ second renderer freeze on 184 records) was **not retested** this pass — intentionally avoided to prevent repeating the freeze/extension-disconnect.
- Login flow note: reaching Reporting pages requires going through the Passport SSO redirect first (`visipoint.uk/login` → `visipoint.me` → back to `visipoint.uk` via `/sso?id=...`) when a Passport session is already active in the browser — no manual credential entry needed in that case.

Full regression details across all modules: `D:\Visipoint md files\Multi_Module_Testing_Report_2026-07-01.md`.

---

## What Was NOT Fully Tested

- **Users not on site**: Sign In button (did not actually sign in a user); Export (caused a 60+ second performance freeze in the original 2026-06-19 session — deliberately not retriggered since)
- **Export List**: No records in test env (still, as of 2026-07-15) — Actions column (download/delete icons) not tested
- **Kiosk Logs**: No records in test env — Actions column not tested; Type/Status header filter values not seen

---

## Session Update — 2026-07-15 (Full Field/Button Walkthrough + API Performance)

**Full report:** `Reporting_Testing_Report_2026-07-15.md`

- **Pagination bugs (Kiosk Logs 5/10/50, Print List 50/100/150/200) — CONFIRMED STILL PRESENT**, not fixed. Also newly found on **Users Not on Site**, which now uses the same 50/100/150/200 set instead of the documented standard 5/10/25/50/100 — this looks like a deliberate pattern for large-dataset pages rather than an isolated Print List issue, worth flagging to product as a UI-consistency question rather than 3 separate bugs.
- **Track and Trace Bug #4 fix re-confirmed**: the "Select a user and date range to show information." message now shows proactively on page load (previously only appeared after clicking Filter with nothing selected).
- **Clear Filters fix re-confirmed on every page tested** (History, Users Not on Site, Track and Trace).
- **Timesheet has changed significantly**: default view is now a cached pivot grid + "Re-generate" button (not the filter form). Month is a multi-select checkbox list, not a single dropdown. Generate is now async ("You will receive an email once ready") though it resolved in seconds in this session. Export remains Excel-only.
- **Users Not on Site "Change criteria" form simplified**: now just Site + User Type, no Date field (previously Date + Site were both required). New helpful "?" tooltips added.
- **Visit Summary's "Site filter dropdown" is now a header filter** on the Site grid column, not a separate toolbar dropdown.
- **History no longer has a visible date-range filter** in the toolbar — loads all records directly, relies on grid filters.
- **Export List showed no records even after generating a Timesheet report earlier the same session** — suggests "Generate" (async report) and "Export" (grid export) are tracked/logged separately.
- **Print List Actions-always-empty behavior confirmed consistent** across 2 records now (was only 1 before) — likely by-design for this read-only log.
- **API baselines captured** (host `api.visipoint.uk`): `get_history_data_grid` avg 1034ms (n=2), `print_queue_list` 1734ms (n=1), `check_active_sessions` avg 1007ms (n=2), `adminPreference` avg 798ms (n=5). Same first-load-slow, subsequent-loads-fast pattern seen in other modules today.
- **Did not retrigger** Users Not on Site → Export (known freeze risk) or click Sign In.

---

## Session Update — 2026-08-01 (Full 8-Page Coverage, Tenant "UK (TESTING02)")

**Full report:** `Reporting_Testing_Report_2026-08-01.md`

**Doc correction:** Visit Summary's real URL is `/reporting/visit_summary` (underscore) — this file previously said `/reporting/visit-summary` (hyphen), which 404s. Always confirm via the sidebar link's `href` if direct navigation 404s, per the skill's general navigation guidance.

**Re-confirmed still present (3rd session running, zero movement across 2026-06-19 → 2026-07-15 → 2026-08-01):**
- Kiosk Logs pagination: only 5/10/50, missing 25/100.
- Print List pagination: only 50/100/150/200, no small sizes.
- Users not on site pagination: also 50/100/150/200 (changed from standard as of 2026-07-15).
- Timesheet User Type field greyed out until Site selected — by design, not a bug.
- Timesheet Export is Excel-only.
- Track and Trace's proactive "Select a user and date range to show information." message on page load.

**NEW — Export List toolbar no longer has an Export button, only Columns.** Confirmed via DOM query (`.dx-toolbar button, .dx-dropdownbutton` → only "Columns" found), not just a visual miss. Every prior session documented an Export (Excel/CSV/PDF) button here. Likely an intentional removal (exporting a list of past exports is a confusing, self-referential feature) but not confirmed with product — flag before assuming either way.

**NEW — Track and Trace may now require BOTH User and Date before showing a grid**, not either alone. Original 2026-06-19 finding was that "User only" (no date) showed a grid with "No data yet." This session, selecting only a User and clicking Filter kept showing the "select a user and date range" message; only after also filling a Date range did the grid appear. The message text itself says "user **and** date range," so this reads as a deliberate validation tightening, not a regression — but note the behavior change for future sessions so it isn't mistaken for the older Bug #4 recurring.

**NEW — Kiosk Logs Actions column tested for the first time with real data (previous sessions always had 0 records).** Shows "-" (no icons rendered) for the one record present, matching Print List's already-documented "Actions column always empty" pattern. Reinforces that this is likely a deliberate read-only-log design shared by both pages, not a one-off gap.

**Users not on site — Export retested at small scale (6 records in this tenant, vs. 184 in the original freeze report) with no freeze.** Dropdown (Excel/CSV/PDF) opened instantly. Did not attempt the large-dataset freeze reproduction since this tenant doesn't have enough records — supports (but doesn't prove) that the original 60+s freeze bug is data-volume-dependent rather than universal. Still avoid triggering Export on any dataset in the 100+ record range without deliberately accepting the freeze/reconnect risk.

**New/previously undocumented:** History page's toolbar now shows a "Temperature Unit °F/°C" toggle in the top-right — not mentioned in any earlier session.

**Recommendation:** the three pagination bugs (Kiosk Logs, Print List, Users not on site) have shown zero movement across 3 sessions — worth escalating to a real ticket rather than continuing to log as "known, needs follow-up."
