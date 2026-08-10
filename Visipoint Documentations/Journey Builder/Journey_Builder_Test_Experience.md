# Journey Builder – Test Experience Report

**Date:** 2026-06-20
**Tester:** Mazen Mohamed (m.mohamed@lamasatech.com)
**Company Under Test:** UK (Testing) at https://visipoint.uk
**Module:** Journey Builder (under Company Dashboard left navigation)

---

## Module Overview

Journey Builder is a left-nav section in the Visipoint Company Dashboard containing **2 sub-sections**:

1. Visit Management (`/journeys`)
2. Survey Management (`/survey-journeys`)

Both sub-sections are accessible after expanding the "Journey Builder" nav item. They share the same underlying journey data but present different filtered views.

---

## Journey Types

The platform supports two journey types, selectable when creating or editing a journey:

| Type | Description |
|------|-------------|
| **Visit Management** | Full visit flow with Input, Checks, Compliance, Output, Notification, Feedback configuration. Supports sub-flows (child rows). |
| **Survey Only** | Simplified journey that links to surveys with scheduling options. No sub-flows. |

---

## 1. Visit Management (`/journeys`)

### Page Layout
- Title: "Journey / Flows" + **+ Create Journey** button (dark blue)
- **Columns** button (Column Chooser dropdown)
- Tree-structured grid: parent journey rows expand to reveal child flow rows (collapse/expand with ▲/▼)
- Grid columns (10 total): **Sites**, **Name**, **User Type**, **Input**, **Checks**, **Compliance**, **Output**, **Notification**, **Feedback**, **Actions**
- All data columns (except Actions) have inline **Search** filter fields
- Name and User Type columns have filter icons (▼)

### Existing Records (pre-test)
**Parent Journey 1:** UK (Testing)-Journey (Visit Management type)
- Site: UK (Testing)
- Child row → "Default Flow":
  - User Type: Staff, Visitor, Walk-in, Approval
  - Input: Mobile App (Site Geofence), RFID, QR Code
  - Checks: Temperature Check
  - Output: Save Data, Print Badge
  - Feedback: Audio

**Parent Journey 2:** Survey (Survey Only type)
- Site: UK (Testing)
- Compliance, Output, Notification, Feedback: all dashes (–)
- No child flow rows visible in this section

> **Note:** Both journeys appear in Visit Management. Survey Management shows only Survey Only journeys. This means Visit Management is an "all journeys" view and Survey Management is a filtered view.

### Select Actions — Parent Journey Row
When clicking "Select Actions" on a parent journey row, two options appear:
1. **Edit journey** – Navigates to full-page Edit Journey form
2. **Add flow** – Navigates to Create Journey Flow form for that journey

### Select Actions — Child Flow Row
When clicking "Select Actions" on a child flow row, one option appears:
1. **Edit flow** – Navigates to full-page Edit Flow form

### Columns Button (Column Chooser)
Opens a dropdown panel with 10 toggleable columns, all ON by default:
- ✅ Sites, Name, User Type, Input, Checks, Compliance, Output, Notification, Feedback, Actions

---

## 2. Edit Journey Form — Visit Management Type (`/update/journey/{id}`)

Accessed via Select Actions → Edit journey on a Visit Management parent row.

### Fields
| Field | Type | Notes |
|-------|------|-------|
| Add Logo | Image upload | Optional. Min 200px width, 45px height, 2MB max |
| Journey Name | Text | Editable |
| Journey Description | Textarea | Optional |
| Journey Builder Type | Radio buttons | Visit Management ● / Survey Only ○ |
| Access Permission | Multi-select (site picker) | "Only admins of selected sites will be able to access this journey." |
| | | Note: Removing sites not allowed if not permitted or assigned to areas |
| Touch Mode | Toggle (ON/OFF) | Enables kiosk/touch screen mode |
| Quick/Private Name Match | Radio | Controls visitor name matching behavior |

### Buttons
- **Update** (dark blue) — saves changes
- **Cancel** (red outline)

---

## 3. Edit Journey Form — Survey Only Type (`/update/journey/{id}`)

