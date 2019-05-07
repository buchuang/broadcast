package com.vr.userserver.dao;

import com.vr.userserver.entity.UserDetailInfo;
import com.vr.userserver.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserInfoDao {

    @Select("select * from user_info")
    List<UserInfo> findAll();

    @Select("select * from user_info where user_id=#{username}")
    UserInfo findUserByUserName(@Param("username") String username);

    @Select("select * from user_info where user_id=#{userid}")
    UserInfo findUserById(@Param("userid") Integer userid);

    @Select(("select * from user_info where openid=#{openid}"))
    UserInfo findByOpenid(@Param("openid") String openid);

    @Insert("insert into user_info(openid,nick_name,addr,avatar_url,sex,push_num) " +
            "values(#{user.openid},#{user.nickName},#{user.addr},#{user.avatarUrl},#{user.sex},#{user.pushNum})")
    int save(@Param("user") UserInfo userInfo);

    @Update("update user_info set email=#{user.email},tel=#{user.tel},income=#{user.income},name=#{user.name} " +
            "where openid=#{openid}")
    int updateUserInfoByOpenid(@Param("openid") String openid,@Param("user") UserDetailInfo userDetailInfo);

    @Select("select max(push_num) from user_info")
    String findMaxPushNum();
}
