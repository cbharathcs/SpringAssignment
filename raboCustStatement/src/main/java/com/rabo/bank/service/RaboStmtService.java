package com.rabo.bank.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.rabo.bank.dto.ErrorRecord;
import com.rabo.bank.dto.Record;
import com.rabo.bank.exception.RaboStmtException;
import com.rabo.bank.util.Constant;
import com.rabo.bank.util.StmtFileUtil;

@Service
@Configuration
@PropertySources({ @PropertySource(value = "classpath:env.properties"), @PropertySource("classpath:errorCode.properties") })
public class RaboStmtService {

	@Autowired
	private Environment env;

	@Autowired
	private StmtFileUtil stmtFileReader;

	@SuppressWarnings("unused")
	public List<ErrorRecord> createStatement() throws RaboStmtException {
		List<ErrorRecord> errorList = new ArrayList<>();
		Map<String,List<Record>> customerRec = null;
		String filePath = "";
		try {
			filePath = System.getenv(env.getProperty(Constant.FILE_LOCATION))+"/";
			String fileName = filePath+ Constant.FILE_NAME;
			String archivePath = filePath+Constant.ARCHIVE +Constant.FILE_NAME;
			List<Record> dataFile = stmtFileReader.getValue(fileName);
			//Archive the transaction file.
			File oldFile = new File(fileName);
			oldFile.renameTo(new File(archivePath));
			
			customerRec = validateTransaction(dataFile,errorList,customerRec);
		
			
			stmtFileReader.successFileWrite(filePath,customerRec);
			
			stmtFileReader.fileReport(filePath,errorList);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RaboStmtException(Constant.E001,env.getProperty(Constant.E001));
		}
		return errorList;
	}



	
	private Map<String, List<Record>> validateTransaction(List<Record> dataFile, List<ErrorRecord> errorList, Map<String,List<Record>> customerRec ) {
		Map<Long,Record> uniqueTransRecord = new HashMap<>();
		
		
		
		Set<Long> dublicateTransID = new HashSet<>();
		
		dataFile.forEach(dataLine ->{
			
			if(dublicateTransID.contains(dataLine.getReference())){
				errorList.add(returnErrorRecord( dataLine,"Transaction ID is duplicate"));
				
			}else{
				if(uniqueTransRecord.get(dataLine.getReference()) == null ){
					uniqueTransRecord.put(dataLine.getReference(), dataLine);
					if(validateAccountBalance(dataLine) != null)
						errorList.add(validateAccountBalance(dataLine));
				}else{
					Record record= uniqueTransRecord.get(dataLine.getReference());
					dublicateTransID.add(dataLine.getReference());
					//if(	record != null)
					errorList.add(returnErrorRecord( record,"Transaction ID is duplicate"));
					uniqueTransRecord.remove(dataLine.getReference());
					
					errorList.add(returnErrorRecord( dataLine,"Transaction ID is duplicate"));
				}
			}
			
			
		});
		
		return uniqueTransRecord.values().stream().collect(Collectors.groupingBy(Record :: getAccountNumber));
		
		
	}


	private ErrorRecord validateAccountBalance(Record record) {
		
		BigDecimal tol = record.getStartBalance().add(record.getMutation());
		
		if(tol.compareTo(record.getEndBalance()) != 0){
			returnErrorRecord( record,"Error in end balance");
		}
		
		return null;
	}
	private ErrorRecord returnErrorRecord(Record record,String msg){
		ErrorRecord errorRecord = new ErrorRecord();
		errorRecord.setReference(record.getReference());
		errorRecord.setAccountNumber(record.getAccountNumber());
		errorRecord.setDescription(msg);
		return errorRecord;
	}
}
