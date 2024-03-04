package engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.Pair;

import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;
import dom2app.MeasurementVector;
import dom2app.SingleMeasureRequest;

public class MainController implements IMainController {
	
	public List<IMeasurementVector> measurementVectors;
	private List<ISingleMeasureRequest> existingRequests;
	
	// Constructor
	public MainController() {
        this.measurementVectors = new ArrayList<>();
        this.existingRequests = new ArrayList<>();
    }

	@Override
    public List<IMeasurementVector> load(String fileName, String delimiter) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int rowCount = 0;  // Counter for the number of rows read

        String line;
        while ((line = reader.readLine()) != null) {
        	rowCount++;  // Increment the row counter
            String[] parts = line.split(delimiter);

            String countryName = parts[1];
            String indicatorString = parts[4];
            List<Pair<Integer, Integer>> measurements = new ArrayList<>();

            for (int i = 5; i < parts.length; i++) {
                int year = 1980 + i - 5;

                int value;
                if (!parts[i].isEmpty()) {
                    value = Integer.parseInt(parts[i]);
                } else {
                    value = 0; // Orizei tin timi 0 gia kenes times
                }
                measurements.add(new Pair<>(year, value));
            }


            MeasurementVector mv = new MeasurementVector(countryName, indicatorString, measurements);
            measurementVectors.add(mv);
        }

