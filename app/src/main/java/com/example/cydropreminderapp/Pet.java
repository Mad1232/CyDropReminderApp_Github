package com.example.cydropreminderapp;

import android.os.Parcel;
import android.os.Parcelable;

// The Pet class implements Parcelable, which allows Pet objects to be written to and restored from a Parcel.
// This is necessary for passing objects between activities.
public class Pet implements Parcelable {
    // Define fields for the Pet class
    private String name;
    private String type;
    private String sex;
    private String dob;
    private String diagnosis;
    private String breed;

    // Constructor to initialize a Pet object
    public Pet(String name, String type, String sex, String dob, String diagnosis, String breed) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.dob = dob;
        this.diagnosis = diagnosis;
        this.breed = breed;
    }

    // Constructor to create a Pet object from a Parcel
    protected Pet(Parcel in) {
        name = in.readString();       // Read the name from the Parcel
        type = in.readString();       // Read the type from the Parcel
        sex = in.readString();        // Read the sex from the Parcel
        dob = in.readString();        // Read the date of birth from the Parcel
        diagnosis = in.readString();  // Read the diagnosis from the Parcel
        breed = in.readString();
    }

    // The CREATOR field is required for Parcelable implementation.
    // It generates instances of the Parcelable Pet class from a Parcel.
    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);  // Call the constructor that creates a Pet from a Parcel
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];  // Create a new array of Pet objects
        }
    };

    // Getter methods to access the fields of the Pet object
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSex() {
        return sex;
    }

    public String getDob() {
        return dob;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getBreed(){return breed;}

    // This method is part of the Parcelable interface. It usually returns 0 for most implementations.
    @Override
    public int describeContents() {
        return 0;
    }

    // This method writes the Pet object's data to a Parcel, allowing it to be passed between activities.
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(sex);
        dest.writeString(dob);
        dest.writeString(diagnosis);
        dest.writeString(breed);
    }
}

