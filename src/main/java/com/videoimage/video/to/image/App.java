package com.videoimage.video.to.image;

import javax.swing.SwingUtilities;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VideoToImage();
                
                /*
                JFrame frame = new JFrame();
                frame.setSize(new Dimension(700, 400));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                ProgressBar barra = new ProgressBar(350, 200);
                frame.add(barra);
                */
            }
        });
    }
}
