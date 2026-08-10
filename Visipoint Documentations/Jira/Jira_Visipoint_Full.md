# Visipoint Jira -- Complete Epic and Story Map
**Generated:** 2026-06-21
**Boards:** CL (Cloud) | KI (Kiosk)
**Coverage:** 32 CL epics with 676 stories | 23 KI epics with 371 stories
**Note:** Story type issues only (Bugs, Tasks, Sub-tasks excluded)

---

## Expected Warnings to Ignore During Testing

The following console warnings appear on **every page load** at `https://visipoint.uk`. They are known, infrastructure-level issues and must **NOT** be reported as bugs.

| Warning | Source |
|---------|--------|
| `W0019 - DevExtreme: Unable to Locate a Valid License Key.` | `https://visipoint.uk/widgets/react-survey-widget.iife.js:175` |
| `Error: <path> attribute d: Expected number, "… 2.27861 12.4233C2.16267 12.3648…"` | `https://visipoint.uk/js/chunk-vendors.a2e1fdb1.js:78` |

> **Note:** The DevExtreme warning is a missing license key for the widget library. The SVG path error is a malformed path in a bundled vendor file. Both are consistent across all pages and sessions — filter them out when reviewing console output.

---

## CL Board -- Cloud (32 Epics)

### [CL-26] Cloud Passport Configuration
**Description:** Create Cloud Passport for Account for different Entities globally distributed. Single passport identity across multiple entities.

| Key | Status | Summary |
|-----|--------|---------|
| IN-67 | Done | Validate unique email & phone in passport registration - BE only |
| IN-60 | Done | Add register page to passport |
| IN-59 | Done | Review create company from passport and check close modal state |
| IN-72 | Done | As a system user, when I login to the passport dashboard for the first time, the update password popup should appear aut... |
| IN-81 | Done | Check registration form for subdomain validation - FE |
| IN-80 | Done | Get a new valid token if current token expired |
| IN-79 | Done | Don't show change temporary password modal if user closed it |
| CL-22 | Done | Edit user Data for certain Entity |
| CL-23 | Done | Edit user Data on passport |
| CL-24 | Done | Deleting User from Entity and deleting his scans |
| CL-25 | Done | Passport DB migration from Kiosk DB and Web DB |
| CL-71 | Done | Move & handle registration from cloud to passport |
| CL-72 | Done | Set user password the same as sent in invitation email |
| CL-73 | Done | Fix create company |
| CL-74 | Done | Update user password in the cloud if changed from Passport |
| CL-80 | Done | Create Company modal >> change the modal title |
| CL-81 | Done | Check that phone number and email are not duplicated in the passport |
| CL-85 | Done | Check the company subdomain validation on blur too |

**Total: 18 stories**

---

### [CL-34] Add Visits
**Description:** Add expected users to an entity (admin or staff). Check if user exists, add as expected visitor or create new.

| Key | Status | Summary |
|-----|--------|---------|
| CL-938 | Done | As a system admin, I should be able to add an expected visit for a new user/an already existing one |
| CL-951 | To Do | As a system admin, I should be able to add multiple visits for multiple users |
| CL-1165 | Done | As a user, I should receive an email/SMS after being added as an expected visitor by the admin  |
| CL-3736 | Done | [Passport]: As a user, I should be able to confirm my "Expected" visit invitation if it's added by the admin |
| CL-4223 | Done | As a system, I should check whether the user has a Passport or not (on the run time) when he clicks on the confirmation ... |
| CL-4226 | Done | If the user entered email address/phone number (already has a Passport) in the form while confirming his visit invitatio... |
| CL-12250 | To Do | [Add Visits]: As a system user, I should be able to set a time to automatically archive any new user |
| CL-12746 | To Do | UI/UX Enhancements for "Add Visits" page |
| CL-12750 | To Do | As a system, I should ask users to add a profile photo based on user type when confirming their "Expected" visit |
| CL-13216 | To Do | Enhance logic of adding an "Expected" visit when another visit exists |
| CL-13549 | To Do | [Print Badge]: As a system user, I should be able to print a badge when adding a visit for a user |

**Total: 11 stories**

---

### [CL-47] Compliance
**Description:** Compliance module supporting Questionnaire, Agreement, and Document-Vaccine/PCR types attached to journey flows.

| Key | Status | Summary |
|-----|--------|---------|
| CL-46 | Done | [Vaccin/PCR]: As a system user, I should be able to add the vaccine as a compliance in the journey flow |
| CL-48 | Done | [Vaccin/PCR]: As a system user, I should be able to add vaccine as a document in the compliance |
| CL-55 | Done | [Vaccin/PCR]: As a system user, I should be able to view the document (Compliance) in the Users List |
| CL-56 | Done | [Vaccin/PCR]: As a system user, I should be able to view the document (Compliance) in the Dashboard and Reports page |
| CL-61 | Done | [Vaccin/PCR]: As a system user, I should be able to control the column visibility for each document separately |
| CL-62 | Done | [Vaccin/PCR]: As a system user, I should be able to disable/enable any document binded to the user profile |
| CL-63 | Done | [Vaccin/PCR]: As a system user, I should be able to delete any document binded to the user profile |
| CL-64 | Done | [Vaccin/PCR]: As a system user, I should be able to add documents (binded to user profile only) to the user profile |
| CL-163 | Done | [Vaccin/PCR]: As a system user, I should be able to select or add Vaccine/PCR file while checking in user from the dashb... |
| CL-582 | Done | [Create/Edit Compliance]: The following fields shouldn't exceed this number of characters |
| CL-792 | To Do | [Edit Flow]: How will changing the compliance/compliance frequency in the user's flow affect his 'Expected' scans?   |
| CL-4334 | To Do | [Edit Flow]: If the system admin changes the compliance in the user's flow and there is a 'Visit rejected' scan for him |
| CL-7268 | To Do | [Enterprise]: As a system user, I should be able to add a new category |
| CL-7271 | To Do | [Enterprise]: As a system user, I should be able to add a new 'Simple' compliance |
| CL-7272 | To Do | [Enterprise]: As a system user, I should be able to add a new 'Advanced' agreement |
| CL-7273 | To Do | [Enterprise]: As a system user, I should be able to add a new 'Advanced' questionnaire |
| CL-7274 | To Do | [Enterprise]: As a system user, I should be able to add a new 'Advanced' document |
| CL-7275 | To Do | [Enterprise]: As a system user, I should be able to preview the 'Advanced' agreement before adding it |
| CL-7300 | To Do | [Enterprise]: As a system user, I should be able to preview the 'Advanced' questionnaire before adding it |
| CL-7403 | Done | As a system user, I should be able to add up to 15 questions in any questionnaire |
| CL-7528 | Done | [Daily Log & History]: As a system user, I should be able to view the compliance 'Details' in any 'Denied' scan in the f... |
| CL-7529 | To Do | [Enterprise]: As a system user, I should be able to view all categories and compliance in my company |
| CL-7531 | To Do | [Lite & Professional]: As a system user, I should be able to view all compliance in my company |
| CL-7534 | To Do | [Lite & Professional]: As a system user, I should be able to add a new 'Simple' agreement and questionnaire |
| CL-7539 | Done | As a system user, I should be able to fill in compliance only based on its frequency as set in the flow page |
| CL-7578 | To Do | [Lite & Professional]: As a system user, I should be able to add the compliance to specific flows during its creation |
| CL-7602 | To Do | [Enterprise]: As a system user, I should be able to preview the 'Advanced' document before adding it |
| CL-7605 | To Do | [Enterprise]: As a system user, I should be able to add the compliance to specific flows during its creation |
| CL-7615 | To Do | [Edit Flow]: As a system, I should automatically add the compliance to the flows that were selected during its creation |
| CL-7645 | To Do | [Enterprise][Daily Log & History]: What should happen, if the user will fill in his compliance online |
| CL-7675 | To Do | [Enterprise][Daily Log & History]: How will the 'Pending' scan be updated according to the user's behavior? |
| CL-7717 | To Do | [Enterprise]: What should happen, if 'Visible if' is clicked in the 'Advanced' questionnaire or document? |
| CL-7718 | To Do | As a system user, I should be able to edit any 'Simple' or 'Advanced' compliance |
| CL-7719 | To Do | As a system user, I should be able to view the history of any 'Simple' or 'Advanced' compliance |
| CL-7821 | To Do | 'Simple' compliance enhancements in the 'Sign in / Sign out' modal |
| CL-7824 | To Do | [Enterprise]: How will the 'Advanced' agreement look like in the 'Sign in / Sign out' modal? |
| CL-7851 | To Do | [Enterprise]: How will the 'Advanced' questionnaire look like in the 'Sign in / Sign out' modal? |
| CL-7852 | To Do | [Enterprise]: How will the 'Advanced' document look like in the 'Sign in / Sign out' modal? |
| CL-7903 | To Do | Compliance enhancements in the 'Fill in Compliance' and 'Manage Visit' modals |
| CL-7917 | To Do | As a user, I should be able to fill in the compliance online if I have an 'Expected' or 'Pending' scan |
| CL-7922 | To Do | [Enterprise]: How will the 'Advanced' compliance look to the user when filling it online? |
| CL-7958 | To Do | As a system, I should display compliance in different screens according to their priorities  |
| CL-8021 | To Do | [Enterprise][Daily Log & History]: How will the 'Pending' scan be updated when the system user signs the user in? |
| CL-8024 | To Do | [Enterprise][Daily Log & History]: How will the 'Pending' scan be updated with changing the user's type? |
| CL-8072 | To Do | How will updating/deleting any compliance/compliance category affect the old scans? |
| CL-8073 | To Do | How will updating any compliance affect the 'Expected' scans? |
| CL-8083 | To Do | [Enterprise]: How will updating/deleting the compliance category affect the 'Expected' and 'Pending' scans? |
| CL-8084 | To Do | [Enterprise]: How will updating any compliance affect the 'Pending' scans? |
| CL-8157 | To Do | How will deleting any compliance affect the 'Expected' scans? |
| CL-8167 | To Do | [Enterprise][Edit Flow]: How will changing the compliance/compliance frequency in the user's flow affect his 'Pending' s... |
| CL-8243 | To Do | [Enterprise]: How will deleting any compliance affect the 'Pending' scans? |
| CL-8343 | To Do | As a system user, I should be able to view all the answers submitted by different users for any compliance |
| CL-8405 | To Do | Database structure |
| CL-12045 | Done | [Add/Edit Compliance]: Increase 'Compliance Name' to 50 characters  |
| CL-12143 | To Do | [Lite & Professional]: As a system user, I should be able to add a new 'Simple' document - Vaccine/PCR |
| CL-12537 | To Do | As a compliance, I should be filled in according to the compliance frequency set on the flow page |
| CL-14390 | Done | [Add Compliance][Agreement]: As a system user, I should be able to upload PDF/image or add text |
| CL-14729 | Done | [Edit Compliance][Agreement]: As a system user, I should be able to upload PDF/image or add text |
| CL-15722 | To Do | [Compliance Grid][UI enhancement] Removing the view button. |

**Total: 59 stories**

---

### [CL-173] Public QuickPass
**Description:** Standalone kiosk feature: users register details and receive a QuickPass QR code valid for 7 days.

| Key | Status | Summary |
|-----|--------|---------|
| CL-174 | Done | As a system user, I should be able to add QR Code as an input method for the flow in the journey builder |
| CL-385 | To Do | [Journey Builder >> Add/Edit Flow]: As a system admin, I should be able to select which user types are allowed to sign i... |

**Total: 2 stories**

---

### [CL-205] Exploratory Testing
**Description:** Exploratory testing epic for the Cloud dashboard.

*(No stories linked via Epic Link field -- may contain Bugs/Tasks only)*

---

### [CL-222] Pre-registration
**Description:** Cloud-only feature allowing users to pre-register at any company before visiting. QR code displayed on kiosk.

| Key | Status | Summary |
|-----|--------|---------|
| CL-223 | Done | [User Settings >> User Types]: As a system admin, I should be able to control user types and assign registration method ... |
| CL-228 | Done | As a system, I should automatically generate a Pre-registration URL for each area |
| CL-230 | Done | If a user (has a passport account) filled in the Pre-registration form |
| CL-232 | Done | If a user (doesn't have a passport account and wants to have one) filled in the Pre-registration form |
| CL-233 | Done | If a user (doesn't have a passport account and doesn't want to have one) filled in the Pre-registration form |
| CL-235 | Done | As a system, I should generate a 'Local QR code' for the user who fills in the Pre-registration form and doesn't have a ... |
| CL-238 | Done | [Daily Log]: As a system, I should add an 'Expected' visit after the user submits the Pre-registration form |
| CL-239 | Done | As a system, I should add/update the user record in the 'Users List' after he submits the Pre-registration form |
| CL-241 | Done | [Daily Log]: As a system admin, I should be able to edit/delete any expected visit |
| CL-246 | Done | As a user, I should receive an email/SMS with the compliance link and the compliance reminder if I have an 'Expected' sc... |
| CL-251 | Done | [Add/Edit Flow]: As a system admin, I should be able to set when the compliance will be sent to the user who has an 'Exp... |
| CL-252 | Done | [Daily Log]: As a system admin, I should be able to send the compliance again if the user didn't pass it |
| CL-256 | Done | [Daily Log]: As a system admin, I should be able to approve the expected visit for users 'with approval required' |
| CL-464 | To Do | As a system, I should update the journey stage if the user's registration method is updated to 'Pre-registration with ap... |
| CL-469 | Done | [Add/Edit Flow]: As a system admin, I shouldn't be able to add more than one user type with (Registration not required) ... |
| CL-480 | Done | As a system, I should add an 'Expected' scan to the user's Passport account |
| CL-533 | Done | As a user, I should be able to download the Passport QR code or the Local QR code |
| CL-598 | Done | As a user, I should be able to access the Pre-registration form |
| CL-789 | Done | If a user (has a Passport account and tried to register for another one) via the Pre-registration form |
| CL-794 | Done | As a user, I should be able to fill in the compliance online before entering the expected area |
| CL-832 | Done | As a system admin, I should be able to select which user type should be added for new users who sign in using any type o... |
| CL-889 | Done | As a user, I shouldn't be able to add an expected visit on a day already has another visit on the same site |
| CL-1118 | To Do | As a system, I should delete the 'Expected' scan/visit in the following cases |
| CL-1127 | Done | [Daily Log]: As a system admin, I should be able to fill in/update the compliance for the expected visitors |
| CL-1148 | Done | [Daily Log & History]: As a system, I should add 2 actions after the user submits the Pre-registration form |
| CL-1149 | Done | [Daily Log & History]: As a system, I should add an action after the user (with approval required) fills in the complian... |
| CL-1150 | Done | As a system, I should convert the visit/scan to 'No show' if the user didn't enter the site/area |
| CL-1395 | Done | [Daily Log]: Changes in the 'Fill in Compliance', 'Manage Visit' and 'Sign in / Sign out' modals |
| CL-1396 | Done | [Daily Log]: As a system admin, I should be able to reject the expected visit for users 'with approval required' |
| CL-1698 | Done | [Daily Log & Users List]: As a system admin, I should be able to check in any active user |
| CL-1747 | To Do | [Sign in / Sign out]: As a system, I should auto sign out all the signed in default users at the midnight |
| CL-1776 | To Do | As a system, I should update the journey stage if the user's registration method is updated to 'Registration allowed' or... |
| CL-1821 | To Do | As a system, I should delete the 'Expected' visit/scan if the user's registration method is updated to 'Pre-registered b... |
| CL-2020 | Done | As a system admin, I shouldn't be able to change registration method from 'Registration allowed' to another value in thi... |
| CL-2523 | Done | As a user, I shouldn't repeat the same steps again in this case |
| CL-2624 | Done | [Daily Log]: As a system admin, I should be able to approve the rejected visit for user 'with approval required' |
| CL-2625 | Done | [Daily Log]: As a system admin, I should be able to reject the approved visit for user 'with approval required' |
| CL-3260 | Done | [Touch Mode][Add / Edit Flow]: As a system admin, I should be able to add multiple 'Registration not required' user type... |
| CL-3291 | Done | As a system, I should display compliance in the 'Sign in / Sign out', 'Fill in Compliance' and 'Manage Visit' modals acc... |
| CL-3539 | Done | As a user, I should be able to activate the camera and take a photo while filling in the 'Pre-registration' form  |
| CL-3567 | To Do | As a 'Registration allowed' user, I should be able to submit the 'Pre-registration' from without adding visit date & tim... |
| CL-4242 | To Do | [Daily Log][Mass Actions]: As a system user, I should be able to edit multiple 'Expected' visits at once |
| CL-4243 | To Do | [Daily Log][Mass Actions]: As a system user, I should be able to delete multiple 'Expected' visits at once |
| CL-4244 | To Do | [Daily Log][Mass Actions]: As a system user, I should be able to resend compliance to multiple 'Expected' visitors at on... |
| CL-4662 | To Do | [Daily Log][Mass Actions]: As a system user, I should be able to manage multiple expected visits if no compliance exists |
| CL-4665 | To Do | [Daily Log][Mass Actions]: As a system user, I should be able to manage multiple expected visits if compliance exists |
| CL-4675 | To Do | [Daily Log][Mass Actions]: As a system user, I should be able to approve multiple rejected visits |
| CL-4676 | To Do | [Daily Log][Mass Actions]: As a system user, I should be able to reject multiple approved visits |
| CL-5607 | To Do | As an archived user, I shouldn't be able to fill in the 'Pre-registration' form |
| CL-5608 | To Do | As a denied user, I shouldn't be able to fill in the 'Pre-registration' form |
| CL-7181 | To Do | As a system, I should create a new visit when signing the user in, if a 'Deleted' visit exists for him on the same day a... |
| CL-12748 | To Do | As a system, I should ask users to add a profile photo based on user type when filling the 'Pre-registration' form |
| CL-12894 | Done | As a system, I should send an email notification to system users, informing them of a user awaiting admin approval |
| CL-12973 | Done | [Daily Log]: Update conditions for 'Manage Visit' action visibility |
| CL-13194 | To Do | Enhance logic to prevent adding an 'Expected' visit via 'Pre-registration' form when another visit exists |
| CL-13195 | To Do | Conditions for adding an 'Expected' visit via 'Pre-registration' form when another visit exists |
| CL-14573 | Done | [Online Compliance]: Display agreement according to selected format (text, PDF, or image) |
| CL-14574 | Done | [Daily Log][Fill in Compliance][Manage Visit]: Display agreement according to selected format (text, PDF, or image) |
| CL-14662 | Done | [Daily Log][Fill in Compliance][Manage Visit]: UI/UX enhancements & new compliance status |
| CL-14689 | Done | [With Passport][Online Compliance]: UI/UX enhancements & new compliance status |
| CL-14745 | To Do | As a system, I should remove the input methods from the mails and pre-registration screens. |
| CL-14753 | Done | [Without Passport][Online Compliance]: UI/UX enhancements & new compliance status |
| CL-15142 | To Do | Enhance logic for adding an 'Expected' visit when another visit exists |
| CL-15563 | Done | [Daily Log] As a visitor with approval required, my visit should be auto-approved if specific conditions are met. |
| CL-15634 | Done | [Auto Approve][System user] Impact of the auto approved visit on VisiPoint. |
| CL-15654 | Done | [Auto Approve][Visitor] Impact of the auto approved visit on VisiPoint. |
| CL-15900 | To Do | Auto Approve][Daily Log] What happens in case of the kiosk has an old version and a user added a visit eligible for auto... |

