package org.bva.jmx.beans;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import org.bva.jmx.JmxConnexion;

public abstract class JmxBean {
	final static String OBJECT_JAVA_PREFIX 		= 	"java.lang:type=";
	final static String OBJECT_HYBRIS_PREFIX 	= 	"hybris:tenantscope=Master Tenant,";
	
	final static char	ESCAPE_CAR			=	'\'';
	final static char	SEP_CAR				=	';';
	final static String	SEP_WAR_CRI			=	":";
	
	private String 		objectName;
	private String	 	attributes;

	protected long		value;
	private int			warning;
	private int			critival;
	private int			min;
	private int			max;
	
	public JmxBean(String objectName, String attributes){
		value =  min = max = -1;
		this.objectName = objectName;
		this.attributes = attributes;		
	}
	
	public JmxBean(String objectName, String attributes, int warning, int critical) {
		this(objectName, attributes);
		this.warning = warning;
		this.critival = critical;
	}

	public JmxBean(String objectName, String attributes, String warningCritical) {
		this(objectName, attributes);
		if(warningCritical == null || warningCritical.indexOf(SEP_WAR_CRI) == -1){
			throw new IllegalArgumentException("warningCritical should be integers : <warning>:<critical>");
		}
		String[] args = warningCritical.split(SEP_WAR_CRI);
		this.warning = Integer.parseInt(args[0]);
		this.critival = Integer.parseInt(args[1]);
	}
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
	
	public void queryJmx(JmxConnexion jmxCo){
		try {
			Object jmx = jmxCo.queryObject(objectName, attributes);
			
			getValueFromJmx(jmx);
		} catch (MalformedObjectNameException | AttributeNotFoundException
				| InstanceNotFoundException | MBeanException
				| ReflectionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getValueFromJmx(Object jmx) {
		if(jmx instanceof Number){
			value = (int) jmx;
		}		
	}

	private String emptyIfNegative(long i){
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
