package org.bva.jmx;

public enum JmxBeansEnu {
	CLASS_LOADING		("ClassLoading", 	new String[]{"LoadedClassCount"}),
	MEMORY				("Memory", 			new String[]{"HeapMemoryUsage"}),
	THREADING			("Threading",		new String[]{"ThreadCount"}),
	RUNTIME				("Runtime",			new String[]{"Uptime"}),	
	OPERATING_SYSTEM	("OperatingSystem",	new String[]{"AvailableProcessors"}),
	LOGGING				("Logging",			new String[]{"LoggerLevel"}),
	
	HYBRIS_CACHE		(JmxBeansEnu.OBJECT_HYBRIS_PREFIX, 	"main=Cache Main",	new String[]{"CurrentCacheSize"});
	
	final static String OBJECT_JAVA_PREFIX 		= 	"java.lang:type=";
	final static String OBJECT_HYBRIS_PREFIX 	= 	"hybris:tenantscope=Master Tenant,";
	private String objectName;
	private String[] attributes;
	
	private JmxBeansEnu(String objectName, String[] attributes) {
		this(OBJECT_JAVA_PREFIX, objectName, attributes);
	}

	private JmxBeansEnu(String objectNamePrefix, String objectName, String[] attributes) {
		this.objectName = objectNamePrefix+objectName;
		this.attributes = attributes;
	}

	public String getObjectName() {
		return objectName;
	}

	public String[] getAttributes() {
		return attributes;
	}
	
}
