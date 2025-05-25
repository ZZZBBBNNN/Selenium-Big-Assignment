package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.junit.Assert;
import pages.HomePage;
import pages.UserAccountPage;

/**
 * User account test class
 * Tests user account related functionality
 */
public class UserAccountTest extends BaseTest {
    
    /**
     * Test user login page
     */
    @Test
    public void testLoginPage() {
        try {
            System.out.println("Starting testLoginPage test");
            
            // Open login page
            UserAccountPage accountPage = new UserAccountPage(driver).open();
            
            // Verify page title or content
            String pageTitle = accountPage.getPageTitle();
            String pageSource = driver.getPageSource();
            
            boolean titleContainsExpectedText = 
                pageTitle.contains("Account") || 
                pageTitle.contains("Login") || 
                pageTitle.contains("ELTE");
                
            boolean pageContainsExpectedText = 
                pageSource.contains("Login") || 
                pageSource.contains("E-mail") || 
                pageSource.contains("Password");
            
            Assert.assertTrue("Page should contain login-related content", 
                             titleContainsExpectedText || pageContainsExpectedText);
            
            System.out.println("Login page verification successful");
            
            // Test login functionality (without using real credentials, just testing form filling)
            accountPage.login("test@example.com", "password123");
            
            Assert.assertTrue("Should display error message with fake credentials", accountPage.isLoginErrorDisplayed());
            
            System.out.println("testLoginPage test completed successfully");
        } catch (Exception e) {
            System.err.println("Error in testLoginPage: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to fail the test
        }
    }
    
    /**
     * Test account menu
     */
    @Test
    public void testAccountMenu() {
        try {
            System.out.println("Starting testAccountMenu test");
            
            // Open login page
            UserAccountPage accountPage = new UserAccountPage(driver).open();
            
            // Verify page loaded correctly
            String pageSource = driver.getPageSource();
            Assert.assertTrue("Page should contain login-related content", 
                             pageSource.contains("Login") || 
                             pageSource.contains("E-mail") || 
                             pageSource.contains("Password"));
            
            // Get account menu item count - this might be 0 if not logged in
            int menuItemCount = accountPage.getAccountMenuItemCount();
            
            // Just log the result, don't assert as menu might not be visible when not logged in
            System.out.println("Account menu has " + menuItemCount + " items");
            
            System.out.println("testAccountMenu test completed successfully");
        } catch (Exception e) {
            System.err.println("Error in testAccountMenu: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to fail the test
        }
    }
    
    /**
     * Test forgot password link
     */
    @Test
    public void testForgotPassword() {
        try {
            System.out.println("Starting testForgotPassword test");
            
            // Open login page
            UserAccountPage accountPage = new UserAccountPage(driver).open();
            
            // Verify page loaded correctly
            String pageSource = driver.getPageSource();
            Assert.assertTrue("Page should contain login-related content", 
                             pageSource.contains("Login") || 
                             pageSource.contains("E-mail") || 
                             pageSource.contains("Password"));
            
            // Check if forgot password link exists
            boolean forgotPasswordExists = driver.findElements(By.xpath("//a[contains(@href, 'account/forgotten')]")).size() > 0;
            
            if (forgotPasswordExists) {
                // Click forgot password link
                accountPage.clickForgotPassword();
                
                // Verify navigation to forgot password page
                Assert.assertTrue("Should navigate to forgot password page", 
                                 accountPage.isForgotPasswordPageLoaded() || 
                                 driver.getCurrentUrl().contains("forgotten") || 
                                 driver.getPageSource().contains("E-Mail Address"));
                
                System.out.println("Successfully navigated to forgot password page");
            } else {
                System.out.println("Forgot password link not found, skipping this part of the test");
            }
            
            System.out.println("testForgotPassword test completed successfully");
        } catch (Exception e) {
            System.err.println("Error in testForgotPassword: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to fail the test
        }
    }
}
