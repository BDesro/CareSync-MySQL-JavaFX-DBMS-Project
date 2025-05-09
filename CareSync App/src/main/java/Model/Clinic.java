package Model;

public class Clinic
{
    private int clinicID;
    private String clinicName;
    private String clinicPhone;
    private String clinicAddress;

    public Clinic(int clinicID, String clinicName, String clinicPhone, String clinicAddress)
    {
        this.clinicID = clinicID;
        this.clinicName = clinicName;
        this.clinicPhone = clinicPhone;
        this.clinicAddress = clinicAddress;
    }

    public int getClinicID()
    {
        return clinicID;
    }

    public void setClinicID(int clinicID)
    {
        this.clinicID = clinicID;
    }

    public String getClinicName()
    {
        return clinicName;
    }

    public void setClinicName(String clinicName)
    {
        this.clinicName = clinicName;
    }

    public String getClinicPhone()
    {
        return clinicPhone;
    }

    public void setClinicPhone(String clinicPhone)
    {
        this.clinicPhone = clinicPhone;
    }

    public String getClinicAddress()
    {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress)
    {
        this.clinicAddress = clinicAddress;
    }

}
