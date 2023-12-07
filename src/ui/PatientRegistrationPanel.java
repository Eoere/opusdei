package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import db.DatabaseUtil;

public class PatientRegistrationPanel extends JPanel {
    // Form fields
    private JTextField firstNameText, lastNameText, addressText, sexText, insurancetypeText, patientIdText, guardianNameText, guardianPhoneText, seenDateTimeText, creditCardInfoText, phoneNumberText, emailText, patientTypeText, dobText, middleInitialText, insuranceNumberText;

    public PatientRegistrationPanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace(); // Or handle more gracefully
        }
        System.err.println("Failed to initialize LaF");

        setLayout(null);

        createLabelAndTextField("PatientID:", 10, 0, 80, 25, 100, 0, 165, 25);
        createLabelAndTextField("First Name:", 10, 20, 80, 25, 100, 20, 165, 25);
        createLabelAndTextField("Middle Initial:", 10, 50, 80, 25, 100, 50, 165, 25);
        createLabelAndTextField("Last Name:", 10, 80, 80, 25, 100, 80, 165, 25);
        createLabelAndTextField("Address:", 10, 110, 80, 25, 100, 110, 165, 25);
        createLabelAndTextField("Sex:", 10, 140, 80, 25, 100, 140, 165, 25);


        createLabelAndTextField("DOB (YYYY-MM-DD):", 10, 170, 150, 25, 160, 170, 165, 25);
        createLabelAndTextField("Phone Number:", 10, 200, 100, 25, 110, 200, 165, 25);
        createLabelAndTextField("Insurance Type:", 10, 230, 100, 25, 110, 230, 165, 25);
        createLabelAndTextField("Insurance Number:", 10, 260, 120, 25, 130, 260, 165, 25);
        createLabelAndTextField("Credit Card Info:", 10, 290, 110, 25, 120, 290, 165, 25);
        createLabelAndTextField("Email:", 10, 320, 80, 25, 90, 320, 165, 25);
        createLabelAndTextField("Patient Type:", 10, 350, 80, 25, 90, 350, 165, 25);
        createLabelAndTextField("Guardian Name:", 10, 380, 100, 25, 110, 380, 165, 25);
        createLabelAndTextField("Guardian Phone:", 10, 410, 100, 25, 110, 410, 165, 25);
        createLabelAndTextField("Seen Date Time (YYYY-MM-DD HH:MM):", 10, 440, 220, 25, 230, 440, 165, 25);



        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 480, 100, 25);
        add(registerButton);

        // Action Listener for Register Button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerPatient();
            }
        });
    }

    private void createLabelAndTextField(String labelText, int labelX, int labelY, int labelWidth, int labelHeight,
                                         int textX, int textY, int textWidth, int textHeight) {
        JLabel label = new JLabel(labelText);
        label.setBounds(labelX, labelY, labelWidth, labelHeight);
        add(label);

        JTextField textField = new JTextField(20);
        textField.setBounds(textX, textY, textWidth, textHeight);
        add(textField);

        switch (labelText) {
            case"PatientID:":
            	
            	patientIdText = textField;
                break;
            case "First Name:":
                firstNameText = textField;
                break;
            case "Middle Initial:":
                middleInitialText = textField;

                break;
            case "Last Name:":
                lastNameText = textField;
                break;
            case "Address:":
                addressText = textField;
                break;
            case"Sex:":
                sexText=textField;
                break;
            case "DOB (YYYY-MM-DD):":
                dobText = textField;
                break;
            case "Phone Number:":
                phoneNumberText = textField;
                break;
            case "Insurance Type:":
                insurancetypeText = textField;
                break;
            case "Insurance Number:":
                insuranceNumberText = textField;
                break;
            case "Credit Card Info:":
                creditCardInfoText = textField;
                break;
            case "Email:":
                emailText = textField;
                break;
            case "Patient Type:":

                patientTypeText = textField;
                break;
            case "Guardian Name:":
                guardianNameText = textField;
                break;
            case "Guardian Phone:":
                guardianPhoneText = textField;
                break;
            case "Seen Date Time (YYYY-MM-DD HH:MM):":
                seenDateTimeText = textField;
                break;


                

           
                

        }
    }

    private void registerPatient() {
        // Get data from text fields
        int patientid= Integer.parseInt(patientIdText.getText());
        String firstName = firstNameText.getText();
        String middleinitial= middleInitialText.getText();
        String lastName = lastNameText.getText();
        String address = addressText.getText();
        String sex=sexText.getText();
        
        Date DOB= Date.valueOf(dobText.getText());
        int  phoneNumber= Integer.parseInt(phoneNumberText.getText());
        String insurancetype= insurancetypeText.getText();

        int insuranceNumber= Integer.parseInt(insuranceNumberText.getText());
        int creditCardInfo= Integer.parseInt(creditCardInfoText.getText());
        String email= emailText.getText();
        String patientType= patientTypeText.getText();
        String guardianName= guardianNameText.getText();
        int guardianPhone= Integer.parseInt(guardianPhoneText.getText());
        String seenDateTime= seenDateTimeText.getText();


    


        // Validate data (e.g., non-empty, correct format)
        try {
            int patientid1 = Integer.parseInt(patientIdText.getText());
            if (patientExists(patientid1)) {
                JOptionPane.showMessageDialog(this, "Patient already exists!");
                return;
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid format for Patient ID.");
            return;
        }

        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() ||  insurancetype.isEmpty() || patientType.isEmpty() || email.isEmpty() || guardianName.isEmpty()  || seenDateTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields!");
            return;
        }

        // Additional Validations
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email!");
            return;
        }

        if (!patientType.equals("adult") && !patientType.equals("adolescent")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid patient type!");
            return;
        }
  insertPatientIntoDatabase(patientid,firstName,middleinitial,lastName,address,sex,DOB,phoneNumber,insurancetype,insuranceNumber,creditCardInfo,email,patientType,guardianName,guardianPhone,seenDateTime);

        }

        // Insert into database
       

    //insert patient into database method logic
    private void insertPatientIntoDatabase(int patientid, String firstName, String middleinitial, String lastName,
            String address, String sex, Date dOB, int phoneNumber, String insurancetype, int  insuranceNumber,
            int creditCardInfo, String email, String patientType, String guardianName, int guardianPhone,
            String seenDateTime) {
        String sql = "INSERT INTO Patient VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection conn= DatabaseUtil.getConnection();
        PreparedStatement pstmt=conn.prepareStatement(sql)){
            //set parameters
            pstmt.setInt(1, patientid);
            pstmt.setString(2, firstName);
            pstmt.setString(3, middleinitial);
            pstmt.setString(4, lastName);
            pstmt.setString(5, address);
            // char value of sex
          

         // Ensure the string is only one character long
         pstmt.setString(6,sex);
        
            pstmt.setDate(7, dOB);
            pstmt.setInt(8, phoneNumber);
            pstmt.setString(9, insurancetype);
            pstmt.setInt(10, insuranceNumber);
            pstmt.setInt(11, creditCardInfo);
            pstmt.setString(12, email);
            pstmt.setString(13, patientType);

            pstmt.setString(14, guardianName);
            pstmt.setInt(15, guardianPhone);
            pstmt.setString(16, seenDateTime);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient registered successfully!");
        }catch(SQLException e){
            e.printStackTrace();



        }

    }

    //patient already exists method logic
    private boolean patientExists(int patientId) {
        String sql = "SELECT * FROM Patient WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider more user-friendly feedback
        }
        return false;
    }




}


