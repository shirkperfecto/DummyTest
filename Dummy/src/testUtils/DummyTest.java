package testUtils;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.remote.RemoteWebDriver;


import org.openqa.selenium.remote.RemoteWebDriver;


// TODO: Auto-generated Javadoc
/**
 * ****************
 * Dummy test Section:.
 */
public final class DummyTest{
	
	private RemoteWebDriver driver;
	private static Log Log;
	private int resolutionWidth;
	private int resolutionHeight;
	
	/** The rand. */
	private Random rand;
	
	/** The rand helper. */
	private Random randHelper;
	
	/** The start coord. */
	private Point startCoord;
	
	/** The end coord. */
	private Point endCoord;
	
	/** The min value. */
	private int minHeightValue;
	private int minWidthValue;
	
	/** The max value. */
	private int maxHeightValue;
	private int maxWidthValue;
	
	/** The size of range. */
	private int sizeOfRangeHeight;
	private int sizeOfRangeWidth;
	
	/** The seed. */
	private long seed;
	
	/** The is seed random. */
	private boolean isSeedRandom;
	
	
	
	/**
	 * Instantiates a new dummy test.
	 *
	 * @param driver the driver
	 */
	public DummyTest(RemoteWebDriver driver) {
		
		this(driver,-1);
		
	}
	
	/**
	 * Instantiates a new dummy test.
	 *
	 * @param driver the driver
	 * @param seed the seed
	 */
	public DummyTest (RemoteWebDriver driver, long seed){
		//super(driver);
		this.driver = driver;
		this.resolutionHeight = 0;
		this.resolutionWidth = 0;
		randHelper = new Random();
		setRandomSeed(seed);
		
		Log= new Log();
				
		//initialize limits:
		startCoord = new Point();
		endCoord = new Point();
		

		HashMap<String, Object> params = new HashMap<String,Object>();
	   
	   //get all Properties:
	   params.put("property", "resolutionWidth");
	   resolutionWidth = Integer.parseInt((String) driver.executeScript("mobile:handset:info", params));
	   params.put("property", "resolutionHeight");
	   resolutionHeight = Integer.parseInt((String) driver.executeScript("mobile:handset:info", params));
	   
	   //sets limits of the screen
	   minHeightValue = (int) (Constants.dummyDeviceBounderies * this.resolutionHeight);
	   maxHeightValue = (int) this.resolutionHeight - minHeightValue;
	   sizeOfRangeHeight = maxHeightValue - minHeightValue + 1;
	   //sets limits of the screen
	   minWidthValue = (int) (Constants.dummyDeviceBounderies * this.resolutionWidth);
	   maxWidthValue = (int) this.resolutionWidth - minWidthValue;
	   sizeOfRangeWidth = maxWidthValue - minWidthValue + 1;
	   //sizeOfRangeZoom = this.resolutionHeight-
		
		
	   
	}
	
	/**
	 * Gets the random seed.
	 *
	 * @return the random seed
	 */
	public long getRandomSeed(){
		//Log.debug("Get value of Seed is: "+ this.seed);
		return this.seed;
	}
	
	
	/**
	 * Sets the random seed.
	 *
	 * @param seed the new random seed
	 */
	public void setRandomSeed(long seed){
		
		if (seed == -1){
			this.isSeedRandom = true;
			this.seed = randHelper.nextInt(Constants.dummyMaxSeed);
		} else {
			this.isSeedRandom = false;
			this.seed = seed;
		}
		
	}
	
