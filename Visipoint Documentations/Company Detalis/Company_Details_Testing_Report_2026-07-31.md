# Company Details — Full System Test Report — 2026-07-31

**Page URL:** `https://visipoint.uk/company`
**Tester:** Claude (automated browser testing via Chrome MCP)
**Scope:** Full button/field coverage, valid and invalid cases — "test Company details screen by test all buttons and actions with valid and invalid cases."

Read `Company_Details_Test_Experience.md` (last updated 2026-07-15) before this session.

> **CORRECTION (2026-07-31):** BUG-1 below was reviewed by the user and confirmed to be **intentional application logic, not a bug**. The Phone field's lack of length validation is by design — only the digits-only check is intended. Retained here for the historical record of what was tested and observed; see `Company_Details_Test_Experience.md`'s "Intentional Design Decisions" section for the corrected status.

---

## Summary of Findings

| # | Finding | Severity | Status |
|---|---------|----------|--------|
| 1 | Phone field has no minimum or maximum length validation — only a digits-only check | — | **Confirmed intentional logic (2026-07-31), not a bug** |

No other new bugs found. All previously confirmed-fixed behavior (silent phone save, Two-Step Authentication role requirement, Temperature Unit persistence) re-verified and still holding.

---

### BUG-1 — Phone field accepts any digit length, no min/max validation (confirmed intentional, not a bug)

| Field | Detail |
|-------|--------|
| **Module** | Company Details |
| **URL** | https://visipoint.uk/company |
| **Severity** | — |
| **Status** | Confirmed intentional logic (2026-07-31), not a bug |

| # | Step | Expected | Actual |
|---|------|----------|--------|
| 1 | Edit Company → Change phone → enter `123` (3 digits) → Save | Either save succeeds (if 3 digits is considered valid) or a "too short" error is shown | Saved successfully — Phone Number shown as `+20123` |
| 2 | Edit Company → Change phone → enter a 30-digit string → Save | A reasonable max-length error, since no real phone number is this long | Saved successfully — Phone Number shown as `+20123456789012345678901234567890` |

**Analysis:** The only client-side validation on this field is a digits-only regex ("Phone number should contain only digits."). There is no minimum-length or maximum-length check, so clearly-invalid phone numbers (too short to dial, or far longer than any real phone number) are accepted and persisted. This is a genuine data-quality gap, distinct from the already-fixed "silent failure" bug — the error messaging itself works correctly, it just doesn't cover length.

**Recommendation:** Add a reasonable length range check (e.g., 7–15 digits, per E.164) alongside the existing digits-only validation.

---

## Full Coverage Checklist (this session)

### Page load / Info grid
- Loads matching documented baseline exactly (Company Name, Subdomain, Number of kiosks: 6, Phone: +200100639489, Package, Temperature Unit: °C).

### API Integration — Token controls
- Eye icon: masks/unmasks JWT token correctly, both directions.
- Copy button: copies token, shows "Copy Token" tooltip + green "Token Copied" success toast. Correct.
- Delete Token: opens confirmation modal with documented title/body/buttons; **Cancel** discards correctly, no data change. Delete itself not triggered (destructive, out of scope per established caution).
- Api URL link: opens `https://documenter.getpostman.com/view/28974403/2s9Ykq8gP7` correctly in a new tab.

### Edit Company modal — Phone field
- "Change" link correctly reveals country selector + empty phone input.
- Invalid — letters (`abcdef`): accepted while typing (validate-on-save, by design), blocked on Save with "Phone number should contain only digits."
- Invalid — special characters (`!@#$%^`): same behavior as letters — accepted while typing, blocked on Save with the same error.
- Invalid — too short (`123`, 3 digits): **saved successfully, no length validation** (BUG-1).
- Invalid — too long (30 digits): **saved successfully, no length validation** (BUG-1).
- Valid (`0100639489`): saves correctly, success toast shown, restored to baseline.
- Country code dropdown: opens correctly, full searchable country list with flags; selecting a country (tested United Kingdom +44) correctly updates the flag/dial code and clears the phone input. Previously only opened, never selected — now fully tested.

### Edit Company modal — Temperature Unit
- °C → °F → Save: persists correctly, confirmed on reload of the info grid.
- °F → °C → Save: persists correctly, restored to baseline.

### Edit Company modal — Two-Step Authentication
- Toggle ON correctly reveals a required "User Role" multi-select dropdown (by design, matches 2026-07-15 finding).
- SAVE CHANGES button is correctly `disabled` (confirmed via DOM `disabled` property) until a role is chosen.
- Dropdown opens and lists selectable roles (e.g., "Admin"). Not saved with a role selected — access-control change, requires explicit user sign-off per established caution.
- Change discarded via X close without persisting.

### Edit Company modal — close behaviors
- X (top-right) button: closes and discards all unsaved changes (tested with a pending country-code change) — confirmed correct.
- Backdrop click (clicking outside the modal): also closes and discards unsaved changes — confirmed correct.

### Logo
- Remove button (overlay on logo) present, inspected only. Not clicked — destructive/irreversible action, consistent with prior sessions' caution; no confirmation step is visible in the DOM, so clicking would likely remove the logo immediately.

---

## What Was Not Fully Tested

- **Remove logo** — still not clicked (destructive, no confirmation dialog observed).
- **Delete Token → Confirm** — still only Cancel tested (destructive).
- **Two-Step Authentication SAVE with a User Role selected** — still not persisted (access-control change, needs explicit sign-off).

## Cleanup

All test values (phone, temperature unit, two-step authentication) were reverted to the original baseline state by the end of the session. No test records were created (this module has no create/delete data entities beyond the single company record).
