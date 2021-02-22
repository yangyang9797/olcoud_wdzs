package com.ocloud.qjwdzs.file;


import com.ocloud.anno.log.Log;
import com.ocloud.anno.web.WebController;
import com.ocloud.anno.web.WebMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Api(tags = "1.1.0", description = "文件服务[file]")
@WebController("/pub/file")

public class FileInfosController {


    @Autowired
    FileInfosService fileInfoService;


    @ApiOperation(value = "下载文件", notes = "下载文件")
    @Log(module = "文件管理", action = "根据id获取文件", tag = "/download")
    @WebMapping("/download")
    public ResponseEntity downloadfile(HttpServletRequest request, HttpServletResponse response, String qjinid) {
        return fileInfoService.download(request, response, qjinid);
    }

}
