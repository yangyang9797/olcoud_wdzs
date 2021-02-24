package com.ocloud.qjwdzs.obligee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ocloud.common.file.FileUtil;
import com.ocloud.common.file.Zip;
import com.ocloud.common.tool.MapUtil;
import com.ocloud.common.tool.StringUtil;
import com.ocloud.file.FileInfo;
import com.ocloud.file.FileInfoService;
import com.ocloud.qjwdzs.home.QjwdzsHome;
import com.ocloud.qjwdzs.home.QjwdzsHomeService;
import com.ocloud.qjwdzs.homestead.QjwdzsHomestead;
import com.ocloud.qjwdzs.homestead.QjwdzsHomesteadService;
import com.ocloud.qjwdzs.instance.QjwdzsInstance;
import com.ocloud.qjwdzs.instance.QjwdzsInstanceService;
import com.ocloud.qjwdzs.utils.CopyFileDirUtil;
import com.ocloud.web.core.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class QjwdzsObligeeInfoService extends BaseService<QjwdzsObligeeInfoMapper, QjwdzsObligeeInfo> {

    @Value("${ovit.com.file.preject-zip-path}")
    public String prejectZipPath;

    @Value("${ovit.com.file.files-path}")
    public String filesPath;

    @Autowired
    private QjwdzsInstanceService qjwdzsInstanceService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private QjwdzsHomesteadService qjwdzsHomesteadService;
    @Autowired
    private QjwdzsHomeService qjwdzsHomeService;

    public List<Map> selectByName(String zzqlr,String cuserid) {
        Assert.notNull(zzqlr, "权利人姓名为空");
        Assert.notNull(cuserid, "创建人ID为空");
        return baseMapper.selectByName(zzqlr,cuserid);
    }

    public List<Map> todayInfo(String cuserid) {
        Assert.notNull(cuserid, "创建人ID为空");
        //获取当天日期和第二天日期
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 0);
        Date update = calendar.getTime(); //这个时间就是日期往后推一天的结果
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        Date downdate = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return baseMapper.todayInfo(format.format(update), format.format(downdate),cuserid);
    }

    public List<Map> lackInfo(String cuserid) {
        Assert.notNull(cuserid, "创建人ID为空");
        return baseMapper.lackInfo(cuserid);
    }

    public List<Map> getAllInfo(String cuserid) {
        Assert.notNull(cuserid, "创建人ID为空");
        return baseMapper.getAllInfo(cuserid);
    }

    public Map selectByQLRId(String qlrid) {
        Assert.notEmpty(qlrid, "权利人id为空");
        //权利人基本信息查询
        Map qlr = MapUtil.tomap(baseMapper.selectById(qlrid));
        Assert.notEmpty(qlr, "权利人不存在");
        //相关人员
        if (StringUtil.IsNotEmpty(qlr.get("publicstate"))) {
            qlr.put("publicstate", JSONArray.parse(qlr.get("publicstate").toString()));
        }

        Map result = Maps.newHashMap();
        result.put("qlr", qlr);
        //权利人所有照片信息
        List<Map<String, Object>> qlrFile = fileInfoService.getBaseMapper().selectMaps(new QueryWrapper<FileInfo>().eq("refid", qlrid));
        result.put("zp", typeOfPic(qlrFile));
        //房屋信息
        result.put("fw", qjwdzsHomeService.getBaseMapper().selectMaps(new QueryWrapper<QjwdzsHome>().eq("qlrid", qlrid)));
        //宅基地信息
        result.put("zjd", qjwdzsHomesteadService.getBaseMapper().selectMaps(new QueryWrapper<QjwdzsHomestead>().eq("qlrid", qlrid)));

        return result;
    }


    //照片分类
    public Map typeOfPic(List<Map<String, Object>> mapList) {
        Set typelist = Sets.newHashSet();
        //1.选出照片类别
        mapList.stream().forEach(map -> {
            typelist.add(map.get("reftype"));
        });
        //2.进行分类
        Map map = Maps.newHashMap();
        typelist.stream().forEach(type -> {
            map.put(type, mapList.stream().filter(m -> m.get("reftype").equals(type)).collect(Collectors.toList()));
        });
        return map;
    }

    /**
     *检查权利人资料是否齐全
     * @param qlrid
     * @return
     */
    public Map setDCZT(String qlrid) {
        Assert.notEmpty(qlrid, "权利人ID为空");
        String dczt = "zldb";
        Map map = baseMapper.checkFile(qlrid);
        //户口本至少1张
        //身份证至少2张
        //土地证至少1张
        //房产证至少1张
        //房屋远景至少3张
        //门牌号至少1张
        if (map != null && map.size() > 0) {
            if (Integer.valueOf(map.get("hkb").toString()) >= 1 &&
                    Integer.valueOf(map.get("sfz").toString()) >= 2 &&
                    Integer.valueOf(map.get("tdz").toString()) >= 1 &&
                    Integer.valueOf(map.get("fcz").toString()) >= 1 &&
                    Integer.valueOf(map.get("fwyj").toString()) >= 3 &&
                    Integer.valueOf(map.get("mph").toString()) >= 1) {
                dczt = "ywc";
            }
        }

        baseMapper.setDCZT(qlrid, dczt);
        Map m = Maps.newHashMap();
        return m;
    }

    public List<Map> selectByQjinid(String qjinid) {
        Assert.notEmpty(qjinid, "项目id为空");
        return baseMapper.selectByQjinid(qjinid);
    }


    @Transactional
    @Async
    public Map<String, Object> output(String qjinid, boolean isUpdate) {
        Map<String, Object> resultMap = Maps.newHashMap();
        List<Map> fileInfoMap = findFileInfoByPId(qjinid);
        QjwdzsInstance qjin = qjwdzsInstanceService.getById(qjinid);
        String projectName = "默认";
        if (qjin != null && !TextUtils.isEmpty(qjin.qjinname)) {
            projectName = qjin.qjinname;
        }

        String projectPath = prejectZipPath + projectName;  // 项目路径
        String filePath = filesPath;  // 照片附件路径
        FileUtil.createDir(projectPath);
        for (Map map : fileInfoMap) {
            StringBuffer sb = new StringBuffer();
            sb.append(map.get("zzqlr")).append("_").append(map.get("zjh"));
            String fwybm = map.get("fwybm") + "";
            if (!TextUtils.isEmpty(fwybm) && !"null".equals(fwybm)) {
                List<HashMap> arrayLists = JSON.parseArray(fwybm, HashMap.class);
                for (HashMap hashMap : arrayLists) {
                    String value = (String) hashMap.get("fwybm");
                    if (!TextUtils.isEmpty(value) && !"null".equals(value)) {
                        sb.append("+").append(value);
                    }
                }
            }
            String fileName = sb.toString();
            String itemPath = projectPath + File.separator + fileName;
            FileUtil.createDir(itemPath);

            String sortPath = itemPath + File.separator + map.get("reftype");
            FileUtil.createDir(sortPath);

            String fileid = (String) map.get("fileid");
            String fileext = (String) map.get("fileext");

            String oldPath = filePath + File.separator + fileid + "." + fileext;
            String newPath = sortPath + File.separator + fileid + "." + fileext;
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                CopyFileDirUtil.copyFile(oldPath, newPath);
            }

        }
        try {
            String zipFilePath = prejectZipPath + projectName + ".zip";
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                zipFile.delete();
            }
            Zip.zipFiles(projectPath, zipFilePath);
            // 修改状态为2
            qjin.setStatus("2");
            qjin.setGdtime(new Date());
            qjwdzsInstanceService.updateById(qjin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultMap.put("desc", "成功归档" + fileInfoMap.size() + "条数据");
        resultMap.put("gdtime", qjin.getGdtime());
        resultMap.put("state", qjin.getStatus());
        return resultMap;
    }

    public List<Map> findFileInfoByPId(String qjinid) {
        return this.baseMapper.findFileInfoByPId(qjinid);
    }

    public void deleteNullData() {
        baseMapper.deleteNullData();
    }
}
