# Company Details — Test Experience & Knowledge Base

**Page URL:** `https://visipoint.uk/company`  
**Last Tested:** 2026-07-31  
**Tester:** Claude (automated browser testing via Chrome MCP)

> Read this file before testing the Company Details page again. It captures everything learned during testing — page structure, feature behavior, automation notes, and bugs found.

---

## Page Structure Overview

The Company Details page is accessible from the sidebar under **Company Details**.

### Top Section — Company Logo
- Company logo displayed at top center
- **Edit** button below the logo — opens the Edit Company modal

### Info Grid
| Field | Value (sample) |
|-------|---------------|
| Company Name | UK (Testing) |
| Subdomain | grinta1911.visipoint.uk |
| Number of kiosks | 5 |
| Phone Number | +200100639489 |
| Package | Cloud (Enterprise) |
| Temperature Unit | °C |

### API Integration Section
- **Token** field — masked by default (dots)
  - **Eye icon** — toggles token visibility (masked ↔ plain JWT text)
  - **Copy button** (clipboard icon) — copies token to clipboard
  - **Delete Token** button (red outline) — opens confirmation modal
- **Api URL** — external link to Postman documentation
  - URL: `https://documenter.getpostman.com/view/28974403/2s9Ykq8gP7`
  - Opens in a new tab (`target="_blank"`)

---

## Edit Company Modal — Feature Behavior

### Opening the Modal
- Click the **Edit** button below the logo
- Modal loads with a brief semi-transparent animation (~3 seconds)
- Use JS click to avoid coordinate issues: `Array.from(document.querySelectorAll('button')).find(b => b.textContent.trim() === 'Edit').click()`

### Modal Contents (top to bottom)
1. **Title**: "Edit Company"
2. **X close button** (red circle, top right corner) — closes without saving
3. **Company Logo** with a **Remove** button overlay
4. **Phone** section:
   - Static display: shows current phone number + "Change" link
   - After clicking "Change": replaces with country code selector + text input
5. **Temperature Unit**: °F / °C radio buttons (°C is default)
6. **Two-Step Authentication**: toggle switch (OFF by default)
7. **SAVE CHANGES** button (dark blue, bottom of modal)

### Phone "Change" Link
- Clicking "Change" replaces the static phone display with an editable input
- Input has: country code dropdown (🇪🇬 Egypt +20 by default) + text field
- Component: `vue-country-select` (custom Vue component)
- Country code dropdown shows full list with flags, country names, and dial codes

### Country Code Dropdown
- Component class: `.vue-country-select`
- To open programmatically: `modal.querySelector('.vue-country-select').__vue__.open = true`
- First item in list: United Kingdom +44 (alphabetical, UK first)
- Full list of countries with flags
- Clicking a country updates the flag and dial code

### Temperature Unit Toggle
- Radio buttons: °F and °C
- Click by label: `Array.from(modal.querySelectorAll('label')).find(l => l.textContent.includes('°F')).click()`
- Switching works correctly — selection updates immediately

### Two-Step Authentication Toggle
- HTML: `input[type="checkbox"]` inside the modal
- JS click: `modal.querySelector('input[type="checkbox"]').click()`
- Toggle switches between gray (OFF) and green (ON)
- Note shown: "By enabling two-step authentication in your company dashboard, you require specific user roles to activate it in their Passport accounts for access."

### X Close Button
- Positioned top-right corner of modal (approximately at modal x=875, y=120 in viewport)
- Clicking X closes the modal **without saving** — all changes discarded
- Backdrop click (clicking outside modal) also closes without saving

### SAVE CHANGES Button
- JS: `modal.querySelector('.submit-btn').click()` (class: `submit-btn btn btn-primary`)
- On successful save: modal closes + green success toast appears ("Company details updated successfully.")
- On invalid phone input: API call made but **no error message shown** — silent failure (see Bug 1)
- Phone field required: empty phone shows red placeholder on validation trigger

---

## API Integration Controls

### Eye Icon (Token visibility toggle)
- Default state: token masked with dots
- Click: reveals full JWT token text (eyJ0eXAi...)
- Click again: hides token back to dots
- Eye icon with strikethrough = hidden; open eye = visible
- Positioned at right edge of Token input field

### Copy Button
- Clipboard icon button to the right of the eye icon
- Clicking copies the token to clipboard
- Brief toast notification appears (may not be capturable due to CDP freeze window)

