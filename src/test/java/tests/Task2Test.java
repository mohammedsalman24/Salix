package tests;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Step;
import page.CreatePage;
import page.LoginPage;
import utilities.ScreenshotUtil;

public class Task2Test extends BaseTest {

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
			ScreenshotUtil.attachAllureScreenshot(driver, "Login successfully");
		} else {
			System.out.println("Login Failed");
			ScreenshotUtil.attachAllureScreenshot(driver, "Login Failed");
		}
	}

	@DataProvider(name = "loginData")
	public Object[][] getData() {
		return new Object[][] { { "gregory.brown@v3coresalix.com", "radius123", true } };
	}

	@Test(dataProvider = "product", priority = 3)
	@Step("Task-2: Check whether the product is available or not")
	public void checkProducts(String productName, boolean returnBack) {
		CreatePage createpage = new CreatePage(driver);
		createpage.clickCreateTab();
		createpage.selectProduct(productName);
		ScreenshotUtil.attachAllureScreenshot(driver, productName);
		if (returnBack)
			createpage.backFromSelectEnhancementType();
	}

	@DataProvider(name = "product")
	public Object[][] getData1() {
		return new Object[][] {
			{ "TRULANCE®" , true}, 
			{ "XIFAXAN-HE®", true },
			{ "XIFAXAN-IBS-D®", true} 
		};
	}

	@Test(dataProvider = "date-icon-is-diabled", priority = 4)
	@Step("Check the date is disabled for the previous date")
	public void checkDateIsDisable(String productName, String enhancementType, String multipleTopic, String topic,
			String date, String timezone, String time) {
		CreatePage createpage = new CreatePage(driver);
		checkProducts(productName, false);
		createpage.selectEnhancementType(enhancementType);
		createpage.validateMultipleTopic();
		createpage.selectMultiTopic(multipleTopic);
		createpage.validateTopic();
		createpage.selectTopic(topic);
		createpage.vaildateDateTime();
		if (createpage.validatePreviousDateIsDisbaled()) {
			System.out.println("Validate pass - prior to the current date cannot be selected");
			ScreenshotUtil.attachAllureScreenshot(driver, "Dissableddatecannotbeselected");
		} else {
			System.out.println("Validate fail - prior to the current date cannot be selected");
			ScreenshotUtil.attachAllureScreenshot(driver, "Abletoselectdisableddate");
		}
		createpage.selectDate(date);
		createpage.selectTimeZone(timezone);
		createpage.selectTime(time);
		createpage.clickContinue();
		ScreenshotUtil.attachAllureScreenshot(driver, "ContinuebuttonClicked");
	}

	@DataProvider(name = "date-icon-is-diabled")
	public Object[][] getData2() {
		return new Object[][] { { "TRULANCE®", "Virtual In Office", "No",
				"Short-Term Therapy With Lasting Relief for Patients With IBS-D (XIFI.0024.USA.20)", "25", "PT",
				"7:30 AM" } };
	}
}
