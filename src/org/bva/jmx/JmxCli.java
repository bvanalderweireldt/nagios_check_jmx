package org.bva.jmx;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class JmxCli {
	
	static final int			EXIT_OK		 	=	0; 
	static final int			EXIT_WARNING 	=	1; 
	static final int			EXIT_CRITICAL	=	2; 
	static final int			EXIT_UNKNOW	 	=	3; 
	static final String			JMX_CHECK_NAME	= 	"Jmx Check";
	
	private Options 			options;
	
	private CommandLineParser 	parser;
	
	private CommandLine			cmd;
	
	private HelpFormatter		help;
	
	private String[]			args;
	
	JmxConnexion 				jmxCo;
	
	public JmxCli(String[] args) {
		options = new Options();
		options.addOption("h", false, "Display this help");
		options.addOption("u", true, "JMX Url, example : <host>:<port>");
		options.addOption("O", true, "Objects to query, must be separated by a coma.Valid values are : TODO ");
		options.addOption("w", true, "Warning level, must correspond to the number of objects, separated by a coma");
		options.addOption("c", true, "Critical level, must correspond to the number of objects, separated by a coma");
		options.addOption("classes", true, "Query loaded classes, must be follow with warning and critical : example 40000:80000");		
		options.addOption("username", true, "JMX username");
		options.addOption("password", true, "JMX password");
		
		parser = new PosixParser();
		
		help = new HelpFormatter();
		
		this.args = args;
	
		this.jmxCo = new JmxConnexion();
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
		if (cmd.hasOption("classes")) {
			
		}
	}
}
