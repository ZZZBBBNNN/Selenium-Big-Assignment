package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Base test class
 * Provides common setup and teardown methods for all test classes
 */
public class BaseTest {
    protected WebDriver driver;
    
    /**
     * Set up WebDriver before each test method
     * @throws MalformedURLException if URL is malformed
     */
    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        
        // FIXED: Add common ChromeOptions for stability in Docker/headless environments
        // These are often required for Chrome in containerized environments,
        // especially older versions like Chrome 65.
        options.addArguments("--no-sandbox"); // Disables sandbox mode, needed for Docker often
        options.addArguments("--disable-dev-shm-usage"); // Overcomes limited /dev/shm in some Docker setups
        // If you were running without the '-debug' image or seeing issues without UI,
        // you might also add --headless here. But for -debug, Xvfb handles it.
        // options.addArguments("--headless"); 
        
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();
    }
    
    /**
     * Close WebDriver after each test method
     */
    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}