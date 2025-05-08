package Model;

import util.CareSyncDB;

import java.time.LocalDate;
import java.util.ArrayList;

public class Visit
{
    private int recordID;
    private int patientID;
    private int clinicID;
    private int roomID;
    private int staffID;
    private String visitReason;
    private LocalDate visitDate;
    private String patientFName;
    private char patientMInit;
    private String patientLName;
    private String clinicName;
    private String roomType;
    private String staffRole;
    private String staffFName;
    private String staffLName;
    private String symptoms;
    private String diagnosis;
    private String treatments;
    private String prescriptions;
    private boolean complete;

    public Visit(int recordID, int patientID, int clinicID, int roomID, int staffID, String reason, LocalDate date,
                 String pFName, char pMInit, String pLName, String clinicName, String roomType, String staffRole,
                 String sFName, String sLName, String symptoms, String diagnosis, String treatment, String prescription, boolean complete)
    {
        this.recordID = recordID;
        this.patientID = patientID;
        this.clinicID = clinicID;
        this.roomID = roomID;
        this.staffID = staffID;
        this.visitReason = reason;
        this.visitDate = date;
        this.patientFName = pFName;
        this.patientMInit = pMInit;
        this.patientLName = pLName;
        this.clinicName = clinicName;
        this.roomType = roomType;
        this.staffRole = staffRole;
        this.staffFName = sFName;
        this.staffLName = sLName;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.treatments = treatment;
        this.prescriptions = prescription;
        this.complete = complete;
    }

    public String getFormattedPatientName()
    {
        return String.format("%s, %s %s.", patientLName, patientMInit, patientFName);
    }

    public String getFormattedStaffName()
    {
        return String.format("%s, %s", staffLName, staffFName);
    }

    public int getRecordID()
    {
        return recordID;
    }

    public void setRecordID(int recordID)
    {
        this.recordID = recordID;
    }

    public int getPatientID() { return patientID; }

    public void setPatientID(int patientID)
    {
        this.patientID = patientID;
    }

    public int getClinicID()
    {
        return clinicID;
    }

    public void setClinicID(int clinicID) { this.clinicID = clinicID; }

    public String getClinicName()
    {
        return clinicName;
    }

    public void setClinicName(String clinicName) { this.clinicName = clinicName; }

    public int getRoomID() { return roomID; }

    public String getRoomType()
    {
        return roomType;
    }

    public void setRoomID(int roomID)
    {
        this.roomID = roomID;
    }

    public int getStaffID()
    {
        return staffID;
    }

    public void setStaffID(int staffID) { this.staffID = staffID; }

    public LocalDate getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getVisitReason()
    {
        return visitReason;
    }

    public void setVisitReason(String visitReason)
    {
        this.visitReason = visitReason;
    }

    public String getSymptoms()
    {
        return symptoms;
    }

    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getDiagnosis()
    {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }

    public String getTreatments() { return treatments; }

    public String getPrescriptions()
    {
        return prescriptions;
    }

    public boolean isComplete() { return complete; }

    public void setComplete(boolean complete)
    {
        this.complete = complete;
    }

}
