# Visipoint Project — Knowledge Synthesis
**Generated:** 2026-06-30 | **Last Updated:** 2026-08-10 (User Settings — Attendance Modes scope-of-application business logic rule added)
**Source:** Full read of Jira_Visipoint_Full.md + Live Jira queries (bugs + test cases) + accumulated module testing experience
**Boards covered:** CL (Cloud Dashboard, 32 epics, 676 stories) | KI (Kiosk + Mobile, 23 epics, 371 stories)

---

## 1. Project Architecture (Quick Reference)

| Component | URL | Purpose |
|-----------|-----|---------|
| Cloud Dashboard | https://visipoint.uk | Admin portal — manage users, sites, journeys, compliance, reports |
| Visipoint Passport | https://visipoint.me | Identity/mobile app — visitor self-service, remote sign-in, geofencing |
| Kiosk (Touch Mode) | Physical device | On-site check-in/sign-in for visitors and staff |

**Key concepts:**
- **Journey / Flow** — configurable sequence of steps a user goes through on the kiosk (compliance, custom fields, host selection, badge printing, etc.)
- **User Type** — visitor, staff, contractor, etc. Each has its own journey/flow
- **Site / Area** — hierarchical location: Site → Area (parent/child relationship matters for sign-in logic)
- **Expected Visit** — a pre-registered visit added by a system user or via mobile app, which can trigger compliance pre-filling
- **Attendance Mode** — sign-in reason/code applied to visits (e.g. "Late", "Remote Working")
- **Custom Fields** — dynamic data fields configured in User Settings; appear in Dashboard/History grids by design
- **Passport Account** — a user's linked Visipoint identity (QR code, profile, expected visits, compliance)

---

## 2. CL Board — All 32 Epics

| Epic | Key | Stories | Focus |
|------|-----|---------|-------|
| Cloud Passport Config | CL-26 | — | Passport account linking, QR codes, kiosk configuration |
| Add Visits | CL-34 | — | System users adding expected visits for passport users |
| Compliance | CL-47 | 59 | Agreement creation, questionnaires, compliance in flows |
| Public QuickPass | CL-173 | — | Public kiosk access without Passport account |
| Exploratory Testing | CL-205 | — | Unstructured testing bucket |
| Pre-registration | CL-222 | 67 | Pre-registered visitor management |
| VisiPoint Passport | CL-373 | — | Passport identity system integration |
| Touch Mode | CL-616 | — | Kiosk touch screen interaction configuration |
| General | CL-1160 | — | General improvements and miscellaneous features |
| Sign in/Sign out | CL-1179 | 61 | Core check-in/check-out flow |
| Host | CL-3215 | — | Host selection, visitor-host relationships |
| Structure | CL-3469 | — | Site/area hierarchy and organizational structure |
| Reporting | CL-4193 | — | History, metrics, performance, export features |
| ACL | CL-4341 | 44 | Access control and permission management |
| Emergency | CL-4346 | — | Emergency sessions and emergency list |
| Users | CL-4385 | 62 | User management, user types, invitation system |
| Attendance Modes | CL-4582 | — | Sign-in reasons, attendance codes, time-based modes |
| Integrations | CL-4966 | — | Third-party system integrations |
| Journeys | CL-5513 | — | Journey builder, flow configuration |
| Daily Log | CL-5750 | — | Scan history, audit trail |
| RFID Enrollment | CL-5927 | — | RFID card management |
| Sites & Devices | CL-6120 | — | Site management, kiosk devices |
| Custom Fields | CL-6470 | 56 | Dynamic field configuration |
| Company Details | CL-6902 | — | Company profile, branding, settings |
| Clients APIs | CL-7322 | — | Developer API access |
| Announcements | CL-7436 | — | Announcement management and delivery |
| Remote Sign in | CL-8152 | — | GPS-based remote sign-in from mobile |
| User Types | CL-8425 | — | Visitor type configuration |
| Geofencing | CL-8861 | — | Location-based automatic sign-in |
| Duplication Control | CL-11774 | — | Prevention of duplicate entries |
| Scalability | CL-14695 | — | Performance and scale improvements |
| **Survey Module** | **CL-16841** | **39** | **Survey creation, builder, reporting — ACTIVE** |

**Currently In Progress:** CL-16617 "Unified Survey Builder Controls"

---

## 3. KI Board — All 23 Epics

| Epic | Key | Stories | Focus |
|------|-----|---------|-------|
| Expected Visitors | KI-33 | — | Pre-registered visitors on kiosk |
| Compliance | KI-42 | 35 | Compliance display/capture on kiosk |
| Public QuickPass | KI-130 | — | Public kiosk access |
| Passport Account | KI-149 | — | Passport user kiosk interactions |
| Exploratory Testing | KI-159 | — | Unstructured kiosk testing |
| Pre-registration | KI-169 | 40 | Pre-registered visitors on kiosk |
| Touch Mode | KI-444 | 26 | Touch mode UI/UX on kiosk |
| Sign in/Sign out | KI-806 | many | Core check-in/out; parent/child area logic; visit permits |
| **Mobile App** | **KI-1414** | **113** | **Visipoint Passport mobile app — largest epic** |
| Host | KI-1560 | 6 | Host selection during kiosk visit |
| Emergency | KI-1970 | 3 | Emergency list printing, emergency alerts on kiosk |
| Attendance Modes | KI-2010 | 13 | Sign-in/out reason handling on kiosk |
| Journey Builder | KI-2328 | 24 | Flow rendering on kiosk (outputs, badges, compliance settings) |
| Users | KI-2356 | 9 | User management on kiosk |
| RFID Enrollment | KI-2387 | 4 | RFID card scanning and linking |
| General | KI-2504 | 5 | Print badge triggers, static custom fields on badges |
| Visits History | KI-2642 | 0 | Visit history display on kiosk (no stories yet) |
| Hardware Status | KI-2803 | 1 | Device health monitoring |
| Remote Sign in | KI-3489 | 15 | Remote sign-in on mobile |
| Custom Fields | KI-3946 | 22 | Custom field display/capture on kiosk |
| Geofencing | KI-3952 | 9 | GPS-based auto sign-in via mobile |
| Company Details | KI-7281 | 1 | Company logo display on kiosk |
| **Survey Modules** | **KI-8486** | **10** | **Smiley, Likert, Stars, Custom Choice on kiosk — ACTIVE** |

---

## 4. Open Bugs (Action Required)

### CL Board — 8 Open Bugs

| Key | Status | Priority | Summary |
|-----|--------|----------|---------|
| CL-7517 | To Do | **Highest** | [Integration] Duplicate users with same email/phone after force sync error |
| CL-17671 | To Do | **High** | [Survey Builder] Surveys from templates have no questions — activation returns HTTP 400 |
| CL-17673 | QC Review | High | [Survey Builder] Activation error toast doesn't explain why activation failed |
| CL-16259 | To Do | Medium | [Dashboard] Compliance title appears twice in tooltip |
| CL-14658 | To Do | Medium | [Daily log] Edit scan modal empty after changing area login mode |
| CL-17672 | To Do | Medium | [Survey Management] Journey API returns null for description (should be empty string) |
| CL-17675 | Ready for Testing | Medium | [Survey Management] Journey description shows "null" instead of empty |
| CL-17680 | QC Review | Low | [Survey Builder] Malformed SVG path error on every page load |

