package StepDefination;

import org.openqa.selenium.*;
import io.cucumber.java.en.And;

public class Dashboard {

	Helper helper;
	By accountCircleBtn = By.id("accountCircle-btn");
	By manageProfileBtn = By.id("manage-profile-btn");

	@And("User navigates to Manage Profile")
	public void userNavigatesToManageProfile() {
		helper = Helper.getInstance();

		helper.click(accountCircleBtn);
		helper.click(manageProfileBtn);
		helper.assertElementDisplayed(ManageProfile.Provider_Profile_Title);
	}

}
