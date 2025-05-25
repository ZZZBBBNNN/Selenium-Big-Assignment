package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Product list page object class
 * Handles interactions with the product list page
 */
public class ProductListPage extends BasePage {

    // Locators
    private final By productItemsLocator = By.cssSelector("h2.product-card-item");
    private final By productNameLocator = By.tagName("a");
    private final By sortSelectLocator = By.id("input-sort");
    private final By filterSelectLocator = By.id("input-limit");
    private final By pageHeadingLocator = By.cssSelector("h1.page-head-title");
    private final By resultsCountTextLocator = By.xpath("//div[contains(@class, 'sortbar-bottom')]//div[@class='results']");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public ProductListPage(WebDriver driver) {
        super(driver);
        // Using ExpectedConditions.or to wait for EITHER the product count element OR the main page heading.
        // This makes the ProductListPage constructor robust for both actual product lists and category overview pages.
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(resultsCountTextLocator), // For pages with product counts (e.g., search results)
                ExpectedConditions.visibilityOfElementLocated(pageHeadingLocator)
            ));
            System.out.println("Product List Page: Either results count element or page heading is visible, page likely loaded.");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Product List Page: Timeout waiting for expected page load element (results count or heading). " + e.getMessage());
            throw new org.openqa.selenium.TimeoutException("Product List Page did not load correctly: Neither results count nor page heading found within timeout.", e);
        }
    }

    /**
     * Get product count by parsing the results text.
     * This method is designed to work for pages that have the results count text.
     * It will return 0 if the element is not present.
     * @return Product count
     */
    public int getProductCount() {
        if (!isElementPresent(resultsCountTextLocator)) {
            System.out.println("Product List Page - Results count element not found on this page. Returning 0.");
            return 0;
        }

        String resultsText = getElementText(resultsCountTextLocator);
        System.out.println("Product List Page - Raw Results Text: " + resultsText);

        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(resultsText);

        if (matcher.find()) {
            try {
                int count = Integer.parseInt(matcher.group(1));
                System.out.println("Product List Page - Parsed Product Count: " + count);
                return count;
            } catch (NumberFormatException e) {
                System.err.println("Product List Page - Error parsing number from results text: " + resultsText + " Error: " + e.getMessage());
                return 0;
            }
        }
        System.err.println("Product List Page - Could not find total product count using regex in text: " + resultsText);
        return 0;
    }

    /**
     * Get all product names displayed on the current page.
     * @return List of product names.
     */
    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        // Only attempt to get product names if product items locator is present.
        // This makes it safe for category pages without direct product listings.
        if (isElementPresent(productItemsLocator)) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(productItemsLocator)); // Ensure at least one product item exists
                List<WebElement> products = driver.findElements(productItemsLocator);

                for (WebElement product : products) {
                    if (product.findElements(productNameLocator).size() > 0) {
                        WebElement nameElement = product.findElement(productNameLocator);
                        productNames.add(nameElement.getText().trim());
                    } else {
                        System.err.println("Product name element not found within a product item for locator: " + productNameLocator);
                    }
                }
            } catch (org.openqa.selenium.TimeoutException e) {
                System.out.println("No product items (h2.product-card-item) found on the page within timeout.");
            } catch (Exception e) {
                System.err.println("Error getting product names with locator " + productItemsLocator + ": " + e.getMessage());
            }
        } else {
            System.out.println("Product List Page - No product items locator found. Returning empty list.");
        }
        return productNames;
    }

    /**
     * Open product at specified index.
     * This method expects product items to be present on the page.
     * @param index Product index (0-based)
     * @return Product detail page object
     */
    public ProductDetailPage openProduct(int index) {
        // Re-find product elements to ensure they are not stale
        List<WebElement> products = driver.findElements(productItemsLocator);
        if (index >= 0 && index < products.size()) {
            WebElement productLink = products.get(index).findElement(productNameLocator);
            wait.until(ExpectedConditions.elementToBeClickable(productLink)).click();
            return new ProductDetailPage(driver);
        }
        throw new IndexOutOfBoundsException("Product index " + index + " out of range. Total products found: " + products.size());
    }

    /**
     * Open product by name.
     * This method expects product links with given name to be present on the page.
     * @param productName Product name
     * @return Product detail page object
     */
    public ProductDetailPage openProductByName(String productName) {
        By productLinkLocator = By.linkText(productName);
        clickElement(productLinkLocator);
        return new ProductDetailPage(driver);
    }

    /**
     * Get page heading text
     * @return Heading text
     */
    public String getPageHeading() {
        return getElementText(pageHeadingLocator);
    }
}