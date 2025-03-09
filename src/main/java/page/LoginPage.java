package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.WaitHelper;

public class LoginPage {
	private WebDriver driver;
	private WaitHelper wait;

	@FindBy(name = "email")
	private WebElement emailTextBox;

	@FindBy(name = "password")
	private WebElement passwordTextBox;

	@FindBy(xpath = "//button[text()= 'Log in']")
	private WebElement loginButton;
	
	@FindBy (xpath = "(//a[@title=\"Logout\"])[2]")
	private WebElement logoutButton;
	
	@FindBy (xpath = "//div[@id=\"logoutconfirm\"]//a[@title=\"Yes\"]")
	private WebElement logoutYes;

	@FindBy(xpath = "//a[@title = 'Home']")
	private WebElement homeButton;
	

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WaitHelper wait = new WaitHelper(driver);
		this.wait = wait;
	}

	public void doLogin(String username, String password) {
		wait.WaitForElementToBeVisible(emailTextBox, 20);
		emailTextBox.sendKeys(username);
		passwordTextBox.sendKeys(password);
		loginButton.click();
	}
	public boolean validateLogin() {
		try {
			wait.WaitForElementToBeVisible(homeButton, 20);
			return true;
		} catch (Exception e) {
			emailTextBox.clear();
			passwordTextBox.clear();
			return false;
		}
		
	}
	
	public void logout() {
		
		if(validateLogin()) {
			wait.WaitForElementToBeClickable(logoutButton, 20);
			logoutButton.click();
			wait.WaitForElementToBeVisible(logoutYes, 20);
			logoutYes.click();
		}
	}

}
