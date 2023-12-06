package entities;

import java.util.List;

public class Medication {
    private int id;
    private String name;
    private String dosage;
    private List<Patient> patients; // If a medication can be linked to multiple patients

    // Getters and setters
}
