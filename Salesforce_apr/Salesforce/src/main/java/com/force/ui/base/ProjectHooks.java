package com.force.ui.base;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import com.force.config.ConfigurationManager;
import com.force.ui.utility.DataLibrary;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Video;

public class ProjectHooks extends PlaywrightWrapper  {
	
	protected static final ThreadLocal<String> bearerToken = new ThreadLocal<String>();
	protected static final ThreadLocal<String> email = new ThreadLocal<String>();

	public static Map<String,String> newEmails;
	public static String videoFolderName = "videos/";
	
	@BeforeSuite
	public void initSuite() {
		newEmails = new HashMap<String, String>();
		videoFolderName = createFolder("videos");
		startReport();
	}
	
	@Override	
	public String takeSnap(){
		return new String(Base64.getEncoder().encode(getPage().screenshot())); 
	}
	
	@BeforeMethod
	public void init() {
		try {
			// Launch the browser (based on configuration) in head(less) mode (based on configuration)
			setDriver(ConfigurationManager.configuration().browser(), ConfigurationManager.configuration().headless());
			
			// Set the extent report node for the test
			setNode();
			
			// Set the video recording ON using context
			context.set(getDriver().newContext(new Browser.NewContextOptions()
					.setIgnoreHTTPSErrors(true)
					.setRecordVideoDir(Paths.get(folderName))));
			
			// Create a new page and assign to the thread local
			page.set(getContext().newPage());
			
			// Set the timeout based on the configuration
			getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
			
			// Get the screen size and maximize
			maximize();
			
			// Load the page with URL based on configuration
			navigate(ConfigurationManager.configuration().baseUrl());
		} catch (Exception e) {
			e.printStackTrace();
			reportStep("The browser and the URL could not be loaded as expected", "fail");
		}
	}
	
	@BeforeClass(alwaysRun = true)
	public void startTestcaseReporting() {
		startTestCase();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		try {
			Video video = getPage().video();
			getPage().close();
			video.saveAs(Paths.get(videoFolderName+"/"+testcaseName+".webm"));
			getContext().close(); // video will be saved
			video.delete();
			getPlaywright().close();
			endResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		return DataLibrary.readExcelData(dataFileName);
	}


	
}
