package org.bva.jmx;

public enum Status {
	OK			(0),
	WARNING		(1),
	CRITICAL	(2),
	UNKNOWN		(3);
	
	
	int code;
	
	private Status(int code) {
		this.code = code;
	}
	
	int getCode(){
		return this.code;
	}
}
