package Tests;

import engine.MainController;
import dom2app.ISingleMeasureRequest;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;


// RainyDayScenario for all methods

public class RainyDayScenario {

    @Test(expected = FileNotFoundException.class)
    public void testLoadWithInvalidFilePath() throws FileNotFoundException, IOException {
        MainController testController = new MainController();
        testController.load("./invalid/path/ClimateRelatedDisasters.tsv", "\t"); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFindSingleCountryIndicatorWithInvalidArguments() {
        MainController testController = new MainController();
        testController.findSingleCountryIndicator("", "Greece", "Total");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindSingleCountryIndicatorYearRangeWithInvalidYear() {
        MainController testController = new MainController();
        testController.findSingleCountryIndicatorYearRange("Greece-2050-2070", "Greece", "Total", 2050, 2070);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetRequestByNameWithEmptyName() {
        MainController testController = new MainController();
        testController.getRequestByName("");
    }

    @Test
    public void testGetRequestByNameWithNonExistingName() {
        MainController testController = new MainController();
        ISingleMeasureRequest request = testController.getRequestByName("NonExistingRequest");
        //System.out.println("request: " + request);
        Assert.assertNull(request);
    }

    @Test
    public void testGetAllRequestNamesWithoutLoadingData() {
    	MainController testController = new MainController();
    	testController.getAllRequestNames();
    	//System.out.println("All Request Names: " + allRequestNames);
       
    }
    
    @Test
    public void testGetDescriptiveStatsWithInvalidRequest() {
        MainController testController = new MainController();
        ISingleMeasureRequest descriptiveStatsRequest = testController.getDescriptiveStats("InvalidRequest");
        Assert.assertNull("Descriptive stats request should be null for invalid input", descriptiveStatsRequest);
    }

    
   @Test
    public void testGetRegressionWithInvalidRequest() {
    	MainController testController = new MainController();
        ISingleMeasureRequest regressionRequest = testController.getRegression("InvalidRequest");
        Assert.assertNull("Regression request should be null for invalid input", regressionRequest);
    }


    @Test
    public void testReportToFileWithInvalidPath() throws IOException {
        MainController testController = new MainController();
        int result = testController.reportToFile("./invalid/path/Greece-Total.txt", "Greece-Total", "text");
        Assert.assertEquals(-1, result);
    }


}
