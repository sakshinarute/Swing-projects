package com.swing.bankingApplication.test;


import org.testng.annotations.Test;

import com.swing.bankingApplication.IconUtil;

import javax.swing.*;

import static org.testng.Assert.*;

public class IconUtilTest {

    @Test
    public void testLoadExistingIcon() {
        ImageIcon icon = IconUtil.loadIcon("success.jpg"); // Make sure this file exists in /icons
        assertNotNull(icon, "Icon should not be null when it exists");
    }

    @Test
    public void testLoadMissingIcon() {
        ImageIcon icon = IconUtil.loadIcon("missing.jpg");
        assertNull(icon, "Icon should be null when it doesn't exist");
    }
}