        reader.close();
        System.out.println("Total number of rows read: " + rowCount);
        return measurementVectors;
    }

	@Override
	public ISingleMeasureRequest findSingleCountryIndicator(String requestName, String countryName, String indicatorString)
	        throws IllegalArgumentException {
	    if (requestName == null || requestName.trim().isEmpty() ||
	        countryName == null || countryName.trim().isEmpty() ||
	        indicatorString == null || indicatorString.trim().isEmpty()) {
	        throw new IllegalArgumentException("Invalid input: requestName, countryName, and indicatorString must not be null or empty.");
	    }

	    for (IMeasurementVector mv : this.measurementVectors) { 
	        if (mv.getCountryName().equalsIgnoreCase(countryName) &&
	            mv.getIndicatorString().equalsIgnoreCase(indicatorString)) {
	            SingleMeasureRequest request = new SingleMeasureRequest(countryName, indicatorString);
	            
	            if (request != null) {
	                
	            	this.existingRequests.add(request);  // Add the request in the list
	                request.setRequestName(requestName);
		            request.setRequestFilter(countryName + "-" + indicatorString);
		            request.setAnswer(mv); 
		            
	            }
	            
	            
	            return request;
	        }
	    }
	    return null; //  throw an exception if no matching data is found
	}


	@Override
	public ISingleMeasureRequest findSingleCountryIndicatorYearRange(String requestName, String countryName,
	        String indicatorString, int startYear, int endYear) throws IllegalArgumentException {
	    if (requestName == null || requestName.trim().isEmpty() ||
	        countryName == null || countryName.trim().isEmpty() ||
	        indicatorString == null || indicatorString.trim().isEmpty() ||
	        startYear < 1980 || endYear > 2022 || startYear > endYear) { // Check if arguments are not null and years are inside the range 
	        throw new IllegalArgumentException("Invalid input: requestName, countryName, indicatorString, startYear, and endYear must not be null or empty and must be within valid range.");
	    }

	    for (IMeasurementVector mv : this.measurementVectors) {
	        if (mv.getCountryName().equalsIgnoreCase(countryName) &&
	            mv.getIndicatorString().equalsIgnoreCase(indicatorString)) {
	            List<Pair<Integer, Integer>> filteredMeasurements = mv.getMeasurements()
	                .stream()
	                .filter(m -> m.getFirst() >= startYear && m.getFirst() <= endYear)
	                .collect(Collectors.toList());

	            if (!filteredMeasurements.isEmpty()) {
	                SingleMeasureRequest request = new SingleMeasureRequest(countryName, indicatorString, filteredMeasurements);
	                this.existingRequests.add(request);  // Add the request in the list
	                request.setRequestName(requestName);
	                request.setRequestFilter(countryName + "-" + indicatorString + "-" + startYear + "-" + endYear);
	                request.setAnswer(new MeasurementVector(countryName, indicatorString, filteredMeasurements));
	                
	                return request;
	            }
	        }
	    }
	    return null; //  throw an exception if no matching data is found
	}


	public Set<String> getAllRequestNames() {
        Set<String> requestNames = new HashSet<>();
        if (existingRequests != null) {
            for (ISingleMeasureRequest request : existingRequests) {
                if (request != null && request.getRequestName() != null) {
                    requestNames.add(request.getRequestName());
                }
            }
        }
        return requestNames;
    }


    @Override
    public ISingleMeasureRequest getRequestByName(String requestName) {
        // Check if requestName is valid
        if (requestName == null || requestName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid request name");
        }

        // Psaxnei stin lista existingRequests gia to requestName pou exoume dwsei san orisma
        for (ISingleMeasureRequest request : existingRequests) {
            if (requestName.equals(request.getRequestName())) {
                return request; // return to request an vrethei stin lista
            }
        }

        return null; // return null an den vrethei to aitima
    }


    @Override
    public ISingleMeasureRequest getRegression(String requestName) {
    	ISingleMeasureRequest request = getRequestByName(requestName);
        if (request == null) {
            return null; // return null an den vrethei to request
        }
        
        String regressionResults = request.getAnswer().getRegressionResultAsString();
        request.setReggresionResultsString(regressionResults);
        
        
        return request; // return to request mazi me to regression
    }

    @Override
    public ISingleMeasureRequest getDescriptiveStats(String requestName) {
        ISingleMeasureRequest request = getRequestByName(requestName);
        if (request == null) {
            return null; // return null an den vrethei to request
        }

        String descriptiveStats = request.getAnswer().getDescriptiveStatsAsString();
        request.setDescriptiveStatsString(descriptiveStats);

        return request; // return to request mazi me ta DescriptiveStats
    }


	@Override
	public int reportToFile(String outputFilePath, String requestName, String reportType) throws IOException {
		ISingleMeasureRequest request = getRequestByName(requestName);
		if (request == null) {
		    return -1; // Request not found
		}
		
		// Analoga me to report type kalei kai kai tin analogh methodo
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
		    switch (reportType.toLowerCase()) {
		        case "text":
		            writeTextReport(writer, request);
		            break;
		        case "md":
		            writeMarkdownReport(writer, request);
		            break;
		        case "html":
		            writeHtmlReport(writer, request);
		            break;
		        default:
		            throw new IllegalArgumentException("Invalid report type: " + reportType);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    return -1;
		}

		return 0; // Successful completion


	}
	
	// Method for txt
	private void writeTextReport(BufferedWriter writer, ISingleMeasureRequest request) throws IOException {
		    writer.write("" + request.getRequestName() + "\n");
		    writer.write("" + request.getRequestFilter() + "\n");

		    List<Pair<Integer, Integer>> measurements = request.getAnswer().getMeasurements();
		    if (measurements != null && !measurements.isEmpty()) {
		        writer.write("Year  Value\n");
		        for (Pair<Integer, Integer> measurement : measurements) {
		            int year = measurement.getFirst();
		            int value = measurement.getSecond();
		            writer.write(year + ": " + value + "\n");
		        }
		    } else {
		        writer.write("No measurement data available.\n");
		    }

		    writer.write("\n\n");
		    writer.write("Descriptive Stats: " + request.getDescriptiveStatsString() + "\n");
		    writer.write("Regression Results: " + request.getRegressionResultString() + "\n");
		}
	
	// Method for Markdown
	private void writeMarkdownReport(BufferedWriter writer, ISingleMeasureRequest request) throws IOException {
	    writer.write("**" + request.getRequestName() + "**\n\n");									// Bold **
	    writer.write("*" + request.getRequestFilter() + "*\n\n");									// Italics *

	    List<Pair<Integer, Integer>> measurements = request.getAnswer().getMeasurements();
	    if (measurements != null && !measurements.isEmpty()) {
	        writer.write("| Year | Value |\n");
	        writer.write("|------|-------|\n");
	        for (Pair<Integer, Integer> measurement : measurements) {
	            writer.write("| " + measurement.getFirst() + " | " + measurement.getSecond() + " |\n");
	        }
	    } else {
	        writer.write("No measurement data available.\n\n");
	    }
	    
	    writer.write("\n\n");
	    writer.write("**Descriptive Stats:** " + request.getDescriptiveStatsString() + "\n\n");			//Bold **
	    writer.write("**Regression Results:** " + request.getRegressionResultString() + "\n\n");		//Bold **
	}


	// Method for Html
	private void writeHtmlReport(BufferedWriter writer, ISingleMeasureRequest request) throws IOException {
	    writer.write("<h2><b>" + request.getRequestName() + "</b></h2>\n");             // Bold <b>
	    writer.write("<p><i>" + request.getRequestFilter() + "</i></p>\n");				// Italics <i>

	    List<Pair<Integer, Integer>> measurements = request.getAnswer().getMeasurements();
	    if (measurements != null && !measurements.isEmpty()) {
	        writer.write("<table>\n");													// Put the data in a table
	        writer.write("<tr><th>Year</th><th>Value</th></tr>\n");
	        for (Pair<Integer, Integer> measurement : measurements) {
	            writer.write("<tr><td>" + measurement.getFirst() + "</td><td>" + measurement.getSecond() + "</td></tr>\n");
	        }
	        writer.write("</table>\n");
	    } else {
	        writer.write("<p>No measurement data available.</p>\n");
	    }
	    
	    writer.write("\n\n");
	    writer.write("<p><b>Descriptive Stats:</b> " + request.getDescriptiveStatsString() + "</p>\n");			//Bold <b>
	    writer.write("<p><b>Regression Results:</b> " + request.getRegressionResultString() + "</p>\n");		//Bold <b>
	}

	
	
}


