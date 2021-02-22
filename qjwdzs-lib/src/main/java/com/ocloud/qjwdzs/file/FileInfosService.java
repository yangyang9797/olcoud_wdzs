package com.ocloud.qjwdzs.file;


import com.ocloud.qjwdzs.instance.QjwdzsInstance;
import com.ocloud.qjwdzs.instance.QjwdzsInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

@Slf4j
@Service
@Scope("prototype")
public class FileInfosService  {


    @Value("${ovit.com.file.upload-path:./web/upload}")
    public String uploadPath;

    @Value("${ovit.com.file.preject-zip-path}")
    public String prejectZipPath;

    @Autowired
    QjwdzsInstanceService qwdzsInstanceService;

    public ResponseEntity download(HttpServletRequest request, HttpServletResponse response, String qjinid) {

        QjwdzsInstance qjin = qwdzsInstanceService.getById(qjinid);
        String projectName = "默认";
        if (qjin!=null && !TextUtils.isEmpty(qjin.qjinname)){
            projectName = qjin.qjinname;
        }

        String path = prejectZipPath+projectName+".zip";

        File file = new File(path);
        if (file.exists()) {
            try {
                InputStream is = new FileInputStream(file);
                InputStreamResource isr = new InputStreamResource(is);
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(projectName+".zip", "UTF-8") )
                        .body(isr);
            } catch (Exception es) {

            }
        }

        return null;
    }
    
}
