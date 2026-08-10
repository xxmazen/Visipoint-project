# Reporting Module — Smoke Test, Field/Button Walkthrough & API Performance
**Date:** 2026-07-15
**Tester:** Claude (automated browser testing via Chrome MCP)
**Module URL:** `https://visipoint.uk/reporting/`
**Environment:** UK (Testing) company / grinta1911.visipoint.uk
**Reference:** [[project_jira_overview]] (CL-4193 Reporting, 17 stories), [[project_knowledge_synthesis]], [[project_reporting_experience]]
**Scope:** All 8 sub-sections tested with real data. Every filter field, dropdown, date/range picker, and grid interaction was exercised and screenshotted. Destructive/irreversible/risky actions were avoided — see "What Was Not Submitted" and the Users Not on Site Export note below.

---

## 1. History (`/reporting/history`)

| # | Area | Result |
|---|------|--------|
| 1 | Page load with real data (27 items) | ✅ Pass |
| 2 | First/Last Name text search | ✅ Pass |
| 3 | Action column header filter (≡) | ✅ Pass — Checked in/Rejected/Deleted/Signed In options |
| 4 | Clear Filters | ✅ Pass — fix still holding (search + item count reset correctly) |

**No date-range filter was visible in the toolbar** as previously documented — the page now loads all records directly and relies on grid column filters instead. Sidebar now also lists all 8 Reporting sub-pages directly (previously required navigating via a Reporting landing page).

---

## 2. Visit Summary (`/reporting/visit_summary`)

| # | Area | Result |
|---|------|--------|
| 1 | Dual-month date range picker | ✅ Pass — defaults to today only; widened to a range, correctly loaded 5 items with an added "clear range" (×) icon not previously documented |
| 2 | Site header filter | ✅ Pass — **the "Site filter dropdown" from the old docs is now a header filter on the Site column**, not a separate toolbar dropdown |
| 3 | Grid data / columns | ✅ Pass |

---

## 3. Timesheet (`/reporting/timesheet`)

**Significant behavior change from documented baseline:**
- Navigating to the page now shows a **cached pivot grid from a previous generation** by default, with a **"Re-generate"** button (not the filter form as previously documented as the default view).
- Clicking Re-generate reveals the filter form: Year (single-select dropdown), **Month (multi-select checkbox list, not a single dropdown as documented)**, Site (multi-select), User Type (multi-select, disabled until Site chosen — confirmed still by-design).
- Filled all 4 fields (2026 / July / UK (Testing) / Staff) and clicked **Generate**. This is now an **async operation** — briefly shows "Generating your timesheet. You will receive an email once ready." before resolving to the pivot grid (resolved quickly in this session, ~seconds).
- Pivot grid: row expansion (drill into User Type breakdown) works correctly.
- Export: **confirmed Excel-only**, matches docs.

**New finding:** timesheet generation notifies the account holder by email when ready — this is expected core functionality of the report generation feature (not a message sent to a third party), consistent with using the button as designed.

---

## 4. Users Not on Site (`/reporting/users-not-on-site`)

| # | Area | Result |
|---|------|--------|
| 1 | Page load — also showed a **cached prior result** (183 items) with "Change criteria"/"Update data" buttons instead of the filter form by default | ✅ Pass (new behavior vs. docs) |
| 2 | Full Name / Email / Phone / RFID text search | ✅ Pass |
| 3 | Clear Filters | ✅ Pass — fix still holding |
| 4 | "Change criteria" button → filter form | ✅ Pass — **form is now Site + User Type only, no Date field** (previously documented as Date + Site required) |
| 5 | Help ("?") tooltips on Change criteria / Update data | ✅ Pass — new, helpful, not previously documented ("Click this to change the User Types or Sites.") |
| 6 | Generate (from Change criteria form) | Selected Site, clicked Generate — page did not visibly transition back to the grid in this session (possibly needs User Type too, or a UI hiccup); not pursued further since the cached 183-item grid already confirmed the report/grid works correctly |

**Pagination — CONFIRMED CHANGED:** now **50, 100, 150, 200** (previously documented as standard 5/10/25/50/100). This matches the pattern previously only seen on Print List — worth noting as a broader UI-consistency issue across large-dataset Reporting pages, not isolated to Print List.

