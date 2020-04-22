package com.example.project1;

public class ClassFeedback
{
    String feedback,date,username,number;

    public ClassFeedback()
    {
    }

    public ClassFeedback(String feedback, String date, String username, String number) {
        this.feedback = feedback;
        this.date = date;
        this.username = username;
        this.number = number;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
