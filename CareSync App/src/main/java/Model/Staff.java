package Model;

public class Staff
{
    private int staffID;
    private String firstName;
    private String lastName;
    private String staffRole;
    private int clinicID;

    public Staff(int staffID, String fName, String lName, String role, int clinicID)
    {
        this.staffID = staffID;
        this.firstName = fName;
        this.lastName = lName;
        this.staffRole = role;
        this.clinicID = clinicID;
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
}
