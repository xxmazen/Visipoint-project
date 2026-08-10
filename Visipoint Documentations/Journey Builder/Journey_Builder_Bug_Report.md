# Journey Builder – Bug Report

**Date:** 2026-06-20
**Tester:** Mazen Mohamed (m.mohamed@lamasatech.com)
**Module:** Journey Builder (Company Dashboard)
**Environment:** UK (Testing) at https://visipoint.uk

---

## BUG-JB-001: Journey Description Displays Literal "null" Text

| Field        | Details |
|--------------|---------|
| **Severity** | Medium  |
| **Sub-section** | Visit Management / Survey Management → Edit Journey |
| **URL**      | `/update/journey/{id}` and `/update/survey-journey/{id}` |
| **Status**   | Open    |

### Steps to Reproduce
1. Create a journey without entering a description (leave the Description field empty).
2. Navigate to Journey Builder → Visit Management (or Survey Management).
3. Click **Select Actions** → **Edit journey** on a journey that has no description.

### Expected Behavior
- The Journey Description textarea should be empty (blank), or display a placeholder such as "No description".

### Actual Behavior
- The Journey Description textarea displays the string **"null"** as its content.
- This is the raw JavaScript `null` value being rendered as a string instead of being converted to an empty string.

### Impact
- If an admin edits and saves the journey without clearing this field, "null" will be saved as the actual journey description.
- Misleading UI — admins may not realize this is a bug and may think "null" is a valid system-generated value.
- Visible to any admin who edits journeys; affects all journeys created without a description.

### Recommendation
- Before populating the textarea, apply a null/undefined check: `description ?? ""` or `description || ""`.
- This applies to both the `/update/journey/` and `/update/survey-journey/` endpoints.

---

## BUG-JB-002: Create Journey Flow Missing Fields vs Edit Journey Flow

| Field        | Details |
|--------------|---------|
| **Severity** | Low–Medium |
| **Sub-section** | Visit Management → Add flow vs Edit flow |
| **URL**      | Create: `/add/journey/flow/{id}` · Edit: `/update/journey/flow/{id}` |
| **Status**   | Open (Design Review) |

### Steps to Reproduce
1. Open the **Create Journey Flow** form (Select Actions → Add flow).
2. Open the **Edit Journey Flow** form on an existing flow (Select Actions → Edit flow).
3. Compare the available fields.

### Observed Behavior
**Create Journey Flow** (`/add/journey/flow/{id}`) has these fields:
Flow Name, Flow Description, User Type, Input, Checks, Compliance, Section, Output, Notification, Feedback

**Edit Journey Flow** (`/update/journey/flow/{id}`) has these **additional** fields:
- **Host** (dropdown)
- **Attendance Mode** (text field)

### Issue
- **Host** and **Attendance Mode** cannot be configured during initial flow creation.
- Admins must first save the flow, then go back and edit it to configure these fields.
- There is no indication on the Create form that additional fields are available post-creation.
- The note "This attendance mode will be applied only on the 'Staff' users" implies Attendance Mode is important — its absence during creation can lead to incomplete configurations.

### Recommendation
- Add Host and Attendance Mode fields to the Create Journey Flow form, consistent with the Edit form.
- If there is a technical reason to exclude them during creation, add a notice: "Additional options (Host, Attendance Mode) are available after saving."

---

## BUG-JB-003: Survey Column Shows "–" in Survey Management Grid Despite Configured Survey

| Field        | Details |
|--------------|---------|
| **Severity** | Low |
| **Sub-section** | Survey Management grid |
| **URL**      | `/survey-journeys` |
| **Status**   | Open (Needs Verification) |

### Steps to Reproduce
1. Navigate to Journey Builder → Survey Management.
2. Observe the **Survey** column for any journey row.

### Observed Behavior
- The Survey row for the "Survey" journey shows **"–"** in the Survey column.
- However, when editing this journey (Edit journey), it has a survey selected: **"Maryoum"**.

### Expected Behavior
- The Survey column should display the name(s) of the survey(s) linked to the journey (e.g., "Maryoum").

### Actual Behavior
- The Survey column shows "–" (dash/empty), not the survey name.

### Impact
- Admins cannot see at a glance which survey is linked to each journey.
- The Survey column provides no useful information, reducing the value of the grid view.

### Recommendation
- Populate the Survey column with the linked survey name(s) from the journey configuration.
- If multiple surveys are linked, show a comma-separated list or "+N more" format.

---

## Summary Table

| Bug ID     | Sub-section                         | Description                                              | Severity   |
|------------|-------------------------------------|----------------------------------------------------------|------------|
| BUG-JB-001 | Edit Journey (both types)           | Description field displays literal "null" text           | Medium     |
| BUG-JB-002 | Create vs Edit Journey Flow         | Create form missing Host and Attendance Mode fields      | Low–Medium |
| BUG-JB-003 | Survey Management grid              | Survey column shows "–" despite survey being configured  | Low        |
