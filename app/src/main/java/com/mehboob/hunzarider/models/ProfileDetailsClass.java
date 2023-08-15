package com.mehboob.hunzarider.models;

public class ProfileDetailsClass {

   private String userName,UserEmail,userPhoneNumber,  userAddress;
    private String userId;

    public ProfileDetailsClass(String userName, String userEmail, String userPhoneNumber, String userAddress, String userId) {
        this.userName = userName;
        UserEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userId = userId;
    }

    public ProfileDetailsClass() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
