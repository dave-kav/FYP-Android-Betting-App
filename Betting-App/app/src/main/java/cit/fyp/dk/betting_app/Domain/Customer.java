package cit.fyp.dk.betting_app.Domain;

import java.util.Date;

/**
 * Created by davyk on 29/03/2017.
 */

public class Customer {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date DOB;
    private double credit;

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

    @Override
    public String toString() {
        return "Customer [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
                + lastName + ", DOB=" + DOB + ", credit=" + credit + "]";
    }


}
