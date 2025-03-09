package utilities;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelper {
private WebDriver driver;
	
	//constructor
    public WaitHelper(WebDriver driver) {
        this.driver = driver;
    }
    //wait for the element to be visible
    public void WaitForElementToBeVisible (WebElement element, int timeoutInSeconds) {
    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeoutInSeconds));
    	wait.until(ExpectedConditions.visibilityOf(element));
    }
    //wait for the element to be clickable
    public void WaitForElementToBeClickable (WebElement element, int timoutInSeconds) {
    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timoutInSeconds));
    	wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    //wait for the alert to present
    public void waitForAlertToBePresent(int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.alertIsPresent());
    }
    //wait for the visibility of the element
    public void waitForElementToHidden(WebElement element, int timeoutSeconds) {
    	WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    	webDriverWait.until(ExpectedConditions.invisibilityOf(element));
    }

}
