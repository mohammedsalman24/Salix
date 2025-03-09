package page;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Base;
import utilities.WaitHelper;

public class EngagementPage {
	private WebDriver driver;
	private WaitHelper wait;
	private Base base = new Base();
	
	enum Months {
	    Jan(1),
	    Feb(2),
	    Mar(3),
	    Apr(4),
	    May(5),
	    Jun(6),
	    Jul(7),
	    Aug(8),
	    Sep(9),
	    Oct(10),
	    Nov(11),
	    Dec(12);

	    private final int index;

	    // Constructor to assign values
	    Months(int index) {
	        this.index = index;
	    }

	    // Getter method to retrieve the index
	    public int getIndex() {
	        return index;
	    }
	}
	
	@FindBy (xpath = "//a[@title=\"Engagements\"]")
	private WebElement engagementsTab;
	@FindBy (xpath = "//span[@class='mydrpicon icon-mydrpcalendar']")
	private WebElement datePicker;
	@FindBy (xpath = "//p[@class=\"no-records\"]")
	private WebElement noData;
	

	public EngagementPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		WaitHelper wait = new WaitHelper(driver);
		this.wait = wait;
	}
	
	public void clickEngagementTab(){
		try {
			wait.WaitForElementToBeClickable(engagementsTab, 5);
			engagementsTab.click();
			System.out.println("Check No Engagement Data is Displayed: " + noData.isDisplayed());
			wait.WaitForElementToBeVisible(noData, 5);
			System.out.println("Validation Pass Engagement tab");
		} catch (Exception e) {
			System.out.println("Validation fail Engagement tab");
		}
	}
	
	public void clickDatePickerAndSelectRange(String start, String end) {
		datePicker.click();
		String startXpath = base.dynamicXpath("//div[@class=\"datevalue currmonth\"]//span[text()='%s']", start);
		String endXpath = base.dynamicXpath("//div[@class=\"datevalue currmonth\"]//span[text()='%s']", end);
		WebElement startElement = driver.findElement(By.xpath(startXpath));
		WebElement endElement = driver.findElement(By.xpath(endXpath));
		base.actionClickMoveTo(driver, startElement, endElement);
		System.out.println("Completed");
	}
	
	public boolean checkNoDataIsAvailable(){
		try {
			wait.waitForElementToHidden(noData, 10);
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
	public void validateRange(String startDate, String endDate) {
		if(checkNoDataIsAvailable()) {
			System.out.println("No Engagement Available");
			return;
		}
		datePicker.click();
		List<WebElement> header = driver.findElements(By.xpath("//my-date-range-picker//table[@class='header']//div[contains(@style, 'float')]//div[2]/button"));
		String year = header.get(1).getText().trim(), month = header.get(0).getText().trim();
		datePicker.click();
		LocalDate startRange = LocalDate.of(Integer.parseInt(year), Months.valueOf(month).getIndex(), Integer.parseInt(startDate));
		LocalDate endRange = LocalDate.of(Integer.parseInt(year), Months.valueOf(month).getIndex(), Integer.parseInt(endDate));
//		Mar 26, 2025
		List<WebElement> elements = driver.findElements(By.xpath("//div[@class=\"whitebox\"]//div[contains(@class, 'date-time')]"));
		for( WebElement card: elements) {
			String []cardDateSplit = card.getText().trim().split("\n")[0].split("[, ]");
			List<String> collect = Arrays.asList(cardDateSplit).stream().filter(e-> !e.isEmpty()).collect(Collectors.toList());
			LocalDate cardDate2 = LocalDate.of(Integer.parseInt(collect.get(2)), Months.valueOf(collect.get(0)).getIndex(), Integer.parseInt(collect.get(1)));
			System.out.println("Card's Date: "+cardDate2 + " - validation: "+ (startRange.isBefore(cardDate2) && endRange.isAfter(cardDate2)));
		}
		
		
	}
}
