# User Settings Module — Smoke Test, Field/Button Walkthrough & API Performance
**Date:** 2026-07-15
**Tester:** Claude (automated browser testing via Chrome MCP)
**Module URL:** `https://visipoint.uk/user-types` (and 5 sibling sub-sections)
**Environment:** UK (Testing) company / grinta1911.visipoint.uk
**Reference:** [[project_jira_overview]], [[project_knowledge_synthesis]], [[project_user_settings_experience]]
**Scope:** All 6 sub-sections (User Types, Privacy Manager, Duplication Control Board, Attendance Modes, Custom Fields, Visit Permits) tested with real data. Every field, dropdown, toggle, and modal was opened and exercised. No record was created, edited, deleted, or had its retention/privacy settings changed — see "What Was Not Submitted."

---

## 1. User Types (`/user-types`)

| # | Area | Result |
|---|------|--------|
| 1 | Grid load (4 records, matches baseline) | ✅ Pass |
| 2 | Select Actions dropdown | ✅ Pass — but **no Delete option shown** for any of the 4 default records (Edit + Visit Permits only) — likely because built-in user types can't be deleted; not previously documented |
| 3 | Edit modal | ✅ Pass — **new field found: "Auto-approve visit requests" toggle**, with an explanatory note ("When enabled, visit requests are automatically approved if compliance is passed or not required..."). Not previously documented. Cancelled without saving. |
| 4 | Add User Type form | ✅ Pass — matches docs. Dynamic behavior confirmed for **two** Registration Methods (previously only "Registration allowed" was documented): "Registration allowed" reveals a Profile Photo checkbox; **"Pre-registration with approval required" reveals Visit Grace Period + the same new Auto-approve toggle + Profile Photo checkbox.** Not submitted. |

---

## 2. Privacy Manager (`/privacy-manager`)

| # | Area | Result |
|---|------|--------|
| 1 | Grid load (4 rows, retention values) | ✅ Pass |
| 2 | Gear icon → View List Settings modal | ✅ Pass — matches docs (toggles now instead of checkboxes) |
| 3 | Available Actions dropdown (Edit / Bulk Delete All Data & Scans) | ✅ Pass, matches docs. **Not clicked** — Bulk Delete is destructive and irreversible |
| 4 | Edit (Data and Scans) modal | ✅ Pass — password-gated, matches docs. Cancelled without attempting a password |
| 5 | Top-right "Privacy Manager" toggle modal | ✅ Pass — currently ON globally, password-gated. Cancelled without changes |

**Not clicked (destructive, by design this session):** any "DELETE SURPLUS NOW" button, any "DELETE NOW" (date-based) button, Bulk Delete All Data & Scans.

---

## 3. Duplication Control Board (`/duplication-control-board`)

Matches docs exactly — "No matching data.." (no duplicates in this environment). Tested First Name text filter (functional) and the **Reason dropdown filter**, which shows real duplicate-detection reason values: **Full Name, User ID, RFID, Face ID, Email** — not previously enumerated in docs.

---

## 4. Attendance Modes (`/attendance-modes`)

| # | Area | Result |
|---|------|--------|
| 1 | Grid + tree expand (parent + Sign in/Sign out children) | ✅ Pass |
| 2 | Parent row Select Actions | ✅ Pass — **new option found: "Delete Mode"** (red), alongside the documented Edit Mode and Add Reason. Not clicked. |
| 3 | Child row (Sign in/Sign out) Select Actions | ✅ Pass — **new: "Edit Reason" / "Delete Reason"**, not previously documented (docs only covered the parent row). Not clicked. |
| 4 | Edit Mode full-page form | ✅ Pass — every field confirmed matching docs (Mode Name, Schedule Days, Allow-only-specified-days checkbox, From/To time, Prevent-before/after checkboxes, Integration dropdown, Sign-in settings with late-sign-in minutes + On Time Attendance Code, Sign-out settings mirrored). **URL data-exposure concern re-confirmed** — the edit page still carries a large JSON blob (attendance codes, settings, IDs) in the query string. Not saved. |
| 5 | Add Reason page (`/add-reason`) | ✅ Pass — **fully undocumented page found.** Fields: Attendance Mode (read-only), Reason Name, Presented reason (optional), Reason Type dropdown (Sign in / Sign out), Attendance Code dropdown + From/To time pickers, "Add New" button, "Add Reason" submit. Character-limit note: "Reason must be unique with 20 characters max." Not submitted. |

---