	/**
	 * Dummy test.
	 *
	 * @param appName the app name
	 * @return true, if successful
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean dummyTest(String appName) throws InterruptedException, IOException{
		
		return dummyTest(appName, Constants.dummyActionsNumber, Constants.dummyTimeout, Constants.dummyTimeoutUnit);
	}
	
	/**
	 * Dummy test.
	 *
	 * @param appName the app name
	 * @param actionsNumber the actions number
	 * @return true, if successful
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean dummyTest(String appName , int actionsNumber) throws InterruptedException, IOException{
		
		return dummyTest(appName, actionsNumber, Constants.dummyTimeout, Constants.dummyTimeoutUnit);
	}

	/**
	 * Dummy test.
	 *
	 * @param appName the app name
	 * @param timeout the timeout
	 * @param unit the unit
	 * @return true, if successful
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean dummyTest(String appName, long timeout, TimeUnit unit) throws InterruptedException, IOException{
	
		return dummyTest(appName,Constants.dummyActionsNumber, timeout, unit);
	}
	
	/**
	 * Dummy test.
	 *
	 * @param appName the app name
	 * @param actionsNumber the actions number
	 * @param timeout the timeout
	 * @param unit the unit
	 * @return true, if successful
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean dummyTest(String appName, int actionsNumber, long timeout, TimeUnit unit) throws InterruptedException, IOException{
		
		//parameters
		long startTime = System.currentTimeMillis();
		Map<String, Object> params = new HashMap<>();
		long elapsedTime = 0;
		
		//initialize logs
		Log.setTestLog(appName);
		
		Log.debug("*********************************************************************************************");
		
		rand = new Random();
		if (isSeedRandom)
			this.seed = randHelper.nextInt(Constants.dummyMaxSeed);
		
		rand.setSeed(this.seed);
		
		
		
		Log.debug("************* Starting dummyTest on App: " +appName + "******************");
		Log.debug("************* Seed is set to: " +getRandomSeed() + "******************");
		Log.debug("************* Max actions = "+ actionsNumber +" Or Timeout = " + timeout + " " + unit.toString()+" ******************");
		try {
			
			params.put("name", appName);
			driver.executeScript("mobile:application:open", params);
			Thread.sleep(4000);
			Log.debug("Opened Application: " +appName);

		} catch (Exception e) {
			//Failed to load web page
			Log.error("******End test due to an unknown error when opening application "+ appName);
			return true;
			
		}
		
		
		//start the random actions test		
		for (int i=0; (i<actionsNumber && elapsedTime<unit.toMillis(timeout));i++){
			setRandomPoints();
			randomAction();
			
			//validate app on front:
			if (!appToFront(appName)){
				return false;
			}
			
			//check for exceptions every 10 actions
			if (i%10 == 0){
				if (checkLogForException())	{
					driver.executeScript("mobile:application:close", params);
					Log.error("******End test due to an exception");
					return true;
				}
			}
			long endTime   = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			
							
		}
		
		if (checkLogForException())	{
			driver.executeScript("mobile:application:close", params);
			Log.error("******End test due to an exception");
			return true;
		}
		
		
		//close the application
		driver.executeScript("mobile:application:close", params);
		
		if (elapsedTime>=unit.toMillis(timeout)){
			Log.debug("*************"+ timeout +"  "+ unit.toString()+ " Elapsed***************");
		}
		else {
			Log.debug("*************Reached "+ actionsNumber +" Actions***************");
		}
		Log.debug("*************End Of Test on: " + appName+ "***************");
		Log.debug("*********************************************************************************************");
		return false;

		
	}
	
	
	/**
	 * Sets the random points.
	 */
	private void setRandomPoints(){
		
		startCoord.x = rand.nextInt(sizeOfRangeWidth) + minWidthValue;
		startCoord.y = rand.nextInt(sizeOfRangeHeight) + minHeightValue;
		endCoord.x = rand.nextInt(sizeOfRangeWidth) + minWidthValue;
		endCoord.y = rand.nextInt(sizeOfRangeHeight) + minHeightValue;
		
	}
	
	/**
	 * Random touch.
	 */
	private void randomTouch(){
		
		String operation="single";
		
		switch (rand.nextInt(4)){
		
			case 0:
				operation = "single";
			break;
			case 1:
				operation = "double";
			break;
			case 2:
				operation = "up";
			break;
			case 3:
				operation = "down";
			break;
			
		}
		Map<String, Object> params = new HashMap<>();
		params.put("location", pointToString(startCoord));
		params.put("operation", operation);
		
		try {
			this.driver.executeScript("mobile:touch:tap", params);
			Log.debug("Touch: " + operation + " ==> " + pointToString(startCoord,true));
			
		} catch (Exception e) {
			Log.debug("##PMCommandError## : Touch: " + operation + " ==> " + pointToString(startCoord,true));
		}
		
	}
	
	
	/**
	 * Random drag.
	 */
	private void randomDrag(){
		
		String auxiliary="tap";
		
		switch (rand.nextInt(4)){
		
			case 0:
				auxiliary = "tap";
			break;
			case 1:
				auxiliary = "notap";
			break;
			case 2:
				auxiliary = "down";
			break;
			case 3:
				auxiliary = "up";
			break;
			
		}
		
						
		Map<String, Object> params = new HashMap<>();
		List<String> location = new ArrayList<>();
		location.add(pointToString(startCoord));
		location.add(pointToString(endCoord));
		params.put("location", location);
		params.put("auxiliary", auxiliary);
		
		try {
			this.driver.executeScript("mobile:touch:drag", params);
			Log.debug("Drag: " + auxiliary + " ==> "+ pointToString(startCoord,true) + " to "+ pointToString(endCoord,true));

		} catch (Exception e) {
			Log.error("##PMCommandError## : Drag : " + auxiliary + " ==> "+ pointToString(startCoord,true) + " to "+ pointToString(endCoord,true));
		}
		
		
		
	}
	
