# Compliance — Full System Test Report — 2026-07-30

**Page URL:** `https://visipoint.uk/compliances`
**Tester:** Claude (automated browser testing via Chrome MCP)
**Scope:** Full field/button/checkbox coverage — "make the same test on Compliance feature" (following the Users grid session earlier the same day).

Read `Compliance_Test_Experience.md` and the 2026-06-20 / 2026-07-01 / 2026-07-15 prior reports before this session.

> **CORRECTION (2026-07-31):** Findings #1–#3 below were reviewed by the user and confirmed to be **intentional application logic, not bugs**. They are retained here for the historical record of what was tested and observed, but should NOT be treated as open defects — see `Compliance_Test_Experience.md` and the `visipoint-module-testing` skill's QA Rules table for the corrected status.

---

## Summary of Findings

| # | Finding | Severity | Status |
|---|---------|----------|--------|
| 1 | Compliance Preview modal shows wrong/generic content for **file-based Agreement** compliances | — | **Confirmed intentional logic (2026-07-31), not a bug** |
| 2 | Compliance Preview modal shows **almost no content** for **Document - Vaccine/PCR** compliances | — | **Confirmed intentional logic (2026-07-31), not a bug** |
| 3 | Edit Compliance form fails to pre-populate Name, Select Protocols, and Accept Negative PCR (+ PCR-valid-days) for **Document - Vaccine/PCR** records | — | **Confirmed intentional logic (2026-07-31), not a bug** |
| 4 | Create-form "Add"/"Create" button silently requires both Positive AND Negative answer labels filled, even when only one checkbox is checked | — | Confirmed behavior, not filed as bug (matches known silent-validation pattern; documented as a UX note) |

---

### BUG-1 — Compliance Preview broken for file-based Agreement compliances (Major, reconfirmed + scoped)

| Field | Detail |
|-------|--------|
| **Module** | Compliance |
| **URL** | https://visipoint.uk/compliances |
| **Severity** | Major |
| **Status** | Still present (first found 2026-07-15); this session narrows the exact scope |

**Original finding (2026-07-15):** all 3 pre-existing records ("Agreement pdf", "Agreement", "Agreement IMAGE") show identical generic placeholder text "agreement" plus a "Document Binding: Yes/No" checkbox pair in the Preview modal (eye icon) — a field that only conceptually belongs to Document-Vaccine/PCR type, not Agreement.

**This session's scoping test:**

| # | Step | Result |
|---|------|--------|
| 1 | Create a **text-based** Agreement ("Enter text manually", real agreement text, custom answer labels "I Agree"/"I Disagree") → open Preview | ✅ **Correct** — shows real type label, real agreement text, real custom answer labels |
| 2 | Create a **file-based** Agreement ("Upload file", real image uploaded, custom labels "Confirmed"/"Declined") → open Preview | ❌ **Broken** — shows generic "agreement" text (not the file), and mislabels the answer checkboxes as "Document Binding: Confirmed / Declined" instead of rendering an image/PDF preview |

**Conclusion:** the bug is specific to Agreement compliances where `Agreement Format = Upload file (PDF or image)`. Text-based Agreements preview correctly. This also confirms the bug is not a legacy-data artifact — it reproduces on a brand-new record created and previewed in the same session.

**Recommendation:** the Preview component's Agreement-type branch appears to only handle the text-format case; the file-format case is falling through to whatever default/placeholder rendering produces the "agreement" + "Document Binding" output (possibly the same fallback used for other broken paths — see BUG-2).

---

### BUG-2 — Compliance Preview shows almost no content for Document - Vaccine/PCR (Major, new)

| Field | Detail |
|-------|--------|
| **Module** | Compliance |
| **URL** | https://visipoint.uk/compliances |
| **Severity** | Major |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Create a Document - Vaccine/PCR compliance (Desired Outcomes labels, 2 protocols selected, Accept Negative PCR ON, 90 days) | — | Created successfully |
| 2 | Open Preview (eye icon) on the new record | Should show type, desired outcomes, selected protocols, PCR settings | Modal shows **only the compliance name** — no type label, no desired outcomes, no protocols, no PCR settings. Confirmed stable after a few seconds' wait (not a loading race condition) |

**Recommendation:** the Preview component appears to have no rendering branch at all for the Document - Vaccine/PCR type — it should be added, mirroring the working Questionnaire/text-Agreement branches.

---

### BUG-3 — Edit form fails to load Name/Protocols/PCR settings for Document - Vaccine/PCR (Major, new)

| Field | Detail |
|-------|--------|
| **Module** | Compliance |
| **URL** | `https://visipoint.uk/compliance/update/{id}` |
| **Severity** | Major |
| **Status** | New |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Create a Document - Vaccine/PCR compliance named "QA Test Vaccine PCR" with 2 protocols selected and Accept Negative PCR ON (90 days) | — | Created successfully; confirmed the name appears correctly in the Compliance List grid |
| 2 | Click Edit (pencil icon) on that record | Form pre-populates with all saved values, matching the Edit behavior already confirmed correct for Questionnaire and Agreement types | **Compliance Name field is empty** (shows placeholder only). **Select Protocols field is empty** (no pills, placeholder only). **Accept Negative PCR toggle is OFF** and the "PCR valid for how many days?" field doesn't even appear. Reconfirmed via a hard page reload — not a transient render issue |
| 3 | (Control check) Type field, Document Uploaded/Not Uploaded checkboxes + their custom labels | — | These 4 fields **do** load correctly — only Name, Protocols, and the Accept-Negative-PCR block fail to populate |

