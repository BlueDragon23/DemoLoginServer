package demo;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
public class SignupServiceImpl implements SignupService {
    @Override
    public void doSignup(String email) {
        System.out.println("Signing up with " + email);
        try {
            sendEmail(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String email) throws MessagingException {
        Properties prop = getSmtpProperties();
        Session session = Session.getInstance(prop);
        Message message = createMessage(email, session);
        Transport.send(message);
    }

    private Message createMessage(String email, Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("awesome_service@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Welcome to Awesome Service");
        String body = "Follow this link to finish signing up\nhttp://localhost:8080/complete?email=" + email;
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        return message;
    }

    private Properties getSmtpProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.starttls.enable", "false");
        prop.put("mail.smtp.host", "localhost");
        prop.put("mail.smtp.port", "1025");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        return prop;
    }
}
