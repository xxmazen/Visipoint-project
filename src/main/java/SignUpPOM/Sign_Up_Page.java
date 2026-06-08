package SignUpPOM;

import BaseTest.Test_Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Objects;

public class Sign_Up_Page extends Test_Base {


    // Locators  related to Visipoint Sign Up Page (Visipoint Passport)

    By signUPButton = By.cssSelector("[class=\"register-redirect\"]");
    By visipointPassportButton = By.xpath("(//*[@class=\"icon-wrapper\"])[1]");
    By firstName = By.id("first_name");
    By lastName = By.id("last_name");
    By email = By.id("email");
    By phoneNumber = By.id("phone");
    By registrationButton = By.cssSelector("[type=\"submit\"]");
    By loginButton = By.xpath("//*[text()='LOGIN']");
    By headerTitle = By.cssSelector("[class=\"header-title\"]");
    By errorMessage = By.cssSelector("[class=\"error-value\"]");
    By nextButton = By.xpath("(//*[@type=\"button\"])[2]");

    // Locator related to the next step of filling the form for company passport

    By companyName = By.xpath("(//*[@aria-required=\"true\"])[5]");
    By companyDashboardButton = By.xpath("(//*[@class=\"icon-wrapper\"])[2]");
    By registrationButtonForCompanyDashboard = By.xpath("(//*[@type=\"button\"])[3]");


    // Constructor
    public Sign_Up_Page(WebDriver driver) {
        super(driver);
    }


    // Test Case ------> Related to Visipoint Sign Up Page (Visipoint Passport)

    public Sign_Up_Page navigateToVisipointSignUpPage() {
        try {
            click(signUPButton);
            click(visipointPassportButton);
        } catch (Exception e) {
            logError("Error during test execution:" + e.getMessage());
        }
        return this;
    }

    public Sign_Up_Page verifyFunctionalityWhenUserEntersValidEmail(String FirstName, String LastName, String ValidEmail) {
        try {
            click(signUPButton);
            click(visipointPassportButton);
            enterText(firstName, FirstName);
            enterText(lastName, LastName);
            enterText(email, ValidEmail);
        } catch (Exception e) {
            logError("Error during test execution:" + e.getMessage());
        }
        return this;

    }

