package page.base;

import com.force.ui.base.ProjectHooks;

public class HomePage extends ProjectHooks {
	
	public HomePage clickAppLauncher() {
		click(".slds-icon-waffle", "App Launcher", "Icon");
		return this;

	}

	public HomePage clickViewAll() {
		click("//button[text()='View All']", "View All", "Link");
		return this;

	}

	public HomePage typeSearchApps(String appName) {
		type("//input[@placeholder='Search apps or items...']", appName, "Search Apps");
		return this;

	}

	public HomePage clickApp(String appName) {
		click("(//span[text()='All Apps']/following::mark[text()='"+appName+"'])[1]", appName);
		return this;

	}

}
