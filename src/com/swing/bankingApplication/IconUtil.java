package com.swing.bankingApplication;

import javax.swing.*;
import java.net.URL;

public class IconUtil {
    public static ImageIcon loadIcon(String name) {
        URL url = IconUtil.class.getResource("/icons/" + name);
        if (url == null) {
            System.err.println("Icon not found: /icons/" + name);
            return null;
        }
        return new ImageIcon(url);
    }
}
