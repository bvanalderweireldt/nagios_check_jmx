package org.bva.jmx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Threshold {
	protected enum ThresholdComp {
		BIGGER,
		SMALLER,
		EQUAL,
		BIGGER_EQUAL,
		SMALLER_EQUAL
	}
	
	final static Pattern DEFAULT = Pattern.compile("(\\d+)");
	final static Pattern BIGGER = Pattern.compile(">(\\d+)");
	final static Pattern SMALLER = Pattern.compile("<(\\d+)");
	final static Pattern EQUAL = Pattern.compile("=(\\d+)");
	final static Pattern BIGGER_EQUAL = Pattern.compile(">=(\\d+)");
	final static Pattern SMALLER_EQUAL = Pattern.compile("<=(\\d+)");
	
	long threshold = -1;
	ThresholdComp comp;
	
	public Threshold(String thresholdStr) {
		Matcher matcher;
		
		if((matcher = DEFAULT.matcher(thresholdStr)).matches() || (matcher = BIGGER.matcher(thresholdStr)).matches()){
			comp = ThresholdComp.BIGGER;
		}
		else if((matcher = SMALLER.matcher(thresholdStr)).matches() ){
			comp = ThresholdComp.SMALLER;
		}
		else if((matcher = EQUAL.matcher(thresholdStr)).matches() ){
			comp = ThresholdComp.EQUAL;
		}
		else if((matcher = BIGGER_EQUAL.matcher(thresholdStr)).matches() ){
			comp = ThresholdComp.BIGGER_EQUAL;
		}
		else if((matcher = SMALLER_EQUAL.matcher(thresholdStr)).matches() ){
			comp = ThresholdComp.SMALLER_EQUAL;
		}
		this.threshold = Long.parseLong( matcher.group(1) );

	}
	
	public boolean thresholdTriggered(long value){
		switch (comp) {
		case BIGGER:
			return value > threshold;
		case SMALLER:
			return value < threshold;
		case EQUAL:
			return value == threshold;
		case BIGGER_EQUAL:
			return value >= threshold;
		case SMALLER_EQUAL:
			return value <= threshold;
		}

		return false;
	}

	public long getThreshold() {
		return threshold;
	}

}
