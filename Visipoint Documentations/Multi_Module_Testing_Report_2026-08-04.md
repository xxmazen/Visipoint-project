# Full System Testing Report (2026-08-04)

**Environment:** `qa.app.d.visipoint.dev` ("QA TESTING" entity, login via `appqa.visipoint.me`), except where noted otherwise.
**Scope:** Security testing of the login page; system testing of Forgot Password and Sign Up; then a full regression pass with valid/invalid scenarios across every remaining Cloud Dashboard module (Survey excluded per user instruction — already covered in a separate session the same day).

---

## Part 1 — Security Testing: Login Page

### Confirmed vulnerabilities

**BUG-SEC-001 (Medium-High) — User enumeration via "One Time Login" and "Forgot Password".**
Both flows return distinguishably different responses for registered vs. unregistered emails:
- Registered: "Please check your email — we sent you an email with a one time login link..."
- Unregistered: "Email address not found, use a different one" (One Time Login) / "This email or phone doesn't exist" (Forgot Password)

An attacker can use either endpoint to harvest which email addresses have accounts in the system. The standard email+password login form does NOT leak this signal on blur (confirmed safe there).

**BUG-SEC-002 (Medium) — Email address exposed in URL query string.**
The One Time Login flow navigates to `login-one-time?email=<address>` — the email is passed as a plaintext GET parameter. Combined with the response's `referrer-policy: no-referrer-when-downgrade` header (which forwards full URLs including query strings to same-scheme third-party resources), this is a real PII leakage vector via browser history, server/proxy logs, and potential referrer headers.

**BUG-SEC-003 (Medium) — No rate limiting on "One Time Login" request endpoint.**
Sent 2 real login-link emails back-to-back with no delay; both succeeded instantly with no throttling, CAPTCHA, or cooldown. Enables email-bombing abuse of real users' inboxes and removes any friction from exploiting BUG-SEC-001 at scale. Contrasts with Forgot Password, which IS rate-limited (triggered "Too many attempts, please slow down the request." after just 1-2 requests — possibly sharing a per-IP bucket with the tested endpoints).

**BUG-SEC-004 (Medium) — No input validation on Sign Up Name fields.**
Registered a real account via `/register-passport` with First Name = `<img src=x onerror=alert(1)>` and Last Name = `' OR '1'='1` — both accepted with zero validation or rejection, account created successfully. No XSS executed on typing/display (values render as plain text in the input), but this is a stored-XSS risk if these raw strings are ever rendered unescaped elsewhere (admin user lists, welcome emails, badges, etc.) — not verified either way this session.

### Observations (not vulnerabilities, but worth noting)
- **Missing security headers:** no `Content-Security-Policy`, `X-Frame-Options`, `Strict-Transport-Security`, or `X-Content-Type-Options` on the login page response. Server version disclosed (`nginx/1.29.0`).
- **Duplicate-email registration** correctly blocked with "The user email is already used" — expected/necessary behavior for a registration flow, not treated as an enumeration bug.
- Malformed email in Sign Up correctly caught on submit ("Please enter valid email."); empty-form submission correctly blocked (Register button stays disabled).
- Login form itself: HTTPS enforced, password field properly masked, no reflected XSS found anywhere tested, empty/malformed inputs handled gracefully.

