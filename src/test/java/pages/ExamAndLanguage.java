package pages;

import java.util.Map;

import HelperClass.WaitHelper;
import HelperClass.WaitParams;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ExamAndLanguage {
	AppiumDriver<MobileElement> driver;
	Map<String,String> xpath;
	WaitHelper wait=new WaitHelper();
	public ExamAndLanguage(AppiumDriver<MobileElement> driver,Map<String,String> xpath){
		this.driver=driver;
		this.xpath=xpath;
	}
	private boolean isPageVisible(String xpathForTitle) {
		boolean result=false;
		WaitParams param=new WaitParams();
		param.setParamters(10, xpathForTitle);
		result=wait.presenceOfElementInDom(driver, param);
		return result;
	}
	public boolean selectExamAndLanguage() {
		boolean result=false;
		WaitParams fieldToBeClicked=new WaitParams();
		WaitParams expectedField=new WaitParams();
		
		fieldToBeClicked.setTime(10);
		expectedField.setTime(10);
		
		if(isPageVisible(xpath.get("gradeupTitle"))) {
			fieldToBeClicked.setXpath(xpath.get("gradeupExamListItem"));
			expectedField.setXpath(xpath.get("languageTitle"));
			if(wait.clickAndWait(driver, fieldToBeClicked, expectedField)) {
				fieldToBeClicked.setXpath(xpath.get("languageContinueButton"));
				expectedField.setXpath(xpath.get("loginButton"));
				if(wait.clickAndWait(driver, fieldToBeClicked, expectedField)) {
					result=true;
				}
			}
		}
			return result;
	}
}
