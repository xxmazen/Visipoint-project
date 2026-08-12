    package utils;
    import org.apache.commons.io.FileUtils;
    import org.openqa.selenium.OutputType;
    import org.openqa.selenium.TakesScreenshot;
    import org.openqa.selenium.WebDriver;
    import java.io.File;

    public class ScreenShotUtils {

        public static void captureScreenshot(WebDriver driver, String screenshotName) {

            try {
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File dest = new File("allureTest-output/allure-results/" + screenshotName + ".png");
                FileUtils.copyFile(src, dest);
                AllureUtils.attachScreenshotToAllure(screenshotName, dest.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Error capturing screenshot: " + e.getMessage());
            }
        }
    }
