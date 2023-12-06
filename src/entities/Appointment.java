package entities;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private LocalDateTime appointmentTime;
    private Patient patient; // Assuming each appointment is linked to one patient
    private Staff staff; // Assuming each appointment is handled by one staff member
    public void setId(int id) {
        this.id = id;
    }
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    // Additional fields as per your ERD
    public int getId() {
        return id;
    }
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }
    public Patient getPatient() {
        return patient;
    }
    public Staff getStaff() {
        return staff;
    }

    // Getters and setters
    
}

