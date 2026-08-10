# Multi-Module Regression Testing Report — 2026-07-01

**Platform:** visipoint.uk (Cloud Dashboard) + visipoint.me (Passport SSO)
**Tester:** Claude Code (AI-assisted regression testing via Chrome MCP)
**Login:** m.mohamed+2468@lamasatech.com (Passport account) → SSO into "UK (Testing)" company dashboard
**Scope:** Regression pass across all 8 previously-tested modules — Survey, Journey Builder, Compliance, User Settings, Announcements, Users, Reporting, Company Details — verifying status of previously filed bugs and spot-checking for regressions.

---

## 1. Login Flow Notes

- `https://visipoint.uk/` (root) can hang indefinitely on first load — `document.readyState` stayed `"loading"` for 30+ seconds with two pending network requests (`react-survey-widget.iife.js`, `gist-widget.b-cdn.net` chat widget script) that never resolved in that session. A second navigation to `/login` succeeded normally, so this looks like a transient CDN/script hang rather than a hard outage — not filed as a bug, but worth noting if users report a blank homepage.
- `/login` auto-redirects through SSO to `visipoint.me` (Passport) when a valid Passport session cookie exists, then redirects back to `visipoint.uk` via a `/sso?id=...` token URL. No credential entry was needed since the Passport session was already active in the browser.

---

## 2. Bug Status Summary

### Fixed Since Last Test

| Module | Bug | Jira / Ref | Verified Fix |
|--------|-----|------------|---------------|
| Survey | Template surveys created with 0 questions | CL-17682 (BUG-BE-01) | New template-derived survey has 6 imported questions, status Active |
| Survey | Report Name field has no validation | CL-17687 (BUG-FE-04) | Inline error "Please enter a report name before generating." now blocks submit |
| Survey | Generate Report fires with no config warning | CL-17688 (BUG-FE-05) | Same validation blocks empty-name generation |
| Survey / Journey Builder | Journey description shows literal "null" | CL-17683 / CL-17686 (BUG-BE-02/FE-03) | Edit Journey now shows empty placeholder, not "null" |
| Announcements | Eye icon preview modal invisible (Bootstrap fade bug) | Bug 1 | Modal now renders fully visible with content |
| Announcements | Trash icon delete-confirm modal invisible | Bug 2 | Modal now renders fully visible, Cancel/Delete both visible |
| Announcements | "Clear filters" doesn't clear text search inputs | Bug 3 | Text filter + sort both cleared correctly |
| Announcements | "Clear filters" button stays visible after clearing | Bug 4 | Button now disappears once filters are cleared |
| Users | Delete modal missing username in confirmation text | Bug 2 | Confirmation now reads "...Delete Iiio Iiio?" |
| Users | "Clear filters" doesn't clear text search inputs | Bug 8 | Confirmed cleared; button also disappears |
| Reporting | Systemic "Clear filters" doesn't clear text search (all grid pages) | — | Verified fixed on Export List page |
| Reporting | Track and Trace: Filter button does nothing with no user/date selected | Bug #4 | Now shows inline message "Select a user and date range to show information." before and after clicking Filter |
| Company Details | SAVE CHANGES with invalid phone silently fails | Bug 2 | Re-confirmed fixed (previously fixed 2026-06-30): inline error "Phone number should contain only digits." blocks save |

### Still Present

*(none remaining after reclassification below)*

### Reclassified — Not a Bug

| Module | Item | Ref | Correction |
|--------|------|-----|------------|
| User Settings | User Types → Add: no validation on empty Name/Registration Method submit | BUG-001 | This is Visipoint's standard silent-submit pattern used across all Create/Add/Save forms platform-wide, confirmed intentional by the team on 2026-06-30. Should not have been re-flagged. |
| Company Details | Phone field accepts non-numeric characters while typing | Bug 1 | Field validates on Save, not on keystroke — a legitimate UX pattern. Save-time validation ("Phone number should contain only digits.") correctly blocks bad data. Accepting characters mid-typing before submit is not a defect. |
| User Settings | Visit Permits → Delete: no confirmation dialog | BUG-004 | Confirmed intentional application logic by the team. |
| Journey Builder → Survey Management | List grid's "Survey" column shows "-" for a journey ("J1") that has a survey ("Test342") actually assigned per the Edit Journey form | — | Confirmed intentional application logic by the team. |

### New Observation (not previously documented)

*(none — the one candidate found this session was reclassified above)*

---

## 3. Modules With No New Issues

- **Compliance** — list, Create form, dynamic type behavior all match documented baseline exactly (3 existing Agreement records, Create button disabled until required fields filled — intentional pattern).
- **Journey Builder → Visit Management** — Journey/Flows grid renders correctly; Survey-only journeys correctly show "-" for Visit-specific columns (Input/Checks/Compliance/Output) since those don't apply to survey journeys.

---

## 4. Testing Notes

- Did not perform any destructive actions (no confirmed deletes, no permanent data changes) — all Delete/Archive flows were opened to inspect modal content/behavior then cancelled.
- Known non-bugs per QA rules were not re-reported: DevExtreme W0019 license warning, SVG path console errors, disabled-until-valid submit buttons, custom fields as grid columns, column text truncation.
- Kiosk Logs / Print List pagination-size inconsistency (5/10/50 and 50/100/150/200 respectively) was not re-verified this session — still flagged as a known cosmetic issue per existing records; recommend a follow-up pass if a full Reporting re-test is scheduled.

---

*Report generated by Claude Code — AI-assisted regression testing session, 2026-07-01*
