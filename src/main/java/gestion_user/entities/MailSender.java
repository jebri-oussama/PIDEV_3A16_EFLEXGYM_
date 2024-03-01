package gestion_user.entities;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private static final String EMAIL_USERNAME = "souhailabdennebi2@gmail.com";
    private static final String APP_SPECIFIC_PASSWORD = "xqha tcfn ekes ggyr";

    public static void sendResetPasswordEmail(String toEmail, String resetToken) {
        String subject = "Password Reset";
        String body = "Votre Token pour accéder à votre compte :\n\n" +
                 resetToken;

        sendEmail(toEmail, subject, body);
    }

    private static void sendEmail(String toEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Use your SMTP server
        props.put("mail.smtp.port", "587"); // Use your SMTP port

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, APP_SPECIFIC_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent to: " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }}