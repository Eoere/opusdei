package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import db.DatabaseUtil;
import entities.Appointment;
import entities.Patient;
import entities.Staff;
//import look and feel


public class AppointmentManagementPanel extends JPanel {
    private JTable appointmentsTable;
    private JButton viewDetailsButton;
    private JButton cancelAppointmentButton;
    private JButton rescheduleAppointmentButton;
    

    public AppointmentManagementPanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace(); // Or handle more gracefully
        }
        System.err.println("Failed to initialize LaF");

        setLayout(new BorderLayout());

        // Initialize and set up the appointments table
        appointmentsTable = new JTable();
        add(new JScrollPane(appointmentsTable), BorderLayout.CENTER);

        // Initialize and add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        viewDetailsButton = new JButton("View Details");
        cancelAppointmentButton = new JButton("Cancel Appointment");
        rescheduleAppointmentButton = new JButton("Reschedule");
      

        // Add buttons to the button panel
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(cancelAppointmentButton);
        buttonPanel.add(rescheduleAppointmentButton);
       

        // Add the button panel to the main panel
        add(buttonPanel, BorderLayout.SOUTH);

    
        rescheduleAppointmentButton = new JButton("Reschedule");

        // Add action listeners to the buttons
        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement view details logic
                int selectedRow = appointmentsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Please select an appointment to view details");
                    return;
                }
                int appointmentId = (int) appointmentsTable.getValueAt(selectedRow, 0);
                Appointment appointment = getAppointmentById(appointmentId); // Implement this method
                if (appointment == null) {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Appointment not found");
                    return;
                }
                // Display appointment details in a dialog
                StringBuilder sb = new StringBuilder();
                sb.append("Appointment ID: ").append(appointment.getId()).append("\n");
                sb.append("Patient Name: ").append(appointment.getPatient().getFirstName()).append(" ").append(appointment.getPatient().getLastName()).append("\n");
                sb.append("Doctor Name: ").append(appointment.getStaff().getLastName()).append("\n");
                sb.append("Appointment Time: ").append(appointment.getAppointmentTime().toString()).append("\n");
                // Append other fields
                JOptionPane.showMessageDialog(AppointmentManagementPanel.this, sb.toString());
            }

            private Appointment getAppointmentById(int appointmentId) {
                String sql = "SELECT * FROM Appointment WHERE id = ?";
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
                    pstmt.setInt(1, appointmentId);
                    ResultSet rs = pstmt.executeQuery();
            
                    if (rs.next()) {
                        Appointment appointment = new Appointment();
                        appointment.setId(rs.getInt("id"));
                        appointment.setAppointmentTime(rs.getTimestamp("appointmentTime").toLocalDateTime());
            
                        int patientId = rs.getInt("patientId");
                        Patient patient = getPatientById(patientId); // Implement this method
                        appointment.setPatient(patient);

            int doctorId = rs.getInt("staffId");
                        Staff staff = getStaffById(doctorId); // Implement this method
                        appointment.setStaff(staff);
            
                        return appointment;
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); // Or handle more gracefully
                }
                return null; // or throw an exception
            }
            
        });
    

        cancelAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement cancellation logic
                int selectedRow = appointmentsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Please select an appointment to cancel");
                    return;
                }
                int appointmentId = (int) appointmentsTable.getValueAt(selectedRow, 0);
                boolean success = cancelAppointment(appointmentId); // Implement this method
                if (success) {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Appointment cancelled successfully");
                    loadAppointments();
                } else {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Appointment cancellation failed");
                }


                // Implement cancellation logic
            }
        });

        rescheduleAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement rescheduling logic
                int selectedRow = appointmentsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Please select an appointment to reschedule");
                    return;
                }
                int appointmentId = (int) appointmentsTable.getValueAt(selectedRow, 0);
                String newTimeStr = JOptionPane.showInputDialog(AppointmentManagementPanel.this, "Enter new appointment time (yyyy-MM-dd HH:mm:ss)");
                LocalDateTime newTime = null ;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                     newTime = LocalDateTime.parse(newTimeStr, formatter);
                    boolean success = rescheduleAppointment(appointmentId, newTime); // Implement this method
                    if (success) {
                        JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Appointment rescheduled successfully");
                        loadAppointments();
                    } else {
                        JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Appointment rescheduling failed");
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Invalid date format. Please use yyyy-MM-dd HH:mm:ss");
                }
                boolean success = rescheduleAppointment(appointmentId, newTime); // Implement this method
                if (success) {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Appointment rescheduled successfully");
                    loadAppointments();
                } else {
                    JOptionPane.showMessageDialog(AppointmentManagementPanel.this, "Appointment rescheduling failed");
                }

               
            }
        });

        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(cancelAppointmentButton);
        buttonPanel.add(rescheduleAppointmentButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load appointments (dummy data for now, replace with DB call)
        loadAppointments();
    }

    protected boolean appointmentExists(int appointmentId) {
        String sql = "SELECT * FROM Appointment WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointmentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or handle more gracefully
        }
        return false;
    }

    private void loadAppointments() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Patient Name");
        model.addColumn("Doctor Name");
        model.addColumn("Appointment Time");

        List<Appointment> appointments = getAppointments();
        for (Appointment appointment : appointments) {
            Object[] row = new Object[4]; // Adjust according to the number of columns
            row[0] = appointment.getId();

            // Check if patient is not null
            if (appointment.getPatient() != null) {
                row[1] = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
            } else {
                row[1] = "Patient data not available";
            }

            // Check if staff is not null
            if (appointment.getStaff() != null) {
                row[2] = appointment.getStaff().getFirstName() + " " + appointment.getStaff().getLastName();
            } else {
                row[2] = "Doctor data not available";
            }

            row[3] = appointment.getAppointmentTime().toString();

            model.addRow(row);
        }

        appointmentsTable.setModel(model);
    }

    private List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setAppointmentTime(rs.getTimestamp("appointmentTime").toLocalDateTime());

                int patientId = rs.getInt("patientId");
                Patient patient = getPatientById(patientId); // Implement this method
                appointment.setPatient(patient);

                int doctorId = rs.getInt("staffId");
                Staff staff = getStaffById(doctorId); // Implement this method
                appointment.setStaff(staff);

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or handle more gracefully
        }
        return appointments;

       
    }

    public boolean rescheduleAppointment(int appointmentId, LocalDateTime newTime) {
        String sql = "UPDATE Appointment SET appointmentTime = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(newTime);
            pstmt.setTimestamp(1, timestamp);
            pstmt.setInt(2, appointmentId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean cancelAppointment(int appointmentId) {
        String sql = "DELETE FROM Appointment WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, appointmentId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Or handle more gracefully
            return false;
        }

    }
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE staffId = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setAppointmentTime(rs.getTimestamp("appointmentTime").toLocalDateTime());

                int patientId = rs.getInt("patientId");
                Patient patient = getPatientById(patientId); // Implement this method
                appointment.setPatient(patient);

                Staff staff = getStaffById(doctorId); // Implement this method
                appointment.setStaff(staff);

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or handle more gracefully
        }
        return appointments;
    }

    // Dummy implementations - replace with actual database fetch methods
    private Patient getPatientById(int patientId) {
        String sql = "SELECT * FROM Patient WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setFirstName(rs.getString("firstName"));
                patient.setMiddleNameInitial(rs.getString("middleNameinitial"));
                patient.setLastName(rs.getString("lastName"));
                patient.setAddress(rs.getString("address"));
                patient.setPhoneNumber(rs.getString("phoneNumber"));

                patient.setDob(rs.getDate("dob").toLocalDate());
                patient.setInsuranceType(rs.getString("insuranceType"));
                patient.setInsuranceNumber(rs.getString("insuranceNumber"));
                patient.setCreditCardInfo(rs.getString("creditCardInfo"));
                patient.setEmail(rs.getString("email"));

                patient.setPatientType(rs.getString("patientType"));


                // Set other fields from the result set
                return patient;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or handle more gracefully
        }
        return null; // or throw an exception
            
        }
        

       
    

        private Staff getStaffById(int staffId) {
            String sql = "SELECT * FROM Staff WHERE id = ?";
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
                pstmt.setInt(1, staffId);
                ResultSet rs = pstmt.executeQuery();
        
                if (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId(rs.getInt("id"));
                    staff.setFirstName(rs.getString("firstName"));
                    staff.setLastName(rs.getString("lastName"));
                    staff.setAddress(rs.getString("address"));
                    staff.setPhoneNumber(rs.getString("phoneNumber"));
        
                    // Null checks for fields that might be null
                    Date dob = rs.getDate("dob");
                    if (dob != null) {
                        staff.setDob(dob);
                    }
                    staff.setSex(rs.getString("sex") != null ? rs.getString("sex") : "");
                    staff.setSsn(rs.getString("ssn") != null ? rs.getString("ssn") : "");
                    staff.setSchedule(rs.getString("schedule") != null ? rs.getString("schedule") : "");
                   
                    staff.setStafftype(rs.getString("stafftype") != null ? rs.getString("stafftype") : "");
        
                    return staff;
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Consider more user-friendly feedback
            }
            return null; // or throw an exception
        }
        
    

}
// implement load appointments  from the database


