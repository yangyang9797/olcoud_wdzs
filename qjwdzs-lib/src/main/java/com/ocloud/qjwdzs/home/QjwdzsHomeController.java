package com.ocloud.qjwdzs.home;

import com.ocloud.anno.web.WebController;
import com.ocloud.web.core.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "8.1.1", description = "房屋信息填报[/qjwdzs/home]")
@WebController("/qjwdzs/home")
public class QjwdzsHomeController extends BaseController<QjwdzsHomeService, QjwdzsHomeMapper, QjwdzsHome> {


}
