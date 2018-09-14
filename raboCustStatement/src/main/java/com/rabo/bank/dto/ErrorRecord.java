package com.rabo.bank.dto;

import java.io.Serializable;
import java.util.List;

public class ErrorRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	private long reference;
	private String description;
	private String accountNumber;

	public long getReference() {
		return reference;
	}

	public void setReference(long reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "ErrorRecord [reference=" + reference + ", description=" + description + ", accountNumber="
				+ accountNumber + "]";
	}

}
