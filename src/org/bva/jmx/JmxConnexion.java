package org.bva.jmx;

import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.net.MalformedURLException;
import java.util.Iterator;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxConnexion {
	private static final String JMX_URL_PREFIX 		= 	"service:jmx:rmi:///jndi/rmi://";
	private static final String JMX_URL_SUFFIX 		= 	"/jmxrmi";
	private static final String JMX_DEFAULT_HOST 	= 	"localhost";
	private static final int 	JMX_DEFAULT_PORT 	= 	9003;

	private String 	host;
	private int 	port;
	private String 	login;
	private String 	password;
	
	private JMXServiceURL 			jmxUrl;
	private JMXConnector 			jmxCon;
	private MBeanServerConnection 	mBeanCon;
	
	StringBuilder			output;
	
	public JmxConnexion() {
		this(JMX_DEFAULT_HOST);
	}

	public JmxConnexion(String host) {
		this(host, JMX_DEFAULT_PORT);
	}

	public JmxConnexion(String host, int port) {
		this.host = host;
		this.port = port;
		output = new StringBuilder();
	}
	
	public boolean openConnexion(){
		try {
			jmxUrl = new JMXServiceURL( JMX_URL_PREFIX + host + ":" + port + JMX_URL_SUFFIX);
			jmxCon = JMXConnectorFactory.connect(jmxUrl);
			mBeanCon = jmxCon.getMBeanServerConnection();
			if(jmxCon.getConnectionId() != null) return true;
		} catch ( IOException e) {
			if(jmxCon != null)
				try {
					jmxCon.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			jmxCon = null;
			jmxUrl = null;
		}
		return false;
	}

	public Object queryObject(String name, String attribute) throws MalformedObjectNameException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException{
		return mBeanCon.getAttribute(new ObjectName(name), attribute);
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
