package tests;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Step;
import page.EngagementPage;
import page.LoginPage;
import utilities.ScreenshotUtil;

public class Task3Test extends BaseTest {

	@Test(dataProvider = "loginData", priority = 2)
	@Step("Do Login Test")
	public void loginValidation(String username, String password, boolean expectedResult)
			throws IOException, InterruptedException {
		LoginPage loginpage = new LoginPage(driver);
		System.out.println("UserName: " + username + "\r\nPass: " + password);
		loginpage.doLogin(username, password);
		boolean status = loginpage.validateLogin();
		if (status) {
			System.out.println("Login Successfully");
		} else {
			System.out.println("Login Failed");
		}
	}

	@DataProvider(name = "loginData")
	public Object[][] getData() {
		return new Object[][] { { "gregory.brown@v3coresalix.com", "radius123", true } };
	}

	@Test(dataProvider = "date-range", priority = 3)
	@Step("Select Date Range and validate")
	public void selectDateRange(String start, String end) {
		EngagementPage page = new EngagementPage(driver);
		page.clickEngagementTab();
		page.clickDatePickerAndSelectRange(start, end);
		ScreenshotUtil.attachAllureScreenshot(driver, "Selecteddaterange");
		page.validateRange(start, end);
	}

	@DataProvider(name = "date-range")
	public Object[][] getData2() {
		return new Object[][] { { "5", "25" } };
	}

}
