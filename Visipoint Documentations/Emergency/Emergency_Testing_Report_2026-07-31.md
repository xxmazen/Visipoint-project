# Emergency Sessions / Emergency List — Smoke + API Test Report — 2026-07-31

**Page URLs:** `https://visipoint.uk/emergency-sessions`, `https://visipoint.uk/emergency-list`
**Tester:** Claude (automated browser testing via Chrome MCP)
**Scope:** First-ever test pass on this module — "smoke testing and API testing for Emergency Sessions feature by using all buttons and fields with valid and invalid scenarios," including reading Jira stories first and creating a real session to observe system-wide effects.

No prior experience file existed; this session created `Emergency_Test_Experience.md` as the ongoing knowledge base — read that for full selector/automation detail. This report covers what was done and found.

> **CORRECTION (2026-07-31):** Both findings below (BUG-1 and BUG-2) were reviewed by the user and confirmed to be **intentional application logic, not bugs**. Retained here for the historical record of what was tested and observed; see `Emergency_Test_Experience.md`'s "Intentional Design Decisions" section for the corrected status.

---

## Summary of Findings

| # | Finding | Severity | Status |
|---|---------|----------|--------|
| 1 | Active Session detail grid never shows checked-in users for its covered area, even though the same data correctly renders on the separate Emergency List page | — | **Confirmed intentional logic (2026-07-31), not a bug** — the grid only shows scan-status sign-ins, not visit-status check-ins like the Quick-Sign-In test used |
| 2 | Emergency Type field capped at 20 characters via HTML `maxLength`, not the 33 specified in Jira (CL-3443) | — | **Confirmed intentional logic (2026-07-31), not a bug** — CL-3443's "33" AC is outdated; 20 is the current correct limit |

No other new bugs. All other tested behavior matched the Jira acceptance criteria (CL-2106, CL-2137, CL-2138, CL-3443, CL-9820, CL-14075).

---

## Jira Research (done before any browser testing)

