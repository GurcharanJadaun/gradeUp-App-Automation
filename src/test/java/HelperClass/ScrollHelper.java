package HelperClass;

import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.touch.TouchActions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class ScrollHelper {
	int height,width;
	AppiumDriver<MobileElement> driver;
	public ScrollHelper(AppiumDriver<MobileElement> driver){
		this.driver=driver;
		Dimension dim=driver.manage().window().getSize();
		height=dim.getHeight();
		width=dim.getWidth();
	}
public void scrollPageDown() {
	TouchAction finger=new TouchAction(driver);
	finger.press(PointOption.point((int) (width*0.5), (int)(0.9*height)))
	.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
			.moveTo(PointOption.point((int) (width*0.5), 0));
	finger.perform();
	
	}
public void scrollPageUp() {
	TouchAction finger=new TouchAction(driver);
	finger.press(PointOption.point((int) (width*0.5), 0))
	.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
			.moveTo(PointOption.point((int) (width*0.5), (int)(0.9*height)))
			.release();
	finger.perform();
	
	}
public void placeElementOnTop(int endPoint,int startPoint) {
	
	//height=startPoint;
	TouchAction finger=new TouchAction(driver);
	try {
	int time=(int) ((startPoint-endPoint)*4);
	if(time<1000) {
		time=1000;
	}
	finger.press(PointOption.point((int) (width*0.5), startPoint))
	.waitAction(WaitOptions.waitOptions(Duration.ofMillis(time)))
			.moveTo(PointOption.point((int) (width*0.5), endPoint))
			.release();
	finger.perform();
	}catch(Exception ex) {
		System.out.println("exception in Scroll helper"+ex.getMessage());
	}
	}
}
