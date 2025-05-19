package com.shapeville.utils;

import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageLoader {
    public static ImageIcon loadImageAndScale(String imagePath, int width, int height) {
        
        try {
        System.out.println("尝试加载图片: " + imagePath);
        // 从资源文件中加载图片
        InputStream is = ImageLoader.class.getResourceAsStream(imagePath);
        if (is == null) {
            System.err.println("无法找到图片: " + imagePath);
            // 尝试使用不同的加载方式
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(imagePath.substring(1));
            if (is == null) {
                System.err.println("使用ContextClassLoader也无法找到图片");
                return null;
            } else {
                System.out.println("使用ContextClassLoader成功加载图片");
            }
        }
            
            // 读取图片
            Image image = ImageIO.read(is);
            if (image == null) {
                System.err.println("无法加载图片: " + imagePath);
                return null;
            }
            
            // 缩放图片
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
            
        } catch (Exception e) {
            System.err.println("加载图片时出错: " + e.getMessage());
            return null;
        }
    }
}