> **Note on CL-17680:** This is the known SVG path error that appears on every page load as a console warning. It is also tracked as a bug. Do not re-report it — it's already in QC Review.

> **2026-07-27 smoke test update:** CL-17671 and CL-17673 could not be reproduced via the standard template-creation flow (CSAT template → Choose and customize → Save and publish). The created survey had questions pre-filled, and the publish failure showed a clear inline field-level message ("Next button label is required"), not a generic toast — after fixing that field, publish succeeded with no HTTP 400. Recommend QC re-verify against original repro steps before closing either ticket. Full details in `Jira/../Multi_Module_Testing_Report_2026-07-27.md`.
>
> **2026-08-04 re-confirmation:** Re-tested via the BAS template in `qa.app.d.visipoint.dev` — same pattern held: all 5 questions pre-filled correctly, one empty required field ("Next button label") blocked publish with a clear inline error, and after filling it publish succeeded cleanly with no HTTP 400/422. Still recommend closing both tickets.

### KI Board — 2 Open Bugs

| Key | Status | Priority | Summary |
|-----|--------|----------|---------|
| KI-7206 | To Do | High | [Mobile][QR code] QR code not updated after user type change |
| KI-3722 | To Do | Medium | Old Bugs (placeholder bucket — not a real ticket) |

> **CL-17843 (not in table above, found separately) — recommend closing.** Survey templates with a Multiple Choice question failing HTTP 422 on save/link was retested full-coverage on 2026-07-29: all 8 affected templates (BAS, CCS, CSS, DPS, ESS, SCF, TTQ, WUS) published successfully with every question correctly linked. See module findings below.

### Bugs Found Outside Jira Tracking (ad-hoc module testing sessions)

These were found during exploratory module testing, not filed/tracked in Jira yet as of the dates below. Cross-check against Jira before re-filing — some may have been ticketed since.

