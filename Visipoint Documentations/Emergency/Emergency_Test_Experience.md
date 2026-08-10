# Emergency Sessions / Emergency List — Test Experience & Knowledge Base

**Page URLs:** `https://visipoint.uk/emergency-sessions` (Emergency Sessions grid), `https://visipoint.uk/emergency-list` (Emergency List — static print/manual page)
**Last Tested:** 2026-07-31 (corrected 2026-07-31)
**Tester:** Claude (automated browser testing via Chrome MCP)

> **CORRECTION (2026-07-31):** Both findings originally filed below as BUG-1 and BUG-2 were reviewed by the user and confirmed to be **intentional application logic, not bugs**. See the "Intentional Design Decisions" section below. Do not re-report either.

> Read this file before testing Emergency Sessions / Emergency List again. Also read `D:\Visipoint md files\Jira\Jira_Visipoint_Knowledge_Synthesis.md` Section 4 for cross-module bug status.

---

## Jira Background

- **CL-4346 "Emergency"** (Cloud epic, 8 stories, all Done except CL-13495) and **KI-1970 "Emergency"** (Kiosk epic, 3 stories, all Done) are the source epics.
- Key stories: CL-2100 (print emergency list), CL-2106 (start session), CL-2137 (end session), CL-2138 (global header bar), CL-2747/CL-9820 (UX enhancements — modal wording, popups for multi-user join/dismiss), CL-14075 (view all signed/checked-in users for permitted sites — Active Session, Past Session, and Emergency List all covered).
- **CL-13495 (Story, still "To Do" as of 2026-07-31, created 2025-01-15, Medium priority, no comments/updates since creation):** "Session ended while the system user is viewing the 'Emergency' sessions page" — when another user's "Dismiss" is clicked on the end-session popup, the page should update the active session to show as ended instead of staying stale. **Not independently retestable in a solo-browser session** (requires two concurrent system users viewing the same session) — flag for a future multi-session/multi-tab test.
- Full CL-3443 (Start Session modal AC) and CL-9820 (Start/End session enhancements) acceptance criteria are quoted in detail in the dated report; see there for exact spec text.

---

## Page Structure

### Emergency Sessions (`/emergency-sessions`)
- Grid of all past + current sessions: Session Name (auto-numbered "Emergency N"), Started By, Started At, Ended By, Ended At, Areas, Type, Actions.
- **Start Session** button top-left opens the Start Session modal.
- An active (unended) session shows a live green dot + a **Join** button in its row instead of Ended By/At.
- Clicking a session name (or Join) navigates to `/emergency-sessions/{Session Name}/{sessionId}` — the Active/Past Session detail grid.

### Emergency List (`/emergency-list`)
- **Static, printable list of all currently checked-in users** — explicitly NOT the same as a dynamic session: "This page is only meant for printing and manual use. To activate a dynamic session, please click the 'Start Session' button above."
- Grid: Image, Full Name, Phone Number, User Type, Site, Area, Status, By, Email Address, RFID.
- If any session(s) are active, an **"Active sessions"** panel appears above the grid with a pill/badge per active session name (e.g., "Emergency 9") — clicking it navigates to that session's detail page.
- This page correctly and reliably shows all currently checked-in users (confirmed working, see Bugs section for contrast with the broken Active Session grid).

### Active/Past Session detail page (`/emergency-sessions/{name}/{id}`)
- Breadcrumb: `Emergency Sessions / {Session Name}`.
- **End Session** button (top, only shown while active).
- Grid columns: Image, Full Name, Status, Phone Number, Site, Area, User Type, By, Email Address, RFID.
- **By design, this grid only shows users who arrived via an actual sign-in scan (kiosk/RFID/etc.), not users who were given a "Visit" check-in status (e.g. via Dashboard Quick Sign In).** Confirmed intentional 2026-07-31 — see "Intentional Design Decisions" below. `/emergency-list` is the correct page to check for Visit-status check-ins; this grid is scan-status only.

---

## Start Session Modal — Feature Behavior (matches current logic; CL-3443's "33 characters" AC is outdated)

Opened via **Start Session** button on either `/emergency-sessions` or `/emergency-list`. Two-step wizard (dot stepper, Back/Next):

### Step 1 — Emergency Type
- Field labeled "Emergency Type (Optional)" — optional, so "Next" is enabled with it empty.
- Default placeholder/auto-value shown when the modal opens: `Emergency (DD/MM/YYYY - HH:MM AM/PM)` — e.g. `Emergency (31/07/2026 - 05:35 PM)`, ~34 characters.
- The input's `maxLength` attribute is **20**. CL-3443 documents 33, but that AC is outdated — the logic was updated since to accept only 20 characters, and 20 is the correct/current behavior. Confirmed intentional 2026-07-31, do not re-report as a spec deviation.
- If left as the default (or blanked and re-defaulted), the value is saved as the session's "Type" column value.

### Step 2 — Site & Area selection
- Tri-state checkbox tree: "All" → Site (e.g. "UK (Testing)", expandable via arrow) → Areas ("default area", "Sign in/out area").
- Checking/unchecking cascades correctly both ways (check a site → all its areas check; uncheck all areas → site auto-unchecks; partial selection → site and "All" show indeterminate dash state).
- **Start Session** button is disabled (grey) until at least one area is selected — confirmed via both visual state and clicking-does-nothing; becomes enabled (dark blue) the instant one area is checked.
- Per spec (CL-3443), areas already claimed by another active session should appear disabled/unchecked with a note, and a site with no free areas should be fully disabled. **Not tested this session** (only one active session existed, no area contention scenario) — worth testing in a future multi-session pass.

