package com.vr.plateserver.dao;

import com.vr.commonutils.utils.R;
import com.vr.plateserver.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PlateDao {

    @Select("select * from dynamics where room_num=#{pushnum}")
    List<Dynamics> findDynamicsByPushNum(@Param("pushnum") String pushNum);

    @Select("select * from room_detail where room_num=#{pushnum}")
    RoomDetail findRoomInfoByPushNum(@Param("pushnum") String pushNum);

    @Insert("insert into room_detail(room_num,room_url) values(#{room.roomNum},#{room.Url})")
    int saveRoomInfo(@Param("room") RoomDetail roomDetail);

    @Insert("insert into dynamics(room_num,video_url,room_status,room_title,plate_id,image_url,city,start_time,audi_num,room_like) " +
            "values(#{room.roomNum},#{room.roomUrl},#{room.roomStatus},#{room.roomTitle},#{room.plateId},#{room.imageUrl},#{room.city},#{room.startTime},#{room.audiNum},#{room.roomLike})")
    int saveDynamic(@Param("room") DynamicsForm dynamicsForm);

    @Update("update dynamics set room_like=room_like+1 where dynamics_id=#{id}")
    int clickLike(@Param("id") Integer dynamicId);

    @Update("update dynamics set room_status='0',audi_num=#{count} where room_status='1' and room_num=#{pushNum}")
    int updateStatusAndCount(@Param("count") Integer count, @Param("pushNum") String pushNum);

    @Update("update dynamics set room_like=room_like-1 where dynamics_id=#{id}")
    int clickNoLike(Integer dynamicId);

    @Select("select * from plate_table")
    List<Plate> findPlateInfo();

    @Select("select * from plate_detail")
    List<PlateDetail> findPlateDetailInfo();

    @Select("select * from dynamics where room_status=1 and plate_id=#{plateid}")
    List<DynamicsForm> findRoomInfo(@Param("plateid") Integer plateId);

    @Select("select * from room_detail where room_num=#{roomnum}")
    RoomDetail findRoomUrlByRoomNum(@Param("roomnum") String roomNum);
}
