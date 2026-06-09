package Listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListeners implements ITestListener {

    private static final Logger logger = LogManager.getLogger(CustomListeners.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("STARTED  : {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("PASSED   : {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("FAILED   : {} — {}", result.getName(), result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("SKIPPED  : {}", result.getName());
    }
}
