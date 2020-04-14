package com.heu.wsvideo.controller;


//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
//import com.xinjian.x.common.ocr.OCRUtil;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OrcTest {

    static {
        //必须要写
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("opencv\t"+Core.VERSION);
    }

    public static void main(String[] args) {
        new OrcTest().test();
    }

    public static void test(){
        Mat src= Imgcodecs.imread("D:/Pictures/160744.28767674_1280.jpg");
        HighGui gui=new HighGui();
        gui.imshow("哈妮",src);
        gui.waitKey(1000);
    }

}
/*public class OrcTest {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //注意程序运行的时候需要在VM option添加该行 指明opencv的dll文件所在路径
        //-Djava.library.path=$PROJECT_DIR$\opencv\x64
    }
    public static void main(String[] args){
        Mat mat = Imgcodecs.imread("D:/Pictures/160744.28767674_1280.jpg");
        System.out.println(mat);
    }
}*/
