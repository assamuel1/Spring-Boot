package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {


    @Select("SELECT * FROM NOTES where userid = #{userid}")
    List<Notes> getAllNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle , notedescription, userid) VALUES(#{notetitle}, #{notedescription},  #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Notes note);

    @Delete("Delete FROM NOTES where noteId = #{noteId}")
    void deleteNotes(Integer noteId);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} where noteId = #{noteId}")
    void updateNotes(Notes note);

    @Select("SELECT * FROM NOTES where noteId = #{noteId}")
    int getNoteBynoteId(Integer noteId);
}
