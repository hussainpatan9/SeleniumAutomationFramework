package StepDefination;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

/**
 * Helper class for Selenium WebDriver operations and test automation.
 *
 * This class provides methods for interacting with web elements, performing
 * actions, logging, and handling WebDriver initialization and teardown.
 */
public class Helper {
	// Logger for logging messages and errors
	// Logger for logging messages and errors
	private static final Logger LOGGER = Logger.getLogger(Helper.class.getName());
	private static Helper instance;
	// WebDriver instance for browser automation
	protected WebDriver driver;

	private Helper() {
		// Uncomment the line below if you want to initialize the driver on Helper
		// instantiation
		// initializeDriver("chrome");
		initLogger();
	}

	// Getter method to obtain the instance
	public static Helper getInstance() {
		if (instance == null) {
			synchronized (Helper.class) {
				if (instance == null) {
					instance = new Helper();
				}
			}
		}
		return instance;
	}

	// Optional: Add a method to reset the instance (useful for testing)
	public static void resetInstance() {
		instance = null;
	}

	private void initLogger() {
		try {
			// Create a file handler to write logs to a file
			FileHandler fileHandler = new FileHandler("test_logs.log", true);
			LOGGER.addHandler(fileHandler);

			// Set the logging level to ALL (you can adjust this level)
			LOGGER.setLevel(Level.ALL);

			// Create a SimpleFormatter to format log records
			fileHandler.setFormatter(new java.util.logging.SimpleFormatter());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to initialize Logger", e);
		}
	}

	/**
	 * Initializes the WebDriver based on the provided browser type and driver path.
	 *
	 * @param browserType The type of the browser (e.g., "chrome").
	 * @param driverPath  The path to the WebDriver executable.
	 */
	protected void initializeDriver() {
		try {
			LOGGER.info("Initializing WebDriver");
			String browserType = getActiveBrowser();
			switch (browserType.toLowerCase()) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				driver = new ChromeDriver(chromeOptions);
				driver.manage().window().maximize();
				break;
			case "firefox":
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				driver = new FirefoxDriver(firefoxOptions);
				driver.manage().window().maximize();
				break;
			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
				driver = new EdgeDriver(edgeOptions);
				driver.manage().window().maximize();
				break;
			default:
				throw new IllegalArgumentException("Invalid browser type: " + browserType);
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			LOGGER.info("WebDriver initialized");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to initialize WebDriver", e);
			throw e;
		}
	}

	/**
	 * Closes the browser and performs cleanup after test execution.
	 */
	protected void tearDown() {
		LOGGER.info("Closing browser");
		if (driver != null) {
			driver.quit();
		}
	}

	/**
	 * Logs an informational message.
	 *
	 * @param message The message to log.
	 */
	protected void logInfo(String message) {
		LOGGER.info(message);
	}

	/**
	 * Logs a warning message.
	 *
	 * @param message The warning message to log.
	 */
	protected void logWarning(String message) {
		LOGGER.warning(message);
	}

	/**
	 * Logs an error message with an associated throwable.
	 *
	 * @param message   The error message to log.
	 * @param throwable The throwable associated with the error.
	 */
	protected void logError(String message, Throwable throwable) {
		LOGGER.log(Level.SEVERE, message, throwable);
	}

	/**
	 * Finds a web element using the provided locator.
	 *
	 * @param locator The locator strategy and value.
	 * @return The web element found.
	 * @throws NoSuchElementException         If the element is not found.
	 * @throws StaleElementReferenceException If the element reference is stale.
	 */
	protected WebElement findElement(By locator) {
		try {
			LOGGER.info("Finding element: " + locator);
			return driver.findElement(locator);
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			LOGGER.log(Level.SEVERE, "Element not found: " + locator, e);
			throw e;
		}
	}

