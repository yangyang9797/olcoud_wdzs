package com.ocloud.qjwdzs.obligee;

import com.ocloud.web.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface QjwdzsObligeeInfoMapper extends BaseMapper<QjwdzsObligeeInfo> {

    List<Map> selectByName(@Param("zzqlr") String zzqlr,@Param("cuserid") String cuserid);

    List<Map> todayInfo(@Param("update") String update, @Param("downdate") String downdate,@Param("cuserid") String cuserid);

    List<Map> lackInfo(String cuserid);

    List<Map> getAllInfo(String cuserid);

    Map checkFile(String qlrid);

    List<Map> selectOthersByQLRId(String qlrid);

    void setDCZT(String qlrid, String dczt);

    List<Map> selectByQjinid(String qjinid);

    List<Map> findFileInfoByPId(@Param("qjinid") String qjinid);

    void deleteNullData();
}
