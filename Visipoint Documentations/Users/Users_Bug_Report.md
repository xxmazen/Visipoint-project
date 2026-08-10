# Users Grid — Bug Report

**Page:** `https://visipoint.uk/users`  
**Tested By:** Claude (automated browser testing)  
**Date:** 2026-06-18  
**Status:** Pending logic review — some items may reflect expected behavior

---

## Bug 1 — Grid shows "-1 items" during refresh

**Severity:** Minor  
**Location:** Pagination bar

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/users`
2. On any user row, click the **Select Actions** dropdown
3. Click **Archive**
4. In the confirmation modal, click **Archive**
5. Watch the pagination counter at the bottom right immediately after confirmation

**Expected:** Counter updates to the new total (e.g., 454 items)  
**Actual:** Counter briefly displays **"-1 items"** for 1–2 seconds before correcting itself

---

## Bug 2 — Delete modal: Username missing from confirmation

**Severity:** Major  
**Location:** Delete User modal

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/users`
2. On any user row (e.g., "John Smith"), click the **Select Actions** dropdown
3. Click **Delete**
4. Observe the confirmation text in the modal body

**Expected:** Modal reads: *"Are you sure you want to 'Delete' **John Smith**?"*  
**Actual:** Modal reads: *"Are you sure you want to 'Delete' **?**"* — username is completely blank

---

## Bug 3 — Delete modal: Loading spinner never resolves

**Severity:** Major  
**Location:** Delete User modal

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/users`
2. On any user row, click **Select Actions** → **Delete**
3. Wait for 30+ seconds after the modal opens

**Expected:** Modal body loads user details with Cancel and Delete buttons ready to use  
**Actual:** A loading spinner appears in the modal body and never completes — content never renders, buttons are inaccessible

---

## Bug 4 — Delete modal: Cannot be dismissed while spinner is active

**Severity:** Major  
**Location:** Delete User modal

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/users`
2. On any user row, click **Select Actions** → **Delete**
3. While the spinner is showing, try each of the following:
   - Click the **Cancel** button
   - Press the **Escape** key
   - Click anywhere **outside** the modal

**Expected:** Modal closes on any of those actions  
**Actual:** None of the three methods dismiss the modal — user is completely stuck inside it

---

## Bug 5 — Sign in/out dialog: Back button non-functional on Step 2

**Severity:** Minor  
**Location:** Sign in/out multi-step dialog

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/users`
2. On any user row, click **Select Actions** → **Sign in/out**
3. On **Step 1**: Select a Site and an Area, then click **Next**
4. On **Step 2** (Print badge / Check in screen): Click the **Back** button

**Expected:** Returns to Step 1 where Site and Area can be changed  
**Actual:** Nothing happens — the dialog remains on Step 2 with no navigation back

---

## Bug 6 — Change User Type modal: No validation feedback on empty submit

**Severity:** Minor  
**Location:** Change User Type modal (mass action)

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/users`
2. Select one or more users using the row checkboxes
3. In the mass action toolbar, click **Change User Type**
4. Leave the **User Type** dropdown empty (do not select anything)
5. Click the **Change** button

**Expected:** A validation error message appears (e.g., "Please select a user type") or the button is clearly disabled  
**Actual:** The modal stays open with no message, no highlight on the empty field, and no feedback of any kind — silent failure

---

## Bug 7 — Add Visit Permit modal: No validation feedback on empty submit

**Severity:** Minor  
**Location:** Add Visit Permit modal (mass action)

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/users`
2. Select one or more users using the row checkboxes
3. In the mass action toolbar, click **Add Visit Permit**
4. Leave the **Visit Permits** dropdown empty (do not select anything)
5. Click the **Add** button

**Expected:** A validation error message appears (e.g., "Please select a visit permit") or the button is clearly disabled  
**Actual:** The modal stays open with no message, no highlight on the empty field, and no feedback of any kind — silent failure

---

## Bug 8 — Clear filters button does not clear active column search filters

**Severity:** Major  
**Location:** Grid toolbar — Clear filters button

**Steps to Reproduce:**
1. Go to `https://visipoint.uk/users`
2. In the **First Name** column, click the **Search** box and type any value (e.g., "Ali")
3. Confirm the grid filters down (e.g., from 455 → 8 items)
4. Click the **Clear filters** button (red outline, top left of the grid)

**Expected:** The search text is removed and the grid resets to show all 455 users  
**Actual:** The search text remains in the field, the grid still shows 8 items — the filter is not cleared

---

## Summary

| # | Bug | Severity | Status |
|---|-----|----------|--------|
| 1 | Grid shows "-1 items" during refresh | Minor | Pending review |
| 2 | Delete modal: Username missing | Major | Pending review |
| 3 | Delete modal: Spinner never resolves | Major | Pending review |
| 4 | Delete modal: Cannot dismiss during loading | Major | Pending review |
| 5 | Sign in/out: Back button non-functional on Step 2 | Minor | Pending review |
| 6 | Change User Type: No validation feedback | Minor | Pending review |
| 7 | Add Visit Permit: No validation feedback | Minor | Pending review |
| 8 | Clear filters: Does not clear column search filters | Major | Pending review |
