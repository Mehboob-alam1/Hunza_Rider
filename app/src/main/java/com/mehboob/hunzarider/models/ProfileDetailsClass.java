package com.mehboob.hunzarider.models;

public class ProfileDetailsClass {

    String userName,UserEmail,userPhoneNumber,  userAddress;

    public ProfileDetailsClass(String userName, String userEmail, String userPhoneNumber, String userAddress) {
        this.userName = userName;
        UserEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
    }

    public ProfileDetailsClass() {
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
