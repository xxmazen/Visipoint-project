# Add Visits Module — First-Ever Test Report — 2026-07-31

**Tester:** Claude (automated browser testing via Chrome MCP)
**Scope:** First-ever full smoke test of the Add Visits module (`https://visipoint.uk/add-expected-visitors`). No prior session/history to retest against. Full detail, selectors, and automation gotchas recorded in `Add_Visits_Test_Experience.md` (read that alongside this report).

---

## Summary of Findings

| # | Finding | Severity | Status |
|---|---------|----------|--------|
| 1 | New-visitor "create new" auto-fill truncates First Name to 8 characters and never populates Last Name, silently discarding the rest of the typed search text | **Medium** | **New bug — BUG-AV-001** |
| 2 | A second "Add Visit" confirmation modal briefly reappears with corrupted/blank Area text right after confirming a successful submission | Minor (cosmetic only, no data impact) | New — confirmed no functional/data consequence |
| 3 | Duplicate active-visit detection ("An expected visit already exists...") | — | Working correctly, not a bug |
| 4 | Stale-time validation ("Visit time can't be before now") | — | Working correctly, not a bug — investigated and ruled out a timezone bug |
| 5 | Empty-submit silent failure | — | Re-confirmed, already-documented by-design pattern |
| 6 | CL-8426 (no Expected visit in a remote area) | — | **Not testable** — no geofenced/remote Area exists in this tenant |

---

## Detail — BUG-AV-001: New visitor First Name/Last Name auto-fill data loss

**Steps:**
1. Add Visits page → select Site "UK (Testing)" + Area "default area"
2. In Visitor Details search box, type a name that matches no existing user
3. Observe the "create new visitor" mini-form that expands (First Name, Last Name, User Type, Email, Phone)

**Test A — single word, 20 characters:** typed `QATestNewVisitor123` → First Name field populated with `QATestNe` (first 8 characters only); remaining 12 characters silently lost.

**Test B — two words:** typed `Jonathan Smith` → First Name = `Jonathan` (8 characters — coincidentally exactly at the cutoff, so this case alone looks correct); **Last Name stayed empty** — "Smith" discarded entirely, not split into Last Name.

**Root cause confirmed:** the First Name input's real HTML `maxLength` is 20 (verified by typing directly into the field, which accepted a full 20-character string correctly). The 8-character cutoff only happens during the search-box → First-Name auto-copy, meaning it's a JS logic bug (likely a hardcoded slice/substring limit that doesn't match the field's actual capacity), not an input-level restriction. No attempt is made to split the typed text on whitespace into First/Last Name.

**Impact:** Anyone creating a new visitor by typing their name in the search box (a very natural, likely-common flow given the CL-34 description "check if user exists... or create new") gets silently truncated/incomplete data pre-filled, with no indication anything was cut. If not manually caught and corrected before submit, this creates visitor records with wrong/incomplete names.

**Recommendation:** File against CL-34, Medium severity. Not yet in Jira as of this session.

---

## Detail — Corrupted second confirmation modal (cosmetic, verified data-safe)

Full narrative in the experience file. Short version: confirming a valid Add Visit sometimes triggers a **second** confirmation modal immediately after, with a blank Area in its text, while the form has already reset behind it (as if the first submit had already completed). Clicking Cancel on this second modal was verified **safe** — a follow-up duplicate-add attempt on the same visitor/date correctly returned "An expected visit already exists, try a different date," proving the original visit was created exactly once and nothing was lost or duplicated. Classified as Minor/cosmetic — recommend filing at low priority since there's no functional or data consequence, just a confusing extra modal.

---

## Detail — Business-logic checks confirmed correct

- **Duplicate visit on the same date is blocked** with a clear message, tested by re-submitting the identical visitor/date combination.
- **Past-time validation is correct**, not a Moscow/Cairo timezone bug — the browser's real system timezone is `Africa/Cairo`, which shares summer UTC offset with the "(Europe/Moscow)" label shown in the UI; the picker was simply catching genuinely-elapsed real time between opening it and confirming.
- **Same-day conflict with an already-checked-in visitor is blocked** — hit this against a visitor left "Checked in" from an earlier Emergency Sessions test session; correctly rejected rather than allowing an overlapping Expected visit.
- **Empty-submit silent failure** re-confirmed as the already-documented platform-wide by-design pattern — not re-filed.

---

## Not testable in this environment

**CL-8426** ("shouldn't be able to add an Expected visit in any remote area") — the UK (Testing) tenant only has two Areas, both with Geofencing **OFF** (confirmed via `/sites`). There is no "remote"/geofenced Area configured to actually exercise this rule against. Recommend either enabling Geofencing on a test Area in a future session (with sign-off, since it changes shared tenant config) or getting a dedicated environment for this check.

---

## Cosmetic oddity, not filed

The Visit Time picker's "(Europe/Moscow) time zone" label on a "UK (Testing)" entity looks like a configuration mismatch, though it produced no functional issue in this session (Cairo and Moscow share the same summer UTC offset). Worth a product/config sanity check, not a functional bug report.

---

## Test data left in place

Mazen Mohamed (existing "Visitor" type user) now has a real Expected Visit on record for 01 August 2026, 09:26 PM, UK (Testing) - default area, created during this session. Left in place — harmless, no cleanup needed.
