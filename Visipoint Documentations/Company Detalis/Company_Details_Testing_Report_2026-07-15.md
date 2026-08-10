# Company Details — Smoke Test & API Performance Report
**Date:** 2026-07-15
**Tester:** Claude (automated browser testing via Chrome MCP)
**Page URL:** `https://visipoint.uk/company`
**Environment:** UK (Testing) company / grinta1911.visipoint.uk
**Reference:** [[project_jira_overview]] (CL-6902 Company Details, KI-7281 Company Details), [[project_knowledge_synthesis]], [[project_company_details_experience]]

---

## 1. Smoke Test Summary

| # | Area | Result |
|---|------|--------|
| 1 | Page load — Info Grid, API Integration section | ✅ Pass — matches documented structure |
| 2 | Edit Company modal opens | ✅ Pass |
| 3 | Phone "Change" → editable input with country selector | ✅ Pass |
| 4 | Phone validation — invalid input ("abc123") blocks Save | ✅ Pass — inline error "Phone number should contain only digits." shown, save blocked |
| 5 | X close discards unsaved changes | ✅ Pass |
| 6 | Temperature Unit toggle °C → °F → Save | ✅ Pass — persisted, page reflects °F |
| 7 | Temperature Unit toggle °F → °C → Save (revert) | ✅ Pass — reverted to original °C |
| 8 | Two-Step Authentication toggle ON | ⚠️ New required field appears (see Finding 1) — reverted OFF, not saved |
| 9 | Token eye icon toggle (mask ↔ reveal) | ✅ Pass |
| 10 | Delete Token → confirmation modal → Cancel | ✅ Pass — modal text matches docs, cancel discards, token untouched |

**Overall: No new bugs found. Previously-fixed Bug 2 re-confirmed fixed (and improved — see Finding 2). All tested state changes were reversible; company data restored to original values (+200100639489, °C) at end of session.**

### Finding 1 — New required field on Two-Step Authentication enable (not a bug)
Enabling the "Two-Step Authentication" toggle in the Edit Company modal now reveals a required **"User Role"** dropdown that wasn't present in the 2026-06-18 documentation. The **SAVE CHANGES** button is correctly `disabled` until a role is selected — confirmed via `button.disabled === true`, consistent with [[project_form_validation_logic]] (disabled button = intended validation, not a bug). Not saved (toggled back off, closed via X) since this affects company-wide authentication/access settings. **Recommendation:** update the experience file to document this new field; a future session with explicit sign-off could test the full save path with a real user role.

### Finding 2 — Bug 2 fix improved: invalid phone no longer hits the API (positive)
Previously (2026-06-18) submitting an invalid phone number triggered a real API call before the fix. As of this session, submitting "abc123" produces the inline error **without any network request** (verified via `performance.getEntriesByType('resource')` — zero entries in the 15s window after the blocked save attempt). Validation is now fully client-side, saving an unnecessary backend round-trip. No action needed — noted for the experience file.

### Minor data drift (not a bug)
"Number of kiosks" now shows **6**, vs. **5** documented on 2026-06-18. Expected environment drift in a shared test company, not a defect.

---

## 2. API Performance — Company Details Module

Measured via the browser's Resource Timing API (`performance.getEntriesByType('resource')`) during real user-flow interactions (page loads, Edit Company saves). API host: `api.visipoint.uk`.

| Endpoint | Trigger | Samples | Min (ms) | Avg (ms) | Max (ms) |
|----------|---------|:-------:|:--------:|:--------:|:--------:|
| `GET /api/get-entity/{entityId}` | Page load (Company Details fetch) | 7 | 148 | 319 | 480 |
| `GET /api/check_active_sessions/{entityId}` | Page load (session check) | 2 | 247 | 655 | 1062 |
| `PUT/PATCH /api/entity/{entityId}` | Edit Company → Save Changes | 2 | 416 | 695 | 974 |

**Raw samples:**
- `get-entity`: 294, 392, 399, 148, 480, 243, 277 ms
- `check_active_sessions`: 1062, 247 ms
- `entity` (save): 974, 416 ms

**Observations:**
- `get-entity` (the core Company Details data fetch) is consistently sub-500ms — acceptable for an admin settings page.
- `check_active_sessions` shows high variance (247ms vs 1062ms) on only 2 samples — the 1062ms reading was the very first page load of the session (cold start / connection setup); worth re-sampling in a dedicated load-test session before drawing conclusions.
- Save (`PUT/PATCH /api/entity/{id}`) averages under 1s including the client waiting for a fresh `get-entity` re-fetch afterward to refresh the displayed values — no red flags, but only 2 samples were gathered since each save mutates real (if reversible) company data.
- Invalid-input save attempts (bad phone number) now produce **zero** API calls — a performance improvement over the previously-documented behavior.

**Scope note:** This is response-time sampling from real interactions in a shared browser session, not a dedicated load/stress test (no concurrent request volume was simulated — that would require a tool like k6/JMeter/Postman Runner, none of which are wired into this environment). Numbers reflect single-user, single-request latency only.

---

## 3. What Was Still Not Fully Tested (carried over + new)
- **Remove logo** — not clicked (irreversible without re-upload)
- **Delete Token → Confirm** — opened and cancelled only (would destroy the live API token)
- **Country code change → Save** — not tested with an actual persisted change
- **Two-Step Authentication → Save with a real User Role selected** — new gap (Finding 1); needs explicit user sign-off before testing since it's an access-control change
- **Concurrent/load testing of the 3 endpoints above** — needs a dedicated performance-testing tool, out of scope for this browser-driven session
