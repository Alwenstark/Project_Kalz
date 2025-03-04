package com.kalz.Service;

import com.kalz.Repository.EmailRepository;
import jakarta.annotation.PostConstruct;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.search.FromTerm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

@Service
public class EmailService {

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Value("${mail.imap.host}")
    private String mailHost;

    @Value("${mail.imap.port}")
    private String mailPort;

    @Value("${mail.imap.username}")
    private String username;

    @Value("${mail.imap.password}")
    private String password;

    @Value("${mail.download.dir}")
    private String downloadDir;

    @Value("${mail.filter.sender}")
    private String allowedSender;

    public void fetchEmails() {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", mailHost);
            properties.put("mail.imaps.port", mailPort);
            properties.put("mail.imaps.ssl.enable", "true");

            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(mailHost, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FromTerm(new InternetAddress(allowedSender)));

            for (Message message : messages) {
                System.out.println("Subject: " + message.getSubject());

                // Get email body
                Object content = message.getContent();
                if (content instanceof String) {
                    System.out.println("Body: " + content);
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        if (bodyPart.isMimeType("text/plain")) {
                            System.out.println("Body: " + bodyPart.getContent());
                            break;
                        }
                    }
                }

                // Check for attachments
                boolean hasAttachment = false;
                if (message.getContent() instanceof Multipart) {
                    Multipart multipart = (Multipart) message.getContent();
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart part = multipart.getBodyPart(i);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            saveAttachment(part);
                            hasAttachment = true;
                        }
                    }
                }
                if (hasAttachment) {
                    System.out.println("Attachment downloaded");
                }

                System.out.println("----------------------------------");
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAttachment(BodyPart bodyPart) throws Exception {
        String fileName = bodyPart.getFileName();
        File dir = new File(downloadDir);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(downloadDir + "/" + fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ((MimeBodyPart) bodyPart).saveFile(file);
        }
    }

    @PostConstruct
    public void init() {
        fetchEmails();
    }
}
