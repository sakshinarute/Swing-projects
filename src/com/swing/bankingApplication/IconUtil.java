package com.swing.bankingApplication;

import javax.swing.*;
import java.net.URL;

public class IconUtil {
    public static ImageIcon loadIcon(String fileName) {
        URL iconURL = IconUtil.class.getResource("/icons/" + fileName);
        if (iconURL != null) {
            return new ImageIcon(iconURL);
        } else {
            System.out.println("Icon not found: " + fileName);
            return null;
        }
    }
}