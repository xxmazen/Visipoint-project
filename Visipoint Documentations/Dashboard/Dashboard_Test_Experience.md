# Dashboard — Test Experience & Knowledge Base

**Page URL:** `https://visipoint.uk/` (root — NOT `/dashboard`, that 404s)
**Last Tested:** 2026-08-08
**Tester:** Claude (automated browser testing via Chrome MCP)

> Read this file before testing the Dashboard again. It captures everything learned during testing — page structure, feature behavior, automation notes, and bugs found.

---

## CORRECTION (2026-08-09) — "BUG-DASH-002" was a false positive, not a bug

The 2026-08-08 session below originally logged a "BUG-DASH-002": bulk/per-row "Sign in/out" never checking an already-checked-in visitor out. **The user confirmed this is intentional logic, not a defect** — re-running the sign-in action on a visit that's already Checked in, selecting the same site+area, is expected to leave the status as Checked in (it does not toggle to Checked out). This has been retracted from the bugs list below and moved to "Intentional Design Decisions." **BUG-DASH-001 (sorting) is unaffected by this correction and remains an open bug.**

## Session Update — 2026-08-08 (P0/P1 smoke test, production `visipoint.uk`, "UK (Testing)" tenant)

**Scope:** P0/P1 smoke test — grid load, display, search, date-range/filters, sorting, pagination, Quick Sign In, bulk Sign in/out, grid stability. Not exhaustive.

**Result: 1 confirmed bug found (sorting). The bulk-sign-out finding originally logged here was retracted — see correction note above. Everything else passed.**

### BUG-DASH-001 (High) — Dashboard grid column sorting does not work
Clicking a sortable column header (confirmed on both **First Name** and **Last Name**) shows the sort arrow indicator and updates the DevExtreme grid's internal `sortOrder` state correctly (`asc`/`desc`, confirmed via `dxGrid.instance.columnOption('FirstName', 'sortOrder')`), but **the displayed row order never actually changes** — it stays identical to the unsorted (Arrival Time descending) order regardless of which column or direction is selected.

**Repro steps:**
1. Widen the date range (default is today-only) so multiple rows are visible — e.g. `01/06/2026 ~ 08/08/2026`.
2. Note the row order (unsorted, by Arrival Time descending).
3. Click the "First Name" column header once (sorts ascending, arrow appears).
4. Observe: row order is byte-identical to step 2 — NOT alphabetically sorted.
5. Click again (sorts descending, arrow flips). Row order is still byte-identical.
6. Repro'd again with "Last Name" ascending — same result.

**Verified via 3 independent methods, all consistent:** real UI header clicks (with 6+ second waits to rule out timing/debounce), `dxGrid.instance.columnOption()` API calls, and `dxGrid.instance.clearSorting()` baseline comparison. All produced the exact same row order regardless of sort state. No network requests fire for sort changes (this grid's 24-item dataset for the widened range loads once and sorts client-side — or should — confirming this is a client-side rendering bug, not a server-side one).

**Not the same bug as the documented Users-grid "Visipoint Passport column sort → HTTP 400" bug** — this is a different grid, a different (silent, non-error) failure mode, and reproduces on ordinary columns (First Name, Last Name), not a specific problem column.

### Everything else — confirmed working
- Grid loads correctly at `visipoint.uk/` (root).
- Columns/headers render correctly (21 available columns via Columns chooser, matches prior docs).
- **Default date range is today-only** (re-confirms existing documented behavior) — widening it via the dual-calendar date-range picker works correctly (tested `01/06/2026 ~ 08/08/2026`, correctly loaded 24 historical records).
- First/Last Name search boxes filter correctly within the active date range (confirmed via exact-match "Maged" → 1 result; note a debounce delay of a couple seconds before the filtered count updates — don't read the count too early in automation).
- Pagination (page size change 25→10, page navigation to page 2) works correctly — first-row content changed appropriately. (Needed a few seconds to settle after triggering — same debounce-timing lesson as search.)
- **Quick Sign In wizard works correctly end-to-end** on this tenant: User Details (search/create, First/Last Name live-mirror confirmed) → Site & Area → Temperature/Notes → Check in. New visitor appeared in the grid immediately with correct data. This tenant's User Type list is short (Approval/Staff/Visitor/Walk-in) unlike the longer list documented for the pre-live "Custom field project" entity — a tenant/entity difference, not a bug.
- No console errors or broken UI elements observed during any of the above.

### Intentional Design Decisions (confirmed by user, not bugs)
- **Re-running "Sign in/out" (bulk or per-row) on a visit that's already "Checked in," selecting the same Site+Area they're already in, correctly leaves the status as "Checked in" — it does not toggle to "Checked out."** Confirmed intentional by the user (2026-08-09), after this was briefly logged as a bug ("BUG-DASH-002") on 2026-08-08: tested by running the bulk mass-action twice on a test visitor already checked into "UK (Testing) > default area," selecting that same area both times — each run produced another "Checked in" Visit History entry (fresh Arrival Time), never a "Checked out" one. **This is expected behavior — do not re-report.** It does not necessarily mean check-out is broken outright — the documented real check-out mechanism (see `visipoint-module-testing` skill's Dashboard section) involves selecting a *different* Site/Area than the visitor's current one, which triggers an auto-signout-from-the-old-area handoff; that specific scenario (different-area selection) was not what was tested in the 2026-08-08 session and remains untested for a check-out-specific repro.

### New automation notes for this module
- Programmatic `dxGrid.instance.pageIndex()`/`pageSize()` calls and real header-click sorts both need several seconds (3-6s) to visibly settle — don't conclude "broken" from a 1-2s check; this cost real time during this session's sort-bug investigation before it was confirmed as a genuine defect (not a timing artifact).
- The Dashboard grid's search input is `input[type="text"]:not([type])` filtered list index **1**, not 0 — index 0 in a naive `querySelectorAll('input')` list is the header "select all" checkbox, which will silently receive text typed for First Name if not filtered out.
- Get the grid's DxDataGrid Vue component via a recursive `$children` walk from `.main-page-container`'s root Vue instance (same pattern as Users/Announcements): useful for direct `columnOption`/`pageIndex`/`clearSorting` calls when coordinate-based clicks are unreliable.