	/**
	 * Checks if a button specified by the locator is clickable.
	 *
	 * @param buttonLocator The locator strategy and value for the button.
	 * @return True if the button is clickable, false otherwise.
	 */
	public boolean isButtonClickable(By buttonLocator, int timeout) {
		try {
			LOGGER.info("Checking if button is clickable: " + buttonLocator);

			// Explicit wait for the button to be clickable
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));

			// Additional conditions to check if the button is clickable
			return button.isEnabled() && button.isDisplayed();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error while checking if button is clickable: " + buttonLocator, e);
			return false;
		}
	}

	/**
	 * Clicks on a web element specified by the locator after checking if it's
	 * clickable.
	 *
	 * @param locator The locator strategy and value.
	 * @param timeout The maximum time to wait for the element to be clickable.
	 */
	protected void click(By locator, int timeout) {
		try {
			LOGGER.info("Checking if element is clickable: " + locator);
			if (isElementClickable(locator, timeout)) {
				LOGGER.info("Clicking element: " + locator);
				findElement(locator).click();
			} else {
				LOGGER.warning("Element is not clickable: " + locator);
				LOGGER.info("Attempting to click through JavaScriptExecutor: " + locator);
				clickWithJavascript(locator);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to click on element: " + locator, e);
			throw e;
		}
	}

	protected void click(By locator) {
		int timeout = 20;
		click(locator, timeout);
	}

	// New method for generic element clickability check
	public boolean isElementClickable(By locator, int timeout) {
		try {
			LOGGER.info("Checking if element is clickable: " + locator);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			return element.isEnabled() && element.isDisplayed();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error while checking if element is clickable: " + locator, e);
			return false;
		}
	}

	/**
	 * Types text into a web element specified by the locator.
	 *
	 * @param locator The locator strategy and value.
	 * @param text    The text to type into the element.
	 * @throws Exception If typing into the element fails.
	 */
	protected void typeText(By locator, String text) {
		try {
			LOGGER.info("Typing text: " + text + " into element: " + locator);

			// Wait for the element to be visible
			waitForElementToBeVisible(locator, 15);

			// Type text into the element
			findElement(locator).sendKeys(text);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to type text into element: " + locator, e);
			throw e;
		}
	}

	protected void typeTextWithEnterAndDownArrow(By locator) {
		try {
			LOGGER.info("Simulating Down arrow key and Enter key press.");

			// Wait for the element to be visible
			waitForElementToBeVisible(locator, 15);

			// Simulate pressing the Down arrow key
			WebElement element = findElement(locator);
			element.sendKeys(Keys.ARROW_DOWN);

			// Simulate pressing the Enter key
			element.sendKeys(Keys.ENTER);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to simulate key presses: " + locator, e);
			throw e;
		}
	}

	/**
	 * Gets the text from a web element specified by the locator.
	 *
	 * @param locator The locator strategy and value.
	 * @return The text of the element.
	 * @throws Exception If getting the text fails.
	 */
	protected String getText(By locator) {
		try {
			LOGGER.info("Getting text from element: " + locator);
			return findElement(locator).getText();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to get text from element: " + locator, e);
			throw e;
		}
	}

	/**
	 * Checks if a web element specified by the locator is displayed.
	 *
	 * @param locator The locator strategy and value.
	 * @return True if the element is displayed, false otherwise.
	 */
	protected boolean isElementDisplayed(By locator) {
		try {
			LOGGER.info("Checking if element is displayed: " + locator);
			return findElement(locator).isDisplayed();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error while checking if element is displayed: " + locator, e);
			return false;
		}
	}

	/**
	 * Waits for a web element specified by the locator to be visible.
	 *
	 * @param locator          The locator strategy and value.
	 * @param timeoutInSeconds The maximum time to wait for the element to be
	 *                         visible.
	 */
	public void waitForElementToBeVisible(By locator, int timeoutInSeconds) {
		LOGGER.info("Waiting for element to be visible: " + locator);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * Takes a screenshot of the current browser window.
	 *
	 * @param fileName The name of the screenshot file.
	 */
	public void takeScreenshot(String fileName) {
		LOGGER.info("Taking screenshot: " + fileName);
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(fileName + ".png");

		try {
			org.apache.commons.io.FileUtils.copyFile(screenshotFile, destinationFile);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to take screenshot", e);
		}
	}

	public byte[] takeScreenshotFile() throws IOException {
		LOGGER.info("Taking screenshot: ");
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
		return fileContent;
//		File destinationFile = new File(fileName + ".png");

//		try {
//			org.apache.commons.io.FileUtils.copyFile(screenshotFile, destinationFile);
//		} catch (IOException e) {
//			LOGGER.log(Level.SEVERE, "Failed to take screenshot", e);
//		}
	}

	/**
	 * Selects an option from a dropdown web element specified by the locator.
	 *
	 * @param dropdownLocator The locator strategy and value for the dropdown.
	 * @param optionText      The visible text of the option to select.
	 */
	public void selectOptionFromDropdown(By dropdownLocator, String optionText) {
		LOGGER.info("Selecting option '" + optionText + "' from dropdown: " + dropdownLocator);
		WebElement dropdown = findElement(dropdownLocator);
		Select select = new Select(dropdown);
		select.selectByVisibleText(optionText);
	}

	/**
	 * Performs a mouse hover on a web element specified by the locator.
	 *
	 * @param elementLocator The locator strategy and value for the element.
	 */
	public void performMouseHover(By elementLocator) {
		LOGGER.info("Performing mouse hover on element: " + elementLocator);
		WebElement element = findElement(elementLocator);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	/**
	 * Performs a right-click on a web element specified by the locator.
	 *
	 * @param elementLocator The locator strategy and value for the element.
	 */
	public void rightClick(By elementLocator) {
		LOGGER.info("Performing right-click on element: " + elementLocator);
		WebElement element = findElement(elementLocator);
		Actions actions = new Actions(driver);
		actions.contextClick(element).perform();
	}

	/**
	 * Performs a double-click on a web element specified by the locator.
	 *
	 * @param elementLocator The locator strategy and value for the element.
	 */
	public void doubleClick(By elementLocator) {
		LOGGER.info("Performing double-click on element: " + elementLocator);
		WebElement element = findElement(elementLocator);
		Actions actions = new Actions(driver);
		actions.doubleClick(element).perform();
	}

	/**
	 * Switches to a frame specified by the locator.
	 *
	 * @param frameLocator The locator strategy and value for the frame.
	 */
	public void switchToFrame(By frameLocator) {
		LOGGER.info("Switching to frame: " + frameLocator);
		driver.switchTo().frame(findElement(frameLocator));
	}

	/**
	 * Switches back to the default content.
	 */
	public void switchToDefaultContent() {
		LOGGER.info("Switching to default content");
		driver.switchTo().defaultContent();
	}

	/**
	 * Switches to a window specified by its handle.
	 *
	 * @param windowHandle The handle of the window to switch to.
	 */
	public void switchToWindow(String windowHandle) {
		LOGGER.info("Switching to window: " + windowHandle);
		driver.switchTo().window(windowHandle);
	}

	/**
	 * Clears browser cookies.
	 */
	public void clearCookies() {
		LOGGER.info("Clearing browser cookies");
		driver.manage().deleteAllCookies();
	}

	/**
	 * Asserts that two text values are equal.
	 *
	 * @param actual   The actual text value.
	 * @param expected The expected text value.
	 * @param message  The assertion failure message.
	 */
	public void assertTextEquals(String actual, String expected, String message, By elementLocator) {
		try {
			LOGGER.info("Asserting text equality: Actual - " + actual + ", Expected - " + expected);

			// Explicit wait for the element to be present
			WebElement element = waitForElement(elementLocator, Duration.ofSeconds(10));

			// Now you can safely get the text of the element
			String elementText = element.getText();

			org.testng.Assert.assertEquals(elementText, expected, message);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Text assertion failed", e);
			throw e;
		}
	}

	/**
	 * Asserts that a web element specified by the locator is displayed.
	 *
	 * @param elementLocator The locator strategy and value for the element.
	 * @param message        The assertion failure message.
	 */

	public void assertElementDisplayed(By elementLocator) {
		try {
			LOGGER.info("Asserting element is displayed: " + elementLocator);

			// Explicit wait for the element to be present
			waitForElementToBeVisible(elementLocator, 20);
			WebElement element = waitForElement(elementLocator, Duration.ofSeconds(20));

			org.testng.Assert.assertTrue(element.isDisplayed(), "Element Not Displayed");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Element display assertion failed", e);
			throw e;
		}
	}

	public void openPortal(String portalName) {
		try {
			LOGGER.info("Opening portal: " + portalName);
			String portalLink = getPortalLink(portalName);

			if (portalLink != null) {
				driver.get(portalLink);
			} else {
				logWarning("Portal link is null. Cannot open the portal.");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to open portal: " + portalName, e);
			throw e;
		}
	}

	/**
	 * Waits for a web element specified by the locator to be present.
	 *
	 * @param locator          The locator strategy and value.
	 * @param timeoutInSeconds The maximum time to wait for the element to be
	 *                         present.
	 * @return The web element found.
	 * @throws RuntimeException If the element is not found within the specified
	 *                          timeout.
	 */
	protected WebElement waitForElement(By locator, Duration timeoutInSeconds) {
		try {
			LOGGER.info("Waiting for element to be present: " + locator);
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Element not present: " + locator, e);
			throw new RuntimeException("Element not present: " + locator, e);
		}
	}

	/**
	 * Gets the portal link for the specified portal name from the configuration
	 * file.
	 *
	 * @param portalName The name of the portal.
	 * @return The portal link.
	 * @throws RuntimeException If the portal link cannot be retrieved.
	 */
	public String getPortalLink(String portalName) {
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader(getConfigFilePath()));
			JSONObject jsonObject = (JSONObject) obj;
			String environment = (String) jsonObject.get("activeEnvironment");
			JSONObject environments = (JSONObject) jsonObject.get("environments");

			if (environments.containsKey(environment)) {
				JSONObject portals = (JSONObject) environments.get(environment);

				if (portals.containsKey(portalName)) {
					return (String) portals.get(portalName);
				} else {
					throw new RuntimeException("Portal not found: " + portalName);
				}
			} else {
				throw new RuntimeException("Environment not found: " + environment);
			}
		} catch (IOException | ParseException e) {
			LOGGER.log(Level.SEVERE, "Failed to get portal link", e);
			throw new RuntimeException("Failed to get portal link", e);
		}
	}

	public String getActiveBrowser() {
		JSONParser parser = new JSONParser();

		try {
			// Parse the JSON file
			Object obj = parser.parse(new FileReader(getConfigFilePath()));
			JSONObject jsonObject = (JSONObject) obj;

			// Get the value of "activebrowser" from the JSON
			return (String) jsonObject.get("activebrowser");
		} catch (Exception e) {
			e.printStackTrace();
			// Handle exceptions as needed
			return null;
		}
	}
	// ... (existing code)

	/**
	 * Gets the path of the configuration file.
	 *
	 * @return The path of the configuration file.
	 */
	private String getConfigFilePath() {
		// Provide the path to your configuration file
		return "src/test/java/ConfigFiles/Environments.json";
	}

	/**
	 * Gets the current URL of the page.
	 *
	 * @return The current URL.
	 */
	public String getCurrentURL() {
		try {
			LOGGER.info("Getting current URL");
			return driver.getCurrentUrl();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to get current URL", e);
			throw e;
		}
	}

	/**
	 * Scrolls to a specific element on the page.
	 *
	 * @param elementLocator The locator strategy and value for the element.
	 */
	public void scrollToElement(By elementLocator) {
		try {
			LOGGER.info("Scrolling to element: " + elementLocator);
			WebElement element = findElement(elementLocator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to scroll to element: " + elementLocator, e);
			throw e;
		}
	}

	/**
	 * Uploads a file using the provided file path.
	 *
	 * @param fileInputLocator The locator strategy and value for the file input
	 *                         element.
	 * @param filePath         The path of the file to upload.
	 */
	public void uploadFile(By fileInputLocator, String filePath) {
		try {
			LOGGER.info("Uploading file: " + filePath);
			WebElement fileInput = findElement(fileInputLocator);
			fileInput.sendKeys(filePath);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to upload file: " + filePath, e);
			throw e;
		}
	}

	/**
	 * Downloads a file from a given URL to a local path.
	 *
	 * @param fileURL The URL of the file to download.
	 * @param saveTo  The local path where the file should be saved.
	 */
	public void downloadFile(String fileURL, String saveTo) {
		// Implement the logic to download a file from a URL to a local path
		// This can involve using libraries like Apache HttpClient or Java NIO
	}

	/**
	 * Accepts an alert.
	 */
	public void acceptAlert() {
		try {
			LOGGER.info("Accepting alert");
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to accept alert", e);
			throw e;
		}
	}

	/**
	 * Dismisses an alert.
	 */
	public void dismissAlert() {
		try {
			LOGGER.info("Dismissing alert");
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to dismiss alert", e);
			throw e;
		}
	}

	/**
	 * Gets the text from an alert.
	 *
	 * @return The text from the alert.
	 */
	public String getAlertText() {
		try {
			LOGGER.info("Getting alert text");
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to get alert text", e);
			throw e;
		}
	}

	/**
	 * Selects multiple chips from a chip list specified by the locator.
	 *
	 * @param chipListLocator The locator strategy and value for the chip list.
	 * @param chipTexts       The visible texts of the chips to select.
	 */
	public void selectMultipleChips(By chipListLocator, String... chipTexts) {
		LOGGER.info("Selecting multiple chips from chip list: " + chipListLocator);
		WebElement chipList = findElement(chipListLocator);

		for (String chipText : chipTexts) {
			By chipLocator = By.xpath("//mat-basic-chip[contains(text(), '" + chipText + "')]");
			WebElement chip = chipList.findElement(chipLocator);

			// Assuming there's a radio button inside the chip, you can click it to select
			// the chip
			WebElement radioButton = chip.findElement(By.cssSelector(".mat-icon-chip"));
			radioButton.click();
		}
	}

	/**
	 * Selects multiple options from a multi-select dropdown specified by the
	 * locator.
	 *
	 * @param dropdownLocator The locator strategy and value for the dropdown.
	 * @param optionTexts     The visible texts of the options to select.
	 */
	public void selectMultipleOptionsFromDropdown(By dropdownLocator, String... optionTexts) {
		By dropdowncontainer = By.id("multi-select-items-container");
		By donebutton = By.id("multi-select-done-button");
		LOGGER.info("Selecting multiple options from dropdown: " + dropdownLocator);
//		WebElement dropdown = findElement(dropdowncontainer);
		driver.findElement(dropdownLocator).click();
		waitForElementToBeVisible(dropdowncontainer, 10);
		// Assuming there's a mat-checkbox inside each option
		for (String optionText : optionTexts) {
			By optionLocator = By.xpath("//mat-checkbox[@id=  'multi-select-checkbox-" + optionText + "']");
			WebElement optionCheckbox = driver.findElement(optionLocator);

			// Click the checkbox to select the option
			optionCheckbox.click();

		}
		driver.findElement(donebutton).click();
	}

	/**
	 * Scrolls to a specific element on the page.
	 *
	 * @param element The web element to scroll to.
	 */
	public void scrollToElement(WebElement element) {
		try {
			LOGGER.info("Scrolling to element: " + element);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to scroll to element", e);
			throw e;
		}
	}

	/**
	 * Clicks on a web element using JavaScript.
	 *
	 * @param element The web element to click.
	 */
	public void clickWithJavascript(By element) {
		try {

			LOGGER.info("Clicking element with JavaScript: " + element);
			WebElement element1 = driver.findElement(element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element1);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to click element with JavaScript", e);
			throw e;
		}
	}

	/**
	 * Checks if a field specified by the locator is typable.
	 *
	 * @param fieldLocator     The locator strategy and value for the field.
	 * @param timeoutInSeconds The maximum time to wait for the field to be typable.
	 * @return True if the field is typable, false otherwise.
	 */
	public boolean isFieldTypable(By fieldLocator, int timeoutInSeconds) {
		try {
			LOGGER.info("Checking if field is typable: " + fieldLocator);

			// Explicit wait for the field to be present and enabled
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
			WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(fieldLocator));

			// Check if the field is enabled and not read-only
			return field.isEnabled() && !field.getAttribute("readonly").equals("true");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error while checking if field is typable: " + fieldLocator, e);
			return false;
		}
	}

	public void selectOptionFromMatSelect(By matSelectLocator, String optionText) {
		LOGGER.info("Selecting option '" + optionText + "' from mat-select: " + matSelectLocator);

		// Wait for the mat-select element to be clickable
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement matSelect = wait.until(ExpectedConditions.elementToBeClickable(matSelectLocator));

		// Click on the mat-select to open the dropdown
		matSelect.click();

		// Use a more specific XPath to locate the mat-option element with the desired
		// text
		By optionLocator = By.xpath("//mat-option//span[normalize-space()='" + optionText + "']");

		// Wait for the mat-option element to be present in the DOM
		WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));

		// Click on the mat-option to select it
		option.click();
	}

	/**
	 * Opens a website URL.
	 *
	 * @param url The URL of the website to open.
	 */
	public void openWebsite(String url) {
		try {
			LOGGER.info("Opening website: " + url);
			driver.get(url);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to open website: " + url, e);
			throw e;
		}
	}

	/**
	 * Switches to a different tab based on the tab index.
	 *
	 * @param tabIndex The index of the tab to switch to (starting from 0).
	 */
	public void switchToTab(int tabIndex) {
		try {
			LOGGER.info("Switching to tab with index: " + tabIndex);

			// Get all window handles
			Set<String> windowHandles = driver.getWindowHandles();

			// Check if the tab index is valid
			if (tabIndex >= 0 && tabIndex < windowHandles.size()) {
				// Convert set to list for indexing
				List<String> handlesList = new ArrayList<>(windowHandles);

				// Switch to the tab with the specified index
				driver.switchTo().window(handlesList.get(tabIndex));
			} else {
				LOGGER.warning("Invalid tab index: " + tabIndex);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to switch to tab with index: " + tabIndex, e);
			throw e;
		}
	}

	/**
	 * Opens a new tab.
	 */
	public void openNewTab() {
		try {
			LOGGER.info("Opening a new tab");

			// Use JavaScript to open a new tab
			((JavascriptExecutor) driver).executeScript("window.open();");

			// Switch to the newly opened tab
			switchToTab(getTabCount() - 1);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to open a new tab", e);
			throw e;
		}
	}

	/**
	 * Closes the current tab.
	 */
	public void closeTab() {
		try {
			LOGGER.info("Closing the current tab");

			// Close the current tab
			driver.close();

			// Switch back to the first tab (index 0)
			switchToTab(0);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to close the current tab", e);
			throw e;
		}
	}

	/**
	 * Gets the number of open tabs.
	 *
	 * @return The number of open tabs.
	 */
	public int getTabCount() {
		try {
			LOGGER.info("Getting the number of open tabs");

			// Get all window handles
			Set<String> windowHandles = driver.getWindowHandles();

			// Return the count of open tabs
			return windowHandles.size();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to get the number of open tabs", e);
			throw e;
		}
	}

}
