package com.force.ui.base;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.nio.file.Paths;
import com.force.config.ConfigurationManager;
import com.force.ui.utility.Reporter;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class PlaywrightWrapper extends Reporter{

	int retry = 0;

	public boolean navigate(String url) {
		try {
			getPage().navigate(url);
			reportStep("The page with URL :"+url+" is loaded", "pass");
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public void maximize() {
		try {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			getPage().setViewportSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
		} catch (HeadlessException e) {

		}
	}

	public boolean isVisible(String locator) {
		boolean bVisible = false;
		try {
			getPage().setDefaultTimeout(2000);
			bVisible = getPage().isVisible(locator);
		} catch (PlaywrightException e) {
		}
		getPage().setDefaultTimeout(ConfigurationManager.configuration().timeout());
		return bVisible;

	}

	public boolean type(String locator, String value, String name) {
		try {
			getPage().fill(locator, "");
			getPage().type(locator, value);
			reportStep("The text box :"+name+" is typed with value :"+value, "pass");
			return true;
		} catch (PlaywrightException e) {
			e.printStackTrace();
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean typeAndEnter(String locator, String value, String name) {
		try {
			getPage().fill(locator, "");
			getPage().type(locator, value);
			getPage().press(locator, "Enter");
			reportStep("The text box :"+name+" is typed with value :"+value, "pass");
			return true;
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean uploadFile(String locator, String value, String name) {
		try {
			getPage().setInputFiles(locator, Paths.get(value));
			reportStep("The text box :"+name+" is uploaded with file :"+value, "pass");
			return true;
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean click(String locator, String name) {
		return click(locator, name, "button");
	}

	public boolean click(String locator, String name, String type) {
		try {
			getPage().locator(locator).scrollIntoViewIfNeeded();
			getPage().click(locator);
			reportStep("The "+type+" :"+name+" is clicked", "pass");
			return true;
		} catch (PlaywrightException e) {
			e.printStackTrace();
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean check(String locator, String name) {
		try {
			getPage().check(locator);
			reportStep("The checkbox: "+name+" is checked", "pass");
			return true;
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;
	}

	public boolean selectByText(String locator, String text, String name) {
		try {
			getPage().locator(locator).selectOption(new SelectOption().setLabel(text));
			reportStep("The drop down :"+name+" is selected with value :"+text, "pass");
			return true;
		} catch (PlaywrightException e) {
			e.getMessage();
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean selectByValue(String locator, String value, String name) {
		try {
			getPage().locator(locator).selectOption(value);
			reportStep("The drop down :"+name+" is selected with value index as :"+value, "pass");
			return true;
		} catch (PlaywrightException e) {
			e.printStackTrace();
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean selectByIndex(String locatorId,int index, String name) {
		try {
			Locator locator = getPage().locator(locatorId+" > option");
			if(index > locator.count() || index < 0)  index = (int)Math.floor(Math.random()*(locator.count()-1))+1;
			getPage().locator(locatorId).selectOption(locator.nth(index).getAttribute("value"));
			reportStep("The drop down :"+name+" is selected with index :"+index, "pass");
			return true;
		} catch (PlaywrightException e) {
			e.getMessage();
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean selectByRandomIndex(String locatorId,String name) {
		return selectByIndex(locatorId, -1, name);
	}

	public boolean clickAndType(String ddLocator, String optionLocator, String option, String name) {
		try {
			getPage().click(ddLocator);
			getPage().type(optionLocator,option);
			getPage().keyboard().press("Enter");
			reportStep("The drop down :"+name+" is selected with value :"+option, "pass");
			return true;
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;
	}

	public boolean clickAndChoose(String ddLocator, String optionLocator, String name, String option) {
		try {
			getPage().click(ddLocator);
			getPage().click(optionLocator);
			reportStep("The drop down :"+name+" is selected with value :"+option, "pass");
			return true;
		} catch (PlaywrightException e) {
			e.printStackTrace();
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;
	}
	
	public boolean mouseOver(String locator, String name) {
		try {
			getPage().hover(locator);
			reportStep("The element :"+name+" is moused over successfully", "pass");
			return true;
		} catch (PlaywrightException e) {
			e.printStackTrace();
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;
	}

	public String getInnerText(String locator) {
		try {
			return getPage().locator(locator).innerText();
		} catch(Exception e) {}
		return "";
	}

	public boolean isEnabled(String locator) {
		try {
			return getPage().locator(locator).isEnabled();
		} catch(Exception e) {}
		return false;
	}

	public boolean isDisabled(String locator) {
		try {
			return getPage().locator(locator).isDisabled();
		} catch(Exception e) {}
		return false;
	}

	public boolean verifyDisabled(String locator, String label) {
		try {
			if(isDisabled(locator)) reportStep("The element :"+label+" is disabled as expected", "pass");
			else reportStep("The element :"+label+" is enabled", "warning");
		} catch(Exception e) {}
		return false;
	}
	
	public boolean waitForDisappearance(String locator) {
		try {
			getPage().locator(locator).waitFor(new Locator.WaitForOptions().setTimeout(ConfigurationManager.configuration().timeout()).setState(WaitForSelectorState.HIDDEN));
			return true;
		} catch(Exception e) {}
		return false;
	}

	public boolean waitForAppearance(String locator) {
		try {
			getPage().locator(locator).waitFor(new Locator.WaitForOptions().setTimeout(ConfigurationManager.configuration().timeout()).setState(WaitForSelectorState.VISIBLE));
			return true;
		} catch(Exception e) {}
		return false;
	}

	public boolean verifyTitle(String title) {
		try {
			if(getPage().title().contains(title)) {
				reportStep("The page with title :"+title+" displayed as expected", "pass");
				return true;
			}else
				reportStep("The page with title :"+title+" did not match", "warning");

		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean verifyExactText(String locator, String expectedText) {
		try {
			System.out.println(getPage().innerText(locator));
			if(getPage().innerText(locator).equals(expectedText)) {
				reportStep("The element with text :"+expectedText+" displayed as expected", "pass");
				return true;
			}else
				reportStep("The element with text :"+expectedText+" did not match", "warning");

		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public boolean verifyPartialText(String locator, String expectedText) {
		try {
			if(getPage().innerText(locator).contains(expectedText)) {
				reportStep("The element with text :"+expectedText+" displayed as expected", "pass");
				return true;
			}else
				reportStep("The element with text :"+expectedText+" did not match", "warning");

		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}
	
	public String getInputText(String locator) {
        try {
            return getPage().locator(locator).inputValue();
        } catch (PlaywrightException e) {}
        return "";
    }


	public boolean verifyInputText(String locator,String expectedText) {
		try {
			if(getPage().locator(locator).inputValue().contains(expectedText)) {
				reportStep("The element with text :"+expectedText+" displayed as expected", "pass");
				return true;
			}else
				reportStep("The element with text :"+expectedText+" did not match", "warning");

		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	public String getAttribute(String locator, String attribute) {
		try {
			return getPage().getAttribute(locator, attribute);
		} catch (PlaywrightException e) {}
		return "";
	}

	public boolean verifyAttribute(String locator, String attribute, String expectedText) {
		try {
			if(getPage().getAttribute(locator, attribute).contains(expectedText)) {
				reportStep("The element with text :"+expectedText+" displayed as expected", "pass");
				return true;
			}else
				reportStep("The element with text :"+expectedText+" did not match", "warning");

		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	private boolean reportVisibleSuccess(String locator, String name) {
		getPage().locator(locator).scrollIntoViewIfNeeded();
		reportStep("The element :"+name+" displayed as expected", "pass");
		return true;
	}

	public boolean verifyDisplayed(String locator, String name) {
		try {
			if(getPage().isVisible(locator)) {
				return reportVisibleSuccess(locator, name);
			} else {
				pause("medium");
				if(getPage().isVisible(locator)) {
					return reportVisibleSuccess(locator, name);
				}
			}
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		if(!getPage().isVisible(locator)) 
			reportStep("The element :"+name+" is not visible in the page", "warning");
		return false;
	}

	public boolean verifyNotDisplayed(String locator, String name) {
		try {
			if(!getPage().isVisible(locator)) {
				reportStep("The element :"+name+" is not displayed as expected", "pass");
				return true;
			} else {
				pause("medium");
				reportStep("The element :"+name+" is not displayed as expected", "pass");
				return true;
			}
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		if(!getPage().isVisible(locator)) 
			reportStep("The element :"+name+" is visible in the page", "warning");
		return false;
	}

	public boolean verifyMandatory(String locator, String name) {
		return verifyMandatory(locator, name, true);
	}

	private boolean verifyEnabledReport(String locator, String name, boolean report, String type) {
		if(!type.equalsIgnoreCase("")) {
			type = "warning";
		}
		try {
			if(getPage().isVisible(locator)) {
				if(report) reportStep("The element :"+name+" is editable", "pass", report);
				return true;
			} else {
				reportStep("The element :"+name+" is not editable", type, report);
			}
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;
	}

	public boolean verifyEnabled(String locator, String name, boolean report) {
		return verifyEnabledReport(locator, name, report, "");
	}

	public boolean verifyEnabledStrict(String locator, String name, boolean report) {
		return verifyEnabledReport(locator, name, report, "fail");
	}

	public boolean verifyMandatory(String locator, String name, boolean report) {
		try {
			if(getPage().isVisible(locator)) {
				if(report) reportStep("The element :"+name+" marked as mandatory", "pass", report);
				return true;
			} else {
				reportStep("The element :"+name+" is not marked as mandatory", "warning", report);
			}
		} catch (PlaywrightException e) {
			reportStep("PlaywrightException : \n" + e.getMessage(), "fail");
		}
		return false;

	}

	protected void pause(String type) {
		try {
			switch (type.toLowerCase()) {
			case "low":
				Thread.sleep(ConfigurationManager.configuration().pauseLow());
				break;
			case "medium":
				Thread.sleep(ConfigurationManager.configuration().pauseMedium());
				break;
			case "high":
				Thread.sleep(ConfigurationManager.configuration().pauseHigh());
				break;
			default:
				Thread.sleep(ConfigurationManager.configuration().pauseLow());
				break;
			}
		} catch (Exception e) { }
	}

}
