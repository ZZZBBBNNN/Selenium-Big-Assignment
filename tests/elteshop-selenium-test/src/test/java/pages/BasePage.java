package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;

/**
 * Base page class, parent class for all page objects
 * Provides common methods and explicit wait functionality
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    // Page title locator
    protected final By titleLocator = By.tagName("title");
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }
    
    /**
     * Wait for element to be visible and return it
     * @param locator Element locator
     * @return Found WebElement
     */
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }
    
    /**
     * Wait for element to be clickable and return it
     * @param locator Element locator
     * @return Found WebElement
     */
    protected WebElement waitAndReturnClickableElement(By locator) {
        this.wait.until(ExpectedConditions.elementToBeClickable(locator));
        return this.driver.findElement(locator);
    }
    
    /**
     * Get page title
     * @return Page title text
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Click on element
     * @param locator Element locator
     */
    protected void clickElement(By locator) {
        waitAndReturnClickableElement(locator).click();
    }
    
    /**
     * Enter text in input field
     * @param locator Element locator
     * @param text Text to enter
     */
    protected void enterText(By locator, String text) {
        WebElement element = waitAndReturnElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get element text
     * @param locator Element locator
     * @return Element text content
     */
    protected String getElementText(By locator) {
        return waitAndReturnElement(locator).getText();
    }
    
    /**
     * Check if element exists
     * @param locator Element locator
     * @return Whether element exists
     */
    protected boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if an element is present in the DOM AND visible on the page.
     * @param locator The By locator for the element.
     * @return true if the element is present and visible, false otherwise.
     */
    protected boolean isElementPresentAndVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            // Element not found or not visible within the timeout
            return false;
        }
    }
    
    /**
     * Wait for page to load
     */
    protected void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }
}
