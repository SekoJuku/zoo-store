package com.example.oauth2.mailsender;

public class MailSenderConstants {
    public static final String REGISTRATION_CONFIRMATION_SUBJECT = "Confirm your email";
    public static final String REGISTRATION_CONFIRMATION_TEXT = "Hello, Dear User!\n\nYour verification link to activate your account: %s";
    public static final String LINK_TO_CONFIRM_REGISTRATION = "%s/api/v1/auth/confirm-registration?token=%s&email=%s";

    public static final String RESET_PASSWORD_SUBJECT = "Confirm reset password";
    public static final String RESET_PASSWORD_TEXT = "Confirm reset password";
}
