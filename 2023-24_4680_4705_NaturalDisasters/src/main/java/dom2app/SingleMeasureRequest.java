package dom2app;

public class SingleMeasureRequest implements ISingleMeasureRequest {

    private String requestName;
    private String requestFilter;
    private boolean answeredFlag;
    private IMeasurementVector answer;
    private String descriptiveStats;
    private String regressionResult;

    // Constructor to set initial values
    public SingleMeasureRequest(String requestName, String requestFilter) {
        this.requestName = requestName;
        this.requestFilter = requestFilter;
        this.answeredFlag = false; // Initialize with no answer
        this.answer = null; // Initialize with no answer
        this.descriptiveStats = null; // Initialize with no descriptive stats
        this.regressionResult = null; // Initialize with no regression result
    }

    public SingleMeasureRequest(String requestName, String countryName, String indicatorString) {
		// TODO Auto-generated constructor stub
	}

	public SingleMeasureRequest(String requestName, String countryName, String indicatorString, int startYear,
			int endYear) {
		// TODO Auto-generated constructor stub
	}

	public SingleMeasureRequest(String requestName2) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public String getRequestName() {
        return requestName;
    }

    @Override
    public String getRequestFilter() {
        return requestFilter;
    }

    @Override
    public boolean isAnsweredFlag() {
    	if (answer!=null)
    	{
    		return true;
    	}
        return answeredFlag;
    }

    @Override
    public IMeasurementVector getAnswer() {
        return answer;
    }

    @Override
    public String getDescriptiveStatsString() {
        return descriptiveStats;
    }

    @Override
    public String getRegressionResultString() {
        return regressionResult;
    }

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public void setRequestFilter(String requestFilter) {
		this.requestFilter = requestFilter;
	}

	public void setAnsweredFlag(boolean answeredFlag) {
		this.answeredFlag = answeredFlag;
	}

	public void setAnswer(IMeasurementVector answer) {
		this.answer = answer;
	}

	public void setDescriptiveStats(String descriptiveStats) {
		this.descriptiveStats = descriptiveStats;
	}

	public void setRegressionResult(String regressionResult) {
		this.regressionResult = regressionResult;
	}

	public String getDescriptiveStats() {
		return descriptiveStats;
	}

	public String getRegressionResult() {
		return regressionResult;
	}

    // You may add setter methods to update the values of the fields as needed.
}
