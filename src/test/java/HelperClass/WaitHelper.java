package HelperClass;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.MobileElement;
import io.appium.java_client.functions.ExpectedCondition;

public class WaitHelper {
	public boolean enterTextInTextBox(AppiumDriver<MobileElement> driver, WaitParams param,String text) {
		boolean result=false;
		try {
			if(presenceOfElementInDom(driver,param)) {
				driver.findElement(By.xpath(param.getXpath())).click();
				waitForKeyboardToAppear(driver);
				
				driver.findElement(By.xpath(param.getXpath())).sendKeys(text);
				driver.hideKeyboard();
				waitForKeyboardToHide(driver);
				result=true;
			}
		}catch(Exception ex) {
			System.out.println("Exception >> "+ex.getLocalizedMessage());
		}
		return result;
		
	}public boolean waitAndClick(AppiumDriver<MobileElement> driver, WaitParams param) {
		boolean result=false;
		try {
			if(presenceOfElementInDom(driver,param)) {
				driver.findElement(By.xpath(param.getXpath())).click();
				result=true;
				Thread.sleep(200);
			}
		}catch(Exception ex) {
			System.out.println("Exception >> "+ex.getLocalizedMessage());
		}
		return result;
		
	}
	public boolean clickAndWait(AppiumDriver<MobileElement> driver, WaitParams fieldToBeClicked,WaitParams expectedField) {
		boolean result=false;
		try {
			if(presenceOfElementInDom(driver,fieldToBeClicked)) {
				driver.findElement(By.xpath(fieldToBeClicked.getXpath())).click();
				
				result=presenceOfElementInDom(driver,expectedField);
			}
		}catch(Exception ex) {
			System.out.println("Exception >> "+ex.getLocalizedMessage());
		}
		return result;
		
	}
	public boolean presenceOfElementInDom(AppiumDriver<MobileElement> driver, WaitParams param) {
		boolean result = false;
		WebDriverWait wait = new WebDriverWait(driver, param.getTime());
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(param.getXpath())));
			//driver.executeScript("argument[0].scrollIntoView()", driver.findElement(By.xpath(param.getXpath())));
			result = true;
		} catch (Exception ex) {
			result = false;
			System.out.println("Exception >> "+ex.getLocalizedMessage());
		}
		return result;
	}

	public boolean elementVisibleOnPage(AppiumDriver<MobileElement> driver, WaitParams param) {
		boolean result = false;
		WebDriverWait wait=new WebDriverWait(driver,param.getTime());
		try {
			if(presenceOfElementInDom(driver, param)) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(param.getXpath())));}
		}catch(Exception ex) {
			result=false;
		}
		return result;
	}
	public boolean elementVisibleOnPage(AppiumDriver<MobileElement> driver, By locator) {
		boolean result = false;
		
		try {
			WebDriverWait wait=new WebDriverWait(driver,1);
			if(wait.until(ExpectedConditions.presenceOfElementLocated(locator))!=null) {
			result=true;}
		}catch(Exception ex) {
			result=false;
			//System.out.println("Exception >> "+locator.toString()+"<<"+ex.getLocalizedMessage());
		}
		return result;
	}
	public boolean installWait(AppiumDriver<MobileElement> driver, String param,int time) {
		boolean result=false;
		WebDriverWait wait = new WebDriverWait(driver,time);
		result=wait.until(waitForInstallation(param));
		return result;
	}
	public boolean waitForKeyboardToAppear(AppiumDriver<MobileElement> driver) {
		boolean result=false;
		WebDriverWait wait = new WebDriverWait(driver,3);
		result=wait.until(waitForQwertyPadToAppear());
		return result;
	}
	public boolean waitForKeyboardToHide(AppiumDriver<MobileElement> driver) {
		boolean result=false;
		WebDriverWait wait = new WebDriverWait(driver,3);
		result=wait.until(waitForQwertyPadToHide());
		return result;
	}
	public boolean placeElementOnTop(AppiumDriver<MobileElement> driver,Map<String,Object> scrollMap) {
		boolean result=false;
		try {
		result=elementVisibleOnPage(driver,(By)scrollMap.get("targetElement"));
		//System.out.println("Result >>"+result);
			}
		catch(NoSuchElementException ex) {
			System.out.println("Excpetion while locating element placeElementOnTop >>"+ex.getMessage());
		}
		if(!result) {
		//	System.out.println("Trying to Scroll>>>>");
		result=scrollToView(driver, scrollMap);}
		if(result) {
			try {
			ScrollHelper scroll=new ScrollHelper(driver);
			int startPoint=driver.findElement((By)(scrollMap.get("targetElement"))).getLocation().getY();
					
			scroll.placeElementOnTop(50, startPoint);}catch(Exception ex) {
				System.out.println("<<exception while scrolling page>>"+ex.getMessage());
			}
		}
		return result;
	}
	public boolean scrollToView(AppiumDriver<MobileElement> driver,Map<String,Object> scrollMap) {
		boolean result=false;
		ScrollHelper scroll=new ScrollHelper(driver);
		/*
		< -------- list of keys ---------->
		 -> numberOfScrolls
		 -> targetElement
		 -> scrollDir
		 * */
		int counter=1;
		
		while(!result&&counter<=((Integer)scrollMap.get("numberOfScrolls")).intValue()) {
			if(((String)scrollMap.get("scrollDir")).equalsIgnoreCase("Up")) {
				scroll.scrollPageUp();
				
				result=elementVisibleOnPage(driver,(By) scrollMap.get("targetElement"));
			}else if(((String)scrollMap.get("scrollDir")).equalsIgnoreCase("Down")) {
				scroll.scrollPageDown();
				result=elementVisibleOnPage(driver,(By) scrollMap.get("targetElement"));
			}
			counter++;
		}
		
		return result;
	}
	
	/*New Expected Conditions*/
	ExpectedCondition<Boolean> waitForInstallation(final String arg){
		return new ExpectedCondition<Boolean>(){

			@SuppressWarnings("unchecked")
			public Boolean apply(WebDriver driver) {
				boolean result=false;
					
				result=((AppiumDriver<MobileElement>)driver).queryAppState(arg).toString().equalsIgnoreCase("RUNNING_IN_FOREGROUND");		
				
				return result;
			}
		};
	}
	
	ExpectedCondition<Boolean> waitForQwertyPadToAppear(){
		return new ExpectedCondition<Boolean>(){

			@SuppressWarnings("unchecked")
			public Boolean apply(WebDriver driver) {
				boolean result=false;					
				result=((AndroidDriver<MobileElement>)driver).isKeyboardShown();					
				return result;
			}
		};
	}
	ExpectedCondition<Boolean> waitForQwertyPadToHide(){
		return new ExpectedCondition<Boolean>(){

			@SuppressWarnings("unchecked")
			public Boolean apply(WebDriver driver) {
				boolean result=false;	
				
				result=!((AndroidDriver<MobileElement>)driver).isKeyboardShown();					
				return result;
			}
		};
	}
	
	
}
