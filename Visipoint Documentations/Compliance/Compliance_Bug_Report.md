# Compliance – Bug Report

**Date:** 2026-06-20
**Tester:** Mazen Mohamed (m.mohamed@lamasatech.com)
**Module:** Compliance (Company Dashboard)
**Environment:** UK (Testing) at https://visipoint.uk

---

## BUG-C-001: "Document Binding to" Field Shows Only One Radio Option

| Field        | Details |
|--------------|---------|
| **Severity** | Low (UX) |
| **Sub-section** | Create Compliance → Type: Document - Vaccine/PCR |
| **URL**      | `/compliance-new` |
| **Status**   | Open (UX Review) |

### Steps to Reproduce
1. Navigate to `https://visipoint.uk/compliance-new`.
2. Click the **Type** dropdown and select **Document - Vaccine/PCR**.
3. Scroll down to the **"Document Binding to"** field.
4. Observe that only one radio option is shown: **"User Profile"**.

### Expected Behavior
- A radio button group implies multiple mutually exclusive options to choose between.
- If only one binding type is supported, it should be presented as a static label or a read-only field — not a radio button group.

### Actual Behavior
- The "Document Binding to" field shows a radio button group with only one option: **"User Profile"**.
- There is nothing to select between, making the radio button meaningless in its current state.

### Impact
- Confusing UI: admins may wonder if they need to explicitly select "User Profile" or if it is already active.
- May indicate that additional binding options (e.g., "Visit", "Device") were planned but not yet implemented, leaving the UI in an incomplete state.

### Recommendation
- If "User Profile" is the only supported binding option: remove the radio button and replace with a static label or a read-only display field.
- If additional binding options are planned: implement them or add a placeholder note.
- Add a tooltip explaining what "Document Binding to User Profile" means in practice.

---

## Summary Table

| Bug ID     | Sub-section                        | Description                                              | Severity |
|------------|------------------------------------|----------------------------------------------------------|----------|
| BUG-C-001  | Create Compliance (Document type)  | "Document Binding to" shows only one radio option        | Low      |
