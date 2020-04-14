package com.heu.wsvideo.videoprocessor;

import com.heu.wsvideo.entity.FormatConvertTools;
import com.heu.wsvideo.entity.VideoInfo;
import com.heu.wsvideo.mapper.VideoURLMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import com.heu.wsvideo.entity.myWindow;

@Slf4j
public class VideoProcessor {

    // 每个视频缓存最大帧数量
    private static final int VIDEO_CACHE_MAX_SIZE = 10;

    public boolean predict = true;
    //==============================================================
    static Mat imag = new Mat();

    @Getter
    private static VideoProcessor instance = new VideoProcessor();

    @Autowired
    private VideoURLMapper videoURLMapper;

    // 线程运行标志
    private HashMap<Long, Boolean> runnings;

    // 处理后的视频帧缓冲
    @Getter
    private HashMap<Long, LinkedBlockingQueue<Object>> videoFrames;

    // TODO: 修改参数类型与算法输出帧类型一致
    private void putVideoFrame(VideoInfo video, Object frame) {
        LinkedBlockingQueue queue = videoFrames.get(video.getId());

        // 缓存当前帧
        try {
            queue.put(frame);
        } catch (InterruptedException e) {
            // 已满，扔掉第一个后重新放入
            queue.poll();
            try {
                queue.put(frame);
            } catch (InterruptedException ee) {
            }

        }

    }

    /**
     * 启动视频处理器
     */
    public void startProcess() {

        runnings = new HashMap<>();
        videoFrames = new HashMap<>();

        // 获取所有视频URL
        List<VideoInfo> urls = videoURLMapper.getAllVideo();

        // 遍历，启用工作线程
        urls.forEach(url -> {
            runnings.put(url.getId(), true);

            // 处理视频
            try {
                processVideo(url);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 停止所有处理线程
     */
    public void stopAll() {
        runnings.values().forEach(v -> v = false);
    }
    public int countWords(String str,char ch){
        int count = 0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==ch){
                count++;
            }
        }
        return count;
    }
    @Async
    public void processVideo(VideoInfo video) throws InterruptedException {

        videoFrames.put(video.getId(), new LinkedBlockingQueue<>(VIDEO_CACHE_MAX_SIZE));

        log.info("开始处理视频 " + video.getIp());

        while (runnings.get(video.getId())) {
            // TODO: 添加视频处理算法

            long timer = 0;
            predict = PredictFunction.predictResult(imag);
            long timeStart = System.currentTimeMillis();
            //使用Vector来保存识别输出的表达式,保证线程安全
            Vector<String> boothExpress = new Vector<String>();
            while(!imag.empty()){
                System.out.println("正式开始");
                predict = PredictFunction.predictResult(imag);
                if(predict==false){
                    boothExpress.add("0");
                    timer = System.currentTimeMillis()-timeStart;
                    System.out.println("非法摊位占时"+timer);
                    if(timer>90000){
                        System.out.println("产生报警");
                        //产生报警后，休眠一分钟，timeStart重计时，再次识别计算
                        Thread.sleep(60000);
                        //重新检测，清空集合中的识别状态字符串
                        boothExpress.clear();
                        timeStart = System.currentTimeMillis();
                    }
                }
                if(predict==true){
                    boothExpress.add("1");
                    timer = 0;
                    timeStart = System.currentTimeMillis();
                    System.out.println("距离第一次识别耗时"+timer);
                }
                String str = boothExpress.toString();
                System.out.println(str);
                //容错方式a：当连续出现3次1时，判断为非法摊不在了,沉睡30s后重新检测
                boolean faultTol = str.matches("\\[.*1,1,1.*\\]");
                if(faultTol==true){
                    System.out.println("容错A触发，当前区域正常");
                    Thread.sleep(30000);
                    timer = 0;
                    timeStart = System.currentTimeMillis();
                }
                //容错方式b：	1比0多 && 1达到某个设定阈值，判断为区域正常，沉睡30s后重新检测
               if(countWords(str,'1')>countWords(str,'0')&&countWords(str,'1')>6){
                    System.out.println("容错B触发，当前区域正常");
                    Thread.sleep(30000);
                    timer = 0;
                    timeStart = System.currentTimeMillis();
                }
                myWindow mw = new myWindow(1080, 720, "CurrentFrame");
                System.out.println("继续运行");
                ImageIcon image= new ImageIcon(FormatConvertTools.Mat2bufferedImage(imag));
                mw.getVidpanel().setIcon(image);
                mw.getVidpanel().repaint();
            }
            System.out.println("视频结束");

            Object frame = null;

            // 缓存当前帧
            putVideoFrame(video, frame);
        }

        log.info("视频 " + video.getIp() + " 处理完毕。");
    }
}