### Submit
- Clicking **Start Session** (enabled) immediately creates and activates the session, shows a "Session Started" toast, and redirects to the new session's Active Session page (breadcrumb + End Session button visible).
- Session Name auto-increments ("Emergency N") regardless of the custom Emergency Type text entered — the Type text is stored separately and shown in the "Type" column on the Emergency Sessions grid.

---

## System-Wide Effects of an Active Session (all confirmed working)

- **Global red bar** appears at the very top of every page (Dashboard, Users, etc.) the instant a session is started: "Emergency sessions are active now. For more details, click 'View'." with a **View** button that navigates to the Active Session page. Confirmed present on Dashboard and Users grid; disappears immediately once the session is ended (confirmed via reload).
- **Emergency List** page's "Active sessions" panel shows a live badge for the session.
- A normal (non-emergency) **Quick Sign In** to the exact site/area covered by the active session is **NOT blocked** — the area appears selectable in the Quick Sign In flow (not visually disabled), and check-in completes normally. (No explicit Jira AC found stating this should be blocked — recorded as an observation, not a bug.)
- Ending a session does **not** sign out or otherwise affect any users who checked in during it — their check-in status is untouched, only the session itself moves to "ended."

---

## Bugs Found

None open as of 2026-07-31 (corrected). Both original findings were confirmed intentional — see below.

## Intentional Design Decisions (Not Bugs)

| Behavior | Why it's not a bug |
|----------|---------------------|
| Active Session grid (`/emergency-sessions/{name}/{id}`) shows "No records yet" for users checked in with a **Visit status** (e.g. via Dashboard Quick Sign In) — it only displays users who arrived via an actual **sign-in scan**. Originally mis-flagged as a High-severity bug after checking a Quick-Sign-In'd user ("Mazen Mohamed") and seeing him absent from this grid despite appearing correctly on `/emergency-list`. | Intentional — confirmed by user 2026-07-31. The Active Session grid and the Emergency List page track different populations by design (scan-status vs. visit-status); they are not expected to show the same set of people. Do not re-report. |
| Start Session modal's "Emergency Type" field has `maxLength=20`, not the 33 documented in Jira story CL-3443. | Intentional — confirmed by user 2026-07-31. The logic was updated after CL-3443 was written; 20 characters is the current correct limit and CL-3443's "33" AC is outdated documentation, not a live spec. Do not re-report. |

---

## Confirmed Working (matches current logic, tested 2026-07-31)

- Start Session modal 2-step wizard: Emergency Type (optional, default value format correct, 20-char cap by design) → Site/Area tri-state checkbox tree with correct cascading and Start-Session-button enable/disable logic.
- Session creation: toast, redirect to Active Session page, sequential auto-naming ("Emergency N"), custom Type text correctly saved and shown in the Emergency Sessions grid.
- Global red header bar: appears sitewide the instant a session starts, "View" button navigates correctly, disappears sitewide the instant the session ends.
- End Session confirmation modal: correct title/body/Cancel/End buttons, ends the session correctly (toast, breadcrumb/button updates, grid row updates with Ended By/At).
- Emergency List page (`/emergency-list`) correctly and reliably shows all currently checked-in users (both scan-status and visit-status) with Present/Not Present/Out status controls.
- Active Session grid correctly shows scan-status sign-ins for the session's covered area (by design, does not show visit-status check-ins — see Intentional Design Decisions above). Not independently re-verified with an actual scan-based sign-in this session (no kiosk/scanner available) — worth a follow-up test with a real scan if one becomes testable.

## Already-Known Bugs Re-Confirmed (not new, don't re-report as new)

- **"Sign in/out" row action never offers Check-out** (Users grid bug, found 2026-07-29) — re-confirmed here on the Dashboard grid too: clicking "Sign in/out" on an already-checked-in user shows the Check-in form again, not a check-out option. "Select Actions" dropdown for that row only offers "Add Visit Notes," no check-out path either. This is the same known bug, just reproduced in a different entry point — see `Users_Grid_Test_Experience.md`.

## What Was NOT Fully Tested

- Multi-session area contention (starting a second session that overlaps an already-active session's areas) — only one active session existed during this test, so the "disabled area with a note" behavior from CL-3443 wasn't exercised.
- Multi-user simultaneous session viewing (Join/Dismiss popups, CL-9820 point A-3 and B-2, and CL-13495's stale-state-on-dismiss bug) — requires two concurrent system-user sessions, not testable solo.
- Ending a session via the mobile app / cross-platform sync (CL-3448) — web-only session, no mobile app access.
- Kiosk-side emergency warning screen behavior (KI-1227, KI-1269) — cloud dashboard testing only, no physical/emulated kiosk in this environment.
- Direct API-level testing (no separate API host/bearer-token pattern was found for this module the way `api-survey.visipoint.me` works for Survey — Emergency Sessions appear to run through the same main app API, and the network-request monitor did not surface those calls for this app during this session, which is itself evidence for BUG-1 but limits how much can be independently API-verified beyond what's stated there).

## Cleanup

Test session "Emergency 9" (Type: "Fire Drill Test", Areas: default area) was ended via the UI's own End Session flow. The test check-in for "Mazen Mohamed" could **not** be signed out via any available UI path (confirmed instance of the known no-checkout-path bug) — left checked in as of session end; a future session should check whether this needs cleaning up via another means (e.g. Users grid Edit, or ask the user).
