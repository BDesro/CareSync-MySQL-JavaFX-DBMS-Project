package Model;

public class Treatment
{
    private int treatmentID;
    private String treatmentName;

    public Treatment(int treatmentID, String treatmentName)
    {
        this.setTreatmentID(treatmentID);
        this.setTreatmentName(treatmentName);
    }

    public int getTreatmentID()
    {
        return treatmentID;
    }

    public void setTreatmentID(int treatmentID)
    {
        this.treatmentID = treatmentID;
    }

    public String getTreatmentName()
    {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName)
    {
        this.treatmentName = treatmentName;
    }

    @Override
    public String toString()
    {
        return treatmentName;
    }
}
