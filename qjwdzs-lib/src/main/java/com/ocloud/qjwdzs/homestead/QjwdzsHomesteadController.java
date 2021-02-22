package com.ocloud.qjwdzs.homestead;

import com.ocloud.anno.web.WebController;
import com.ocloud.web.core.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "8.1.0",description = "宅基地信息填报[/qjwdzs/homestead]")
@WebController("/qjwdzs/homestead")
public class QjwdzsHomesteadController extends BaseController<QjwdzsHomesteadService,QjwdzsHomesteadMapper,QjwdzsHomestead> {


}