| Module | Finding | Severity | Status |
|--------|---------|----------|--------|
| Passport Account (`/passport` direct URL) | Page renders completely blank when navigated to directly via URL — no app content, only third-party chat-widget requests fire | **Downgraded from P1, 2026-08-02** | Still reproducible as of 2026-08-02 **only via direct/hard URL navigation**. New finding 2026-08-02: the real user-facing path (clicking "Passport Account" in the Cloud Dashboard sidebar) does NOT reproduce this — it correctly redirects to `visipoint.me/dashboard`, which is fully functional. Root cause refined: Vue Router's internal state never resolves past `/` for a direct `/passport` load (route name stays `null`) even though `window.location.pathname` is correct. Recommend re-triage — no real workflow is currently blocked. See `D:\Visipoint md files\Passport Account\Passport_Account_Test_Experience.md`. |
| Passport Account (`visipoint.me/dashboard`) → Add Company wizard | "Subdomain requirements" (ⓘ) info hint never shows any content when clicked, even once enabled (after selecting a Server) | **Minor (new, 2026-08-02)** | Found and root-caused 2026-08-02: click handler correctly toggles a Vue flag (`openKioskInfo`) but no template content is bound to it anywhere in the DOM — orphaned/dead code, likely copy-pasted from an unrelated Kiosk component. See `D:\Visipoint md files\Passport Account\Passport_Account_Test_Experience.md`. |
| ~~Passport Account → Add Company "Company Name" doesn't bind~~ | ~~Typed Company Name never registered in app state, blocking Create~~ | **RETRACTED 2026-08-02 — false positive** | Was a testing methodology error: checked the wrong Vue data property (`vue.name` on the wrong component instance) instead of the actual `v-model="entity.name"` binding. Retested and confirmed the form works correctly end-to-end (Create enables once all fields are properly filled). Do not re-file. |
| Dashboard | **Column sorting completely non-functional** — clicking First Name or Last Name column headers updates the sort-arrow indicator and the grid's internal `sortOrder` state, but the displayed row order never actually changes (verified via real clicks, API calls, and a clean baseline comparison, both asc/desc) | **High** | Found 2026-08-08 on production (`visipoint.uk`, "UK (Testing)" tenant). Not the same mechanism as the Users-grid Visipoint Passport sort bug (that one HTTP 400s; this one fails silently with no error). See `D:\Visipoint md files\Dashboard\Dashboard_Test_Experience.md`. |
| ~~Dashboard → bulk "Sign in/out" never performs a Check-out~~ | ~~Repeated sign-in attempts on an already-checked-in user both produced another "Checked in" entry instead of a checkout~~ | **RETRACTED 2026-08-09 — not a bug** | Found 2026-08-08 on production, "UK (Testing)" tenant, briefly logged as a High bug ("BUG-DASH-002") that appeared to contradict the 2026-08-05 pre-live finding. **User confirmed 2026-08-09 this is intentional**: re-running the sign-in action on an already-checked-in visit, selecting the same site+area, is expected to leave status as Checked in rather than toggle to Checked out. The 2026-08-05 pre-live finding (bulk action correctly offers Check-out) remains the accurate description of the real check-out path — that scenario involves selecting a *different* area than the visitor's current one, which was not what was tested on 2026-08-08. Do not re-file. See `D:\Visipoint md files\Dashboard\Dashboard_Test_Experience.md`. |
| Users grid | Sorting by "Visipoint Passport" column returns HTTP 400 and breaks the entire grid; corrupted state now persists via both a server-side `adminPreference` row and a `localStorage['vuex']` copy — survives reload/new tabs, no in-app recovery | **High** | Escalated 2026-07-30 — previously had a workaround (Active Users tab toggle), workaround no longer works. **Re-confirmed 2026-08-06 on the QA environment (`qa.app.d.visipoint.dev`, "QA testing" entity)** — not environment-specific, reproduces on both. **New recovery finding, contradicts 2026-07-30:** this session, "Clear filters" successfully recovered the grid (verified via a clean page reload afterward), where 2026-07-30 documented Clear filters as NOT working (only the internal `clearSorting()` method did). Recovery behavior may be session-dependent — worth trying Clear filters first on future reproductions. |
| Users grid | Edit User silently wipes Phone Number on save (for a user who already has a phone number set) | Major | Found 2026-07-29, not yet re-verified |
| Users grid | "Sign in/out" row action never offers Check-out — only ever shows Check-in form, no check-out path exists from the grid | Major | Found 2026-07-29, not yet re-verified. **Scoped 2026-08-05:** this is specific to the Users module's single-row action — the Dashboard's row-checkbox → bulk "Sign in/out" mass-action page (`/multiple-signin`) correctly offers and completes Check-out, confirmed end-to-end on pre-live. Do not conflate the two entry points. |
| Users grid | User Type multi-select filter throws a JS exception when 2+ types checked | — | **Not reproducible on retest** (2026-07-30) — likely a downstream symptom of the Passport-sort grid corruption above, not independent |
| User Settings → Attendance Modes | Edit Mode exposes a full JSON-encoded config blob (setting IDs, labels, types, timestamps, pivot data, UUIDs) in the URL query string, several thousand characters long | Medium | Open since 2026-06-20, re-confirmed 2026-07-15 and 2026-07-31 — no change |
| User Settings → Custom Fields | A newly-created section's field(s) never render as child rows in the list grid (data is genuinely saved correctly — confirmed via Edit — this is a display-only rendering bug) | Medium | Found 2026-07-31. **Did NOT reproduce on pre-live, 2026-08-06** — new section's field correctly appeared as a child row once its expand chevron was clicked (same chevron every section has). Root cause of the original finding may have been an automation-methodology artifact (child row rendering below the scrolled viewport after expand, easy to misread from a screenshot) rather than a real bug — see the 2026-08-06 session note in `User_Settings_Test_Experience.md`. **Needs direct re-verification on `visipoint.uk` before closing** — not yet confirmed fixed everywhere. |
| ~~Add Visits → new-visitor auto-fill (BUG-AV-001)~~ | ~~First Name auto-fill truncated, Last Name never populated~~ | **RETRACTED 2026-08-05 — not a bug** | Found 2026-07-31, re-observed with varying "truncation lengths" through 2026-08-05. Root cause confirmed 2026-08-05: First/Last Name mirror the search box live and freeze at whatever text exists when an async existing-user check fires — a timing snapshot, not a fixed truncation. Last Name **does** populate (proven with controlled tests + proper wait/blur before reading). Fields stay editable pre-submit; a real user typing continuously rarely triggers it. User confirmed intentional. Do not re-file. See `D:\Visipoint md files\Add Visits\Add_Visits_Test_Experience.md` and the `visipoint-module-testing` skill's Add Visits section. |
| Add Visits → confirmation modal | A second "Add Visit" confirmation modal briefly reappears with corrupted/blank Area text immediately after confirming a successful submission, while the form has already reset behind it | Minor (cosmetic, no data impact — verified original visit created exactly once regardless of Confirm/Cancel on the stray modal) | Found 2026-07-31, first occurrence. Did not reproduce on retest 2026-08-05 (pre-live) — confirmed intermittent, still open. |
| Add Visits → new-visitor auto-fill (BUG-AV-001) | Truncation length varies across sessions: 8 chars (2026-07-31), 3 chars "Smi" (2026-08-04), 12 chars (2026-08-05, pre-live) — same underlying bug (Last Name never populates either way) | Medium | Re-confirmed 2026-08-05 on pre-live, third distinct truncation length — reinforces this is likely timing/debounce-dependent rather than a fixed offset. Still not filed in Jira. |
| Pre-live dashboard — slow/hung SSO landing page | After Passport → company-entity SSO handoff, the Cloud Dashboard landed on a blank page for 2.5+ minutes (`document.readyState` stuck "loading", `#app` never mounted, zero network activity) before self-resolving with no retry needed. Root cause candidate: two synchronous/blocking `<script>` tags (no async/defer) loading `unpkg.com/leaflet` ahead of the app bundle — a stalled CDN request would block the HTML parser | Unconfirmed — possible High if reproducible | Found once, 2026-08-05, on `appprelive.visipoint.me` → `prelive.app.d.visipoint.dev` ("Custom field project" entity). Self-resolved without intervention. Needs re-verification on a future pre-live session before filing — not yet reproducible on demand. |
| Reporting → Kiosk Logs | Pagination missing 25/100 (only 5, 10, 50 available) | Minor | Re-confirmed still present across 4 sessions (2026-06-19, 2026-07-15, 2026-08-01, 2026-08-02), zero movement — recommend escalating to a real ticket |
| Reporting → Print List | Pagination is 50/100/150/200 — no small sizes, forces minimum 50 | Minor | Re-confirmed still present across 4 sessions, zero movement |
| Reporting → Users not on site | Pagination changed to 50/100/150/200 (was standard 5/10/25/50/100 before 2026-07-15) | Minor | Re-confirmed still present (2026-07-15, 2026-08-01, 2026-08-02) — likely a deliberate pattern for large-dataset pages across all 3 of the above, worth one consolidated product conversation rather than 3 separate bugs |
| Reporting → Export List | Toolbar no longer has an Export button (only Columns) — every prior session documented Excel/CSV/PDF export here | — | Re-confirmed still missing 2026-08-02 (2nd session), needs product confirmation whether intentional (self-referential "export the export list" button removed) or a regression |
| Login / One Time Login / Forgot Password | User enumeration — both endpoints return distinguishably different success/error text for registered vs. unregistered emails | Medium-High | Found 2026-08-04 in `qa.app.d.visipoint.dev`/`appqa.visipoint.me`. Standard email+password login form does NOT leak this on blur (safe). See `Multi_Module_Testing_Report_2026-08-04.md` Part 1. |
| Login → One Time Login | Email address passed as a plaintext GET parameter (`login-one-time?email=...`) combined with a permissive `referrer-policy` header | Medium | Found 2026-08-04 — PII leakage vector via browser history/logs/referrer. |
| Login → One Time Login | No rate limiting — 2 real login-link emails sent back-to-back instantly, no throttling/CAPTCHA/cooldown | Medium | Found 2026-08-04 — contrasts with Forgot Password, which IS rate-limited. Enables email-bombing + removes friction from the enumeration bug above. |
| Sign Up (`/register-passport`) | First/Last Name fields accept unvalidated input including HTML/script and SQLi-style strings; stored raw, no rejection | Medium | Found 2026-08-04 — no XSS executes on typing/display (renders as plain text), but stored-XSS risk if rendered unescaped elsewhere (not verified). |
| Announcements → Add Announcement | Submitting a fully valid announcement fails every time: `POST /api/announcement` → HTTP 422, error text "The selected server name is invalid." (unrelated to any real field) | **High — re-confirmed, escalation-worthy** | Found 2026-08-04 in `qa.app.d.visipoint.dev` QA tenant ("QA testing" entity), reproduced twice that session. **Re-confirmed 2026-08-06, 4/4 attempts across 2 page loads**, with field-by-field isolation: reproduces with URL populated, URL empty, Urgent/Pinned toggled, custom Publish/Expiry dates, and the simplest possible config (Immediately + Never, no URL) — ruling out any single field as the trigger. The error string references a "server name," a field never exposed anywhere in the Add Announcement UI, suggesting a backend-side misconfiguration tied to this entity/tenant rather than a form-validation bug. Same error *shape* (cryptic 422 unrelated to visible fields) as CL-17855 (Survey submit), tested the same session — possible shared root cause, not confirmed. Still contradicts the 2026-08-01 session where Add Announcement worked end-to-end on a different tenant ("UK (TESTING02)", `visipoint.uk`) — root cause is very likely entity/tenant-specific misconfiguration rather than a platform-wide regression, but this is not yet proven since a clean same-session comparison against a second QA entity was not achieved (SSO entity-redirect quirk interfered). **Filed as CL-17913** (Sub-bug of CL-17095, assignee Moataz Khaled, priority High) 2026-08-06 — this fully blocks the Add Announcement feature for this tenant. |

