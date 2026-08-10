# Add Visits — Test Experience File

**Module:** Add Visits (`https://visipoint.uk/add-expected-visitors`, top-nav "Add Visits" link)
**Jira epic:** CL-34 — "Add expected users to an entity (admin or staff). Check if user exists, add as expected visitor or create new."
**First tested:** 2026-07-31 (this is the first-ever session for this module — no prior history)

---

## Page Structure

Three sections, top to bottom:

1. **Site & Area** — Site dropdown, Area dropdown. A "Note" links a pre-registration URL visitors can use to self-register (`/pre-registration/<code>`). In the test tenant (UK (Testing)) there is one Site with two Areas: "default area" and "Sign in/out area".
2. **Visitor Details** — a single combobox: "Search by name, phone number or email". Disabled until Site + Area are both selected.
   - If the search matches an existing user, selecting them fills the field with "`Name` - `UserType`" (e.g. "Mazen Mohamed - Visitor").
   - If the search matches nothing, a "create new visitor" mini-form expands below the search box: First Name, Last Name, User Type (dropdown: **Approval**, **Visitor** — only two options in this tenant), Email Address (optional), Phone Number with country-code selector (optional). See **BUG-AV-001** below for a real defect in how this auto-fills.
3. **Visit Date & Time** — Visit Date radios (Today / Tomorrow / Custom date with a date picker), Visit Time (custom scrollable hour/minute/AM-PM picker). A note under Visit Time reads "Selected visit time is set to (Europe/Moscow) time zone" — see the Moscow-timezone-label note below.

"Add Visit" button is the only submit control, at the bottom of the page.

---

## Automation Gotchas (for future sessions)

1. **"Add Visit" button is frequently below the visible viewport.** A coordinate click can silently land on empty page background with no visible effect. Always click it via JS text-match instead of coordinates:
   ```javascript
   Array.from(document.querySelectorAll('button')).find(b => b.textContent.trim() === 'Add Visit').click();
   ```
2. **The Visitor search field can lose typed text due to a layout shift.** Selecting Site + Area causes a "pre-registration link" note to render, which can shift the search field's position out from under a click made too early. If typed text doesn't appear, re-click the field after taking a fresh screenshot to confirm the layout has settled, then retype.
3. **The "create new visitor" First Name auto-fill is asynchronous/debounced** — reading the field's value immediately after typing in the search box can catch it mid-population. Wait ~1.5–2s (or poll) before asserting on its value. This is what nearly caused a false read during this session (see BUG-AV-001 below — the bug is real, but an early, unsettled read exaggerated it as "5 characters" when the actual truncation point is 8).
4. **Time picker default:** clicking the Visit Time field opens a scrollable hour/minute/AM-PM picker pre-populated with the current real time (e.g. clicked at 9:13pm real time → defaulted to "09:13 PM"). If several other interactions happen before you click Confirm, real elapsed time can push the previously-picked time into the past, correctly triggering "Visit time can't be before now" — this is working validation, not a bug (see Findings below).

---

## Findings — 2026-07-31 first-ever session

### ~~BUG-AV-001 — New-visitor auto-fill truncates First Name to 8 characters and silently drops everything after the first space (Last Name never populated)~~

**RETRACTED 2026-08-05 — NOT A BUG, confirmed intentional logic by the user. Do not re-file under this or any similar name.** Original write-up preserved below for historical context, followed by the corrected understanding.

| Field | Detail |
|-------|--------|
| **Module** | Add Visits |
| **URL** | https://visipoint.uk/add-expected-visitors |
| **Severity** | ~~Medium~~ N/A — not a defect |
| **Status** | Retracted 2026-08-05 |

| # | Step | Expected | Actual (as originally observed, 2026-07-31) |
|---|------|----------|--------|
| 1 | Select Site + Area, type a search string with no matching existing user, e.g. `"QATestNewVisitor123"` (single word, 20 chars) into the Visitor search box | "create new visitor" form expands with First Name pre-filled from the typed text (verbatim, or at minimum not silently truncated) | First Name field populated with only the **first 8 characters**: `"QATestNe"`. |
| 2 | Same flow, type `"Jonathan Smith"` (two words) | First Name = "Jonathan", Last Name = "Smith" (standard first/last split) | First Name = "Jonathan"; **Last Name field stayed completely empty**. |

