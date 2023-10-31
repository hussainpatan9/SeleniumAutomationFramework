package StepDefination;


import org.openqa.selenium.By;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class ExternalUser {
	Helper helper;
	By emailField = By.xpath("//input[@type='email']");
	By nextbtn = By.xpath("//input[@type='submit']");
	By passwordfield = By.xpath("//input[@type='password']");
	By signintext = By.className("text-title");
	By CRCHeader = By.xpath("//p[normalize-space()='CRC Dashboard']");
	By Hamburger = By.id("toggle-sidenav-btn");
	By Setups = By.xpath("//mat-expansion-panel-header[@id='mat-expansion-panel-header-2']");
	By ExternalUsers = By.xpath("//mat-list-item[@id='sidenav-manageUsers-btn']");
	By CreateUser = By.xpath("//button[@id='Manage External Users_CREATE USER']");
	By PersonaDropdown = By.id("userInformation-role-select");
	By LastnameField = By.id("userInformation-lastName-input");
	By FirstnameField= By.id("userInformation-firstName-input");
	By Middlenameield = By.id("userInformation-middleName-input");
	By PhoneTypeDropdown = By.id("extendedPhone-role-select");
	By PhonenumField = By.id("extendedPhone-phoneNumber-input");
	By EmailField = By.id("userInformation-email-input");
	By CredentialsDropdown = By.id("userInformation-credentials-select");
	By OrganizationDropdown = By.id("userInformation-organization-select");
	By login_anchor = By.id("goToLogin-anchor");
	@Given("User is logged in to CRC Portal")
	public void user_is_logged_in_to_crc_portal() throws InterruptedException {

		helper = Helper.getInstance();
		helper.initializeDriver();
		helper.openPortal("CRC Portal");
		helper.typeText(emailField, "user@example.com"); // Replace with a generic email
		helper.click(nextbtn);
		helper.typeText(passwordfield, "SecurePassword123");
		helper.isButtonClickable(nextbtn, 10);
		helper.click(nextbtn);
		helper.waitForElementToBeVisible(signintext, 10);
		helper.click(nextbtn);
		Thread.sleep(15000);
		if (helper.isElementDisplayed(login_anchor)) {
			helper.click(login_anchor);
			Thread.sleep(10000);
			helper.waitForElementToBeVisible(CRCHeader, 35);
		}
		helper.waitForElementToBeVisible(CRCHeader, 35);
	}

	@Given("navigates to Manage External User")
	public void navigates_to_manage_external_user() throws InterruptedException {
		helper = Helper.getInstance();
		Thread.sleep(4000);
		helper.click(Hamburger);
		helper.click(Setups);
		helper.click(ExternalUsers);
		Thread.sleep(7000);
		helper.assertElementDisplayed(CreateUser);
	}

	@When("User fills Create External User Form for Provider {}")
	public void user_fills_create_external_user_form_for_provider(String email) throws InterruptedException {
		helper = Helper.getInstance();
		By roledropdown = By.className("add-items-button");
		By faxField =By.id("userInformation-fax-input");
		By directAdressField =By.id("DirectAddress-input");
		By AdressField =By.id("addressGooglePlaces-addressAutocomplete");
		By donebutton = By.id("createUser-doneForm-btn");
		By pageTitle = By.className("page-title");
		helper.click(CreateUser);
		helper.typeText(LastnameField, "Hussain");
		helper.typeText(FirstnameField, "Khuzema");
		helper.typeText(Middlenameield, "Middle");
		helper.selectOptionFromMatSelect(PhoneTypeDropdown, "Cell");
		helper.typeText(PhonenumField, "87678876789");
		helper.typeText(EmailField, email);
		helper.selectOptionFromMatSelect(PersonaDropdown, "Provider");
		Thread.sleep(7000);
		helper.selectOptionFromMatSelect(CredentialsDropdown,"Advanced Practice Clinician");
		Thread.sleep(2000);
		helper.selectOptionFromMatSelect(OrganizationDropdown, "Bay Cove");
		helper.selectMultipleOptionsFromDropdown(roledropdown,"Provider","UsamaProviderRole" );
		helper.typeText(faxField,"5555555555555");
		helper.typeText(directAdressField,"test@mailinator.com");
		helper.typeText(AdressField,"400 Tradecenter Dr");
		Thread.sleep(2000);
		helper.typeTextWithEnterAndDownArrow(AdressField);
		helper.click(donebutton);
		helper.assertElementDisplayed(CreateUser);
	}
	
	@Given("User gets verification link from {}")
	public void user_gets_verification_link_from_email(String email) {
		helper = Helper.getInstance();
		By MailinatorInboxSearch = By.id("search");
		By goButton = By.xpath("//button[normalize-space()='GO']");
		By acceptButton = By.className("accept-button");
		helper.openNewTab();
		helper.openWebsite("https://www.mailinator.com/");
		helper.typeText(MailinatorInboxSearch,email);
		helper.click(goButton);
		select_first_email();
		helper.click(acceptButton);
		
		
	}
	public void select_first_email() {
		
		By firstemailBy =By.xpath("//table[@class='table-striped jambo_table']/tbody/tr[position()=1]");
		helper.click(firstemailBy);
	}

}