### Delete Token Button
- Red-outlined button labeled "Delete Token"
- Opens confirmation modal with:
  - **Title**: "Delete Token"
  - **Body**: "Are you sure you want to 'Delete' Integration API key?"
  - **Cancel** button (dark blue) — closes modal without deleting
  - **Delete** button (red outline) — permanently deletes the token
- To click Cancel via JS: `Array.from(document.querySelector('.modal.show').querySelectorAll('button')).find(b => b.textContent.trim() === 'Cancel').click()`

---

## Automation Notes

### CDP Freeze Pattern
Every button click on this page triggers a 30–45 second CDP timeout before the screenshot is available. Pattern to handle:
1. Click or use JS
2. Attempt screenshot → timeout error
3. `wait` 10 seconds
4. Take screenshot again — usually succeeds

### Getting the modal state
```javascript
// Check if modal is open
document.querySelector('.modal.show')

// Get modal title
document.querySelector('.modal.show .modal-title')?.textContent

// Find buttons in active modal
Array.from(document.querySelector('.modal.show').querySelectorAll('button')).map(b => b.textContent.trim())
```

### Opening/using the country code dropdown
```javascript
const modal = document.querySelector('.modal.show');
const countryComp = modal.querySelector('.vue-country-select');
// Open dropdown
countryComp.__vue__.open = true;
countryComp.__vue__.$forceUpdate();
```

### Phone input (after clicking Change)
```javascript
const modal = document.querySelector('.modal.show');
const phoneInput = modal.querySelector('input[placeholder="Enter Phone"]');
phoneInput.value = '0100639489';
phoneInput.dispatchEvent(new Event('input', { bubbles: true }));
phoneInput.dispatchEvent(new Event('change', { bubbles: true }));
```

### Save Changes
```javascript
document.querySelector('.modal.show .submit-btn').click();
```

### Delete Token modal
```javascript
// Open Delete Token modal
Array.from(document.querySelectorAll('button')).find(b => b.textContent.trim() === 'Delete Token').click();
// Cancel from modal
Array.from(document.querySelector('.modal.show').querySelectorAll('button')).find(b => b.textContent.trim() === 'Cancel').click();
```

---

## Bugs Found

| # | Bug | Severity | Confidence |
|---|-----|----------|------------|
| 1 | ~~Phone field accepts letters without client-side validation~~ — **not a bug** | N/A | — |
| 2 | SAVE CHANGES with invalid phone: no error message shown, silent failure | Major | High |

### Bug 1 — RECLASSIFIED: NOT A BUG (validate-on-save is correct behavior)
**Steps:**
1. Open Edit Company modal
2. Click "Change" on the Phone row
3. Type letters (e.g., "abc123") in the phone input field
4. Observe: the field accepts all characters while typing
5. Click SAVE CHANGES
6. Observe: inline error "Phone number should contain only digits." appears and save is blocked

**Correction (2026-07-01):** This field validates on submit, not on keystroke — a legitimate and common UX pattern. The functional check (blocking bad data from being saved) works correctly. Accepting arbitrary characters while typing, before the user has finished and clicked Save, is not a defect. Originally mis-flagged as a bug; see [[feedback_check_known_logic_before_filing_bugs]] in memory.

### Bug 2 — No error message when saving invalid phone format
**Steps:**
1. Open Edit Company modal
2. Click "Change", type "abc123" in the phone field
3. Scroll down and click SAVE CHANGES
4. Wait for the response

**Expected:** Error message shown near the phone field (e.g., "Invalid phone number format")  
**Actual (2026-06-18):** API call is made (CDP freeze observed), modal stays open, no error text, no field highlight — complete silent failure

**UPDATE (2026-06-30): BUG 2 CONFIRMED FIXED**  
After clicking SAVE CHANGES with "abc123" in the phone field, the modal now stays open and shows inline error text: "Phone number should contain only digits." — save is correctly blocked. No data corruption occurs.

**UPDATE (2026-07-01): BUG 2 fix re-verified, still holding.** Bug 1 was reclassified as not-a-bug the same session — see above.

---

## What Was NOT Fully Tested

- **Remove logo** — button visible but not clicked (would permanently remove company logo); no confirmation dialog observed in the DOM as of 2026-07-31, so this is likely a one-click permanent removal
- **Delete Token → Confirm** — opened modal and cancelled; did not confirm delete (would destroy API token)
- **Two-Step Authentication SAVE with a User Role selected** — still needs explicit sign-off (access-control change)

