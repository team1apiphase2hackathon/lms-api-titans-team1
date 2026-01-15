package hooks;

import config.ConfigReader;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import utils.GoogleDriveDownloader;

public class Hooks {

	 private static boolean isExcelDownloaded = false;
	 
	 @Before(order = 0)
	    public void setupBaseUri() {
	        RestAssured.baseURI = ConfigReader.get("base.url");
	        
	     // Download test data Excel from Google Drive only once
	        if (!isExcelDownloaded) {
	            String fileId = "1rY52wlgqmpIOFKBH_LuYo8QZ1unx44o_";
	            String localPath = "src/test/resources/Team1_lms_TestDataSheet.xlsx";
	            try {
	                GoogleDriveDownloader.downloadExcelFromDrive(fileId, localPath);
	                isExcelDownloaded = true; 
	            } catch (Exception e) {
	                e.printStackTrace();
	                throw new RuntimeException("Failed to download test data Excel. Tests cannot run.");
	            }
	        } 
	    }
	
}