	/**
	 * Random swipe.
	 */
	private void randomSwipe(){
		
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("start", pointToString(startCoord));
			params.put("end", pointToString(endCoord));
			driver.executeScript("mobile:touch:swipe", params);
			Log.debug("Swipe: " + pointToString(startCoord,true) + " to "+ pointToString(endCoord,true));

		} catch (Exception e) {
			Log.error("##PMCommandError## : Swipe: " + pointToString(startCoord,true) + " to "+ pointToString(endCoord,true));
		}
		
		
	}
	
	/**
	 * Random gesture.
	 */
	private void randomGesture(){
		
		String operation="zoom";
		
		switch (rand.nextInt(2)){
		
			case 0:
				operation = "pinch";
				//set new end coordinates:
				int maxPinchX = Math.min(endCoord.x, resolutionWidth-endCoord.x);
				int maxPinchY = (int) Math.min(endCoord.y - minHeightValue, maxHeightValue);
				
				startCoord.x = rand.nextInt(maxPinchX);
				startCoord.y = rand.nextInt(maxPinchY);
			break;
			case 1:
				operation = "zoom";
				//set new end coordinates:
				int maxZoomX = Math.min(startCoord.x, resolutionWidth-startCoord.x);
				int maxZoomY = (int) Math.min(startCoord.y - minHeightValue, maxHeightValue);
				
				endCoord.x = rand.nextInt(maxZoomX);
				endCoord.y = rand.nextInt(maxZoomY);
			break;
			
		}
		
		
		
		Map<String, Object> params = new HashMap<>();
		params.put("start", pointToString(startCoord));
		params.put("end", pointToString(endCoord));
		params.put("operation", operation);
		
		try {
			driver.executeScript("mobile:touch:gesture", params);
			Log.debug("Gesture: " + operation + " ==> "+ pointToString(startCoord,true) + " to "+ pointToString(endCoord,true));
		
		} catch (Exception e) {
			Log.error("##PMCommandError##: Gesture: " + operation + " ==> "+ pointToString(startCoord,true) + " to "+ pointToString(endCoord,true));
		}
	}
	
	private void deviceHome(){
		Map<String, Object> params = new HashMap<>();
		try {
			driver.executeScript("mobile:handset:ready", params);
			Log.debug("Home Click");
			
		} catch (Exception e) {
			Log.error("##PMCommandError##: Home Click");
		}
		
	}
	
	private void randomRotate(){
		
		Map<String, Object> params = new HashMap<>();
		
		//state operation:
		switch (rand.nextInt(2)){
		
			case 0:
				params.put("operation", "reset");
			break;
			case 1:
				params.put("operation", "next");
			break;
			
		}
	/*	//rotate state:
		switch (rand.nextInt(3)){
		
			case 0:
				params.put("state", "portrait");
			break;
			case 1:
				params.put("state", "landscape");
			break;
			case 2:
				
			break;
			
		}
		*/
		//rotate method:
		switch (rand.nextInt(3)){
		
			case 0:
				params.put("method", "device");
			break;
			case 1:
				params.put("method", "view");
			break;
			case 2:
				
			break;
			
		}
		
		
		try {
			this.driver.executeScript("mobile:handset:rotate", params);
			Log.debug("Rotate: " + params.toString());

		} catch (Exception e) {
			Log.error("##PMCommandError## : Rotate: " + params.toString());
		}
		
		
		
	}
	
	/**
	 * Random action.
	 */
	private void randomAction(){
		
		switch (rand.nextInt(Constants.eventsCount)){
		
			case 0:
				randomTouch();
			break;
			case 1:
				randomDrag();
			break;
			case 2:
				randomSwipe();
			break;
			case 3:
				randomGesture();
			break;
			case 4:
				randomRotate();
			break;
			/*case 5:
				deviceHome();
			break;*/
		}
	}
	
	
	//Bring app to front in case its not:
	private boolean isAppOnFront(String appName){

		String command = "mobile:application:info";
		Map<String, Object> params = new HashMap<>();
		params.put("property", "frontapp");
		String frontapp = (String) driver.executeScript(command, params);
		
		if (frontapp.equalsIgnoreCase(appName)){
			return true;
		}
			
		return false;
	}
	
	private boolean openApp(String appName){
		Map<String, Object> params = new HashMap<>();
		
		try {
			params.put("name", appName);
			driver.executeScript("mobile:application:open", params);
			Log.debug("Relaunched App " + appName );
			Thread.sleep(1000);
			return true;
		} catch (Exception e) {
			Log.error("******Can't get app back to Front, Exiting");
			return false;
		}
	}
	private boolean appToFront(String appName){
		if (!isAppOnFront(appName)){
			Log.debug("******OOPS... Where did the application go?");
			return openApp(appName);
		}
		return true;
	}
	
	/**
	 * Point to string.
	 *
	 * @param point the point
	 * @param args the args
	 * @return the string
	 */
	private String pointToString(Point point, boolean... args){
		String strPoint =  point.x + "," + point.y;
		
		if (args.length > 0 && args[0] == true){
			strPoint= "[" + strPoint + "]";
		}
		return strPoint;
	}
	
	/**
	 * Check log for exception.
	 *
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private boolean checkLogForException() throws IOException{
		Map<String, Object> params = new HashMap<>();
		params.put("tail", 1000);
		String logMessages[] = ((String) this.driver.executeScript("mobile:handset:log", params)).split("\\n");
		
		for (int i=0; i<logMessages.length;i++){
			if (logMessages[i].toLowerCase().contains("exception")){
				Log.fatal("****EXCEPTION: " + logMessages[i]+ " Link to screenshot: "+Constants.takeScreenshot(this.driver,"DummyTest"));
				return true;
			}
		}
		
		return false;
	}
	
		
}			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			