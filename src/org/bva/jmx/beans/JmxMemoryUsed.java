package org.bva.jmx.beans;

import javax.management.openmbean.CompositeDataSupport;

public class JmxMemoryUsed extends JmxMemory{
	
	public JmxMemoryUsed(String warningCritical) {
		super(warningCritical);
	}

	@Override
	protected void getValueFromJmx(Object jmx) {
		value = ( (Long) ((CompositeDataSupport) jmx).get("used")).intValue() ;
	}
	
}
