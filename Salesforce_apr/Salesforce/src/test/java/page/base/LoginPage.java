package page.base;

import com.force.config.ConfigurationManager;
import com.force.ui.base.ProjectHooks;

public class LoginPage extends ProjectHooks {
	// #username -- User Name
	// #password -- Password
	// #Login -- Login

	public LoginPage typeUserName(String username) {
		type("#username", username, "User Name");
		return this;
	}

	public LoginPage typePassword(String password) {
		type("#password", password, "Password");
		return this;
	}

	public LoginPage clickLogin() {
		click("#Login", "Log In");
		return this;
	}
	
	public LoginPage doLogin() {
		return typeUserName(ConfigurationManager.configuration().appUserName())
				.typePassword(ConfigurationManager.configuration().appPassword())
				.clickLogin();
	}

}
