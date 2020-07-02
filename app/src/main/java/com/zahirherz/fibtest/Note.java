package com.zahirherz.fibtest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int inputNumber;
    private int fibNumber;
    private double timeToCalculate;
    private double totalTimeToCalculate;

    public Note(int inputNumber, int fibNumber, double timeToCalculate, double totalTimeToCalculate) {
        this.inputNumber = inputNumber;
        this.fibNumber = fibNumber;
        this.timeToCalculate = timeToCalculate;
        this.totalTimeToCalculate = totalTimeToCalculate;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getInputNumber() {
        return inputNumber;
    }

    public int getFibNumber() {
        return fibNumber;
    }

    public double getTimeToCalculate() {
        return timeToCalculate;
    }

    public double getTotalTimeToCalculate() {
        return totalTimeToCalculate;
    }
}
