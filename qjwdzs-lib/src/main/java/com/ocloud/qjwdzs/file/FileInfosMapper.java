package com.ocloud.qjwdzs.file;

import com.ocloud.file.FileInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface FileInfosMapper  {

    @Select("DELETE FROM com_fileinfo WHERE refid  = #{refid}")
    void deletByrefid(@Param("refid") String refid);

    List<FileInfo> getByUserId(@Param("search") String search);

    List<Map> pagebyType(@Param("filename") String filename, @Param("xzqh") String xzqh);

    @Select("update COM_FILEINFO set userid=#{userid},ctime=sysdate where fileid=#{fileid}")
    List<FileInfo> save(@Param("fileid") String fileid, @Param("userid") String userid);

    List<FileInfo> getFileinfoByXzqh(@Param("xzqh") String xzqh);

    @Select("select * FROM com_fileinfo where reftype='压缩包' order by ctime desc")
    List<Map> pagebyZip();

}
