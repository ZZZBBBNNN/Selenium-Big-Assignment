package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

/**
 * User account page object class
 * Handles interactions with the user account page
 */
public class UserAccountPage extends BasePage {
    // Updated locators based on actual page structure
    private final By emailInputLocator = By.id("email_login");
    private final By passwordInputLocator = By.id("password_login");
    private final By loginButtonLocator = By.xpath("//button[span[text()='Login']]");
    private final By forgotPasswordLinkLocator = By.linkText("Forgotten password");
    private final By accountMenuLocator = By.cssSelector("#column-right .list-group");
    private final By logoutLinkLocator = By.linkText("Logout");
    private final By registerLinkLocator = By.linkText("Create your own account");
    private final By myAccountLinkLocator = By.linkText("My Account");
    private final By forgotPasswordEmailLabelLocator = By.xpath("//label[@for='inputEmail' and contains(text(), 'E-Mail Address')]");
    private final By forgotPasswordEmailInputLocator = By.id("inputEmail");
    
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
            // Don't throw exception here to allow tests to continue with fallback approaches
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
     * Click register link
     * @return Current page object
     */
    public UserAccountPage clickRegister() {
        try {
            if (isElementPresent(registerLinkLocator)) {
                clickElement(registerLinkLocator);
            } else {
                // Try alternative locators
                By altRegisterLocator = By.partialLinkText("Create");
                
                if (isElementPresent(altRegisterLocator)) {
                    clickElement(altRegisterLocator);
                } else {
                    System.out.println("Register link not found");
                }
            }
            waitForPageLoad();
        } catch (Exception e) {
            System.out.println("Error clicking register: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * Click my account link
     * @return Current page object
     */
    public UserAccountPage clickMyAccount() {
        try {
            if (isElementPresent(myAccountLinkLocator)) {
                clickElement(myAccountLinkLocator);
            } else {
                // Try alternative locators
                By altMyAccountLocator = By.partialLinkText("Account");
                
                if (isElementPresent(altMyAccountLocator)) {
                    clickElement(altMyAccountLocator);
                } else {
                    System.out.println("My account link not found");
                }
            }
            waitForPageLoad();
        } catch (Exception e) {
            System.out.println("Error clicking my account: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * Logout
     * @return Current page object
     */
    public UserAccountPage logout() {
        try {
            if (isElementPresent(logoutLinkLocator)) {
                clickElement(logoutLinkLocator);
                waitForPageLoad();
            } else {
                // Try alternative locators
                By altLogoutLocator = By.partialLinkText("Logout");
                
                if (isElementPresent(altLogoutLocator)) {
                    clickElement(altLogoutLocator);
                } else {
                    System.out.println("Logout link not found");
                }
                waitForPageLoad();
            }
        } catch (Exception e) {
            System.out.println("Error during logout: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * Check if logged in
     * @return Whether logged in
     */
    public boolean isLoggedIn() {
        try {
            return isElementPresent(logoutLinkLocator) || 
                   isElementPresent(By.partialLinkText("Logout"));
        } catch (Exception e) {
            System.out.println("Error checking if logged in: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get account menu item count
     * @return Menu item count
     */
    public int getAccountMenuItemCount() {
        try {
            if (isElementPresent(accountMenuLocator)) {
                WebElement menu = waitAndReturnElement(accountMenuLocator);
                List<WebElement> menuItems = menu.findElements(By.tagName("a"));
                return menuItems.size();
            } else {
                // Try alternative approach - count all links in the account section
                List<WebElement> accountLinks = driver.findElements(By.cssSelector(".account-section a, .account-area a"));
                if (!accountLinks.isEmpty()) {
                    return accountLinks.size();
                }
                System.out.println("Account menu locator not found. Returning 0 items.");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error getting account menu item count: " + e.getMessage());
            return 0;
        }
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
}
