package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface NoteMapper {


    @Select("SELECT * FROM NOTES where userid = #{userid}")
    List<Notes> getAllNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle , notedescription, userid) VALUES(#{notetitle}, #{notedescription},  #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Notes note);

    @Delete("Delete FROM NOTES where noteid = #{noteid}")
    void deleteNotes(Integer noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} where noteid = #{noteid}")
    void updateNotes(Notes note);

    @Select("SELECT * FROM NOTES where noteid = #{noteid}")
    int getNoteByNoteid(Integer noteid);
}
