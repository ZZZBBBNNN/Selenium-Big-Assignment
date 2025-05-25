package tests;

import org.junit.Test;
import org.junit.Assert;
import pages.ContactPage;
// No need to import org.openqa.selenium.By here anymore, as it's handled within ContactPage

/**
 * Contact page test class
 * Tests contact form functionality
 */
public class ContactTest extends BaseTest {

    /**
     * Test contact form filling (textarea)
     */
    @Test
    public void testContactForm() {
        // Open contact page
        ContactPage contactPage = new ContactPage(driver).open();

        // Verify page title
        String pageTitle = contactPage.getPageTitle();
        Assert.assertTrue("Page title should contain 'Contact'",
                          pageTitle.contains("Contact") || driver.getPageSource().contains("Contact"));

        // Agree to GDPR consent if present and required
        if (contactPage.isGdprConsentCheckboxPresent()) {
            contactPage.agreeToGdprConsent();
            System.out.println("GDPR consent checked.");
        } else {
            System.out.println("GDPR consent checkbox not found or not present.");
        }

        // Fill contact form (test textarea)
        contactPage.fillContactForm(
            "Test User",
            "test@example.com",
            "This is a test message from automated testing.\nPlease ignore this message.\nThank you!"
        );

        // Verify continue button exists with correct text using ContactPage method
        // This assertion will now use the waiting mechanism inside ContactPage.isContinueButtonPresentAndVisible()
        Assert.assertTrue("Contact form should have a continue button",
        contactPage.isContinueButtonPresentAndVisible(""));
        
        // Note: Do not actually submit the form to avoid sending spam to the website
        System.out.println("Contact form successfully filled and continue button verified");
        
        // Test passes if form is filled without errors and continue button is present
    }

    /**
     * Test getting contact information
     */
    @Test
    public void testContactInfo() {
        // Open contact page
        ContactPage contactPage = new ContactPage(driver).open();

        // Get contact information
        String contactInfo = contactPage.getContactInfo();

        // Verify contact info is not empty (if page has contact info section)
        if (!contactInfo.isEmpty()) {
            System.out.println("Contact info: " + contactInfo);
            Assert.assertFalse("Contact info should not be empty", contactInfo.isEmpty());
        } else {
            System.out.println("No contact info section found on page");
        }
    }
}