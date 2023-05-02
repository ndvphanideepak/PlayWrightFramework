package page.services;

import com.force.ui.base.ProjectHooks;

public class ServiceDashboardPage extends ProjectHooks{
	
	
			
			
	public ServiceDashboardPage clickTab(String tabName) {
		click("(//span[text()='"+tabName+"'])[1]", tabName, "Tab");
		return this;	
	}
	
	public ServiceDashboardPage clickMenu(String menuName) {
		click("//div[@title='"+menuName+"']", menuName, "Menu");
		return this;	
	}

}