Same form as Visit Management Edit, but with Survey Only selected. **Key difference**: shows a Survey section instead of Touch Mode.

### Additional Fields (Survey Only)
| Field | Type | Notes |
|-------|------|-------|
| Survey Name | Dropdown (numbered) | Selects an existing survey from the platform |
| Scheduling | Radio tabs | **Always Active** / **Date Range** / **Recurring** |
| – Recurring: Repeat On | Day toggles | Mo, Tu, We, Th, Fr, Sa, Su |
| – Recurring: Start Time | Time picker | HH:MM |
| – Recurring: End Time | Time picker | HH:MM |
| – Date Range | Date pickers | Start Date / End Date |
| + Add another survey | Button | Adds a second survey row (numbered 2, 3, etc.) |
| Access Permission | Site picker | Same as Visit Management |

### Buttons
- **Update** (dark blue)
- **Cancel** (red outline)

> ~~**BUG**: The Journey Description textarea displays the literal string "null" when no description was provided during creation.~~ **FIXED as of 2026-07-01** — now shows an empty placeholder instead.

---

## 4. Create Journey Form — Visit Management (`/add-journey`)

Accessed via "+ Create Journey" from Visit Management section.

### Step 1 Fields
| Field | Notes |
|-------|-------|
| Add Logo | Optional, same spec as Edit |
| Journey Name | Required (but no validation enforced — see Bug Report) |
| Journey Description | Optional |
| Journey Builder Type | **Visit Management** (default) / Survey Only |
| Access Permission | Site picker (empty by default) |
| Touch Mode | Toggle OFF by default |

### Dynamic Behavior
- Selecting **Survey Only** hides Touch Mode and shows Survey Name section
- Selecting **Visit Management** hides Survey Name section and shows Touch Mode

### Buttons (Visit Management selected)
- **Next** (leads to flow creation step — Step 2)
- **Cancel**

### Buttons (Survey Only selected)
- **Save** (single-step, no Step 2)
- **Cancel**

> ✅ **Behavior**: The **Next** / **Save** button is disabled when required fields (Journey Name) are not filled — clicking it does nothing. This is intentional logic, not a bug.

---

## 5. Edit Journey Flow Form (`/update/journey/flow/{flowId}`)

Accessed via Select Actions → Edit flow on a child flow row.

### Layout
- Left panel: form fields
- Right panel: **live visual flow diagram** (updates as fields are filled)
- **Scale** field (default 0.9) — controls diagram zoom level
- Breadcrumb: "Journey: [Journey Name]"
- **+ Add New Journey** button (top right shortcut)

### Form Fields
| Field | Current Value | Notes |
|-------|--------------|-------|
| Flow Name | Default Flow | Required text field |
| Flow Description | UK (Testing) Default Journey Flow | Optional textarea |
| User Type ⚙ | Staff, Visitor, Walk-in, Approval | Multi-select tag input |
| Input | Mobile App (Site Geofence), RFID, QR Code | Multi-select; info: "Mobile app (site geofence) input can only be used by the 'Staff' users." |
| Checks ⚙ | Temperature Check | Multi-select with gear settings |
| Compliance | (empty) | Dropdown; info: "PDF and image agreements can only be used in journeys where Touch Mode is enabled." |
| Section | (empty) | Dropdown |
| Host | (empty) | Dropdown; info: "'Registration not required' user types and user types that have no users can't be added as a host." |
| Attendance Mode | Attendance mode | Text; info: "This attendance mode will be applied only on the 'Staff' users." |
| Output ⚙ | Print Badge, Save Data | Multi-select with gear settings |
| Notification | (empty) | Dropdown |
| Feedback ⚙ | Audio | Multi-select with gear settings |

### Visual Flow Diagram (Right Panel)
Displays boxes in sequence:
- **User Type** → **Input** → **Checks** → **Output** → **Feedback** + **Attendance Mode**

### Buttons
- **Update Journey Builder** (dark blue, top right)
- **Cancel**

---

## 6. Create Journey Flow Form (`/add/journey/flow/{journeyId}`)

Accessed via Select Actions → Add flow on a parent journey row.