Read via live Jira query (Atlassian MCP) and the static `Jira_Visipoint_Full.md` export:
- **CL-4346** (Cloud "Emergency" epic, 8 stories) and **KI-1970** (Kiosk "Emergency" epic, 3 stories) — all stories Done except **CL-13495** (still "To Do", created 2025-01-15, no activity since) — a stale-state bug on "Dismiss" when another user ends a session while you're viewing the sessions page.
- Read full acceptance criteria on CL-3443 (Start Session modal spec — Emergency Type field format/length, site/area cascading checkbox rules, area-contention disabling) and CL-9820 (Start/End session UX — join/dismiss popups, End Session button disable logic, Emergency List's Active Sessions section visibility rules). These were used as the test oracle throughout.

---

### BUG-1 — Active Session grid shows "No records yet" despite a real checked-in user in its area (confirmed intentional, not a bug)

| Field | Detail |
|-------|--------|
| **Module** | Emergency Sessions |
| **URL** | `https://visipoint.uk/emergency-sessions/Emergency 9/{id}` |
| **Severity** | — |
| **Status** | Confirmed intentional logic (2026-07-31), not a bug — grid is scan-status only, Quick Sign In produces a visit-status check-in |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Start a new emergency session covering "UK (Testing) > default area" | Session created, Active Session page shown | ✅ Correct — "Session Started" toast, redirected to Active Session page for "Emergency 9" |
| 2 | Via Dashboard → Quick Sign In, check in "Mazen Mohamed" to "UK (Testing) > default area" | User checked in | ✅ Correct — "Visit Added" toast, Dashboard grid confirms "Checked in" at the right site/area |
| 3 | View the Active Session page for "Emergency 9" | Mazen Mohamed listed as present | ❌ **"No records yet"** — wrong |
| 4 | Full page reload of the Active Session page | Mazen Mohamed listed | ❌ Still "No records yet" |
| 5 | (Contrast check) View `/emergency-list` instead | Mazen Mohamed listed with Present/Not Present/Out controls | ✅ Correct — proves the underlying data and query work; only the Active Session grid is broken |

**Network evidence:** Monitored network requests while loading/reloading the Active Session page — no request to any `visipoint.uk` API host fired at all, only third-party analytics/font/chat-widget calls. The page appears to never query for its participant data.

**Recommendation:** High priority — this is the primary "who's currently in the affected area" view meant for live use during a real emergency (CL-2106, CL-14075), and it's non-functional. Wire it to the same data source `/emergency-list` correctly uses.

---

### BUG-2 — Emergency Type field maxLength is 20, not the documented 33 (confirmed intentional, not a bug)

| Field | Detail |
|-------|--------|
| **Module** | Emergency Sessions |
| **URL** | `https://visipoint.uk/emergency-list` (Start Session modal) |
| **Severity** | — |
| **Status** | Confirmed intentional logic (2026-07-31), not a bug — CL-3443's "33" AC is outdated, 20 is current |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Open Start Session modal, inspect Emergency Type input | — | Default value shown: `Emergency (31/07/2026 - 05:35 PM)` (~34 chars) |
| 2 | Type an 85-character string into the field | Field accepts up to 33 characters (per CL-3443), cursor stops there | Field stopped accepting input at exactly **20** characters (`input.maxLength === 20` confirmed via DOM inspection) |

**Notable:** the system's own auto-generated default text is already longer than what a user is allowed to type — an internal inconsistency on top of the spec deviation.

**Recommendation:** Change `maxLength` from 20 to 33 to match CL-3443.

---

## Full Coverage Checklist (this session)

### Start Session modal (invalid → valid progression)
- Step 1 (Emergency Type): confirmed optional (Next enabled with it empty); confirmed default auto-value format matches spec; confirmed 20-char cap (BUG-2, above); typed a valid custom value ("Fire Drill Test") and confirmed it saved correctly to the Type column.
- Step 2 (Site/Area): confirmed Start Session button disabled with nothing selected; confirmed tri-state cascading (single area check → site/All show indeterminate); confirmed Start Session button enables the instant one area is checked.
- Submission: confirmed session creation, auto-naming, redirect, toast.

### System-wide effects (the core ask — "create a new session and see the effect in the system")
- Global red header bar: appears on Dashboard and Users grid the instant the session starts; "View" button navigates correctly to the Active Session page; disappears sitewide the instant the session ends (confirmed via reload after ending).
- Emergency List "Active sessions" panel: shows a live badge for the running session.
- Quick Sign In to the exact area covered by the active session: **not blocked** — completed normally (observation, not evaluated as bug/intentional — no explicit Jira AC found either way).
- Active Session grid: **broken, see BUG-1.**
- Emergency List grid: **correct**, shows the checked-in user with status controls.
- Ending the session: confirmed via End Session button → confirmation modal ("are you sure you want to 'End' this emergency session?") → End → "Session Ended" toast → breadcrumb/button updated → Emergency Sessions grid row shows correct Ended By/At and Type.

### Re-confirmed known bugs (not new)
- "Sign in/out" row action never offering Check-out (previously found on Users grid, 2026-07-29) reproduced here too via the Dashboard grid — same root cause, different entry point. Not re-filed as new.

### Not tested (documented for future sessions)
- Multi-session area contention (disabled/note behavior from CL-3443)
- Multi-user Join/Dismiss popups and CL-13495's stale-state bug (needs 2 concurrent sessions)
- Mobile app cross-sync (CL-3448)
- Kiosk-side emergency warning screen (KI-1227/KI-1269) — no kiosk environment available

## Cleanup

Emergency session "Emergency 9" was ended via the UI's own End Session confirmation flow — confirmed in the Emergency Sessions grid (Ended By/At populated, Type "Fire Drill Test" preserved). The test check-in for "Mazen Mohamed" could not be reversed via any UI path (confirmed instance of the known no-checkout-path bug, not something this session introduced) — left as-is.
