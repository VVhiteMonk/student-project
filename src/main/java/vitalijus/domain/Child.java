package vitalijus.domain;

import java.time.LocalDate;

public class Child extends Person{
    private String certificateNumber;
    private LocalDate issueDate;
    private RegisterOffice issuesDepartment;

    public Child(){}

    public Child(String giveName, String surname, LocalDate dateOfBirth) {
        super(giveName, surname, dateOfBirth);
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public RegisterOffice getIssuesDepartment() {
        return issuesDepartment;
    }

    public void setIssuesDepartment(RegisterOffice issuesDepartment) {
        this.issuesDepartment = issuesDepartment;
    }

    @Override
    public String toString() {
        return "Child{" +
                "certificateNumber='" + certificateNumber + '\'' +
                ", issueDate=" + issueDate +
                ", issuesDepartment=" + issuesDepartment +
                "} " + super.toString();
    }
}
