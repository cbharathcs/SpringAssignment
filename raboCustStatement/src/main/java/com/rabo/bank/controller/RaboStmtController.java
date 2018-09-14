package com.rabo.bank.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.bank.dto.ErrorRecord;
import com.rabo.bank.exception.RaboStmtException;
import com.rabo.bank.service.RaboStmtService;
import com.rabo.bank.util.Constant;

@RestController
@RequestMapping("/statement")
public class RaboStmtController {
	@Autowired
	private RaboStmtService raboStmtService;

	@Autowired
	private Environment env;

	@RequestMapping(path = "/generate", method = RequestMethod.GET)
	public List<ErrorRecord> createStatement() throws RaboStmtException {

		return raboStmtService.createStatement();
	}

	@RequestMapping(path = "/getFile/{accountNo}", method = RequestMethod.GET, produces = { "application/zip",
			"application/csv", "application/xml" })
	public ResponseEntity<byte[]> getZipfile(HttpServletResponse response, @PathVariable("accountNo") String accountNo,
			@QueryParam("fileType") String fileType) throws Exception {
		String filePath = System.getenv(env.getProperty(Constant.FILE_LOCATION)) + "/";
		if ("CSV".equals(fileType)) {
			filePath = filePath + Constant.CSVFile + accountNo + Constant.CSV;
			return generateResponse(response, filePath, Constant.CSV, accountNo);
		} else if ("XML".equals(fileType)) {
			filePath = filePath+Constant.XMLFile + accountNo + Constant.XML;
			return generateResponse(response, filePath, Constant.XML, accountNo);
		} else {
			filePath = filePath+Constant.ZIPFile + accountNo + Constant.ZIP;
			return generateResponse(response, filePath, Constant.ZIP, accountNo);
		}

	}

	private static ResponseEntity<byte[]> generateResponse(HttpServletResponse response, String filePath, String extention,
			String accountNo) throws Exception {
		byte[] data = Files.readAllBytes(new File(filePath).toPath());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + accountNo + extention)
				.contentLength(new File(filePath).length()) //
				.body(data);
	}

}
