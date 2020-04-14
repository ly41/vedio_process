package com.heu.wsvideo.entity;


import org.opencv.core.Point;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

public class myWindow {
    private JFrame jframe = null;
    JLabel vidpanel = null;
    public JFrame getJframe() {
        return jframe;
    }

    public void setJframe(JFrame jframe) {
        this.jframe = jframe;
    }

    public JLabel getVidpanel() {
        return vidpanel;
    }

    public void setVidpanel(JLabel vidpanel) {
        this.vidpanel = vidpanel;
    }

    public myWindow(int width, int height, String windowName){
        jframe = new JFrame(windowName);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setSize(width, height);
        jframe.setVisible(true);

    }

    public Queue<Point> mouseListener(){
        final Queue<Point> points = new LinkedList<Point>();
        jframe.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1){
                    int x = e.getX();
                    int y = e.getY();
                    System.out.println(x+"--"+y);
                    points.offer(new Point(x,y));
                }
            }
        });
        return points;
    }
}
