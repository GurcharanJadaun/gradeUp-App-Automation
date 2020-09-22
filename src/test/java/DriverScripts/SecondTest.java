package DriverScripts;

import static org.testng.Assert.assertTrue;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import HelperClass.ExcelHelper;
import HelperClass.ScrollHelper;
import HelperClass.WaitHelper;
import HelperClass.WaitParams;
import deviceSetup.SetupDeviceCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import pages.Dashboard;
import pages.ExamAndLanguage;
import pages.GradeUpLogin;
import pages.PreviousPapers;


public class SecondTest {
	AppiumDriver<MobileElement> driver;
	WaitHelper wait=new WaitHelper();
	Map<String,String> xpath=new HashMap<String,String>();
	@BeforeClass
	public void beforeClass() {
		ExcelHelper excel=new ExcelHelper();
		xpath=excel.getXpathMap("src/test/resources/objectRepository/ObjectRepository.xls");
		SetupDeviceCapabilities cap=new SetupDeviceCapabilities();
		driver=cap.setupDevice(driver, "Sony");
	}

	@Test(priority=1)
	public void launchApp() {
		boolean result=wait.installWait(driver, "co.gradeup.android", 20);
		Assert.assertTrue(result);
	}
	@Test(priority=2,dependsOnMethods= {"launchApp"})
	public void selectExamAndLanguage(){
		
		ExamAndLanguage obj=new ExamAndLanguage(driver,xpath);
		Assert.assertTrue(obj.selectExamAndLanguage());
		 
	}
	@Test(priority=3,dependsOnMethods= {"launchApp"})
	public void login() {
		GradeUpLogin login=new GradeUpLogin(driver,xpath);
		Assert.assertTrue(login.loginWithEmail());
	}

	@Test(priority=4,dependsOnMethods= {"launchApp"})
	public void dashboard() {
		Dashboard dash=new Dashboard(driver, xpath);
		Assert.assertTrue(dash.navigateToPreviousPapers());
	}
	@Test(priority=5,dependsOnMethods= {"launchApp"})
	public void startTest() {
		PreviousPapers paper= new PreviousPapers(driver, xpath);
		Assert.assertTrue(paper.selectAndAttemptExam());
	}
	
		@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
