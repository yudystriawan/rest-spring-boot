package com.example.restspringboot.email;

public interface EmailSender {
    void send(String to, String bodyHtml);
}
