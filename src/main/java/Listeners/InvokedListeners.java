    package Listeners;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;
    import org.testng.*;
    import utils.AllureUtils;
    import utils.ScreenShotUtils;


    public class InvokedListeners implements IInvokedMethodListener , ITestListener {

        private static final Logger logger = LogManager.getLogger(InvokedListeners.class);

        @Override
        public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
            try {
                logger.info("Before invoking method: {}", method.getTestMethod().getMethodName());
                System.out.println("Before invoking method: " + method.getTestMethod().getMethodName());
            } catch (Exception e) {
                logger.error("Error in beforeInvocation: {}", e.getMessage());
                System.err.println("Error in beforeInvocation: " + e.getMessage());
            }

        }

        @Override
        public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
            try {
                logger.info("After invoking method: {}", method.getTestMethod().getMethodName());
                System.out.println("After invoking method: " + method.getTestMethod().getMethodName());
                ScreenShotUtils.captureScreenshot(null, method.getTestMethod().getMethodName() + ".png");
            } catch (Exception e) {
                logger.error("Error in afterInvocation: {}", e.getMessage());
                System.err.println("Error in afterInvocation: " + e.getMessage());
            }
        }

        @Override
        public void onTestStart(ITestResult result) {
            try {
                logger.info("Test started: {}", result.getMethod().getMethodName());
                System.out.println("Test started: " + result.getMethod().getMethodName());
                AllureUtils.clearAllureResults();
            } catch (Exception e) {
                logger.error("Error in onTestStart: {}", e.getMessage());
                System.err.println("Error in onTestStart: " + e.getMessage());
            }
        }
    }