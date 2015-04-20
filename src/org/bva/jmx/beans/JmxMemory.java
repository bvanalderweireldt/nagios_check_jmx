package org.bva.jmx.beans;

public abstract class JmxMemory extends JmxBean {
	static final String CLASS_MEMORY_OBJECT = "Memory";
	static final String CLASS_MEMORY_ATTRIBUTE = "HeapMemoryUsage";

	public JmxMemory(String warningCritical) {
		super(OBJECT_JAVA_PREFIX+CLASS_MEMORY_OBJECT, CLASS_MEMORY_ATTRIBUTE, warningCritical);
	}

	@Override
	protected void getValueFromJmx(Object jmx) {
		System.out.println(jmx);
		System.out.println(jmx.getClass());
	}
}
