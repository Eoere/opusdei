package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import db.DatabaseUtil;
import entities.Patient;

public class MedicalRecordsPanel extends JPanel {
    private JLabel patientIdLabel;
    private JTextField firstNameField,middlenameinitial, lastNameField, addressField, phoneNumberField, sexField, insuranceTypeField, insuranceNumberField, creditCardInfoField, emailField, patientTypeField;
    private JTextField searchField; // Assuming you have a search field
    private JComboBox<Integer> dayComboBox, monthComboBox, yearComboBox;
    private int patientId;

    public MedicalRecordsPanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace(); // Or handle more gracefully
        }
        System.err.println("Failed to initialize LaF");

        setLayout(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);

        JButton searchButton = new JButton("Search");
      
        searchButton.addActionListener(e -> searchPatient());
        searchPanel.add(new JLabel("Patient ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Medical Record fields setup
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2));
        fieldsPanel.add(new JLabel("Patient ID:"));
        patientIdLabel = new JLabel("");
        fieldsPanel.add(patientIdLabel);
        fieldsPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(20);
        fieldsPanel.add(firstNameField);
        fieldsPanel.add(new JLabel("Middle Name Initial:"));
        middlenameinitial = new JTextField(20);
        fieldsPanel.add(middlenameinitial);
        fieldsPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        fieldsPanel.add(lastNameField);
        fieldsPanel.add(new JLabel("Address:"));
        addressField = new JTextField(20);
        fieldsPanel.add(addressField);
        fieldsPanel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField(20);
        fieldsPanel.add(phoneNumberField);
        fieldsPanel.add(new JLabel("sexfield"));
        fieldsPanel.add(sexField = new JTextField(20));
        fieldsPanel.add(new JLabel("Insurance Type:"));
        insuranceTypeField = new JTextField(20);
        fieldsPanel.add(insuranceTypeField);
        fieldsPanel.add(new JLabel("Insurance Number:"));
        insuranceNumberField = new JTextField(20);
        fieldsPanel.add(insuranceNumberField);
        fieldsPanel.add(new JLabel("Credit Card Info:"));
        creditCardInfoField = new JTextField(20);
        fieldsPanel.add(creditCardInfoField);
        fieldsPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        fieldsPanel.add(emailField);
        fieldsPanel.add(new JLabel("Patient Type:"));
        patientTypeField = new JTextField(20);
        fieldsPanel.add(patientTypeField);




        // ... [Add other fields in a similar fashion]

        // Date of Birth Picker setup
        dayComboBox = new JComboBox<>();
        for (int day = 1; day <= 31; day++) {
            dayComboBox.addItem(day);
        }

        monthComboBox = new JComboBox<>();
        for (int month = 1; month <= 12; month++) {
            monthComboBox.addItem(month);
        }

        Calendar cal = Calendar.getInstance();
        yearComboBox = new JComboBox<>();
        int currentYear = cal.get(Calendar.YEAR);
        for (int year = currentYear; year >= 1900; year--) {
            yearComboBox.addItem(year);
        }

        fieldsPanel.add(new JLabel("Date of Birth:"));
        JPanel dobPanel = new JPanel(new FlowLayout());
        dobPanel.add(dayComboBox);
        dobPanel.add(monthComboBox);
        dobPanel.add(yearComboBox);
        fieldsPanel.add(dobPanel);

        add(fieldsPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveRecord());
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private  void searchPatient() {
        String patientId = searchField.getText();
        try {
            int id = Integer.parseInt(patientId);
            Patient patient = getPatientById(id);
            if (patient != null) {
                patientIdLabel.setText(String.valueOf(patient.getId()));
                firstNameField.setText(patient.getFirstName());
                middlenameinitial.setText(patient.getMiddleNameInitial());
                lastNameField.setText(patient.getLastName());
                addressField.setText(patient.getAddress());
                phoneNumberField.setText(patient.getPhoneNumber());
                sexField.setText(patient.getSex());
                insuranceTypeField.setText(patient.getInsuranceType());
                insuranceNumberField.setText(patient.getInsuranceNumber());
                creditCardInfoField.setText(patient.getCreditCardInfo());
                emailField.setText(patient.getEmail());
                patientTypeField.setText(patient.getPatientType());
                // Set other fields from the patient object
            } else {
                JOptionPane.showMessageDialog(this, "No patient with the specified ID found.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid format for Patient ID.");
        }


        
    }

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
                patient.setMiddleNameInitial(rs.getString("middleNameInitial"));
                patient.setLastName(rs.getString("lastName"));
                patient.setAddress(rs.getString("address"));
                patient.setPhoneNumber(rs.getString("phoneNumber"));
                patient.setSex(rs.getString("sex"));
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

        private void saveRecord() {
            // Retrieve values from the text fields
            String patientId = patientIdLabel.getText(); // Used only for identifying the record
            String firstName = firstNameField.getText();
            String middleNameInitial = middlenameinitial.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String phoneNumber = phoneNumberField.getText();
            String sex = sexField.getText();
            String insuranceType = insuranceTypeField.getText();
            String insuranceNumber = insuranceNumberField.getText();
            String creditCardInfo = creditCardInfoField.getText();
            String email = emailField.getText();
            String patientType = patientTypeField.getText();
        
            // Convert the selected date from ComboBoxes to LocalDate
            LocalDate dob = LocalDate.of((int) yearComboBox.getSelectedItem(), (int) monthComboBox.getSelectedItem(), (int) dayComboBox.getSelectedItem());
        
            // SQL query to update patient information
            String sql = "UPDATE Patient SET firstName = ?, middleNameInitial = ?, lastName = ?, address = ?, phoneNumber = ?, sex = ?, insuranceType = ?, insuranceNumber = ?, creditCardInfo = ?, email = ?, patientType = ?, dob = ? WHERE id = ?";
        
            try (Connection conn = DatabaseUtil.getConnection(); // Assuming DatabaseUtil.getConnection() provides a valid connection
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
                // Set parameters for the PreparedStatement
                pstmt.setString(1, firstName);
                pstmt.setString(2, middleNameInitial);
                pstmt.setString(3, lastName);
                pstmt.setString(4, address);
                pstmt.setString(5, phoneNumber);
                pstmt.setString(6, sex);
                pstmt.setString(7, insuranceType);
                pstmt.setString(8, insuranceNumber);
                pstmt.setString(9, creditCardInfo);
                pstmt.setString(10, email);
                pstmt.setString(11, patientType);
                pstmt.setDate(12, java.sql.Date.valueOf(dob)); // Convert LocalDate to SQL Date
                pstmt.setInt(13, Integer.parseInt(patientId)); // Use patient ID as a reference
        
                // Execute the update
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Record updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed. No record with the specified ID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid format for Patient ID.");
            }
        }
        
        

    // ... [Rest of the class]
}
