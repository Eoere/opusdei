package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;



public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    public LoginPanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace(); // Or handle more gracefully
        }
        System.err.println("Failed to initialize LaF");

        setLayout(new GridLayout(3, 2));



    add(new JLabel("Username:"));
    usernameField = new JTextField();
    add(usernameField);

    add(new JLabel("Password:"));
    passwordField = new JPasswordField();
    add(passwordField);

    loginButton = new JButton("Login");
    loginButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            authenticateUser(usernameField.getText(), new String(passwordField.getPassword()));
        }
    });
    add(loginButton);
} // ... (same as the previous LoginPanel implementation)

    private void authenticateUser(String username, String password) {
        
        UserRole userRole = getUserRole(username, password); // Implement this
        if (userRole != null) {
            openMainSystem(userRole);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }

    private UserRole getUserRole(String username, String password) {
        if (username.equals("doctor") && password.equals("doctor1")) {
            return UserRole.DOCTOR;
        } else if (username.equals("nurse") && password.equals("nurse1")) {
            return UserRole.NURSE;
        } else if (username.equals("admin") && password.equals("admin1")) {
            return UserRole.ADMINISTRATOR;
        } else if (username.equals("receptionist") && password.equals("receptionist1")) {
            return UserRole.RECEPTIONIST;
        } else {
            return null; // Invalid credentials
        }
    }

    private void openMainSystem(UserRole role) {
        JFrame mainFrame = new JFrame("OpusDei");
        HospitalManagementSystem.setupMainFrame(mainFrame, role);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}



