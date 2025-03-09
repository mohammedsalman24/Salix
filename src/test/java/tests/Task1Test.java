package tests;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import page.LoginPage;
import utilities.ScreenshotUtil;

public class Task1Test extends BaseTest {

	@Test(dataProvider = "loginData")
	@Step("Task-1: Perform Login Test")
	@Description("Validates login with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
	public void loginValidation(String username, String password, boolean expectedResult)
			throws IOException, InterruptedException {
		ScreenshotUtil.attachAllureScreenshot(driver, ""+ browserName);
		System.out.println("UserName: " + username + "\r\nPass: " + password);
		LoginPage page = new LoginPage(driver);
		page.doLogin(username, password);
		boolean status = page.validateLogin();
		ScreenshotUtil.attachAllureScreenshot(driver, "Validating the login page" + browserName);
		if (status) {
			System.out.println("Login Successfully");
		} else {
			System.out.println("Login Failed");
		}
		page.logout();
		ScreenshotUtil.attachAllureScreenshot(driver, "logout page" + browserName);	
	}

	@DataProvider(name = "loginData")
	public Object[][] getData() {
		return new Object[][] { { "gregory.brown@v3coresalix.com", "radius123", true },
				{ "gregory.brown@v3coresalix.com", "validPass", false }, { "validUser", "radius123", false },
		};
	}

}