**What was originally concluded (now known to be incomplete):** that this was a fixed-length JS truncation bug (e.g. a `.slice(0, 8)` call) with Last Name never populating at all.

**Corrected understanding (2026-08-05, pre-live re-test):** Retyped controlled test names (`"Alexander Bright"`, `"Alexandrina Brightwood"`) and — critically — **waited 3-4 seconds and clicked elsewhere to blur before reading field values**, instead of reading immediately after the type action (the mistake in every earlier session, including this file's own Automation Gotcha #3 above, which flagged the *symptom* without following through on the fix). Results:
- `"Alexander Bright"` → First Name **"Alexander"** (full 9 chars, correct), Last Name **"Bri"** (partial, 3 of 6 chars) — stable after blur, not a mid-read artifact.
- `"Alexandrina Brightwood"` → First Name **"Alexandrina"** (full 11 chars, correct), Last Name **""** (empty) — stable after blur.
- A single unbroken word, `"PreliveSmokeTestVisitor"` (24 chars) → First Name **"PreliveSmoke"** (12 chars, partial).

This rules out both a fixed 8-character cap and "Last Name never populates" — Last Name **does** populate, and First Name is **not always truncated**. The actual mechanism: **First/Last Name mirror the search box's text live as you type, and freeze at whatever text exists the instant an async "does this match an existing user" check fires** (the same debounced lookup that also drives the "no options available" / matching-user dropdown). Total characters landed varies session-to-session (8, then 3 ["Smi"], then 12, then 9+3, then 11+0) because it's a **timing snapshot**, not a fixed offset — it depends on how much was typed before that async check kicked off, which in turn depends on typing speed/pauses. Confirmed via direct-typing test (unchanged from the original finding) that both First Name and Last Name independently accept up to their real 20-char HTML `maxLength` with no bug when typed directly.

**Why this isn't a bug:** the fields stay fully editable after the freeze — nothing is submitted until the user reviews and clicks "Add Visit," and the confirmation modal always shows the final, actually-submitted text (verified correct/uncorrupted in every test today). A real user typing a name continuously, without a mid-word pause, is unlikely to trigger the freeze before finishing. Automated `type` actions (with per-keystroke round-trip delay) and users who pause mid-typing are the main way to observe partial text — and in both cases, the fields are still there to fix before submitting.

**Lesson for future sessions:** when a field's value is populated by JS in response to typing/search (debounced auto-fill, live mirroring, etc.), **always wait for network/debounce to settle (3+ seconds) and blur the field before reading its value** — reading too early doesn't just risk exaggerating a truncation length (as Gotcha #3 above already warned), it can manufacture an entirely wrong bug classification (here: "never populates" vs. "populates late/partially depending on timing").

---

### Confirmed correct behavior (not bugs)

**Duplicate active-visit detection works correctly.** After successfully adding an expected visit for "Mazen Mohamed" on "Tomorrow" (01 August 2026) at "UK (Testing) - default area", attempting to add a second visit for the same visitor on the same date (different time, 09:33 PM vs the original 09:26 PM) correctly showed **"An expected visit already exists, try a different date."** and blocked the submission. This also served as indirect proof that the original submission had succeeded (see the corrupted-second-modal item below).

**Stale-time validation works correctly.** Early in testing, selecting "Today" + a time picker default that had since passed (several other interactions happened between opening the time picker and clicking Confirm) correctly produced **"Visit time can't be before now."** Investigated to rule out a timezone bug — confirmed via `Intl.DateTimeFormat().resolvedOptions().timeZone` that the browser's real system timezone is `Africa/Cairo` (UTC+3, same offset as Moscow in summer), and the validation was simply catching genuinely-elapsed real time. Not a bug. (The "(Europe/Moscow)" label itself is a separate, still-unresolved cosmetic oddity — see below.)

**"An active visit already exists" on a same-day conflict is also correct** — separately hit this message when trying to add an Expected visit for a visitor ("Mazen Mohamed") who was already in "Checked in" status at the same Site/Area *today*, a leftover state from an earlier Emergency Sessions test session (checked in via Dashboard Quick Sign In, and — per the already-documented Users-grid "no check-out path" bug — never signed back out). The system correctly blocked a conflicting visit rather than silently allowing an overlap. Worked around by testing with a "Tomorrow" date instead.

**Empty-submit silent failure — re-confirmed, by-design, do not re-report.** Clicking "Add Visit" with all fields empty produces no validation message and does nothing visible; this matches the platform-wide silent-submit pattern already documented for every other module.

**User Type dropdown (new-visitor form) only offers "Approval" and "Visitor" in this tenant, and neither shows additional custom fields or a profile-photo prompt when selected.** Consistent with CL-12750 ("profile photo prompt by user type") still being an open To Do story — not yet implemented, not a bug.

---

### Unresolved-then-resolved: corrupted second confirmation modal

**What was observed:** After filling a valid Add Visit (Mazen Mohamed, Tomorrow 01 Aug 2026, 09:26 PM, UK (Testing) - default area) and clicking Confirm on the first "Add Visit" confirmation modal, a **second** confirmation modal appeared immediately after, showing corrupted text: `Are you sure you want to "Add" A visit for Mazen Mohamed in "UK (Testing) - " as a "Visitor" on 01 August 2026 at 09:26 PM ?` — note the blank Area (`"UK (Testing) - "` instead of `"UK (Testing) - default area"`). Behind this second modal, the form fields had already visibly reset to empty (Visitor field back to placeholder, Visit Date back to "Today", Visit Time cleared) — as if the first submission had already completed and the form had reset for a new entry.

**Action taken at the time:** Clicked **Cancel** on the second modal rather than Confirm, out of caution against a possible duplicate submission, since it wasn't yet clear whether this was a second independent action being requested or a stale re-render of the first.

**Resolution:** Re-filled the identical visit (Mazen Mohamed, Tomorrow, default area) with a different time (09:33 PM) and submitted. This time only a single, correctly-worded confirmation modal appeared (`"UK (Testing) - default area"`, no blank Area), and after confirming, the page correctly showed **"An expected visit already exists, try a different date."** — proving the *original* 09:26 PM visit really had been created successfully the first time, and that clicking Cancel on the corrupted second modal did not lose or corrupt any data.

**Conclusion:** This is a **real but Minor, cosmetic-only bug** — a stray/duplicate confirmation-modal re-render with stale/blank interpolated data fires right after a successful Add Visit confirm, while the form has already reset behind it. It does not cause data loss, a failed submission, or a duplicate visit (confirmed the underlying visit was created exactly once, whether the second modal is confirmed or cancelled — cancelling is safe). Worth filing as Minor/cosmetic against CL-34, low priority given zero functional/data impact.

---

### Not testable in this environment

**CL-8426 ("shouldn't be able to add an Expected visit in any remote area") — could not be exercised.** The UK (Testing) tenant has exactly one Site with two Areas ("default area", "Sign in/out area"), and per `Sites & Devices` (`/sites`), **Geofencing is OFF for both** — there is no Area in this tenant currently configured as "remote"/geofenced to test the restriction against. Testing this would require enabling Geofencing on an Area first, which changes shared tenant configuration and could affect other modules' tests — did not do this without explicit sign-off. Flag to the team: either confirm Geofencing = "remote" is the right proxy to test this rule, or provide/point to an environment with a geofenced remote area configured.

---

### Cosmetic oddity (not filed, needs product input)

**"(Europe/Moscow)" timezone label under Visit Time.** The UK (Testing) entity's Visit Time picker note reads "Selected visit time is set to (Europe/Moscow) time zone," but the actual browser/system timezone during testing was `Africa/Cairo` (UTC+3 — happens to share the same summer offset as Moscow, which is why validation behaved correctly and didn't expose a functional bug). Worth flagging separately since a UK-named test entity showing a Moscow timezone label looks like a configuration mismatch, even though no functional issue was observed as a result.

---

## Reusable data note

Test visitor used throughout: **Mazen Mohamed** (existing "Visitor" type user, searchable by name). He has a real Expected Visit now on record for **01 August 2026, 09:26 PM, UK (Testing) - default area** as a result of this session — leave this in place (do not delete) unless a future session needs a clean slate, since it's harmless test data and deleting it isn't necessary for other modules.

---

## 2026-08-05 — First session on the new **pre-live** server (`appprelive.visipoint.me` → `prelive.app.d.visipoint.dev`)

**Context:** Pre-live is a third, distinct Visipoint environment (alongside production-style `visipoint.uk` and QA's `qa.app.d.visipoint.dev`), just handed to the user for testing. Entity used: **"Custom field project"** (chosen by the user specifically — this entity's Add Visits form has several custom fields configured: Test1, Test2 (radio), Age, Age2 (date), Name, plus a smiley-face rating widget — all rendered correctly, consistent with the platform-wide "custom fields appear in forms by design" rule). Site: **Site10**, Area: **field**.

**Before reaching Add Visits — dashboard SSO load took 2.5+ minutes, self-resolved (not yet confirmed reproducible):** Clicking into "Custom field project" from the Passport "My Companies" list triggers the same encrypted-blob SSO handoff (`/sso?id=...&atex=...&entity_id=...&ip=...`) documented for the QA environment, but on pre-live the landing page stayed on a **completely blank white screen for over 2.5 minutes** — `document.readyState` stuck at `"loading"`, `#app` div empty (Vue never mounted), zero network requests captured. DOM inspection found two **synchronous, blocking** `<script>` tags (no `async`/`defer`) loading `unpkg.com/leaflet@1.7.1` and `unpkg.com/leaflet-control-geocoder@3.3.1` ahead of the app's own bundle — if that CDN request stalls, the HTML parser blocks and the whole app hangs. The page eventually did load correctly on its own (no reload/retry needed) while we were mid-conversation, so this is **not a hard block**, but a 2.5+ minute cold load is a real reliability concern worth re-testing on a future session to see if it's consistent or a one-off. Flagging as a possible finding, not yet filed — see `Jira_Visipoint_Knowledge_Synthesis.md` Section 4.

**New-visitor auto-fill behavior observed again (12-char partial capture) — later fully re-investigated and RETRACTED as a bug the same day, see the corrected write-up below and at the top of the 2026-07-31 section.** Typed `"PreliveSmokeTestVisitor"` (24 chars) into the Visitor search box with no matching existing user → First Name auto-filled to `"PreliveSmoke"` (12 characters). At the time this was logged as "BUG-AV-001 re-confirmed, 3rd truncation length" — that conclusion did not yet account for the timing/live-mirror mechanism confirmed later in this same session (see "Corrected understanding" above). Not a bug.

**Layout-shift search-field gotcha reproduced exactly as documented (item #2 above):** clicked the Visitor search box immediately after Area selection rendered the pre-registration-link note, before the layout had settled — typed text landed nowhere and the field showed empty on the next screenshot. Re-clicking the field at its new (shifted-down) position and retyping fixed it. Not a bug, just confirms the existing automation gotcha still applies here.

**No "(Europe/Moscow)" timezone label mismatch on this entity** — the Visit Time note correctly read "(Africa/Cairo) time zone," matching the actual browser timezone. Either this is entity/tenant-specific (unlike whatever entity showed the Moscow label previously) or the label has been fixed generally; worth noting as a positive, not conclusive either way since it's a different tenant.

**Full Add Visit flow confirmed working end-to-end:**
1. Site/Area selection (vue-multiselect dropdowns) — works correctly.
2. New-visitor creation — mini-form expands correctly, User Type "Visitor" selectable, Last Name/Email fillable manually (see corrected BUG-AV-001 understanding above — not a bug).
3. Visit Date "Today" + Visit Time picker (hour/minute/AM-PM scrollable) — sets correctly, validated against real current time (08:58 PM selected while real time was 6:57 PM — correctly accepted as future).
4. Confirmation modal — showed fully correct, non-corrupted text (`PreliveSmoke Tester in "Site10 - field" as a "Visitor" on 05 August 2026 at 08:58 PM`) — **the known cosmetic stray-second-modal bug did NOT reproduce this run** (intermittent, consistent with prior notes).
5. Submission → "Created successfully" toast, form reset cleanly, no stray modal.
6. **Existing-user search correctly found the just-created visitor** ("PreliveSmoke Tester - Visitor") on a second visit attempt.
7. **Duplicate-visit detection confirmed working** — submitting a second visit for the same visitor on the same date (different time, 08:03 PM vs original 08:58 PM) correctly blocked with "An expected visit already exists, try a different date."

**Net result (initial pass): no confirmed functional bugs on pre-live. One reliability observation (slow initial SSO/dashboard load) flagged for re-verification, not yet a confirmed bug. See the full valid/invalid test matrix below for the complete follow-up pass.**

---

## 2026-08-05 (continued) — Full valid/invalid scenario matrix, per user request

Following the BUG-AV-001 retraction above, the user asked for exhaustive valid/invalid-scenario coverage on Add Visits, with any new discoveries folded into the skill. Full results:

### Past-time validation (Today + time before now)
Selected 01:03 PM while real time was 7:13 PM (same day). **Correctly blocked**: inline red error "Visit time can't be before now." appeared immediately on selection, field got a red border, and clicking "Add Visit" produced no confirmation modal and no submission (confirmed via DOM check — no `.modal.show`, no success toast). Real, working validation. Not a bug.

### Past-date validation (Custom date)
Opened the Custom date calendar (Aug 2026): dates before today (Aug 1-4) rendered greyed-out/disabled; clicking one did nothing (Visit Date field stayed empty, calendar stayed open). **Past custom dates are unselectable at the UI level** — no code path exists to even attempt submitting one. Not a bug. (Note: "Custom date" radio itself is `disabled` in the DOM until Site+Area are selected — same dependency as Visitor Details, not a bug, just reproduce Site+Area selection first if it looks broken.)

### Empty submit
Clicked "Add Visit" with the entire form empty (fresh page load). No confirmation modal, no error, no visible effect — silent no-op. Matches the platform-wide silent-submit-on-empty-form pattern. Not a bug.

### Duplicate-visit / existing-visitor search
Re-confirmed both: existing-user search correctly finds and reuses a previously-created visitor (no auto-fill/truncation behavior — that only applies to the *no-match* path), and submitting a second visit for the same visitor on the same date is blocked with "An expected visit already exists, try a different date."

### New-visitor full-field valid submission
Created "Alexandrina Brightwood" (Approve01 user type) with: valid email (after first testing an invalid one, see below), valid Egyptian phone number, Test2 radio = "1", Test1 text field, Name textarea field (SQLi payload, see below), Visit Date "Tomorrow". Confirmation modal showed the fully correct, non-truncated name and all details; submission succeeded with a clean form reset. Proves the new-visitor path works end-to-end once First/Last Name are reviewed/corrected (or already complete) before submit.

### Email format validation
Typed `not-a-valid-email` into Email Address (Optional) → real-time inline error "Please enter valid email" appeared, field got a red border. Typed a valid address afterward → error cleared immediately. Real-time client-side validation, working correctly. Not a bug.

### Phone format validation
Typed `abcXYZ<script>` into Phone Number (Optional) → **every character was rejected at the input level**, field stayed completely empty (confirmed via DOM: `value: ""`). This is stricter than Company Details' phone field (which allows typing non-digits but only validates on save) — Add Visits' phone field is digits-only from the first keystroke. Typed a valid 10-digit number afterward → accepted normally. Not a bug.

### XSS/SQLi payloads in custom fields
- `Test1` (short text custom field, `maxLength=20`): typed `<img src=x onerror=alert(1)>` (29 chars) → truncated to `<img src=x onerror=a` at exactly 20 characters, standard native HTML `maxLength` behavior. No injection risk (never executes, plain attribute truncation).
- `Name` (textarea custom field, `maxLength=255`): typed `' OR '1'='1` → accepted in full, stored as literal text. Consistent with the platform's general "no input validation, but safe plain-text rendering" pattern already documented for Sign Up Name fields and Company Name. Not a bug.

### Age custom field (number-type)
Typed `abcXYZ-99` into "Age" (a native `type="number"` input) → non-numeric characters were rejected at the input level, but the negative number **`-99` was accepted** with no minimum/range validation (`min`/`max` attributes both empty in the DOM). **Minor observation, not filed as a bug** — this is very likely a per-field configuration gap (User Settings → Custom Fields lets you set min/max per number field) rather than a code defect, specific to how this custom field happens to be configured on the "Custom field project" entity. Worth a product mention if seen elsewhere, but don't file without first checking that field's own configured min/max.

### Net result of the full matrix
No new confirmed bugs found beyond what's already logged. One config-dependent minor observation (Age field's missing min constraint) worth a product conversation but not a filed bug. The BUG-AV-001 retraction (above) is the most significant outcome of this session — the skill's "Add Visits" section and the QA Rules understanding have been corrected accordingly.