**Total: 67 stories**

---

### [CL-373] VisiPoint Passport
**Description:** Passport account creation. System users get passport automatically. Non-system users can create their own.

| Key | Status | Summary |
|-----|--------|---------|
| CL-214 | Done | [Users][Permissions]: If the system user is logged in to a specific dashboard, he should be redirected to his passport a... |
| CL-317 | Done | Subdomain Login Cases |
| CL-345 | Done | As a system user, I should be able to login to subdomain (company dashboard) by phone number or email address |
| CL-404 | Done | [Users][Permissions]: If the system user is logged in to his passport account and has been deactivated/deleted/revoked f... |
| CL-617 | To Do | As a user, for mobile view only, I should be able to download the passport QR code to Google Pay and Apple Wallet |
| CL-618 | Done | Passport magic link can be used only one time |
| CL-619 | Done | As a user, I should be able to view my last 10 visits in my Passport account |
| CL-911 | Done | Add Login button to the last screen after registering to a Passport with email address |
| CL-6520 | Done | As a system, I should remove the Kiosk details step from the sign up form and the add company dashboard modal |
| CL-11035 | Done | As a user, I should be able to enable Two-step authentication on my account |
| CL-11487 | Done | [Edit my data]: Enhancements in full name, phone number and email address |
| CL-11488 | Done | [Change password][Deactivate my account]: Enhancements |
| CL-11492 | To Do | [Edit my data]: Enhancements in uploading photo |
| CL-12212 | Done | As a user, I must accept the 'Privacy Policy' and 'Terms and Conditions' when creating a company dashboard |

**Total: 14 stories**

---

### [CL-616] Touch Mode
**Description:** Enable touch mode on kiosk so users select existing user or add new one to sign in via touchscreen.

| Key | Status | Summary |
|-----|--------|---------|
| CL-614 | Done | As a system admin, I should be able to enable the touch mode on the journey |
| CL-615 | Done | As a system admin, I should be able to add flows without input methods if the touch mode is enabled |
| CL-655 | Done | [Edit Journey]: As a system admin, I shouldn't be able to disable the touch mode if at least one of the flows has no inp... |
| CL-1045 | Done | As a system admin, I should be able to select the name match mode if the touch mode is enabled |
| CL-1060 | Done | Face recognition will not be working for default Walk-in user if touch mode is enabled |
| CL-1103 | To Do | As a system admin, I should be able to make the checks before recognizing the user  |

**Total: 6 stories**

---

### [CL-1160] General
**Description:** General improvements and miscellaneous cloud dashboard features.

| Key | Status | Summary |
|-----|--------|---------|
| CL-43 | Done | Add Back to Passport button in the entity dashboard |
| CL-1176 | Done | Side menu + top bar |
| CL-1498 | To Do | As a system user, I should be directed to the 'Sorry Page' if my company is deleted from the database |
| CL-1513 | Done | As a system, I should save the columns chosen, the columns order, the filters and sorting set by the system admin for ea... |
| CL-1823 | To Do | [Integration]: As a system, I should save the columns chosen, the columns order, the filters and sorting set by the syst... |
| CL-2821 | To Do | [Web & Mobile view][Side menu + top bar] When side menu collapsed |
| CL-4286 | Done | UI enhancements |
| CL-9255 | Done | [All system grids]: Clearing filters, sorting and searches enhancements. |
| CL-9488 | Done | Page template & side menu enhancements |
| CL-9944 | Done | Grid styling enhancements |
| CL-10308 | To Do | When to capitalize words? |
| CL-12154 | Done | Unisex wording |
| CL-12877 | To Do | Wording enhancements |
| CL-13503 | Done | Improve side menu expand/collapse functionality |
| CL-15718 | To Do | [Top Bar]: Language switch button UI enhancement |

**Total: 15 stories**

---

### [CL-1179] Sign in / Sign out
**Description:** Sign in and sign out workflows for visitors and staff through the cloud dashboard.

| Key | Status | Summary |
|-----|--------|---------|
| CL-1180 | Done | [Add/Edit Area]: As a system admin, I should be able to set the login mode and the sign out mode for each area |
| CL-1183 | Done | As a system, I should update the visit status from 'Signed in' to 'Signed out' based on the sign out mode |
| CL-1263 | Done | As a system, I should add a 'Signed in/Signed out' scan if the admin checks user in a 'Sign in/Sign out' area |
| CL-1489 | Done | As a system, I should update the visit status if the area's login mode has been changed |
| CL-1522 | Done | As a user, I should have 2 visits per day per site if the arrival date of the first visit is earlier than today |
| CL-1526 | Done | [Obsolete]: As a system, I should auto sign out the signed in user if he made a new scan with another user type |
| CL-1685 | Done | [Daily Log & Users List]: As a system, I shouldn't ask the admin to fill in checks or compliance while signing out any u... |
| CL-1690 | Done | As a system, I should send an email to the users who auto signed out by the system at midnight or after 24 hours  |
| CL-1722 | Done | As a system, I should auto sign out all the signed in default walk-ins at the midnight |
| CL-1737 | Done | 2 new statuses should be added to the visit statuses if auto signed in / out by the system |
| CL-1844 | Done | If the user signed in and out multiple times within the same visit, then the area's login mode has been changed  |
| CL-1859 | Done | [Obsolete]: As a system, I should auto sign out all the signed in users if the admin removed them from the flow |
| CL-4283 | Done | [Daily Log][Mass Actions]: As a system user, I should be able to 'Sign in/out' multiple users at once |
| CL-4479 | Done | [Daily Log][Mass Actions]: As a system user, I should be able to add/update the required fields for the selected users w... |
| CL-4491 | Done | [Daily Log][Mass Actions]: If the selected users have different user types while signing them in/out |
| CL-4496 | Done | [Daily Log][Mass Actions]: When the system user hits the 'Apply for all' button to sign the selected users in/out |
| CL-6179 | Done | As a system, I should sync any 'Signed in' and 'Auto signed in' visit in the full sync  |
| CL-6897 | Done | [Daily Log][Mass Actions]: As a system, I should remove the 'Temperature/Mask', 'Host' and 'Compliance' columns from the... |
| CL-7173 | To Do | [Active Users][Enhancements]: When the system user archives/deletes a user who is 'Signed in' or 'Auto signed in' |
| CL-7902 | Done | Enhancements in the first screen of the 'Sign in / Sign out' modal |
| CL-11101 | Done | [Sign/Check in][Default user]: Error messages enhancements |
| CL-11399 | Done | As a system, I should show the proper sites in the 'Sign in / Sign out' modal |
| CL-11493 | Done | [Daily Log][Quick Sign in]: As a system user, I should be able to sign users in quickly |
| CL-12231 | Done | [Daily Log][Active Users][Users not on site]: As a system user, I should be able to print a badge when signing a normal ... |
| CL-12232 | Done | [Daily Log][Active Users][Mass Action]: As a system user, I should be able to print badges when signing normal users in/... |
| CL-12330 | Done | [Daily Log][Active Users][Mass Action]: Behavior of 'Apply for all' when printing badges during 'Sign in / Sign out' |
| CL-12534 | Done | As a system, I should email users with selected user types upon auto sign-out at midnight or after 24 hours |
| CL-13074 | Done | [Single Action]: Sign/check user into area with 'Visit rejected' scan |
| CL-13085 | To Do | [Active Users][Signed In][Normal Area]: Impact of changing user type on visits without an 'Expected' scan |
| CL-13096 | Done | [Mass Action]: Sign/check user into area with 'Visit rejected' scan |
| CL-13115 | To Do | [Single Action]: Sign/check user into area with 'No show' scan |
| CL-13118 | To Do | [Single Action]: Sign/check 'Pre-registration with approval required' user into child area with parent area has 'Expecte... |
| CL-13122 | To Do | [Mass Action]: Sign/check user into area with 'No show' scan |
| CL-13123 | To Do | [Single Action][Within Grace Period]: Sign/check user into child area with parent area has 'Expected' scan |
| CL-13124 | To Do | [Single Action][Before Grace Period]: Sign/check user into child area with parent area has 'Expected' scan |
| CL-13128 | To Do | [Single Action]: Sign/check user into site with 'Deleted' visit |
| CL-13131 | Done | [Single Action]: Sign/check user into child area with parent area has 'Visit rejected' scan |
| CL-13132 | Done | [Mass Action]: Sign/check user into child area with parent area has 'Visit rejected' scan |
| CL-13133 | To Do | [Single Action]: Sign/check user into child area with parent area has 'No show' scan |
| CL-13134 | To Do | [Mass Action]: Sign/check user into child area with parent area has 'No show' scan |
| CL-13136 | To Do | [Mass Action]: Sign/check user into site with 'Deleted' visit |
| CL-13141 | To Do | [Mass Action]: Sign/check 'Pre-registration with approval required' user into child area with parent area has 'Expected'... |
| CL-13143 | To Do | [Mass Action][Within Grace Period]: Sign/check user into child area with parent area has 'Expected' scan |
| CL-13165 | To Do | [Mass Action][Before Grace Period]: Sign/check user into child area with parent area has 'Expected' scan |
| CL-13187 | To Do | [Active Users][Not Signed In]: Impact of changing user type on visits with an 'Expected' scan |
| CL-13222 | To Do | signed in visit with arrival < today + expected visit today?? |
| CL-13242 | To Do | [Active Users][Signed In][Remote Area]: Impact of changing user type on visits without an 'Expected' scan |
| CL-13318 | Done | [Edit/Delete Flow]: As a system, I shouldn't auto sign out any user if they no longer has a flow in his Signed in or Aut... |
| CL-13844 | Done | [Without Custom Fields]: As a local printer, I should print a badge when users are checked/signed in from the dashboard |
| CL-13847 | Done | [Without Custom Fields]: As a local printer, I should print a badge when users are signed out from the dashboard |
| CL-13934 | Done | [Mass Action]: Empty 'Compliance' and 'Host' fields when user is rejected and signs/checks into rejected area or its sub... |
| CL-14571 | Done | [Quick Sign in][Sign in/Sign out][Single Action]: Display agreement according to selected format (text, PDF, or image) |
| CL-14572 | Done | [Sign in/Sign out][Mass Action]: Display agreement according to selected format (text, PDF, or image) |
| CL-14596 | Done | [Daily Log][Active Users][Users not on site][Sign in/Sign out][Single Action]: UI/UX Enhancements  |
| CL-14653 | Done | [Daily Log][Active Users][Sign in/Sign out][Mass Action]: UI/UX Enhancements |
| CL-14654 | Done | [With Custom Fields]: As a local printer, I should print a badge when users are checked/signed in from the dashboard |
| CL-14656 | Done | [With Custom Fields]: As a local printer, I should print a badge when users are signed out from the dashboard |
| CL-14737 | Done | [Daily Log][Quick Sign in]: UI/UX Enhancements |
| CL-15114 | To Do | [Active Users][Not Signed In]: Impact of changing user type on visits without an 'Expected' scan |
| CL-15540 | To Do | [Quick Sign in][Sign in/Sign out][Single Action][Visit Permits]: Allow or deny entry depending on visit permits |
| CL-15637 | To Do | [Sign in / Sign out][Mass Action][Visit Permits]: Allow or deny entry depending on visit permits |

**Total: 61 stories**

---

### [CL-3215] Host
**Description:** Host functionality - linking visitors to a host within the company during sign-in/visit flows.

| Key | Status | Summary |
|-----|--------|---------|
| CL-3216 | Done | [Touch Mode][Add/Edit Flow]: As a system admin, I should be able to add a host for any flow |
| CL-3220 | Done | [Touch mode]: As a user, I should be able to select my host while filling in the 'Pre-registration' form |
| CL-3221 | Done | [Touch Mode]: As a system admin, I should be able to select a host when adding an expected visitor |
| CL-3224 | Done | As a system admin, I should be able to select a host while checking in the user from the dashboard |
| CL-3225 | Done | As a system, I should display the host name and user type in the 'Emergency List' and the 'Emergency Session' grids |
| CL-3227 | Done | As a user, I should receive an email if I'm selected as a host |
| CL-3305 | Done | As a system, I should send an email to the site contact email if the user/admin doesn't know the host |
| CL-3382 | Done | [Daily Log] [History]: As a system, I should display the host name and user type |
| CL-3502 | Done | [Passport]: As a system, I should display the host name and user type |
| CL-4498 | Done | [Touch Mode][Daily Log][Mass Actions]: As a system user, I should be able to select a host for the selected users while ... |
| CL-7353 | To Do | As a user, I should be notified if the 'Expected' visit to which I was added as a host was edited or deleted |
| CL-7402 | Done | As a system, I should notify the host if his visitor is denied entry |
| CL-7618 | Done | [Add/Edit Flow]: Host enhancements |
| CL-7676 | Done | As a system, I should notify the host why his visitor was denied entry |
| CL-7686 | To Do | updates to the email sent to host when visitor is entry denied + advanced compliance |
| CL-11769 | Done | [Custom Fields]: As a system, I should display the binded to 'Visit' custom fields in the email sent to the host  |
| CL-14074 | Done | Include visitorâ€™s email and phone number in host/site notification emails |

