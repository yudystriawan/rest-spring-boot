package com.example.restspringboot.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String bodyHtml) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(bodyHtml, true);
            helper.setTo(to);
            helper.setSubject("Confirm your account");
            helper.setFrom("no-reply@example.com");

            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            log.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email.");
        }
    }
}
