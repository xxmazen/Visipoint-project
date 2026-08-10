# Passport Account Module — Test Experience & Knowledge Base

**Module entry point:** Cloud Dashboard sidebar → "Passport Account" (visipoint.uk), which redirects to `https://visipoint.me/dashboard`
**Last Tested:** 2026-08-02
**Tester:** Claude (automated browser testing via Chrome MCP)
**Sections Tested:** Full button/field system test — profile card, 3-dot menu (Edit my data, Change password, Two step-authentication, Deactivate my account), My Companies (+ Add Company wizard, company row navigation), My entry logs / Expected visits tabs, Sign Out.

> Read this file before testing Passport Account again. It captures a major severity revision to the previously-known "blank page" bug, plus one confirmed dead-code bug in the Add Company form's "Subdomain requirements" hint. It also documents a same-day self-correction: an initially-reported "Company Name field doesn't bind" bug was retracted after the user couldn't reproduce it — see the RETRACTED section below for the root cause of the false positive and the lesson for future sessions.

---

## MAJOR FINDING (2026-08-02): The "blank page" P1 bug does NOT block real users — severity likely overstated

Prior sessions (2026-06-30 → 2026-07-29) documented `https://visipoint.uk/passport` as rendering a completely blank page and filed it as P1. This was always tested via **direct URL navigation** (typing/loading the URL directly).

**This session tested the actual click-through path a real user takes: clicking "Passport Account" in the Cloud Dashboard sidebar.** That click does **not** reproduce the blank page — it correctly performs a full-page redirect to `https://visipoint.me/dashboard` (the standalone Passport product), which loads completely and is fully functional (profile, QuickPass QR, My Companies, entry logs, expected visits — all tested in detail below, all working).

