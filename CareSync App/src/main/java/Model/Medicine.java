package Model;

public class Medicine
{
    private int medicineID;
    private String medicineName;
    private int quantity;

    public Medicine(int medicineID, String medicineName)
    {
        this.setMedicineID(medicineID);
        this.setMedicineName(medicineName);
        this.setQuantity(0);
    }

    public int getMedicineID()
    {
        return medicineID;
    }

    public void setMedicineID(int medicineID)
    {
        this.medicineID = medicineID;
    }

    public String getMedicineName()
    {
        return medicineName;
    }

    public void setMedicineName(String medicineName)
    {
        this.medicineName = medicineName;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public String toString()
    {
        return medicineName;
    }
}