## 5. Custom Fields (`/custom-fields`)

| # | Area | Result |
|---|------|--------|
| 1 | Grid (Main Section, Test + YTYT fields) | ✅ Pass, matches docs — now also shows "Approval" in the User Type list (Staff-Visitor-Walk-in-Approval) |
| 2 | Add dropdown (Add to Main Section / Add new section) | ✅ Pass, matches docs |
| 3 | Edit Section page | ✅ Pass — Section Name, binding radio (User Profile/Visit), existing fields (Test: Checkboxes, YTYT: Radio buttons) all confirmed with their full right-panel config (Mandatory, Fillable by user, Printable on badge, Show on, pre-registration checkbox) |
| 4 | **Field Type dropdown — full list confirmed: Checkboxes, Radio buttons, Text, Number, Date, Long text, Rating scale, Toggle button (8 total)** — docs previously only said "e.g., Checkboxes, Radio buttons, Text, etc." |
| 5 | Dynamic right-panel per field type | ✅ Pass — confirmed **Toggle button** type has no Mandatory toggle and no Options fields (unlike Checkboxes/Radio), since a toggle is inherently binary. Not previously documented. |

Not saved — navigated away without submitting any change to the live Main Section.

---

## 6. Visit Permits (`/visit-permits`)

| # | Area | Result |
|---|------|--------|
| 1 | Grid (1 record "BV", direct Edit/Delete buttons, standard pagination) | ✅ Pass, matches docs |
| 2 | Add Visit Permit modal — all fields | ✅ Pass — Permit name, description, Start/End Date, Start/End Time, Recurrence Settings accordion (Days of month, **Days of week — confirmed real dropdown with Monday–Sunday**, Months, Years) |
| 3 | Validation | ✅ Pass — "Permit name is required" and "Start Date is required" both confirmed with red borders, matches docs exactly |
| 4 | Edit Visit Permit modal | ✅ Pass — pre-fills all existing data correctly, submit button labeled "Edit" |

Not submitted/saved — cancelled both Add and Edit without creating or modifying a record. Delete not tested (only 1 record, no confirmation dialog per prior finding — would be unrecoverable).

---

## 7. API Performance — User Settings Module

Measured via the browser's Resource Timing API during real page loads. API host: `api.visipoint.uk`.

| Endpoint | Page(s) | Samples | Values (ms) | Avg (ms) |
|---|---|:---:|---|---:|
| `check_active_sessions` | User Types, Privacy Manager, Visit Permits | 4 | 544, 463, 263, 249 | 380 |
| `adminPreference` | All pages | 5 | 370, 419, 231, 250, 273 | 309 |
| `user-types` | User Types (2x) + Privacy Manager | 3 | 414, 473, 299 | 395 |
| `users_entities` | Privacy Manager | 1 | 357 | — |
| `cron_jobs` | Privacy Manager | 1 | 255 | — |
| `section` | Custom Fields | 1 | 289 | — |
| `visit-permits` | Visit Permits | 1 | 253 | — |

**Observation:** `user-types` is called even on pages other than the User Types grid itself (e.g. Privacy Manager) — likely a shared lookup used to populate the per-user-type rows on multiple pages. `cron_jobs` appearing on Privacy Manager is a new, unexplained endpoint worth noting — plausibly related to the scheduled data-retention deletion jobs that page configures.

**Scope note:** Single-user, single-request latency sampling — not a concurrent load/stress test. Consistent with today's other sessions, all endpoints stayed in a fast-to-moderate range (231–544ms) with no extreme outliers this time.

---

## 8. What Was Not Submitted (by design, this session)

- **Privacy Manager:** DELETE SURPLUS NOW, DELETE NOW, Bulk Delete All Data & Scans, the password-gated Edit (Data and Scans) modal, and the global Privacy Manager ON/OFF toggle — all destructive or company-wide-impacting, none attempted
- **User Types:** Add form filled but not submitted; Edit modal (with the new Auto-approve toggle) viewed but not saved
- **Attendance Modes:** Edit Mode form viewed but not saved (Edit button not clicked); Add Reason page filled with dropdown checks but not submitted; Delete Mode / Delete Reason not clicked
- **Custom Fields:** Edit Section page — new field type ("Toggle button") added to the form to inspect its dynamic config, then navigated away without saving
- **Visit Permits:** Add modal filled (name + recurrence dropdown) but not submitted; Edit modal viewed but not saved; Delete not tested (only 1 record, no confirmation dialog)
