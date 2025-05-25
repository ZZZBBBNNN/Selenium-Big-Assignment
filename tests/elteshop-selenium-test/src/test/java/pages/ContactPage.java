package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Contact page object class
 * Handles interactions with the contact page
 */
public class ContactPage extends BasePage {
    // Locators
    private final By nameInputLocator = By.id("form-element-name");
    private final By emailInputLocator = By.id("form-element-email");
    private final By enquiryTextareaLocator = By.id("form-element-enquiry");
    // GDPR consent checkbox locator based on label's 'for' attribute and common pattern
    private final By gdprConsentCheckboxLocator = By.id("form-element-gdpr_consent");
    private final By continueButtonLocator = By.cssSelector(".buttons.contact-buttons .btn.btn-primary");
    private final By contactInfoLocator = By.id("contact-info");
    private final By successMessageLocator = By.cssSelector(".alert-success");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public ContactPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Open contact page
     * @return Current page object
     */
    public ContactPage open() {
        driver.get("https://elteshop.com/index.php?route=information/contact");
        waitForPageLoad();
        // Add a more specific wait for the form itself to be visible after page load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contact")));
        return this;
    }

    /**
     * Fill contact form
     * @param name Name
     * @param email Email
     * @param enquiry Enquiry text
     * @return Current page object
     */
    public ContactPage fillContactForm(String name, String email, String enquiry) {
        enterText(nameInputLocator, name);
        enterText(emailInputLocator, email);
        enterText(enquiryTextareaLocator, enquiry);
        return this;
    }

    /**
     * Handle GDPR consent by clicking the checkbox
     * @return Current page object
     */
    public ContactPage agreeToGdprConsent() {
        // Ensure the checkbox is visible and clickable before attempting to click
        clickElement(gdprConsentCheckboxLocator);
        return this;
    }

    /**
     * Check if GDPR consent checkbox is present.
     * This method is made public so it can be called from test classes.
     * @return Whether GDPR consent checkbox is present.
     */
    public boolean isGdprConsentCheckboxPresent() {
        return isElementPresent(gdprConsentCheckboxLocator);
    }

    /**
     * Click the "Continue" button after filling the form and agreeing to GDPR.
     * @return Current page object
     */
    public ContactPage clickContinueButton() {
        clickElement(continueButtonLocator);
        waitForPageLoad();
        return this;
    }

    /**
     * Get contact info
     * @return Contact info text
     */
    public String getContactInfo() {
        if (isElementPresent(contactInfoLocator)) {
            return getElementText(contactInfoLocator);
        }
        return "";
    }

    /**
     * Check if success message is displayed
     * @return Whether success message is displayed
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    /**
     * Get success message text
     * @return Success message text
     */
    public String getSuccessMessageText() {
        if (isSuccessMessageDisplayed()) {
            return getElementText(successMessageLocator);
        }
        return "";
    }

    /**
     * Checks if the 'Continue' button is present and visible, and optionally verifies its text.
     * This method leverages BasePage's waiting capabilities.
     * @param expectedText The text expected on the button (e.g., "Continue", "Submit", "Send"). Can be null or empty to just check visibility.
     * @return true if the button is present and visible (and text matches if provided), false otherwise.
     */
    public boolean isContinueButtonPresentAndVisible(String expectedText) {
        try {
            if (isElementPresent(continueButtonLocator)) {
                String actualButtonText = getElementText(continueButtonLocator).trim();
                System.out.println("Actual button text: '" + actualButtonText + "'");
                
                if (expectedText == null || expectedText.isEmpty() || 
                    actualButtonText.toLowerCase().contains(expectedText.toLowerCase().trim())) {
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error checking Continue button: " + e.getMessage());
            return false;
        }
    }
}