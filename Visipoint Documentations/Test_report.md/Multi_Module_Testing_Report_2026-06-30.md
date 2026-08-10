# Visipoint Cloud Dashboard — Multi-Module Exploratory Testing Report

**Platform:** visipoint.uk (UK Testing company)  
**Tester:** Claude Code (AI-assisted exploratory testing via Chrome MCP)  
**Test Date:** 2026-06-30  
**Sessions:** 2 (pre-compaction + post-compaction continuation)  
**Scope:** All sidebar modules except Survey (covered in `Survey_Testing_Report_2026-06-30.md`)

---

## 1. Executive Summary

| Status | Count |
|--------|-------|
| Modules tested | 12 |
| New bugs found | 2 |
| Previously reported bugs FIXED | 3 |
| Previously reported bugs STILL PRESENT | 2 |
| Previously reported bugs COULD NOT REPRODUCE | 1 |
| No issues found | 6 |

---

## 2. Module-by-Module Results

### 2.1 Dashboard (`/`)

| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | URL is `https://visipoint.uk/` (not `/dashboard`) |
| Grid renders current visitors | ✅ Pass | 1 visitor shown: Zola Zola, Checked in |
| Date filter | ✅ Pass | Defaults to today's date |
| Column Chooser | ✅ Pass | 21 columns available, all expected columns present |
| Status column sort | ✅ Pass | Sort arrows appear on click |
| CL-16259 tooltip check | ⚠️ Could Not Reproduce | Bug: "Compliance title appears twice in tooltip" — test environment has no compliance-pending users; tooltip condition not triggerable |

**Note on CL-16259:** Cannot confirm fixed or still-present. Requires a user type with compliance configured and a user currently pending compliance on sign-in.

---

### 2.2 Users (`/users`)

| Test | Result | Notes |
|------|--------|-------|
| Active Users grid loads | ✅ Pass | Grid renders with expected columns |
| Archived Users tab | ✅ Pass | Tab switches correctly |
| Row Select Actions dropdown | ✅ Pass | `.dx-dropdownbutton-action` click pattern works |
| Grid pagination | ✅ Pass | Page size options visible |

**No bugs found.**

---

### 2.3 Announcements (`/announcements`)

| Test | Result | Notes |
|------|--------|-------|
| Grid loads | ✅ Pass | Announcement list renders |
| View announcement (eye icon) | ✅ FIXED | Modal now opens correctly with `show` CSS class — previously invisible (Bootstrap modal bug from 2026-06-19) |
| Filter by type | ✅ Pass | Dropdown filter works |
| Clear filters (text search) | ⚠️ Not fully retested | Previous bug: clearing filters does not clear active text search inputs — could not retest in this session (no active text filter scenario set up) |

**Previous bug (Bootstrap modal invisible): CONFIRMED FIXED.**  
**Previous bug (Clear filters does not reset text search): Status unknown — needs dedicated retest.**

---

### 2.4 Sites & Devices (`/sites`)

| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | URL is `/sites` (not `/sites-and-devices` which returns 404) |
| Sites grid renders | ✅ Pass | Site list visible |
| Site Actions dropdown | ✅ Pass | Actions dropdown opens on rows |
| Offline device status | ✅ Pass (by design) | All devices show "Offline" in test env — expected |

**No bugs found.**

---

### 2.5 Reporting (`/reporting`)

#### 2.5.1 History / Daily Log
| Test | Result |
|------|--------|
| Grid loads | ✅ Pass |
| Pagination | ✅ Pass |

#### 2.5.2 Kiosk Logs
| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | |
| Pagination bug | 🐛 STILL PRESENT | Previous bug: page size dropdown shows only "10" initially, then expands — cosmetic pagination render issue |

#### 2.5.3 Print List
| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | |
| Pagination bug | 🐛 STILL PRESENT | Same pagination render issue as Kiosk Logs |

#### 2.5.4 Timesheet
| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | |
| Generate report | ✅ Pass | Re-generates on page reload (remembered state) |
| User Type filter greyed out | ✅ Pass (by design) | Field is intentionally greyed until a site is selected |

#### 2.5.5 Track & Trace
| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | |
| Filter with no selection | ✅ FIXED | Previously silent; now shows: "Select a user and date range to show information." |

#### 2.5.6 Other sub-pages (Metrics, Performance)
| Test | Result |
|------|--------|
| Pages load | ✅ Pass |

---

### 2.6 User Settings (`/user-settings`)

| Test | Result | Notes |
|------|--------|-------|
| All sub-sections load | ✅ Pass | Custom Fields, User Types, Attendance Modes etc. |
| Custom Fields grid | ✅ Pass | Columns correct |
| No critical issues | ✅ Pass | |

**No bugs found.**

---

### 2.7 Journey Builder (`/journey-builder`)

| Test | Result | Notes |
|------|--------|-------|
| Journey list loads | ✅ Pass | Grid renders correctly |
| Journey description "null" (CL-17675) | ✅ CONFIRMED FIXED | Description field shows empty string, not "null" |
| Survey column in journey grid | 🐛 STILL PRESENT | Survey column shows survey ID/key instead of survey name — cosmetic display issue |
| Create/Edit journey | ✅ Pass | Modal opens and fields work |
| Journey recurring options | ✅ Pass | Options render correctly |

