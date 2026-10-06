package com.crimerecord.ui;

import com.crimerecord.dao.UserDAO;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();

    public LoginFrame() {
        setTitle("Crime Record Management System - Login");
        setSize(430, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("CRIME RECORD MANAGEMENT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        g.gridx=0; g.gridy=0; g.gridwidth=2;
        panel.add(title, g);
        g.gridwidth=1;
        g.gridx=0; g.gridy=1; panel.add(new JLabel("Username:"), g);
        g.gridx=1; panel.add(username, g);
        g.gridx=0; g.gridy=2; panel.add(new JLabel("Password:"), g);
        g.gridx=1; panel.add(password, g);

        JButton login = new JButton("Login");
        g.gridx=0; g.gridy=3; g.gridwidth=2;
        panel.add(login, g);

        login.addActionListener(e -> doLogin());
        password.addActionListener(e -> doLogin());
        add(panel);
    }

    private void doLogin() {
        try {
            if (new UserDAO().login(username.getText().trim(),
                    new String(password.getPassword()))) {
                dispose();
                new DashboardFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password.", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage() + "\n\nCheck MySQL and DBConnection.java.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
