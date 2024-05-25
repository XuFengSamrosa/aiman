package com.example.ai.service;

import org.apache.commons.io.FileUtils;

import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


@Service

public class ReturnMultipartFile {
	private static final Logger logger = LoggerFactory.getLogger(PythonScriptService.class);
	@Value("${linux.server.result}")
	private String resultDir;
	private static final String result = "result_voice.mp4";
	public void returnMultipartFile(HttpServletResponse response) {
		File file = new File(resultDir+result);
		try {
			long fileSize = file.length();
			logger.info("Actual file size: " + fileSize + " bytes");
			byte[] filebyte = FileUtils.readFileToByteArray(file);
			MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), "application/octet-stream", filebyte);
			logger.info(multipartFile.getName());
			OutputStream stream= response.getOutputStream();
			response.setContentType("video/mp4");
			stream.write(filebyte);
			logger.info("Writing " + filebyte.length + " bytes to response");
			stream.flush();
			stream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
