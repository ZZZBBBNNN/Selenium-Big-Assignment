package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Product detail page object class
 * Handles interactions with the product detail page
 */
public class ProductDetailPage extends BasePage {
    // Locators
    private final By productNameLocator = By.cssSelector("h1.product-page-head-title span.product-page-product-name");
    private final By productDescriptionLocator = By.cssSelector("#tab-description");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public ProductDetailPage(WebDriver driver) {
        super(driver);
        // Use explicit wait for the specific product name element to confirm page loaded.
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productNameLocator));
            System.out.println("Product Detail Page: Product name element is visible, page likely loaded and stable.");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Product Detail Page: Timeout waiting for product name element. " + e.getMessage());
            throw new org.openqa.selenium.TimeoutException("Product Detail Page did not load correctly: Product name element not found.", e);
        }
    }

    /**
     * Get product name
     * @return Product name
     */
    public String getProductName() {
        return getElementText(productNameLocator);
    }

    /**
     * Get product description
     * @return Product description text
     */
    public String getProductDescription() {
        if (isElementPresent(productDescriptionLocator)) {
            return getElementText(productDescriptionLocator);
        }
        return "";
    }

}