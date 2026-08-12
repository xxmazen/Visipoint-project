package signupExecution;

import BaseTest.Test_Base;
import SignUpPOM.Sign_Up_Page;
import drivers.ChromeFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.LocalDateTime;

public class signUP {

    private WebDriver driver;
    private Test_Base testBase;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            driver = new ChromeFactory()
                    .setupDriver();
            testBase = new Test_Base(driver);
            LocalDateTime startTime = LocalDateTime.now();
            testBase.logInfo("Test execution started at: " + startTime);
            testBase.getBaseUrl();
        } catch (Exception e) {
            if (testBase == null) {
                System.out.println("[ERROR] Failed to initialize WebDriver: " + e.getMessage());
                e.printStackTrace();
            } else {
                testBase.logError("Failed to initialize WebDriver: " + e.getMessage());
            }
            throw new RuntimeException("Failed to setup WebDriver", e);
        }
    }

    // Test Cases related to Sign Up Functionality (VISIPOINT Passport)

    @Test(priority = 1)
    public void navigateToVisipointSignUpPage() {
        new Sign_Up_Page(driver)
                .navigateToVisipointSignUpPage()
                .isUserNavigateToSignUpPage("register-");
    }

    @Test(priority = 2)
    public void verifyFunctionalityWhenUserEntersValidEmail() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersValidEmail("Mazen", "Mohamed","m.mohamed+9820@lamasatech.com")
                .isRegistrationButtonDisabled();
    }

    @Test(priority = 3)
    public void verifyErrorMessageWhenUserEntersInvalidEmail() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersInvalidEmail("Mazen", "Mohamed","m.mohamedlamasatech.com")
                .isValidationMessageDisplayed("Please enter valid email.");
    }

    @Test(priority = 4)
    public void verifyErrorMessageWhenUserEnterEmailAlreadyExist() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEnterCharactersIntoPhoneNumber("Mazen", "Mohamed", "test@visipoint.me")
                .isEmailAlreadyExistsErrorDisplayed();
    }

    @Test(priority = 5)
    public void verifyRegistrationButtonDisabledWhenFieldsAreEmpty() {
        new Sign_Up_Page(driver)
                .navigateToVisipointSignUpPage()
                .isRegistrationButtonDisabled();
    }

    @Test(priority = 6)
    public void verifyRegistrationButtonDisabledWithOnlyFirstName() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersFirstNameOnly("Mazen")
                .isRegistrationButtonDisabled();
    }

    @Test(priority = 7)
    public void verifyRegistrationButtonDisabledWithOnlyEmail() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersEmailOnly("m.mohamed+9820@lamasatech.com")
                .isRegistrationButtonDisabled();
    }

    @Test(priority = 8)
    public void verifyFunctionalityWhenUserEntersValidPhoneNumber() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersValidPhoneNumber("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com", "+201234567890")
                .isRegistrationButtonEnabled();
    }

    @Test(priority = 9)
    public void verifyRegistrationButtonEnabledWhenUserEntersCharactersInPhoneNumber() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersCharactersInPhoneNumber("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com", "abcdef")
                .isRegistrationButtonEnabled();
    }

    @Test(priority = 10)
    public void verifyHeaderTitleOnSignUpPage() {
        new Sign_Up_Page(driver)
                .navigateToVisipointSignUpPage()
                .isHeaderTitleDisplayed("PASSPORT ACCOUNT");
    }

    @Test(priority = 11)
    public void verifyUserCanNavigateToLoginFromSignUpPage() {
        new Sign_Up_Page(driver)
                .clickLoginLinkOnSignUpPage()
                .isUserNavigatedToLoginPage();
    }

    @Test(priority = 12)
    public void verifyRegistrationButtonDisabledWithNamesButNoEmail() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersNamesOnly("Mazen", "Mohamed")
                .isRegistrationButtonDisabled();
    }

    // Test Cases related to Sign Up Functionality (COMPANY Passport)

    @Test(priority = 13)
    public void navigateToCompanySignUpPage() {
        new Sign_Up_Page(driver)
                .navigateToCompanySignUpPage()
                .isUserNavigateToSignUpPage("register");
    }

    @Test(priority = 14)
    public void verifyFunctionalityWhenUserEntersValidCredentialsForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEnterValidCredentialsForCompanyPassport("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com", "0123456789")
                .isNextButtonEnabled();
    }

    @Test(priority = 15)
    public void verifyErrorMessageWhenUserEntersInvalidCredentialsForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersInvalidCredentialsForCompanyPassport("Mazen", "Mohamed", "m.mohamedlamasatech.com", "01234nhvhj56789")
                .isValidationMessageDisplayed("Invalid email format.");
    }

    @Test(priority = 16)
    public void verifyNextButtonDisabledWhenAllFieldsEmptyForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserLetAllFieldsEmptyForCompanyPassport()
                .isNextButtonDisabled();
    }

    @Test(priority = 17)
    public void verifyWhenUserEnterEmailAlreadyExistForCompanyPassport() {
       new Sign_Up_Page(driver)
               .verifyFunctionalityWhenUserEntersEmailAlreadyExist("Mazen", "Mohamed", "m.mohamed@lamasatech.com" )
               .isEmailAlreadyExistsErrorDisplayed();

    }

    @Test (priority = 18)
    public void verifyTheNextButtonFunctionalityWhenUserClearTheEmail() {
        new Sign_Up_Page(driver)
                .verifyTheNextButtonFunctionalityWhenUserClearTheEmail("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com")
                .isNextButtonDisabled();
    }

    @Test (priority = 19)
    public void verifyTheNextButtonFunctionalityWhenUserEnterInvalidPhoneNumberForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyTheNextButtonFunctionalityWhenUserEntersInvalidPhoneNumber("Mazen", "Mohamed", "012345jnubfurv6789")
                .isNextButtonDisabled();
    }

    @Test (priority = 20)
    public void verifyTheNextButtonFunctionalityWhenUserClearThePhoneNumber() {
        new Sign_Up_Page(driver)
                .verifyTheNextButtonFunctionalityWhenUserClearPhoneNumber("Mazen", "Mohamed", "0123456789")
                .isNextButtonDisabled();
    }

    // Test Case ------> For the next step of filling the form for company passport

    @Test(priority = 21)
    public void verifyRegistrationButtonEnabledAfterFillingCompanyNameOnSecondStep() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersNamesOnlyForCompanyPassport("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com", "0123456789", "Lamasatech")
                .isRegistrationButtonForCompanyDashboardDisabled();
    }

    @Test(priority = 22)
    public void verifyFunctionalityIfTheUserDoesNotChooseAnyServer() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityIfTheUserDoesNotChooseAnyServer("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com", "0123456789")
                .isNextButtonDisabled();
    }

    @Test(priority = 23)
    public void verifyNextButtonDisabledWithOnlyLastNameForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersLastNameOnlyForCompanyPassport("Mohamed")
                .isNextButtonDisabled();
    }

    @Test(priority = 24)
    public void verifyNextButtonDisabledWithOnlyEmailForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersEmailOnlyForCompanyPassport("m.mohamed+9820@lamasatech.com")
                .isNextButtonDisabled();
    }

    @Test(priority = 25)
    public void verifyNextButtonDisabledWithOnlyPhoneForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersPhoneOnlyForCompanyPassport("0123456789")
                .isNextButtonDisabled();
    }

    @Test(priority = 26)
    public void verifyNextButtonDisabledWithNamesOnlyForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersNamesOnlyForCompanyPassportFirstStep("Mazen", "Mohamed")
                .isNextButtonDisabled();
    }

    @Test(priority = 27)
    public void verifyNextButtonDisabledWithNamesAndEmailButNoPhoneForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersNamesAndEmailForCompanyPassport("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com")
                .isNextButtonDisabled();
    }

    @Test(priority = 28)
    public void verifyNextButtonDisabledWithNamesAndPhoneButNoEmailForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersNamesAndPhoneForCompanyPassport("Mazen", "Mohamed", "0123456789")
                .isNextButtonDisabled();
    }

    @Test(priority = 29)
    public void verifyFunctionalityWhenUserEntersValidPhoneNumberForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEnterValidCredentialsForCompanyPassport("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com", "+201234567890")
                .isNextButtonEnabled();
    }

    @Test(priority = 30)
    public void verifyNextButtonEnabledWhenUserEntersCharactersInPhoneNumberForCompanyPassport() {
        new Sign_Up_Page(driver)
                .verifyFunctionalityWhenUserEntersCharactersInPhoneNumberForCompanyPassport("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com", "abcdef")
                .isNextButtonEnabled();
    }

    @Test(priority = 31)
    public void verifyHeaderTitleOnCompanySignUpPage() {
        new Sign_Up_Page(driver)
                .navigateToCompanySignUpPage()
                .isHeaderTitleDisplayed("COMPANY ACCOUNT");
    }

    @Test(priority = 32)
    public void verifyUserCanNavigateToLoginFromCompanySignUpPage() {
        new Sign_Up_Page(driver)
                .clickLoginLinkOnCompanySignUpPage()
                .isUserNavigatedToLoginPage();
    }

    @Test(priority = 33)
    public void verifyRegistrationButtonDisabledWhenCompanyNameIsEmptyOnSecondStep() {
        new Sign_Up_Page(driver)
                .navigateToCompanySignUpSecondStep("Mazen", "Mohamed", "m.mohamed+9820@lamasatech.com", "0123456789")
                .isRegistrationButtonForCompanyDashboardDisabled();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            if (driver != null) {
                if (testBase == null) {
                    testBase = new Test_Base(driver);
                }
                testBase.closeBrowser();
                LocalDateTime endTime = LocalDateTime.now();
                testBase.logInfo("Test execution ended at: " + endTime);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Error during teardown: " + e.getMessage());
            e.printStackTrace();
            if (driver != null) {
                driver.quit();
            }
        }
    }
}