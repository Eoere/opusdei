package ui;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DatabaseUtil;










public class MakeAppointmentPanel extends JPanel {






    private JTextField appointmentIdField, patientIdField, staffIdField, appointmentDateTimeField;
    private JButton createButton;

    public MakeAppointmentPanel() {
        setLayout(new FlowLayout());

        // Initialize fields
        appointmentIdField = new JTextField(20);
        patientIdField = new JTextField(20);
        staffIdField = new JTextField(20);
        appointmentDateTimeField = new JTextField(20);

        // Initialize button
        createButton = new JButton("Create Appointment");

        // Add components
        add(new JLabel("Appointment ID:"));
        add(appointmentIdField);
        add(new JLabel("Patient ID:"));
        add(patientIdField);
        add(new JLabel("Staff ID:"));
        add(staffIdField);
        add(new JLabel("Appointment Date & Time (yyyy-MM-dd HH:mm):"));
        add(appointmentDateTimeField);
        add(createButton);

        // Add action listener to create button
        createButton.addActionListener(e -> makeAppointment());
    }

    private void makeAppointment() {
        int appointmentId = Integer.parseInt(appointmentIdField.getText());
        int patientId = Integer.parseInt(patientIdField.getText());
        int staffId = Integer.parseInt(staffIdField.getText());
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    
        if (appointmentExists(appointmentId)) {
            JOptionPane.showMessageDialog(this, "Appointment ID already exists.");
            return;
        }
    
        if (!patientExists(patientId) || !staffExists(staffId)) {
            JOptionPane.showMessageDialog(this, "Invalid patient or staff ID.");
            return;
        }
    
        if (dateTime.isBefore(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(this, "Appointment time must be in the future.");
            return;
        }
    
        if (insertAppointment(appointmentId, patientId, staffId, dateTime)) {
            JOptionPane.showMessageDialog(this, "Appointment successfully created.");
            // Optionally refresh the appointment list
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create appointment.");
        }
    }
    private boolean appointmentExists(int appointmentId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM appointment WHERE id = ?");
            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean patientExists(int patientId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM patient WHERE id = ?");
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean staffExists(int staffId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE id = ?");
            stmt.setInt(1, staffId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean insertAppointment(int id, int patientId, int staffId, LocalDateTime dateTime) {
        String sql = "INSERT INTO Appointment (id, patientid, staffid, appointmenttime) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                Timestamp timestamp = Timestamp.valueOf(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            pstmt.setInt(1, id);
            pstmt.setInt(2, patientId);
            pstmt.setInt(3, staffId);
            pstmt.setTimestamp(4, Timestamp.valueOf(dateTime)); // Convert LocalDateTime to Timestamp
    
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if the row is successfully inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there's an SQL exception
        }
    }
}