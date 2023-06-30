package com.example.babatopecustomersupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttachmentTest {

    @Test
    void getName() {
        // creating an attachment
        Attachment attachment = new Attachment();

        // setting a name
        attachment.setName("Test One");

        // retreive name and verify
        String name = attachment.getName();
        Assertions.assertEquals("Test One", name);
    }

    @Test
    void setName() {
        // creating an attachment
        Attachment attachment = new Attachment();

        // setting a name
        attachment.setName("Test Two");

        // retreive name and verify
        String name = attachment.getName();
        Assertions.assertEquals("Test Two", name);
    }

    @Test
    void getContents() {
        // creating an attachment
        Attachment attachment = new Attachment();

        // set contents
        byte[] contents = {0x01, 0x02, 0x03};
        attachment.setContents(contents);

        // retrieve contents and verify
        byte[] retrievedContents = attachment.getContents();
        Assertions.assertArrayEquals(contents, retrievedContents);

    }

    @Test
    void setContents() {
        // creating an attachment
        Attachment attachment = new Attachment();

        // set contents
        byte[] contents = {0x04, 0x05, 0x06};
        attachment.setContents(contents);

        // retrieve contents and verify
        byte[] retrievedContents = attachment.getContents();
        Assertions.assertArrayEquals(contents, retrievedContents);
    }
}