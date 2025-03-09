package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class ScreenshotUtil {
	private static final String folderName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	private static final String SCREENSHOT_PATH = "screenshots/"+ folderName+ "/";
    // Method to capture screenshot and save it with auto-generated name
    public static String captureScreenshot(WebDriver driver, String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Generate file name with timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = testName + "_" + timestamp + ".png";
        String screenshotPath = SCREENSHOT_PATH + screenshotName;

        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath));
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }

        return screenshotPath;
    }

    // Attach Screenshot to Allure Report
    @Attachment(value = "{testName} Screenshot", type = "image/png")
    public static byte[] attachScreenshotToAllure(WebDriver driver, String testName) {
        System.out.println("Attaching screenshot to Allure report...");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    
    public static void attachAllureScreenshot(WebDriver driver, String name){
    	try {
			Allure.addAttachment(name, new FileInputStream(captureScreenshot(driver, name)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
