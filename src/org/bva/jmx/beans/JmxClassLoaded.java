package org.bva.jmx.beans;

public class JmxClassLoaded extends JmxBean{
	static final String CLASS_LOADED_OBJECT = "ClassLoading";
	static final String CLASS_LOADED_ATTRIBUTE = "LoadedClassCount";
	
	static final int DEFAULT_CLASS_LOADED_WARNING = 40000;
	static final int DEFAULT_CLASS_LOADED_CRITICAL = 80000;

	public JmxClassLoaded() {
		super(OBJECT_JAVA_PREFIX+CLASS_LOADED_OBJECT, CLASS_LOADED_ATTRIBUTE, DEFAULT_CLASS_LOADED_WARNING, DEFAULT_CLASS_LOADED_CRITICAL);
	}
	public JmxClassLoaded(int warning, int critical) {
		super(OBJECT_JAVA_PREFIX+CLASS_LOADED_OBJECT, CLASS_LOADED_ATTRIBUTE, warning, critical);
	}
}
