package org.bva.jmx;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class JmxCli {
	
	static final String			JMX_CHECK_NAME	= 	"Jmx Check";
	static final String			JMX				= 	"JMX";

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
		
		for (JmxBeansEnu jmxBean : JmxBeansEnu.values()) {
			options.addOption(jmxBean.name(), true, "Query "+jmxBean.name()+", must be follow with warning and critical : example 40000:80000");			
		}
		
		parser = new PosixParser();
		
		help = new HelpFormatter();
		
		this.args = args;
			
		beans = new ArrayList<JmxBean>();
		
		this.jmxCo = new JmxConnexion();

	}
	
	public void parse(){
		try {
			cmd = parser.parse(options, args);
			dispatch();
		} catch (ParseException e) {
			help.printHelp(JMX_CHECK_NAME, options);
			System.exit(Status.UNKNOWN.getCode());
		}		
	}

	private void dispatch() {
		if(cmd.hasOption('h')){
			help.printHelp(JMX_CHECK_NAME, options);
			System.exit(Status.UNKNOWN.getCode());
		}
		
		if (cmd.hasOption('u')){
			this.jmxCo.setHost(cmd.getOptionValue('u'));
		}

		if (cmd.hasOption("username")){
			this.jmxCo.setLogin(cmd.getOptionValue("username"));
		}

		if (cmd.hasOption("password")){
			this.jmxCo.setPassword(cmd.getOptionValue("password"));
		}

		for (JmxBeansEnu jmxBean : JmxBeansEnu.values()) {
			if (cmd.hasOption(jmxBean.name())) {
				beans.add(new JmxBean(jmxBean, cmd.getOptionValue(jmxBean.name())));
			}
		}
		
		if( ! this.jmxCo.openConnexion() ){
			System.exit(Status.CRITICAL.getCode());
		}
		
		run();
		
		print();
	}

	private void run() {
		for (JmxBean jmx : beans) {
			jmx.queryJmx(jmxCo);
		}
	}

	private void print() {
		StringBuilder sb = new StringBuilder(20);
		
		sb.append(JMX);
		sb.append(" ");
		sb.append(getStatus().name());
		sb.append(" - ");
		
		for (JmxBean jmx : beans) {
			sb.append(jmx.toStringTitle());
			sb.append(" ");
		}

		sb.append(" | ");

		for (JmxBean jmx : beans) {
			sb.append(jmx.toString());
			sb.append(" ");
		}
		
		System.out.print(sb.toString());
	}

	private Status getStatus() {
		Status status = Status.OK;
		for (JmxBean jmx : beans) {
			if( jmx.getStatus() == Status.CRITICAL )
				return Status.CRITICAL;
			
			if( jmx.getStatus() == Status.WARNING )
				status = Status.WARNING;
		}
		return status;
	}

}