### Survey Module — Full Bug History (CL-17093 sub-bugs + subsequent findings)

Survey has the deepest testing history of any module (sessions from 2026-06-17 through 2026-07-29). Master experience file: `D:\Visipoint md files\Survey\Survey_Testing_Report.md` — read it before any new Survey testing session, this table is a status index only.

| Bug / Area | Jira | Found | Current Status |
|---|---|---|---|
| Template surveys created with 0 questions, activation returns HTTP 400 | CL-17682 | 2026-06-17 | **Fixed** — confirmed 2026-07-01, re-confirmed 2026-07-05 and 2026-07-09 |
| Activation error toast gives no explanation | CL-17684 | 2026-06-17 | **Fixed** — clear inline message ("Cannot activate a survey with no questions...") added by 2026-07-05 |
| Journey API/UI shows literal "null" for empty description | CL-17683 / CL-17686 | 2026-06-17 | **Fixed** — confirmed 2026-07-01, empty placeholder now shown instead |
| Report Name field has no validation ("Untitled Report" saved) | CL-17687 | 2026-06-17 | **Fixed** — confirmed 2026-07-01 (Create Report page redesign) |
| Generate Report fires immediately with no config warning | CL-17688 | 2026-06-17 | **Fixed** — same redesign fix as above, confirmed 2026-07-01 |
| No success toast after Update Journey | CL-17689 | 2026-06-17 | **Fixed** — confirmed 2026-07-09 ("Updated successfully" toast now appears) |
| No validation feedback on empty Journey Name save | CL-17690 | 2026-06-17 | **Not a bug** — reclassified 2026-07-09, matches the platform-wide silent-submit pattern (Section 6 rule 1) |
| Malformed SVG path console error on every page load | CL-17691 | 2026-06-17 | Known/tracked, by design (Section 6 rule 4) — appears to be the same underlying issue tracked as **CL-17680** in the live Jira open-bugs list (Section 4 main table, QC Review). **Ticket numbers need reconciling on next live Jira query** — don't assume they're definitely separate or definitely the same. |
| DevExtreme W0019 license-key-missing console warning | CL-17692 | 2026-06-17 | Known/tracked, by design (Section 6 rule 4) |
| Default 30-day Survey Responses filter silently excludes out-of-range responses (no "N responses outside this range" message) | CL-17685 | 2026-06-17 | **Confirmed STILL PRESENT, not fixed (retested 2026-07-31 and again 2026-08-04)** — reproduced fresh with a new test survey + real submitted response, then applying a period that excludes it: "No responses found for the selected surveys and period" with no explanatory message, while the sidebar survey list simultaneously still shows the correct "1 responses" count right next to the empty-state panel — an unexplained contradiction on top of the original silent-exclusion issue. Re-confirmed 2026-08-04 in `qa.app.d.visipoint.dev` against a survey with 31 real responses, identical contradiction. ~49 days open with no progress. |
| 8 of 19 templates (containing a Multiple Choice question in a multi-question batch) fail to save — HTTP 422 on `/link`, unreadable "[object Object]" toast | CL-17843 | 2026-07-05 | **Fixed, confirmed full coverage 2026-07-29** (all 8/8 templates: BAS, CCS, CSS, DPS, ESS, SCF, TTQ, WUS). Was still broken as recently as 2026-07-09 and 2026-07-06 — ticket had been prematurely marked "Ready for Testing" once before actually being fixed. Recommend closing. |
| `POST /api/v1/questions/survey/{id}/link` not idempotent — replaying a request creates duplicate question records | CL-17844 | 2026-07-05 | **Fixed, confirmed 2026-07-06** via 10x replay against a fresh request — only 1 question persisted |
| Comparison Report — Unmatched Questions chip renders with only the ⓘ icon, no visible label (violates AC10) | CL-17845 | 2026-07-06 | **Not reproducible, retested 2026-08-04** — checked an 11-chip Unmatched Questions section (e.g. "Q2", "Q7", "Language"), every chip shows a visible label plus the ⓘ icon. Recommend closing. |
| Comparison Report — Unmatched Questions chip tooltip missing the phrase "code represents a" (violates AC11) | CL-17846 | 2026-07-06 | **Tooltip copy fully rewritten, retested 2026-08-04** — current text: *"Q2" is a question that has no matching question in the other survey. It exists — it just has no pair to compare with.* Conveys the same information via different, clearer wording; doesn't contain the original AC's specific phrase because the copy was redesigned, not because it's still broken. Recommend re-scoping the ticket against current copy rather than re-flagging verbatim. |
| Comparison Report tooltips — overflow on narrow screens (CL-17759) / edge positioning (CL-17760) | CL-17759, CL-17760 | 2026-07-06 | **Not fully verifiable** — test environment's browser window resize didn't take effect, couldn't force a narrow viewport. Recommend a manual (non-automated) pass. |
| Comparison Report tooltip — rapid-hover flicker (CL-17761) | CL-17761 | 2026-07-06 | Not observed, but also not stress-tested — inconclusive |
| Survey Responses page survey-selector sidebar hard-caps at 20 of 42 surveys — no scroll-load-more, no search box; a real survey with live responses was unreachable via this page | Not yet filed | 2026-07-09 | **Open — re-confirmed still present 2026-07-31** (same 20-survey cap on "Select All"). Recommend filing against epic CL-16841 |
| Conditions: reordering a jump target past the current question doesn't auto-remove the now-broken condition (jump-to silently cleared instead, same treatment as a deleted target) | Sub-bug of CL-17724 (test case CL-17791) | 2026-07-16 | Reported "no longer reproducible" 2026-07-31 (multi-condition UI believed removed). **2026-08-04 update: multi-condition builder is back in the `qa.app.d.visipoint.dev` QA environment — not retested there yet (out of scope for this session).** See major note below. |
| Conditions: deleting a condition corrupts the promoted condition's jump target — it silently inherits the deleted condition's old target instead of keeping its own; confirmed via DOM inspection, persists across navigation (real corrupted state, not a render glitch) | Sub-bug of CL-17724 (test case CL-17792) | 2026-07-16 | Reported "no longer reproducible" 2026-07-31 (multi-condition UI believed removed). **2026-08-04 update: RETESTED in `qa.app.d.visipoint.dev` now that multi-condition is back — does NOT reproduce. Deleted condition 1, promoted condition 2 correctly kept its own jump target, not corrupted.** Needs re-verification on visipoint.uk directly (different environment) before closing the ticket. |
| ~~Matrix question type (Survey Builder) — builder grid's own radio/checkbox answer cells non-interactive in the default state~~ | CL-17863 (story status "To Do" in Jira, but feature is live in the QA environment) | 2026-08-03 | **RETRACTED same day — not a bug.** Builder grid is intentionally display-only; interactivity is only meant to be tested via the "Preview" modal, which works correctly (confirmed by user). 21/21 numbered ACs pass, no open findings. Tested against `qa.app.d.visipoint.dev` ("QA TESTING" entity), not the usual production-style tenant. Full AC-by-AC results in `Survey_Testing_Report.md`'s 2026-08-03 correction section. |

