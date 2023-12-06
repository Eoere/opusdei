package entities;
import java.time.LocalDate;


public class Patient {
    private int id;
    private String firstName;
    private String middleNameInitial;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String sex;
    private String insuranceType;
    private String insuranceNumber;
    private String creditCardInfo;
    private String email;
    private String patientType;
    private LocalDate dob; // Date of birth

    // Constructor
    public Patient() {
        this.id = id;
        this.firstName = firstName;
        this.middleNameInitial = middleNameInitial;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.insuranceType = insuranceType;
        this.insuranceNumber = insuranceNumber;
        this.creditCardInfo = creditCardInfo;
        this.email = email;
        this.patientType = patientType;
        this.dob = dob;
    }

    // Getters and Setters
    

    // ... Include remaining getters and setters for phoneNumber, sex, insuranceType, etc.

    // You can also override the toString method for easy printing of patient details
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleNameInitial='" + middleNameInitial + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sex='" + sex + '\'' +
                ", insuranceType='" + insuranceType + '\'' +
                ", insuranceNumber='" + insuranceNumber + '\'' +
                ", creditCardInfo='" + creditCardInfo + '\'' +
                ", email='" + email + '\'' +
                ", patientType='" + patientType + '\'' +
                ", dob=" + dob +
                '}';
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleNameInitial() {
		return middleNameInitial;
	}

	public void setMiddleNameInitial(String middleNameInitial) {
		this.middleNameInitial = middleNameInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getCreditCardInfo() {
		return creditCardInfo;
	}

	public void setCreditCardInfo(String creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
}

