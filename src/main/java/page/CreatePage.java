package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utilities.Base;
import utilities.WaitHelper;

public class CreatePage {
	private WebDriver driver;
	private WaitHelper wait;
	private Base base = new Base();

	@FindBy(xpath = "//li/a[text()='Create']")
	private WebElement createButton;

	@FindBy(xpath = "//div[@class=\"tab-pane active in\" and @id=\"producttype\"]")
	private WebElement productSection;

	@FindBy(xpath = "//a[@id='dtlback']")
	private WebElement backButton;

	@FindBy(xpath = "//a[@id='dtlback']// following-sibling::a[text() = 'Next ']")
	private WebElement nextButton;
	
	@FindBy (xpath = "//h3[text()='Select Multi Topic']")
	private WebElement multipleTopic;
//	@FindBy (xpath = "//div[@id=\"selectmultitype\"]//input[@value=\"No\"]")
//	private WebElement selectmultiasNo;
	@FindBy (xpath = "//h3[text()='Select Topic']")
	private WebElement topic;
	@FindBy (xpath = "//td[@class=\"today day\"] //preceding-sibling::td[1]")
	private WebElement previousDate;
	
	@FindBy (xpath = "//h3[text()='Select Date & Time']")
	private WebElement dateTime;
	
	@FindBy (name="timezone")
	private WebElement timezone;
	
	@FindBy (xpath = "//a[text()='Continue']")
	private WebElement continuebtn;
	
	@FindBy (xpath = "//span[text() = 'Primary']")
	private WebElement primaryBtn;
	
	
	public CreatePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WaitHelper wait = new WaitHelper(driver);
		this.wait = wait;
	}

	public void clickCreateTab() {
		wait.WaitForElementToBeVisible(createButton, 30);
		createButton.click();
	}

	public void selectProduct(String productName) {
		try {
			String dXpath = base.dynamicXpath("(//span[contains (text() ,'%s')])[1]", productName);
			WebElement products = driver.findElement(By.xpath(dXpath));
			Thread.sleep(2000);
			wait.WaitForElementToBeVisible(products, 20);
//			products.click();
			base.jsClick(driver, products);
			System.out.println("Product is Selected: " + productName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validateProductNextPage() {
		boolean status = false;
		try {
			wait.WaitForElementToBeVisible(productSection, 20);
			status = productSection.isDisplayed();
		} catch (Exception e) {
			status = false;
		}
		if (status)
			System.out.println("Product Next Page Validation: true");
		else
			System.out.println("Product Next Page Validation: false");
		return status;
	}

	public void backFromSelectEnhancementType() {
		try {
			if (validateProductNextPage()) {
				try {
					wait.WaitForElementToBeClickable(backButton, 20);
					base.scrollElement(driver, backButton);
					backButton.click();
				} catch (Exception e) {
				}
				System.out.println("Back from Select Enhancement Type to selec product");
			} else {
				System.out.println("Failed Back from Select Enhancement Type to selec product");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectEnhancementType(String enhancementType) {
		String enhancementtype = base.dynamicXpath("//li[normalize-space(text())= \"%s\"] //ancestor:: div[@class=\"row outrow\"]", enhancementType);
		WebElement enhancementElement = driver.findElement(By.xpath(enhancementtype));
		wait.WaitForElementToBeClickable(enhancementElement, 10);
		enhancementElement.click();
		System.out.println("click Enhancement type: "+enhancementType);
	}

	public boolean validateMultipleTopic(){
		try {
			wait.WaitForElementToBeVisible(multipleTopic, 20);
			System.out.println("validate multiple menu: pass");
			return true;
		} catch (Exception e) {
			System.out.println("validate multiple menu: fail");
			return false;
		}
	}
	
	public void selectMultiTopic(String options) {
		String xpathStr = base.dynamicXpath("//div[@id=\"selectmultitype\"]//input[@value=\"%s\"]", options);
		WebElement element = driver.findElement(By.xpath(xpathStr));
		try {
			wait.WaitForElementToBeVisible(element, 10);
		} catch (Exception e) {
		}
		element.click();
		System.out.println("click multiple topic type: "+options);
	}

	public boolean validateTopic() {
		try {
			wait.WaitForElementToBeVisible(topic, 20);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void selectTopic(String topic) {
		String xpathstr = base.dynamicXpath("//span[text()=\"%s\"]", topic);
		WebElement element = driver.findElement(By.xpath(xpathstr));
		try {
			wait.WaitForElementToBeVisible(element, 10);
		} catch (Exception e) {
		}
		element.click();
		System.out.println("click topics: "+topic);
	}

	public boolean vaildateDateTime(){
		try {
			wait.WaitForElementToBeVisible(dateTime, 20);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validatePreviousDateIsDisbaled() {
		if(previousDate.getAttribute("class").toLowerCase().equals("disabled day")) {
			System.out.println("Previous Date is Disabled: "+previousDate.isEnabled());
			return true;
		}
		else return false;
	}

	public void selectDate(String date) {
		String xpath = base.dynamicXpath("//div[@id=\"date-time\"]//td[contains(@class,'day') and not(contains(@class, 'disabled')) and text()='%s']", date);
		WebElement element = driver.findElement(By.xpath(xpath));
		try {
			wait.WaitForElementToBeClickable(element, 5);
		} catch (Exception e) {
		}
		base.scrollElement(driver, element);
		element.click();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		element  = driver.findElement(By.xpath(base.dynamicXpath("//div[@id=\"date-time\"]//td[contains(@class,'day') and not(contains(@class, 'disabled')) and text()='%s']", date)));
		if(element.getAttribute("class").toLowerCase().contains("active")) {
			System.out.println("Date selected: "+date);
		}else {
			System.out.println("Failed to Date selected: "+date);
		}
	}
	
	public void selectTimeZone(String timeZone){
		Select s = new Select(timezone);
		s.selectByVisibleText(timeZone);
	}

	public void selectTime(String time) {
		String xpath = base.dynamicXpath("//label[@class=\"labelavl\"]//span[text()='%s']", time);
		WebElement  element = driver.findElement(By.xpath(xpath));
		try {
			wait.WaitForElementToBeClickable(element, 5);
		} catch (Exception e) {
		}
		element.click();
	}
	
	public void clickContinue(){
		try {
			continuebtn.click();
			Thread.sleep(5000);
			wait.WaitForElementToBeVisible(primaryBtn, 10);
			System.out.println("Click Continue Successfully");
		} catch (Exception e) {
			System.out.println("Click Continue Failed");
		}
	}

}