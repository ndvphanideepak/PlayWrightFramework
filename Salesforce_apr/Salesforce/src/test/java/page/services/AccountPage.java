package page.services;

import com.force.data.dynamic.FakerDataFactory;
import com.force.ui.base.ProjectHooks;

public class AccountPage extends ProjectHooks {

	public AccountPage typeAccountName() {

		// type("//label[text()='Account Name']/following::input[1]", accName, "Account
		// Name");

		type("//label[text()='Account Name']/following::input[1]", FakerDataFactory.getFirstName(), "Account Name");
		return this;
	}

	public AccountPage typeAccountNumber() {
		type("//label[text()='Account Number']/following::input[1]", FakerDataFactory.getContactNumber(),
				"Account Number");
		return this;
	}

	public AccountPage clickSave() {
		click("//button[@class='slds-button slds-button_brand']", "Save");
		return this;

	}

}