> **MAJOR FINDING (2026-07-31):** Retesting the two CL-17724 bugs above revealed that the entire multi-condition builder UI (numbered conditions, "Add Condition" button, per-condition jump targets — the feature both bugs depend on) **no longer exists** in the Survey Builder. Each question now supports exactly one condition block (with OR-able multi-answer matching, single jump target). Confirmed via full button enumeration in the question editor's shadow DOM — no "Add Condition" control anywhere. Both bugs describe interactions *between multiple conditions*, which is no longer a possible scenario. **This is not confirmed-fixed** — the feature was replaced, not repaired, and it's unclear whether that was intentional. Needs product/dev team confirmation before closing CL-17724's sub-bug tickets. The single condition that remains was tested end-to-end (built, published, driven through a real response submission) and routes correctly. See `Survey_Testing_Report.md`'s 2026-07-31 part 2 update for full detail.
>
> **UPDATE (2026-08-04) — the 2026-07-31 finding does not hold in the `qa.app.d.visipoint.dev` QA environment.** The multi-condition builder ("+ Add Condition" producing multiple independent, numbered condition blocks per question, each with its own answer picker and jump target, with proper mutual-exclusivity between conditions) is fully present and functional there. Retested the delete-corruption sub-bug directly: configured 2 conditions with distinct jump targets, deleted condition 1, and the promoted condition correctly retained its own original target — no corruption. **This does not mean CL-17724 is confirmed fixed on `visipoint.uk`** — the two environments may simply differ, and this needs direct re-verification on visipoint.uk before any ticket status changes. Full detail in `Survey_Testing_Report_2026-08-04.md`.

**Also confirmed working / not bugs (Survey):** Journey list "Survey" column showing "-" (same as Section 6 rule 7, confirmed 2026-07-01); Create/Save Draft/Publish toasts all correct on retest (2026-06-17 false positives removed); 30/32 CL-17724 condition test cases passed on 2026-07-16 (drag-reorder, badge numbering, answer-picker rules, jump-to picker rules, runtime routing, broken-reference detection all correct — note this was before the UI redesign observed 2026-07-31, so some of those test cases may also no longer apply); 10/10 direct Survey API valid/invalid test cases passed with clean server-side validation (2026-07-29); single-condition runtime routing re-confirmed correct 2026-07-31.

**Reusable technique — direct Survey API testing:** `api-survey.visipoint.me` needs its own bearer token + tenant ID from `localStorage.survey` (`{access_token, tenant_id}`), NOT the main app's `localStorage.user.data.access_token` (401s). Send `Authorization: Bearer <survey.access_token>` + `X-Tenant-Id: <survey.tenant_id>` headers via `fetch()` in the page's JS context.

---

## 5. Test Cases — All 100 in Survey Module

**All existing test cases target the Survey Module exclusively.** No test cases have been written yet for other modules (Users, Compliance, Sites & Devices, etc.).

### CL Board Test Cases (50 — all To Do)

**Area 1: Conditions / Jump Logic (34 test cases)**
- Conditions are evaluated top-to-bottom; first match triggers the jump
- OR logic applied within a single multi-answer condition
- An answer option can only be assigned to one condition
- Add Condition button disabled when all answers are assigned / question has only 1 answer
- Drag-and-drop reordering changes condition evaluation priority
- Broken jump targets detected when destination questions are deleted
- Warning shown if builder continues with broken jump reference
- Deleting middle condition renumbers subsequent conditions
- Deleting condition 1 promotes condition 2 as new default
- Condition auto-removed when reordering breaks forward-only constraint
- No condition fires when respondent skips the question
- Catch-all condition with multiple answers always fires correctly
- Survey proceeds to next default question when no condition matches

**Area 2: Metrics Guide PDF (16 test cases)**
- Metrics Guide button visible on Overview / Deltas / Questions tabs
- Metrics Guide button visible when report has zero/no/insufficient responses
- Clicking Metrics Guide opens PDF in an overlay within the same browser
- PDF overlay shows branded dark toolbar with document title
- Download PDF button saves file as `Survey_Metrics_Guide.pdf`
- Clicking backdrop / pressing Escape / clicking Close dismisses overlay
- Metrics Guide PDF accessible offline
- PDF overlay displayed above all other page content

### KI Board Test Cases (50 — all To Do)

**Area 1: Language Selection / Switch (27 test cases — KI-8511 to KI-8537)**
- Language buttons display flags/text per configuration
- Language selection screen is the first screen when multiple languages configured
- Survey not shown until a language is selected
- Full survey renders in English / Arabic correctly
- Answer choices, button labels, question titles translated to Arabic
- RTL layout applied correctly when Arabic selected
- No English text leaks through when Arabic selected
- Survey behaves correctly when Arabic translation is missing for a question
- Survey loads correctly when only one language configured
- Answers are reset when switching language mid-survey
- No mixed-language data saved when language switched mid-survey
- Back navigation from question 1 returns to language selection screen
- Special characters in Arabic translations handled correctly

**Area 2: Custom Choice Question Display — Kiosk (23 test cases — KI-8459 to KI-8481)**
- Each answer renders as solid rectangular button
- Question title displayed at top
- Button background / border / label color matches configuration
- No icon displayed in labels-only style
- Single-select auto-advances after selection
- Skip-enabled single-select can advance without selection
- Next button hidden for required single-select
- Multi-select allows selecting and deselecting buttons
- Multi-select Next activates after one selection
- Multi-select enforces maximum N selections
- Go-To rule navigates to configured destination
- Layout adapts to many options without overflow
- Selected state appears when button is tapped

---

## 6. Key Insights for Future Testing Sessions

### What's actively being developed (test these first):
- **Survey Module** — CL-16841 / KI-8486 — In Progress (CL-16617 Unified Survey Builder Controls)
  - Survey builder: conditions/jump logic, template activation, metrics guide
  - Kiosk: language selection, RTL (Arabic), custom choice question display
  - Known active bugs: CL-17672/CL-17675 (journey description null, Ready for Testing); **CL-17685 (response filter silently excludes out-of-range responses — confirmed still broken 2026-07-31, ~44 days open)**; CL-17845/CL-17846 (comparison chip bugs); CL-17724 Conditions reorder/delete bugs (Medium + High severity). Full status table in Section 4 "Survey Module — Full Bug History" above — **read that before assuming any Survey bug is still open**, several 2026-06-17-era findings are now confirmed fixed.

