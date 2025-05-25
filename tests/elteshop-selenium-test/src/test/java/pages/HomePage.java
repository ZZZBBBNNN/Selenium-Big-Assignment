package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Home page object class
 * Handles interactions with the website home page
 */
public class HomePage extends BasePage {
    // Locators
    private final By searchInputLocator = By.xpath("//input[@placeholder='Keywords']");
    private final By searchButtonLocator = By.xpath("//button[@onclick='moduleSearch();']");

    private final By logoLocator = By.xpath("//div[contains(@class, 'header-navbar-top-center')]//img[@alt='ELTE SHOP ']");

    private final By navigationMenuLocator = By.xpath("//div[@id='category-nav']/ul");

    // MODIFIED: clothesMenuLocator to use XPath based on the <li>'s ID.
    // Please verify that 'cat_133' indeed corresponds to the "Clothes" menu item on your website.
    private final By clothesMenuLocator = By.xpath("//li[@id='cat_133']/a");

    private final By cookieAcceptButtonLocator = By.linkText("Elfogadom");

    // These locators should be correct if the navigationMenuLocator is fixed
    private final By productsMenuLocator = By.linkText("Products");
    private final By newMenuLocator = By.linkText("New");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Open home page
     * @return Current page object
     */
    public HomePage open() {
        driver.get("https://elteshop.com/");
        waitForPageLoad(); // Wait for body element to be visible

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(logoLocator));
            System.out.println("Header Logo is visible. Page is likely loaded and stable.");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Timeout waiting for header logo visibility on homepage with locator: " + logoLocator.toString());
            System.err.println("Error details: " + e.getMessage());
            throw new org.openqa.selenium.TimeoutException("Failed to load Homepage: Header Logo not found or not visible.", e);
        }

        try {
            WebDriverWait cookieWait = new WebDriverWait(driver, 5);
            WebElement cookieButton = cookieWait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButtonLocator));
            cookieButton.click();
            System.out.println("Cookie notice accepted.");
            cookieWait.until(ExpectedConditions.invisibilityOfElementLocated(cookieAcceptButtonLocator));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Cookie notice not found or not clickable within 5 seconds. Continuing without accepting.");
        }

        return this;
    }

    /**
     * Search for product
     * @param keyword Search keyword
     * @return Search results page object
     */
    public ProductListPage searchProduct(String keyword) {
        enterText(searchInputLocator, keyword);
        clickElement(searchButtonLocator);
        return new ProductListPage(driver);
    }

    /**
     * Navigate to clothes category
     * @return Product list page object
     */
    public ProductListPage navigateToClothes() {
        clickElement(clothesMenuLocator); // This line should now correctly click the "Clothes" link
        return new ProductListPage(driver);
    }

    /**
     * Navigate to products category
     * @return Product list page object
     */
    public ProductListPage navigateToProducts() {
        clickElement(productsMenuLocator);
        return new ProductListPage(driver);
    }

    /**
     * Navigate to new products category
     * @return Product list page object
     */
    public HomePage navigateToNew() { // Return HomePage here, as it's a category link
        clickElement(newMenuLocator);
        // Assuming "New" page is a product list page. If not, this needs to return HomePage or a new specific Page Object.
        // For consistency, returning HomePage as it's a top-level nav link.
        return new HomePage(driver);
    }

    /**
     * Get navigation menu items count
     * @return Menu items count
     */
    public int getNavigationMenuItemsCount() {
        WebElement navMenu = waitAndReturnElement(navigationMenuLocator);
        return navMenu.findElements(By.tagName("li")).size();
    }

    /**
     * Check if logo is displayed
     * @return Whether logo is displayed
     */
    public boolean isLogoDisplayed() {
        return isElementPresent(logoLocator);
    }
}