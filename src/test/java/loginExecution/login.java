package loginExecution;
import BaseTest.Test_Base;
import drivers.ChromeFactory;
import LoginPOM.Login_Page;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.PropertiesReader;
import java.time.LocalDateTime;

public class login {

   // Variables
    private WebDriver driver;
    private Test_Base testBase;


   // Configuration
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

    // Test Cases related to Log in Functionality

    @Feature("Login Functionality")
    @Story("Valid Login Functionality")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void VerifyLoginWithValidCredentials() {
        new Login_Page(driver)
                .Login(PropertiesReader.getProperty("validEmail"), PropertiesReader.getProperty("validPassword"))
                .isLoggedIn("Testing(UK) Testing(UK)");

    }

    @Feature("Login Functionality")
    @Story("Invalid Login Functionality")
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    public void VerifyLoginWithInvalidEmail() {
         new Login_Page(driver)
                  .Invalid_Login(PropertiesReader.getProperty("invalidEmail"),PropertiesReader.getProperty("validPassword"))
                  .isNotLoggedIn("The username or password is incorrect");
    }

    @Feature("Login Functionality")
    @Story("Invalid Login Functionality")
    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    public void VerifyLoginWithInvalidPassword() {
         new Login_Page(driver)
                  .Invalid_Login(PropertiesReader.getProperty("validEmail"),PropertiesReader.getProperty("invalidPassword"))
                  .isNotLoggedIn("The username or password is incorrect");

    }

    @Feature("Login Functionality")
    @Story("Invalid Login Functionality")
    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void VerifyLoginWithInvalidEmailAndInvalidPassword() {
         new Login_Page(driver)
                  .Invalid_Login(PropertiesReader.getProperty("invalidEmail"),PropertiesReader.getProperty("invalidPassword"))
                  .isNotLoggedIn("The username or password is incorrect");
    }

    @Feature("Login Functionality")
    @Story("Login With Empty Credentials")
    @Test(priority = 5)
    @Severity(SeverityLevel.CRITICAL)
    public void VerifyLoginWithEmptyCredentials() {
         new Login_Page(driver)
                  .EmptyFields("","")
                  .isLoginButtonDisabled();
    }

    // Test Cases related to Forgot Password Functionality
    @Feature("Forgot Password Functionality")
    @Story("Forgot Password Link Navigation")
    @Test(priority = 6)
    public void forgotPasswordLinkNavigation() {
         new Login_Page(driver)
                 .forgotPasswordLinkNavigation()
                 .isForgotPasswordPageDisplayed("Forgot Password");

    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Valid Email")
    @Test(priority = 7)
    public void forgotPasswordWithValidEmail() {
         new Login_Page(driver)
                  .forgotPasswordWithValidEmail("m.mohamed+2468@lamasatech.com")
                  .isErrorMessageDisplayed("Please check your email");

    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Invalid Email")
    @Test(priority = 8)
    public void forgotPasswordWithInvalidEmail() {
         new Login_Page(driver)
                   .forgotPasswordWithInvalidEmail("m.mohamed+2468lamasatech.com")
                   .isValidationMessageDisplayed("Invalid email address");

    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Empty Email")
    @Test(priority = 9)
    public void forgotPasswordFieldWithEmptyEmail() {
         new Login_Page(driver)
                 .forgotPasswordFieldWithEmptyEmail()
                 .isValidationMessageDisplayed("Email is required");

    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Phone Number")
    @Test(priority = 10)
    public void verifyThatThePhoneNumberFieldWillBeDisabledWhenTheEmailFieldCheckBoxIsChecked() {
          new Login_Page(driver)
                  .verifyThatThePhoneNumberFieldWillBeDisabledWhenTheEmailFieldCheckBoxIsChecked()
                  .isPhoneNumberRadioButtonDisabled();

    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Valid Phone Number")
    @Test(priority = 11)
    public void verifyFunctionalityWhenUserEnterValidPhoneNumber() {
         new Login_Page(driver)
                  .verifyFunctionalityWhenUserEnterValidPhoneNumber("224535463546456")
                  .isOtpMessageDisplayed("We sent you an SMS with a 6 digit code.");
    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Email Field Disabled")
    @Test(priority = 12)
    public void verifyThatTheEmailFieldWillBeDisabledWhenThePhoneNumberFieldCheckBoxIsChecked() {
        new Login_Page(driver)
                .verifyThatTheEmailFieldWillBeDisabledWhenThePhoneNumberFieldCheckBoxIsChecked()
                .isEmailRadioButtonDisabled();
    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Characters In Phone Number Field")
    @Test(priority = 13)
    public void verifyFunctionalityWhenUserEnterCharactersIntoThePhoneNumberField() {
        new Login_Page(driver)
                .verifyFunctionalityWhenUserEnterCharactersIntoThePhoneNumberField("abcdefghij")
                .isValidationMessageDisplayed("Phone number should contain only digits.");

    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Characters And Numbers In Phone Number Field")
    @Test(priority = 14)
    public void verifyFunctionalityWhenUserEnterCharactersWithNumbersIntoThePhoneNumberField() {
        new Login_Page(driver)
                .verifyFunctionalityWhenUserEnterCharactersWithNumbersIntoThePhoneNumberField("Mazen1234")
                .isValidationMessageDisplayed("Phone number should contain only digits.");
    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Non-Existent Phone Number")
    @Test(priority = 15)
    public void verifyFunctionalityWhenUserEnterPhoneNumberNotExist() {
         new Login_Page(driver)
                 .verifyFunctionalityWhenUserEnterPhoneNumberNotExist("012023543540")
                 .isNotLoggedIn("This email or phone doesn't exist");
    }

    @Feature("Forgot Password Functionality")
    @Story("Forgot Password With Empty Phone Number Field")
    @Test(priority = 16)
    public void verifyFunctionalityWhenUserLetPhoneNumberFieldEmpty() {
         new Login_Page(driver)
               .verifyFunctionalityWhenUserLetPhoneNumberFieldEmpty()
               .isValidationMessageDisplayed("Phone Number is required");

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