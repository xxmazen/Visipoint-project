# Users Module — Smoke Test, Field/Button Walkthrough & API Performance
**Date:** 2026-07-15
**Tester:** Claude (automated browser testing via Chrome MCP)
**Page URL:** `https://visipoint.uk/users`
**Environment:** UK (Testing) company / grinta1911.visipoint.uk
**Reference:** [[project_jira_overview]] (CL-4385 Users, 62 stories; CL-4341 ACL), [[project_knowledge_synthesis]], [[project_users_grid_experience]]
**Scope:** Every field, button, dropdown, and modal reachable from the Users grid was opened and interacted with; screenshots captured at each step. All actions that would mutate real data (create/delete/archive a real user, send invites, print, sign someone in/out, apply a permanent label/permit/type change) were intentionally cancelled before final confirmation — see "What Was Not Submitted" below.

---

## 1. Grid Core Interactions

| # | Area | Result |
|---|------|--------|
| 1 | Page load — grid renders 458 active users, page 1 of 10 | ✅ Pass |
| 2 | First Name search ("Ma") | ✅ Pass — 32 matches, contains-filter, case-insensitive |
| 3 | Last Name search ("Ash") | ✅ Pass — 4 matches |
| 4 | Email Address search ("lamasatech") | ✅ Pass — 6 matches |
| 5 | Phone Number search ("1") | ✅ Pass — 0 matches, confirmed correct (verified via direct grid-API filter call — no user has phone data on file) |
| 6 | User Type filter (Visitor) | ✅ Pass — 183 items |
| 7 | Dashboard Access filter (Admin) | ✅ Pass — 1 item (the logged-in admin) — filter now offers granular roles (No/Pending/Admin/Employee/Fire Warden/Employee with reporting), not just Yes/No as previously documented |
| 8 | Sort by First Name (ascending) | ✅ Pass |
| 9 | Clear Filters | ✅ Pass — clears both text search and dropdown filters (Bug 8 fix still holding) |
| 10 | Row selection (single + multi) | ✅ Pass — mass action toolbar appears with "N Users selected" |
| 11 | Selection persists across page-size change and pagination | ✅ Pass (new observation, not previously documented) |
| 12 | Pagination (page size 10/25/50/100, page navigation) | ✅ Pass |

**New columns not in prior docs:** Pin Code, RFID, Document - Vaccine/P..., Label, YTYT, Test (the last three are custom fields — consistent with by-design custom-fields-as-columns behavior). New toolbar buttons: **Columns**, **Export**.

**Minor cosmetic note (not a bug):** When a column search returns 0 results, the grid's horizontal scroll snaps to the far-right columns instead of staying put. Reproducible but cosmetic only — consistent with this app's general tolerance for minor display quirks; not filed as a bug.

---

## 2. Add User Modal — All Fields

The Add User modal is a **2-step wizard** (not previously documented — old docs implied a single form).

**Step 1 — Main Section:** User Type (dropdown: Approval/Staff/Visitor/Walk-in), First Name, Last Name, Email Address (optional), Phone Number with country code (optional), ID (freetext + Generate button, auto-fills e.g. "VP-nLF7hUl9"), RFID (optional, tag-input), Label (optional, tag-input).

**Step 2 — Custom Fields:** dynamically rendered from company custom fields (observed: "Test" as a checkbox group, "YTYT" as a radio group) — matches the columns seen in the grid, confirming custom fields flow through to the Add form.

All fields were filled and screenshotted. Closed via X without clicking final ADD — no synthetic user was created in the live system.

### RFID (optional) tag field — RECLASSIFIED: NOT A BUG (numeric-only, by design)

Initially flagged as a bug: typing a letters+digits value ("RFIDTEST01") into the RFID field and pressing Enter silently discarded the input (confirmed via Vue component state — `value` stayed `[]`, no console error).

**Correction (2026-07-15, same session):** User confirmed the RFID field is intended to accept **numeric values only**. Retested with a digits-only value (`1234567890`) — the tag pill was created correctly and `value` updated as expected. The field silently rejects non-numeric characters with no validation message, which matches this app's established pattern of minimal/silent validation (e.g. Company Details phone field's validate-on-save behavior). Not a defect — see [[feedback_check_known_logic_before_filing_bugs]].

**Residual, lower-severity observation:** no inline error/hint is shown when non-numeric characters are entered (the input just silently fails to commit) — this is a minor UX-clarity point, not something to file as a bug per this app's QA rules, but worth a mention if the product team is ever looking at RFID-field UX.

---

## 3. Row "Select Actions" Dropdown — All Options

Full list (more than previously documented): **Edit User, Visit Permits, Invite to Dashboard, Print QuickPass, Archive, Delete**. "Sign in/out" is a separate button next to the dropdown, not inside it.

