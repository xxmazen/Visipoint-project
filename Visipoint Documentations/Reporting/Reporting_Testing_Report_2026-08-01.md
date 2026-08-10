# Reporting Module — Full Coverage Regression Report — 2026-08-01

**Tester:** Claude (automated browser testing via Chrome MCP)
**Scope:** Full smoke pass across all 8 Reporting sub-pages (History, Visit Summary, Timesheet, Users not on site, Track and Trace, Export List, Kiosk Logs, Print List) — the last full-coverage pass was 2026-07-15. Read `Reporting_Test_Experience.md` (updated with this session's findings) for full detail and selectors.

**Environment note:** Tested against tenant "UK (TESTING02)" (same as the current Announcements/Add Visits sessions), not "UK (Testing)" used by the 2026-07-15 session. This tenant has much sparser data (a handful of records per page vs. 184 in the "Users not on site" freeze-risk case from the original 2026-06-19 report) — this incidentally allowed safely testing Export on "Users not on site" without risking the known performance freeze.

---

## Summary of Findings

| # | Finding | Status |
|---|---------|--------|
| 1 | Visit Summary's real URL is `/reporting/visit_summary` (underscore) — the experience file previously documented `/reporting/visit-summary` (hyphen), which 404s | **Documentation correction** — not a product bug, just a stale doc |
| 2 | Kiosk Logs pagination missing 25/100 (only 5/10/50) | **Re-confirmed still present** |
| 3 | Print List pagination wrong set entirely (50/100/150/200, no small sizes) | **Re-confirmed still present** |
| 4 | Users not on site pagination also 50/100/150/200 (changed from standard 5/10/25/50/100 as of 2026-07-15) | **Re-confirmed still present** |
| 5 | Export List no longer has an Export button in its toolbar (only Columns) | **New structural change** — likely intentional (removes a confusing self-referential "export the export list" button), but deviates from prior docs — flag to product to confirm intentional |
| 6 | Track and Trace's Filter now appears to require both User AND Date before showing a grid (previously either alone was enough) | **Behavior change, likely intentional** — the helper message text itself says "Select a user **and** date range," consistent with requiring both |
| 7 | Timesheet User Type field greyed out until Site selected | Re-confirmed intentional by design, not a bug |
| 8 | Timesheet Export is Excel-only | Re-confirmed, unchanged |
| 9 | Users not on site → Export performance freeze (60+s on 184 records) | **Not retested at scale** — this tenant only has 6 records, Export opened instantly with no freeze, consistent with the freeze being data-volume-dependent rather than universal |
| 10 | Kiosk Logs Actions column, now with a real record: shows no icons ("-") | **New finding, matches Print List's existing "no actions rendered" pattern** — likely by-design for read-only logs, not a bug |

No new severity-worthy bugs found this session — this was a clean regression pass that also closed two data-dependent gaps (Kiosk Logs Actions column, small-scale Export retest) that couldn't be tested before due to empty test data.

---

## Detail — Per-page notes

**History** — loads with 0 records in this tenant, no toolbar date-range filter (matches the 2026-07-15 finding that removed it), standard 5/10/25/50/100 pagination. New/previously-undocumented: a "Temperature Unit °F/°C" toggle appears in the top-right of the toolbar — not seen or noted in any prior session's report.

**Visit Summary** — loads correctly once the correct URL (`/reporting/visit_summary`) is used. Date range picker defaults to today, Site/Area are header filters (not separate toolbar dropdowns) matching the 2026-07-15 redesign, grid supports column-header grouping ("Drag a column header here to group by that column"). 0 records for today's date in this tenant.

**Timesheet** — filter form (Year/Month multi-select/Site multi-select/User Type) shown by default since this tenant has no cached prior report. User Type correctly stays disabled until a Site is checked (re-confirmed by-design). Filled Year 2026, Month August, Site "UK (Testing02)", User Type "Staff", clicked Generate — pivot grid rendered with "Grand Total: No data" (expected, no visit history in this tenant). Export dropdown confirmed Excel-only, unchanged.

**Users not on site** — simplified filter form (Site + User Type, re-confirmed no Date field) with Generate correctly disabled until both fields are filled. Generated successfully for Site "UK (Testing02)" + User Type "Staff": grid showed 6 users with working "Sign in" action buttons per row (did not click Sign In — that mutates data). Pagination re-confirmed still 50/100/150/200 (missing the standard smaller sizes). Export dropdown opened cleanly (Excel/CSV/PDF) with no freeze on this small 6-record dataset — did not attempt to reproduce the original 60+s freeze since that requires a large dataset (184 records) not present in this tenant.

**Track and Trace** — proactive "Select a user and date range to show information." message re-confirmed showing on page load (the 2026-07-15 fix holds). Selected a User only + clicked Filter: message persisted, grid did not appear. Then also filled a Date range + clicked Filter: grid appeared correctly with standard 5/10/25/50/100 pagination and "No data yet." This differs from the original 2026-06-19 report, which found "User only" sufficient to show a grid — now both User and Date appear required. Not filing as a bug since the message text itself instructs selecting both, and this reads as a deliberate validation tightening rather than a regression.

**Export List** — 0 records, header filters showing correctly. Toolbar now shows only "Columns" — no "Export" dropdown, confirmed via DOM query (not just a visual miss). This is a change from every prior session's documentation. Recommend flagging to product to confirm this was an intentional removal (exporting a list of past exports was arguably a confusing, low-value feature) rather than a regression.

**Kiosk Logs** — 1 real record this session (previous sessions always had 0). Pagination re-confirmed still limited to 5/10/50 (missing 25/100). "Reason of failure" column re-confirmed to have a header filter but no text search box, unlike the other columns. Actions column, testable for the first time with real data, shows "-" (no icons) for this record — consistent with Print List's already-documented "Actions always empty" pattern, suggesting this is a deliberate read-only-log design across both pages rather than a one-off gap.

**Print List** — 0 records this session. Pagination re-confirmed still 50/100/150/200 (no small sizes). Column structure (only User Name has a text search box, everything else is header-filter-only) matches prior documentation exactly.

---

## Recommendation

The three pagination inconsistencies (Kiosk Logs, Print List, Users not on site) have now been re-confirmed present across three sessions (2026-06-19 → 2026-07-15 → 2026-08-01) with zero movement — worth escalating as a real, stable bug rather than continuing to treat it as "needs a follow-up." The Export List button removal and Track and Trace's tightened validation are both plausible intentional changes but haven't been confirmed with the product team — flag both for a quick sanity check rather than treating either as settled.
