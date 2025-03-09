package tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.qameta.allure.Step;
import utilities.BrowserFactory;
import utilities.ScreenshotUtil;

public class BaseTest {
	protected WebDriver driver;
	 public String browserName;

	@BeforeClass
	@Parameters("browser")
	public void setup(@Optional("chrome") String browser) {
		this.browserName = browser; // Store browser name for dynamic screenshot names
		driver = BrowserFactory.startBrowser(browser);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		ScreenshotUtil.attachAllureScreenshot(driver, "Browser launched" + browserName);
	}

	@Test
	@Step("Navigate to url")
	public void Goto() {
		driver.get("https://salixv3core.radiusdirect.net/user");
		ScreenshotUtil.attachAllureScreenshot(driver, "Hit the URL" + browserName);
	}

	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
