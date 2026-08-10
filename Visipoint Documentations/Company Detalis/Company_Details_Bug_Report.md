# Company Details — Bug Report

**Page:** `https://visipoint.uk/company`  
**Tested By:** Claude (automated browser testing via Chrome MCP)  
**Date:** 2026-06-18  
**Status:** Pending logic review

---

## Bug 1 — Phone field accepts letters without validation

**Severity:** Minor  
**Location:** Edit Company modal → Phone field (after clicking "Change")

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/company`
2. Click the **Edit** button below the company logo
3. In the modal, click the **Change** link next to the phone number
4. In the phone text field (next to the country code dropdown), type: `abc123`
5. Observe the field contents

**Expected:** Letters are blocked by client-side validation; only numeric characters accepted  
**Actual:** The field accepts all characters including letters without any restriction or visual feedback

---

## Bug 2 — No error message shown when saving with invalid phone format

**Severity:** Major  
**Location:** Edit Company modal → SAVE CHANGES button

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/company`
2. Click the **Edit** button
3. Click the **Change** link next to the phone number
4. Type `abc123` in the phone text field
5. Scroll down and click **SAVE CHANGES**
6. Wait for the response (approximately 5–10 seconds for the API call to complete)

**Expected:** An error message appears near the phone field or at the top of the modal indicating the phone format is invalid (e.g., "Phone number is invalid" or "Only numbers are allowed")  
**Actual:** The SAVE CHANGES button triggers an API call (network activity observed via CDP freeze), but the modal remains open with no error text, no field highlight, and no toast notification — completely silent failure. The user has no way to know what went wrong.

---

## Summary

| # | Bug | Severity | Status |
|---|-----|----------|--------|
| 1 | Phone field accepts letters without client-side validation | Minor | Pending review |
| 2 | No error message shown when saving invalid phone format | Major | Pending review |
