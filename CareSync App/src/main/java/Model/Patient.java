package Model;

public class Patient
{
    private int patientID;
    private String firstName;
    private char middleInitial;
    private String lastName;
    private char gender;
    private String phoneNumber;
    private String email;
    private int contactID;

    public Patient(int patientID, String firstName, char middleInitial, String lastName, char gender,
                   String phoneNumber, String email, int contactID)
    {
        this.patientID = patientID;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.contactID = contactID;
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
}