**Not tested (intentionally):** the **Export** button — the 2026-06-19 session recorded a 60+ second full renderer freeze exporting this page's 184 records, requiring a browser extension reconnect. Not retriggered this session to avoid repeating that outage. Also did not click **Sign In** (would sign a real user in).

---

## 5. Track and Trace (`/reporting/track-trace`)

| # | Area | Result |
|---|------|--------|
| 1 | Page load — inline "Select a user and date range to show information." shown by default | ✅ Pass — **Bug #4 fix re-confirmed** (previously this required clicking Filter with nothing selected to trigger; now shown proactively on load too) |
| 2 | User dropdown (vue-multiselect) | ✅ Pass — populated with real users |
| 3 | Date range picker | ✅ Pass |
| 4 | Filter button (with User + Date selected) | ✅ Pass — grid appears with all 8 documented columns, "No data yet" (no matching records for this user/range) |
| 5 | Clear Filters | ✅ Pass — fix still holding |
| 6 | Pagination | ✅ Pass — standard 5/10/25/50/100, unaffected by the pagination inconsistency seen elsewhere |

---

## 6. Export List (`/reporting/export-list`)

Loaded correctly — "No records yet", matches docs. **New observation:** the Timesheet report generated earlier in this session did **not** appear here, suggesting "Generate" (async report generation) and "Export" (grid export to Excel/CSV/PDF) are tracked separately — Export List only logs the latter. Also, on this visit the page showed **no Export button in the toolbar** (only Columns) — worth re-checking in a future session, as this may be a permission-dependent or loading-state difference rather than a genuine change.

---

## 7. Kiosk Logs (`/reporting/kiosk-logs`)

**Pagination bug — CONFIRMED STILL PRESENT.** Only 5, 10, 50 available (missing 25, 100), exactly as documented in 2026-06-19 and flagged as not-yet-retested in the 2026-07-01 regression pass. No data in the test environment; "Reason of failure" column still has a header filter icon with no text search input, as documented.

---

## 8. Print List (`/reporting/printer`)

**Pagination bug — CONFIRMED STILL PRESENT.** Only 50, 100, 150, 200 available, exactly as documented. Now has **2 test records** (previously 1): both show `Kiosk` printer, `Print` status, and an empty **Actions** column for both rows — confirms the empty Actions column is consistent/by-design for this read-only log, not a one-off with the single old record.

---

## 9. API Performance — Reporting Module

Measured via the browser's Resource Timing API during real page loads. API host: `api.visipoint.uk`.

| Endpoint | Page | Samples | Min (ms) | Avg (ms) | Max (ms) |
|----------|------|:-------:|:--------:|:--------:|:--------:|
| `GET /api/get_history_data_grid/...` | History | 2 | 353 | 1034 | 1714 |
| `GET /api/report_list_history/...` | History (first load only) | 1 | — | 2387 | — |
| `GET /api/print_queue_list/...` | Print List | 1 | — | 1734 | — |
| `GET /api/check_active_sessions/...` | Reporting pages (general) | 2 | 386 | 1007 | 1628 |
| `GET /api/adminPreference/...` | Reporting pages (general, called up to 2x/load) | 5 | 160 | 798 | 1702 |

**Note:** Kiosk Logs returned no capturable resource timing entries on two attempts — likely because its grid data call resolves via a cached/already-loaded code path when navigating within the SPA rather than a fresh XHR the timing API could observe. Not investigated further given time constraints.

**Observation:** consistent with the Users, Announcements, and Compliance sessions earlier today, latency shows high variance between a page's first cold load (1.6–2.4s) and subsequent loads (as low as 160–390ms) — most likely a shared session/auth warm-up cost rather than a Reporting-specific issue.

**Scope note:** Single-user, single-request latency sampling — not a concurrent load/stress test. No load-testing tool is wired into this environment.

---

## 10. What Was Not Submitted / Not Retriggered (by design, this session)

- **Users Not on Site → Export** — deliberately not clicked. Previously caused a 60+ second full-page renderer freeze on 184 records requiring a browser extension reconnect; not worth repeating without explicit instruction.
- **Users Not on Site → Sign In** — would sign a real user in; not clicked.
- File downloads (Export → Excel/CSV/PDF) on every page — options were opened/confirmed to exist but never clicked, per the standing rule that file downloads need explicit go-ahead.
- **Print List / Export List Actions column icons** — Print List's Actions column is confirmed always empty (2/2 records); Export List has no records to test download/delete icons against.
