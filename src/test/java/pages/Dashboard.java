package pages;

import java.util.Map;

import org.openqa.selenium.By;

import HelperClass.WaitHelper;
import HelperClass.WaitParams;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Dashboard {
	AppiumDriver<MobileElement> driver;
	Map<String,String> xpath;
	//Map<String,Object>scrollMap;
	WaitHelper wait=new WaitHelper();
	public Dashboard(AppiumDriver<MobileElement> driver,Map<String,String> xpath){
		this.driver=driver;
		this.xpath=xpath;
	}
	
	private void closePhoneNumberNotification() {
		WaitParams param=new WaitParams();
		param.setParamters(25, xpath.get("continueWithPhoneNumber"));
		if(wait.presenceOfElementInDom(driver, param)) {
			WaitParams expectedField=new WaitParams();
			param.setXpath(xpath.get("cancelPhoneNumberNotif"));
			expectedField.setParamters(5, xpath.get("enterMobileNumberHeading"));
			if(wait.clickAndWait(driver, param, expectedField)) {
				param.setXpath(xpath.get("closePageButton"));
				wait.waitAndClick(driver, param);
			}
			
		}else {
			param.setXpath(xpath.get("enterMobileNumberHeading"));
			if(wait.presenceOfElementInDom(driver, param)) {
			param.setXpath(xpath.get("closePageButton"));
			wait.waitAndClick(driver, param);}
		}
	}
	public boolean navigateToPreviousPapers() {
		boolean result=false;
		WaitParams fieldToBeClicked=new WaitParams();
		WaitParams expectedField=new WaitParams();
		closePhoneNumberNotification();
		
		fieldToBeClicked.setParamters(5, xpath.get("previousYearPaperTab"));
		expectedField.setParamters(5, xpath.get("previousYearPaperPageHeader"));
		
		if(wait.clickAndWait(driver, fieldToBeClicked, expectedField)) {
			result=true;
		}
		return result;
	}
}
