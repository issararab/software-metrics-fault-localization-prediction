package ClassPackage;

import ClassPackage.Metric;

public class IntegerMetric extends Metric {
	
	
	Integer value;
	
	public IntegerMetric(String name, Integer value) {
		super(name);
		this.value = value;
	}
	
	public void addToValue(Integer val)
	{
		value += val;
	}
	
	public void subFromValue(Integer val)
	{
		value -= val;
	}

	public IntegerMetric(String name) 
	{
		super(name);
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}
	
	

}
