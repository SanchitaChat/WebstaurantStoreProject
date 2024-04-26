
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class BasePage {

    protected static WebDriver driver;
    protected static Logger log = LogManager.getLogger();

    public String browser = "chrome";
    public String baseUrl = "https://www.webstaurantstore.com/";

    private void openBrowser() {
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            // Disable message 'Chrome is being controlled by automated test software'
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
            driver = new ChromeDriver(options);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    public void closeBrowser() {
        driver.quit();
    }

    // 1. Go to https://www.webstaurantstore.com/
    public Boolean goToHomepage() {
        try {
            openBrowser();
            driver.get(baseUrl);
        } catch (Exception e) {
            System.out.println("Unable to navigate to the homepage");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setText(By locator, String text) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
        //driver.findElement(locator).sendKeys(Keys.TAB);
    }

    public String getText(By locator) {
        String displayedText = driver.findElement(locator).getText();
        if (displayedText.isEmpty()) {
            return driver.findElement(locator).getAttribute("value");
        } else {
            return displayedText;
        }
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void goBack() {
        driver.navigate().back();
    }

    public void waitForElementText(By locator, String text) {
        // This is an explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.textToBe(locator, text));
    }
    @SuppressWarnings("deprecation")
    public void waitFor2Secs() {
        // This is an implicit wait
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }


}
