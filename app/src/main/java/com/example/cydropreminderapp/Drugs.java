package com.example.cydropreminderapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Drugs implements Parcelable {
    private String name;
    private String concentration;
    private String dosage_method;
    private int frequency;
    private List<Calendar> selected_Times;

    public Drugs(String name, String concentration, String dosage_method, int frequency) {
        this.name = name;
        this.concentration = concentration;
        this.dosage_method = dosage_method;
        this.frequency = frequency;
        this.selected_Times = new ArrayList<>();
    }

    protected Drugs(Parcel in) {
        name = in.readString();
        concentration = in.readString();
        dosage_method = in.readString();
        frequency = in.readInt();
        // Handle reading Calendar objects
        selected_Times = new ArrayList<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            long timeInMillis = in.readLong();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeInMillis);
            selected_Times.add(calendar);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(concentration);
        dest.writeString(dosage_method);
        dest.writeInt(frequency);
        // Handle writing Calendar objects
        dest.writeInt(selected_Times.size());
        for (Calendar calendar : selected_Times) {
            dest.writeLong(calendar.getTimeInMillis());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Drugs> CREATOR = new Creator<Drugs>() {
        @Override
        public Drugs createFromParcel(Parcel in) {
            return new Drugs(in);
        }

        @Override
        public Drugs[] newArray(int size) {
            return new Drugs[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getConcentration() {
        return concentration;
    }

    public String getDosage_method() {
        return dosage_method;
    }

    public int getFrequency() {
        return frequency;
    }

    public List<Calendar> getSelected_Times() {
        return selected_Times;
    }

    public void setSelectedTimes(List<Calendar> selected_Times) {
        this.selected_Times = selected_Times;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public void setDosage_method(String dosage_method) {
        this.dosage_method = dosage_method;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
