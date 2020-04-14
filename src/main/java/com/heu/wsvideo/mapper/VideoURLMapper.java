package com.heu.wsvideo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.heu.wsvideo.entity.VideoInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoURLMapper extends BaseMapper<VideoInfo> {

    // FIXME
    @Select("SELECT * FROM video")
    List<VideoInfo> getAllVideo();
}
