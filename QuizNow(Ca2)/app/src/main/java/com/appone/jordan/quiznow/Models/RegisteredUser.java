package com.appone.jordan.quiznow.Models;

import java.util.Date;

public class RegisteredUser
{
    private String userEmail;
    private int userScore;
    private Date userJoinedDate;

    public RegisteredUser(String userEmail, int userScore) {
        this.userEmail = userEmail;
        this.userScore = userScore;

        userJoinedDate = new Date();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public Date getUserJoinedDate() {
        return userJoinedDate;
    }

}
