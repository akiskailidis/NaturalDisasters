package dom2app;

import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class MeasurementVector implements IMeasurementVector {

    private String countryName;
    private String indicatorString;
    private List<Pair<Integer, Integer>> measurements;

    // Constructor
    public MeasurementVector(String countryName, String indicatorString, List<Pair<Integer, Integer>> measurements) {
        this.countryName = countryName;
        this.indicatorString = indicatorString;
        this.measurements = measurements;
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
        if (measurements == null || measurements.isEmpty()) {
            return "No data available";
        }

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Pair<Integer, Integer> measurement : measurements) {
            stats.addValue(measurement.getSecond());
        }

        return  " Descriptive Stats:"
        		+ "\n Mean: " + stats.getMean()
                + "\n Standard Deviation: " + stats.getStandardDeviation()
                + "\n Min: " + stats.getMin()
                + "\n Max: " + stats.getMax()									
        		+ "\n Kurtosis: " + stats.getKurtosis()
        		+ "\n gMean: " + stats.getGeometricMean()
        		+ "\n Count: " + stats.getN()
        		+ "\n Median: " + stats.getPercentile(50)
        		+ "\n Sum: "    + stats.getSum();
    }

    @Override
    public String getRegressionResultAsString() {
        if (measurements == null || measurements.isEmpty()) {
            return "No data available for regression analysis";
        }

        SimpleRegression regression = new SimpleRegression();
        for (Pair<Integer, Integer> measurement : measurements) {
            regression.addData(measurement.getFirst(), measurement.getSecond());
        }

        double intercept = regression.getIntercept();
        double slope = regression.getSlope();
        double slopeError = regression.getSlopeStdErr();

        String trend = getLabel(slope);

        return " Regression Analysis:"
                + "\n Intercept: " + intercept
                + "\n Slope: " + slope
                + "\n Slope Error: " + slopeError
                + "\n Trend: " + trend;
    }

    
    public String getLabel(double slope) {
        if (Double.isNaN(slope)) {
            return "Tendency Undefined";
        } else if (slope > 0.1) {
            return "Increased Tendency";
        } else if (slope < -0.1) {
            return "Decreased Tendency";
        }
        return "Tendency Stable";
    }

    
    

}
