package org.bva.jmx;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;

public class JmxBean {
	final static String OBJECT_JAVA_PREFIX 		= 	"java.lang:type=";
	final static String OBJECT_HYBRIS_PREFIX 	= 	"hybris:tenantscope=Master Tenant,";
	
	final static char	ESCAPE_CAR			=	'\'';
	final static char	SEP_CAR				=	';';
	final static String	SEP_WAR_CRI			=	":";
	
	private JmxBeansEnu	jmxType;
	
	private Status status;
	
	protected long		value;
	private Threshold	warning;
	private Threshold	critival;
	private int			min;
	private int			max;
	
	private JmxBean(){
		value =  min = max = -1;
	}
	
	private JmxBean(String warningCritical) {
		this();
		if(warningCritical == null || warningCritical.indexOf(SEP_WAR_CRI) == -1){
			throw new IllegalArgumentException("warningCritical should be integers : <warning>:<critical>");
		}
		String[] args = warningCritical.split(SEP_WAR_CRI);
		this.warning = new Threshold(args[0]);
		this.critival = new Threshold(args[0]);
	}
	
	public JmxBean(JmxBeansEnu jmxType, String warningCritical){
		this(warningCritical);
		this.jmxType = jmxType;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(20);
		sb.append(ESCAPE_CAR);
		sb.append(jmxType.name());
		sb.append(ESCAPE_CAR);
		sb.append('=');
		sb.append(getValue());
		sb.append(SEP_CAR);
		sb.append(warning.getThreshold());
		sb.append(SEP_CAR);
		sb.append(critival.getThreshold());
		sb.append(SEP_CAR);
		sb.append(getMin());
		sb.append(SEP_CAR);
		sb.append(getMax());
		
		return sb.toString();
	}

	public String toStringTitle() {
		StringBuilder sb = new StringBuilder(20);
		sb.append(jmxType.name());
		sb.append('=');
		sb.append(getValue());
		
		return sb.toString();
	}
	
	public void queryJmx(JmxConnexion jmxCo){
		try {
			Object jmx = jmxCo.queryObject(jmxType.getObjectName(), jmxType.getAttribute());
			
			getValueFromJmx(jmx);
			
			checkStatus();
			
		} catch (MalformedObjectNameException | AttributeNotFoundException
				| InstanceNotFoundException | MBeanException
				| ReflectionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkStatus() {
		if( critival.thresholdTriggered(value) ){
			status = Status.CRITICAL;
		}
		else if ( warning.thresholdTriggered(value) ){
			status = Status.WARNING;
		}
		else {
			status = Status.WARNING;			
		}
	}

	protected void getValueFromJmx(Object jmx) {
		if(jmx instanceof Number){
			value = (int) jmx;
		}
		else if (jmxType == JmxBeansEnu.MEMORY_USED) {
			value = ( (Long) ((CompositeDataSupport) jmx).get("used")).intValue() ;
		}
		else if (jmxType == JmxBeansEnu.MEMORY_MAX) {
			value = ( (Long) ((CompositeDataSupport) jmx).get("max")).intValue() ;
		}
	}

	private String emptyIfNegative(long i){
		return ( i == -1 ) ? "" : String.valueOf(i);
	}
	
	public String getValue(){
		return emptyIfNegative(value);
	}
	
	public String getMin(){
		return emptyIfNegative(min);		
	}

	public String getMax(){
		return emptyIfNegative(max);		
	}
	
	public Status getStatus() {
		return status;
	}

}
