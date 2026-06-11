package PassportPom;

import BaseTest.Test_Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Passport_Page extends Test_Base {

 // Locators
    private final By fullName = By.id("name");
    private final By quickPass = By.xpath("(//*[@class=\"qr-title\"])[1]");
    private final By myEntryLogsButton = By.xpath("(//*[@type=\"button\"])[3]");
    private final By expectedVisitsButton = By.xpath("(//*[@type=\"button\"])[4]");
    private final By threeDotsButton = By.xpath("(//*[@type=\"button\"])[2]");
    private final By editMyDataButton = By.xpath("(//*[@role=\"menuitem\"])[1]");
    private final By pencilButton = By.xpath("(//*[@role=\"button\"])[2]");
    private final By firstNameField = By.xpath("(//*[@type=\"text\"])[5]");
    private final By saveButton = By.cssSelector("[size=\"md\"]");


 // Constructor
    public Passport_Page(WebDriver driver) {
        super(driver);
    }

 // Actions

    // Test cases related to edit my data

    public Passport_Page verifyFunctionalityWhenUserClickOnEditMyData() {
         click(threeDotsButton);
         click(editMyDataButton);
        return this;
    }
    public Passport_Page verifySaveButtonFunctionalityWhenUserClearDataFromFirstNameField() {
        click(pencilButton);
        Clear(firstNameField);
        return this;
    }
 // Validations
    public void isOnPassportPage(String ExpectedTitle) {
        Assert.assertEquals(driver.findElement(fullName).getText(), ExpectedTitle, "User is not on the Passport page");
    }
    public void isQuickPassDisplayed(String ExpectedTitle) {
        Assert.assertEquals(driver.findElement(quickPass).getText(), ExpectedTitle, "Quick Pass is not displayed on the Passport page");
    }
    public void isThereIsButtonsNamedMyentrylogsAndExpectedvisits(String ExpectedTitle1, String ExpectedTitle2) {
        Assert.assertEquals(driver.findElement(myEntryLogsButton).getText(), ExpectedTitle1, "My Entry Logs button is not displayed on the Passport page");
        Assert.assertEquals(driver.findElement(expectedVisitsButton).getText(), ExpectedTitle2, "Expected Visits button is not displayed on the Passport page");
    }
    public void isPencilButtonDisplayed() {
        Assert.assertTrue(driver.findElement(pencilButton).isDisplayed(), "Pencil button is not displayed on the Passport page");
    }
    public void isSaveButtonDisabled() {
        Assert.assertFalse(driver.findElement(saveButton).isEnabled(), "Save button is not disabled when first name field is empty");
    }
}
