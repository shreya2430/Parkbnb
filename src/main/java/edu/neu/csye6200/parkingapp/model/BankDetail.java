package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bankdetails")
public class BankDetail extends BaseEntity {

    // Foreign Key relationship with Renter
    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;

    @Column(name = "bank_name", nullable = false, length = 20)
    private String bankName;

    @Column(name = "account_type", nullable = false, length = 50)
    private String accountType;

    @Column(name = "account_number", nullable = false, unique = true, length = 50)
    private Long accountNumber;

    @Column(name = "routing_number", nullable = false, unique = true, length = 50)
    private Long routingNumber;

    // Getters and Setters
    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(Long routingNumber) {
        this.routingNumber = routingNumber;
    }
}

