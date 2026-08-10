# Compliance Module — Smoke Test, Field/Button Walkthrough & API Performance
**Date:** 2026-07-15
**Tester:** Claude (automated browser testing via Chrome MCP)
**Page URL:** `https://visipoint.uk/compliances`
**Environment:** UK (Testing) company / grinta1911.visipoint.uk
**Reference:** [[project_jira_overview]] (CL-47 Compliance, 59 stories), [[project_knowledge_synthesis]], [[project_compliance_experience]]
**Scope:** Every field, button, dropdown, and modal reachable from the Compliance List and Create/Edit Compliance forms was opened and interacted with; screenshots captured at each step. No compliance record was created, edited, or deleted — see "What Was Not Submitted."

---

## 1. Compliance List — Grid Interactions

| # | Area | Result |
|---|------|--------|
| 1 | Page load — 3 existing Agreement records, matches documented baseline exactly | ✅ Pass |
| 2 | Name column search ("IMAGE") | ✅ Pass — filters correctly |
| 3 | Clear Filters | ✅ Pass |
| 4 | Column Chooser | ✅ Pass — Name/Version/Date/Actions listed, Version disabled by default (matches docs) |
| 5 | Export dropdown (Excel/CSV/PDF) | ✅ Pass — matches docs, not clicked (file download needs explicit go-ahead) |
| 6 | Pagination | ✅ Pass |

**New finding (data drift, not a bug):** enabling the previously-undocumented-as-populated **Version** column shows a real GUID value for all 3 records (e.g. `a2aa6558-d8ad-42d6-8e7a-b5248082a6eb`), contradicting the 2026-06-20 note that "all existing records show no version value when enabled." The column's purpose is still not documented in the UI.

---

## 2. Create Compliance — All 3 Types, All Fields

Confirmed as a full page (`/compliance-new`). All fields across all three dynamic type forms were filled and screenshotted; the form was **never submitted** — no test compliance was created.

### Common
- Compliance Name, Type dropdown (Questionnaire / Agreement / Document - Vaccine/PCR) — ✅ Pass, matches docs

### Questionnaire
| Field | Result |
|-------|--------|
| Question | ✅ Pass |
| Positive/Negative Answer checkboxes + editable "Statement" labels | ✅ Pass |
| **ADD QUESTION** | ✅ Pass — adds a "Question 2" block with its own delete (trash) icon; **shows a real inline validation message "Fields above should be filled first"** if the current question's Statement labels are empty (a positive UX detail — this app is usually silent on validation, this is a rare exception) |
| Delete-question trash icon | ✅ Pass — removes the added question block correctly |

### Agreement
| Field | Result |
|-------|--------|
| Agreement Format radio (Enter text manually / Upload file) | ✅ Pass |
| Agreement Text textarea | ✅ Pass |
| Upload file (Choose File, .pdf/.png/.jpg, 2MB note) | ✅ Pass — visible and correctly configured; **not clicked** (native OS file picker) |
| Positive/Negative Answer checkboxes | ✅ Pass |

### Document - Vaccine/PCR
| Field | Result |
|-------|--------|
| Desired Outcomes (Document Uploaded/Not Uploaded checkboxes + "Vaccinated"/"Unchecked" labels) | ✅ Pass |
| Select protocols (multi-select) | ✅ Pass — all 4 options confirmed (EU Digital COVID Certificate, NHS Domestic/International, Smart Health Card (SHC), NYS Excelsior Pass & Plus); tag-pill created correctly on selection |
| Document Binding to (User Profile radio) | ✅ Pass |
| Accept Negative PCR toggle | ✅ Pass — **NEW FIELD REVEALED**: toggling this ON shows a previously-undocumented **"PCR valid for how many days?"** number input |

---

## 3. Edit Compliance — Verified Against Live Record

Opened Edit for "Agreement pdf" (`/compliance/update/{id}`) — confirmed all documented behavior: Type field read-only, existing PDF file shown as an icon with a × remove control, Desired Answer labels pre-populated with the saved values ("Yes"/"No"), submit button reads "Update", and there is no Cancel button. Navigated away without clicking Update — no changes were saved.

---

## 4. Row Actions — View, Edit, Delete