~~Country code change — dropdown opened and verified, but no country was selected~~ — **now fully tested (2026-07-31):** selecting a country (United Kingdom +44) correctly updates the flag/dial code and clears the phone input.

---

## Session Update — 2026-07-15 (Smoke Test + API Performance)

**Full report:** `Company_Details_Testing_Report_2026-07-15.md`

- **Temperature Unit SAVE** — now fully tested both directions (°C→°F→save, then °F→°C→save). Persists correctly, reversible. No longer an untested gap.
- **Bug 2 (invalid phone) — re-confirmed fixed, and improved.** Submitting an invalid phone ("abc123") now shows the inline error with **zero API calls** — validation is fully client-side now (previously the fixed version still made an API call before showing the error). Verified via Resource Timing API.
- **NEW: Two-Step Authentication enable now requires a "User Role" field.** Toggling ON reveals a required `User Role` dropdown not previously documented. SAVE CHANGES is correctly `disabled` until a role is chosen (matches [[project_form_validation_logic]] — by design, not a bug). Not saved — reverted OFF without persisting, since this affects company-wide auth/access settings and needs explicit user sign-off before a live test.
- **Data drift (not a bug):** "Number of kiosks" now shows 6 (was 5 on 2026-06-18) — expected in a shared test environment.
- **API surface identified** (host `api.visipoint.uk`):
  - `GET /api/get-entity/{entityId}` — page load, avg 319ms (7 samples, 148–480ms range)
  - `GET /api/check_active_sessions/{entityId}` — page load, avg 655ms (2 samples, 247–1062ms — high variance, needs more sampling)
  - `PUT/PATCH /api/entity/{entityId}` — Edit Company save, avg 695ms (2 samples, 416–974ms)
- No new bugs found this session.

---

## Session Update — 2026-07-31 (Full Valid/Invalid Coverage)

**Full report:** `Company_Details_Testing_Report_2026-07-31.md`

- ~~NEW BUG (Minor): Phone field has no minimum or maximum length validation~~ — **CORRECTION (2026-07-31): confirmed intentional, not a bug.** Only the digits-only check ("Phone number should contain only digits.") is enforced by design; length is intentionally not restricted. See "Intentional Design Decisions" section below. Do not re-report.
- **Country code dropdown selection — now fully tested (previously only opened).** Selecting a country (tested United Kingdom +44) correctly updates the flag/dial code and clears the phone input.
- **Two-Step Authentication — role dropdown confirmed populated and functional.** Opened the "User Role" multi-select (not previously inspected past its presence); at least "Admin" is a selectable option. Save-disabled-until-role-chosen behavior (found 2026-07-15) re-confirmed via DOM `disabled` property check, not just visually.
- **X close and backdrop click — both re-confirmed to discard unsaved changes**, tested this time with a pending country-code change (not just an untouched form).
- **Temperature Unit both directions — re-confirmed persists correctly** (°C→°F→save, °F→°C→save).
- **Token controls (eye, copy, Delete Token→Cancel, Api URL link) — all re-confirmed working correctly,** matching documented behavior exactly. No regressions.
- **Logo Remove button — inspected (not clicked).** It's a plain overlay `<span class="d-block">` on the logo with no visible confirmation-dialog wiring in the DOM — suggests clicking it removes the logo immediately with no "are you sure" step, but this is inferred, not confirmed by actually clicking.

## Bugs Found — Updated Table

| # | Bug | Severity | Confidence |
|---|-----|----------|------------|
| 1 | ~~Phone field accepts letters without client-side validation~~ — **not a bug** | N/A | — |
| 2 | SAVE CHANGES with invalid phone: no error message shown, silent failure | Major | **Fixed (2026-06-30), fully client-side as of 2026-07-15** |
| 3 | ~~Phone field has no minimum/maximum length validation~~ — **not a bug, confirmed intentional 2026-07-31** | N/A | — |

## Intentional Design Decisions (Not Bugs)

| Behavior | Why it's not a bug |
|----------|---------------------|
| Phone field has no minimum/maximum length validation — only a digits-only check runs client-side (3-digit and 30-digit values both save successfully) | Intentional — confirmed by user 2026-07-31. Do not re-report. |
