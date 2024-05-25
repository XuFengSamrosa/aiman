package com.example.ai.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileTransToLocalService {
	@Value("${linux.server.directory}")
	private String serverDirectory;

	public void TransMultToLocalFile(MultipartFile videoFile, MultipartFile audioFile){
		final Logger logger = LoggerFactory.getLogger(PythonScriptService.class);
		try {
			String originalvideoName = videoFile.getOriginalFilename();
			String originalaudioName = audioFile.getOriginalFilename();
			String videoName = serverDirectory+originalvideoName;
			String audioName = serverDirectory+originalaudioName;
			videoFile.transferTo(new File(videoName));
			audioFile.transferTo(new File(audioName));
			logger.info(videoName);
			logger.info(audioName);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
}
