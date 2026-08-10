# Compliance – Test Experience Report

**Last Tested:** 2026-07-31

## CORRECTION (2026-07-31) — Preview/Edit "bugs" from 2026-07-30 are confirmed intentional logic, NOT bugs

The three findings reported on 2026-07-30 (Preview showing generic content for file-based Agreement, Preview showing almost nothing for Document-Vaccine/PCR, Edit form not pre-populating Name/Protocols/PCR settings for Document-Vaccine/PCR) were reviewed by the user and confirmed to be **intentional application logic, not defects**. Do not report these as bugs in future sessions — see the "Intentional Design Decisions" section below and the `visipoint-module-testing` skill's QA Rules table, both updated with this correction.

1. **Compliance Preview modal content for file-based Agreement and Document - Vaccine/PCR is by design.** Text-based Agreement and Questionnaire compliances preview with full detail; file-based Agreement shows generic "agreement" text + "Document Binding" fields, and Document-Vaccine/PCR shows only the name — this is intentional, not a rendering defect.
2. **Compliance Edit form not pre-populating Name/Select Protocols/Accept Negative PCR for Document - Vaccine/PCR is by design.** Type and the Document Uploaded/Not Uploaded checkboxes+labels do load; the rest intentionally doesn't.
3. Also noted (separately, still just a UX observation not a bug): the main **Create** button (not just Questionnaire's "ADD QUESTION") silently requires BOTH Positive and Negative Desired Answer labels to be filled before it enables, even when only one checkbox is checked — matches the app's general silent-validation style, just noted so it isn't mistaken for a stuck/broken Create button.

> Read this correction before testing Compliance again — the Preview/Edit behavior for file-based Agreement and Document-Vaccine/PCR is confirmed intentional, not a defect to re-report.

---

**Date:** 2026-06-20
**Tester:** Mazen Mohamed (m.mohamed@lamasatech.com)
**Company Under Test:** UK (Testing) at https://visipoint.uk
**Module:** Compliance (under Company Dashboard left navigation)

---

## Module Overview

Compliance is a left-nav section in the Visipoint Company Dashboard accessible at `/compliances`. It allows admins to create and manage compliance checks that can be attached to journey flows. Three compliance types are supported: Questionnaire, Agreement, and Document - Vaccine/PCR.

---

## 1. Compliance List Page (`/compliances`)

### Page Layout
- Title: **"Compliance List"** + **Create Compliance** button (dark blue)
- **Columns** button (Column Chooser dropdown)
- **Export** button (dropdown with format options)
- Grid with 3 visible columns: **Name**, **Date**, **Actions**
- Name and Date columns each have inline **Search** filter inputs
- Actions column has 3 icon buttons per row: ✏️ Edit, 👁️ View, 🗑️ Delete

### Existing Records (pre-test)
| Name | Date | Notes |
|------|------|-------|
| Agreement pdf | 11/11/2025 – 1:46 PM | Agreement type, PDF file uploaded |
| Agreement | 10/11/2025 – 6:33 PM | Agreement type, text-based |
| Agreement IMAGE | 11/11/2025 – 3:32 PM | Agreement type, image file uploaded |

> **Note:** All 3 existing records are Agreement type. No Questionnaire or Document–Vaccine/PCR records exist in the test environment.

### Row Action Icons
| Icon | Action | Behavior |
|------|--------|----------|
| ✏️ Edit (pencil) | Opens Edit Compliance | Navigates to `/compliance/update/{id}` (full-page form) |
| 👁️ View (eye) | Opens Preview | Opens a DevExtreme overlay popup showing compliance content (PDF viewer for file-based agreements, text content for text-based) |
| 🗑️ Delete (trash) | Delete compliance | Shows a confirmation before deletion; does NOT directly delete without confirmation |

### Pagination
- Items per page selector: **5 / 10 / 25 / 50 / 100** (25 selected by default)
- Displayed as: "Page 1 of 1 (3 items)"

---

## 2. Column Chooser

Opened via the **Columns** button. Panel title: "Column Chooser" with × close button.

| Column | Default State |
|--------|--------------|
| Name | ✅ Enabled |
| Version | □ Disabled (hidden) |
| Date | ✅ Enabled |
| Actions | ✅ Enabled |

> **Note:** A "Version" column exists but is disabled by default. Its purpose is not documented in the UI — it may relate to versioning compliance policy documents. All existing records show no version value when enabled.

---

## 3. Export Button

Opens a dropdown with 3 format options:
- **Excel** (xlsx)
- **CSV**
- **PDF**

---

## 4. Create Compliance (`/compliance-new`)

The form is **dynamic** — the fields displayed change entirely based on the **Type** selection.

### Common Fields (all types)
| Field | Type | Notes |
|-------|------|-------|
| Compliance Name | Text input | Required; placeholder "Enter Compliance Name" |
| Type | Dropdown (multiselect component) | Options: Questionnaire, Agreement, Document - Vaccine/PCR |

---

### Type: Questionnaire

| Field | Notes |
|-------|-------|
| Question | Text input; the question to ask visitors |
| Desired Answer – Positive | Checkbox (default ✅) + editable label (default "Statement") |
| Desired Answer – Negative | Checkbox (default □) + editable label (default "Statement") |
| **ADD QUESTION** button | Adds another question block (supports multiple questions per compliance) |

**Button**: **Create**

> The ADD QUESTION pattern allows stacking multiple Q&A pairs in a single Questionnaire compliance.

---

### Type: Agreement

| Field | Notes |
|-------|-------|
| Agreement Format | Radio buttons: **Enter text manually** (default) / **Upload file (PDF or image)** |
| – Enter text manually | Shows **Agreement Text** textarea ("Enter your agreement") |
| – Upload file | Shows file input ("Choose File"); accepts .pdf, .png, .jpg; max 2MB |
| | Note: "PDF and image agreements can only be used in journeys where Touch Mode is enabled." |
| Desired Answer – Positive | Checkbox (default ✅) + editable label (default "Statement") |
| Desired Answer – Negative | Checkbox (default □) + editable label (default "Statement") |

**Button**: **Create**

> Agreement type has **no ADD QUESTION** button — only one agreement text/file per compliance.

---

### Type: Document - Vaccine/PCR

| Field | Notes |
|-------|-------|
| Desired Outcomes – Document Uploaded | Checkbox (default ✅) + editable label (default "Vaccinated") |
| Desired Outcomes – Document Not Uploaded | Checkbox (default □) + editable label (default "Unchecked") |
| Select protocols | Multi-select dropdown; options: EU Digital COVID Certificate, NHS (Domestic/International), Smart Health Card (SHC), NYS Excelsior Pass & NYS Excelsior Pass Plus |
| Document Binding to | Radio: **User Profile** (only option visible) |
| Accept Negative PCR | Toggle switch (OFF by default) |

**Button**: **Create**

> This type verifies digital proof documents (vaccine certificates, PCR test results) against supported protocol standards.

---

## 5. Edit Compliance (`/compliance/update/{id}`)

The Edit form is the same structure as Create, pre-populated with existing values.

### Key Differences from Create Form
| Aspect | Create | Edit |
|--------|--------|------|
| Type field | Editable dropdown | **Read-only** (cannot change type after creation) |
| Existing file | N/A | Existing PDF/image shown with × icon to remove |
| Desired Answer labels | Default "Statement" | Customizable — populated with saved labels (e.g., "Yes" / "No") |
| Submit button | **Create** | **Update** |
| Cancel button | None | **None** (no Cancel button — must use browser back) |

> **Note:** The **Type field is read-only** after creation — this is intentional design. If an admin creates a compliance with the wrong type, they must delete it and create a new one.

> **Note:** The Edit form has **no Cancel button** — this is intentional. Admins use the browser back button to discard changes.

---

## 6. URL Reference

| Action | URL Pattern |
|--------|-------------|
| Compliance list | `/compliances` |
| Create compliance | `/compliance-new` |
| Edit compliance | `/compliance/update/{id}` |

---

## 7. General UI Observations

| Feature | Behavior |
|---------|----------|
| Dynamic type form | Switching Type in Create form completely replaces the dynamic fields section |
| Desired Answer labels | Editable text fields — admins can customize positive/negative answer labels |
| ADD QUESTION | Only available for Questionnaire type (allows multiple questions per compliance) |
| Agreement Format radio | Only visible for Agreement type; switches between text and file upload |
| View icon | Opens DevExtreme popup overlay showing compliance content (not a separate page) |
| Delete icon | Opens confirmation dialog before deletion (no direct delete) |
| Column Chooser | Has Version column (disabled by default) — purpose not documented in UI |
| Export | DevExtreme DropDownButton component; supports Excel, CSV, PDF |
| Pagination | 5/10/25/50/100 items per page; DevExtreme DataGrid with filter row |

---

## Logic Summary

- **Questionnaire** is for custom yes/no or multi-option question surveys embedded in the check-in flow
- **Agreement** is for policy/terms agreements that visitors must accept (text or PDF/image document)
- **Document - Vaccine/PCR** is for validating health documentation (vaccine certificates, PCR tests) against global protocols
- The **Type** is immutable after creation — a deliberate design choice given that each type has completely different field structures
- **Touch Mode** in the journey flow is required for PDF/image-based Agreement compliances to work
- Compliances can be attached to Journey flows via the **Compliance** field in the Journey Flow editor

---

## 8. Intentional Design Decisions (Not Bugs)

| Behavior | Reason |
|----------|--------|
| **No Cancel button on Edit form** | Intentional — admins use the browser back button to discard changes |
| **No Type column in the grid** | Intentional — the grid is designed to show Name and Date only; Type is visible on the Edit form |
| **Type is read-only after creation** | Intentional — each compliance type has a completely different field structure; changing type post-creation would invalidate all existing field data |
| **Preview modal shows generic "agreement" text + "Document Binding" fields for file-based Agreement compliances** | Intentional — confirmed by user 2026-07-31, not a rendering defect |
| **Preview modal shows only the name (no other content) for Document - Vaccine/PCR compliances** | Intentional — confirmed by user 2026-07-31, not a rendering defect |
| **Edit form doesn't pre-populate Name/Select Protocols/Accept Negative PCR for Document - Vaccine/PCR compliances** (Type + Document Uploaded/Not Uploaded checkboxes+labels still load) | Intentional — confirmed by user 2026-07-31, not a defect |

---

## UPDATE (2026-07-01) — Regression Retest

- Compliance List page matches documented baseline exactly: 3 existing Agreement records (Agreement pdf, Agreement, Agreement IMAGE), same columns, same Export options.
- Create Compliance form behaves as documented — Create button stays disabled until required fields are filled (Compliance Name + Type), consistent with the platform-wide silent-submit/disabled-button pattern.
- No new issues found. No regressions from baseline.

Full regression details across all modules: `D:\Visipoint md files\Multi_Module_Testing_Report_2026-07-01.md`.

---

## Session Update — 2026-07-15 (Full Field/Button Walkthrough + API Performance)

**Full report:** `Compliance_Testing_Report_2026-07-15.md`

- **NEW BUG (Major):** the Compliance Preview modal (eye icon) does not render actual saved content for any of the 3 existing records — all show identical generic placeholder text "agreement" plus a "Document Binding: Yes/No" checkbox pair (a field that only belongs to the Document - Vaccine/PCR type, not Agreement). Verified this is wrong by cross-checking the Edit form, which correctly shows "Agreement pdf" has a real PDF file attached. This contradicts the previous documentation (View icon = "PDF viewer for file-based agreements, text content for text-based") — either a regression, or the original note was unverified.
- **Version column** (previously "no version value when enabled") now shows real GUID values for all 3 records when enabled — data drift, purpose still undocumented in the UI.
- **Full Create Compliance form tested for all 3 types.** New findings:
  - Questionnaire's ADD QUESTION button shows a genuine inline validation message ("Fields above should be filled first") if Statement labels are empty — a rare exception to this app's usual silent-validation pattern.
  - Document - Vaccine/PCR's "Accept Negative PCR" toggle reveals a previously-undocumented **"PCR valid for how many days?"** number field when enabled.
- **Edit Compliance re-verified** against a live record (Type read-only, existing file with remove-×, pre-filled Desired Answer labels, "Update" button, no Cancel) — all still holds.
- **Delete confirmation** correctly shows the record name; cancelled (only 3 records exist in this environment).
- **API baselines captured** (host `api.visipoint.uk`): `compliances` avg 786ms (n=3), `check_active_sessions` avg 749ms (n=3), `adminPreference` avg 774ms (n=6). High variance across all three (one fast ~300ms load, others 1.3s+) suggests a shared session/auth bottleneck rather than a per-endpoint issue.