**Total: 17 stories**

---

### [CL-3469] Structure
**Description:** Platform structure and architecture improvements.

*(No stories linked via Epic Link field -- may contain Bugs/Tasks only)*

---

### [CL-4193] Reporting
**Description:** Reporting module - visit reports, export functionality, analytics and dashboards.

| Key | Status | Summary |
|-----|--------|---------|
| CL-605 | Done | [Dashboard, History & Track and Trace]: If the system admin checked in user manually, his name should be displayed in th... |
| CL-613 | Done | As a system user, I should be able to export the Users, Daily Logs and History grids if they have large number of record... |
| CL-2066 | Done | [Visit Summary]: As a system admin, I want to view the duration between signing in and signing out for each user |
| CL-2081 | Done | [Timesheet]: As a system admin, I want a report showing the total number of logged hours per day/month/quarter for each ... |
| CL-2421 | Done | As a system, I shouldn't send an SMS if the exported file is ready for download |
| CL-3310 | Done | [Timesheet]: As a system admin, I should be able to select months instead of quarter when generating a timesheet  |
| CL-4192 | Done | [Users not on site]: As a system user, I should be able to view the users who haven't entered specific site(s) 'Today'  |
| CL-4238 | Done | [Timesheet]: Validations and UI enhancements while generating the report |
| CL-4356 | Done | [Users not on site]: As a system user, I should be able to sign the user into any of the selected sites   |
| CL-5816 | To Do | [Users not on site][Mass Actions]: As a system user, I should be able to 'sign in' multiple not on site users at once |
| CL-6442 | Done | [Track and Trace]: As a system, I should add a 'Site' column in the grid |
| CL-6794 | To Do | As a system, I should remove archived and deleted users from the generated reports |
| CL-6869 | To Do | [Timesheet]: As a system user, I should be able to view the time spent by the user for sign in/out reasons |
| CL-10829 | Done | [History]: Export enhancements |
| CL-11813 | To Do | [RFID Enrollment]: As a system user, I should be able to view the status of RFID enrollment for my permitted users |
| CL-12209 | To Do | [RFID Enrollment]: Special cases |
| CL-12693 | Done | [History]: Remove PDF from the 'Export' list |

**Total: 17 stories**

---

### [CL-4341] ACL
**Description:** Access Control Lists - role-based permissions for admin, staff, and other user types across the platform.

| Key | Status | Summary |
|-----|--------|---------|
| CL-4340 | Done | [Users]: As a system user, I should be able to select different user roles while inviting user to dashboard  |
| CL-4342 | Done | As a user has the "Host" role, I should be able to add "Expected" visits only |
| CL-4343 | Done | As a user has the "Fire warden" role, I should be able to access all the "Emergency" pages besides adding "Expected" vis... |
| CL-4375 | Done | [Users]: As a system user, I should be able to change the user role for any user has access to the dashboard |
| CL-5774 | Done | As a system, I should display the user role name in the invitation to dashboard email/SMS |
| CL-5937 | Done | [Active Users][Mass Actions]: As a system user, I should be able to change permissions for multiple users |
| CL-6038 | Done | As a system, I should notify the user if his role or permissions have been changed |
| CL-6276 | Done | As a system user, I should be able to restrict the user access to specific sites and user types while inviting him to da... |
| CL-6279 | Done | As a system user, I should be able to change the user's permissions after inviting him to dashboard  |
| CL-6303 | To Do | As a system user, I should be able to view the available user roles in the company dashboard |
| CL-6356 | Done | [Daily Log]: As a system user, I should be able to view data related to my permitted sites and user types |
| CL-6357 | Done | [Users]: As a system user, I should only be able to access the data related to my permitted sites and user types |
| CL-6358 | Done | [Sites & Devices]: As a system user, I should only be able to access data related to my permitted sites |
| CL-6359 | Done | [Reporting][1]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6360 | Done | [User Settings][1] : As a system user, I should only be able to access data related to my permitted user types |
| CL-6361 | Done | [Journey Builder]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6362 | Done | [Add Integration]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6363 | Done | [Emergency Sessions]: As a system user, I should only be able to access data related to my permitted sites and user type... |
| CL-6364 | Done | [Emergency List]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6365 | Done | [Add Visits]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6421 | Done | [Active Users][Mass Actions]: When should the "Permissions" mass action button appear |
| CL-6427 | Done | As a system, I should add "Employee with reporting" role to the available user roles |
| CL-6430 | Done | As an "Employee with reporting" user, I should be able to access the "Reporting" and "Users" pages besides adding "Expec... |
| CL-6432 | Done | [Active Users][Mass Actions]: As a system user, I should be able to specify the permitted sites and user types while inv... |
| CL-6434 | Done | [Active Users][Mass Actions]: When the system user hits the "Invite all" button to invite the selected users to dashboar... |
| CL-6459 | Done | [Reporting][2]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6461 | Done | [Daily Log][History]: As a system, I should display visits/scans based on the user's role |
| CL-6464 | Done | [Users][RFID Enrollment]: As a system user, I should only be able to access data related to my permitted sites |
| CL-6495 | Done | [Daily Log][Active Users][Users not on site]: As a system user, I should be able to "Sign in/out" any user, if he has on... |
| CL-6496 | Done | [Daily Log][Single Action]: As a system user, I should be able to "Sign in/out" any not permitted user, if he has an "Ex... |
| CL-6734 | Done | [Edit Integration]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6735 | Done | [View Integration]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6737 | Done | [Flow]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6756 | Done | [User Settings][2] : As a system user, I should only be able to access data related to my permitted user types |
| CL-6862 | Done | [Daily Log][Mass Actions]: As a system user, I should be able to "Sign in/out" any not permitted user, if he has an "Exp... |
| CL-6927 | Done | [Active Users]: As a system user, I shouldn't be able to change permissions/revoke/archive/delete myself or the company'... |
| CL-6954 | Done | [All Integrations]: As a system user, I should only be able to access data related to my permitted sites and user types |
| CL-6984 | Done | [All Integrations]: As a system, I should Replace the "Active with errors" status with "Active" status |
| CL-7492 | Done | [Owner][Permissions Updated]: When a new site or user type has been added |
| CL-8887 | Done | [Daily Log][History]: As a system user, I should be able to see any "Undefined User" scan if it is within my permitted s... |
| CL-13291 | Done | [Sign/Check in]: Update host search criteria |
| CL-13293 | Done | [Add visits]: Update host search criteria |
| CL-13294 | Done | [Pre-registration from]: Update host search criteria |
| CL-13650 | Done | As a system user, I should be able to sign a user in or out even if their flow includes a non-permitted attendance mode |

**Total: 44 stories**

---

### [CL-4346] Emergency
**Description:** Emergency list and emergency management features for site safety.

| Key | Status | Summary |
|-----|--------|---------|
| CL-2100 | Done | [Emergency List]: As a system admin, I should be able to print a list of all users who currently exist in a specific are... |
| CL-2106 | Done | [Emergency Sessions]: As a system admin, I should be able to start an emergency session on a specific site(s)/area(s) |
| CL-2137 | Done | [Emergency Sessions]: As a system admin, I should be able to end any emergency session |
| CL-2138 | Done | [Emergency Sessions]: As a system admin, I want to see a global bar in the header if there is any active emergency sessi... |
| CL-2747 | Done | Emergency  Sessions Updates |
| CL-9820 | Done | [Emergency List][Start/End Session]: Enhancements |
| CL-13495 | To Do | session ended while the system user is viewing the 'Emergency' sessions page |
| CL-14075 | Done | [Emergency Session][Emergency List]: As a system user, I should be able to view all signed/checked in users for my permi... |

**Total: 8 stories**

---

### [CL-4385] Users
**Description:** User management - creating, editing, deleting, and managing users in the company dashboard.

| Key | Status | Summary |
|-----|--------|---------|
| CL-67 | To Do | As a system user, I should be able to view the user's profile |
| CL-227 | Done | [Add / Edit User]: Add country code field before the phone number field in Add /edit user popup |
| CL-297 | To Do | [User Profile]: The system user should be able to do the following actions to the user |
| CL-329 | Done | [Users List]: As a system admin, I should be able to Invite user to dashboard & Revoke user from dashboard |
| CL-330 | Done | [Users List >> Available Actions]: As a system admin, I should be able to deactivate/activate any user account |
| CL-332 | Done | [Users List >> Add/Edit user]: System user toggle button will be deleted |
| CL-374 | Done | [Users List >> Walk-in User Type]: As a system admin, I should be able to Invite Walk-in user to dashboard and Add RFID ... |
| CL-467 | Done | As a system admin, I shouldn't be able to Invite/Revoke any deactivated user to/from dashboard  |
| CL-468 | To Do | [Active Users]: As a system user, I should be able to deny any user from entering the company if one site exists |
| CL-1017 | Done | [Users >> Import Users]: Add instruction about the uploaded excel file |
| CL-1105 | Done | As a system, I shouldn't send an email/SMS when a non system user is deactivated/activated by the admin |
| CL-1119 | Done | [Obsolete][Add/Edit User]: As a system admin, I should be able to add 'Vehicle Registration' and 'Company Name' for each... |
| CL-1199 | Done | [Archived Users]: As a system, I should display the archived users in a separate grid |
| CL-4396 | Done | [Active Users]: As a system, I should display the activated users in a separate grid |
| CL-4400 | Done | As a system, I should differentiate between the users who accepted the dashboard invitation and who haven't yet  |
| CL-4408 | Done | [Active Users]: As a system user, I should be able to add label(s) to any user |
| CL-4413 | To Do | [Active Users]: As a system user, I should be able to allow any denied user |
| CL-4416 | To Do | [Active Users][Mass Actions]: As a system user, I should be able to 'Sign in/out' multiple users at once + denied sites |
| CL-4418 | To Do | [Active Users][Mass Actions]: as a system user, I should be able to deny multiple active users |
| CL-4419 | To Do | [Archived Users][Mass Actions]: As a system user, I should be able to select multiple users and view the available mass ... |
| CL-4420 | To Do | [Active Users][Mass Actions]: as a system user, I should be able to allow multiple denied users |
| CL-4421 | Done | [Active Users][Mass Actions]: as a system user, I should be able to change user type for multiple users |
| CL-4422 | Done | [Active Users][Mass Actions]: as a system user, I should be able to invite multiple users to dashboard |
| CL-4423 | Done | [Active Users][Mass Actions]: as a system user, I should be able to revoke multiple users from dashboard |
| CL-4424 | Done | [Active Users][Mass Actions]: as a system user, I should be able to add label(s) to multiple users |
| CL-4425 | To Do | [Active Users][Mass Actions]: as a system user, I should be able to archive multiple users |
| CL-4426 | To Do | [Archived Users][Mass Actions]: as a system user, I should be able to activate multiple users |
| CL-4427 | To Do | [Archived Users][Mass Actions]: as a system user, I should be able to delete multiple users |
| CL-4920 | To Do | [Active Users][Mass Actions]: as a system user, I should be able to delete multiple users |
| CL-5021 | To Do | [Active Users]: As a system user, I should be able to deny any user from entering any site if multiple sites exist in th... |
| CL-5585 | Done | [Users][Mass Actions]: As a system, I should display only the 'Active' users in the grid and enable multi-select |
| CL-6117 | Done | [Active Users]: As a system I should differentiate between active and deactivated Passports   |
| CL-6253 | Done | Deleted user sync |
| CL-6440 | Done | As a system user, I should be able to search with '-' to get the records that have null values |
| CL-6448 | To Do | [Import users]: Update the already existing users by importing a file that contains their new details |
| CL-6792 | Done | [Active Users][Mass Actions]: As a system user, I should be able to 'Sign in/out' multiple users at once |
| CL-7131 | Done | As a system, I should check the uniqueness of email address, phone number & ID values with typing |
| CL-7750 | To Do | [Active Users]: As a system user, I should be able to add documents to the user only if they are binded to user profile |
| CL-8341 | To Do | add label mass action enhancements |
| CL-8352 | Done | Limit 'First Name' and 'Last Name' fields to 20 characters |
| CL-9246 | To Do | As a system, I should distinguish the owner of the entity in the active users grid. |
| CL-9247 | To Do | [Active user grid] As a super admin, I should be able to transfer company ownership to any user in the active users grid... |
| CL-9904 | To Do | As a system, I should add a new role named 'Owner' |
| CL-9906 | To Do | When can 'Owner' be deleted?  |
| CL-10025 | To Do | When can 'Owner' be archived? |
| CL-10030 | To Do | When can 'Owner' be revoked? |
| CL-10031 | To Do | When can the role of the 'Owner' be updated to another role? |
| CL-10818 | Done | [Active Users]: Export Enhancements |
| CL-10989 | Done | [Active Users][Mass Actions]: As a system user, I shouldn't be able to change the user type for the default users |
| CL-12210 | To Do | [RFID Enrollment]: As a system user, I should be able to know for which users the RFID instructions haven't been sent |
| CL-12233 | Done | [Active Users][Print QuickPass]: As a system user, I should be able to print a QuickPass for any normal user |
| CL-12234 | Done | [Active Users][Mass Action][Print QuickPass]: As a system user, I should be able to print QuickPasses for multiple norma... |
| CL-12245 | To Do | [Active Users][Add/Edit User]: As a system user, I should be able to set a time to automatically archive any active user |
| CL-12247 | To Do | [Active Users]: As a system user, I should be able to view the archive date and time for any user if it exists |
| CL-12252 | To Do | What should happen if 'Auto Archive' is enabled for a specific user? |
| CL-13325 | Done | [Active Users][Mass Action]: Enhance styling in the 'Change User Type' modal |
| CL-13845 | Done | [Print QuickPass]: As a local printer, I should print a QuickPass for any normal user when triggered from the 'Active Us... |
| CL-14020 | Done | As a system, I should check whether a user has a Passport during the process of importing or activating users  |
| CL-15123 | To Do | [Active Users][Signed In][Normal/Remote Area]: Restrict user type changes when an 'Expected' scan exists |
| CL-15451 | To Do | [Add Visit Permits]: As an admin, I should be able to add visit permits. |
| CL-15498 | To Do | Edit Visit Permits]: As an admin, I should be able to view and edit visit permits grid. |
| CL-15499 | To Do | [Delete Visit Permits]: As an admin, I should be able to view visit permits grid. |

**Total: 62 stories**

---

### [CL-4582] Attendance Modes
**Description:** Attendance mode configuration - controlling how attendance is recorded for different user types.

