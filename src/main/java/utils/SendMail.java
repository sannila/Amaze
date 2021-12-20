package utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class SendMail {

    // Create object of Property file
    static Properties prop = new Properties();


    public static void mailFunction(){
        // this will set host of server- you can change based on your requirement
        prop.put("mail.smtp.host", "smtp.gmail.com");

        // set the port of socket factory
        prop.put("mail.smtp.socketFactory.port", "465");

        // set socket factory
        prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

        // set the authentication to true
        prop.put("mail.smtp.auth", "true");

        // set the port of SMTP server
        prop.put("mail.smtp.port", "465");

        // This will handle the complete authentication
        Session session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication("sanjaim@kmitsolutions.com", "wuiljdwypwemamra");
                    }
                });

        try {
            // Create object of MimeMessage class
            Message message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress("sanjaim@kmitsolutions.com"));

            // Set the recipient address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sanjaim@kmitsolutions.com"));

            // Add the subject link
            message.setSubject("Amaze POS - Test Result");

            // Create object to add multimedia type content
            BodyPart messageBodyPart1 = new MimeBodyPart();

            // Set the body of email
            messageBodyPart1.setText("This the amaze pos test result");

            // Create another object to add another content
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            // Mention the file which you want to send
            String fileName = "src/logs/application.html";

            // Create data source and pass the filename
            DataSource source = new FileDataSource(fileName);

            // set the handler
            messageBodyPart2.setDataHandler(new DataHandler(source));

            // set the file
            messageBodyPart2.setFileName(fileName);

            // Create object of MimeMultipart class
            Multipart multipart = new MimeMultipart();

            // add body part 1
            multipart.addBodyPart(messageBodyPart2);

            // add body part 2
            multipart.addBodyPart(messageBodyPart1);

            // set the content
            message.setContent(multipart);


            // finally send the email
            Transport.send(message);

            System.out.println("=====Email Sent=====");

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
