# Announcements Grid — Bug Report

**Page:** `https://visipoint.uk/announcements`  
**Tested By:** Claude (automated browser testing via Chrome MCP)  
**Date:** 2026-06-19  
**Status:** Pending review

---

## Bug Summary Table

| # | Bug Title | Severity | Steps to Reproduce | Expected Result | Actual Result |
|---|-----------|----------|--------------------|-----------------|---------------|
| 1 | Eye icon: Announcement Preview modal is invisible after clicking | Critical | 1. Go to `https://visipoint.uk/announcements` <br> 2. Scroll the grid right to reveal the Actions column <br> 3. Click the eye (👁) icon on any announcement row | A modal titled "Announcement Preview" appears showing the announcement title, tags, body text, publish time, expiry time, and creator info | The page backdrop darkens slightly but no modal content is visible. The modal exists in the DOM (`document.querySelector('.modal')` returns an element) but lacks the CSS `show` class, keeping it at `opacity: 0` |
| 2 | Trash icon: Delete Announcement confirmation modal is invisible after clicking | Critical | 1. Go to `https://visipoint.uk/announcements` <br> 2. Scroll the grid right to reveal the Actions column <br> 3. Click the trash (🗑) icon on any announcement row | A confirmation modal appears with the title "Delete Announcement", the message 'Are you sure you want to "Delete" this Announcement?', and Cancel / Delete buttons | The page backdrop darkens slightly but no modal content is visible. The modal is in the DOM but missing the CSS `show` class, so it remains invisible (`opacity: 0`) |
| 3 | "Clear filters" button does not clear active column search inputs | Major | 1. Go to `https://visipoint.uk/announcements` <br> 2. Type any text in the Title search box (e.g. "Test") <br> 3. Confirm the grid filters to matching rows and "Clear filters" button appears <br> 4. Click the "Clear filters" button | All active search inputs are cleared and the grid returns to showing all rows; the "Clear filters" button disappears | The Title search box still contains the typed text; the grid remains filtered to the typed value; the "Clear filters" button stays visible |
| 4 | "Clear filters" button does not disappear after all filters are cleared | Minor | 1. Go to `https://visipoint.uk/announcements` <br> 2. Click any column header to apply a sort (e.g. click "Title" — an ↑ sort indicator appears) <br> 3. The "Clear filters" button appears in the toolbar <br> 4. Click "Clear filters" — the sort is removed from the grid <br> 5. Observe the toolbar | Once all active filters and sorts are cleared, the "Clear filters" button should disappear from the toolbar | The "Clear filters" button remains visible in the toolbar even though no filters or sorts are active |

---

## Detailed Bug Descriptions

### Bug 1 — Eye icon: Announcement Preview modal invisible

**Severity:** Critical  
**Location:** Announcements grid → Actions column → Eye icon

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/announcements`
2. Scroll the grid right until the Actions column is visible
3. Click the eye (👁) icon on any announcement row

**Expected:**  
A modal titled "Announcement Preview" appears over the page, showing:
- Announcement title (bold)
- Tags (e.g. "Urgent" in red, user type in gray)
- Announcement body text
- Publish Time, Expiry Time
- Created At timestamp and creator name
- An X button to close

**Actual:**  
The dark backdrop appears momentarily but no modal content is displayed. The modal element IS present in the DOM (`class="modal fade"`, `display: block`) but lacks the CSS `show` class required for Bootstrap's fade-in to complete — so it stays at `opacity: 0` and is invisible.

**Root Cause (Technical):**  
Bootstrap-Vue 2's BModal component renders the modal element and sets `isVisible = true`, but the Vue `<transition>` component's enter hook fails to append the `show` CSS class to the modal element. This prevents Bootstrap 4's opacity transition from running. Confirmed via: `document.querySelector('.modal').classList` shows `["modal", "fade"]` instead of `["modal", "fade", "show"]`.

---

### Bug 2 — Trash icon: Delete Announcement modal invisible

**Severity:** Critical  
**Location:** Announcements grid → Actions column → Trash icon

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/announcements`
2. Scroll the grid right until the Actions column is visible
3. Click the trash (🗑) icon on any announcement row (icon is only shown for announcements the current user has permission to delete)

**Expected:**  
A confirmation modal appears with:
- Title: "Delete Announcement"
- Body: 'Are you sure you want to **"Delete"** this Announcement?'
- **Cancel** button (dark blue) — closes modal without deleting
- **Delete** button (red outline) — permanently removes the announcement

**Actual:**  
The dark backdrop appears momentarily but no modal is visible. The modal is in the DOM (`id="deleteAnnouncementConfirmModal___BV_modal_outer_"`, `display: block`) but the `show` CSS class is never added, so the modal remains invisible.

**Root Cause (Technical):**  
Same root cause as Bug 1 — the Bootstrap-Vue `show` CSS class is not appended during the Vue transition lifecycle. Both modals share the same underlying BModal component behavior and the same failure mode.

---

### Bug 3 — "Clear filters" does not clear column search inputs

**Severity:** Major  
**Location:** Announcements grid → Toolbar → "Clear filters" button

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/announcements`
2. Type any text in the Title search box (e.g. "Test")
3. Confirm the grid filters to rows containing "Test" and the "Clear filters" button appears in the toolbar
4. Click "Clear filters"

**Expected:**  
The Title search box is cleared, the grid returns to showing all rows, and the "Clear filters" button disappears.

**Actual:**  
- The Title search box still contains "Test"
- The grid remains filtered (only showing matching rows)
- The "Clear filters" button remains visible
- Clicking "Clear filters" has no visible effect on text search inputs

---

### Bug 4 — "Clear filters" button remains visible when no filters are active

**Severity:** Minor  
**Location:** Announcements grid → Toolbar → "Clear filters" button

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/announcements`
2. Click the "Title" column header to sort ascending (↑ indicator appears)
3. The "Clear filters" button appears in the toolbar
4. Click "Clear filters"
5. Verify that the sort is removed (no ↑/↓ indicator on any column)
6. Observe the toolbar

**Expected:**  
After all filters, sorts, and searches are cleared, the "Clear filters" button disappears from the toolbar.

**Actual:**  
The "Clear filters" button remains permanently visible even though the DxDataGrid instance confirms no active sorts or filters (`instance.getCombinedFilter()` returns null, no column has `sortOrder` set).

---

## Summary

| # | Bug | Severity | Status |
|---|-----|----------|--------|
| 1 | Eye icon preview modal invisible (Bootstrap fade transition bug) | Critical | Pending review |
| 2 | Trash icon delete modal invisible (same Bootstrap fade transition bug) | Critical | Pending review |
| 3 | "Clear filters" does not clear column search inputs | Major | Pending review |
| 4 | "Clear filters" button stays visible after clearing | Minor | Pending review |