    public Sign_Up_Page verifyFunctionalityWhenUserEntersInvalidEmail(String FirstName, String LastName, String InValidEmail) {
        try {
            click(signUPButton);
            click(visipointPassportButton);
            enterText(firstName, FirstName);
            enterText(lastName, LastName);
            enterText(email, InValidEmail);
        } catch (Exception e) {
            logError("Error during the test execution:" + e.getMessage());
        }
        return this;

    }
    public Sign_Up_Page verifyFunctionalityWhenUserEnterCharactersIntoPhoneNumber(String FirstName, String LastName, String mailAlreadyExist) {
        try {
            click(signUPButton);
            click(visipointPassportButton);
            enterText(firstName, FirstName);
            enterText(lastName, LastName);
            enterText(email, mailAlreadyExist);
            click(registrationButton);
        } catch (Exception e) {
            logError("Error during the test execution:" + e.getMessage());
        }
        return this;
    }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersFirstNameOnly(String FirstName) {
       try {
           click(signUPButton);
           click(visipointPassportButton);
           enterText(firstName, FirstName);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersEmailOnly(String Email) {
       try {
           click(signUPButton);
           click(visipointPassportButton);
           enterText(email, Email);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersNamesOnly(String FirstName, String LastName) {
       try {
           click(signUPButton);
           click(visipointPassportButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersValidPhoneNumber(String FirstName, String LastName, String Email, String PhoneNumber) {
       try {
           click(signUPButton);
           click(visipointPassportButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
           enterText(email, Email);
           enterText(phoneNumber, PhoneNumber);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   // Test Case ------> Fill valid fields with characters in phone number
   public Sign_Up_Page verifyFunctionalityWhenUserEntersCharactersInPhoneNumber(String FirstName, String LastName, String Email, String InvalidPhone) {
       try {
           click(signUPButton);
           click(visipointPassportButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
           enterText(email, Email);
           enterText(phoneNumber, InvalidPhone);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }


   public Sign_Up_Page clickLoginLinkOnSignUpPage() {
       try {
           click(signUPButton);
           click(visipointPassportButton);
           click(loginButton);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }
    // Test Case ------> Related to Visipoint Sign Up Page (Company Dashboard Passport)

   public Sign_Up_Page navigateToCompanySignUpPage() {
       try {
           click(signUPButton);
           click(companyDashboardButton);

       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }
   public Sign_Up_Page verifyFunctionalityWhenUserEnterValidCredentialsForCompanyPassport(String FirstName, String LastName, String ValidEmail, String ValidPhone) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
           enterText(email, ValidEmail);
           enterText(phoneNumber, ValidPhone);
           waitForElementClickable(nextButton,1);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }
   public Sign_Up_Page verifyFunctionalityWhenUserEntersInvalidCredentialsForCompanyPassport(String FirstName, String LastName, String InValidEmail, String InvalidPhone) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
           enterText(email, InValidEmail);
           enterText(phoneNumber, InvalidPhone);
       } catch (Exception e) {
           logError("Error during the test execution:" + e.getMessage());
       }
       return this;
   }
   public Sign_Up_Page verifyFunctionalityWhenUserLetAllFieldsEmptyForCompanyPassport() {
       try {
           click(signUPButton);
           click(companyDashboardButton);
       } catch (Exception e) {
           logError("Error during the test execution:" + e.getMessage());
       }
       return this;
   }
    public Sign_Up_Page verifyFunctionalityWhenUserEntersEmailAlreadyExist(String FirstName, String LastName , String existEmail) {
        try {
            click(signUPButton);
            click(companyDashboardButton);
            enterText(firstName, FirstName);
            enterText(lastName, LastName);
            enterText(email, existEmail);
        } catch (Exception e) {
            logError("Error during test execution:" + e.getMessage());
        }
        return this;
    }
    public Sign_Up_Page verifyTheNextButtonFunctionalityWhenUserClearTheEmail(String FirstName, String LastName , String ValidEmail) {
        try {
            click(signUPButton);
            click(companyDashboardButton);
            enterText(firstName, FirstName);
            enterText(lastName, LastName);
            enterText(email, ValidEmail);
            Clear(email);
        } catch (Exception e) {
            logError("Error during test execution:" + e.getMessage());
        }
        return this;
    }
    public Sign_Up_Page verifyTheNextButtonFunctionalityWhenUserEntersInvalidPhoneNumber(String FirstName, String LastName , String InvalidPhone) {
        try {
            click(signUPButton);
            click(companyDashboardButton);
            enterText(firstName, FirstName);
            enterText(lastName, LastName);
            enterText(phoneNumber, InvalidPhone);
        } catch (Exception e) {
            logError("Error during test execution:" + e.getMessage());
        }
        return this;
    }
    public Sign_Up_Page verifyTheNextButtonFunctionalityWhenUserClearPhoneNumber(String FirstName, String LastName , String ValidPhone) {
        try {
            click(signUPButton);
            click(companyDashboardButton);
            enterText(firstName, FirstName);
            enterText(lastName, LastName);
            enterText(phoneNumber, ValidPhone);
            Clear(phoneNumber);
        } catch (Exception e) {
            logError("Error during test execution:" + e.getMessage());
        }
        return this;
    }
  // Test Case ------> For the next step of filling the form for company passport

  public Sign_Up_Page verifyFunctionalityWhenUserEntersNamesOnlyForCompanyPassport(String FirstName, String LastName , String ValidEmail, String ValidPhone,String CompanyName) {
       try {
           verifyFunctionalityWhenUserEnterValidCredentialsForCompanyPassport(FirstName, LastName, ValidEmail, ValidPhone);
           click(nextButton);
           enterText(companyName, CompanyName);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page navigateToCompanySignUpSecondStep(String FirstName, String LastName, String ValidEmail, String ValidPhone) {
       try {
           verifyFunctionalityWhenUserEnterValidCredentialsForCompanyPassport(FirstName, LastName, ValidEmail, ValidPhone);
           click(nextButton);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityIfTheUserDoesNotChooseAnyServer(String FirstName, String LastName, String existEmail, String ValidPhone) {
       try {
           verifyFunctionalityWhenUserEnterValidCredentialsForCompanyPassport(FirstName, LastName, existEmail, ValidPhone);
           click(nextButton);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }
 // Continue filling the form for company passport with different scenarios
   public Sign_Up_Page verifyFunctionalityWhenUserEntersFirstNameOnlyForCompanyPassport(String FirstName) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(firstName, FirstName);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersLastNameOnlyForCompanyPassport(String LastName) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(lastName, LastName);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersEmailOnlyForCompanyPassport(String Email) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(email, Email);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersPhoneOnlyForCompanyPassport(String Phone) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(phoneNumber, Phone);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersNamesOnlyForCompanyPassportFirstStep(String FirstName, String LastName) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersNamesAndEmailForCompanyPassport(String FirstName, String LastName, String Email) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
           enterText(email, Email);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersNamesAndPhoneForCompanyPassport(String FirstName, String LastName, String Phone) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
           enterText(phoneNumber, Phone);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page verifyFunctionalityWhenUserEntersCharactersInPhoneNumberForCompanyPassport(String FirstName, String LastName, String Email, String InvalidPhone) {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           enterText(firstName, FirstName);
           enterText(lastName, LastName);
           enterText(email, Email);
           enterText(phoneNumber, InvalidPhone);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   public Sign_Up_Page clickLoginLinkOnCompanySignUpPage() {
       try {
           click(signUPButton);
           click(companyDashboardButton);
           click(loginButton);
       } catch (Exception e) {
           logError("Error during test execution:" + e.getMessage());
       }
       return this;
   }

   // Validations
   public void isUserNavigateToSignUpPage(String expectedUrlFragment){
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains(expectedUrlFragment),
                "User is not navigated to the Sign Up page.");
   }
   public void isNextButtonEnabled(){
        Assert.assertTrue(isElementEnabled(nextButton), "Next button is not enabled");
   }
   public void isNextButtonDisabled(){
        waitForElementClickable(nextButton, DEFAULT_WAIT_TIME);
        Assert.assertFalse(isElementEnabled(nextButton), "Next button should be disabled but is enabled");
   }
   public void isRegistrationButtonEnabled(){
        Assert.assertTrue(isElementEnabled(registrationButton), "Registration button is not enabled");
   }
   public void isRegistrationButtonDisabled(){
        Assert.assertFalse(isElementEnabled(registrationButton), "Registration button should be disabled but is enabled");
   }
   public void isRegistrationButtonForCompanyDashboardEnabled(){
        Assert.assertTrue(isElementEnabled(registrationButtonForCompanyDashboard), "Registration button for company dashboard is not enabled");
   }
    public void isRegistrationButtonForCompanyDashboardDisabled(){
          Assert.assertFalse(isElementEnabled(registrationButtonForCompanyDashboard), "Registration button for company dashboard should be disabled but is enabled");
    }
   public void isValidationMessageDisplayed(String expectedMessage){
         waitForElementVisible(errorMessage, DEFAULT_WAIT_TIME);
         Assert.assertEquals(driver.findElement(errorMessage).getText(), expectedMessage, "Validation message text does not match expected");
   }
   public void isHeaderTitleDisplayed(String expectedTitle){
        waitForElementVisible(headerTitle,DEFAULT_WAIT_TIME);
        Assert.assertEquals(getElementText(headerTitle), expectedTitle, "Header title does not match expected");
   }
   public void isEmailAlreadyExistsErrorDisplayed(){
        waitForElementVisible(errorMessage,DEFAULT_WAIT_TIME);
        Assert.assertEquals(getElementText(errorMessage), "Email address already exists, register with a different one or");
   }
   public void isUserNavigatedToLoginPage(){
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/login"), "User is not navigated to the login page");
   }
}
