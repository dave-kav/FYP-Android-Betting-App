package cit.fyp.dk.betting_app.domain;

import java.io.Serializable;

/**
 * Created by davyk on 30/03/2017.
 */

public class Bet implements Serializable {

    // Unique number auto-assigned to each bet via DB
    private int betID;
    private String timePlaced;
    // E.g. horse that is being bet on
    private String selection;
    private int RaceID;
    //amount being bet
    private double stake;
    private String odds;
    //location of captured betting slip image
    private String imagePath;
    private boolean eachWay;
    // indicates whether bet has been translated i.e. details entered into system
    private boolean translated;
    // indicates whether bet was placed via app
    private boolean onlineBet;
    // amount returned if bet is a winner
    private double winnings;
    // indicates if bet needs to be settled, is winner or loser
    private Status status;
    //indicates if bet has been paid out or not
    private boolean paid;
    //for online bets
    private String customerID;

    public int getBetID() {
        return betID;
    }

    public void setBetID(int betID) {
        this.betID = betID;
    }

    public String getTimePlaced() {
        return timePlaced;
    }

    public void setTimePlaced(String timePlaced) {
        this.timePlaced = timePlaced;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public int getRaceID() {
        return RaceID;
    }

    public void setRaceID(int raceID) {
        RaceID = raceID;
    }

    public double getStake() {
        return stake;
    }

    public void setStake(double stake) {
        this.stake = stake;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String image) {
        this.imagePath = image;
    }

    public boolean isEachWay() {
        return eachWay;
    }

    public void setEachWay(boolean eachWay) {
        this.eachWay = eachWay;
    }

    public boolean isTranslated() {
        return translated;
    }

    public void setTranslated(boolean translated) {
        this.translated = translated;
    }

    public boolean isOnlineBet() {
        return onlineBet;
    }

    public void setOnlineBet(boolean onlineBet) {
        this.onlineBet = onlineBet;
    }

    public double getWinnings() {
        return winnings;
    }

    public void setWinnings(double winnings) {
        this.winnings = winnings;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "Bet [betID=" + betID + ", timePlaced=" + timePlaced + ", selection=" + selection + ", RaceID=" + RaceID
                + ", stake=" + stake + ", odds=" + odds + ", imagePath=" + imagePath + ", eachWay=" + eachWay
                + ", translated=" + translated + ", onlineBet=" + onlineBet + ", winnings=" + winnings + ", status="
                + status + ", paid=" + paid + ", customerID=" + customerID + "]";
    }
}
