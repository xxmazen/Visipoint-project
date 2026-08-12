# Visipoint Product — Consolidated Logic Reference

**Last consolidated:** 2026-08-12
**Source:** full read of all 52 files under `D:\Visipoint md files` (Jira synthesis docs, per-module `*_Test_Experience.md` files, dated testing/bug reports)

> This is a cross-module snapshot for quick orientation — read the module's own `*_Test_Experience.md` for full detail before testing/automating that module. This file exists so a fresh session doesn't have to re-read all 52 files just to get oriented.

---

## Platform Overview

**Visipoint** = visitor/staff management platform across three surfaces:
- **Cloud Dashboard** — `https://visipoint.uk` (admin portal: users, sites, journeys, compliance, reports)
- **Passport** — `https://visipoint.me` (identity/mobile self-service, remote sign-in, geofencing)
- **Kiosk** — physical on-site device (Touch Mode check-in)

**Jira boards:** CL (Cloud, 32 epics / 676 stories) and KI (Kiosk+Mobile, 23 epics / 371 stories). Both share 9 issue types (Epic, Story, Bug, Task, Sub-task, Sub-bug, Test Case, Feature, Spike). **Test Cases currently exist only for the Survey Module** (50 CL + 50 KI, all "To Do") — no other module has Jira-tracked test cases yet.

**Core domain concepts:** Journey/Flow (kiosk step sequence per user type), User Type (visitor/staff/contractor…), Site→Area hierarchy (parent/child sign-in logic), Expected Visit, Attendance Mode (sign-in reason/code), Custom Fields (dynamic, appear as grid columns by design), Passport Account (linked identity).

**Largest/highest-risk epics (by story count):** Mobile App (KI-1414, 113), Pre-registration (CL-222, 67), Sign in/Sign out (CL-1179, 61), Users (CL-4385, 62), Custom Fields (CL-6470, 56), ACL (CL-4341, 44). **Actively in development now:** Survey Module (CL-16841/KI-8486) — CL-16617 "Unified Survey Builder Controls" in progress, which is why Survey has by far the deepest, most volatile testing history and needs the most caution about stale status.

---

## Module-by-module: open bugs, by-design behavior, contradictions

