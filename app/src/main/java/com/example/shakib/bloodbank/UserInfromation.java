package com.example.shakib.bloodbank;

import java.util.Date;

/**
 * Created by shakib on 11/4/17.
 */

public class UserInfromation {
    public String name;
    public int age;
    public String bloodGroup;
    public int heightFeet;
    public int heightInch;
    public int weight;
    public String district;
    public String upazilla;
    public String mobile;
    public double bmi;
    public Boolean availability;


    public UserInfromation(){

    }

    public UserInfromation(Boolean availability){
        this.availability = availability;
    }

    public UserInfromation(String name, int age, String bloodGroup, int heightFeet, int heightInch, int weight, String district, String upazilla, String mobile, double bmi, Boolean availability) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.heightFeet = heightFeet;
        this.heightInch = heightInch;
        this.weight = weight;
        this.district = district;
        this.upazilla = upazilla;
        this.mobile = mobile;
        this.bmi = bmi;
        this.availability = availability;
    }
}
