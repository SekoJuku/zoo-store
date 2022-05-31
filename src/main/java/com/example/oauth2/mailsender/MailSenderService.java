package com.example.oauth2.mailsender;


import com.example.exception.domain.InternalServerException;
import com.example.oauth2.model.RegistrationConfirmation;
import com.example.oauth2.model.User;
import com.example.oauth2.repository.RegistrationConfirmationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

import static com.example.oauth2.mailsender.MailSenderConstants.LINK_TO_CONFIRM_REGISTRATION;
import static com.example.oauth2.mailsender.MailSenderConstants.REGISTRATION_CONFIRMATION_SUBJECT;
import static com.example.oauth2.mailsender.MailSenderConstants.REGISTRATION_CONFIRMATION_TEXT;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${custom-properties.domain}")
    private String webSiteDomain;

    private final JavaMailSender mailSender;
    private final RegistrationConfirmationRepository registrationConfirmationRepository;

    public void sendEmail(String subject, String text, String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setFrom(fromEmail);
            helper.setText(text);
            helper.setSubject(subject);
            helper.setTo(toEmail);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new InternalServerException("Failed to send email", e);
        }
    }


    public void sendRegistrationConfirmation(User to) {
        String token = UUID.randomUUID().toString();

        RegistrationConfirmation registrationConfirmation = new RegistrationConfirmation();
        registrationConfirmation.setToken(token);
        registrationConfirmation.setUser(to);
        registrationConfirmationRepository.save(registrationConfirmation);

        String verificationLink = String.format(LINK_TO_CONFIRM_REGISTRATION, webSiteDomain, registrationConfirmation.getToken(), to.getEmail());
        String text = String.format(REGISTRATION_CONFIRMATION_TEXT, verificationLink);

        sendEmail(REGISTRATION_CONFIRMATION_SUBJECT, text, to.getEmail());
    }
}
