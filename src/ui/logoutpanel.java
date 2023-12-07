package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


public class logoutpanel extends JPanel {
    //design logout panel
    //add color


    //create logout panel
    public logoutpanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace(); // Or handle more gracefully
        }
        System.err.println("Failed to initialize LaF");
        UIManager.put("Button.arc", 8); // Rounded buttons with an arc of 8

        setLayout(null);
    //create logout button
    JButton logoutButton = new JButton("Logout");
    logoutButton.setBackground(java.awt.Color.RED);
   logoutButton.setBounds(10, 480, 100, 25);
    add(logoutButton);
    // Action Listener for logout Button
    logoutButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            logout();
        }
    });}
    //logout method
    private void logout() {
        // close current frame
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        
        // open login frame
        JFrame loginFrame = new JFrame("Login");
        
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.add(new LoginPanel());
        loginFrame.pack();
        loginFrame.setVisible(true);
    }
    
}
