package org.bva.jmx;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.bva.jmx.beans.JmxBean;
import org.bva.jmx.beans.JmxClassLoaded;
import org.bva.jmx.beans.JmxMemory;
import org.bva.jmx.beans.JmxMemoryUsed;

public class JmxCli {
	
	static final int			EXIT_OK		 	=	0; 
	static final int			EXIT_WARNING 	=	1; 
	static final int			EXIT_CRITICAL	=	2; 
	static final int			EXIT_UNKNOW	 	=	3; 
	static final String			JMX_CHECK_NAME	= 	"Jmx Check";
	static final String			CLI_CLASSES		=	"classes";
	static final String			CLI_MEMORY		=	"memory";

	private Options 			options;
	
	private CommandLineParser 	parser;
	
	private CommandLine			cmd;
	
	private HelpFormatter		help;
	
	private String[]			args;
	
	private JmxConnexion		jmxCo;
	
	private List<JmxBean>		beans;
	
	public JmxCli(String[] args) {
		options = new Options();
		options.addOption("h", false, "Display this help");
		options.addOption("u", true, "JMX Url, default : locahost");
		options.addOption("p", true, "JMX port, default : 9003");
		options.addOption("username", true, "JMX username");
		options.addOption("password", true, "JMX password");
		options.addOption(CLI_CLASSES, true, "Query loaded classes, must be follow with warning and critical : example 40000:80000");
		options.addOption(CLI_MEMORY, true, "Query memory, must be follow with warning and critical : example 4000:8000");
		
		parser = new PosixParser();
		
		help = new HelpFormatter();
		
		this.args = args;
	
		this.jmxCo = new JmxConnexion();
		if( ! this.jmxCo.openConnexion() ){
			System.exit(EXIT_CRITICAL);
		}
		
		beans = new ArrayList<JmxBean>();
	}
	
	public void parse(){
		try {
			cmd = parser.parse(options, args);
			dispatch();
		} catch (ParseException e) {
			help.printHelp(JMX_CHECK_NAME, options);
			System.exit(EXIT_UNKNOW);
		}		
	}

	private void dispatch() {
		if(cmd.hasOption('h')){
			help.printHelp(JMX_CHECK_NAME, options);
		}
		
		if (cmd.hasOption('u')){
			this.jmxCo.setHost(cmd.getOptionValue('u'));
		}
		
		if (cmd.hasOption(CLI_CLASSES)) {
			beans.add(new JmxClassLoaded(cmd.getOptionValue(CLI_CLASSES)));
		}

		if (cmd.hasOption(CLI_MEMORY)) {
			beans.add(new JmxMemoryUsed(cmd.getOptionValue(CLI_MEMORY)));
		}
		
		run();
	}

	private void run() {
		for (JmxBean jmx : beans) {
			jmx.queryJmx(jmxCo);
			System.out.println(jmx);
		}
	}
}
