package com.mehboob.hunzarider.models;

public class PaymentDetailClass {

    String bankName,userBankName,userAccountNumber;


    public PaymentDetailClass() {
    }

    public PaymentDetailClass(String bankName, String userBankName, String userAccountNumber) {
        this.bankName = bankName;
        this.userBankName = userBankName;
        this.userAccountNumber = userAccountNumber;
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
