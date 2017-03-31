package cit.fyp.dk.betting_app.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by davyk on 31/03/2017.
 */

public class Race implements Serializable {

    private int raceID;
    private String time;
    private String track;
    private int places;
    private double terms;
    private int runners;
    private Horse winner;
    private int winnerID;
    private List<Horse> placedHorses;
    private List<Integer> placedHorseIDs;
    private List<Horse> allHorses;

    public int getRaceID() {
        return raceID;
    }

    public void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    public String  getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public double getTerms() {
        return terms;
    }

    public void setTerms(double terms) {
        this.terms = terms;
    }

    public int getRunners() {
        return runners;
    }

    public void setRunners(int runners) {
        this.runners = runners;
    }

    public Horse getWinner() {
        return winner;
    }

    public void setWinner(Horse winner) {
        this.winner = winner;
    }

    public List<Horse> getPlacedHorses() {
        return placedHorses;
    }

    public void setPlacedHorses(List<Horse> placedHorses) {
        this.placedHorses = placedHorses;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(int winnerID) {
        this.winnerID = winnerID;
    }

    public List<Integer> getPlacedHorseIDs() {
        return placedHorseIDs;
    }

    public void setPlacedHorseIDs(List<Integer> placedHorseIDs) {
        this.placedHorseIDs = placedHorseIDs;
    }

    public List<Horse> getAllHorses() {
        return allHorses;
    }

    public void setAllHorses(List<Horse> allHorses) {
        this.allHorses = allHorses;
    }

    @Override
    public String toString() {
        return "Race [raceID=" + raceID + ", time=" + time + ", track=" + track + ", places=" + places + ", terms="
                + terms + ", runners=" + runners + "]";
    }


}
