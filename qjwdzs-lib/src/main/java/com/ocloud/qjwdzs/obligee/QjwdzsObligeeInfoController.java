package com.ocloud.qjwdzs.obligee;

import com.google.common.collect.Maps;
import com.ocloud.anno.log.Log;
import com.ocloud.anno.web.WebController;
import com.ocloud.anno.web.WebMapping;
import com.ocloud.web.core.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.Map;


@Slf4j
@Api(tags = "8.1.2", description = "权利人填报信息[qjwdzs/obligee]")
@WebController("/qjwdzs/obligee")
@EnableAsync
public class QjwdzsObligeeInfoController extends BaseController<QjwdzsObligeeInfoService, QjwdzsObligeeInfoMapper, QjwdzsObligeeInfo> {


    @ApiOperation(value = "全局模糊查询", notes = "根据姓名查询")
    @Log(module = "全局模糊查询", action = "根据姓名查询", tag = "/selectByName")
    @WebMapping("/selectByName")
    public List<Map> selectByName(String zzqlr,String cuserid) {
        return service.selectByName(zzqlr,cuserid);
    }

    @ApiOperation(value = "查询当天录入的信息", notes = "查询当天录入的信息")
    @Log(module = "查询当天录入的信息", action = "查询当天录入的信息", tag = "/todayInfo")
    @WebMapping("/todayInfo")
    public List<Map> todayInfo(String cuserid ) {
        return service.todayInfo(cuserid);
    }

    @ApiOperation(value = "资料待补查询", notes = "查询资料欠缺的信息")
    @Log(module = "资料待补查询", action = "查询资料欠缺的信息", tag = "/lackInfo")
    @WebMapping("/lackInfo")
    public List<Map> lackInfo(String cuserid) {
        return service.lackInfo(cuserid);
    }

    @ApiOperation(value = "全部查询", notes = "查询所有信息")
    @Log(module = "全部查询", action = "查询所有信息", tag = "/getAllInfo")
    @WebMapping("/allInfo")
    public List<Map> getAllInfo(String cuserid) {
        return service.getAllInfo(cuserid);
    }

    @ApiOperation(value = "详情查询", notes = "根据权利人ID查询")
    @Log(module = "详情查询", action = "根据权利人ID查询", tag = "/selectByQLRId")
    @WebMapping("/selectByQLRId")
    public Map selectByQLRId(String qlrid) {
        return service.selectByQLRId(qlrid);
    }

    @ApiOperation(value = "设置调查状态", notes = "设置调查状态")
    @Log(module = "设置调查状态", action = "设置调查状态", tag = "/setDCZT")
    @WebMapping("/setDCZT")
    public Map setDCZT(String qlrid) {
        return service.setDCZT(qlrid);
    }

    @ApiOperation(value = "查询项目下的权利人及统计照片",notes = "查询项目下的权利人及统计照片")
    @Log(module = "查询项目下的权利人及统计照片",action = "查询项目下的权利人及统计照片",tag = "/selectByQjinid")
    @WebMapping("/selectByQjinid")
    public List<Map> selectByQjinid(String qjinid){
        return service.selectByQjinid(qjinid);
    }

    @ApiOperation(value = "导出数据", notes = "导出数据")
    @Log(module = "导出数据", action = "导出数据", tag = "/output")
    @WebMapping("/output")
    public Map<String, Object> output(String qjinid, boolean isUpdate) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("message", "正在归档中...");
        // 调用异步处理方法
        service.output(qjinid, isUpdate);
        return res;
    }

    @ApiOperation(value = "删除空数据",notes = "删除空数据")
    @Log(module = "删除空数据",action = "删除空数据",tag = "/deleteNullData")
    @WebMapping("/deleteNullData")
    public void deleteNullData(){
        service.deleteNullData();
    }

}
