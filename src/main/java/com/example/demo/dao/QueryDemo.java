package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QueryDemo {

    @Insert("insert into query_demo(id, name) values(#{id}, #{name})")
    public void insertTo(@Param("id") int id, @Param("name") String name);
}
