import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

import org.openqa.selenium.remote.*;
import com.perfectomobile.selenium.util.EclipseConnector;
import testUtils.*;

public class Example {
	
	public static void main(String[] args) throws MalformedURLException, IOException {

		System.out.println("Run started");
		/**************************************
		 * Example of a mobile Driver
		 **************************************/		
		System.out.println("Run started");
		String browserName = "mobileOS";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		String host = "myHost.perfectomobile.com";		
		capabilities.setCapability("user", "myUser");
		capabilities.setCapability("password", "myPassword");
		capabilities.setCapability("deviceName", "12345");
		
		// Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
		// capabilities.setCapability("automationName", "PerfectoMobile");
		
		// Call this method if you want the script to share the devices with the recording plugin.
		setExecutionIdCapability(capabilities);

        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);

        
				
        /**************************************
         * Below is a sample of the usage of the dummyTest
         *************************************/
		try {
			DummyTest dummyTest = new DummyTest(driver);
			
			//Option1: Use default values of 100 gestures or 300 seconds:
			if (dummyTest.dummyTest("Maps")){
				System.out.println("Found an Exception");
			}
			
			//Option2: limit dummy test to 30 gestures:
			if (dummyTest.dummyTest("Maps", 30)){
				System.out.println("Found an Exception");
			}
			//Option3: limit dummy test to 60 seconds:
			if (dummyTest.dummyTest("Maps", 60, TimeUnit.SECONDS)){
				System.out.println("Found an Exception");
			}
			
			//Option4: limit dummy test to either 10 commands or 60 seconds:
			if (dummyTest.dummyTest("Maps", 10, 60, TimeUnit.SECONDS)){
				System.out.println("Found an Exception");
			}
			
			//option 5: set a speciofic seed and execute the dummy test
			dummyTest.setRandomSeed(30);
			dummyTest.dummyTest("Maps");
					
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				driver.close();
				
				// In case you want to down the report or the report attachments, do it here.
				// RemoteWebDriverUtils.downloadReport(driver, "pdf", "C:\\test\\report");
				// RemoteWebDriverUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
				// RemoteWebDriverUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			driver.quit();
		}
		
		System.out.println("Run ended");
	}
	
		
 	private static void setExecutionIdCapability(DesiredCapabilities capabilities) throws IOException {
		EclipseConnector connector = new EclipseConnector();
		String executionId = connector.getExecutionId();
		capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
	}
}
