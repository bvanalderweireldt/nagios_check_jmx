package org.bva.jmx.beans;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;

import org.bva.jmx.JmxBeansEnu;
import org.bva.jmx.JmxConnexion;

public class JmxBean {
	final static String OBJECT_JAVA_PREFIX 		= 	"java.lang:type=";
	final static String OBJECT_HYBRIS_PREFIX 	= 	"hybris:tenantscope=Master Tenant,";
	
	final static char	ESCAPE_CAR			=	'\'';
	final static char	SEP_CAR				=	';';
	final static String	SEP_WAR_CRI			=	":";
	
	private JmxBeansEnu	jmxType;
	
	private String 		objectName;
	private String	 	attributes;

	protected long		value;
	private int			warning;
	private int			critival;
	private int			min;
	private int			max;
	
	private JmxBean(String objectName, String attributes){
		value =  min = max = -1;
		this.objectName = objectName;
		this.attributes = attributes;		
	}
	
	private JmxBean(String objectName, String attributes, String warningCritical) {
		this(objectName, attributes);
		if(warningCritical == null || warningCritical.indexOf(SEP_WAR_CRI) == -1){
			throw new IllegalArgumentException("warningCritical should be integers : <warning>:<critical>");
		}
		String[] args = warningCritical.split(SEP_WAR_CRI);
		this.warning = Integer.parseInt(args[0]);
		this.critival = Integer.parseInt(args[1]);
	}
	
	public JmxBean(JmxBeansEnu jmxType, String warningCritical){
		this(jmxType.getObjectName(), jmxType.getAttribute(), warningCritical);
		this.jmxType = jmxType;
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
		else if (jmxType == JmxBeansEnu.MEMORY_USED) {
			value = ( (Long) ((CompositeDataSupport) jmx).get("used")).intValue() ;
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
