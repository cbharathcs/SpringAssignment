package com.rabo.bank.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Component;


@Component
public class RecordJAXBUtil {
	public void xmlData(Object dataObject,File file)throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(dataObject.getClass());

        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dataObject, file);

        marshaller.marshal(dataObject, System.out);
	}
}
