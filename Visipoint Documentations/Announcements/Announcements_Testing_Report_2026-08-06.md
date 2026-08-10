# Announcements — Testing Report, 2026-08-06

**Environment:** QA (`qa.app.d.visipoint.dev`), logged in via `appqa.visipoint.me`, entity **"QA testing"** (0 pre-existing announcements at session start).
**Scope:** Full valid/invalid scenario pass — every button, every field, create/edit/delete cycle attempted.
**Tester:** Claude (automated browser testing via Chrome MCP)

---

## Part 1 — Grid-level controls (all confirmed working, no change from prior sessions)

- **Columns / Column Chooser:** opens correctly, unchecking/rechecking "Title" hides/shows the column and shifts the grid layout correctly.
- **Export dropdown:** Excel/CSV/PDF options present, closes cleanly without triggering a download when clicked outside.
- No pre-existing announcements to test filter/sort/Clear-filters behavior against this session (grid was empty) — these were already verified clean in the 2026-08-01 session and not expected to have regressed; not re-tested this session due to the Add-flow blocker below consuming the available time.

---

## Part 2 — Add Announcement: full field-by-field validation mapping

Unlike Add Visits' platform-wide silent-submit pattern, this form has **real client-side gating** on the Add button (`disabled` DOM attribute):

| Field | Requirement | Notes |
|---|---|---|
| Title | Required | **Accepts whitespace-only text** (`"   "` still enables Add) — no trim validation. Minor gap, not filed as a standalone bug. |
| Announcement (body) | Required | Rich text editor; plain text also accepted. |
| URL | Labeled "(optional)" | Has **genuine real-time format validation** — invalid values show red border + "Invalid URL." and disable Add, even though the field can be left empty entirely. |
| Announcement target (User type or Area) | Required (at least one value) | User type is a multi-select checkbox dropdown (Staff, Staff02 available on this entity). |
| Urgent / Pinned toggles | Optional | Cosmetic booleans, toggle freely, no validation. |
| Publish (Immediately / Custom date+time) | — | Custom calendar disables all past dates (unclickable, real no-op). For today's date, time picker also disables past hours/AM-PM relative to real current time. For a future date, all 24 hours are selectable — a bold/highlighted hour is just the default suggestion, not a disabled/enabled signal (verified by clicking a non-bold hour and confirming it registers). |
| Expiry (Never / Custom date+time) | — | **Enforces Expiry ≥ Publish as a real cross-field constraint**, not just "≥ today." Verified by setting Publish to a future date (Aug 9) — the Expiry calendar's minimum-selectable date shifted to match (Aug 6-8 became unclickable). Verified at time-level too: with Publish = 01:26 PM, Expiry's "AM" period became unclickable same-day, and only hours/minutes ≥ Publish's time were selectable. This is the most rigorous date/time validation seen in any module tested this project. |

---

## Part 3 — BUG-ANN-001: Add Announcement always fails (HTTP 422)

**Filed as [CL-17913](https://lamasatech.atlassian.net/browse/CL-17913)** — Sub-bug of CL-17095, assignee Moataz Khaled, priority High.

Every Add Announcement submission attempt failed identically:
- `POST https://qa.api.d.visipoint.dev/api/announcement` → **HTTP 422**
- Error toast: **"The selected server name is invalid."**
- No announcement created; grid remained empty throughout the session.

### Reproduction attempts (4/4 failed, isolating one variable each time)

| # | Configuration | Result |
|---|---|---|
| 1 | Full form: Title, Body, URL="https://example.com", Urgent=on, Pinned=on, target="Staff", custom Publish (Aug 9, 1:26 PM), custom Expiry (Aug 9, 2:31 PM) | 422 |
| 2 | Same as #1, URL field cleared to empty | 422 (rules out URL as cause) |
| 3 | Simplest possible valid form: Title, Body, target="Staff", Publish=Immediately, Expiry=Never, no URL, toggles off | 422 |
| 4 | Fresh page load, fresh minimal form (different Title/Body text), same entity | 422 |

Client-side validation (the Add button's enabled/disabled state) passed correctly before every attempt — the failure is purely server-side. The error text references a "server name," a concept never exposed anywhere in the Add Announcement UI.

### Automation gotcha discovered while investigating

A plain `button.click()` fired via `javascript_tool` does **not** reliably trigger this form's real Vue submit handler — confirmed via `read_network_requests`: the JS-dispatched click produced zero `POST /api/announcement` calls (no error, no loading state, page just sat there). A real `computer` coordinate click on the same button immediately fired the POST (and surfaced the 422). Always use a real coordinate click for this button.

### Scope investigation (inconclusive)

Attempted to determine whether this is specific to the "QA testing" entity or affects the whole QA environment by testing a second entity ("QA testing02"). This was hampered by a **known entity-redirect quirk**: clicking a company row on `appqa.visipoint.me/dashboard` does not reliably land on the clicked entity — it landed on "QA testing02" once when "QA testing" was clicked, and vice versa. This matches the same quirk previously documented for `visipoint.uk` (2026-08-01 session, "UK (TESTING02)" instead of "UK (Testing)"). A clean, verified same-session comparison against a second entity was not achieved in the time available. **Recommend a dedicated follow-up session** that explicitly verifies the sidebar tenant name after every entity click before testing, to properly scope this bug.

### Relation to CL-17855

The error shape — a cryptic 422 whose message references something not present in the visible form — matches CL-17855 (Survey submit, investigated earlier the same overall testing arc). Not confirmed as a shared root cause, but worth a shared-backend-cause check by the dev team.

---

## Part 4 — What could not be tested this session

Edit Announcement, Delete Announcement, and all row-Actions (eye/trash icons) could not be exercised — there was never a successfully-created announcement to act on, since every Add attempt failed with the 422 above. These are carried over to the next Announcements session, ideally once BUG-ANN-001 / CL-17913 is resolved or a working entity is identified.

---

## Tracking updates made this session

- `Announcements_Test_Experience.md` — appended full "Session Update — 2026-08-06" section.
- `Jira_Visipoint_Knowledge_Synthesis.md` — Section 4 bug table entry updated (was "found 2026-08-04, needs checking" → now "re-confirmed, filed as CL-17913"); Section 7 Announcements row updated.
- `visipoint-module-testing` skill — Announcements section updated with BUG-ANN-001, the field validation map, and the automation gotchas.
- `visipoint_known_bugs.md` (memory) — new paragraph added for BUG-ANN-001 and the QA entity-redirect quirk.
- `MEMORY.md` index line updated.
- Jira: created **CL-17913** (Sub-bug of CL-17095, assignee Moataz Khaled, priority High) per explicit user request.