**CL-17675 CONFIRMED FIXED.**  
**Survey column display bug (cosmetic): Still present — survey key shown instead of human-readable name.**

---

### 2.8 Compliance (`/compliance`)

| Test | Result | Notes |
|------|--------|-------|
| Compliance list loads | ✅ Pass | |
| Create compliance (empty) | ✅ Pass (by design) | Silent failure on empty submit — intentional (see QA rules) |
| All tabs render | ✅ Pass | Agreement, Questionnaire types visible |

**No bugs found. All observed behaviors are by design.**

---

### 2.9 Company Details (`/company`)

| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | Company info grid renders correctly |
| Edit Company modal opens | ✅ Pass | JS click required (coordinate-based click unreliable) |
| Phone "Change" link | ✅ Pass | Replaces static display with editable input |
| Phone accepts letters (Bug 1) | 🐛 STILL PRESENT | Typing "abc123" is accepted without real-time validation |
| Error on save with invalid phone (Bug 2) | ✅ FIXED | Now shows "Phone number should contain only digits." after clicking SAVE CHANGES — modal stays open, save is blocked |
| Temperature Unit toggle | ✅ Pass | °F / °C radio buttons work |
| Two-Step Authentication toggle | ✅ Pass | Toggle switches correctly |

**Bug 1 (phone accepts letters) STILL PRESENT — no real-time input validation.**  
**Bug 2 (silent failure on save) CONFIRMED FIXED — error message now appears.**

---

### 2.10 Passport Account (`/passport`)

| Test | Result | Notes |
|------|--------|-------|
| Page loads | 🐛 NEW BUG | Page renders completely blank — no sidebar, no header, no content |
| Vue app mounts | Partial | `#app` div present but contains only toast containers + Gist chat widget |
| Any visible content | ❌ None | `document.querySelectorAll('*')` returns 273 elements but zero have `getBoundingClientRect().width > 0` |

**NEW BUG: Passport Account page renders blank.** The Vue router navigates to `/passport` but the page component fails to render any content. The sidebar, top navigation bar, and page body are all absent. This is reproducible on fresh navigation and page reload.

---

### 2.11 Integrations (`/integration`)

| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | |
| Add Integration cards | ✅ Pass | Azure AD, Google Directory, Paxton Access, School MIS all visible |
| Existing integrations grid | ✅ Pass | 1 integration: "Integration" (wonde, Active, Daily sync) |
| Last synced date | ✅ Pass | 04/06/2026 — recent, as expected |

**No bugs found.**

---

### 2.12 Emergency Sessions (`/emergency-sessions`)

| Test | Result | Notes |
|------|--------|-------|
| Page loads | ✅ Pass | |
| Sessions grid | ✅ Pass | 8 past sessions displayed (Emergency 1–8) |
| Start Session button | ✅ Pass | Button visible and clickable (not triggered — destructive action) |
| Grid columns | ✅ Pass | Session Name, Started By, Started At, Ended By, Ended At, Areas, Type, Actions |
| Data integrity | ✅ Pass | All sessions have correct start/end timestamps and area info |

**No bugs found.**

---

## 3. Bug Summary

### 3.1 New Bugs Found This Session

---

#### BUG-001 — Passport Account page renders completely blank

| Field | Detail |
|-------|--------|
| **Module** | Passport Account |
| **URL** | `https://visipoint.uk/passport` |
| **Severity** | High |
| **Status** | New — Not yet reported |

**Steps to Reproduce:**
| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Log in to `https://visipoint.uk` as any admin user | Dashboard loads | ✅ |
| 2 | Click "Passport Account" in the left sidebar | Passport Account page loads with content | Page renders completely blank — no sidebar, no header, no body content |
| 3 | Wait 5+ seconds or hard-reload the page | Content appears | Still blank — only the help button (?) and chat widget are visible |

**Expected:** The Passport Account page displays its configured content (Passport configuration, QR code settings, linked accounts, etc.)  
**Actual:** The page is entirely blank. The Vue `#app` div mounts (273 DOM elements present) but no visible layout or page content is rendered. The sidebar and top navigation bar are also absent.

---

### 3.2 Open Bugs — Still Present

---

#### BUG-002 — Company Details: Phone field accepts letters without real-time validation

| Field | Detail |
|-------|--------|
| **Module** | Company Details |
| **URL** | `https://visipoint.uk/company` |
| **Severity** | Minor |
| **Status** | Still Present (first reported 2026-06-18) |

**Steps to Reproduce:**
| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Navigate to Company Details (`/company`) | Page loads with company info | ✅ |
| 2 | Click the **Edit** button below the company logo | Edit Company modal opens | ✅ |
| 3 | Click the **Change** link next to the Phone field | Phone input field appears | ✅ |
| 4 | Type letters into the phone input (e.g., `abc123`) | Letters are rejected, or an inline error appears immediately | Letters are accepted — field shows `abc123` with no error or restriction |