### Fields (subset of Edit form)
| Field | Notes |
|-------|-------|
| Flow Name | Placeholder text "Flow Name" |
| Flow Description | Placeholder "Flow Description" |
| User Type | Dropdown: "select user type.." |
| Input | Dropdown: "select input.." |
| Checks | Dropdown: "select check.." |
| Compliance | Dropdown; same note about Touch Mode |
| Section | Dropdown: "select section.." |
| Output | Dropdown: "select output.." |
| Notification | Dropdown: "select notification.." |
| Feedback | Dropdown: "select feedback.." |

> ⚠️ **Missing fields vs Edit form**: Create form lacks **Host** and **Attendance Mode** fields (only available after flow creation via Edit). See Bug Report.

> ✅ **Behavior**: The **Save Journey Builder** button is disabled when required fields are not filled — clicking it does nothing. This is intentional logic, not a bug.

### Buttons
- **Save Journey Builder** (dark blue, top right)
- **Cancel**

---

## 7. Survey Management (`/survey-journeys`)

### Page Layout
- Title: "Survey Management" + **+ Create Journey** button
- **Columns** button
- Flat grid (no tree/child structure)
- Grid columns (4 total): **Site**, **Name**, **Survey**, **Actions**
- Site, Name, Survey columns have inline Search filters

### Existing Record
| Site | Name | Survey | Actions |
|------|------|--------|---------|
| UK (Testing) | Survey | – | Select Actions ▼ |

> **Note**: The "Survey" column shows "–" even though this journey has a survey configured. **Confirmed intentional application logic (2026-07-01), not a bug.**

### Select Actions (per row)
1. **Edit journey** – Navigates to `/update/survey-journey/{id}` (same form as Edit Journey, Survey Only type)
   - Same "null" description bug confirmed here

### Columns Button
4 options, all enabled: Site ✅, Name ✅, Survey ✅, Actions ✅

### Create Journey (`/add-survey-journey`)
- Same form as `/add-journey` but **defaults to Survey Only** type (Survey Name section pre-shown)
- Button label is **Save** (no multi-step "Next")
- Journey Builder Type radio is still editable (can switch to Visit Management)

---

## URL Reference

| Action | URL Pattern |
|--------|-------------|
| Visit Management list | `/journeys` |
| Survey Management list | `/survey-journeys` |
| Create Journey (from Visit Mgmt) | `/add-journey` |
| Create Journey (from Survey Mgmt) | `/add-survey-journey` |
| Edit Journey | `/update/journey/{id}` |
| Edit Journey (from Survey Mgmt) | `/update/survey-journey/{id}` |
| Create Flow | `/add/journey/flow/{journeyId}` |
| Edit Flow | `/update/journey/flow/{flowId}` |

---

## General UI Observations

| Feature | Behavior |
|---------|----------|
| Column Chooser | Dropdown panel with toggle checkboxes — no "Select All" option observed |
| Diagram (flow editor) | Live updates as fields are filled; Scale field controls zoom (0.9 default) |
| Journey Builder Type | Dynamically changes form structure when toggled |
| Multi-select fields | Use tag-pill pattern with × to remove individual selections |
| ⚙ gear icons | Appear on Checks, Output, Feedback fields — purpose not fully explored |
| + Add another survey | Allows stacking multiple surveys with independent schedules |
| Touch Mode | Toggle that gates PDF/image compliance features |

---

## Logic Summary

- **Visit Management** journeys support complex multi-step visitor flows (Input → Checks → Compliance → Output → Notification → Feedback) with sub-flows (child rows). Each parent journey can have multiple flows for different user types or scenarios.
- **Survey Only** journeys are simpler — they link a journey to one or more surveys with scheduling control (Always Active / Date Range / Recurring). No input/output pipeline.
- **Survey Management** sub-section is a filtered view showing only Survey Only journeys; Visit Management shows all journeys regardless of type.
- **The same journey** ("Survey") appears in both Visit Management and Survey Management views.
- **Flow creation is two-step** for Visit Management (Journey details → Flow configuration) but single-step for Survey Only.
