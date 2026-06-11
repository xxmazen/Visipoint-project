package PassportPageExcution;

import BaseTest.Test_Base;
import LoginPOM.Login_Page;
import PassportPom.Passport_Page;
import drivers.ChromeFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class PassportPage {
    private WebDriver driver;
    private Test_Base testBase;

// Configuration

  @BeforeMethod (alwaysRun = true)
       public void setUp() {
            try {
                driver = new ChromeFactory()
                        .setupDriver();
                testBase = new Test_Base(driver);
                LocalDateTime startTime = LocalDateTime.now();
                testBase.logInfo("Test execution started at: " + startTime);
                testBase.getBaseUrl();
                new Login_Page(driver)
                        .Login("m.mohamed+2468@lamasatech.com","Mazen1234@@");
                testBase.logInfo("Successfully logged in to the application");
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
  @Test (priority = 1)
    public void VerifyUserCanNavigateToPassportPage() {
       new Passport_Page(driver)
               .isOnPassportPage("Testing(UK) Testing(UK)");
    }
  @Test (priority = 2)
    public void VerifyThatTheQuickPassIsDisplayed() {
        new Passport_Page(driver)
                .isQuickPassDisplayed("My QuickPass");
    }
  @Test (priority = 3)
    public void VerifyThatThereIsButtonsNamedMyEntryLogsAndExpectedVisits() {
        new Passport_Page(driver)
                .isThereIsButtonsNamedMyentrylogsAndExpectedvisits("My entry Logs","Expected visits");
    }
 @Test (priority = 4)
    public void VerifyThatTheUserCanNavigateToMyEntryLogsPage() {
       new Passport_Page(driver)
               .verifyFunctionalityWhenUserClickOnEditMyData()
               .isPencilButtonDisplayed();
    }
  @Test (priority = 5)
    public void VerifyThatTheSaveButtonIsDisabledWhenUserClearDataFromFirstNameField() {
        new Passport_Page(driver)
                .verifyFunctionalityWhenUserClickOnEditMyData()
                .verifySaveButtonFunctionalityWhenUserClearDataFromFirstNameField()
                .isSaveButtonDisabled();
    }
 // TearDown

    @AfterMethod (alwaysRun = true)
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


