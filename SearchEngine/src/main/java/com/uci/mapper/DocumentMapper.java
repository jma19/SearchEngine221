package com.uci.mapper;

import com.uci.mode.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DocumentMapper {

    @Select("SELECT * FROM CITY WHERE state = #{state}")
    List<Document> findByState(@Param("state") String state);

}