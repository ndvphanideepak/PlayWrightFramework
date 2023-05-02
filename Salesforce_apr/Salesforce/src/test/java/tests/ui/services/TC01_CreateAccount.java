package tests.ui.services;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import page.base.HomePage;
import page.base.SalesForceHooks;
import page.services.AccountPage;
import page.services.ServiceDashboardPage;

public class TC01_CreateAccount extends SalesForceHooks{
	
	@BeforeTest
	public void setReportValues() {
		testcaseName = "TC01_CreateAccount";
		testDescription = "Create New Account under Service Module";
	}
	
	@Test
	public void createAccount() {
		
		new HomePage()
		.clickAppLauncher()
		.clickViewAll()
		.typeSearchApps("Service")
		.clickApp("Service");
		
		new ServiceDashboardPage()
		.clickTab("Accounts")
		.clickMenu("New");
		
		new AccountPage()
		.typeAccountName()
		.typeAccountNumber()
		.clickSave();
		
	}

}
