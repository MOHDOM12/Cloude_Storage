package com.example.NewPro_cloudeStorage.mapper;


import com.example.NewPro_cloudeStorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getCredentials(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid} AND credentialid = #{credentialid}")
    Credentials getCredential(Integer userid, Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key , userid, password)"+
            " VALUES(#{url}, #{username}, #{key}, #{userid}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer addCredential(Credentials credentials);


    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Integer deleteCredentials(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password}"+
            " WHERE credentialid = #{credentialid}")
    Integer updateCredentials(Credentials credentials);

}
