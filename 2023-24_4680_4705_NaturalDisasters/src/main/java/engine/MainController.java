package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;
import dom2app.MeasurementVector;
import dom2app.SingleMeasureRequest;
import engine.IMainController;

public class MainController implements IMainController{
	
	private List<IMeasurementVector> measurementVectors;
    private Set<String> requestNames;
    
    public MainController() {
        measurementVectors = new ArrayList<>();
        requestNames = new HashSet<>();
    }

	@Override
	public List<IMeasurementVector> load(String fileName, String delimiter) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		// Create a list to store the measurement vectors
        List<IMeasurementVector> measurementVectors = new ArrayList<>();
        int rowRead = 0; // Initialize row count

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
            	rowRead++; // Increment row count
                // Split the line into fields based on the delimiter
                String[] fields = line.split(delimiter);

                // Assuming you have implemented IMeasurementVector, create a new MeasurementVector
                IMeasurementVector measurementVector = new MeasurementVector(fields);

                // Add the measurement vector to the list
                measurementVectors.add(measurementVector);
            }
        }
        
     // Print the number of rows
        System.out.println("Rows Read: " + rowRead);

        return measurementVectors;
	
	}

	@Override
	public ISingleMeasureRequest findSingleCountryIndicator(String requestName, String countryName, String indicatorString)
	        throws IllegalArgumentException {
	    // Check if any of the arguments is an empty string and throw an exception if necessary.
	    if (requestName.isEmpty() || countryName.isEmpty() || indicatorString.isEmpty()) {
	        throw new IllegalArgumentException("Arguments cannot be empty strings.");
	    }

	    // Create an instance of ISingleMeasureRequest with the provided parameters and return it.
	    ISingleMeasureRequest request = new SingleMeasureRequest(requestName, countryName, indicatorString);
	    return request;
	}

	@Override
	public ISingleMeasureRequest findSingleCountryIndicatorYearRange(String requestName, String countryName,
	        String indicatorString, int startYear, int endYear) throws IllegalArgumentException {
	    // Check if any of the string arguments is an empty string or if endYear < startYear, and throw an exception if necessary.
	    if (requestName.isEmpty() || countryName.isEmpty() || indicatorString.isEmpty() || endYear < startYear) {
	        throw new IllegalArgumentException("Arguments are invalid. They cannot be empty strings, and endYear must be greater than or equal to startYear.");
	    }

	    // Create an instance of ISingleMeasureRequest with the provided parameters and return it.
	    ISingleMeasureRequest request = new SingleMeasureRequest(requestName, countryName, indicatorString, startYear, endYear);
	    return request;
	}

	@Override
	public Set<String> getAllRequestNames() {
		// TODO Auto-generated method stub
		return requestNames;
	}

	@Override
	public ISingleMeasureRequest getRequestByName(String requestName) {
		// TODO Auto-generated method stub
		ISingleMeasureRequest request = new SingleMeasureRequest(requestName);
		request.getRequestName();
		return request;
	}

	@Override
	public ISingleMeasureRequest getRegression(String requestName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISingleMeasureRequest getDescriptiveStats(String requestName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int reportToFile(String outputFilePath, String requestName, String reportType) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}