**Root cause detail (new diagnostic, direct URL navigation only):**
- `window.location.pathname` correctly shows `/passport`
- But the mounted Vue app's router state (`vue.$route.path`) is stuck at `/` with `route.name === null` — the SPA's router never resolves/transitions to the `/passport` route on a hard/direct load
- The Vue app does mount (not literally empty DOM — there's a real Vue root with nested empty `<div>`/`<section>` elements), it just renders nothing because the router never got past `/`
- Console: zero errors. Network: only third-party Gist chat-widget requests fire (translation file, logo) — no app bundle/API requests attempt to run at all for this route

**Practical impact:** since the only user-facing entry point to Passport Account (the sidebar link) works perfectly via redirect, this bug is a **dead URL / deep-link edge case**, not a feature-blocking issue for normal usage. Recommend the team re-triage from P1 down to a lower priority, and clarify intended behavior: should `visipoint.uk/passport` itself also redirect to `visipoint.me/dashboard` (consistent with the sidebar link), or is that bare URL simply not meant to be navigated to directly? Either way it's not currently blocking any real workflow.

This does NOT invalidate the original finding — the blank page is real and reproducible on-demand — it revises the **severity and user-facing impact**, which changes based on how the page is reached.

---

## RETRACTED (2026-08-02, same-day correction): "Add Company Name field doesn't bind" — was a FALSE POSITIVE

**Original claim:** typing into "Company Name" in the Add Company wizard never updated the app's form state, permanently blocking Create.

**What actually happened:** this was a testing methodology error, not a product bug. The original check inspected `vue.name` on the nearest DOM-ancestor Vue instance found by walking `parentElement` from the Create button. That walk landed on the `CreateCompany` component, which does have a *separate, unrelated* top-level `name` data property that genuinely stays `""` — but the Company Name `<input>` is actually bound via `v-model="entity.name"` (confirmed by reading the input's vnode `data.model.expression` directly), not the top-level `name`. Checking `vue.entity.name` instead showed the typed value was correctly captured every time (`"Retest2 Co"` etc.).

**Verified on retest:** filled Company Name, Server, Subdomain, and checked the Terms checkbox in a fresh modal — **Create enabled correctly**. Did not click Create (would have created a real company on the shared test account) — cancelled once the enabled state was confirmed.

**Lesson for future sessions:** when using DOM-`parentElement`-walk to find a Vue instance (the pattern documented in this skill's JS Interaction Patterns section), be aware the first `__vue__`-bearing ancestor found this way is not guaranteed to be the component that actually owns the field you're inspecting — nested component boundaries (e.g. wizard tab wrappers) can sit in between and have their own same-named-but-unrelated data properties. Prefer walking the proper `$parent` chain and matching on `$options.name`, and always check the actual `v-model` binding expression (via the input's `$vnode.data.model.expression`) rather than guessing the bound property name from context.

---

## Minor Finding (confirmed on retest, steps corrected): "Subdomain requirements" info hint is dead code — click handler fires but nothing renders

**Location:** Add Company wizard → Subdomain field → "ⓘ Subdomain requirements" label.

**Corrected steps to reproduce** (original write-up tested this before selecting a Server, which is a different, separately-disabled state — retested properly this time):
1. Open Add Company wizard, select any Server (e.g. "visipoint.uk (for UK and Europe)") — this changes the hint's container class from `disabled-subdomain-requirements-container` (`pointer-events: none`, genuinely unclickable) to `subdomain-requirements-container` (`pointer-events: auto`, now clickable)
2. Click the "ⓘ Subdomain requirements" label/icon
3. Observe: no tooltip, popover, or modal appears anywhere on screen

**Root cause (confirmed via Vue instance inspection):** the click handler does fire and correctly toggles a Vue data flag (`openKioskInfo: false → true`) — so this isn't simply an unwired button. But there is no DOM element anywhere in the page gated by that flag (confirmed via DOM search for any `kiosk`-related class/id — zero matches). The flag's name (`openKioskInfo`) strongly suggests this was copy-pasted from an unrelated Kiosk-related component and repurposed for the Subdomain hint, but the actual content/template binding was never added (or was removed and the handler left behind). This is dead/orphaned code, not a CSS or rendering bug.

**Two distinct broken states depending on form progress:**
- **Before selecting a Server:** the hint is inert by CSS (`pointer-events: none`) — clicking does nothing because the click never reaches any handler.
- **After selecting a Server:** the hint becomes clickable, a handler does run, but still shows nothing because no template content is bound to the flag it toggles.

Either way, a user can never actually see the subdomain requirements. Low impact — the Subdomain field itself still accepts input fine without the hint.

---

## Minor/Unclear Finding: Repeated 401 Unauthorized console errors on visipoint.me/dashboard

7 identical `Error: Request failed with status code 401` exceptions fired in the console within about 1 second of each other while the Add Company modal was open (`chunk-vendors.js` XHR handler). Did not identify the exact endpoint (network request inspection was blocked by a cookie/query-string detection heuristic in the browser tool). Could be an unrelated background poll (e.g. a stale secondary token being retried), not necessarily tied to the modal or Company Name bug above. Worth a follow-up session with full network capture if this recurs.

---

## Full System Test Results — Everything Else (all confirmed working)

### Profile card (top-left card)
- **3-dot menu** opens correctly with 4 options: Edit my data, Change password, Two step-authentication, Deactivate my account (styled red, destructive)
- **Edit my data** → toggles edit mode: pencil icons appear next to Full Name, Email, Phone; "Done" button appears under avatar
  - **Full Name pencil** → opens "Edit Full Name" modal (First Name / Last Name fields, "Update my name on all my companies" checkbox, Save/Cancel). Save correctly stays disabled until a field is dirty, then enables. Cancel correctly discards changes.
  - **Email pencil** → opens "Edit Email Address" modal gated behind current password re-entry (show/hide eye toggle present). Did not proceed past this (no test credentials, and entering passwords is out of scope) — Cancel works correctly.
  - **Phone pencil** → same password-gate pattern as Email. Cancel works correctly.
  - **Avatar click (in edit mode)** → opens "Change my photo" modal (upload target, "Update my photo on all my companies" checkbox, Update/Cancel). Update correctly disabled until a photo is chosen. Cancel works correctly.
  - **Done button** → correctly exits edit mode, pencils disappear, view returns to normal.
- **Change password** (3-dot menu) → opens "Edit Password" modal, gated behind current password re-entry. Cancel works correctly.
- **Two step-authentication** (3-dot menu) → opens modal with a toggle switch and explanation text. Toggle works (visually flips, Apply button enables once changed). Cancelled without applying (did not want to enable 2FA on the shared test account).
- **Deactivate my account** (3-dot menu) → opens a clear, well-worded confirmation modal: explains the account will be permanently deleted after 30 days, is recoverable by logging in again within that window, and requires current password entry (Deactivate button stays disabled until password is entered). Cancelled without proceeding (destructive, did not want to actually deactivate the shared test account).
- **QuickPass QR code + "Download as PDF"** → works correctly. Opens a genuine PDF (hosted on S3) with a personalized "Hello Testing(uk), Thanks for creating a VisiPoint Passport account" message and the QR code image.

### My Companies section
- **"+" Add Company button** → opens the Company Dashboard creation wizard (see bug above)
- **Company row click** (e.g. "UK (Testing) at https://visipoint.uk") → correctly performs a full navigation back into that tenant's Cloud Dashboard at `visipoint.uk/`, landing on the Dashboard page fully authenticated. Round-trip navigation (Dashboard → Passport Account → back into Dashboard) works cleanly.

### My entry logs / Expected visits tabs
- **My entry logs tab** (default) → loads a grid with real historical data: Status, Company, Site, User Type, Arrival Time, Departure Time columns. Search filter on Status/Company/Site/User Type columns works correctly (tested "Signed" filter, correctly narrowed 4 rows to 3, excluding a "Denied" row).
- **Expected visits tab** → tab switch works correctly, loads a distinct grid (Compliance, Status, Company, Site, Area, Host, User Type, Input Type, Expected At, Journey Status columns), correctly showed "No expected visits yet" for this account, pagination (5/10/20) present.

### Sign Out
- Works correctly — redirects to a proper login page (`visipoint.me/login`) with Email/Phone + Password fields, "Forgot password?" link, LOGIN button, and a "ONE TIME LOGIN" passwordless option.
- **Confirmed the Cloud Dashboard session at `visipoint.uk` is entirely independent** — after signing out of Passport, `visipoint.uk/` was still fully authenticated and functional. Signing out of Passport does not affect the Cloud Dashboard session.

---

## Recommendations

1. **Re-triage the original "blank page" bug from P1 down**, given the real user-facing path (sidebar click) works perfectly. Get product confirmation on whether `visipoint.uk/passport` is meant to be a valid direct URL at all.
2. **File the "Subdomain requirements" dead-hint as a Minor ticket** — clean repro, root cause already identified (orphaned `openKioskInfo` flag with no bound template content).
3. **Do NOT file the "Add Company Name doesn't bind" bug** — retracted, was a testing error (see above).
4. Low priority: follow up on the repeated 401 errors if a future session has more time/network-capture tooling available.
