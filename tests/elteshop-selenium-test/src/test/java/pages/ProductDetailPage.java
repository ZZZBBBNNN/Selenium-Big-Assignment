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

    // Product price locator removed as requested.

    private final By productQuantityLocator = By.id("input-quantity");
    private final By addToCartButtonLocator = By.id("button-cart");
    private final By productDescriptionLocator = By.cssSelector("#tab-description");
    private final By productOptionLocator = By.cssSelector(".form-group select");
    private final By radioOptionLocator = By.cssSelector("input[type='radio']");
    private final By stockInfoLocator = By.cssSelector(".stock-info");
    private final By cartLinkLocator = By.cssSelector("a[title='Shopping Cart']");

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

    // getProductPrice() method removed as requested.

    /**
     * Set product quantity
     * @param quantity Quantity
     * @return Current page object
     */
    public ProductDetailPage setQuantity(int quantity) {
        if (isElementPresent(productQuantityLocator)) {
            WebElement quantityInput = waitAndReturnElement(productQuantityLocator);
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(quantity));
        }
        return this;
    }

    /**
     * Select product option (dropdown)
     * @param optionIndex Option index
     * @param valueIndex Value index
     * @return Current page object
     */
    public ProductDetailPage selectOption(int optionIndex, int valueIndex) {
        if (isElementPresent(productOptionLocator)) {
            WebElement selectElement = driver.findElements(productOptionLocator).get(optionIndex);
            Select dropdown = new Select(selectElement);
            dropdown.selectByIndex(valueIndex);
        }
        return this;
    }

    /**
     * Select radio option
     * @param optionIndex Option index
     * @return Current page object
     */
    public ProductDetailPage selectRadioOption(int optionIndex) {
        if (isElementPresent(radioOptionLocator)) {
            WebElement radioButton = driver.findElements(radioOptionLocator).get(optionIndex);
            if (!radioButton.isSelected()) {
                radioButton.click();
            }
        }
        return this;
    }

    /**
     * Add to cart
     * @return Current page object
     */
    public ProductDetailPage addToCart() {
        clickElement(addToCartButtonLocator);
        return this;
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

    /**
     * Get stock info
     * @return Stock info text
     */
    public String getStockInfo() {
        if (isElementPresent(stockInfoLocator)) {
            return getElementText(stockInfoLocator);
        }
        return "";
    }

}