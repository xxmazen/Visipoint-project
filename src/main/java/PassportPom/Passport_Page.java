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


 // Constructor
    public Passport_Page(WebDriver driver) {
        super(driver);
    }

 // Actions


 // Validations
    public void isOnPassportPage(String ExpectedTitle) {
        Assert.assertEquals(driver.findElement(fullName).getText(), ExpectedTitle, "User is not on the Passport page");
    }
    public void isQuickPassDisplayed(String ExpectedTitle) {
        Assert.assertEquals(driver.findElement(quickPass).getText(), ExpectedTitle, "Quick Pass is not displayed on the Passport page");
    }
    public void isThereIsButtonsNamedMyentrylogsAndExpectedvisits() {
        Assert.assertEquals(driver.findElement(myEntryLogsButton).getText(), "My entry Logs", "My Entry Logs button is not displayed on the Passport page");
        Assert.assertEquals(driver.findElement(expectedVisitsButton).getText(), "Expected Visits", "Expected Visits button is not displayed on the Passport page");
    }
}
