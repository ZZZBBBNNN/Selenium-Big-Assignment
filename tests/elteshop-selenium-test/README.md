# ELTE Shop Selenium Test Project

## Overview
This project contains automated Selenium tests for the ELTE Shop website (https://elteshop.com/). The tests are designed to demonstrate various Selenium testing capabilities including Page Object Model implementation, explicit waits, and interaction with different web elements.

## Project Structure
- `src/test/java/pages/` - Page Object classes
- `src/test/java/tests/` - Test classes

## Page Objects
The project implements the Page Object Model pattern with the following classes:
- `BasePage` - Base class with common methods and explicit wait functionality
- `HomePage` - Home page interactions
- `ProductListPage` - Product listing page interactions
- `ProductDetailPage` - Product detail page interactions
- `UserAccountPage` - User interactions
- `ContactPage` - Contact form page interactions

## Tests
The project includes the following tests:
- `BaseTest` - Base test class with setup and teardown methods
- `HomePageTest` - Tests for home page and navigation
- `UserAccountTest` - Tests for user account functionality
- `ContactTest` - Tests for contact form (textarea interaction)

## Running the Tests
To run the tests in the Docker environment:

1. Make sure Docker and Docker Compose are installed
2. Navigate to the selenium-docker-sandbox-master directory
3. Run `docker compose up` to start the containers
4. In a new terminal, run `docker exec -it selenium_testenv bash`
5. Inside the container, navigate to the test directory: `cd tests/elteshop-selenium-test`
6. Run the tests with Gradle: `gradle test`

## Viewing Test Results
After running the tests, you can view the results in:
- `build/reports/tests/test/index.html` - HTML report with test results
- `build/test-results/test/` - XML test results

## Troubleshooting
- If tests fail due to timing issues, try increasing the wait times in BasePage.java
- If elements cannot be found, check the console output for detailed error messages
- The tests include fallback mechanisms to handle different page structures
- For any persistent issues, check the Selenium Grid console at http://localhost:8081/

## Notes
- The tests are designed to be non-destructive (they will not submit orders or forms)
- Some tests may be skipped if certain elements are not found on the page
- The tests include detailed logging to help diagnose issues

## Technical Requirements
- Java 8 or higher
- Gradle
- Selenium WebDriver
- Docker and Docker Compose (for running in the provided environment)

## Assignment Information
This project was created as part of the Software Quality and Testing course assignment. It demonstrates the use of Selenium WebDriver for automated testing of web applications, with a focus on the Page Object Model design pattern and various web element interactions.
