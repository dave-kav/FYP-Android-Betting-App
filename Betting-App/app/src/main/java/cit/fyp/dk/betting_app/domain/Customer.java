package cit.fyp.dk.betting_app.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davyk on 29/03/2017.
 */

public class Customer implements Serializable {

    private static final String TAG = "CUSTOMER";

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date DOB;
    private double credit;
    private List<Bet> bets;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date dOB) {
        DOB = dOB;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public List<Bet> getBetsByStatus(Status status) {
        List<Bet> newBetList = new ArrayList<>();
        for (int i = 0; i < bets.size(); i++) {
            Bet b = bets.get(i);

            if (b.getStatus() == status)
                newBetList.add(b);
        }
        return newBetList;
    }

    @Override
    public String toString() {
        return "Customer [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
                + lastName + ", DOB=" + DOB + ", credit=" + credit + "]";
    }
}