**This is a display/pre-population bug only, not real data loss** — the record's actual saved name is correctly shown in the Compliance List grid throughout, so the underlying save is fine; only the Edit form's initial field population is broken for this type.

**Scope check:** re-tested the equivalent Edit flow for a file-based Agreement record created the same way — Name, Type, Agreement Format, and both Desired Answer labels all pre-populated correctly there (only the file thumbnail itself didn't render, most likely because the test file used was a minimal synthetic 1×1 PNG rather than a real image — not being treated as a confirmed bug). This confirms BUG-3 is specific to the Document - Vaccine/PCR type's Edit form, not a general Edit-form regression.

**Recommendation:** the Edit form's Document-Vaccine/PCR field-population logic needs the same fix as BUG-2's Preview — likely the same underlying "no handler for this type" root cause in a shared component.

---

## Full Coverage Checklist (this session)

### Compliance List page
- Grid layout (Name, Date, Actions) matches documented baseline exactly — 3 pre-existing Agreement records unchanged.
- Name column search filter: valid substring match ("pdf" → 1/3 items), no-match string ("No records yet"). Both correct.
- Column Chooser: Version column toggled on — still shows real GUID values (consistent with 2026-07-15 finding, data drift from the original "no value" baseline — cosmetic only, purpose still undocumented in UI).
- Export dropdown: Excel/CSV/PDF options present, not triggered (per policy against downloading files without explicit request).
- Pagination size selector (5/10/25/50/100): works correctly.

### Row actions
- **Edit** (pencil): correct for Questionnaire and both Agreement formats; broken for Document-Vaccine/PCR (BUG-3).
- **View/Preview** (eye): correct for Questionnaire and text-Agreement; broken for file-Agreement (BUG-1) and Document-Vaccine/PCR (BUG-2).
- **Delete** (trash): confirmation modal shows correct record name every time; Cancel and confirm-delete both work correctly with no spinner freeze, tested across all 3 compliance types.

### Create Compliance — all 3 types tested end-to-end
- **Questionnaire:** Compliance Name + Type dropdown (all 3 types listed correctly) → Question field → Desired Answer checkboxes/labels → ADD QUESTION (correctly shows inline "Fields above should be filled first" validation until both Positive and Negative labels are filled, regardless of checkbox state) → delete-question (trash) icon works → full submission succeeds ("Created successfully") → Preview renders correctly.
- **Agreement (text):** Agreement Format radio, Agreement Text textarea, Desired Answer labels → Create button same "both labels required" quirk as Questionnaire's ADD QUESTION (not previously documented for the main Create button) → submission succeeds → Preview renders correctly.
- **Agreement (file):** Upload file radio, file input (uploaded a synthetic test PNG via direct DOM file assignment), Desired Answer labels → submission succeeds → Preview broken (BUG-1).
- **Document - Vaccine/PCR:** Desired Outcomes checkboxes + labels, Select Protocols multi-select (all 4 protocols present, multi-select works, removable pills), Document Binding radio (User Profile only, as documented), Accept Negative PCR toggle correctly reveals "PCR valid for how many days?" number input (native number field, correctly rejects non-numeric "abc", accepts "90") → submission succeeds → Preview broken (BUG-2), Edit broken (BUG-3).
- Empty-submit (Create button with no fields filled): silent no-op, no error — matches app-wide by-design pattern.

### Edit Compliance
- Type field is read-only for all types (confirmed, matches documented intentional design).
- No Cancel button on Edit form (confirmed, matches documented intentional design).
- Questionnaire and Agreement (both formats) Edit forms pre-populate correctly.
- Document-Vaccine/PCR Edit form pre-population broken (BUG-3).

---

## What Was Not Fully Tested

- Actual Excel/CSV/PDF export download (not triggered per policy).
- Real (non-synthetic) PDF/JPG file upload and its thumbnail rendering in Preview/Edit — only a minimal synthetic 1×1 PNG was used; a real-world file might render differently, though the BUG-1 finding (wrong Preview content) is independent of file validity since it reproduced identically on the pre-existing real PDF/image records too.
- Compliance attachment to a Journey Builder flow (out of scope for this module-only pass).
- Multiple-question Questionnaire compliances with 3+ questions (only tested up to 2).

---

## Cleanup

All 4 test records created this session (QA Test Questionnaire, QA Test Agreement Text, QA Test Agreement File, QA Test Vaccine PCR) were deleted at the end of the session via the UI's own Delete confirmation flow. The Compliance List is back to its original 3 pre-existing records (Agreement pdf, Agreement, Agreement IMAGE).
