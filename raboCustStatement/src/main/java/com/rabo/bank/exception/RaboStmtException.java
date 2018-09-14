package com.rabo.bank.exception;

import java.io.Serializable;

public class RaboStmtException extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;
	public String errorCode;
	public RaboStmtException(String errorCode,String msg){
		super(msg);
		this.errorCode = errorCode;
	}
}
