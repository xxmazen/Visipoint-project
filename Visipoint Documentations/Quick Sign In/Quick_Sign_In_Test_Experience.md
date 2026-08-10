# Quick Sign In — Test Experience File

**Module:** Quick Sign In (Dashboard's "Quick Sign in" button, top-left of the grid toolbar, next to "Dashboard" tab)
**First tested:** 2026-08-05, on pre-live (`prelive.app.d.visipoint.dev`), entity "Custom field project"

---

## Page Structure

A modal wizard, opened by clicking "Quick Sign in" on the Dashboard screen:

1. **User Details** — "Search by name, phone number or email" combobox. Matching an existing user shows a single read-only "Name" field. No match expands a "create new visitor" mini-form: First Name, Last Name, User Type, Email (optional), Phone (optional).
2. **Site & Area** — Site dropdown, then Area (disabled until Site chosen). May show a Visit-Permit/journey restriction message plus a required Notes field — see Findings below.
3. **Custom Fields** — the entity's configured custom fields (Test, Test2, Test1, Age, Age2, Name, Smile rating, Toggle), all optional.

A dot-indicator with Back/Next tracks progress across steps 2-3. The "Sign in" button is present from step 2 onward and is only enabled once all validation passes — its real state is the DOM `disabled` attribute (see gotcha below).

**Unlike Add Visits**, this immediately signs the user in — it's a data-modifying action, not a future booking. Treat it with the same caution as any other action that changes real state.

---

## Findings — 2026-08-05 first session (pre-live)

### Confirmed correct behavior (not bugs)

**Last Name is required to advance past step 1** — clicking "Next" with only First Name filled does nothing (silent no-op). Fill Last Name to proceed. This differs from Add Visits, where Last Name is optional.

**New-visitor First/Last Name auto-fill is the same live-mirror-then-freeze timing behavior documented for Add Visits (see that module's BUG-AV-001 retraction) — not a bug.** Wait 3+ seconds and blur before trusting the field values in automation.

**User Type dropdown lists every configured user type for the entity**, a much longer list than Add Visits shows (includes internal/test-looking types like "aaaa", "Agreement", "Allow child", "Vaccine", "Walk-in", RTL-script entries). Expected; scroll to find a specific type.

**Visit-Permit / journey restriction check, confirmed real business logic:** selecting a Site+Area where the chosen User Type isn't permitted per the configured journey shows:

> "This user is not permitted to enter this area according to the journey. Please provide justification for this action in the notes."

This makes a **Notes** textarea required, with a genuine minimum of **20 characters** (a hint reads "You must add at least 20 characters."). Verified: a 10-character note leaves "Sign in" disabled; 39+ characters enables it. Confirmed via full valid submissions:
- New visitor ("QuickSignZZZNoMatch TestLast", User Type "Visitor") signed into Site10/field with a 39-char justification note — succeeded, grid showed Status "Signed In".
- Existing visitor ("tbjb ugib") signed into Site10/field with a justification note — succeeded.

**"Sign in" button's disabled state is not always visually obvious in a screenshot** — it's styled with `opacity: 0.65` and `cursor: not-allowed`, easy to miss against an already-dark button. Always check the DOM `disabled` attribute in automation:
```javascript
Array.from(document.querySelectorAll('button')).find(b => b.textContent.trim() === 'Sign in').disabled
```

**Cross-site auto-signout, confirmed working correctly end-to-end:** if the selected user is currently signed in at a different Area, an additional note appears alongside the permission message:

> "This user will be auto signed out from the '<area name>' area because they are being signed into another site."

Completing the sign-in produces **two grid rows** for the same visitor: a new row with Status "Signed In" (the new Area), and the visitor's prior row flips to Status "Auto Signed Out" (the old Area). Verified with "tbjb ugib," who was signed in at "default area" and got correctly auto-signed-out there upon signing into Site10/field.

**Empty submit is a silent no-op**, consistent with the platform-wide silent-submit pattern (clicking "Next" at step 1 with a totally empty form does nothing, no error).

**Cancel is clean** — closes the modal with zero side effects, confirmed via grid item-count check before and after.

### Unresolved — needs a clean re-test before filing as a bug

**"Sign in" stayed disabled once despite a valid 60+ character Notes value.** Occurred with existing user "PreliveSmoke Tester," Site "Custom field project," Area "default area," after several Back/Next navigations across steps while exploring the wizard (re-visiting step 1, coming back to step 2, etc.). Re-checked the DOM `disabled` state multiple times, added more text to the Notes field, selected a Smile rating to force re-validation — stayed `disabled` throughout. A fresh, single-pass attempt immediately after (different existing user "tbjb," Site10/field — the same Site/Area combo that had worked in the very first test) enabled correctly and completed successfully. This looks more like wizard/component state getting confused by heavy back-and-forth navigation than a general defect, but was only observed once with that specific user+Site/Area combination — **if this recurs on a clean, single-pass attempt with no repeated Back/Next, treat it as a real bug.**

### Background noise, not attributable to Quick Sign In specifically

Repeated `TypeError: this.setTimeout is not a function` exceptions and DevExtreme `W1011` warnings fire continuously in the console from a socket.io-driven grid-state-save routine on the Dashboard page, regardless of which action was just taken. Don't attribute these to a specific Quick Sign In step without separate corroborating evidence (e.g. a failed network request at the same timestamp).

---

## Reusable data note

Test visitors created/modified this session (pre-live, "Custom field project" entity):
- **QuickSignZZZNoMatch TestLast** (Visitor) — new visitor created and signed in at Site10/field with justification notes.
- **tbjb ugib** (Visitor) — existing user, signed into Site10/field (auto-signed-out of "default area" as part of the flow), then a second isolation-test sign-in with a different justification note.
- **PreliveSmoke Tester** (Visitor) — existing user, used for the unresolved-disabled-button investigation; Site "Custom field project"/"default area" sign-in was NOT completed (left cancelled, no side effects).

None of this data needs cleanup — harmless test data, safe to leave in place for future sessions.
