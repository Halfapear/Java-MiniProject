package com.shapeville.utils;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageLoader {
    public static ImageIcon loadImageIcon(String path) {
        URL imgURL = ImageLoader.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null; // Or a placeholder icon
        }
    }

    public static ImageIcon loadImageAndScale(String path, int width, int height) {
        ImageIcon originalIcon = loadImageIcon(path);
        if (originalIcon != null) {
            Image img = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return null;
    }
}