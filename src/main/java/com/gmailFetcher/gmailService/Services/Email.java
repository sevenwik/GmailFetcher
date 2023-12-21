package com.gmailFetcher.gmailService.Services;

public class Email {

    private final String sender;
    private final String subject;

    public Email(String sender, String subject) {
        this.sender = sender;
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }
}