### Largest/most complex areas (risk-heavy):
- **Mobile App** (KI-1414 — 113 stories): Expected visits, emergency sessions, compliance, custom fields, two-step auth, remote sign-in, geofencing
- **Users** (CL-4385 — 62 stories): User management, roles, invitations, ACL
- **Sign in/Sign out** (CL-1179 — 61 stories): Core flow — high regression risk
- **Pre-registration** (CL-222 — 67 stories): Expected visitor workflows
- **Custom Fields** (CL-6470 — 56 stories): Dynamic fields in forms and grids

### Key business logic rules (never report these as bugs):
1. **Silent submit behavior** — all Create/Add/Save forms fail silently on empty required fields (by design). Company Details phone field is the one exception that DOES show inline validation on save.
2. **Custom fields appear in grids** — by design, not unexpected columns.
3. **Column text truncation** — by design in all grids.
4. **SVG path console errors (W0019 DevExtreme)** — known warning on every page load; tracked as CL-17680 (QC Review). Do not re-report.
5. **Disabled buttons with no tooltip** — e.g., START SESSION in Emergency List step 2 — intentional UX.
6. **Visit Permits → Delete has no confirmation dialog** — by design, even though it's inconsistent with other modules' delete flows that do confirm.
7. **Journey Builder → Survey Management: Survey column shows "-" or an internal key instead of the display name** — confirmed intentional (2026-07-01).
8. **Compliance Preview modal shows generic "agreement" text + "Document Binding" fields for file-based Agreement compliances** — confirmed intentional (2026-07-31).
9. **Compliance Preview modal shows only the name (no other content) for Document - Vaccine/PCR compliances** — confirmed intentional (2026-07-31).
10. **Compliance Edit form doesn't pre-populate Name/Select Protocols/Accept Negative PCR for Document - Vaccine/PCR compliances** (Type + Document Uploaded/Not Uploaded fields still load correctly) — confirmed intentional (2026-07-31).
11. **Company Details Phone field has no minimum/maximum length validation** (only a digits-only check; 3-digit and 30-digit values both save) — confirmed intentional (2026-07-31).
12. **Compliance module URL is `/compliances` (plural)** — `/compliance` 404s. Navigation gotcha, not a bug.
13. **Emergency Sessions — Active Session grid (`/emergency-sessions/{name}/{id}`) only shows scan-status sign-ins, not visit-status check-ins** (e.g. from Dashboard Quick Sign In) — confirmed intentional (2026-07-31). `/emergency-list` is the page that shows both; don't expect the two pages to show the same population.
14. **Emergency Sessions — Start Session modal's "Emergency Type" field has `maxLength=20`, not the 33 documented in Jira story CL-3443** — confirmed intentional (2026-07-31), CL-3443's AC is outdated.
15. **Add Visits — new-visitor First/Last Name fields mirror the search box live and freeze at whatever text exists when an async existing-user check fires** (a timing snapshot, not a fixed truncation) — confirmed intentional (2026-08-05), formerly mis-tracked as BUG-AV-001. Fields stay editable before submit. Don't re-report as truncation/data-loss.
16. **User Settings — Attendance Modes only apply to a user type whose Registration Method is "Pre-registered by admin"** (e.g. the default "Staff" user type) — confirmed by user (2026-08-10). Other registration methods (Registration allowed, Pre-registration with approval required, Pre-registered only, Registration not required) are out of scope for this feature; seeing no effect when testing against those user types is expected, not a bug.

### Confirmed-fixed bugs (re-verify only if regression suspected, don't re-file):
- **Users grid — "Add Label" Enter-key clearing the input instead of creating a tag** — confirmed fixed 2026-07-30.
- **Users — "Last Name" duplicate label in Edit User modal** (both first/last name fields showed "Last Name") — fixed as of 2026-07-27.
- **Company Details — silent failure saving an invalid phone number** — confirmed fixed 2026-06-30, and further improved by 2026-07-15 (validation is now fully client-side, zero API calls before the error shows).
- **CL-17843 — Survey templates with a Multiple Choice question failing HTTP 422 on save** — confirmed fixed, full coverage across all 8 affected templates (2026-07-29). Recommend closing the ticket.
- **CL-17671 / CL-17673 — Survey template activation HTTP 400 / unclear error** — likely fixed; could not reproduce via standard flow on 2026-07-27 (see note above). Needs QC re-verification against original repro steps before closing.
- **User Settings — Visit Permits Delete confirmation dialog** — confirmed present and working 2026-07-31 (created and deleted a real test permit). This corrects a 2026-07-01 note that had concluded "no confirmation dialog, by design" without ever actually clicking Delete to verify. Treat the dialog as existing going forward.
- **Add Visits — BUG-AV-001 (new-visitor First/Last Name "truncation")** — retracted 2026-08-05, confirmed intentional logic (live-mirror-then-freeze timing behavior, not data loss). See Section 6 rule 15.

### Parent/child area logic (complex sign-in rules):
- A user signed into a parent area can check into a child area
- If parent area has an 'Expected' scan, child area entry follows different rules
- Visit permits (KI-7663) can allow or deny entry by permit type
- Changing a user's type mid-visit has documented behavior for both signed-in and not-signed-in states

### Compliance frequency matters:
- Compliance can be set to ask every visit, once, or on a schedule
- If compliance was already completed, it may not re-appear on next sign-in
- System users can re-send compliance to a visitor

---

## 7. Module Testing Status (Living — update after every session)

Quick reference for which modules have been exercised outside Jira's own test-case tracking (Section 5 — currently Survey-only), when, and where the detailed notes live. Read the linked experience file before re-testing a module; it has full selectors, automation patterns, and step-by-step bug repro details that don't belong in this summary.

