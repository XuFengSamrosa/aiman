package com.example.ai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PythonScriptService {



    @Value("${linux.server.directory}")
    private String serverDirectory;


    private static final Logger logger = LoggerFactory.getLogger(PythonScriptService.class);

    public void runPythonScript(MultipartFile videoFile, MultipartFile audioFile) {
        try {
            String originalVideoName = videoFile.getOriginalFilename();
            String originalAudioName = audioFile.getOriginalFilename();
            String videoName = serverDirectory + originalVideoName;
            String audioName = serverDirectory + originalAudioName;
//            String scriptPath = "inference.py";
//            String command = python + " " + scriptPath +
//                    " --checkpoint_path ./checkpoints/wav2lip.pth" +
//                    " --face " + videoName +
//                    " --audio " + audioName;
//            ProcessBuilder builder = new ProcessBuilder();
//            List<String> list = new ArrayList<>();
//            list.add("bash ");
//            list.add("/app/bin/runtest.sh");

//            Process process = builder.command(
////            String baidu="ping www.baidu.com";
//            logger.info("Running command: {}", list);
//
//            ProcessBuilder processBuilder = new ProcessBuilder();
////            processBuilder.redirectErrorStream(true);
//            Process process = processBuilder.command(list).start();
//            process.waitFor();
            String[] cmd = {"/app/bin/run.sh",videoName,audioName};
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            boolean exitCode = p.waitFor(300L, TimeUnit.SECONDS);
            System.out.println("output:" + sb.toString());
            System.out.println("exit:" + exitCode);
            // 读取标准输出和错误输出
            logger.info("Python script executed successfully");

        } catch (Exception e) {
            logger.error("Error executing Python script", e);
        }
    }
}
