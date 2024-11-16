package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BankDetailDTO {

    private Long id; // Inherited primary key from BankDetail base entity
    private Long renterId;

    @NotNull(message = "Bank name is required")
    @Size(min = 2, max = 20, message = "Bank name must be between 2 and 20 characters")
    private String bankName;

    @NotNull(message = "Account type is required")
    @Size(min = 2, max = 20, message = "Account type must be between 2 and 20 characters")
    private String accountType;

    @NotNull(message = "Account number is required")
    private Long accountNumber;

    @NotNull(message = "Routing number is required")
    private Long routingNumber;

    public BankDetailDTO(Long id, Long renterId, String bankName, String accountType, Long accountNumber, Long routingNumber) {
        this.id = id;
        this.renterId = renterId;
        this.bankName = bankName;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRenterId() {
        return renterId;
    }

    public void setRenterId(Long renterId) {
        this.renterId = renterId;
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
