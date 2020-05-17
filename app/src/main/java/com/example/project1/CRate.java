package com.example.project1;

public class CRate
{
    int reating;
    String subject,feedback,qrString,date,mobile;

    public CRate()
    {
    }

    public CRate(int reating, String subject, String feedback, String qrString, String date, String mobile) {
        this.reating = reating;
        this.subject = subject;
        this.feedback = feedback;
        this.qrString = qrString;
        this.date = date;
        this.mobile = mobile;
    }

    public int getReating() {
        return reating;
    }

    public void setReating(int reating) {
        this.reating = reating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