| Action | Result |
|--------|--------|
| Edit User | ✅ Pass — modal pre-fills all fields correctly (First/Last Name, User Type, ID). Also revealed a **Pin Code** field (with its own Generate button) not seen in Add mode's default view. Closed without saving. |
| Archive | ✅ Pass — confirmation modal shows the real username and a clearer warning message than previously documented ("...will no longer be able to check in at any of your sites nor access the company dashboard"). Cancelled. |
| Delete | ✅ Pass — confirmation modal shows username correctly (Bug 2 fix re-confirmed, no spinner freeze observed this session — Bugs 3/4 from the original 2026-06-18 report did not reproduce). Cancelled. |
| Sign in/out (single row) | ✅ Pass — same Site/Area first screen as bulk version. Continue/Cancel buttons were off-screen (dialog wider than viewport, consistent with prior notes); closed via the modal's X instead. Not completed (would actually sign a real user in). |

---

## 4. Mass Action Toolbar — All 6 Buttons

Selected 2 real users, triggering the toolbar: **Change User Type, Invite to Dashboard, Add Label, Add Visit Permit, Print QuickPass, Sign in/out**.

| Action | Result |
|--------|--------|
| Change User Type | ✅ Pass — dropdown (Approval/Staff/Visitor/Walk-in) matches docs; Change button correctly stays disabled until a type is picked. Cancelled (would actually change 2 real users' type). |
| Invite to Dashboard | ✅ Pass — navigates to `/invite-users-to-dashboard`; pre-validation correctly shows "Email address or phone number is required." for both selected users (they have no contact info on file). **Did not click "Invite all"** — that would send real emails/SMS to real users, which requires your explicit go-ahead. |
| Add Label | ✅ Pass — typed a test tag, pill created correctly, Add button activated. **Did not submit** — would permanently tag 2 real user accounts. |
| Add Visit Permit | ✅ Pass — modal matches docs (Visit Permits dropdown + inheritance note). Cancelled (real access-control consequences). |
| Print QuickPass | ✅ Pass — Local/Online printer radio. Selecting "Online printer" revealed a new "Checking for printers" loading state not previously documented. Cancelled (did not print). |
| Sign in/out (bulk) | ✅ Pass — same Site/Area screen. Cancelled (would sign real users in). |

---

## 5. Import & Archived Users

- **Import button** → `/users/import`: full wizard with Excel template download, file browse (2MB/.xlsx limit), unique-migration-field selection (First/Last Name/Email checkboxes), duplicate-handling radio (Updated/Ignored), and a separate optional photo ZIP upload section (50MB limit, JPG/PNG). Much more detailed than previously documented as "navigates to Import Queue." Did not click Download or Browse (file download/upload needs your go-ahead).
- **Import Queue** (separate page, `/users/import/queue`, reached via a link on the Import page): its own filterable grid (Operator/Serial/Date/Time/Status) — currently empty, no imports on record.
- **Archived Users tab**: loads correctly (3 archived users). Row actions: **Activate, Delete** — neither tested (would restore or permanently delete real archived users).

---

## 6. API Performance — Users Module

Measured via the browser's Resource Timing API during real page loads. API host: `api.visipoint.uk`.

| Endpoint | Trigger | Samples | Min (ms) | Avg (ms) | Max (ms) |
|----------|---------|:-------:|:--------:|:--------:|:--------:|
| `GET /api/data_users_grid/{entityId}` | Page load | 5 | 410 | 522 | 650 |
| `GET /api/check_active_sessions/{entityId}` | Page load | 5 | 377 | 514 | 700 |
| `GET /api/adminPreference/{entityId}` | Page load (called ~3x per load — grid column/preference config) | 3 | 465 | 647 | 984 |
| `GET /api/users/{entityId}?skip=&take=50&filterBy=...` (main paginated grid data — confirmed via network inspector) | Page load | — | — | — | — |

**Raw samples:**
- `data_users_grid`: 459, 650, 410, 454, 637 ms
- `check_active_sessions`: 377, 647, 404, 444, 700 ms
- `adminPreference`: 491, 984, 465 ms

**Note on the main list endpoint:** `GET /api/users/{entityId}?skip=0&take=50&filterBy=...` (confirmed via direct network inspection) carries filter/sort data in its query string, which the browser extension's privacy layer redacts from JS-visible resource timing (`[BLOCKED: Cookie/query string data]`). Duration data for these redacted entries was still readable and showed **high variance across the session: as low as 100ms, but spiking to 1.6s–5.2s on some loads** — this is the single biggest performance concern from this session. Because the exact endpoint can't be confirmed per-sample, this should be re-verified with a dedicated network/APM tool rather than treated as a confirmed regression.

**Scope note:** Single-user, single-request latency sampling from real interactions — not a concurrent load/stress test. No load-testing tool (k6/JMeter/Postman Runner) is wired into this environment.

---

## 7. What Was Not Submitted (by design, this session)

To avoid mutating real data or sending real communications, the following were opened/filled but intentionally **not confirmed**:
- Add User (full form filled, not saved)
- Edit User (viewed, not saved)
- Archive / Delete (single row and Archived-tab Activate/Delete)
- Change User Type, Add Label, Add Visit Permit (mass actions)
- Invite to Dashboard — "Invite all" (would send real email/SMS)
- Print QuickPass — "Print" (would send to a real/local printer)
- Sign in/out (single + bulk)
- Import — file download/upload

If you want any of these tested end-to-end (e.g. confirming a mass-action save, or a real invite/print), let me know and I'll get explicit confirmation before each one.
