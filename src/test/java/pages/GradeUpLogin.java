package pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

import HelperClass.WaitHelper;
import HelperClass.WaitParams;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class GradeUpLogin {
	AppiumDriver<MobileElement> driver;
	Map<String,String> xpath;
	WaitHelper wait=new WaitHelper();
	public GradeUpLogin(AppiumDriver<MobileElement> driver,Map<String,String> xpath){
		this.driver=driver;
		this.xpath=xpath;
	}
	private void closeAdvertisement() {
		WaitParams param=new WaitParams();
		param.setParamters(4, xpath.get("advertisement"));
		wait.waitAndClick(driver, param);
	}
	public boolean loginWithEmail() {

		boolean result=false;
		WaitParams fieldToBeClicked=new WaitParams();
		WaitParams textField=new WaitParams();
		
		fieldToBeClicked.setTime(10);
		textField.setTime(10);
		
		fieldToBeClicked.setXpath(xpath.get("loginButton"));
		if(wait.waitAndClick(driver, fieldToBeClicked)) {
		closeAdvertisement();
		textField.setXpath(xpath.get("emailTextBox"));
		if(wait.enterTextInTextBox(driver, textField, "gurcharanjadaun@gmail.com")) {
			textField.setXpath(xpath.get("emailPassword"));
			if(wait.enterTextInTextBox(driver, textField, "123456789")) {
				Map<String,Object> scrollMap=new HashMap<String,Object>();
				 
				scrollMap.put("numberOfScrolls", 1);
				 scrollMap.put("scrollDir", "down");
				 scrollMap.put("targetElement", By.xpath(xpath.get("emailLoginButton")));
				if(wait.scrollToView(driver, scrollMap)) {
					fieldToBeClicked.setParamters(5, xpath.get("emailLoginButton"));		
					result=wait.waitAndClick(driver, fieldToBeClicked);
				}
				
			}
		}
		}
		return result;
	}
}
