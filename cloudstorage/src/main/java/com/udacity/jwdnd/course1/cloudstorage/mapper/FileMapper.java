package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {


    @Select("SELECT * FROM FILES where userid = #{userid}")
    List<Files> getAllFiles(Integer userid);

    @Insert("INSERT INTO FILES (filename , contenttype, filesize, userid, filedata) VALUES(#{filename} , #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insertFile(Files file);

    @Delete("Delete FROM FILES where fileid = #{fileid}")
    void deleteFile(Integer fileid);


    @Select("SELECT * FROM FILES where fileid = #{fileid}")
    Files getFilebyFileid(Integer fileid);


    @Select("SELECT * FROM FILES where filename = #{filename}")
    Files getFilebyFileName(String filename);
}
