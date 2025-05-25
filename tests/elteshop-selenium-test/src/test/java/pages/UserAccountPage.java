package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import org.openqa.selenium.interactions.Actions;

/**
 * User account page object class
 * Handles interactions with the user account page
 */
public class UserAccountPage extends BasePage {
    private final By emailInputLocator = By.id("email_login");
    private final By passwordInputLocator = By.id("password_login");
    private final By loginButtonLocator = By.xpath("//button[span[text()='Login']]");
    private final By forgotPasswordLinkLocator = By.linkText("Forgotten password");
    private final By registerLinkLocator = By.linkText("Create your own account");
    private final By myAccountLinkLocator = By.linkText("My Account");
    private final By forgotPasswordEmailLabelLocator = By.xpath("//label[@for='inputEmail' and contains(text(), 'E-Mail Address')]");
    private final By forgotPasswordEmailInputLocator = By.id("inputEmail");
    // This is the main dropdown trigger link (e.g., "Welcome Zhao!")
    private final By loggedDropdownTriggerLocator = By.cssSelector(".logged-dropdown > .nav-link"); 
    // This is the actual "Log Off" link inside the dropdown menu
    private final By logOffLinkLocator = By.xpath("//ul[contains(@class, 'dropdown-hover-menu')]//a[@title='Log Off' and normalize-space()='Log Off']");
    // Locator to verify being on the login page after logout
    private final By loginPageHeaderLocator = By.xpath("//div[@class='center page-head-center text-center']/h1[normalize-space()='Login']");
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public UserAccountPage(WebDriver driver) {
        super(driver);
        try {
            // Use a more flexible approach to verify page is loaded
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(emailInputLocator),
                ExpectedConditions.titleContains("Login"),
                ExpectedConditions.titleContains("Account")
            ));
            System.out.println("User Account Page: Login page appears to be loaded.");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("User Account Page: Timeout waiting for login page to load. Current URL: " + driver.getCurrentUrl());
            System.err.println("User Account Page: Page source excerpt: " + driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())));
        }
    }
    
    /**
     * Open login page
     * @return Current page object
     */
    public UserAccountPage open() {
        driver.get("https://elteshop.com/customer/login");
        waitForPageLoad();
        System.out.println("Navigated to login page: " + driver.getCurrentUrl());
        return this;
    }
    
    /**
     * Login
     * @param email Email
     * @param password Password
     * @return Current page object
     */
    public UserAccountPage login(String email, String password) {
        try {
            if (isElementPresent(emailInputLocator)) {
                enterText(emailInputLocator, email);
                enterText(passwordInputLocator, password);
                
                if (isElementPresent(loginButtonLocator)) {
                    clickElement(loginButtonLocator);
                } else {
                    // Try alternative locators
                    By altLoginLocator = By.cssSelector("button");
                    
                    if (isElementPresent(altLoginLocator)) {
                        clickElement(altLoginLocator);
                        System.out.println("Clicked login button using alternative locator");
                    } else {
                        System.out.println("Login button not found");
                    }
                }
                
                waitForPageLoad();
                System.out.println("Attempted login with email: " + email);
            } else {
                System.out.println("Email or password input not found");
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * Click forgot password link
     * @return Current page object
     */
    public UserAccountPage clickForgotPassword() {
        try {
            if (isElementPresent(forgotPasswordLinkLocator)) {
                clickElement(forgotPasswordLinkLocator);
            } else {
                // Try alternative locators
                By altForgotLocator = By.partialLinkText("Forgot");
                
                if (isElementPresent(altForgotLocator)) {
                    clickElement(altForgotLocator);
                } else {
                    System.out.println("Forgot password link not found");
                }
            }
            waitForPageLoad();
        } catch (Exception e) {
            System.out.println("Error clicking forgot password: " + e.getMessage());
        }
        return this;
    }
    

    /**
     * Get page title (for verification in tests)
     * @return Page title text
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    /**
    * Check if login error message is displayed
    * @return Whether login error message is displayed
    */
    public boolean isLoginErrorDisplayed() {
    try {
        By loginErrorLocator = By.cssSelector(".alert.alert-danger");
        return isElementPresent(loginErrorLocator) && 
               getElementText(loginErrorLocator).contains("Incorrect username and/or password");
    } catch (Exception e) {
        System.out.println("Error checking login error message: " + e.getMessage());
        return false;
    }
    }
    /**
     * Check if forgot password page is loaded
     * @return Whether forgot password page is loaded
     */
    public boolean isForgotPasswordPageLoaded() {
        try {
            return isElementPresent(forgotPasswordEmailLabelLocator) || 
                    isElementPresent(forgotPasswordEmailInputLocator);
        } catch (Exception e) {
            System.out.println("Error checking if forgot password page is loaded: " + e.getMessage());
            return false;
    }
    }

        /**
     * Triggers the dropdown menu to become visible (by hovering or clicking the main trigger).
     * @return Current page object.
     */
    public UserAccountPage triggerAccountDropdown() {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitAndReturnElement(loggedDropdownTriggerLocator)).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logOffLinkLocator)); // Wait for the menu items to be visible
        System.out.println("Account dropdown triggered.");
        return this;
    }

    /**
     * Clicks the "Log Off" link in the account dropdown menu.
     * Assumes the dropdown is already open/triggered.
     * @return Current page object.
     */
    public UserAccountPage clickLogOffLink() {
        clickElement(logOffLinkLocator);
        waitForPageLoad(); // Wait for navigation to the login page
        System.out.println("Clicked Log Off link.");
        return this;
    }
    
    /**
     * Checks if the user is currently logged in by checking for the dropdown trigger.
     * This is a better check than just looking for a "Logout" link that might be hidden.
     * @return Whether the user appears to be logged in.
     */
    public boolean isLoggedIn() { 
        try {
            boolean loggedInDropdownVisible = isElementPresentAndVisible(loggedDropdownTriggerLocator);

            System.out.println("DEBUG: isLoggedIn() - Logged-in dropdown visible: " + loggedInDropdownVisible);
            return loggedInDropdownVisible;

        } catch (Exception e) {
            System.err.println("Error checking if logged in: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if the page is the login page (after logout).
     * @return true if the login page is loaded, false otherwise.
     */
    public boolean isLoginPageLoaded() {
        try {
            return isElementPresent(loginPageHeaderLocator);
        } catch (Exception e) {
            System.err.println("Error checking if login page is loaded: " + e.getMessage());
            return false;
        }
    }
}
