package dom2app;

import java.util.List;

import org.apache.commons.math3.util.Pair;

public class SingleMeasureRequest implements ISingleMeasureRequest {
    
	private String requestName;
    private String requestFilter;
    private boolean answered;
    private MeasurementVector answer;
	private String descriptiveStats;
	private String regressionResults; 
	private String countryName;
	private String indicatorString;
	private List<Pair<Integer, Integer>> filteredMeasurements;
    
   

	// Constructor1
    public SingleMeasureRequest(String requestName, String requestFilter) {
        this.requestName = requestName;
        this.requestFilter = requestFilter;
        this.answered = false;
        this.answer = null;
        
    }

    // Constructor2
    public SingleMeasureRequest(String countryName, String indicatorString, List<Pair<Integer, Integer>> filteredMeasurements) {
        this.countryName = countryName;
        this.indicatorString = indicatorString;
        this.filteredMeasurements = filteredMeasurements;
        
    }
    
    // Getters

	@Override
    public String getRequestName() {
        return requestName;
    }

    @Override
    public String getRequestFilter() {
        return requestFilter;
    }

    @Override
    public IMeasurementVector getAnswer() {
        return answer;
    }
    
    @Override
    public boolean isAnsweredFlag() {
        return answered;
    }

    @Override
    public String getDescriptiveStatsString() {
        if (answer == null) {
            return "No data available";
        }
        return answer.getDescriptiveStatsAsString();
    }

    @Override
    public String getRegressionResultString() {
        if (answer == null) {
            return "No data available for regression analysis";
        }
        return answer.getRegressionResultAsString();
    }

    
    
    // Setters
    
    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }
    
    public void setRequestFilter(String requestFilter) {
        this.requestFilter = requestFilter;
    }

	public boolean isAnswered() {
		return answered;
	}
	
	public void setAnswer(IMeasurementVector mv) {
        this.answer = (MeasurementVector) mv;
        this.answered = (mv != null);
    }

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}
	
	@Override
	public void setDescriptiveStatsString(String descriptiveStats) {
		this.descriptiveStats=descriptiveStats;
	}

	@Override
	public void setReggresionResultsString(String regressionResults) {
		this.regressionResults=regressionResults;
	}

	@Override
    public String toString() {
        return "SingleMeasureRequest{" +
               "requestName='" + requestName + '\'' +
               ", requestFilter='" + requestFilter + '\'' +
               ", descriptiveStats='" + descriptiveStats + '\'' +
               ", regressionResult='" + regressionResults + '\'' +
               '}';
    }
    
    

}
