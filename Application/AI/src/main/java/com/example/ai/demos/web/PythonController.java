package com.example.ai.demos.web;

//import com.example.ai.service.FileTransToLocalService;
import com.example.ai.service.FileTransToLocalService;
import com.example.ai.service.PythonScriptService;
//import com.example.ai.service.ReturnMultipartFile;
import com.example.ai.service.ReturnMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PythonController {

    @Autowired
    private PythonScriptService pythonScriptService;

    @Autowired
    private ReturnMultipartFile returnMultipartFile;

    @Autowired
    private FileTransToLocalService fileTransToLocalService;



    private static final Logger logger = LoggerFactory.getLogger(PythonController.class);


    @PostMapping("/run-python")
    public String runPythonScript(
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam("audioFile") MultipartFile audioFile,
            HttpServletResponse response
    )  {
//         将传入文件保存到本地
        fileTransToLocalService.TransMultToLocalFile(videoFile, audioFile);

//         执行Python脚本
        pythonScriptService.runPythonScript(videoFile, audioFile);

        // 返回处理后的文件
        returnMultipartFile.returnMultipartFile(response);

        logger.info("Python script executed and result file returned.");
        return "Python script executed";
    }
}
