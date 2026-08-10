# User Settings – Bug Report

**Date:** 2026-06-20
**Tester:** Mazen Mohamed (m.mohamed@lamasatech.com)
**Module:** User Settings (Company Dashboard)
**Environment:** UK (Testing) at https://visipoint.uk

---

## BUG-001: User Types – No Validation on Empty Create Form Submission — NOT A BUG (by design)

| Field        | Details |
|--------------|---------|
| **Severity** | N/A  |
| **Sub-section** | User Types → Add |
| **URL**      | `https://visipoint.uk/add-user-type` |
| **Status**   | **Closed — intentional application logic, confirmed 2026-07-01** |

> **Correction (2026-07-01):** This is Visipoint's standard silent-submit pattern used across all Create/Add/Save forms platform-wide (Announcements, Journey Builder, Compliance, Sites & Devices, etc.) — confirmed by the team on 2026-06-30. Do not report silent submission of empty required fields as a bug anywhere in this app unless it corrupts/saves bad data or navigates incorrectly. See `project_form_validation_logic.md` in memory.

### Steps to Reproduce
1. Navigate to User Settings → User Types.
2. Click the **Add** button.
3. On the "Create New User Type" page, leave both **Name** and **Registration Method** fields empty.
4. Click the **Submit** button.

### Expected Behavior
- Form should remain on the same page.
- Inline validation error messages should appear:
  - Under Name field: "Name is required" (or similar)
  - Under Registration Method: "Registration Method is required" (or similar)
- Submit should be blocked until required fields are filled.

### Actual Behavior
- No validation error messages are displayed.
- The form submits without any user feedback.
- The page redirects back to the User Types list at `/user-types` without creating a record (server-side rejection is silent).
- The user has no idea why the submission failed or what they need to fix.

### Impact
- Poor UX: users are confused when the form disappears without explanation.
- Inconsistency: the **Edit User Type** modal correctly shows "The maximum length is 20 characters" – demonstrating that validation exists elsewhere but is missing on the create flow.

### Note on Contrast
- **Visit Permits Add modal** correctly validates: shows "Permit name is required" and "Start Date is required" in red — this is the expected behavior that User Types should match.
- **User Types Edit modal** correctly validates: shows character limit errors.

---

## BUG-002: Attendance Modes – Sensitive Data Exposed in URL Query Parameters

| Field        | Details |
|--------------|---------|
| **Severity** | Medium  |
| **Sub-section** | Attendance Modes → Select Actions → Edit Mode |
| **URL**      | `https://visipoint.uk/add-Attendance-Mode?id=...&name=...&attendance_codes=[...]&settings=[...]` |
| **Status**   | **Open — re-confirmed still present 2026-07-31** (tested with a fresh record, identical exposure pattern) |

### Steps to Reproduce
1. Navigate to User Settings → Attendance Modes.
2. Click **Select Actions** → **Edit Mode** on any attendance mode row.

### Expected Behavior
- The Edit Mode page should load attendance mode data by fetching it server-side using the record ID.
- Sensitive configuration data should not be visible in the URL.

### Actual Behavior
- The URL contains extensive JSON-encoded data as query parameters, including:
  - Attendance code IDs and full descriptions
  - Integration IDs and names
  - Setting IDs, names, types, default values, and pivot data with timestamps
  - UUID references to internal entities
- Example URL length exceeds 3000+ characters with base64-like encoded JSON.

### Impact
- **Security concern**: URLs may be captured in browser history, server access logs, proxy logs, or analytics tools — exposing internal configuration data.
- **Functional concern**: Very long URLs may be truncated by some browsers, proxies, or copy-paste, causing the edit page to load broken/incomplete.
- **Data concern**: Timestamps, UUIDs, and integration names are internal data that should not be in client-visible URLs.

### Recommendation
- Load attendance mode data by ID via an API call on the Edit Mode page load.
- Pass only the record ID as a URL parameter: `/edit-attendance-mode/{id}`

---

## BUG-003: User Types – "Visit Permits" Option in Select Actions May Be Confusing / Misplaced — RESOLVED (not a bug)

| Field        | Details |
|--------------|---------|
| **Severity** | N/A |
| **Sub-section** | User Types → Select Actions |
| **URL**      | `https://visipoint.uk/user-types` |
| **Status**   | **Closed — resolved by direct testing, 2026-07-31** |

> **Resolution (2026-07-31):** Actually clicked through this action for the first time (previous sessions only speculated about its behavior). It navigates to a clearly breadcrumbed sub-page ("User Types / {Type}'s Visit Permits") with its own explanatory note: "Users under this user type are allowed to access the entity even if they hold two overlapping permits." This is self-explanatory in context — the original concern (no tooltip, ambiguous purpose) doesn't hold up once the feature is actually used. Not filing as a UX bug.

### Steps to Reproduce
1. Navigate to User Settings → User Types.
2. Click **Select Actions** on any user type row.

### Observed Behavior
- The dropdown shows three options: **Edit**, **Visit Permits**, **Delete**.
- "Visit Permits" navigates to the Visit Permits sub-section when clicked (not confirmed – may filter permits by user type).

### Issue
- It is unclear why "Visit Permits" appears inside the User Types actions menu.
- If it links to the Visit Permits page filtered by that user type, this is undocumented.
- If it's a general navigation link, it should not be inside a row-level actions menu.
- No tooltip or description is provided to explain its purpose.

