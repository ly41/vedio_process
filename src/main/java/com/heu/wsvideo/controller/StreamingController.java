package com.heu.wsvideo.controller;

import com.heu.wsvideo.entity.VideoFrame;
import com.heu.wsvideo.videoprocessor.VideoProcessor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingQueue;

@RestController
public class StreamingController {

    @MessageMapping("/video/{videoId}")
    @SendTo("/video/{videoId}")
    public VideoFrame sendVideoFrame(@DestinationVariable Long videoId) throws Exception {

        LinkedBlockingQueue<Object> q = VideoProcessor.getInstance().getVideoFrames().get(videoId);
        if (q == null) {
            throw new Exception(videoId + "视频不存在！");
        }

        VideoFrame f = new VideoFrame();
        f.setVideoId(videoId);
        f.setVideoData(q.peek());

        return f;
    }

}