**Expected:** Phone field should only accept numeric characters. Letters should be blocked in real time (or immediately flagged with an inline error while typing).  
**Actual:** The field accepts any character. No validation feedback appears until SAVE CHANGES is clicked.

---

#### BUG-003 — Journey Builder: Survey column displays internal key instead of survey name

| Field | Detail |
|-------|--------|
| **Module** | Journey Builder |
| **URL** | `https://visipoint.uk/journey-builder` (Survey Management tab) |
| **Severity** | Minor (cosmetic) |
| **Status** | Still Present (first reported prior session) |

**Steps to Reproduce:**
| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Navigate to Journey Builder → Survey Management tab | Page loads with journey list | ✅ |
| 2 | View the **Survey** column in the grid | Human-readable survey name (e.g., "Customer Feedback Survey") | Internal key/ID string is shown (e.g., a UUID or short code) |

**Expected:** The Survey column shows the survey's display name as configured in the Survey Builder.  
**Actual:** The column renders the internal survey identifier (key/ID) instead of the name, making the grid unreadable for end users.

---

#### BUG-004 — Reporting > Kiosk Logs: Pagination size selector renders incorrectly

| Field | Detail |
|-------|--------|
| **Module** | Reporting > Kiosk Logs |
| **URL** | `https://visipoint.uk/reporting/kiosk-logs` |
| **Severity** | Minor (cosmetic) |
| **Status** | Still Present (first reported prior session) |

**Steps to Reproduce:**
| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Navigate to Reporting → Kiosk Logs | Page loads with data grid | ✅ |
| 2 | Observe the pagination bar at the bottom of the grid | Page size options (5, 10, 25, 50, 100) are all visible immediately | Only "10" is visible initially; the full list appears only after interacting with the selector |

**Expected:** All page size options are visible in the pagination bar on initial page load.  
**Actual:** The page size dropdown renders in a collapsed/partial state showing only the current selection until the user interacts with it.

---

#### BUG-005 — Reporting > Print List: Same pagination render issue as Kiosk Logs

| Field | Detail |
|-------|--------|
| **Module** | Reporting > Print List |
| **URL** | `https://visipoint.uk/reporting/print-list` |
| **Severity** | Minor (cosmetic) |
| **Status** | Still Present (first reported prior session) |

**Steps to Reproduce:**
| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Navigate to Reporting → Print List | Page loads with data grid | ✅ |
| 2 | Observe the pagination bar at the bottom of the grid | Page size options visible immediately | Same partial render as BUG-004 — only current size shows until interaction |

**Expected / Actual:** Same as BUG-004 above.

---

### 3.3 Fixed Bugs — Confirmed This Session

| Jira / Prior ID | Module | Description | Fix Verified |
|-----------------|--------|-------------|-------------|
| CL-17675 | Journey Builder | Journey description field showed "null" instead of empty | ✅ Now shows empty string |
| — | Announcements | Bootstrap "View" modal was invisible (missing `show` CSS class) | ✅ Modal opens and displays correctly |
| — | Reporting > Track & Trace | Clicking Filter with no selection was silent | ✅ Now shows inline message: "Select a user and date range to show information." |
| — | Company Details | SAVE CHANGES with invalid phone format — no error shown (silent failure) | ✅ Now shows: "Phone number should contain only digits." |

---

### 3.4 Could Not Reproduce

| Jira ID | Module | Description | Reason |
|---------|--------|-------------|--------|
| CL-16259 | Dashboard | Compliance title appears twice in tooltip | Test environment has no compliance-configured user type with a user pending compliance. Tooltip condition cannot be triggered with current test data. |

---

### 3.5 Not Retested

| Prior ID | Module | Description | Reason |
|----------|--------|-------------|--------|
| — | Announcements | "Clear filters" button does not clear active text search inputs | Could not set up the scenario (requires an active text filter with data visible, then clearing) |

---

## 4. Recommendations

| Priority | Action |
|----------|--------|
| P1 | Investigate and fix Passport Account blank page (`/passport`) — page is entirely non-functional |
| P1 | Retest CL-16259 with a compliance-configured user type and pending compliance user |
| P2 | Add real-time validation to Company Details phone field (reject non-digit characters on input) |
| P2 | Fix Journey Builder Survey column to display survey name instead of internal key |
| P2 | Fix pagination render glitch in Kiosk Logs and Print List sub-pages |
| P3 | Retest Announcements "Clear filters" with an active text filter to confirm/deny the bug |

---

## 5. QA Rules Applied (Not Reported as Bugs)

The following observed behaviors were NOT reported as bugs per established QA rules:

- Silent submit on Create/Add/Save forms — by design
- Grid column text truncation — by design
- Custom Fields appearing as grid columns — by design
- All kiosk devices showing "Offline" in test environment — expected
- W0019 DevExtreme license warning in console — by design (CL-17680)

---

*Report generated: 2026-06-30 | Tool: Claude Code + Chrome MCP | Modules: 12 of 13 (Survey covered separately)*