### Recommendation
- Add a tooltip or description explaining what "Visit Permits" does in this context.
- If it navigates to a filtered view of permits for that user type, label it more clearly: "View Visit Permits for this Type".

---

## BUG-004: Visit Permits – No Confirmation Dialog on Delete — CORRECTION: dialog exists (2026-07-31)

| Field        | Details |
|--------------|---------|
| **Severity** | N/A |
| **Sub-section** | Visit Permits → Delete |
| **URL**      | `https://visipoint.uk/visit-permits` |
| **Status**   | **Closed — confirmation dialog confirmed present and working, 2026-07-31** |

> **Correction (2026-07-01):** [SUPERSEDED — see 2026-07-31 update below] Not a bug — confirmed by the team as intended logic.
>
> **Update (2026-07-31):** Actually clicked Delete on a real test permit for the first time (the 2026-07-01 correction was never verified by clicking Delete — the original report explicitly noted "Delete button itself was still not clicked to avoid unrecoverable data loss"). A confirmation modal **does** appear: "Delete Visit Permit — Are you sure you want to 'Delete' this Visit Permit? By deleting this visit permit, it will be permanently removed from all linked user types and associated users." with Cancel/Delete buttons, consistent with User Types' delete flow. Either this was fixed since 2026-07-01, or the earlier "no dialog" conclusion was never actually verified and was incorrect. Current state (2026-07-31): a confirmation dialog exists and works correctly — this is now consistent with the rest of the app's delete flows, not an inconsistency.

### Steps to Reproduce
1. Navigate to User Settings → Visit Permits.
2. Click the red **Delete** button on any permit row.

### Expected Behavior
- A confirmation dialog should appear: "Are you sure you want to delete permit [Name]?" with Cancel and Delete buttons.
- This is consistent with the User Types delete flow which shows a confirmation dialog.

### Actual Behavior
- During testing, the **Delete** button appeared to be a direct action button without an intermediate confirmation step.
- This was not fully confirmed as a hard delete was not executed during testing to avoid data loss.

### Impact
- If confirmed as a direct delete (no confirmation), accidental permit deletions cannot be undone.
- Inconsistent with User Types section which uses a confirmation dialog.

### Recommendation
- Add a confirmation dialog before deleting a Visit Permit, consistent with the User Types behavior.

---

## BUG-005: Custom Fields – New Section's Field(s) Don't Render as Child Rows in the List Grid

| Field        | Details |
|--------------|---------|
| **Severity** | Medium |
| **Sub-section** | Custom Fields |
| **URL**      | `https://visipoint.uk/custom-fields` |
| **Status**   | Open, found 2026-07-31 |

### Steps to Reproduce
1. Navigate to User Settings → Custom Fields.
2. Click Add → Add new section.
3. Fill in a Section Name, select a User Type, add one field (Field Name, Field Type, Show on), submit.
4. Return to the Custom Fields list grid.

### Expected Behavior
- The new section appears as an expandable row, with its field(s) listed as child rows underneath — matching how "Main Section" shows "Test" and "YTYT" as children.

### Actual Behavior
- The new section appears in the grid, but with **no child field row** — it looks like an empty section with zero fields.
- Reproducible after a full page reload and after toggling the row's expand/collapse arrow.
- Opening the section via Edit (pencil icon) confirms the field **was** saved correctly (Field Name, Field Type, and toggles all present and correct) — this is a display-only bug in the list grid's rendering, not data loss.

### Impact
- An admin scanning the Custom Fields list would incorrectly conclude a newly-created section has no fields, when it actually does — could lead to duplicate field creation or confusion during setup.

### Recommendation
- Fix the list grid's tree-expansion logic/query so newly-created sections' fields render as child rows immediately, without requiring some other trigger (e.g. server restart, cache clear) that "Main Section" happens to already satisfy.

---

## UPDATE (2026-07-01) — Regression Retest

- **BUG-001 (User Types Add: no validation on empty submit): RECLASSIFIED — NOT A BUG.** This is the standard silent-submit pattern used across all Create/Add/Save forms in Visipoint, confirmed intentional by the team on 2026-06-30. Should never have been filed as a new bug; corrected below.
- **BUG-004 (Visit Permits: no delete confirmation): RECLASSIFIED — NOT A BUG.** Confirmed intentional application logic; corrected below.
- BUG-002 and BUG-003 not retested this pass.

Full details in `D:\Visipoint md files\Multi_Module_Testing_Report_2026-07-01.md`.

---

## Summary Table

| Bug ID   | Sub-section              | Description                                          | Severity |
|----------|--------------------------|------------------------------------------------------|----------|
| BUG-001  | User Types → Add         | ~~No validation errors on empty form submission~~ — **not a bug, by design** | N/A   |
| BUG-002  | Attendance Modes → Edit  | Sensitive data exposed in URL query parameters — **still open, re-confirmed 2026-07-31** | Medium   |
| BUG-003  | User Types → Actions     | ~~"Visit Permits" option in row actions is confusing~~ — **resolved, not a bug (2026-07-31)** | N/A      |
| BUG-004  | Visit Permits → Delete   | ~~No delete confirmation dialog~~ — **correction: dialog exists and works (2026-07-31)** | N/A  |
| BUG-005  | Custom Fields            | New section's field(s) don't render as child rows in the list grid (data is saved correctly, display-only bug) | Medium |