| Key | Status | Summary |
|-----|--------|---------|
| CL-3749 | Done | As a system user, I want to see the attendance modes in a grid view |
| CL-3750 | Done | As a system user, I should be able to add an attendance mode, if no Wonde integration exists |
| CL-3781 | Done | [Touch Mode][Add/Edit Flow]: As a system user, I should be able to add an attendance mode for any flow |
| CL-4439 | Done | As a system user, I should be able to add a reason not connected to Wonde integration |
| CL-4456 | Done | As a system user, I should be able to add an attendance mode, if Wonde integration exists |
| CL-4458 | Done | As a system user, I should be able to add a reason connected to Wonde integration |
| CL-4487 | Done | [Daily Log & History]: As a system, I should display the attendance code and the reason |
| CL-5527 | Done | [Attendance Modes]: As a system user, I should be able to edit the mode if no reasons created below it |
| CL-5553 | Done | [Touch Mode][Add/Edit Flow]: Enhancements |
| CL-5711 | Done | [Edit Mode]: As a system user, I should be able to edit the mode even if there are reasons created below it |
| CL-5778 | Done | [Add/Edit Reason]: Validations + Enhancements  |
| CL-6048 | Done | [Add/Edit Mode]: As a system user, I should be able to prevent signing in outside the allowed days and times |
| CL-6055 | Done | [Add/Edit Reason]: Validations + UI Enhancements |
| CL-6092 | Done | [Daily Log & History]: As a system, I should add a denied scan, if the user tried to enter the area at a not allowed tim... |
| CL-6484 | Done | As a system, I should prevent signing users into any area according to the applied attendance mode |
| CL-6485 | Done | As a system user, I should be able to select a reason while signing users in, if the mode is linked to Wonde |
| CL-6486 | Done | As a system user, I should be able to select a reason while signing users in, if the mode is not linked to Wonde |
| CL-6487 | Done | As a system user, I should be able to select a reason while signing users out, if the mode is linked to Wonde |
| CL-6489 | Done | As a system user, I should be able to select a reason while signing users out, if the mode is not linked to Wonde |
| CL-6491 | Done | [Daily Log][Mass Actions]: As a system, I should apply the "Attendance Mode" logic when signing multiple users in/out |
| CL-6505 | Done | [Daily Log][Mass Actions]: When the system user hits the "Apply for all" button to sign the selected user in/out |
| CL-6678 | Done | [Daily Log]: As a system, I should display the reason for the last scan in the visit details |
| CL-6854 | Done | As a user, I shouldn't be able to enter the child area, if I'm not allowed to enter its parent area due to the applied m... |
| CL-6894 | Done | [Sign in/Sign out]: As a system, I should automatically add the reason to the scan, if only one reason is available |
| CL-7057 | To Do | What should happen when editing or deleting any attendance mode? |
| CL-7065 | Done | As a system user, I shouldn't be able to delete any attendance mode, if it's added to any flow |
| CL-7350 | Done | [Add/Edit Reason]: The attendance code should be linked to only one reason per mode and not per entity |
| CL-8851 | Done | [Add/Edit Reason]: "Presented Reasons" should be unique per reason |
| CL-11698 | To Do | What should happen when editing or deleting any reason? |
| CL-14371 | Done | [Add/Edit Reason][Wonde]: As a system user, I should be able to remove the first linked attendance code, provided that o... |

**Total: 30 stories**

---

### [CL-4966] Integrations
**Description:** Third-party integrations - Azure AD, Google Directory, Paxton Access, School MIS via Wonde.

| Key | Status | Summary |
|-----|--------|---------|
| CL-4869 | Done | [Wonde]: As a system, I should sync any new scan not linked with reason from Wonde to VisiPoint in real time for any syn... |
| CL-4870 | Done | [Wonde]: As a system, I should sync any new scan linked with reason from Wonde to VisiPoint in real time for any synced ... |
| CL-5543 | Done | [Wonde]: As a system, I should take into consideration the attendance code time range when syncing scan from Wonde to VP |
| CL-6056 | Done | [Wonde]: As a system, I should only sync scans with 'PRESENT' attendance code from Wonde to VisiPoint |
| CL-6744 | Done | [Add/Edit integrations] As a system, I should prevent the system user from creating/editing an integration with the same... |
| CL-12430 | Done | [All integrations]: As a system, I should change the criteria of detecting the duplications while syncing. |
| CL-12445 | To Do | [Daily Log][History]: As a system, I should distinguish scans synced from Paxton from other scans |
| CL-12505 | Done | [Wonde][Integration Settings]: As a system user, I should have the option to choose whether to sync all scans or only on... |
| CL-12516 | Done | [Wonde]: As a system user, I should be able to specify the 'Attendance Code Terminology'  |
| CL-12743 | Done | [Wonde]: Impact of 'Attendance Code Terminology' on syncing scans from Wonde to VP |
| CL-12915 | To Do | When to call 'Duplicates' API from backend? |

**Total: 11 stories**

---

### [CL-5513] Journeys
**Description:** Journey Builder - configuring visit flows with Input, Checks, Compliance, Output, Notification, Feedback.

| Key | Status | Summary |
|-----|--------|---------|
| CL-597 | Done | [Non Touch Mode]: As a system admin, I should be able to manage the content of the instructions screen |
| CL-913 | To Do | Create Journey and Add/Edit Flow changes |
| CL-1691 | Done | As a system admin, I should be able to determine which outputs will be applied based on the scan type |
| CL-2803 | Done | As a system admin, I should be able to upload logo for each journey |
| CL-3122 | To Do | As a system user, I should be able to add only one vaccine as a compliance to the journey flow |
| CL-5512 | Done | [Add/Edit Flow]: As a system, I should display the cog icon if only 'Compliance Alert' is selected in the 'Notification'... |
| CL-5557 | Done | [Edit Journey][Touch Mode]: As a system, I should hide the 'Instruction screen' section |
| CL-5792 | Done | [Edit Journey][Touch Mode]: As a system, I should hide the 'New users via QuickPass' section |
| CL-6523 | Done | As a system user, I should be able to restrict journey access to specific site(s) |
| CL-8161 | To Do | How will changing the journey assigned to the area affect the 'Expected' and 'Pending' scans? |
| CL-8236 | Done | [Add/Edit Flow]:  Output enhancements |
| CL-8237 | Done | [Flow][Notification]: As a system, I should send an email to the specified email address in case of denied entry, sign i... |
| CL-8319 | Done | [Add/Edit Flow]: Notification enhancements |
| CL-8320 | Done | [Add/Edit Flow]: Feedback enhancements |
| CL-9447 | Done | [Professional & Enterprise][Add/Edit Flow][Output][Print Badge]: As a system user, I should be able to select custom fie... |
| CL-11775 | Done | [Add/Edit Flow][Feedback]: As a system user, I should be able to choose to display the first name, last name, or both on... |
| CL-11811 | Done | [Professional & Enterprise][Add/Edit Flow]: Changes that impact custom fields selected for badge printing |
| CL-12535 | To Do | [Add/Edit Flow][Compliance Settings]: New compliance frequency options |
| CL-12881 | Done | [Add/Edit Journey]: Increase 'Journey Name' to 50 characters |
| CL-13001 | To Do | [Non Touch Mode][Edit Journey]: Enhancements for 'New users via QuickPass' field |
| CL-13329 | Done | [Edit Flow]: Logic enhancement in disabling the 'Mobile App (Remote Work)' and 'Mobile App (Site Geofence)' input method... |
| CL-14193 | To Do | [Add/Edit Flow][Output][Print Badge]: As a system user, I should be able to select compliance for badge printing |
| CL-14200 | Done | [Add/Edit Flow][Output][Print Badge]: UI/UX Enhancements |
| CL-14261 | Done | [Lite][Add/Edit Flow][Output][Print Badge]: As a system user, I shouldn't be able to modify custom fields for badge prin... |
| CL-14341 | Done | [Professional & Enterprise][Edit Section][Edit Flow]: Changes that impact custom fields selected for badge printing |
| CL-14640 | Done | [Add/Edit Flow]: PDF/image agreements not supported in Non Touch journeys |
| CL-14641 | Done | [Edit Journey]: Improve popups shown when Touch Mode cannot be disabled |
| CL-14670 | To Do | [Add/Edit Flow]: As a system user, I should be able to add or edit a flow to be without any input methods. |
| CL-14762 | Done | [Edit Journey]: PDF/image agreements not supported in Non Touch journeys |

**Total: 29 stories**

---

### [CL-5750] Daily Log
**Description:** Daily log / dashboard - viewing and managing daily visit records and sign-in/out activity.

| Key | Status | Summary |
|-----|--------|---------|
| CL-253 | Done | [Daily Log & History]: As a system admin, I should be able to view all the compliance details |
| CL-793 | Done | All the scans per user per day per site should be binded to the same visit record in the database |
| CL-798 | Done | [Daily Log]: As a system admin, I should be able to add notes for each visit |
| CL-1120 | Done | Any visit should be per user type |
| CL-1144 | Done | [Daily log & History]: The new structure for the visit |
| CL-1146 | Done | [Daily Log]: As a system admin, I should be able to edit the user's details |
| CL-4274 | Done | [Daily Log][Mass Actions]: As a system user, I should be able to select multiple visits |
| CL-4854 | Done | [Daily Log & History]: As a system, I should keep all the visits/scans for any deleted user |
| CL-5902 | Done | As a system, I should add a denied scan for the undefined user in the following cases |
| CL-5903 | Done | As a system, I should update the denial reasons in the following cases |
| CL-6037 | Done | As a system, I should delete any attached Vaccine/PCR file from the scan details for any deleted user |
| CL-6856 | Done | [Daily Log][History]: Temperature & mask columns |
| CL-8229 | Done | Styling |
| CL-10828 | Done | [Daily Log]: Export enhancements |
| CL-11125 | Done | [Daily Log][History]: As a system user, I should be able to view label(s) assigned to any user |
| CL-11531 | Done | As a system user, I should be able to switch the temperature unit between Â°F & Â°C |
| CL-12253 | Done | [Daily Log][History]: Enhancements to 'By' and 'Input' values for scans synced from Wonde |
| CL-12269 | To Do | [Print Badge][Visit Without Expected Scan]: As a system user, I should be able to print a badge for normal users |
| CL-12321 | To Do | [Mass Action][Print Badge]: As a system user, I should be able to print badges for multiple normal users |
| CL-12453 | To Do | [Daily Log][History]: The 'Auto signed in/out' scan details |
| CL-12456 | Done | [Daily Log][History]: Enhancements to 'By' and 'Input' values for scans added from mobile app |
| CL-12458 | Done | [Daily Log][History]: Enhancements to 'Input' values for scans added from kiosk or dashboard |
| CL-13139 | Done | [Daily Log][History][Compliance Details]: Avoid displaying duplicate answers/notes |
| CL-13227 | To Do | [Edit Expected Visit]: Enhance logic of adding an 'Expected' visit when another visit exists |
| CL-13500 | Done | UI/UX enhancements for 'Daily Log' grid |
| CL-13915 | Done | Add 'Resend Compliance' to the right click actions in 'Daily Log' |
| CL-13932 | To Do | [Print Badge][Expected]: As a local printer, I should print a badge for any normal user when triggered from the 'Daily L... |
| CL-14575 | Done | [Daily Log][History][Compliance Details]: UI/UX Enhancements |
| CL-14754 | Done | [Daily Log][History][Compliance Details]: Display agreement according to selected format (text, PDF, or image) |
| CL-14987 | To Do | [Print Badge][Checked/Signed In]: As a local printer, I should print a badge for any normal user when triggered from the... |
| CL-14999 | To Do | [Print Badge][Signed Out]: As a local printer, I should print a badge for any normal user when triggered from the 'Daily... |
| CL-15031 | To Do | [Print Badge][Visit With Expected Scan]: As a system user, I should be able to print a badge for normal users |
| CL-15052 | To Do | [Mass Action][Print Badge]: Behavior of 'Apply for all' when printing badges |
| CL-15113 | To Do | [Pre-registration]: Impact of changing user type on visits with/without an 'Expected' scan |

**Total: 34 stories**

---

### [CL-5927] RFID Enrollment
**Description:** RFID card enrollment - linking RFID cards to user profiles for sign-in.

| Key | Status | Summary |
|-----|--------|---------|
| CL-5923 | Done | [Active Users]: As a system user, I should be able to send the RFID enrollment instructions to the user, if he has an ac... |
| CL-5936 | Done | [Active Users]: As a system user, I should be able to print a badge for the user, if he doesn't have an email address an... |
| CL-6104 | Done | [Active Users]: As a system user, I should be able to send the RFID enrollment instructions to the user, if he has a dea... |
| CL-6115 | Done | [Active Users][Mass Actions]: As a system user, I should be able to send the RFID enrollment instructions or print badge... |
| CL-6219 | Done | As a system, I should be able to send the print request to the kiosk remotely |

**Total: 5 stories**

---

### [CL-6120] Sites & Devices
**Description:** Managing sites (locations) and devices (kiosks, readers) across the company.

| Key | Status | Summary |
|-----|--------|---------|
| CL-607 | Done | [Kiosk Settings Modal]: Change the Temperature Offset min & max values to -10 & 10 in Celsius and -20 & 20 in Fahrenheit |
| CL-1488 | Done | As a system admin, I should be able to assign 'Sign in / Sign out' login mode for multiple areas in the same site |
| CL-1507 | Done | As a system user, I should be able to assign journey to the kiosk different than the one assigned to its area |
| CL-6119 | Done | As a system, I should add a new column to display printers that are connected to online kiosks |
| CL-6530 | Done | As a system user, I should only be able to assign journeys to their specified site(s) |
| CL-8135 | Done | [Add/Edit site] As a System, I should show a hint if the system user or owner add an invalid mail format. |
| CL-9153 | Done | [Obsolete][Edit Area]: As a system, I should 'Auto Sign out' any 'Signed in' or 'Auto Signed in' users out of the area i... |
| CL-12529 | Done | [Add Normal/Remote Area]: As a system user, I should be able to select which user types receive an email upon auto sign-... |
| CL-12543 | Done | [Edit Normal/Remote Area]: As a system user, I should be able to select which user types receive an email upon auto sign... |
| CL-12752 | To Do | [Add/Edit Site]: As a system user, I should be able to specify the default language for my permitted sites |
| CL-12788 | Done | [Add/Edit Area]: Enhancements to 'Fire Alarm' settings |
| CL-12880 | Done | [Add/Edit Site/Area]: Increase 'Site Name' and 'Area Name' to 50 characters |
| CL-12905 | Done | Remote area enhancements |
| CL-12913 | Done | [Edit Area]: The parent area is 'Check in only' and its child is 'Sign in / Sign out' |
| CL-13088 | Done | UI/UX enhancements for 'Sites & Devices' grid |
| CL-13332 | To Do | [Add Device]: As a system user, I should be able to add different types of devices |
| CL-13341 | To Do | As a system user, I should view and access added PCs in the 'Sites & Devices' grid |
| CL-13554 | Done | As a system user, I should be able to download .exe file for any of my permitted normal areas |
| CL-13556 | Done | [Edit Remote/Normal Area]: The system shouldn't 'Auto Sign out' user when changing their journey to one lacking flow for... |
| CL-14372 | Done | [Edit Area]: Modify when the 'Apply Journey to' radio buttons are displayed |
| CL-14692 | To Do | [Add/Edit/Move Area]: As a system user, I should be able to choose any journey to any normal area. |
| CL-14694 | To Do | [Add/Move Kiosk][Change Journey]: As a system user, I should be able to choose any journey to any kiosk. |
| CL-15195 | Done | Add 'Sign In/Out Mode' setting for kiosks in 'Sign in / Sign out' areas |
| CL-15653 | Done | [Kiosk Settings]: Allow system users to show or hide Pre-registration QR and URL on kiosk screen |

**Total: 24 stories**

---

### [CL-6470] Custom Fields
**Description:** Custom fields - adding custom data fields to user profiles and visit records.

