package com.force.ui.base;


import com.force.config.ConfigurationManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class DriverFactory  {

	private static final ThreadLocal<Playwright> playwright = new ThreadLocal<Playwright>();
	private static final ThreadLocal<Browser> driver = new ThreadLocal<Browser>();
	protected static final ThreadLocal<BrowserContext> context = new ThreadLocal<BrowserContext>();
	protected static final ThreadLocal<Page> page = new ThreadLocal<Page>();

	public void setDriver(String browser, boolean headless) {		
		playwright.set(Playwright.create());

		switch (browser) {
		case "chrome":
			driver.set(getPlaywright().chromium().launch(
					new BrowserType.LaunchOptions().setChannel("chrome")
					.setHeadless(headless)
					.setSlowMo(ConfigurationManager.configuration().slowMotion())));
			break;
		case "egde":
			driver.set(getPlaywright().chromium().launch(
					new BrowserType.LaunchOptions().setChannel("msedge")
					.setHeadless(headless)
					.setSlowMo(ConfigurationManager.configuration().slowMotion())));
			break;
		case "firefox":
			driver.set(getPlaywright().firefox().launch(
					new BrowserType.LaunchOptions()
					.setHeadless(headless)
					.setSlowMo(ConfigurationManager.configuration().slowMotion())));
		case "safari":
			driver.set(getPlaywright().webkit().launch(
					new BrowserType.LaunchOptions()
					.setHeadless(headless)
					.setSlowMo(ConfigurationManager.configuration().slowMotion())));
		default:
			break;
		}
	}

	public Browser getDriver() {
		return driver.get();
	}

	public BrowserContext getContext() {
		return context.get();
	}

	public Page getPage() {
		return page.get();
	}

	public Playwright getPlaywright() {
		return playwright.get();
	}

}
