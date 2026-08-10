# Announcements Module — Regression & Gap-Filling Report — 2026-08-01

**Tester:** Claude (automated browser testing via Chrome MCP)
**Scope:** Full regression retest of the 4 previously-filed-and-fixed bugs, plus closing the two biggest coverage gaps flagged in every prior session: a real end-to-end Add Announcement submission, and a real end-to-end Delete (both Cancel and confirmed Delete). Read `Announcements_Test_Experience.md` (updated with this session's findings) for full detail, selectors, and automation notes.

**Environment note:** Logged into a different tenant than all prior sessions — **"UK (TESTING02)"** rather than "UK (Testing)" — after the dashboard session had expired and required re-login via the Passport product. This tenant has 5 real pre-existing announcements (prior sessions only ever had 1), which was useful for this session's testing. Flag for a future session: re-verify these results still hold on "UK (Testing)" if the two tenants ever appear to diverge.

---

## Summary of Findings

| # | Finding | Status |
|---|---------|--------|
| 1 | Bootstrap modal fade transition bug (eye icon preview, trash icon delete-confirm) — previously Critical, fixed 2026-07-01 | **Re-confirmed still fixed** |
| 2 | "Clear filters" not clearing text search / not disappearing — previously fixed 2026-07-01 | **Re-confirmed still fixed** |
| 3 | Full Add Announcement submission, end-to-end | **NEW — tested for the first time, works correctly** |
| 4 | Full Delete Announcement flow (Cancel + actual Delete), end-to-end | **NEW — tested for the first time, works correctly** |
| 5 | Preview modal doesn't show a "Pinned" badge even when Pinned=Yes | Observation only, not filed as a bug — needs product input |

**No new bugs found this session.** This was a positive-coverage session: closed the two largest historical testing gaps (real Add submission, real Delete) with clean results, and re-confirmed no regression on the 4 previously-fixed bugs.

---

## Detail — Regression retest of Bugs 1–4

Both the Preview (eye icon) and Delete-confirm (trash icon) Bootstrap modals rendered fully visible on a real click, with correct content in each case (verified Preview against an Urgent+Pinned row; verified Delete-confirm text matched the correct row via DOM order, not just visual proximity). "Clear filters" correctly cleared a Title text search (5→2→5 rows) and the button correctly disappeared once no filters remained. All four originally-fixed bugs remain fixed — no regression.

One automation-specific wrinkle worth noting for future sessions: the previously-documented JS vnode-click-handler pattern for triggering the eye/trash icons stopped working in this session (produced zero effect — no modal, no error). Switched to real coordinate clicks (carefully scaled against the actual vs. screenshot viewport ratio, since screenshots are returned scaled down from the real render size) and that worked reliably. Not clear if this is a genuine regression in the app's Vue internals or purely an automation-tooling quirk — noted in the experience file for whoever hits it next.

---

## Detail — Add Announcement, full end-to-end (NEW coverage)

Filled every relevant field (Title, rich-text Announcement body, left Urgent/Pinned off, User type target defaulted to "Staff", Publish = Immediately, Expiry = Never) and clicked **Add** — something no prior session had actually done. Result: a green "Announcement created successfully." toast, redirect to the grid, and the new announcement appearing correctly at the top of the list with the right Title, User type, Active status, and a Publish Time matching the submission time. Clean pass, no defects found in this flow.

---

## Detail — Delete Announcement, full end-to-end (NEW coverage)

Prior sessions only ever tested Cancel, to avoid deleting the environment's one real announcement. This session created a disposable test announcement specifically so the actual Delete path could finally be exercised safely. Clicked the trash icon on the test row, confirmed the modal referenced the correct row (cross-checked via DOM row order, not just screen position), clicked **Delete**, and the row was removed cleanly — grid count returned to the original 5 real announcements, none of which were touched. Clean pass, no defects found in this flow.

---

## Observation (not filed as a bug)

The Announcement Preview modal shows an "Urgent" badge and a User Type badge when applicable, but never shows any indicator for "Pinned" even when the announcement's Pinned column is "Yes" in the grid. This is an asymmetry (Urgent gets a badge, Pinned doesn't) that might be intentional — Pinned may only affect list ordering/placement rather than being something worth surfacing in a preview — but it's worth a quick confirmation from product before ruling it out as a gap.

---

## Not tested this session (carried forward as gaps)

- Edit Announcement pre-fill — still no `canEdit=true` row available in this tenant either (both action columns show only eye + trash, no pencil icon).
- Actual Export download (Excel/CSV/PDF) — dropdown confirmed working, but did not trigger a real file download per the file-download permission policy.
- Image/Video insert buttons in the rich text editor — likely a native OS file picker, still not exercised.
- True pagination behavior with 50+ rows — only 5 real rows exist in this tenant, not enough to page past the default "50" page size.
