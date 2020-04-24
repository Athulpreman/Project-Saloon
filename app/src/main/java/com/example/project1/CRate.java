package com.example.project1;

public class CRate
{
    int reating;
    String subject,feedback,qrString;

    public CRate()
    {
    }

    public CRate(int reating, String subject, String feedback, String qrString) {
        this.reating = reating;
        this.subject = subject;
        this.feedback = feedback;
        this.qrString = qrString;
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
}
