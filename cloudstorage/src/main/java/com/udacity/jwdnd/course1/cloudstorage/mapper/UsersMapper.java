package com.udacity.jwdnd.course1.cloudstorage.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
    public interface UsersMapper {
        @Select("SELECT * FROM USERS WHERE username = #{username}")
        Users getUser(String username);

        @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
        @Options(useGeneratedKeys = true, keyProperty = "userId")
        int insert(Users user);

        @Select("SELECT userid FROM USERS WHERE username = #{username}")
        int getUserIdByUsername(String username);
}


