package org.bva.jmx;

public enum JmxBeansEnu {
	CLASS_LOADED		("ClassLoading", 	"LoadedClassCount"),
	MEMORY_USED			("Memory", 			"HeapMemoryUsage"),
	MEMORY_MAX			("Memory", 			"HeapMemoryUsage"),
	THREAD_COUNT		("Threading",		"ThreadCount"),
	RUNTIME_UPTIME		("Runtime",			"Uptime"),	
	OS_CPU				("OperatingSystem",	"AvailableProcessors"),
	LOG_LEVEL			("Logging",			"LoggerLevel"),
	
	HYBRIS_CACHE		(JmxBeansEnu.OBJECT_HYBRIS_PREFIX, 	"main=Cache Main",	"CurrentCacheSize");
	
	final static String OBJECT_JAVA_PREFIX 		= 	"java.lang:type=";
	final static String OBJECT_HYBRIS_PREFIX 	= 	"hybris:tenantscope=Master Tenant,";
	private String objectName;
	private String attribute;
	
	private JmxBeansEnu(String objectName, String attribute) {
		this(OBJECT_JAVA_PREFIX, objectName, attribute);
	}

	private JmxBeansEnu(String objectNamePrefix, String objectName, String attribute) {
		this.objectName = objectNamePrefix+objectName;
		this.attribute = attribute;
	}

	@Override
	public String toString() {
		return this.attribute;
	}
	
	public String getObjectName() {
		return objectName;
	}

	public String getAttribute() {
		return attribute;
	}
	
}
