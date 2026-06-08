package LoginPOM;

import BaseTest.Test_Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
public class Login_Page extends Test_Base {


    // Locators
    private final By Email = By.id("email");
    private final By Password = By.id("password");
    private final By LoginButton = By.xpath("//*[@type=\"submit\"]");
    private final By AlertMessage = By.cssSelector("[role=\"alert\"]");
    private final By ForgotPasswordLink = By.linkText("Forgot password?");
    private final By SendButton = By.cssSelector("[size=\"md\"]");
    private final By FullName = By.id("name");
    private final By ForgotPassword = By.cssSelector("[class=\"text-title\"]");
    private final By RightTitle = By.cssSelector("[class=\"content-title\"]");
    private final By PhoneNumberField = By.id("phone");
    private final By PhoneNumberRadioButton = By.xpath("(//*[@type=\"radio\"])[2]");
    private final By EmailRadioButton = By.xpath("(//*[@type=\"radio\"])[1]");
    private final By ValidationMessageForPhoneNumberField = By.cssSelector("[class=\"error-value\"]");
    private final By BVC = By.cssSelector("[class=\"text-subtitle\"]");

    // Constructor
    public Login_Page(WebDriver driver) {
        super(driver);
    }

    // Actions

    public Login_Page Login (String email, String password){
        try {
            enterText(Email, email);
            enterText(Password, password);
            click(LoginButton);
            waitForElementVisible(FullName, 5);
            logInfo("Login with valid credentials test passed successfully");
        } catch (Exception e) {
            logError("Error during test execution:" + e.getMessage());
        }
        return this;
    }

    public Login_Page Invalid_Login(String email, String password){
        try {
            enterText(Email, email);
            enterText(Password, password);
            click(LoginButton);
            waitForElementVisible(AlertMessage, 2);
        } catch (Exception e) {
            logError("Error during test execution:" + e.getMessage());
        }
        return this;
    }

    public Login_Page EmptyFields (String email, String password) {
        enterText(Email, email);
        enterText(Password, password);
        return this;
    }

    // Test cases related to forgot password page

    public Login_Page forgotPasswordLinkNavigation() {
        click(ForgotPasswordLink);
        return this;
    }

    public Login_Page forgotPasswordWithValidEmail(String validEmail) {
        click(ForgotPasswordLink);
        enterText(Email,validEmail);
        click(SendButton);
        return this;
    }

    public Login_Page forgotPasswordWithInvalidEmail(String invalidEmail) {
        click(ForgotPasswordLink);
        enterText(Email,invalidEmail);
        click(SendButton);
        return this;
    }

    public Login_Page forgotPasswordFieldWithEmptyEmail() {
        click(ForgotPasswordLink);
        enterText(Email,"");
        click(SendButton);
        return this;
    }

    public Login_Page verifyThatThePhoneNumberFieldWillBeDisabledWhenTheEmailFieldCheckBoxIsChecked() {
        click(ForgotPasswordLink);
        return this;
    }

    public Login_Page verifyFunctionalityWhenUserEnterValidPhoneNumber(String validPhoneNumber) {
        click(ForgotPasswordLink);
        jsClick(PhoneNumberRadioButton);
        enterText(PhoneNumberField,validPhoneNumber);
        click(SendButton);
        return this;
    }

    public Login_Page verifyThatTheEmailFieldWillBeDisabledWhenThePhoneNumberFieldCheckBoxIsChecked() {
        click(ForgotPasswordLink);
        jsClick(PhoneNumberRadioButton);
        return this;
    }

    public Login_Page verifyFunctionalityWhenUserEnterCharactersIntoThePhoneNumberField(String characters) {
        click(ForgotPasswordLink);
        jsClick(PhoneNumberRadioButton);
        enterText(PhoneNumberField, characters);
        click(SendButton);
        return this;
    }

    public Login_Page verifyFunctionalityWhenUserEnterCharactersWithNumbersIntoThePhoneNumberField(String charactersWithNumbers) {
        click(ForgotPasswordLink);
        jsClick(PhoneNumberRadioButton);
        enterText(PhoneNumberField, charactersWithNumbers);
        click(SendButton);
        return this;
    }

    public Login_Page verifyFunctionalityWhenUserEnterPhoneNumberNotExist(String phoneNumberNotExist) {
        click(ForgotPasswordLink);
        jsClick(PhoneNumberRadioButton);
        enterText(PhoneNumberField, phoneNumberNotExist);
        click(SendButton);
        waitForElementVisible(AlertMessage, 2);
        return this;
    }

    public Login_Page verifyFunctionalityWhenUserLetPhoneNumberFieldEmpty() {
        click(ForgotPasswordLink);
        jsClick(PhoneNumberRadioButton);
        enterText(PhoneNumberField, "");
        click(SendButton);
        return this;
    }

    // Validations

    public void isLoggedIn (String ExpectedResult) {
        Assert.assertEquals(driver.findElement(By.id("name")).getText(), ExpectedResult, "Should login successfully and navigate to the home page");
    }
    public void isNotLoggedIn (String ExpectedResult) {
        Assert.assertEquals(driver.findElement(AlertMessage).getText(), ExpectedResult, "Should not login and should display an error message indicating that the username or password is incorrect");
    }
    public void isLoginButtonDisabled () {
        Assert.assertTrue(driver.findElement(LoginButton).isDisplayed(), "Login button should be displayed on the login page");
    }
    public void isForgotPasswordPageDisplayed (String ExpectedResult) {
        Assert.assertEquals(driver.findElement(ForgotPassword).getText(),ExpectedResult, "Should navigate to the forgot password page and should display the forgot password form");
    }
    public void isErrorMessageDisplayed (String ExpectedResult) {
        Assert.assertEquals(getElementText(RightTitle),ExpectedResult , "Should display a message indicating that an email has been sent to the user with instructions on how to reset their password");
    }
    public void isValidationMessageDisplayed(String ExpectedResult) {
        Assert.assertEquals(getElementText(ValidationMessageForPhoneNumberField), ExpectedResult, "Should display an error message indicating that the email address is invalid");
    }
    public void isPhoneNumberRadioButtonDisabled () {
        Assert.assertFalse(driver.findElement(PhoneNumberRadioButton).isEnabled(), "Phone number radio button should be disabled on the forgot password page");
    }
    public void isEmailRadioButtonDisabled () {
        Assert.assertFalse(driver.findElement(EmailRadioButton).isEnabled(), "Email radio button should be disabled on the forgot password page");
    }
    public void isOtpMessageDisplayed(String ExpectedResult) {
        Assert.assertEquals(getElementText(BVC), ExpectedResult, "Should display a message indicating that an SMS with a 6 digit code has been sent to the user");
     }
}