| Module | Last Tested | Detailed Experience File | Status Summary |
|--------|-------------|---------------------------|-----------------|
| Login page (security) | 2026-08-04 | `Multi_Module_Testing_Report_2026-08-04.md` Part 1 | New: 3 security findings (user enumeration via One Time Login/Forgot Password, email-in-URL, no rate limiting on One Time Login). Standard login form leaks nothing on blur, no reflected XSS, HTTPS enforced. Password-field-dependent checks (lockout, SQLi-in-password) deferred. |
| Forgot Password | 2026-08-04 | `Multi_Module_Testing_Report_2026-08-04.md` Part 2 | Enumeration finding applies (see Login row). Rate limiting confirmed working. Full reset-link completion deferred (password field). |
| Sign Up | 2026-08-04 | `Multi_Module_Testing_Report_2026-08-04.md` Part 3 | New: no input validation on Name fields (stored-XSS risk, unconfirmed downstream). Duplicate-email/malformed-email/empty-submit all correctly blocked. Full temp-password completion deferred (inbox access). |
| Dashboard | 2026-08-09 (correction pass following 2026-08-08 P0/P1 smoke test, production `visipoint.uk`, "UK (Testing)" tenant) | `D:\Visipoint md files\Dashboard\Dashboard_Test_Experience.md` | **1 new High bug found and confirmed:** column sorting is completely non-functional (arrow/state updates, rows never reorder — confirmed on First Name and Last Name). **A second finding from 2026-08-08 (bulk/per-row "Sign in/out" never performs Check-out) was retracted 2026-08-09 — user confirmed this is intentional logic**, not a contradiction of the 2026-08-05 pre-live finding (real check-out requires selecting a different area than the visitor's current one; re-selecting the same area is correctly a no-op re-check-in). Everything else passed: grid load, date-range widening, search, pagination, and a full Quick Sign In end-to-end. Test visitor "QC SmokeVisitor" remains in "Checked in" state on production (not destructive, just residue from an intentional-behavior test). |
| Dashboard | 2026-08-04 | `Multi_Module_Testing_Report_2026-08-04.md` Part 4 | No new bugs. Date-range picker normalizes reversed clicks correctly. Observation (unconfirmed): narrowing date range didn't visibly change grid row count — needs a closer look. |
| Users grid | 2026-08-08 (P0/P1 smoke test, production `visipoint.uk`, "UK (Testing)" tenant) | `D:\Visipoint md files\Users\Users_Grid_Test_Experience.md` | **PASS — no smoke-test failures.** Grid load/display, search, filter, sort, pagination, full Add→Edit→Delete User cycle (disposable test user, cleaned up), Sign in/out modal, Select Actions dropdown all confirmed working on production. "Clear filters" and Delete-confirmation-username fixes both re-confirmed holding on production specifically. New doc-only findings: Add User wizard actually has 3 steps (not 2 — a third custom-fields page exists on this tenant), and the "ID" field is silently required to advance past step 1. One-off observation: first cold navigation to `visipoint.uk/` took ~5 min to boot (many chunk requests pending), second navigation was fast (~5-8s, cache warm) — not filed as a bug, see file for detail. |
| Users grid | 2026-08-06 (monkey/chaos testing pass, QA environment, "QA testing" entity, 327 users) | `D:\Visipoint md files\Users\Users_Grid_Test_Experience.md` | **Visipoint Passport sort bug re-confirmed HIGH, now confirmed on QA too** (not visipoint.uk-specific) — see Section 4. New recovery finding: "Clear filters" worked this session, contradicting 2026-07-30's finding that only internal `clearSorting()` worked. **No crashes found from extensive chaos testing**: rapid tab toggling, combined pagination+page-size spam, rapid select-all/deselect, firing 3 mass-action buttons in one batch (modal backdrop correctly blocks stacking), Escape-key spam on unsaved-data modal (correctly ignored), rapid triple-click on ID-Generate button (correctly idempotent). New Add User validation findings: First/Last Name have real `maxLength=20` (safely truncates XSS/SQLi payloads), Email has genuine inline validation, Phone silently rejects non-digits, column search filters accept unbounded garbage input safely (rendered as plain text, no crash). Add button's silently-required-custom-fields gotcha re-confirmed on this entity — prevented completing a full double-submit/duplicate-creation test, carried over to next session. |
| Compliance | 2026-08-04 (spot regression in new QA env; full pass still 2026-07-30/31) | `D:\Visipoint md files\Compliance\Compliance_Test_Experience.md` / `Multi_Module_Testing_Report_2026-08-04.md` | No open bugs. Text-based Agreement Preview modal renders correctly with real content. |
| Company Details | 2026-08-04 (spot regression in new QA env; full pass still 2026-07-31) | `D:\Visipoint md files\Company Detalis\Company_Details_Test_Experience.md` / `Multi_Module_Testing_Report_2026-08-04.md` | No open bugs. Phone-field validate-on-save behavior re-confirmed intentional, unchanged. |
| Survey Module | 2026-08-04 (full regression pass over all prior scenarios) | `D:\Visipoint md files\Survey\Survey_Testing_Report.md` (master — has full dated UPDATE log back to 2026-06-17) | **2026-08-04 regression (in `qa.app.d.visipoint.dev` QA environment):** Templates (CL-17671/17673/17843) still fixed. CL-17685 response-filter bug re-confirmed still open. Responses 20-survey cap still open. New minor finding: extreme-past date range causes a hard load error instead of graceful empty state. Survey Overview (all 4 tabs) working. Comparison Report CL-17845 not reproducible (recommend closing); CL-17846's tooltip copy was fully rewritten (recommend re-scoping). Journeys: Survey column "-" still by design, CL-17683/17686 still fixed. **Headline finding:** the multi-condition builder (thought removed per 2026-07-31) is present and functional in this QA environment — retested CL-17724's delete-corruption sub-bug and it does NOT reproduce, but this needs re-verification on visipoint.uk directly since environments may differ. **Also (2026-08-03):** CL-17863 (Matrix Question Type) is fully implemented and live despite Jira showing "To Do" — 21/21 numbered ACs pass, no open bugs. Full detail in Section 4 above and `Survey_Testing_Report_2026-08-04.md` / the 2026-08-03 correction section. |
| Passport Account | 2026-08-02 (full system test — all buttons/fields; same-day correction applied) | `D:\Visipoint md files\Passport Account\Passport_Account_Test_Experience.md` | Severity of the "blank page" bug downgraded — only reproduces via direct URL nav, the real sidebar-click path works fine via redirect to visipoint.me/dashboard. 1 confirmed Minor bug: Add Company wizard's "Subdomain requirements" hint is dead code (handler fires, nothing renders). 1 bug retracted same-day: "Company Name doesn't bind" was a false positive from checking the wrong Vue property — retested and the Add Company form works correctly end-to-end. Everything else on the real Passport Account screen (profile edit, password/2FA/deactivate flows, QuickPass PDF, My Companies navigation, entry logs/expected visits tabs, Sign Out) tested and working correctly. |
| Announcements | 2026-08-08 (P0/P1 smoke test, production `visipoint.uk`, "UK (Testing)" tenant) | `D:\Visipoint md files\Announcements\Announcements_Test_Experience.md` | **PASS — no smoke-test failures.** Grid load/display, search, filter, sort, full Add→View(Preview)→Delete cycle (disposable test announcement, cleaned up) all confirmed working on production. Bootstrap fade-transition fix and Delete-modal-content still holding. **BUG-ANN-001 did NOT reproduce here** — reinforces it's scoped to the QA "QA testing" entity, not platform-wide. Edit icon not available for either row this session (permission-gated, not a bug). Automation-only: the documented viewport-shrink quirk reproduced again (recovered via navigate+resize), and the eye/trash icon click handlers needed several attempts before firing — consistent with prior sessions' documented flakiness. |
| Announcements | 2026-08-06 (full valid/invalid pass, QA environment, "QA testing" entity) | `D:\Visipoint md files\Announcements\Announcements_Test_Experience.md` / `Announcements_Testing_Report_2026-08-06.md` | **BUG-ANN-001 re-confirmed, filed as CL-17913:** Add Announcement's `POST /api/announcement` returns HTTP 422 "The selected server name is invalid." on every submit attempt (4/4 across 2 sessions), isolated to rule out any single field as cause. See Section 4. Everything else on the Add form validated correctly and is well-implemented: Title/Body/target required (real disabled-button gating, not silent-submit), URL field has genuine format validation despite being labeled optional, Publish/Expiry date-time pickers correctly block past dates and past times for today, and Expiry correctly enforces "must be ≥ Publish" at the date/hour/AM-per-PM level via a real min-datetime cross-field check — the best-implemented date validation seen in any module so far. One gap: Title accepts whitespace-only input (button enables, no trim check) — minor. Grid-level controls (Columns, Export, filter/sort) unchanged from prior sessions. Edit/Delete/other row actions blocked from full testing since Add never succeeds (no row to act on) — carried over to next session. |
| Reporting | 2026-08-04 (spot regression in new QA env; full pass still 2026-08-02) | `D:\Visipoint md files\Reporting\Reporting_Test_Experience.md` / `Multi_Module_Testing_Report_2026-08-04.md` | 3 pagination inconsistencies re-confirmed present, now 5 sessions with zero movement — recommend escalating. Timesheet showed an async "Generating..." state by default this session (possible tenant-default difference, not confirmed as a bug). |
| User Settings | 2026-08-06 (full pass, all 6 sub-sections, on new **pre-live** server) | `D:\Visipoint md files\User Settings\User_Settings_Test_Experience.md` | Full CRUD confirmed working on User Types, Attendance Modes, and Visit Permits (fresh test records, add/edit/delete cycles). No new bugs. Attendance Modes URL data-exposure (BUG-002) re-confirmed still present. Visit Permits delete-confirmation-dialog correction re-confirmed. **Custom Fields "new section never shows children" did NOT reproduce** — needs re-verification on visipoint.uk before closing (see Section 4). New gotcha: Custom Fields "Add Section" form has a required-but-silent User Type field. Privacy Manager column-visibility toggle confirmed working; password-gated actions not tested (policy). Duplication Control Board and Custom Fields Field Type list unchanged from prior docs. |
| Journey Builder | 2026-08-04 (spot regression in new QA env; full pass still 2026-07-01) | `D:\Visipoint md files\Journey Builder\Journey_Builder_Test_Experience.md` / `Multi_Module_Testing_Report_2026-08-04.md` | Survey Management column display still by design. Create Journey form loads correctly with Visit Management type pre-selected. |
| Sites & Devices | 2026-08-04 (first pass) | `Multi_Module_Testing_Report_2026-08-04.md` | No bugs found. Hierarchical Site→Area→Device grid loads correctly. Add Site modal: empty-submit blocked, malformed Contact Email correctly caught with real-time inline validation. |
| Emergency Sessions / Emergency List | 2026-08-04 (spot regression in new QA env; full pass still 2026-07-31) | `D:\Visipoint md files\Emergency\Emergency_Test_Experience.md` / `Multi_Module_Testing_Report_2026-08-04.md` | No open bugs. Confirmed fully working in the correct-access "QA TESTING" tenant; a plan-restricted sibling tenant ("QA testing02") shows the feature greyed out/404ing, which is a plan limitation, not a bug. |
| Passport Account | 2026-08-04 (spot regression in new QA env; full pass still 2026-08-02) | `D:\Visipoint md files\Passport Account\Passport_Account_Test_Experience.md` / `Multi_Module_Testing_Report_2026-08-04.md` | Direct-URL blank-page bug re-confirmed still present; real sidebar-click path re-confirmed fully working via SSO redirect. |
| Quick Sign In | 2026-08-05 (first-ever session, on new **pre-live** server) | `D:\Visipoint md files\Quick Sign In\Quick_Sign_In_Test_Experience.md` | Full valid/invalid pass on pre-live (`prelive.app.d.visipoint.dev`, "Custom field project" entity). No confirmed bugs. Confirmed working: new/existing-visitor sign-in end-to-end, Last Name required (differs from Add Visits), Visit-Permit/journey restriction correctly requires a 20+ char justification note when the User Type isn't permitted in the selected Area, cross-site auto-signout produces correct dual grid rows (new "Signed In" + old "Auto Signed Out"), empty-submit silent no-op, Cancel is clean. One unresolved, not-yet-confirmed anomaly: "Sign in" stayed disabled once after heavy wizard back/forth navigation with a specific user+Site/Area combo — a clean single-pass retest with a different user worked fine; needs re-verification before filing. |
| Add Visits | 2026-08-05 (full valid/invalid matrix pass on new **pre-live** server) | `D:\Visipoint md files\Add Visits\Add_Visits_Test_Experience.md` | Full flow + exhaustive valid/invalid scenario matrix on pre-live (`prelive.app.d.visipoint.dev`, "Custom field project" entity, Site10/field). **BUG-AV-001 RETRACTED — confirmed not a bug** (see Section 6 rule 15): the "truncation" is a live-mirror-then-freeze timing snapshot, not fixed truncation, and Last Name does populate. Confirmed working correctly: past-time block (Today), past-date block (Custom date calendar disables past dates entirely), empty-submit silent no-op, duplicate-visit detection, existing-user search, email format validation, phone digits-only enforcement, XSS/SQLi payloads safely handled in custom fields (maxLength-truncated or safely rendered as plain text). One minor config-dependent observation: "Age" number field accepts negative values (no min set) — likely a per-field config gap, not filed. Stray-second-modal cosmetic bug did NOT reproduce this run (intermittent). No "(Europe/Moscow)" label mismatch on this entity — correctly showed "(Africa/Cairo)". Also see Section 4 for a pre-live dashboard slow-load observation (not Add-Visits-specific, self-resolved). |

**Rule for future sessions:** whenever a module is tested (full pass or spot-check), update this table's row (last-tested date + one-line status) in addition to the module's own `*_Test_Experience.md` file. If Jira ticket status changes as a result (bug confirmed fixed/still-present, or a new bug should be filed), update Section 4 too.

---

## 8. Issue Type Reference

Both CL and KI boards have the same 9 issue types:
- **Epic** — top-level feature area
- **Story** — user story / feature requirement
- **Bug** — defect
- **Task** — non-story work item
- **Sub-task** — part of a task
- **Sub-bug** — sub-issue of a bug
- **Test Case** — QA test cases (currently only in Survey Module)
- **Feature** — high-level feature
- **Spike** — research/investigation task

---

*Document generated: 2026-06-30 | Source: Full Jira read + live bug/test case queries*
*Last updated: 2026-08-08 | Sections 4, 6, and 7 are living — updated after each module testing session with findings from `D:\Visipoint md files\<Module>\*_Test_Experience.md` and `*_Testing_Report_*.md` files. Section 1–3, 5, 8 reflect the last full Jira sync (2026-06-30) and should be refreshed by re-querying Jira, not by testing sessions.*