### Not tested (deferred per user's instruction on password-field handling)
Failed-login lockout behavior, SQLi/XSS directly in the password field, and the full password-based Sign Up completion (blocked on a temporary password sent to a real inbox we didn't have access to check).

---

## Part 2 — System Testing: Forgot Password

- Enumeration behavior confirmed (see BUG-SEC-001 above) — same finding applies here.
- Rate limiting confirmed working (see BUG-SEC-003 note) — arguably the strongest protection on any auth-adjacent endpoint tested this session.
- Radio toggle between Email/Phone reset methods works correctly; phone country code defaults to +20 (Egypt) — not a bug, just a locale default worth knowing.
- Did not complete the full reset-link → new-password flow (password field, deferred per user's instruction).

---

## Part 3 — System Testing: Sign Up

- Valid registration (Passport Account path) succeeds; **the flow is NOT a "set your own password on this form" flow** — it emails a temporary password with "Use it to login and set your own." This is a hard boundary for automated testing without inbox access.
- Duplicate email correctly blocked.
- Malformed email correctly blocked at submit.
- Empty-form submission correctly blocked (button stays disabled).
- **No validation on First/Last Name fields** — see BUG-SEC-004.
- Did not test the "Company Dashboard" registration path (creates a new tenant) or the full password-setting step.

---

## Part 4 — Full Module Regression (valid + invalid scenarios)

### Dashboard
- Columns/filter grid loads correctly with real data (11 items).
- Date-range picker correctly normalizes reversed click order into a proper range (clicked day 8 then day 4 → range auto-sorted to 4–8).
- **Observation, not confirmed as a bug:** narrowing the date range didn't appear to change the grid's row count — needs a closer look in a future session; may be that this filter only affects a specific column (Expected Time) rather than the whole grid.
- Quick Sign In: empty submission is silently blocked (Next button, no crash) — matches the platform's known silent-submit pattern, not a bug.

### Users
- Grid loads with 327 real records across 7 pages.
- "Sign in/out" action still only ever shows the Check-in form regardless of current status — **known bug re-confirmed still present** (no check-out path from this grid).
- Add User modal: required-field gating (Add button disabled) confirmed working. A full injection-payload submission attempt was inconclusive this session due to a UI coordinate-drift artifact partway through (not a product bug) — already covered equivalently via the Sign Up name-field test (BUG-SEC-004).

### Announcements
- **New bug found — HTTP 422 on Add Announcement (Medium-High, needs verification on other environments).** Submitting a fully valid Add Announcement (title, rich-text body, target user type "Staff") fails every time with `POST /api/announcement` → 422 and the confusing, unrelated error "The selected server name is invalid." Reproduced twice consecutively. This may block all announcement creation in this specific QA tenant — worth checking whether it's environment-specific (a 2026-08-01 session on the other tenant had Add Announcement working end-to-end) or a genuine regression.
- Rich-text editor: typed HTML/XSS payloads (`<img src=x onerror=alert('xss')>`) render as literal plain text, not parsed/executed — safe.
- Empty-target submission correctly blocked (Add button disabled until a User Type or Area is selected).

### Sites & Devices
- Hierarchical Site → Area → Device grid loads correctly with real data.
- Add Site modal: empty-submission correctly blocked; malformed email in Contact Email field triggers real-time inline validation ("Invalid email format.") that keeps Add disabled — good, working validation.

### Reporting (all 8 sub-pages checked)
- **All 3 known pagination bugs re-confirmed still present, now across 5 sessions with zero movement:**
  - Kiosk Logs: only 5/10/50 (missing 25/100)
  - Print List: only 50/100/150/200 (missing smaller sizes)
  - Users not on site: also only 50/100/150/200
- History, Visit Summary (`/reporting/visit_summary`, underscore URL confirmed), Track and Trace all load and validate correctly.
- Track and Trace: empty-filter click correctly shows "Select a user and date range to show information." with no crash.
- **Observation:** Timesheet immediately showed an async "Generating your timesheet... you'll receive an email" state on page load rather than the filter form documented previously — may be a UX/default-filter change in this tenant, not confirmed as a bug.

### User Settings
- **Attendance Modes URL data exposure — re-confirmed still present.** Editing a reason navigates to `/add-reason?id=...&attendance_mode_id=...&reason_name=...&reason_type=...&from_time=...&to_time=...&attendance_codes=%5B%5D` — full config exposed in the query string.
- Custom Fields page loads correctly with existing sections (Main Sec, Visit); did not re-test the specific "new section fields don't render" display bug this session due to time.

### Journey Builder (Visit Management)
- Journey/Flows grid loads with real hierarchical data (Sites → Journeys).
- Create Journey form loads correctly with "Visit Management" type pre-selected and proper structure.

### Compliance
- Compliance list loads with 5 real records.
- Preview modal for a text-based Agreement compliance renders correctly with real question content and Yes/No options — working as expected (this is distinct from the previously-confirmed-intentional file-based Agreement behavior, which wasn't re-tested this session).

### Company Details
- Company profile loads correctly with real data (name, subdomain, package, kiosk count).
- **Phone field validation re-confirmed unchanged:** accepts non-digit characters while typing, blocks on save with "Phone number should contain only digits." — confirmed intentional behavior, not a bug (per prior correction).

### Passport Account
- **Direct URL blank-page bug re-confirmed still present** (`/passport` navigated to directly shows a fully blank page).
- **Real user path re-confirmed fully working:** clicking "Passport Account" in the sidebar correctly redirects via SSO to `visipoint.me/dashboard`, which loads and functions correctly.

### Emergency Sessions / Emergency List
- Confirmed fully working in the correct-access "QA TESTING" tenant: Emergency List loads real checked-in visitor data with Present/Not Present/Out status controls, matching documented behavior exactly.
- (Note: initial attempts in a different, plan-restricted tenant — "QA testing02" — showed the Emergency List link greyed out/404ing; this is due to that tenant's limited-access plan, not a bug in the feature itself.)

### Add Visits
- **BUG-AV-001 re-confirmed still present, with a minor variation from the original report.** Typing "Jonathan Smith" into the Visitor search box (no match found) triggered the "create new visitor" auto-fill: First Name populated as "Jonathan" (8 chars, matches the field's known 8-char truncation point), but Last Name populated as "Smi" (truncated to 3 characters) rather than being completely empty as the original 2026-07-31 report documented. The underlying defect (auto-fill mangling the typed name) is still clearly present; the exact truncation behavior may not be fully deterministic or may have shifted slightly — worth a closer look in a dedicated Add Visits session.
- Known layout-shift issue re-confirmed: selecting Site + Area causes the pre-registration-link note to render, shifting the Visitor search field's position — matches documented behavior, not a new finding.

---

## Summary of New Findings This Session

| Finding | Severity | Area |
|---|---|---|
| User enumeration via One Time Login / Forgot Password | Medium-High | Login security |
| Email exposed in URL query string (One Time Login) | Medium | Login security |
| No rate limiting on One Time Login | Medium | Login security |
| No input validation on Sign Up Name fields (stored XSS risk, unconfirmed downstream) | Medium | Sign Up security |
| Add Announcement fails HTTP 422 "server name is invalid" | Medium-High (env-specific?) | Announcements |

## Summary of Known Bugs Re-Confirmed Still Open
- Users grid "Sign in/out" never offers Check-out
- Reporting: 3 pagination inconsistencies (Kiosk Logs, Print List, Users not on site) — 5 sessions, zero movement
- User Settings Attendance Modes URL data exposure
- Passport Account direct-URL blank page (real user path unaffected)
- Add Visits BUG-AV-001 (new-visitor name auto-fill corruption)

## Summary of Confirmed-Working / Not-Bugs
- Company Details phone validation (intentional, by design)
- Add Site / Add Announcement / Add User client-side validation gating
- Rich-text XSS payloads rendered as plain text (safe)
- Passport Account real user path (sidebar click, not direct URL)
- Emergency Sessions/List (in the correct-access tenant)
- Standard login form does not leak enumeration on blur

## Not Tested / Deferred
- Password-field-dependent security tests on login (lockout, SQLi in password)
- Full Sign Up completion (temp password step)
- Full Forgot Password reset-link completion
- Company Dashboard registration path
- Custom Fields "new section" display bug re-verification
