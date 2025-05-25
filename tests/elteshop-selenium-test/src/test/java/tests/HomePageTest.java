package tests;

import org.junit.Test;
import org.junit.Assert;
import pages.HomePage;
import pages.ProductListPage;
import pages.ProductDetailPage;

/**
 * Home page and product Browse test class
 * Tests website home page and product Browse functionality
 */
public class HomePageTest extends BaseTest {

    /**
     * Test home page title and basic elements
     */
    @Test
    public void testHomePageTitle() {
        HomePage homePage = new HomePage(driver).open();

        // Assert: Logo with 'ELTE SHOP' alt text should be displayed on home page.
        Assert.assertTrue("Logo with 'ELTE SHOP' alt text should be displayed on home page", homePage.isLogoDisplayed());

        // Assert: Navigation menu should contain multiple items.
        int menuItemCount = homePage.getNavigationMenuItemsCount();
        Assert.assertTrue("Navigation menu should contain multiple items", menuItemCount > 0);
    }

    /**
     * Test product search functionality
     */
    @Test
    public void testProductSearch() {
        HomePage homePage = new HomePage(driver).open();

        // Search for products - CHANGED KEYWORD TO "gloves"
        ProductListPage productListPage = homePage.searchProduct("gloves");

        // Verify search results should contain products
        Assert.assertTrue("Search results should contain products", productListPage.getProductCount() > 0);

        // Verify search results should contain search keyword
        boolean containsKeyword = false;
        for (String productName : productListPage.getProductNames()) {
            if (productName.toLowerCase().contains("gloves")) { // Keyword changed to "gloves" here too
                containsKeyword = true;
                break;
            }
        }
        Assert.assertTrue("Search results should contain search keyword", containsKeyword);
    }

    /**
     * Test viewing product details after searching for a specific product (e.g., "gloves").
     */
    @Test
    public void testViewProductDetails() {
        HomePage homePage = new HomePage(driver).open();

        // Step 1: Search for "gloves" to get a product list
        ProductListPage productListPage = homePage.searchProduct("gloves");

        // Ensure search results contain products
        if (productListPage.getProductCount() > 0) {
            // Step 2: Open the first product from the search results
            ProductDetailPage productDetailPage = productListPage.openProduct(0);

            // Step 3: Verify product name is not empty
            String productName = productDetailPage.getProductName();
            Assert.assertFalse("Product name should not be empty", productName.isEmpty());
            System.out.println("Product Name (from details page): " + productName);

        } else {
            // If no products are found after searching for "gloves", fail the test.
            Assert.fail("No products found for 'gloves' to test product details.");
        }
    }
}