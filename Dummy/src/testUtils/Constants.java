/*
 * 
 */
package testUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

/***************************
 * The Class Constants includes constants for the LinksTest class.
 ************************/
public class Constants {
	
/** The dummy actions number. */
	//DummyTestConstants
	public static int dummyActionsNumber = 100;
	
	/** The dummy timeout. */
	public static long dummyTimeout = 300;
	
	/** The dummy timeout unit. */
	public static TimeUnit dummyTimeoutUnit = TimeUnit.SECONDS;
	
	/** The dummy device bounderies. */
	public static double dummyDeviceBounderies = 0.05;
	
	/** The dummy max seed. */
	public static int dummyMaxSeed = 5000;
	
	public static int eventsCount = 5;
	
	/**
	 * The Enum logFileTypes.
	 */
	//Log Constants:
	public enum logFileTypes {
		
		/** The Dummy test. */
		DummyTest,
		/** The Broken links. */
		BrokenLinks,
		/** The Fat fingers. */
		YETTOCOME2;
		
		
	}
	
	/********************************************************************
	 * Takes a screenshot of a given driver.
	 * by default screenshot are saved under test-output/screenshot
	 *
	 * @param driver the driver
	 * @return the name of the screenshot
	 * @throws IOException Signals that an I/O exception has occurred.
	 ******************************************************************/
	public static String takeScreenshot(RemoteWebDriver driver,String testName) throws IOException{
		  String filePath = new File("").getAbsolutePath();
		  filePath += "\\test-output\\screenshots";
		  File theDir = new File(filePath);

		  // if the directory does not exist, create it
		  if (!theDir.exists()) {
			  //System.out.println("creating directory: " + directoryName);

			  try{
				  theDir.mkdir();
			  } 
			  catch(SecurityException se){
				  return null;
			  }        
		  }
		  filePath+= "//";
		  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		  String filename = filePath + testName+"_"+getDateAndTime(0) + ".png";
		  FileUtils.copyFile(scrFile, new File(filename));
		  return filename;
	  }
	
	/*********************************************
	 * Gets the date and time.
	 *
	 * @param offset the offset
	 * @return the date and time
	 *****************************************/
	public static String getDateAndTime(int offset){
		  Calendar c = Calendar.getInstance();
		  c.setTime(new Date());
		  c.add(Calendar.DATE, offset);
		  DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss z");
		  return dateFormat.format(c.getTime());
	  }
	
	
}
