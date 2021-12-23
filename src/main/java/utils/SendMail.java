package utils;

import org.apache.commons.compress.harmony.pack200.PackingUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            String log_file = "src/logs/application.html";

            // Create data source and pass the filename
            DataSource source = new FileDataSource(log_file);

            // set the handler
            messageBodyPart2.setDataHandler(new DataHandler(source));

            // set the file
            messageBodyPart2.setFileName(log_file);

            MimeBodyPart messageBodyPart3 = new MimeBodyPart();
            String zip_file = "No Screenshots Found";
            long kilobytes = 0;
            if(new File(zip_file).exists()){
                zip_file = "src/zipFolder/failedScreenshots.zip";
                Path path = Paths.get(zip_file);
                long bytes = Files.size(path);
                kilobytes = (bytes / 1024);
                System.out.println("File size 1: " + String.format("%,d bytes", kilobytes));
                System.out.println("File size 2: " + String.format("%,d kilobytes", kilobytes / 1024));

                DataSource source1 = new FileDataSource(zip_file);
                messageBodyPart3.setDataHandler(new DataHandler(source1));
                messageBodyPart3.setFileName(zip_file);
            }

            // Create object of MimeMultipart class
            Multipart multipart = new MimeMultipart();

            // add body part 1
            multipart.addBodyPart(messageBodyPart2);

            // add body part 2
            multipart.addBodyPart(messageBodyPart1);

            if(new File(zip_file).exists() && kilobytes <= 20) {
                multipart.addBodyPart(messageBodyPart3);
            } else {
                messageBodyPart3.setText("***********************************\n***********************************\n***********************************" +
                        "Screenshot is not found or may the size is greater than 20MB, Kindly check or the log files" +
                        "***********************************\n***********************************\n***********************************");
            }

            // set the content
            message.setContent(multipart);


            // finally send the email
            Transport.send(message);

            System.out.println("=====Email Sent=====");

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
