package com.example.demo.dao;

import com.example.demo.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 访问miaosha_user表
 * create table miaosha_user
 * (
 *     id              bigint auto_increment comment '用户ID，手机号码'
 *         primary key,
 *     nickname        varchar(255)  not null,
 *     password        varchar(32)   null comment 'MD5(MD5(pass明文+固定salt) + salt)',
 *     salt            varchar(10)   null,
 *     head            varchar(128)  null comment '头像，云存储的ID',
 *     register_date   datetime      null comment '注册时间',
 *     last_login_date datetime      null comment '上蔟登录时间',
 *     login_count     int default 0 null comment '登录次数'
 * );
 * @author wk-ff
 */
@SuppressWarnings("all")
@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where id = #{id}")
    MiaoshaUser getByID(@Param("id") long id);
}
