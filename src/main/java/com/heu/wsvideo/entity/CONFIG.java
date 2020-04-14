package com.heu.wsvideo.entity;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

public class CONFIG {
    //public static String filename = "D:\\Data\\摄像头视频\\松花江街-公司街7占道经营.dav";
    public static String modelPath = getWebRootAbsolutePath()+"model\\";//改成直接获取本地视频就行

    public static Scalar Colors[] = { new Scalar(255, 0, 0), new Scalar(0, 255, 0),
            new Scalar(0, 0, 255), new Scalar(255, 255, 0),
            new Scalar(0, 255, 255), new Scalar(255, 0, 255),
            new Scalar(255, 127, 255), new Scalar(127, 0, 255),
            new Scalar(127, 0, 127), new Scalar(50,205,50) };

    public static double learningRate = 0.005;

    public static String getWebRootAbsolutePath() {
        String path = null;
        String folderPath = System.getProperty("webapp.root");
        if (folderPath.indexOf("WEB-INF") > 0) {
            path = folderPath.substring(0, folderPath.indexOf("WEB-INF/classes"));
        }else{
            path = folderPath;
        }
        return path;
    }
}
