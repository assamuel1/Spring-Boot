package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS where userid = #{userid}")
    List<Credentials> getAllCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url , username, password, key, userid) VALUES(#{url}, #{username},  #{password}, #{key}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int createCredential(Credentials credentials);

    @Delete("Delete FROM CREDENTIALS where credentialid = #{credentialid}")
    void deleteCredentials(Integer credentialsid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} , key = #{key} where credentialid = #{credentialid}")
    void updateCredentials(Credentials note);
}
