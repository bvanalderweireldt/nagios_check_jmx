package org.bva.jmx.beans;

public abstract class JmxBean {
	final static String OBJECT_JAVA_PREFIX 		= 	"java.lang:type=";
	final static String OBJECT_HYBRIS_PREFIX 	= 	"hybris:tenantscope=Master Tenant,";
	
	final static char	ESCAPE_CAR			=	'\'';
	final static char	SEP_CAR				=	';';
	
	private String 		objectName;
	private String	 	attributes;

	private int			value;
	private int			warning;
	private int			critival;
	private int			min;
	private int			max;
	
	public JmxBean() {
		value = warning = critival = min = max = -1;
	}
	///'EntitiesMissCountInPercent size'=3;1;2;;
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(ESCAPE_CAR);
		sb.append(attributes);
		sb.append(ESCAPE_CAR);
		sb.append('=');
		sb.append(getValue());
		sb.append(SEP_CAR);
		sb.append(getWarning());
		sb.append(SEP_CAR);
		sb.append(getCritival());
		sb.append(SEP_CAR);
		sb.append(getMin());
		sb.append(SEP_CAR);
		sb.append(getMax());
		
		return sb.toString();
	}
	
	private String emptyIfNegative(int i){
		return ( i == -1 ) ? "" : String.valueOf(i);
	}
	
	public String getValue(){
		return emptyIfNegative(value);
	}
	
	public String getWarning(){
		return emptyIfNegative(warning);		
	}

	public String getCritival(){
		return emptyIfNegative(critival);		
	}

	public String getMin(){
		return emptyIfNegative(min);		
	}

	public String getMax(){
		return emptyIfNegative(max);		
	}
}
