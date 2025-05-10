package Model;

import java.time.LocalDate;

public class Invoice
{
    private int invoiceID;
    private int recordID;
    private double totalAmount;
    private LocalDate issueDate;
    private boolean paid;

    public Invoice(int invoiceID, int recordID, double totalAmount, LocalDate issueDate, boolean paid)
    {
        this.invoiceID = invoiceID;
        this.recordID = recordID;
        this.totalAmount = totalAmount;
        this.issueDate = issueDate;
        this.paid = paid;
    }

    public int getInvoiceID()
    {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID)
    {
        this.invoiceID = invoiceID;
    }

    public int getRecordID()
    {
        return recordID;
    }

    public void setRecordID(int recordID)
    {
        this.recordID = recordID;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public LocalDate getIssueDate()
    {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate)
    {
        this.issueDate = issueDate;
    }

    public boolean isPaid()
    {
        return paid;
    }

    public void setPaid(boolean paid)
    {
        this.paid = paid;
    }
}
