package dom2app;

import java.util.List;
import org.apache.commons.math3.util.Pair;

public class MeasurementVector implements IMeasurementVector {

    private String countryName;
    private String indicatorString;
    private List<Pair<Integer, Integer>> measurements;

    public MeasurementVector(String countryName, String indicatorString, List<Pair<Integer, Integer>> measurements) {
        this.countryName = countryName;
        this.indicatorString = indicatorString;
        this.measurements = measurements;
    }

    public MeasurementVector(String[] fields) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public String getCountryName() {
        return countryName;
    }

    @Override
    public String getIndicatorString() {
        return indicatorString;
    }

    @Override
    public List<Pair<Integer, Integer>> getMeasurements() {
        return measurements;
    }

    @Override
    public String getDescriptiveStatsAsString() {
        // Implement logic to compute and return descriptive statistics as a String
        // Example: return "Mean: 10.5, Median: 8.0, Std. Deviation: 3.1";
    	return null;
    }

    @Override
    public String getRegressionResultAsString() {
        // Implement logic to perform regression analysis and return results as a String
        // Example: return "Regression Coefficient: 0.7, p-value: 0.001";
    	return null;
    }
}
