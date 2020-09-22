package deviceSetup;



import java.io.FileReader;
import java.net.URL;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class SetupDeviceCapabilities {
public AppiumDriver<MobileElement> setupDevice(AppiumDriver<MobileElement> driver,String device) {
	JSONParser path=new JSONParser();
	JSONObject obj=null;
	try {
		DesiredCapabilities driverCap=new DesiredCapabilities();
		obj=(JSONObject) path.parse(new FileReader("src/test/resources/config/deviceConfiguration.json"));
		Map<String,String> cap=(Map<String, String>) obj.get("capabilities");
		for(Map.Entry entry:cap.entrySet()) {
			driverCap.setCapability(entry.getKey().toString(), entry.getValue());
		}
		Map<String,Object> deviceNames=(Map<String,Object>) obj.get("environment");
		Map<String,String> deviceCap=(Map<String, String>) deviceNames.get(device);
		for(Map.Entry entry:deviceCap.entrySet()) {
			driverCap.setCapability((String) entry.getKey(), entry.getValue());
		}
		driver=new AndroidDriver(new URL(obj.get("server").toString()),driverCap);
	}catch(Exception ex) {
		System.out.println("Exception while setting up the driver "+ex.getMessage());
	}
	return driver;
}
}