| Key | Status | Summary |
|-----|--------|---------|
| CL-8270 | Done | [Professional & Enterprise]: As a system user, I should be able to add a custom field to a new section  |
| CL-8376 | Done | [Active Users][Add User]: Which binded to 'User profile' custom fields should appear? |
| CL-8377 | Done | [Add Visits]: Which custom fields should appear when adding an 'Expected' visit? |
| CL-8384 | Done | [Active Users][Archived Users]: Which binded to 'User profile' custom fields should be displayed in each grid? |
| CL-8413 | Done | As a system user, I should be able to view sections and custom fields according to my permitted user types |
| CL-8423 | Done | [Active Users][Edit User]: Which binded to 'User profile' custom fields should appear? |
| CL-8424 | Done | [Daily Log][Active Users][Users not on site]: Which custom fields should appear in the 'Sign in / Sign out' modal? |
| CL-8429 | Done | [Daily Log][Active Users]: Which custom fields should appear in the 'Sign in / Sign out' mass action grid? |
| CL-8766 | Done | [Professional & Enterprise]: As a system user, I should be able to add a custom field to an already existing section |
| CL-8767 | Done | [Professional & Enterprise]: What should happen, if a binded to 'User profile' field has been updated? |
| CL-8795 | Done | [Professional & Enterprise]: What should happen, if a section has been deleted? |
| CL-8846 | Done | [Daily Log]: Which binded to 'User profile' custom fields should be displayed in the grid? |
| CL-8895 | Done | [Pre-registration]: Which custom fields should appear when adding an 'Expected' visit? |
| CL-8897 | Done | [Professional & Enterprise]: As a system user, I should be able to edit any of my sections |
| CL-9080 | Done | [History]: Which custom fields should be displayed in the grid? |
| CL-9081 | Done | [Professional & Enterprise][Daily Log]: As a system user, I should be able to edit the binded to 'Visit' custom fields |
| CL-9184 | Done | [Professional & Enterprise]: As a system user, I should be able to add the binded to 'Visit' section to specific flows d... |
| CL-9187 | Done | [Professional & Enterprise][Add/Edit Flow]: As a system user, I should be able to add the binded to 'Visit' sections to ... |
| CL-9231 | Done | [Daily Log]: Which binded to 'Visit' custom fields should be displayed in the grid? |
| CL-9252 | To Do | [Daily Log][History]: What is the effect of changing the 'User Type'? |
| CL-9258 | Done | [Professional & Enterprise]: What should happen, if the section details have been updated? |
| CL-9259 | Done | [Professional & Enterprise]: What should happen, if a binded to 'Visit' field has been updated? |
| CL-9310 | Done | [Visit Summary][Timesheet][Users not on site]: No custom fields should be displayed |
| CL-9347 | Done | [Daily Log][Active Users]: In case of a default user, which custom fields should appear when checking/signing in? |
| CL-9444 | Done | [Daily Log][History]: What is the effect of removing a binded to 'visit' section from the user flow? |
| CL-9609 | Done | Database structure |
| CL-10378 | Done | [Professional & Enterprise][Add/Edit Section]: As a system user, I should be able to specify the custom fields that shou... |
| CL-10379 | Done | [Active Users][Add User]: Custom fields enhancements |
| CL-10401 | Done | [Add Visits]: Which custom fields should appear when confirming an expected visit via the confirmation link, if the user... |
| CL-10415 | Done | [Public QuickPass][VisiPoint Passport]: How will they be affected? |
| CL-10421 | Done | [Pre-registration]: The 'Custom fields' screen |
| CL-10435 | Done | [Pre-registration]: When an already existing user adds an 'Expected' visit with another user type |
| CL-10436 | Done | [Daily Log][Active Users][Users not on site]: Custom fields enhancements in the 'Sign in / Sign out' modal |
| CL-10437 | To Do | What happens when a user fills in custom field while signing in/out using the mobile app? |
| CL-10507 | Done | [Add Visits][Passport]: Which custom fields should appear when confirming an expected visit? |
| CL-10509 | To Do | [Add Visits]: Enhancements in confirming expected visits by the user  |
| CL-10513 | Done | What should happen, if the customer's plan has been changed? |
| CL-10633 | Done | [Add Visits]: Which custom fields should appear when confirming an expected visit via the confirmation link, if the user... |
| CL-10844 | To Do | [Daily Log][Active Users]: Display 'Add' button in the 'All' row for any binded to 'Visit'section in the 'Sign in / Sign... |
| CL-10847 | Done | [Daily Log][Active Users]: As a system user, I should be able to fill in custom fields for users in the 'Sign in / Sign ... |
| CL-11106 | Done | How will removing a binded to 'visit' section from the user flow affect the 'Sign in / Sign out' process, adding and con... |
| CL-11114 | Done | [Professional & Enterprise]: What should happen, if the custom field has been removed from its section? |
| CL-11134 | Done | [Professional & Enterprise][Daily Log]: What should happen when the 'Edit' button is clicked? |
| CL-11205 | Done | As a user, I should be able to fill in the 'Fillable by user' custom fields while confirming any 'Expected' visit added ... |
| CL-11217 | Done | [Daily Log][History]: What should happen when a user is archived/deleted? |
| CL-11481 | Done | [Professional & Enterprise][Add/Edit Section]: Enhancements to 'Stars' and 'Smileys' rating scales |
| CL-11482 | Done | [Professional & Enterprise]: How will the 'Stars' and 'Smileys' fields appear in different screens? |
| CL-11483 | Done | [Professional & Enterprise]: How will the 'Stars' and 'Smileys' fields appear in grids? |
| CL-11536 | Done | [Professional & Enterprise]: What should happen, if a 'Stars' or 'Smileys' field has been updated? |
| CL-12053 | Done | As a system, I should remove the 'Company' & 'Vehicle Registration' columns from the Emergency List & Sessions |
| CL-13954 | Done | Temporarily: Hide elements related to 'Print badge' from all screens |
| CL-14011 | Done | As a system user, I should be able to export the custom fields grid. |
| CL-14194 | Done | Re-display elements related to 'Print badge' on all screens |
| CL-15030 | To Do | [Add/Edit Section]: Add 'Static' as a new custom field type |
| CL-15059 | To Do | 'Static' custom fields shouldn't appear in any user or system user forms |
| CL-15061 | To Do | Display 'Static' custom fields on printed badges if available for printing |

**Total: 56 stories**

---

### [CL-6902] Company Details
**Description:** Company settings - managing company profile, logo, settings, and configuration.

| Key | Status | Summary |
|-----|--------|---------|
| CL-482 | Done | As a system admin, I should be able to upload logo for my company |
| CL-1768 | To Do | As a system, I should revoke pending invitations when the package is downgraded to Standalone   |
| CL-9624 | Done | [Cloud]: As an 'lt_techteam' account, I should be able to update the customer's plan |
| CL-9905 | To Do | As a system, I should display the owners in the 'Company Details' page |
| CL-11520 | Done | As a company owner, I should be able to enable Two-step authentication in my company dashboard |
| CL-11800 | Done | The email in the changing package popup should be a hyperlink |
| CL-11918 | Done | If the company owner enables Two-step authentication in the company dashboard without it being enabled in their Passport... |
| CL-12510 | Done | As a system user, I should be able to enable two-step authentication on my company dashboard based on user roles |
| CL-12738 | To Do | Handle user role change to one that requires Two-step authentication while accessing the dashboard |
| CL-12745 | To Do | As a system user, I should be able to edit the company details |
| CL-12751 | To Do | UI/UX Enhancements |
| CL-14090 | To Do | As an 'lt_techteam' account, I should be able to change the company subscription |

**Total: 12 stories**

---

### [CL-7322] Clients APIs
**Description:** Client-facing API endpoints for integration with external systems.

*(No stories linked via Epic Link field -- may contain Bugs/Tasks only)*

---

### [CL-7436] Announcements
**Description:** Announcements module - creating and broadcasting announcements to users/sites.

| Key | Status | Summary |
|-----|--------|---------|
| CL-7401 | Done | As an admin, I should be able to send announcements to any user type |
| CL-7437 | Done | [Obsolete]: As an admin, I should be able to view all the announcements created in the company |
| CL-7606 | Done | Announcements feedback |
| CL-7659 | Done | [View Announcement]: as a system user I should be able to view all the existing announcements in a grid |
| CL-7660 | Done | [Edit Announcement]: As a system user I should be able to edit the scheduled announcements. |
| CL-13327 | Done | [Add Announcement]: As a system user, I should be able to publish an announcement in my permitted areas |
| CL-13330 | Done | As a system user, I should be able to view announcements in my permitted areas |
| CL-13331 | Done | [Edit Announcement]: As a system user, I should be able to edit announcements scheduled in my permitted areas |

**Total: 8 stories**

---

### [CL-8152] Remote Sign in
**Description:** Remote sign-in - allowing users to sign in/out remotely via mobile app or geofencing.

| Key | Status | Summary |
|-----|--------|---------|
| CL-8158 | Done | [Remote Sign in] As an Owner/a system user, I should be able to add a new input method in the flow called 'Remote' |
| CL-8159 | Done | [Remote Sign in] As a system user/owner, I should be able to add/edit 'Remote Area'. |
| CL-8160 | Done | [Remote Sign in] As a passport user, I should be able to select/change my primary entity. |
| CL-8222 | Done | [Remote Sign in] As a system, I should add the visit/scan in the dashboard once the process is done from the mobile side... |
| CL-8314 | Done | [Remote Sign in] Cases where the system user/owner will not be able to edit the journey to make it fully remote |
| CL-8345 | Done | The reports pages and emergency pages affected by Remote Sign in feature |
| CL-8426 | Done | [Add Visits]: As a system user, I shouldnâ€™t be able to add an â€œExpectedâ€ visit in any remote area |
| CL-8477 | Done | [Sites & Devices]: How will the 'Remote' feature affect the 'Move' modals? |
| CL-8744 | Done | What should happen if the user tried to remote sign in while he is signed into a normal area? |
| CL-8769 | Done | [Add/Edit Flow]: Remote input settings |
| CL-8844 | Done | [Remote Scans][Daily Log]: As a system, I should add a new column in the 'Visit History' sub-table named 'Location' |
| CL-8894 | Done | [Obsolete][Edit Flow]: Disable 'Remote' option in case of 'Journey' is assigned to 'Remote Area'. |
| CL-9289 | Done | [Remote Sign in] Enhancements. |

**Total: 13 stories**

---

### [CL-8425] User Types
**Description:** User type management - defining user types (Staff, Visitor, Walk-in, Approval, etc.).

| Key | Status | Summary |
|-----|--------|---------|
| CL-1073 | Done | As a system admin, I shouldn't be able to change registration method to 'Registration not required' in this case |
| CL-6736 | Done | [User Types]As a system, I should prevent the system user from creating/editing a parent user type or child user type wi... |
| CL-12496 | Done | [Add/Edit User Type]: As a system user, I should be able to specify whether profile photo is mandatory |
| CL-13009 | Done | The 'Registration Not Required' user type cannot have children or be a child |
| CL-15519 | Done | [Add/Edit User Type] As a system, I should add a setting for auto approval for 'Pre-registration with approval required'... |

**Total: 5 stories**

---

### [CL-8861] Geofencing
**Description:** Geofencing - automatic sign-in/out when users enter or exit geofenced boundaries.

| Key | Status | Summary |
|-----|--------|---------|
| CL-8862 | Done | [Sites & Devices]: As a system user, I should be able to specify the site's location |
| CL-8863 | Done | [Add/Edit Flow]: As a system user, I should be able to add a new input method in the flow called 'Geofencing' |
| CL-8878 | Done | [Daily Log]: As a system, I should add a visit/scan once the process is done from the mobile side |
| CL-9138 | Done | [Add/Edit Flow] As a system, I should show a note to elaborate which user types the 'Geofencing' option will be applied ... |
| CL-9143 | Done | [Add/Edit Site]: 'Geofencing' Enhancements. |
| CL-9215 | Done | [Sites & Devices]: As a system user, I should be able to download a QR code for the 'Check in only' areas, if the 'Geofe... |

**Total: 6 stories**

---

### [CL-11774] Duplication Control Board
**Description:** Detecting and managing duplicate user records and duplicate visit entries.

| Key | Status | Summary |
|-----|--------|---------|
| CL-294 | To Do | [User Settings >> Duplication Control Board]: The system admin should be able to find and solve duplicates during day to... |
| CL-486 | To Do | When merge users with different passport id or local id |
| CL-11773 | Done | As a system, I shouldn't show duplicates if only the 'First Name' or the 'Last Name' is duplicated |

**Total: 3 stories**

---

### [CL-14695] Scalability and Performance Enhancements
**Description:** Platform scalability improvements and performance optimizations.

*(No stories linked via Epic Link field -- may contain Bugs/Tasks only)*

---

### [CL-16841] Survey Module
**Description:** Complete Survey Module - Survey Dashboard, Responses, Survey Builder, Reports, NPS Setup. Create, distribute and analyze surveys.

