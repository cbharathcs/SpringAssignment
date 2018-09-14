package com.rabo.bank.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabo.bank.dto.ErrorRecord;
import com.rabo.bank.dto.Record;
import com.rabo.bank.jaxb.RecordJaxb;
import com.rabo.bank.jaxb.RecordJaxbRoot;

@Component
public class StmtFileUtil {

	@Autowired
	private RecordJAXBUtil recordJAXBUtil;
	public CSVParser csvReader(String fileName) throws Exception {
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));

		CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(br);

		return parser;
	}

	public List<Record> getValue(String fileName) throws Exception {
		CSVParser parser = csvReader(fileName);
		
		List<Record> records = new ArrayList<>();
		for (CSVRecord record : parser) {
			Record recordVal = new Record();
			String refNumber = record.get("Reference").trim();
			recordVal.setReference(Long.parseLong(refNumber));
			recordVal.setAccountNumber(record.get("AccountNumber").trim());
			recordVal.setDescription(record.get("Description").trim());
			recordVal.setStartBalance(new BigDecimal(record.get("Start Balance")));
			recordVal.setMutation(new BigDecimal(record.get("Mutation").trim()));
			recordVal.setEndBalance(new BigDecimal(record.get("End Balance")));

			records.add(recordVal);
		}
		parser.close();
		return records;
	}


	public void successFileWrite(String filePath,Map<String,List<Record>> customerRec) throws Exception {
		
		for(String key:customerRec.keySet()){
			List<Record> records = customerRec.get(key);
			File filecsv = new File(filePath+Constant.CSVFile+key+Constant.CSV);
			BufferedWriter writercsv = new BufferedWriter(new FileWriter(filecsv));
			RecordJaxbRoot recordJaxbRoot =  new RecordJaxbRoot();
			List<RecordJaxb> recordJaxbs = new ArrayList<>();
			RecordJaxb recordJaxb = null;
			CSVPrinter csvPrinter = new CSVPrinter(writercsv,
					CSVFormat.DEFAULT.withHeader("reference", "accountNumber", "description", "startBalance","mutation","endBalance"));
			for(Record record:records){
				recordJaxb = new RecordJaxb(); 
				csvPrinter.printRecord(record.getReference(),record.getAccountNumber(),record.getDescription(),record.getStartBalance(),record.getMutation(),record.getEndBalance());
				recordJaxb.setReference(record.getReference());
				recordJaxb.setAccountNumber(record.getAccountNumber());
				recordJaxb.setDescription(record.getDescription());
				recordJaxb.setAccountNumber(record.getAccountNumber());
				recordJaxb.setStartBalance(record.getStartBalance());
				recordJaxb.setEndBalance(record.getEndBalance());
				recordJaxb.setMutation(record.getMutation());
				recordJaxbs.add(recordJaxb);
				recordJaxbRoot.setRecords(recordJaxbs);
			};
			csvPrinter.flush();
			csvPrinter.close();
			String filexmlname = filePath+Constant.XMLFile+key+Constant.XML;
			//BufferedWriter writerxml = new BufferedWriter(new FileWriter(filexml));
			File filexml = new File(filexmlname);
			recordJAXBUtil.xmlData(recordJaxbRoot, filexml);
			
			
			String zipFileName = filePath +Constant.ZIPFile+ key+Constant.ZIP;
			 
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            
            addToZipFile(filecsv, zos);
            addToZipFile(filexml, zos);
            zos.closeEntry();
            zos.close();
			
		}

		
		
		
		
	}
	
	
	public void addToZipFile(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(file.getName());
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close(); 
	}

	public void fileReport(String filePath, List<ErrorRecord> errorList) throws Exception{
		File filecsv = new File(filePath+Constant.FAILED+"/"+Constant.FAILED+Constant.CSV);
		BufferedWriter writercsv = new BufferedWriter(new FileWriter(filecsv));
		
		@SuppressWarnings("resource")
		CSVPrinter csvPrinter = new CSVPrinter(writercsv,
				CSVFormat.DEFAULT.withHeader("reference", "description", "accountNumber"));
	
			
		for(ErrorRecord errorRecord :errorList){
			
			csvPrinter.printRecord(errorRecord.getReference(),errorRecord.getDescription(),errorRecord.getAccountNumber());
			
		}
		csvPrinter.flush();
		csvPrinter.close();
	}
}
