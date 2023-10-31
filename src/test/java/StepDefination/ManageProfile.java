package StepDefination;

import org.openqa.selenium.*;
import org.testng.Assert;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ManageProfile {

	Helper helper; // Declare Helper as a class variable
	static By Provider_Profile_Title = By.id("manage-provider-profile-title");
	By lastNameInput = By.id("manage-provider-profile-LastName-input");
	By firstNameInput = By.id("manage-provider-profile-firstName-input");
	By updateFormButton = By.id("manage-provider-profile-updateForm-btn");
	By updateSuccessText = By.id("updateSuccess-text");
	By updateSuccessCloseButton = By.id("updateSuccess-close-btn");

	// Constructor or setup method
	public ManageProfile() {
	}

	@When("User edit provider profile")
	public void userEditsProviderProfile() throws InterruptedException {
		helper = Helper.getInstance();

		helper.waitForElementToBeVisible(lastNameInput, 10);
		helper.waitForElementToBeVisible(firstNameInput, 10);
		Thread.sleep(3000);

		helper.typeText(lastNameInput, "add");
		helper.typeText(firstNameInput, "add");
	}

	@When("User Submits the changes")
	public void userSubmitsTheChanges() throws InterruptedException {
		helper = Helper.getInstance();

		helper.isButtonClickable(updateFormButton, 10);
		helper.scrollToElement(updateFormButton);
		helper.clickWithJavascript(updateFormButton);
	}

	@Then("the changes are reflected")
	public void theChangesAreReflected() {
		helper = Helper.getInstance();

		helper.waitForElementToBeVisible(updateSuccessText, 20);

		String successMessage = helper.getText(updateSuccessText);
		Assert.assertTrue(successMessage.contains("The information has been updated on your"));
//		Assert.assertTrue(successMessage.contains("free"));


		helper.click(updateSuccessCloseButton, 10);
		helper.tearDown();
	}
}
