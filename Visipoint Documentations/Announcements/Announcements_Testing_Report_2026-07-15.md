# Announcements Module — Smoke Test, Field/Button Walkthrough & API Performance
**Date:** 2026-07-15
**Tester:** Claude (automated browser testing via Chrome MCP)
**Page URL:** `https://visipoint.uk/announcements`
**Environment:** UK (Testing) company / grinta1911.visipoint.uk
**Reference:** [[project_jira_overview]] (CL-7436 Announcements), [[project_knowledge_synthesis]], [[project_announcements_experience]]
**Scope:** Every field, button, dropdown, and modal reachable from the Announcements grid and the Add Announcement page was opened and interacted with; screenshots captured at each step. No test announcement was created or deleted — see "What Was Not Submitted."

---

## 1. Grid Core Interactions

| # | Area | Result |
|---|------|--------|
| 1 | Page load — 1 announcement present, all 14 columns visible on horizontal scroll | ✅ Pass |
| 2 | Title column search ("Test") | ✅ Pass — filters correctly, "Clear filters" button appears |
| 3 | Clear Filters (text search) | ✅ Pass — **Bug 3 fix re-confirmed**: search box cleared and grid unfiltered |
| 4 | Clear Filters button disappears after clearing | ✅ Pass — **Bug 4 fix re-confirmed**: button vanished correctly |
| 5 | Status header filter (≡ icon) | ✅ Pass — matches docs (Active option) |
| 6 | Verbal header filter (≡ icon) — previously untested | ✅ Pass — opens correctly, shows the single "-" unique value present in data |
| 7 | Column sort (Title, ascending) | ✅ Pass — sort arrow appears, "Clear filters" reappears (sort counts as an active filter) |
| 8 | Columns → Column Chooser | ✅ Pass — matches docs, all 14 columns listed with checkboxes |
| 9 | Export dropdown (Excel/CSV/PDF) | ✅ Pass — options match docs. Not clicked (file download needs explicit go-ahead) |
| 10 | Pagination (page size 10) | ✅ Pass — no errors with single-item grid |

**No new bugs in grid interactions.** All 4 previously-filed bugs (Bootstrap modal invisibility x2, Clear Filters not clearing search, Clear Filters button not disappearing) remain fixed as of this session — see Section 3 for modal re-verification.

---

## 2. Add Announcement Form — All Fields

Confirmed as a **full page** (`/add-announcement`), not a modal, as previously documented. All fields filled and screenshotted; form was **not submitted** — no test announcement was created.

| Field | Result |
|-------|--------|
| Title | ✅ Pass |
| Announcement body (rich text editor) | ✅ Pass — typed body text |
| Bold / Italic / Underline | ✅ Pass — all three toggle correctly on selected text |
| Text color picker | ✅ Pass — full color palette opens, color applies |
| Alignment dropdown | ✅ Pass — Left/Center/Right/Justify options, applies correctly |
| Link tool | ✅ Pass — inline "Enter link:" prompt appears on the link button; closed via Escape without saving a link |
| Image / Video buttons | Present, not clicked — likely triggers a native OS file picker, which isn't safely testable/reversible in this session |
| URL (optional) | ✅ Pass |
| Urgent announcement toggle | ✅ Pass |
| Pinned announcement toggle | ✅ Pass |
| Announcement target: User type / Area radio | ✅ Pass — switching to "Area" reveals an Area dropdown (expandable site/area tree) **and a new "Verbal announcement" checkbox not previously documented** — this explains the grid's "Verbal" column |
| User type tag-select (pre-filled "Staff") | ✅ Pass |
| Area dropdown | ✅ Pass — expandable tree, "UK (Testing)" site node shown |
| Verbal announcement checkbox | ✅ Pass |
| Announcement publish: Immediately / Custom date and time | ✅ Pass — switching to Custom reveals Publish date + Publish time inputs |
| Publish date picker | ✅ Pass — calendar widget, correctly highlights today (15 Jul 2026) |
| Publish time picker | ✅ Pass — hour/minute/AM-PM scrollable columns |
| Announcement expiry: Never / Custom date and time | ✅ Pass — same date/time picker pattern as Publish |

**New finding (not a bug):** the "Verbal announcement" checkbox only appears when Announcement target = **Area** (it's hidden under the User type target). This is presumably intentional (verbal announcements are read aloud at a physical area/kiosk, not meaningful for a user-type-targeted announcement) but is worth flagging to the product team if not already known, since it wasn't documented before.

---

## 3. Row Actions — Eye (View) and Delete

Only 1 announcement exists in this environment; it has no visible Edit (pencil) icon, consistent with the documented `canEdit` permission flag being false for this row.

| Action | Result |
|--------|--------|
| Eye icon → Announcement Preview modal | ✅ Pass — **Bugs 1 fix re-confirmed**: modal renders fully visible via a plain native click, no JS workaround needed, no CDP freeze. Content shown correctly (title, Urgent/Staff badges, body, publish/expiry/created-at). |
| Trash icon → Delete Announcement modal | ✅ Pass — **Bug 2 fix re-confirmed**: modal renders fully visible via native click. Cancelled (this is the only announcement in the environment — deleting it would remove test data). |

The Bootstrap modal fade-transition bug (previously Critical, affecting both these modals platform-wide) continues to show no signs of recurrence in this session, consistent with the 2026-07-01 regression retest finding it fixed platform-wide.

---

## 4. API Performance — Announcements Module

Measured via the browser's Resource Timing API during real page loads. API host: `api.visipoint.uk`.

| Endpoint | Trigger | Samples | Min (ms) | Avg (ms) | Max (ms) |
|----------|---------|:-------:|:--------:|:--------:|:--------:|
| `GET /api/announcements/{...}` | Page load (grid data) | 3 | 570 | 741 | 999 |
| `GET /api/user-types/{...}` | Page load | 3 | 332 | 422 | 598 |
| `GET /api/check_active_sessions/{...}` | Page load | 3 | 272 | 393 | 537 |
| `GET /api/adminPreference/{...}` | Page load | 3 | 237 | 385 | 529 |

**Raw samples:**
- `announcements`: 999, 654, 570 ms
- `user-types`: 598, 332, 337 ms
- `check_active_sessions`: 537, 371, 272 ms
- `adminPreference`: 529, 390, 237 ms

No privacy-redacted ("[BLOCKED]") entries appeared this session — all announcement-page API calls resolved with plain, readable URLs this time, unlike the Users module's filtered list endpoint.

**Scope note:** Single-user, single-request latency sampling from real interactions — not a concurrent load/stress test. No load-testing tool is wired into this environment.

---

## 5. What Was Not Submitted (by design, this session)

- Add Announcement form — filled completely, **not submitted** (no test announcement created)
- Image / Video insert buttons in the rich text editor — not clicked (likely triggers a native file picker)
- Export — Excel/CSV/PDF — options confirmed, **no file downloaded**
- Delete Announcement — confirmation modal opened and cancelled (only announcement in the environment)
- Edit Announcement — no editable row was available this session (no `canEdit` row present) to retest the pre-fill behavior