| Action | Result |
|--------|--------|
| Edit (pencil) | ✅ Pass — see Section 3 |
| Delete (trash) | ✅ Pass — confirmation modal correctly shows the record name ("Are you sure you want to 'Delete' Agreement?"). Cancelled — these are the only pre-existing test records in the environment. |
| View (eye) | ❌ **BUG — see below** |

### BUG — Compliance Preview modal does not render actual saved content; shows generic placeholder + fields from a different compliance type

| Field | Detail |
|-------|--------|
| **Module** | Compliance → Compliance List → View (eye icon) |
| **URL** | https://visipoint.uk/compliances |
| **Severity** | Major |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Click the eye icon on "Agreement pdf" (a file-based Agreement, confirmed via Edit to have a real PDF attached) | Preview shows the actual PDF (per documented behavior: "PDF viewer for file-based agreements") | Shows plain literal text **"agreement"** and a **"Document Binding: Yes / No"** checkbox block |
| 2 | Click the eye icon on "Agreement IMAGE" (image-based) | Preview shows the actual image | Identical generic content: text "agreement" + "Document Binding: Yes/No" |
| 3 | Click the eye icon on "Agreement" (text-based, per docs) | Preview shows the actual agreement text | Identical generic content again: text "agreement" + "Document Binding: Yes/No" |

**Expected:** Each record's Preview should render its actual saved content — the real agreement text, or the uploaded PDF/image — as previously documented.
**Actual:** All 3 records, despite having different underlying formats (PDF file, image file, plain text) and being of type **Agreement**, show byte-for-byte identical Preview content: the literal string "agreement" and a "Document Binding" Yes/No checkbox pair. "Document Binding" is not even an Agreement-type field — per the Create/Edit forms, it only appears on **Document - Vaccine/PCR** type compliances. This strongly suggests the Preview modal is rendering a hardcoded placeholder/wrong-type template rather than the record's real data, for every record in this environment.

**Confidence:** High — reproduced identically across all 3 available records with three different underlying formats (PDF, image, text), and cross-checked against the Edit form (which correctly shows the real PDF attached to "Agreement pdf") to confirm the Preview's content doesn't match reality.

**Note:** This contradicts the 2026-06-20 documentation, which described the View icon as showing "a DevExtreme overlay popup showing compliance content (PDF viewer for file-based agreements, text content for text-based)." Either this is a regression since that observation, or the original documentation was based on an assumption rather than a verified screenshot — worth a follow-up with the product/dev team either way.

---

## 5. API Performance — Compliance Module

Measured via the browser's Resource Timing API during real page loads. API host: `api.visipoint.uk`.

| Endpoint | Trigger | Samples | Min (ms) | Avg (ms) | Max (ms) |
|----------|---------|:-------:|:--------:|:--------:|:--------:|
| `GET /api/compliances/{...}` | Page load (list data) | 3 | 315 | 786 | 1363 |
| `GET /api/check_active_sessions/{...}` | Page load | 3 | 251 | 749 | 1321 |
| `GET /api/adminPreference/{...}` | Page load (called 2x per load) | 6 | 306 | 774 | 1379 |

**Raw samples:**
- `compliances`: 681, 315, 1363 ms
- `check_active_sessions`: 675, 251, 1321 ms
- `adminPreference`: 678, 650, 306, 330, 1298, 1379 ms

**Observation:** All three endpoints show a similar pattern — one fast load (~300ms) and other loads spiking to 1.3s+ — suggesting the variance is likely network/session-level (e.g. a shared auth/session check gating all calls) rather than endpoint-specific. Consistent with similar variance seen in the Users and Company Details sessions earlier today.

**Scope note:** Single-user, single-request latency sampling — not a concurrent load/stress test. No load-testing tool is wired into this environment.

---

## 6. What Was Not Submitted (by design, this session)

- Create Compliance — all 3 type forms filled completely, **never submitted** (no test compliance created)
- File upload (Choose File) in the Agreement type form — not clicked (native OS file picker)
- Edit Compliance — viewed pre-filled data, **did not click Update**
- Delete Compliance — confirmation modal opened and cancelled (only 3 pre-existing records in the environment)
- Export — Excel/CSV/PDF — options confirmed, **no file downloaded**
