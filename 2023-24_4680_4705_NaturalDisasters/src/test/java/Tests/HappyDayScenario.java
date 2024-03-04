package Tests;

import engine.MainController;
import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;


// HappyDayScenario for all methods

public class HappyDayScenario {

    private MainController testController;

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        testController = new MainController();
        testController.load("./src/main/resources/InputData/ClimateRelatedDisasters.tsv","\t");
    }

    @Test
    public void testLoadData() throws FileNotFoundException, IOException {
        List<IMeasurementVector> loadedData = testController.load("./src/main/resources/InputData/ClimateRelatedDisasters.tsv","\t");
        Assert.assertNotNull("Loaded data should not be null", loadedData);
    }

    @Test
    public void testFindSingleCountryIndicator() {
        ISingleMeasureRequest request = testController.findSingleCountryIndicator("Greece-Total", "Greece", "Total");
        Assert.assertNotNull("Invalid request arguments", request);
    }

    @Test
    public void testFindSingleCountryIndicatorYearRange() {
        ISingleMeasureRequest requestYearRange = testController.findSingleCountryIndicatorYearRange("Greece-2000-2010", "Greece", "Total", 2000, 2010);
        Assert.assertNotNull("Invalid request arguments", requestYearRange);
    }

    @Test
    public void testGetAllRequestNames() {
    	testController.findSingleCountryIndicator("Greece-Total", "Greece", "Total");
        Set<String> allRequestNames = testController.getAllRequestNames();
        Assert.assertNotNull("All request names should not be null", allRequestNames);
        Assert.assertFalse("All request names should not be empty", allRequestNames.isEmpty());
    }

    @Test
    public void testGetRequestByName() {
    	testController.findSingleCountryIndicator("Greece-Total", "Greece", "Total");
        ISingleMeasureRequest requestByName = testController.getRequestByName("Greece-Total");
        Assert.assertNotNull("Request should not have null arguments", requestByName);
    }

    @Test
    public void testGetDescriptiveStats() {
        testController.findSingleCountryIndicator("Greece-Total", "Greece", "Total");
        ISingleMeasureRequest descriptiveStatsRequest = testController.getDescriptiveStats("Greece-Total");
        Assert.assertNotNull("Descriptive stats request should not be null", descriptiveStatsRequest);
        Assert.assertNotNull("Descriptive stats should not be null", descriptiveStatsRequest.getDescriptiveStatsString());
    }

    
    @Test
    public void testGetRegression() {
        // Ensure the request exists
        testController.findSingleCountryIndicator("Greece-Total", "Greece", "Total");
        ISingleMeasureRequest regressionRequest = testController.getRegression("Greece-Total");
        Assert.assertNotNull("Regression request should not be null", regressionRequest);
        Assert.assertNotNull("Regression results should not be null", regressionRequest.getRegressionResultString());
    }


    
    @Test
    public void testReportToFile() throws IOException {
        String requestName = "Greece-Total";
        String textFilePath = "./src/test/resources/output/Greece_Total_Report.txt";
        String mdFilePath = "./src/test/resources/output/Greece_Total_Report.md";
        String htmlFilePath = "./src/test/resources/output/Greece_Total_Report.html";

        testController.reportToFile(textFilePath, requestName, "text");
        testController.reportToFile(mdFilePath, requestName, "md");
        testController.reportToFile(htmlFilePath, requestName, "html");
    }
    

}
