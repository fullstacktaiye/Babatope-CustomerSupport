package com.example.babatopecustomersupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
class TicketTest {
    private Ticket ticket;

    @BeforeEach
    public void setup(){
        ticket = new Ticket();
    }
    @Test
    void getCustomerName() {
        ticket.setCustomerName("Taiye Babatope");
        String customerName = ticket.getCustomerName();
        Assertions.assertEquals("Taiye Babatope", customerName);
    }

    @Test
    void setCustomerName() {
        ticket.setCustomerName("Deckard Cain");
        String customerName = ticket.getCustomerName();
        Assertions.assertEquals("Deckard Cain", customerName);
    }

    @Test
    void getSubject() {
        ticket.setSubject("The River of Flame");
        String subject = ticket.getSubject();
        Assertions.assertEquals("The River of Flame", subject);
    }

    @Test
    void setSubject() {
        ticket.setSubject("The River of Flame");
        String subject = ticket.getSubject();
        Assertions.assertEquals("The River of Flame", subject);
    }

    @Test
    void getBody() {
        ticket.setBody("Turn back");
        String body = ticket.getBody();
        Assertions.assertEquals("Turn back", body);
    }

    @Test
    void setBody() {
        ticket.setBody("Turn back");
        String body = ticket.getBody();
        Assertions.assertEquals("Turn back", body);
    }

    @Test
    void getAttachments() {
        Map<Integer, Attachment> attachments = new HashMap<>();
        Attachment attachment1 = new Attachment();
        Attachment attachment2 = new Attachment();
        attachments.put(1, attachment1);
        attachments.put(2, attachment2);

        ticket.setAttachments(attachments);
        Map<Integer, Attachment> retrievedAttachments = ticket.getAttachments();
        Assertions.assertEquals(attachments, retrievedAttachments);
    }

    @Test
    void setAttachments() {
        Map<Integer, Attachment> attachments = new HashMap<>();
        Attachment attachment1 = new Attachment();
        Attachment attachment2 = new Attachment();
        attachments.put(1, attachment1);
        attachments.put(2, attachment2);

        ticket.setAttachments(attachments);
        Map<Integer, Attachment> retrievedAttachments = ticket.getAttachments();
        Assertions.assertEquals(attachments, retrievedAttachments);
    }

    @Test
    void addAttachment() {
        Attachment attachment = new Attachment();
        ticket.addAttachment(1, attachment);

        Assertions.assertEquals(1, ticket.getNumberOfAttachments());
        Assertions.assertEquals(attachment, ticket.getAttachmentByIndex(1));
    }

    @Test
    void getNumberOfAttachments() {
        Attachment attachment1 = new Attachment();
        Attachment attachment2 = new Attachment();
        ticket.addAttachment(1, attachment1);
        ticket.addAttachment(2, attachment2);

        int numOfAttachments = ticket.getNumberOfAttachments();
        Assertions.assertEquals(2, numOfAttachments);
    }

    @Test
    void getAttachmentByIndex() {
        Attachment attachment1 = new Attachment();
        Attachment attachment2 = new Attachment();
        ticket.addAttachment(1, attachment1);
        ticket.addAttachment(2, attachment2);

        Attachment retrievedAttachment1 = ticket.getAttachmentByIndex(1);
        Attachment retrievedAttachment2 = ticket.getAttachmentByIndex(2);

        Assertions.assertEquals(attachment1, retrievedAttachment1);
        Assertions.assertEquals(attachment2, retrievedAttachment2);

    }

    @Test
    void getAllAttachments() {
        Attachment attachment1 = new Attachment();
        Attachment attachment2 = new Attachment();
        ticket.addAttachment(1, attachment1);
        ticket.addAttachment(2, attachment2);

        List<Attachment> attachmentsList = ticket.getAllAttachments();

        Assertions.assertEquals(2, attachmentsList.size());
        Assertions.assertTrue(attachmentsList.contains(attachment1));
        Assertions.assertTrue(attachmentsList.contains(attachment2));
    }
}