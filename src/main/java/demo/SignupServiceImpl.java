package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
public class SignupServiceImpl implements SignupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupServiceImpl.class);

    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;

    @Override
    public void doSignup(String email) {
        LOGGER.debug("Signing up with {}", email);
        try {
            sendEmail(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completeSignup(String email, String password) {
        LOGGER.debug("Completing signup with email {} and password {}", email, password);
    }

    private void sendEmail(String email) throws MessagingException {
        Properties prop = getSmtpProperties();
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Message message = createMessage(email, session);
        Transport.send(message);
    }

    private Message createMessage(String email, Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
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
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }
}
