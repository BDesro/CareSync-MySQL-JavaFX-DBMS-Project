package Model;

import java.time.LocalDate;

public class Patient
{
    private int patientID;
    private String firstName;
    private char middleInitial;
    private String lastName;
    private LocalDate dateOfBirth;
    private char gender;
    private String phoneNumber;
    private String email;
    private int contactID;

    private String formattedName;

    public Patient(int patientID, String firstName, char middleInitial, String lastName, LocalDate dob,
                   char gender, String phoneNumber, String email, int contactID)
    {
        this.patientID = patientID;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.dateOfBirth = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.contactID = contactID;
    }

    public String getFormattedName()
    {
        formattedName = String.format("%s, %s %s.", lastName, firstName, middleInitial);
        return formattedName;
    }

    public int getPatientID()
    {
        return patientID;
    }

    public void setPatientID(int patientID)
    {
        this.patientID = patientID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public char getMiddleInitial()
    {
        return middleInitial;
    }

    public void setMiddleInitial(char middleInitial)
    {
        this.middleInitial = middleInitial;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public char getGender()
    {
        return gender;
    }

    public void setGender(char gender)
    {
        this.gender = gender;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getContactID()
    {
        return contactID;
    }

    public void setContactID(int contactID)
    {
        this.contactID = contactID;
    }

    @Override
    public String toString()
    {
        return firstName + " " + middleInitial + ". " + lastName;
    }
}
