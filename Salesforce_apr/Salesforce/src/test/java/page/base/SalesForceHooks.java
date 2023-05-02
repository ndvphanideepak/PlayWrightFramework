package page.base;

import org.testng.annotations.BeforeMethod;

import com.force.ui.base.ProjectHooks;

public class SalesForceHooks extends ProjectHooks{
	
	@BeforeMethod
	public void zlogin() {
		new LoginPage().doLogin();
	}

}