### Survey (highest churn — read `Survey_Testing_Report.md` before any session)
**OPEN:**
- CL-17685 — default 30-day Responses filter silently excludes out-of-range responses, sidebar count contradicts empty-state panel. Open 49+ days, zero movement.
- Responses sidebar hard-caps "Select All" at 20 surveys, no search/load-more (not yet filed).
- CL-17724 sub-bugs (Conditions delete-corruption / reorder-doesn't-clear) — genuinely unresolved contradiction: multi-condition builder UI appeared *removed* on visipoint.uk 2026-07-31, reappeared functional on `qa.app.d.visipoint.dev` 2026-08-04 with the bug not reproducing there. Needs a clean same-environment (visipoint.uk) retest before any status change.
- CL-17759/CL-17760 (Comparison Report tooltip overflow) — inconclusive.
- Unfiled: extreme-past date range in Responses throws a hard load error instead of empty state.

**FIXED (don't re-report):** CL-17682/17671, CL-17684/17673, CL-17683/17686, CL-17687/17688, CL-17689, CL-17843 (8/19 templates 422 — confirmed fixed 2026-07-29 with full 8/8 coverage), CL-17844. CL-17845 not reproducible — recommend closing. CL-17846 — copy rewritten, needs re-scoping not reopening.

**By-design:** Matrix builder grid is display-only (interactivity only via Preview modal). CL-17863 Matrix question type is live despite Jira showing "To Do." Journey list "Survey" column shows "-" instead of name.

**Automation caveats:** toasts live in shadow DOM, invisible to `document.body` observers — use DOM state as ground truth, not toast text. Native HTML5 drag-and-drop for reordering doesn't respond to synthetic `DragEvent` dispatch. Environment fragmentation (visipoint.uk vs `qa.app.d.visipoint.dev` vs `appqa.visipoint.me`) repeatedly yields different results for the same bug — always note which environment a result came from.

### Users grid
**OPEN:** Sorting by "Visipoint Passport" column → HTTP 400, corrupts grid state (persists via server `adminPreference` + `localStorage.vuex`) — High, recovery-via-clear-filters is session-dependent/unconfirmed. Edit User silently wipes Phone Number on save — Major, not re-verified since 2026-07-29. "Sign in/out" row action never offers Check-out (Users-grid-specific; Dashboard's bulk page does offer it correctly) — Major. Column search/filter completely inert — **High, new 2026-08-12, pre-live only, not yet confirmed on production**.

**FIXED:** "Add Label" Enter-key clearing input; duplicate "Last Name" label in Edit User modal.

**By-design:** Add User wizard gated by required "ID" field on step 1; can have 3 steps if custom fields configured on tenant.

### Announcements
**OPEN:** CL-17913 — Add Announcement 422 "selected server name is invalid" (field never exposed in UI) — High, confirmed 4/4 on "QA testing" tenant only, works fine on other tenants — appears tenant-config-specific.

**FIXED:** Bootstrap fade-transition modal-invisibility bug; Clear Filters text-search bug — both re-confirmed holding on production 2026-08-08.

**By-design/well-implemented:** required-field gating via real disabled-button state (not silent submit); Publish/Expiry date-time validation is the best-implemented in any module. Minor gap: Title accepts whitespace-only input.

### Dashboard (root `/`, not `/dashboard` which 404s)
**OPEN:** BUG-DASH-001 (High) — column sorting updates sort-arrow/state but never reorders rows. Confirmed on both production and pre-live, platform-wide.

**Retracted:** "bulk sign-in never checks out" — confirmed intentional; re-selecting the *same* area on an already-checked-in visitor correctly stays "Checked in," real checkout requires a *different* area.

### Passport Account
**OPEN (Minor):** Add Company wizard's "Subdomain requirements" (ⓘ) hint is dead code — no template content bound to the toggle.

**Downgraded, not closed:** `/passport` direct-URL navigation renders blank (Vue Router stuck) — downgraded from P1 once the real sidebar-click path was confirmed working correctly. Still reproducible via direct/hard URL nav.

**Retracted:** "Company Name doesn't bind" — false positive from inspecting the wrong Vue component instance.

### Quick Sign In
No confirmed bugs (first tested 2026-08-05). One unfiled anomaly: Sign In button stayed disabled once after heavy wizard back/forth with one user+Site/Area combo; clean retest with different user worked fine.

### Add Visits
**OPEN (Minor, cosmetic, intermittent):** stray duplicate confirmation modal with corrupted Area text briefly reappears after successful submit — no data impact, didn't reproduce on retest.

**Retracted — do not re-file:** "new-visitor Name auto-fill truncation." Root cause: First/Last Name fields live-mirror the search box and freeze at whatever text exists when an async existing-user check fires — a timing snapshot, not fixed truncation.

**Not filed, config-dependent:** "Age" custom field accepts negative values (no min set).

### Reporting (8 sub-pages, DevExtreme grids)
**OPEN, zero movement across 4-5 sessions — recommend escalating to real tickets:**
- Kiosk Logs pagination missing 25/100 (only 5/10/50)
- Print List pagination forced to 50/100/150/200 (no small sizes)
- Users Not on Site pagination changed to 50/100/150/200 (was standard before 2026-07-15)
- Export List toolbar missing Export button entirely — unconfirmed intentional vs regression

**FIXED:** systemic "Clear filters" bug across all DxDataGrid pages; Track and Trace silent no-op on empty filter now shows helper message.

**Contradiction:** original report said Track and Trace worked with User-only (no date); later sessions require both — treated as intentional tightening but not product-confirmed.

### Journey Builder
**OPEN, unverified since 2026-06-20:** Create Journey Flow form missing Host and Attendance Mode fields present on Edit — Low-Medium, never retested.

**FIXED:** journey description showing literal "null."

### Emergency Sessions / Emergency List
No open bugs (last full pass 2026-07-31). Retracted same-day: Active Session grid scan-status-only scope is intentional; Emergency Type `maxLength=20` is correct (Jira story AC of 33 is stale); plan-restricted greyed-out feature is a plan limitation, not a bug.

### Company Details
No open bugs. Silent-failure phone-save bug fixed, improved to fully client-side validation (verified zero API calls before error shows). By-design: phone accepts non-digits while typing, validates on Save only; no min/max length enforced.

### Compliance
No currently-open bugs, but **flagged as the most fragile record in the doc set**: three Preview/Edit findings (generic Preview for file-based Agreement; near-empty Preview for Document-Vaccine/PCR; Edit form not pre-populating for Document-Vaccine/PCR) were well-evidenced Major bugs across two sessions, then reclassified "by design" with **no technical rationale recorded**. Worth confirming with the user before treating as permanently settled. Still open by omission: BUG-C-001 (Document Binding radio shows only "User Profile" option) — Low/UX, never revisited since 2026-06-20.

### User Settings (6 sub-sections)
**OPEN:** BUG-002 — Attendance Modes Edit Mode exposes full JSON config blob in URL query string — Medium, zero movement since 2026-06-20. BUG-005 — Custom Fields new-section child rows don't render in grid (data saves correctly, display-only) — Medium, did **not** reproduce on pre-live retest, needs direct re-verification.

**Resolved contradiction:** Visit Permits Delete confirmation dialog does exist and works (earlier "no dialog" report was from a session where Delete was never actually clicked).

**By-design (user-confirmed):** Attendance Modes only apply to user types with Registration Method "Pre-registered by admin" AND Areas in "Sign in/out" login mode.

### Login / Sign Up / Forgot Password (2026-08-04 security pass)
Not yet in standard bug tracking, all new findings: user enumeration via One Time Login and Forgot Password response text (Medium-High); email passed as plaintext GET param on One Time Login plus permissive referrer-policy (Medium, PII leak vector); no rate limiting on One Time Login (contrasts with rate-limited Forgot Password); Sign Up's First/Last Name accept unvalidated HTML/SQLi input, stored raw (Medium, no confirmed downstream XSS). Standard login form itself: no blur-leak, no reflected XSS, HTTPS enforced.

### Infrastructure / cross-cutting
**Pre-live app-boot instability — High, escalated 2026-08-12, currently the single biggest active blocker to testing.** `document.readyState` stuck "loading" 60s-3.5min+ across 7 attempts, reproduced via both SSO handoff and direct root URL — not entity/SSO-specific. Root-caused to a blocking synchronous `<script>` (unpkg.com/leaflet, chargebee) stalling the parser ahead of the main app bundle (bundle itself verified healthy via direct fetch). Passport product unaffected — isolated to Cloud Dashboard app.

---

## Top things to keep in mind

1. **Pre-live instability** blocks reliable smoke testing platform-wide right now — not module-specific.
2. **Survey's CL-17724** needs one clean same-environment test to resolve a real contradiction.
3. **Compliance's three "by design" reclassifications** (2026-07-31) lack recorded rationale — don't treat as unquestionably settled.
4. **Users grid has two separate, unrelated High bugs active at once** (sort corruption vs. inert search) — don't conflate them.
5. Company Details, Emergency, Quick Sign In are stable (zero new findings across 3+ sessions) — lower priority for re-test versus Survey/Users/Dashboard/Announcements.