| Key | Status | Summary |
|-----|--------|---------|
| CL-16507 | Done | Create a New Survey (Basic Modal)(MVP) |
| CL-16508 | Done | Open Survey Builder After Creating a New Survey |
| CL-16509 | Done | Question Selection & Builder Initialization |
| CL-16510 | Done | Configure Smiley Question & Builder Interface |
| CL-16511 | Done | Configure Likert & NPS Question Types |
| CL-16512 | Done | Customize Survey Design and Appearance |
| CL-16514 | Done |  Edit Button Modal for Supported Question Types |
| CL-16533 | Done | Surveys Page: Empty & Populated States |
| CL-16534 | Done | Configure Star Rating Question in Survey Builder |
| CL-16535 | Done | As a Survey Creator, I want to configure a binary choice question (Thumbs or Yes/No) |
| CL-16617 | In Progress | Unified Survey Builder Controls & Dynamic Feedback  |
| CL-16678 | Done | Survey Builder Question Preview & Sequential Navigation |
| CL-16774 | Done | [Journey Builder] : Survey Management Grid |
| CL-16775 | Done | Configure a Survey-Based Journey in Journey Builder |
| CL-16842 | Done | US-1.1 â€” Overview Tab: Monitor Survey Performance at a Glance |
| CL-16843 | Done | US-1.2 â€” Metrics Tab: View Per-Survey KPI Cards |
| CL-16844 | Done | US-1.3 â€” Performance Tab: Best Performers Ranking |
| CL-16845 | Done | US-1.4 â€” Averages Tab: Heatmap by Day and Hour |
| CL-16846 | Done | US-2.1 â€” Browse and Filter Individual Survey Responses |
| CL-16847 | Done | US-3.1 â€” Survey Builder: View Survey List and Summary Stats |
| CL-16848 | To Do | US-3.2 â€” Survey Builder: Create a New Survey |
| CL-16849 | Done | US-3.3 â€” Survey Builder: Perform Actions on Existing Surveys |
| CL-16850 | Done | US-4.1 â€” Reports: View All Generated Reports |
| CL-16851 | Done | US-4.2 â€” Reports: Create and Configure a New Survey Report |
| CL-16852 | To Do | US-5.1 â€” NPS Setup: View NPS Scoring Range Configuration |
| CL-16853 | To Do | US-5.2 â€” NPS Setup: Edit NPS Scoring Ranges |
| CL-17043 | Done | US-6.1-Add Introduction Page Option in the Add Question Modal |
| CL-17049 | Done | US-6.2- Introduction Page Block in the Survey Builder |
| CL-17073 | Done | [Survey][Open Text] :As a survey creator, I want to add an open text form question  |
| CL-17074 | To Do | [Survey][Names and Age Form]: As a survey creator, I want to add a Names and Age form question to my survey |
| CL-17075 | Done | [Survey][Phone Number]:As a survey creator, I want to add a Phone Number form question  |
| CL-17076 | Done | [Survey][Email Form Question] : As a survey creator, I want to add an Email form question  |
| CL-17077 | To Do | [Survey][Comment Form Question] : As a survey creator, I want to add a Comment form question  |
| CL-17078 | Done | [Survey[[Edit]As a survey creator, I want to configure an open text field in my form question so that I can control how ... |
| CL-17079 | Done | [Survey][Edit]As a survey creator, I want to configure the First Name, Last Name, and Age fields in my Names and Age for... |
| CL-17080 | Done | [Survey][Edit]As a survey creator, I want to configure a phone number field in my form question |
| CL-17081 | To Do | [Survey][Edit]As a survey creator, I want to configure an email field in my form question  |
| CL-17082 | To Do | [Survey][Edit]As a survey creator, I want to configure an open ended comment field in my form question  |
| CL-17285 | To Do | [Survey][Single/Multi-selection]: As survey creator, I want to build a custom choice question using the 'Icons and Label... |

**Total: 39 stories**

---

## KI Board -- Kiosk (23 Epics)

### [KI-33] Expected Visitors
**Description:** Managing expected visitors on kiosk - pre-registered visitors expected to arrive at a site.

*(No stories linked via Epic Link field)*

---

### [KI-42] Compliance
**Description:** Compliance checks on kiosk - Questionnaire, Agreement, and Document-Vaccine/PCR types in journey flow.

| Key | Status | Summary |
|-----|--------|---------|
| KI-44 | Done | [Vaccin/PCR]: As a system user, I should be able to view and control all documents binded to the user profile |
| KI-46 | Done | [Vaccin/PCR]: As a system user, I should be able to delete any document binded to the user profile |
| KI-47 | Done | [Vaccin/PCR]: As a system user, I should be able to disable/enable any document binded to the user profile |
| KI-49 | Done | [Vaccin/PCR]: As a system user, I should be able to view the document (Compliance) in the Scan Details screen |
| KI-51 | Done | [Vaccin/PCR]: As a system user, I should be able to add vaccine as a document in the compliance |
| KI-52 | Done | [Vaccin/PCR]: As a system user, I should be able to add the vaccine as a compliance in the journey flow |
| KI-63 | Done | [Vaccin/PCR]: As a user, i should scan my vaccine QR in the entry, and see the result |
| KI-67 | Done | [Vaccin/PCR]: Processing SHC vaccine data |
| KI-3281 | Done | [Standalone]: As a system user, I should be able to add up to 15 questions in any questionnaire |
| KI-3319 | Done | [Kiosk][Mobile app]: As a compliance, I should be filled in according to the compliance frequency set on the flow page |
| KI-3334 | To Do | [Touch Mode][Enterprise]: As a user, I should be able to fill in the 'Advanced' agreement on the kiosk screen |
| KI-3337 | To Do | [Touch Mode][Enterprise]: As a user, I should be able to fill in the 'Advanced' questionnaire on the kiosk screen |
| KI-3339 | To Do | [Enterprise]: As an already existing user, I should be able to fill in my compliance online not on the kiosk screen in s... |
| KI-3341 | To Do | [Enterprise]: What should happen, if the 'Pre-registered by admin' user will fill in his compliance online? |
| KI-3342 | To Do | [Enterprise]: How should the 'Pending' scan be updated, if the user tried to sign/check into the same area again? |
| KI-3343 | To Do | As a kiosk, I should display compliance to users according to their priorities |
| KI-3344 | To Do | [Enterprise]: What should happen, if the user has a 'Pending' scan and tried to enter a child area |
| KI-3357 | To Do | [Touch Mode]: 'Simple' compliance enhancements |
| KI-3358 | To Do | [Compliance]: 'Document - Vaccine/PCR' enhancements  |
| KI-3395 | Done | As a system, I should save the wrong answers submitted by the user for any compliance |
| KI-3468 | To Do | [Enterprise]: What should happen, if the not 'Pre-registered by admin' user will fill in his compliance online? |
| KI-3470 | To Do | [Enterprise]: How should the 'Expected' scan be updated, if the user tried to sign/check into the same area again? |
| KI-3471 | To Do | [Enterprise]: As a user, I should be able to sign into the same area, if my 'Pending' scan has been updated to 'No show' |
| KI-3472 | To Do | [Enterprise]: As a new user, I should be able to fill in my compliance online not on the kiosk screen in some cases |
| KI-3502 | To Do | How will updating any compliance affect the 'Expected' scans? |
| KI-3508 | To Do | How will deleting any compliance affect the 'Expected' scans? |
| KI-3509 | To Do | [Enterprise]: How will updating/deleting the compliance category affect the 'Expected' scans? |
| KI-3510 | To Do | How will changing the compliance/compliance frequency in the user's flow affect his 'Expected' scans? |
| KI-3701 | To Do | [Flow]: 'Document - Vaccine/PCR' enhancements |
| KI-3720 | To Do | Database structure |
| KI-3724 | To Do | [Standalone][Add/Edit Compliance]: Compliance name, positive and negative answers should be unique |
| KI-6204 | To Do | As a Kiosk, I should show the compliance to the user according to the compliance frequency set on the flow page |
| KI-6651 | Done | [Standalone]: Compliance questionnaire enhancement |
| KI-7273 | Done | [Cloud][Administration][Compliances]: PDF/image agreements in 'Compliance List' |
| KI-7274 | Done | [Standalone][Administration][Compliances]: PDF/image agreements in 'Compliance List' |

**Total: 35 stories**

---

### [KI-130] Public QuickPass
**Description:** Kiosk-side QuickPass - standalone kiosk registration with QR code valid for 7 days.

| Key | Status | Summary |
|-----|--------|---------|
| KI-131 | Done | As a system user, I should be able to add QR Code as an input method for the flow in the journey builder |
| KI-132 | Done | As a user, I should be able to scan the QR code (public URL) displayed on the standalone kiosk |
| KI-136 | Done | As a kiosk, I should allow the user types set to use the QR code function if it's enabled in the journey builder as an i... |
| KI-140 | Done | As a user, I should be able to scan the QuickPass QR code from my mobile, email or pdf easily on the kiosk to sign in. |
| KI-144 | Done | As a kiosk, If the QR code is not enabled as an input in the flow |
| KI-2637 | Done | As a kiosk, I should prevent any 'Pre-registered by admin' user when scanning his 'Public QuickPass' |

**Total: 6 stories**

---

### [KI-149] Visipoint Passport Account
**Description:** Passport account creation and management on the kiosk side.

| Key | Status | Summary |
|-----|--------|---------|
| KI-239 | Done | As a user, I should be able to scan my passport QR code through any kiosk and get my details appear in the users list |
| KI-284 | Done | [Standalone Kiosk]: As a system user, I should be able to add the QR code as an input method for any user type |
| KI-460 | Done | [Cloud]: As a system, I should check if the Passport is deactivated or deleted when the user scans his Passport QR code ... |

**Total: 3 stories**

---

### [KI-159] Exploratory Testing
**Description:** Exploratory testing epic for kiosk features.

*(No stories linked via Epic Link field)*

---

### [KI-169] Pre-registration
**Description:** Kiosk-side pre-registration - displaying QR code for visitors to pre-register before visiting.

| Key | Status | Summary |
|-----|--------|---------|
| KI-173 | Done | As a user, I should be able to scan the Pre-registration QR code (URL) displayed on the cloud kiosk |
| KI-174 | Done | As a user (with approval required), I should be able to fill in the compliance on the kiosk screen within the grace peri... |
| KI-180 | Done | As a kiosk, I shouldn't display the compliance on the kiosk screen if filled online by the user |
| KI-454 | Done | As a user, I should be able to enter any area inside the site following some rules |
| KI-455 | Done | The content of the 'Local QR code' should be encrypted, so no user can scan alternative QR codes |
| KI-456 | Done | As a user, I should be able to scan my 'Local QR code' through any kiosk if the QR code is enabled in my flow |
| KI-465 | Done | [Create/Edit Flow]: As a system admin, I shouldn't be able to add more than one user type with registration method (Regi... |
| KI-466 | Done | As undefined user, I should be able to sign in to the site with the user type determined by the system admin |
| KI-649 | Done | [Cloud]: New user (signs in with any type of QR code) should be added to the users list with the user type selected by t... |
| KI-651 | Done | As a kiosk, I should prevent the user (with approval required) if journey stage is 'Waiting for admin approval' |
| KI-656 | Done | [Standalone]: The already existing users should be able to enter the site without registering for a visit |
| KI-757 | Done | As a system, I should create a default user for each 'Registration not required' user type |
| KI-784 | Done | As a kiosk, I should prevent the user from entering the site/area in some cases |
| KI-787 | Done | As a user (with approval required), I should be able to fill in the compliance again if the admin updated my expected vi... |
| KI-820 | Done | [Touch Mode][Cloud]: As a user, I should be able to add a new profile for myself depending on my registration method |
| KI-821 | Done | [Touch Mode]: If the user adds a new profile with email address/phone number already exists with the same user type |
| KI-867 | Done | As a system, I should create a new visit for each user not recognized by the kiosk |
| KI-1029 | Done | As a user (with approval required), I should be able to enter the expected area if the admin approved my visit request |
| KI-1030 | Done | As a user (with approval required), I shouldn't be able to enter the expected area if the admin rejected my visit reques... |
| KI-1116 | Done | [Touch Mode]: Entry screen changes |
| KI-1196 | Done | [Touch Mode][Standalone]: As a user, I should be able to add a new profile for myself depending on my registration metho... |
| KI-1207 | Done | [Touch Mode]: If the user adds a new profile with email address/phone number already exists with a different user type |
| KI-1208 | Done | [Touch Mode]: As an already existing user, I should be able to enter the site depending on my registration method |
| KI-1209 | Done | [Touch Mode]: As a kiosk, I should prevent the user from entering the expected area before and after the grace period |
| KI-1210 | Done | [Touch Mode]: As a kiosk, I should prevent the user from entering the expected area if the admin didn't approve his visi... |
| KI-1343 | Done | As a kiosk, I shouldn't display the compliance to the user if filled by the admin from the dashboard |
| KI-1578 | Done | As a kiosk, I should upload the expired vaccine or the positive PCR document for the 'with approval required' user |
| KI-1597 | Done | [Touch Mode][Create/Edit Flow][Standalone]: As a system admin, I should be able to add multiple 'Registration not requir... |
| KI-1598 | Done | [Touch Mode]: As a system, I should display a confirmation screen before adding an 'Expected' visit |
| KI-1681 | Done | As a kiosk, my behavior should change according to the registration method if the journey stage is 'Compliance failed' |
| KI-1686 | Done | [Touch Mode][Cloud]: As a user, I should be able to register for another visit if the admin deleted my 'Expected' visit |
| KI-1747 | Done | [Touch Mode]: As a 'Registration allowed' user, I shouldn't add visit date & time while adding a new profile for myself |
| KI-2634 | Done | [Non Touch Mode]: As a system, I should add a profile for any new user scans his valid QuickPass |
| KI-2635 | Done | [Touch Mode]: As a new user, I should be able to select my user type after scanning a valid QuickPass |
| KI-6330 | To Do | [Cloud]: What should happen when a new user scans their QuickPass? |
| KI-6691 | To Do | [Touch Mode]: Conditions for adding an 'Expected' visit when another visit exists |
| KI-6694 | To Do | [Touch Mode]: Enhance logic for adding new users with existing email address/phone number |
| KI-6729 | To Do | [Touch Mode]: Enhance logic to prevent adding an 'Expected' visit when another visit exists |
| KI-7805 | Done | [Kiosk] As a visitor with approval required, my visit should be auto-approved if specific conditions are met. |
| KI-7814 | Done | [Cloud][Kiosk]: Show or hide Pre-registration URL & QR on kiosk screen |

**Total: 40 stories**

---

### [KI-444] Touch Mode
**Description:** Touch mode on kiosk - touchscreen enabling users to select existing user or add new one to sign in.

| Key | Status | Summary |
|-----|--------|---------|
| KI-445 | Done | [Standalone]: As a system admin, I should be able to enable the touch mode on the journey |
| KI-446 | Done | [Standalone]: As a kiosk, I should display the user types having flows on my entry screen |
| KI-447 | Done | As a user, I should be able to sign in to the company if the touch mode is enabled in the journey |
| KI-448 | Done | [Standalone]: As a system, I should allow only the Visitor user type to add a new user through the kiosk screen |
| KI-459 | To Do | Color themes on the kiosk |
| KI-601 | Done | [Standalone]: As a system admin, I shouldn't be able to disable the touch mode if at least one of the flows has no input... |
| KI-613 | Done | [Cloud]: The behavior of different user types if the touch mode is enabled |
| KI-752 | Done | UI enhancements |
| KI-758 | To Do | As a kiosk, I should be able to make the temperature/mask checks first before recognizing the user |
| KI-793 | Done | As a system admin, I should be able to select the name match mode if the touch mode is enabled |
| KI-3279 | Done | [Touch Mode]: As a kiosk, I should alert the user before timing out - 1 |
| KI-3991 | Done | [Touch Mode]: As a kiosk, I should alert the user before timing out - 2 |
| KI-4860 | Done | [Touch Mode]: As a kiosk, I should alert the user before timing out - 3 |
| KI-6160 | Done | [Cloud][Touch Mode]: As a kiosk, I should ask users to take a profile photo based on user type during sign/check in |
| KI-6183 | Done | [Cloud][Touch Mode]: As a kiosk, I should ask users to take a profile photo based on user type when adding a new profile |
| KI-6187 | Done | [Cloud]: As a kiosk, I should ask existing users to take a profile photo based on user type when adding expected visits |
| KI-6256 | Done | [Cloud][Entry Screen]: Which user types should appear on the kiosk screen? |
| KI-6262 | Done | [Cloud]: What should happen if a user cannot find their profile during a search? |
| KI-6264 | Done | [Standalone][Entry Screen]: Which user types should appear on the kiosk screen? |
| KI-6265 | Done | [Cloud][Entry Screen]: When should the kiosk show the 'Registration required' status? |
| KI-6275 | Done | [Standalone]: What should happen if a user cannot find their profile during a search? |
| KI-6331 | Done | As a 'Registration not required' user, I should be able to go through the flow normally |
| KI-7166 | To Do | [Cloud][Standalone] As a user, I should be able to select my preferred language to use the kiosk. |
| KI-7278 | Done | [Touch Mode][Compliance]: Extend timeout duration to 20s before displaying timeout popup |
| KI-7821 | Done | [Cloud]: Enhance visibility of user types on kiosk entry screen |
| KI-7822 | Done | [Standalone]: Enhance visibility of user types on kiosk entry screen |

**Total: 26 stories**

---

### [KI-806] Sign in / Sign out
**Description:** Kiosk sign-in and sign-out flows - the core check-in/check-out experience.

| Key | Status | Summary |
|-----|--------|---------|
| KI-807 | To Do | [Standalone][Journey Builder]: As a system admin, I should be able to set the login mode and the sign out mode |
| KI-808 | Done | As a system, I should add a 'Signed in/Signed out' scan if the user enters or leaves a 'Sign in/Sign out' area |
| KI-864 | To Do | [Standalone]: As a system, I should update the visit status from 'Signed in' to 'Signed out' based on the sign out mode |
| KI-993 | Done | As a system, I should auto sign out all the 'Signed in' visits when downgrading from cloud to standalone |
| KI-1025 | Done | [Obsolete][Cloud]: As a system, I should auto sign out the signed in user if he made a new scan with another user type |
| KI-1082 | Done | [Non Touch Mode]: As a user, I shouldn't be asked to pass the checks or fill in the compliance if I'm signing out |
| KI-1083 | Done | [Touch Mode]: As a user, I shouldn't be asked to pass the checks or fill in the compliance if I'm signing out |
| KI-2018 | Done | As an archived user, I shouldn't be able to enter any area in the company |
| KI-2641 | To Do | [Denied Scan]: Kiosk behavior when no flow is assigned to the user |
| KI-2756 | Done | [Denied Scan]: As a system, I should prevent any user from signing in using a deleted Local QuickPass |
| KI-6268 | Done | [Cloud]: When should a 'Pre-registration with approval required' user be prevented from entering a child area? |
| KI-6269 | Done | [Cloud]: When should the user be allowed to enter the child area if the parent area has an 'Expected' scan? |
| KI-6329 | Done | [Cloud]: When should a 'Pre-registered only' user be prevented from entering a child area? |
| KI-6541 | To Do | [Cloud]: As a user, I should be prevented from entering a child area with parent area has 'Expected' scan before the gra... |
| KI-6658 | To Do | [Cloud][Kiosk]: What happens if a user is signed into a parent area while not allowed to then attempts to check into a c... |
| KI-6666 | To Do | [Cloud][Kiosk][Signed In]: Impact of changing user type by system user on visits without an 'Expected' scan |
| KI-6675 | To Do | [Cloud][Kiosk]: Impact of changing user type by system user on visits with an 'Expected' scan |
| KI-6690 | To Do | [Cloud][Kiosk]: Enhance entry logic for existing users if an 'Expected' scan exists |
| KI-6711 | To Do | [Cloud][Kiosk]: What happens if a user tries to check into a child area without being signed into its parent area? |
| KI-6751 | Done | [Cloud]: As a user, I should be able to sign out if I'm signed in or auto signed in even though I don't have a flow. |
| KI-6752 | To Do | [Cloud][Kiosk]: Enhance entry logic for existing users if no 'Expected' scan exists |
| KI-7221 | Done | [Kiosk][Touch Mode]: Display agreement according to selected format (text, PDF, or image) |
| KI-7265 | Done | [Kiosk][Touch Mode]: Compliance UI/UX Enhancements |
| KI-7375 | To Do | [Cloud][Kiosk][Not Signed In]: Impact of changing user type by system user on visits without an 'Expected' scan |
| KI-7412 | Done | [Cloud]: Add 'Sign In/Out Mode' setting for kiosks in 'Sign in / Sign out' areas |
| KI-7663 | Done | [Cloud][Kiosk][Visit Permits]: Allow or deny entry depending on visit permits |

**Total: 26 stories**

---

### [KI-1414] Mobile App
**Description:** Visipoint mobile/passport app - features for remote sign-in, geofencing, notifications.

| Key | Status | Summary |
|-----|--------|---------|
| KI-1390 | Done | Visipoint Mobile App (Sign Up) |
| KI-1402 | Done | Visipoint Mobile App ( Profile screens) |
| KI-1418 | Done | Visipoint Mobile App (Forget password) |
| KI-1460 | Done | Visipoint Mobile App ( Companies pages - Emergency session) |
| KI-1530 | Done | [Profile]: As a user, I should be able to reset my password  |
| KI-1539 | Done | [Companies]: As a user, I should receive an invitation when I'm invited to access a company dashboard |
| KI-1611 | Done | [Emergency Sessions]: As a system user, I should be able to start a new session |
| KI-1636 | Done | [Emergency Sessions]: As a system user, I should be able to join any active session  |
| KI-1637 | Done | [Emergency Sessions]: As a system user, I should be able to end any active session |
| KI-1742 | Done | [Home][Upcoming Visits]: As a user, I should be able to fill in the compliance again if the system user re-sent it |
| KI-1793 | Done | [Home][Upcoming Visits]: As a user, I should be able to confirm my 'Expected' visit if it's added by a system user |
| KI-1796 | Done | [Expected Visits]: As a system user, I should be able to add an 'Expected' visit for a new user/an already existing one |
| KI-1805 | Done | [Expected Visits]: Show confirmation modal when system user adds an 'Expected' visit |
| KI-1823 | Done | [Expected Visits][Touch Mode]: As a system user, I should be able to select a host when adding an 'Expected' visit |
| KI-1824 | Done | [Expected Visits]: As a system user, I should be able to view all the 'Expected' visits added by me |
| KI-1825 | Done | [Expected Visits]: As a system user, I should be able to edit the 'Expected' visit |
| KI-1826 | Done | [Expected Visits]: As a system user, I should be able to delete the 'Expected' visit |
| KI-1827 | Done | [Expected Visits]: As a system user, I should be able to approve the 'Expected' visit for users 'with approval required' |
| KI-1828 | Done | [Expected Visits]: As a system user, I should be able to reject the 'Expected' visit for users 'with approval required' |
| KI-1829 | Done | [Expected Visits]: As a system user, I should be able to approve the rejected visit for users 'with approval required' |
| KI-1830 | Done | [Expected Visits]: As a system user, I should be able to reject the approved visit for users 'with approval required' |
| KI-1831 | Done | [Expected Visits]: As a system user, I should be able to manage the visit for users 'with approval required' |
| KI-1872 | Done | [Expected Visits]: As a system user, I should be able to send the compliance again if the visitor didn't pass it |
| KI-1873 | Done | [Expected Visits]: As a system user, I should be able to change the filter criteria |
| KI-1891 | Done | [Home][Upcoming Visits]: As a user, I should be able to fill in the compliance again if the system user edited my 'Expec... |
| KI-1971 | Done | [Companies]: As a user, what I will be able to access in any of my companies depends on my role |
| KI-1972 | Done | [Expected Visits]: As a user has the 'Host' role, I should be selected as a host automatically when adding an 'Expected'... |
| KI-2027 | Done | [Expected Visits]: As a system, I should display the 'Fill in compliance' option for 'Registration allowed' and 'Pre-reg... |
| KI-2029 | Done | [Expected Visits]: As a system user, I should be able to fill in/update the compliance for the expected visitors |
| KI-2043 | Done | [Profile]: As a user, I should be able to view all my personal details |
| KI-2044 | Done | [Profile]: As a user, I should be able to sign out from my Passport account |
| KI-2045 | Done | [Profile]: As a user, I should be able to deactivate/activate my Passport account |
| KI-2046 | Done | [Profile]: As a user, I should be able to edit my personal details |
| KI-2164 | Done | [Home][Upcoming Visits]: As a user I should be able to view all the compliance added to my flow |
| KI-2172 | Done | [Home][Upcoming Visits]: As a user, I should be able to view all my upcoming visits not just today's visits |
| KI-2259 | To Do | [Companies]: Downgrade package from Cloud to Standalone |
| KI-2280 | To Do | [Home][Upcoming Visits]: As a user, I should be able to scan the vaccine/PCR QR code while filling the compliance online |
| KI-2644 | Done | As a system user, I should be able to quickly add an expected visit or start an emergency session |
| KI-2651 | Done | [Companies]: As a system, I should update the allowed screens with changing the user's role |
| KI-3005 | Done | [Expected Visits]: Which 'Expected' scans should appear based on the user's role  |
| KI-3007 | Done | [Expected Visits]: As a system user, I should only be able to access data related to my permitted sites and user types |
| KI-3011 | Done | As a system, I should notify the user if his role or permissions have been changed |
| KI-3022 | Done | [Emergency Sessions]: As a system user, I should only be able to access data related to my permitted sites and user type... |
| KI-3034 | Done | As a system, I should add 'Employee with reporting' role to the available user roles |
| KI-3071 | Done | [Expected Visits][New Visit]: 'Host' and 'Visitor' fields enhancements |
| KI-3232 | Done | [Expected Visits]: As a system user, I should only view my permitted sites and user types in the filter screen |
| KI-3278 | Done | [Announcements]: As a user, I should be able to receive announcements sent by any admin from the dashboard |
| KI-3280 | Done | As a user, I should receive a notification when being added as a host to an 'Expected' visit and when my visitor arrives |
| KI-3345 | Done | [View Announcement]: Enhancements |
| KI-3474 | To Do | [Home][Upcoming Visits]: Compliance enhancements |
| KI-3475 | To Do | [Enterprise][Home][Upcoming Visits]: How will the 'Advanced' agreement look like? |
| KI-3476 | To Do | [Enterprise][Home][Upcoming Visits]: How will the 'Advanced' questionnaire look like? |
| KI-3477 | To Do | [Enterprise][Home][Upcoming Visits]: How will the 'Advanced' document look like? |
| KI-3487 | To Do | [Expected Visits]: Compliance enhancements in the 'Fill in Compliance' and 'Manage Visit' screens |
| KI-3488 | To Do | [Expected Visits]: How will the 'Advanced' compliance look like in the 'Fill in Compliance' and 'Manage Visit' screens? |
| KI-3520 | To Do | [Remote Sign in]: Compliance enhancements |
| KI-3669 | To Do | [Emergency Sessions][Expected Visits]: Enhancements |
| KI-3994 | Done |  [Custom fields][Remote sign in][Geofencing]: As a user, I should fill in the 'Fillable by user' custom fields when chec... |
| KI-3995 | Done | [Custom fields][Remote sign in][Geofencing]: As a user, I should fill in the 'Fillable by user' custom fields when signi... |
| KI-4023 | Done | [Custom fields]: What should happen, if a section has been deleted? |
| KI-4059 | To Do | [Remote Sign in][Geo-Sign in] Screen order enhancements. |
| KI-4190 | Done | [Custom Fields][Expected Visits]: Which custom fields should appear when adding an 'Expected' visit? |
| KI-4217 | Done | [Custom fields]: For an activated user, the binded to 'User profile' custom fields should be filled in again  |
| KI-4336 | To Do | Styling enhancements |
| KI-4464 | Done | [Custom fields][Remote sign in][Geofencing]: What should happen after hitting the 'Check in', 'Sign in' or 'Sign out' bu... |
| KI-4856 | Done | [Custom fields][Profile][Expected Visits]: Remove 'Company Name' field |
| KI-5024 | Done | [Mobile App] As a user, I should be able to enable Two-step authentication on my account |
| KI-5089 | Done | [Custom fields]: What should happen, if the section details have been updated? |
| KI-5090 | Done | [Custom fields]: What should happen, if a binded to 'User profile' field has been updated/deleted? |
| KI-5091 | Done | [Custom fields]: What should happen, if a binded to 'Visit' field has been updated/deleted? |
| KI-5157 | Done | [Custom fields][Home][Upcoming visits]: Which custom fields should appear while confirming an expected visit? |
| KI-5368 | Done | [Custom fields]: How will removing a binded to 'visit' section from the user flow affect the 'Sign in / Sign out' proces... |
| KI-5370 | Done | [Custom fields]: What should happen, if the customer's plan has been changed? |
| KI-5398 | Done | [Custom fields]: How will the 'Stars' and 'Smileys' fields appear in different screens? |
| KI-5530 | Done | What should happen, if 'Two step-authentication' is enabled in company dashboard? |
| KI-5647 | Done | [Expected Visits]: As a system, I should send an email to the host or to the site contact when adding a new visit |
| KI-5650 | Done | [Remote Sign in][Geofencing]: As a system, I should send an email to the host or to the site contact email in various ca... |
| KI-6036 | Done | [Remote Sign in][Geofencing][Custom Fields]: As a system, I should display the binded to 'Visit' custom fields in the em... |
| KI-6044 | Done | Unisex wording |
| KI-6058 | To Do | [Expected Visits]: As a system user, I should be able to set a time to automatically archive any new user |
| KI-6063 | To Do | What should happen if 'Auto Archive' is enabled for a specific user? |
| KI-6174 | To Do | Loader in upcoming visits & expected visit (mobile app) |
| KI-6176 | Done | As a system, I should implement Two-Step Authentication in company dashboard based on user role |
| KI-6206 | To Do | As a compliance, I should be filled in according to the compliance frequency set on the flow page |
| KI-6258 | Done | [Home]: QR code enhancements |
| KI-6261 | Done | As a mobile application, I should send a notification to the system users, informing them of a user awaiting admin appro... |
| KI-6682 | Done | [Custom fields][Sign up]: Remove 'Company Name' field |
| KI-6683 | Done | [Remote Sign in][Geofencing][Home]: Displaying the 'Sign in' and 'Geo-Check in' buttons for child user types |
| KI-6684 | To Do | [Remote Sign in]: Impact of changing user type by system user on 'Signed in' visits without an 'Expected' scan |
| KI-6687 | To Do | [Geofencing]: Impact of changing user type on 'Signed in' visits |
| KI-6724 | To Do | [Expected Visits]: Enhance logic of adding an 'Expected' visit when another visit exists |
| KI-6731 | Done | [Expected Visits]: Update host search criteria |
| KI-6733 | Done | [Geofencing][Remote Sign in]: Update host search criteria |
| KI-6750 | To Do | [Geofencing]: What happens if a user tries to check into a child area without being signed into its parent area? |
| KI-6765 | To Do | [Geofencing]: As a signed in user who now has no flow, I should be able to Sign out. |
| KI-6769 | Done | [Remote Sign in]: As a signed in user who now has no flow, I should be able to Sign out. |
| KI-6784 | Done | [Expected Visits]: Update conditions for 'Manage Visit' action visibility |
| KI-6798 | To Do | [Expected Visits][Print Badge]: As a system user, I should be able to print a badge when adding a visit for a user |
| KI-6863 | Done | As an admin or fire warden, I should be able to view the 'Users On Site Report'. |
| KI-6950 | Done | [Remote Sign in][Geofencing][Home]: Sign in details should reflect the current site/area name |
| KI-6978 | Done | [Emergency Session][Users on Site Report]: As a system user, I should be able to view all signed/checked in users for my... |
| KI-7024 | Done | As a system user, I shouldn't be able to select the same user multiple times while creating an expected visit for multip... |
| KI-7222 | To Do | [Remote Sign in][Geofencing]: Display agreement according to selected format (text, PDF, or image) |
| KI-7223 | To Do | [Home][Online Compliance]: Display agreement according to selected format (text, PDF, or image) |
| KI-7224 | To Do | [Expected Visits][Fill in Compliance][Manage Visit]: Display agreement according to selected format (text, PDF, or image... |
| KI-7339 | Done | [Expected Visits][Fill in Compliance][Manage Visit]: UI/UX enhancements & new compliance status |
| KI-7340 | To Do | [Home][Online Compliance]: UI/UX enhancements & new compliance status |
| KI-7355 | Done | [Remote Sign in][Geofencing][Compliance]: UI/UX Enhancements |
| KI-7363 | To Do | [Custom Fields]: 'Static' custom fields shouldn't appear in any user or system user forms |
| KI-7518 | To Do | [Expected Visits]: Hide 'Manage Visit' button if user type was changed by system user |
| KI-7697 | To Do | [Remote Sign In][Visit Permits]: Allow or deny entry depending on visit permits |
| KI-7817 | To Do | [Auto Approve][Visitor] Impact of the auto approved visit in online compliance. |
| KI-7819 | Done | [Auto Approve][System user] Impact of the auto approved visit on VisiPoint. |

**Total: 113 stories**

---

### [KI-1560] Host
**Description:** Host selection on kiosk during visitor sign-in - linking visitor to host.

| Key | Status | Summary |
|-----|--------|---------|
| KI-1591 | Done | [Touch Mode][Cloud]: As user, I should be able to select my host while adding an 'Expected' visit |
| KI-1592 | Done | [Touch Mode][Cloud]: As a user, I should be able to select my host while entering any area |
| KI-1593 | Done | [Touch Mode][Cloud][Scan Details]: As a kiosk, I should display the host name and user type |
| KI-1596 | Done | [Touch Mode][Cloud][Journey Builder & Flow]: As a kiosk, I should display the user type(s) selected as a host |
| KI-1604 | Done | [Touch Mode][Cloud]: As a kiosk, I should display the host name and user type in the 'Emergency List' |
| KI-6732 | Done | [Cloud][Kiosk][Touch Mode]: Update host search criteria |

**Total: 6 stories**

---

### [KI-1970] Emergency
**Description:** Emergency features on kiosk - emergency list, emergency alerts.

| Key | Status | Summary |
|-----|--------|---------|
| KI-1226 | Done | [Emergency List]: As a system admin, I want to print the emergency list from the kiosk |
| KI-1227 | Done | [Emergency Session]: As a system admin, I want an emergency warning on the kiosk screen when the emergency session start... |
| KI-1269 | Done | [Emergency Session]: As a system admin, I should be able to hide the emergency warning screen from the kiosk screen |

**Total: 3 stories**

---

### [KI-2010] Attendance Modes
**Description:** Attendance mode handling on kiosk side.

| Key | Status | Summary |
|-----|--------|---------|
| KI-1997 | Done | [Touch Mode][Cloud][Scan Details]: As a kiosk, I should display the reason and the attendance code |
| KI-2021 | Done | [Touch Mode][Cloud]: As a kiosk, I should display the available reasons in the 'Sign in Reasons' screen |
| KI-2022 | Done | [Touch Mode][Cloud]: As a kiosk, I should display the available reasons in the 'Sign out Reasons' screen |
| KI-2023 | Done | [Touch Mode][Cloud][Journey Builder & Flow]: As a kiosk, I should display the attendance mode |
| KI-2024 | Done | [Touch Mode][Cloud]: As a kiosk, I should display the 'Sign in Reasons' screen while the user is signing in  |
| KI-2025 | Done | [Touch Mode][Cloud]: As a kiosk, I should display the 'Sign out Reasons' screen while the user is signing out |
| KI-2049 | Done | [Touch Mode][Cloud]: As a kiosk, I should allow the user to enter a reason if tries to sign in again after the allowed t... |
| KI-2755 | Done | As a kiosk, I should allow/prevent the signing in process according to the applied mode |
| KI-2949 | Done | [Journey Builder & Flow]: The attendance mode shouldn't appear, if the area's login mode is 'Check in only' |
| KI-3106 | Done | [Night/24 hours shift]: As a kiosk, I should extend applying the mode to the next day |
| KI-3151 | Done | [Sign in / Sign out]: As a kiosk, I should automatically add the reason to the scan, if only one reason is available |
| KI-3154 | Done | As a user, I shouldn't be able to enter the child area, if I'm not allowed to enter its parent area due to the applied m... |
| KI-5584 | To Do | [Touch Mode]: How will deleting any attendance mode affect the 'Sign in / Sign out' process? |

**Total: 13 stories**

---

### [KI-2328] Journey Builder
**Description:** Journey Builder on kiosk - rendering the configured journey flow steps on kiosk screen.

| Key | Status | Summary |
|-----|--------|---------|
| KI-1093 | Done | [Cloud]: As a kiosk, I should apply the outputs according to the admin's selection |
| KI-1752 | Done | [Flow]: As a kiosk, I should display the 'Compliance Settings' as set by the admin from the dashboard |
| KI-2327 | Done | [Journey Builder][Touch Mode]: As a system, I should hide the 'Instruction screen' section |
| KI-2606 | Done | [Standalone][Non Touch Mode][Journey Builder]: As a system user, I should be able to select which user type should be ad... |
| KI-3551 | Done | As a kiosk, I should display the granted, denied and goodbye messages as set by the system user |
| KI-3552 | Done | [Standalone][Flow][Outputs][Print Badge]: As a kiosk connected to a printer, I should print a badge for the user when ch... |
| KI-3553 | Done | [Flow]: Notification enhancements |
| KI-3708 | Done | [Standalone][Flow][Notification]: As a system, I should send an email to the specified email address in case of denied e... |
| KI-4335 | Done | [Flow]: Outputs enhancements |
| KI-4792 | Done | [Cloud][Flow][Outputs][Print Badge]: As a kiosk connected to a printer, I should print a badge for the user when checkin... |
| KI-4793 | Done | [Cloud][Flow][Outputs][Print Badge]: As a kiosk connected to a printer, I should print a badge for the user when signing... |
| KI-5626 | Done | [Cloud]: As a kiosk, I should display the first name, last name, or both according to the 'Feedback' settings  |
| KI-5646 | Done | [Flow][Outputs][Save Data]: As a kiosk, I should capture the scan image if the 'Scan Image' toggle is 'On'  |
| KI-5648 | Done | [Cloud][Flow][Output][Print Badge]: Custom fields in print badge settings modal |
| KI-5649 | Done | [Cloud][Flow][Outputs][Print Badge]: Display custom fields on printed badge during check in or sign in/out |
| KI-6052 | Done | [Cloud][Flow][Outputs][Print Badge]: As a kiosk connected to a printer, I should print a badge when signing users in/out... |
| KI-6205 | To Do | [Flow][Compliance Settings]: New compliance frequency options |
| KI-7028 | To Do | [flow][output][print badge]: compliance in print badge settings modal |
| KI-7029 | To Do | display compliance in printed badge when check/sign in/out |
| KI-7260 | Done | [Without Custom Fields]: Enhancements to check in / sign in / sign out badge |
| KI-7264 | To Do | [Cloud][Non Touch Mode]: Kiosk behavior if there is no available input method assigned to it. |
| KI-7275 | Done | [Administration][Journey Builder][Flow]: PDF/image agreements not supported in Non Touch journeys |
| KI-7276 | Done | [Administration][Journey Builder]: PDF/image agreements not supported in Non Touch journeys |
| KI-7505 | To Do | [Without Custom Fields]: Enhancements to check in / sign in / sign out badge 2 |

**Total: 24 stories**

---

### [KI-2356] Users
**Description:** User management on kiosk side - user lookup, profile display during sign-in.

| Key | Status | Summary |
|-----|--------|---------|
| KI-464 | To Do | As a denied user, I shouldn't be able to enter any area inside the denied site(s) within the denial period |
| KI-2382 | To Do | [Touch Mode]: As a user, I shouldn't be able to register for a visit on any denied site within its denial period |
| KI-2391 | Done | [Cloud]: As a user, I should be able to have multiple RFIDs |
| KI-2753 | Done | As a system, I should add a label beside any archived user in the users list |
| KI-2944 | Done | [Touch Mode]: As a 'Signed in' user, I should be auto signed out if I'm archived from the dashboard |
| KI-3620 | Done | Limit 'First Name' and 'Last Name' fields to 20 characters |
| KI-6053 | Done | [Print QuickPass]: As a kiosk connected to a printer, I should print a QuickPass for any normal user when triggered from... |
| KI-6062 | To Do | What should happen if 'Auto Archive' is enabled for a specific user? |
| KI-6180 | Done | [Standalone][Add/Edit User]: Limit the 'First Name' and 'Last Name' fields to 20 characters |

**Total: 9 stories**

---

### [KI-2387] RFID Enrollment
**Description:** RFID card enrollment on kiosk - scanning and linking RFID cards to user profiles.

| Key | Status | Summary |
|-----|--------|---------|
| KI-2389 | Done | As a kiosk, I should ask the user to scan his QuickPass after scanning an unknown RFID |
| KI-2390 | Done | [Non Touch Mode]: As a system, I should add a new user if the scanned QuickPass doesn't match with any of the existing u... |
| KI-2804 | Done | As a kiosk, I should be able to print a badge for any user when the printing action is triggered from the dashboard |
| KI-3628 | To Do | RFID badge enhancements |

**Total: 4 stories**

---

### [KI-2504] General
**Description:** General kiosk improvements and miscellaneous features.

| Key | Status | Summary |
|-----|--------|---------|
| KI-6880 | To Do | [Print Badge][Expected]: As a kiosk connected to a printer, I should print a badge for any normal user when triggered fr... |
| KI-6882 | To Do | [Print Badge]: As a kiosk connected to a printer, I should print a badge for any selected user when adding an 'Expected'... |
| KI-7357 | To Do | [Print Badge][Checked/Signed In]: As a kiosk connected to a printer, I should print a badge for any normal user when tri... |
| KI-7358 | To Do | [Print Badge][Signed Out]: As a kiosk connected to a printer, I should print a badge for any normal user when triggered ... |
| KI-7365 | To Do | [Cloud]: Display 'Static' custom fields on printed badges if available for printing |

**Total: 5 stories**

---

### [KI-2642] Visits History (Scans)
**Description:** Visit history display on kiosk - showing previous sign-in/out records.

*(No stories linked via Epic Link field)*

---

### [KI-2803] Hardware Status
**Description:** Hardware status monitoring on kiosk - device health, connectivity, peripheral status.

| Key | Status | Summary |
|-----|--------|---------|
| KI-2802 | Done | [Cloud]: As a kiosk, I should send details about the connected printer in the synced data  |

**Total:  stories**

---

### [KI-3489] Remote Sign in
**Description:** Remote sign-in handling on kiosk side.

| Key | Status | Summary |
|-----|--------|---------|
| KI-3490 | Done | [Remote Sign in] As a passport user, I should be able to remote sign in if no attendance mode applied in the flow. |
| KI-3491 | Done | [Remote Sign in] As a passport user, I should be able to remote sign out. |
| KI-3492 | Done | [Remote Sign in] As a passport user, I should be able to select my primary entity. |
| KI-3501 | Done | [Remote Sign in] As a passport user, I should be able to sign in again after signing out with reason has a duration. |
| KI-3518 | Done | [Remote Sign in] As a passport user with 'Pre-registered by admin' registration method, I should be able to remote sign ... |
| KI-3666 | Done | [Expected Visits][Emergency Session]: Enhancements |
| KI-3700 | Done | What should happen if the user tried to sign/check into normal area while he is signed into a remote area? |
| KI-3702 | Done | When a user who is 'Signed in' remotely is archived/deleted |
| KI-3703 | Done | 'Signed in', 'Signed out' and 'Denied' scans behavior  |
| KI-3769 | Done | What should happen if the user tried to remote sign in while he is signed into a normal area? |
| KI-3775 | Done | Remote sign in steps enhancements |
| KI-3869 | Done | As a mobile application, I should send the GPS co-ordinates to the dashboard if needed |
| KI-4066 | Done | Cases where downgrading to standalone will affect the 'Remote Sign-in' feature. |
| KI-4067 | Done | [Remote Sign in/out][Geo-Sign in/out] Title of the 'Site & Area' choosing page behavior. |
| KI-4167 | To Do | Cases where downgrading to standalone will affect the 'Remote Sign-in' feature. |

**Total: 15 stories**

---

### [KI-3946] Custom Fields
**Description:** Custom fields on kiosk - displaying and capturing custom data fields during sign-in.

| Key | Status | Summary |
|-----|--------|---------|
| KI-3945 | Done | [Touch Mode]: As a user, I should fill in the 'Fillable by user' custom fields on the kiosk screen when checking/signing... |
| KI-3981 | Done | [Touch Mode]: As a user, I should fill in the 'Fillable by user' custom fields on the kiosk screen when signing out |
| KI-3993 | Done | [Touch Mode]: The order of 'Custom fields' screens between other screens during the check/sign in process |
| KI-4189 | Done | [Kiosk]: Remove 'Company Name' and 'Vehicle Registration' from the user's profile |
| KI-4191 | Done | [Touch Mode]: In case of a default user, which custom fields should appear on the kiosk screen?  |
| KI-4457 | Done | Database structure |
| KI-4852 | Done | [Touch Mode]: What should happen when a new user adds an 'Expected' visit? |
| KI-4855 | Done | [Touch Mode]: What should happen when an already existing user adds an 'Expected' visit with the same user type? |
| KI-4922 | Done | [Touch Mode]: What should happen after hitting the 'Check in', 'Sign in' or 'Sign out' button? |
| KI-4923 | Done | [Touch Mode]: The activated user should fill in his binded to 'User profile' custom fields again |
| KI-4924 | Done | [Touch Mode]: Custom fields enhancements when signing/checking in |
| KI-4926 | To Do | [Touch Mode]: Details of the 'Denied' scan added due to inactivity in the custom fields screen |
| KI-5081 | Done | [Touch Mode]: What should happen when the system user empties an optional 'Fillable by user' field? |
| KI-5082 | Done | [Touch Mode]: How will removing a binded to 'visit' section from the user flow affect the 'Sign in / Sign out' process a... |
| KI-5083 | Done | [Touch Mode]: What should happen, if the section details have been updated? |
| KI-5084 | Done | [Touch Mode]: What should happen, if a binded to 'User profile' field has been updated/deleted? |
| KI-5087 | Done | [Touch Mode]: What should happen, if a binded to 'Visit' field has been updated/deleted? |
| KI-5367 | Done | [Touch Mode]: What should happen, if a section has been deleted? |
| KI-5369 | Done | [Touch Mode]: What should happen, if the customer's plan has been changed? |
| KI-5397 | Done | [Touch Mode]: How will the 'Stars' and 'Smileys' fields appear on the kiosk screen? |
| KI-5576 | Done | [Touch Mode]: What should happen, if a 'Stars' or 'Smileys' field has been updated? |
| KI-7361 | To Do | [Kiosk][Touch Mode]: 'Static' custom fields shouldn't appear in any user forms |

**Total: 22 stories**

---

### [KI-3952] Geofencing
**Description:** Geofencing on kiosk/mobile - automatic sign-in/out based on location boundaries.

| Key | Status | Summary |
|-----|--------|---------|
| KI-3953 | Done | As a VP application, I should ask for the location permissions |
| KI-3954 | Done | As a VP application, I should push a notification if the passport user crossed into the geofenced boundaries |
| KI-3955 | Done | As a VP application, I should push a notification if the passport user exited the geofenced boundaries |
| KI-3992 | Done | As a kiosk, I should show the 'No input methods' screen in this case |
| KI-4100 | Done | As VP application, I should push the Geo-sign in/out notification within the timeframe specified by the passport user. |
| KI-4125 | Done | As a user, I should be able to Geo-sign in/out using the 'Sign in' & 'Sign out' button |
| KI-4126 | Done | As a user, I should be able to Geo-Check in using the 'Geo-Check in' button |
| KI-4138 | Done | [Remote sign in][Geofencing][Home]: What should happen with clicking on the 'Sign in' button? |
| KI-4192 | Done | [Geo-Sign in] As a system, I should push a Geo-Sign in notification in the following cases. |

**Total: 9 stories**

---

### [KI-7281] Company Details
**Description:** Company settings and branding reflected on kiosk side.

| Key | Status | Summary |
|-----|--------|---------|
| KI-510 | Done | The uploaded company logo should be displayed in the following locations |

**Total:  stories**

---

### [KI-8486] Survey Modules
**Description:** Survey module on kiosk - Smiley, Likert scale, Star rating, Custom Choice questions for visitors.

| Key | Status | Summary |
|-----|--------|---------|
| KI-8138 | Done | As a kiosk ,I want to present Smiley questions  |
| KI-8142 | Done | As a kiosk, I want to present the fixed 5-point Likert scale |
| KI-8143 | Done | As a kiosk, I want to present the 5-Star Rating question |
| KI-8275 | To Do | As a survey respondent, I want to see a welcoming introduction screen before the survey starts so that I understand what... |
| KI-8299 | To Do | As a kiosk respondent, I want to see name and age input fields on screen so that I can enter my personal details as part... |
| KI-8300 | To Do | As a kiosk respondent, I want to see one or more phone number input fields on screen so that I can enter phone number(s)... |
| KI-8324 | To Do | Kiosk â€” Display Custom Choice Question (Icons Only Style) |
| KI-8325 | To Do | Kiosk â€” Display Custom Choice Question (Labels Only Style) |
| KI-8494 | To Do | KIOSK-004: Present Customers With a Language Choice at the Start |
| KI-8495 | To Do | KIOSK-005: Deliver the Full Survey in the Customer's Chosen Language |

**Total: 10 stories**

---

## QA Testing Notes — Expected Behaviors (Do NOT Report as Bugs)

> Added: 2026-06-30 | Source: Smoke testing session on https://visipoint.uk
> These items were initially flagged during smoke testing but confirmed by the team as **intentional application logic**.

| # | Module | Behavior | Why It Is By Design |
|---|--------|----------|---------------------|
| 1 | Users > Edit Full Name | Both name fields appear labeled "Last Name" | Intentional UI layout/logic — not a labeling defect |
| 2 | Users > Edit Full Name | No validation error when saving with empty first name | Silent save behavior is by design for this form |
| 3 | Dashboard > Quick Sign In | No validation error when clicking Next without selecting a user | Expected — Next button behavior without selection is intentional |
| 4 | Announcements > Add Announcement | No validation error when submitting with empty Title/body | Submit with empty fields is handled silently by design |
| 5 | Journey Builder > Create Journey | Save silently fails when Journey Name is empty or Site not selected | Intentional — form does not surface validation messages |
| 6 | Compliance > Create | No validation error when creating with empty required fields | Silent failure behavior is by design |
| 7 | Emergency List > Start Session | START SESSION button (step 2) shows no tooltip/feedback when disabled with no site selected | Disabled button with no feedback is intentional UX choice |
| 8 | Add Visits | No validation error when submitting Add Visit with all fields empty | Silent failure on empty submit is by design |
| 9 | Sites & Devices > Add site | No validation error when adding a site with empty Name/Email/TimeZone | Silent failure behavior is by design |

**Rule for future QA sessions:** Do not raise validation-absence issues on Create/Add/Save forms as bugs. The application intentionally allows silent submit behavior on required fields. Only report validation issues if a form **corrupts data** or **navigates incorrectly** as a result.

---
