package StepDefination;

import io.cucumber.java.en.And;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
	WebDriver driver;
	WebDriverWait wait;
	Actions actions;
	Helper helper;
	By emailField = By.id("sign-in-email-field");
	By passwordField = By.id("sign-in-password-field");
	By loginBtn = By.id("sign-in-continue-btn");
	By login_anchor = By.id("goToLogin-anchor");
	By download_mobile = By.id("Download-Mobile-App-Menu");
	By main_Logo = By.xpath("//img[@alt='Main Logo']");

	@Given("User is on Provider Login Screen")
	public void user_is_on_Provider_login_screen() {

		helper = Helper.getInstance();
		helper.initializeDriver();
		helper.openPortal("Provider Portal");
		helper.assertElementDisplayed(emailField);
	}

	@And("User click on Submit")
	public void user_click_on_submit() throws InterruptedException {
		// Write code here that turns the phrase above into concrete actions
		helper.click(loginBtn, 10);
		Thread.sleep(7000);
		if (helper.isElementDisplayed(login_anchor)) {
			helper.click(login_anchor);
			Thread.sleep(7000);
			helper.waitForElementToBeVisible(download_mobile, 15);
		}

	}

	@Then("User is logged in")
	public void user_is_logged_in() {

		helper.assertElementDisplayed(main_Logo);
		helper.tearDown();
	}

	@When("User Enter {} and {}")
	public void user_enter_email_and_password(String email, String password) throws InterruptedException {

		helper = Helper.getInstance();
		helper.typeText(emailField, email);
		helper.typeText(passwordField, password);
	}

	@Given("User is logged in to the Provider Portal")
	public void userIsLoggedInToTheProviderPortal() throws InterruptedException {
		helper = Helper.getInstance();
		helper.initializeDriver();
		logintoportal();
	}

	public void logintoportal() throws InterruptedException {
		helper = Helper.getInstance();

		helper.openPortal("Provider Portal");
		helper.assertElementDisplayed(emailField);

		helper.typeText(emailField, "user@example.com"); // Replace with a generic email
		helper.typeText(passwordField, "SecurePassword123");

		helper.click(loginBtn, 10);
		Thread.sleep(10000);
		if (helper.isElementDisplayed(login_anchor)) {
			helper.click(login_anchor);
			Thread.sleep(7000);
			helper.waitForElementToBeVisible(download_mobile, 25);
		}
		helper.assertElementDisplayed(main_Logo);
	}
}
