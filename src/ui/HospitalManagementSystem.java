package ui;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import db.DatabaseTest;


public class HospitalManagementSystem {
    public static void main(String[] args) {
        // Initial frame for login
          DatabaseTest.testConnection();
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.add(new LoginPanel());
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    public static void setupMainFrame(JFrame frame, UserRole role) {
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs based on the user role
        switch (role) {

            case DOCTOR:
                // Add tabs for patient management
                
                tabbedPane.addTab("Appointments", new AppointmentManagementPanel());
                tabbedPane.addTab("Records", new  MedicalRecordsPanel());
                //add new tab for logout that returns to login screen
                tabbedPane.addTab("Logout", new logoutpanel());

                break;

            case NURSE:
                // Add medical-related tabs
                tabbedPane.addTab("Patients", new PatientRegistrationPanel());
                tabbedPane.addTab("Records", new  MedicalRecordsPanel());

                tabbedPane.addTab("Logout", new logoutpanel());

                break;
            case RECEPTIONIST:
                // Add scheduling and registration tabs
                tabbedPane.addTab("Appointments", new AppointmentManagementPanel());
                tabbedPane.addTab(MakeAppointmentPanel.class.getSimpleName(), new MakeAppointmentPanel());
                tabbedPane.addTab("Register Patient", new PatientRegistrationPanel());
                tabbedPane.addTab("Logout", new logoutpanel());
                break;
            case ADMINISTRATOR:
            

                // Add tabs for administration
                // ...
                break;
            // Other cases as needed
        }

        frame.add(tabbedPane, BorderLayout.CENTER);
    }
}




