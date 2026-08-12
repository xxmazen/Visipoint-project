package utils;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.file.Path;
import static java.nio.file.Files.newInputStream;


public class AllureUtils {

     // Method to clear Allure results folder before each test execution

     public static void clearAllureResults() {
         try {
             FileUtils.deleteQuietly(new File("allureTest-output/allure-results"));
         }
            catch (Exception e) {
                System.err.println("Error clearing Allure results: " + e.getMessage());
            }
     }

     public static void attachScreenshotToAllure(String screenshotName, String screenshotPath) {
         try {
             Allure.addAttachment(screenshotName, newInputStream(Path.of(screenshotPath)));
         } catch (Exception e) {
             System.err.println("Error attaching screenshot to Allure: " + e.getMessage());
         }
     }
}
