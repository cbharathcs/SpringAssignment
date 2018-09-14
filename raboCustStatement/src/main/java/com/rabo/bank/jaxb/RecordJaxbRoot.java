package com.rabo.bank.jaxb;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "records")
public class RecordJaxbRoot implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<RecordJaxb> getRecords() {
		return records;
	}

	public void setRecords(List<RecordJaxb> records) {
		this.records = records;
	}

	private List<RecordJaxb> records;

}
