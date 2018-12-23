package com.appone.jordan.quiznow.Models;

import java.util.Date;

public class RegisteredUser
{
    /**
     * This class defines our register used for our real time database
     */
    private String username;
    private int userScore;
    private String userJoinedDate;

    public RegisteredUser(String username, int userScore) {
        this.username = username;
        this.userScore = userScore;

        userJoinedDate = "2018";
    }

    public RegisteredUser(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getUserJoinedDate() {
        return userJoinedDate;
    }

}
