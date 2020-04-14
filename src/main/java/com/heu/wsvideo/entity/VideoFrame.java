package com.heu.wsvideo.entity;

import lombok.Data;

@Data
public class VideoFrame {
    private Long videoId;

    // FIXME 修改视频帧数据类型
    private Object videoData;
}
