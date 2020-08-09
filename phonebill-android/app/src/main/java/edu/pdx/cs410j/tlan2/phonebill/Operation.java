package edu.pdx.cs410j.tlan2.phonebill;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Operation implements Serializable {

    private double value;

    public Operation(double value){
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    @NonNull
    @Override
    public String toString() {
        return " = " + this.getValue();
    }

}


