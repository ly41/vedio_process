package com.heu.wsvideo.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class VideoInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String ip;
}
