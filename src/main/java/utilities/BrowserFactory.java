package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {
	 public static WebDriver startBrowser(String browser) {
	        WebDriver driver;
	        switch (browser.toLowerCase()) {
	            case "chrome":
	                WebDriverManager.chromedriver().setup();
	                driver = new ChromeDriver();
	                break;
	            case "firefox":
	                WebDriverManager.firefoxdriver().setup();
	                driver = new FirefoxDriver();
	                break;
	            case "edge":
	                WebDriverManager.edgedriver().setup();
	                driver = new EdgeDriver();
	                break;
	            case "safari":
	            	  if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
	                      throw new UnsupportedOperationException("Safari is only supported on macOS.");
	                  }
	                  driver = new SafariDriver();
	                  break;
	            default:
	                throw new IllegalArgumentException("Invalid browser: " + browser);
	        }
	        driver.manage().window().maximize();
	        return driver;
	        
	 }

}
	 
