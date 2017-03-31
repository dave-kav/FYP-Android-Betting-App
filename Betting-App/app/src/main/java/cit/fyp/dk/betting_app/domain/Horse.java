package cit.fyp.dk.betting_app.domain;

import java.io.Serializable;

/**
 * Created by davyk on 31/03/2017.
 */

public class Horse implements Serializable{

    private int selectionID;
    private String name;
    // unique ID of race in which horse is running
    private int raceID;
    // The horses assigned number within the race
    private int number;

    public int getSelectionID() {
        return selectionID;
    }

    public void setSelectionID(int selectionID) {
        this.selectionID = selectionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRaceID() {
        return raceID;
    }

    public void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Horse [name=" + name + ", raceID=" + raceID + ", number=" + number + "]";
    }
}

