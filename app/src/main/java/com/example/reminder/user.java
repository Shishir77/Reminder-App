package com.example.reminder;

public class user {

    public String firstName , lastName , dob , email , phoneNumber ;


    public user(){ // constructor

    }

    public user(String firstName, String lastName,String dob, String email , String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }


}
