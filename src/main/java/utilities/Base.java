package utilities;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Base {
	public String dynamicXpath (String xpath, String replacetext) {
        return String.format(xpath, replacetext);
	}
	
	public void scrollElement(WebDriver driver, WebElement element){
		 JavascriptExecutor js = (JavascriptExecutor) driver;
	     js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void jsClick(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
	
	public void actionClickMoveTo(WebDriver driver, WebElement source, WebElement target) {
		Actions a = new Actions(driver);
		a.click(source).pause(Duration.ofSeconds(1)).moveToElement(target).pause(Duration.ofSeconds(1)).click().build().perform();
	}
}
