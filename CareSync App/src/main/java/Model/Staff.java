package Model;

public class Staff
{
    private int staffID;
    private String firstName;
    private String lastName;
    private String staffRole;
    private int clinicID;
    private String email;
    private String hashedPassword;

    public Staff(int staffID, String fName, String lName, String role, int clinicID, String email, String hashedPassword)
    {
        this.staffID = staffID;
        this.firstName = fName;
        this.lastName = lName;
        this.staffRole = role;
        this.clinicID = clinicID;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public int getStaffID()
    {
        return staffID;
    }

    public void setStaffID(int staffID)
    {
        this.staffID = staffID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getStaffRole()
    {
        return staffRole;
    }

    public void setStaffRole(String staffRole)
    {
        this.staffRole = staffRole;
    }

    public int getClinicID()
    {
        return clinicID;
    }

    public void setClinicID(int clinicID)
    {
        this.clinicID = clinicID;
    }

    public String getEmail() { return email; }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getHashedPassword() { return hashedPassword; }

    public void setHashedPassword(String hashedPassword)
    {
        this.hashedPassword = hashedPassword;
    }
}
