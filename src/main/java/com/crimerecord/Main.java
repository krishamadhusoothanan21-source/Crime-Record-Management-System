package com.crimerecord;

import javax.swing.SwingUtilities;
import com.crimerecord.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
