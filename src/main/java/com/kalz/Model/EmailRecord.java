package com.kalz.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "email_records")
public class EmailRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private String subject;
    private Date receivedDate;
    private String attachmentPath; // Stores only the file path

    public EmailRecord() {
    }

    public EmailRecord(String sender, String subject, Date receivedDate, String attachmentPath) {
        this.sender = sender;
        this.subject = subject;
        this.receivedDate = receivedDate;
        this.attachmentPath = attachmentPath;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
}
