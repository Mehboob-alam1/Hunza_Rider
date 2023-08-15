package com.mehboob.hunzarider.models;

public class PaymentDetailClass {

  private   String bankName,userBankName,userAccountNumber;

private String userId;
    public PaymentDetailClass() {
    }

    public PaymentDetailClass(String bankName, String userBankName, String userAccountNumber, String userId) {
        this.bankName = bankName;
        this.userBankName = userBankName;
        this.userAccountNumber = userAccountNumber;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUserBankName() {
        return userBankName;
    }

    public void setUserBankName(String userBankName) {
        this.userBankName = userBankName;
    }

    public String getUserAccountNumber() {
        return userAccountNumber;
    }

    public void setUserAccountNumber(String userAccountNumber) {
        this.userAccountNumber = userAccountNumber;
    }
}
