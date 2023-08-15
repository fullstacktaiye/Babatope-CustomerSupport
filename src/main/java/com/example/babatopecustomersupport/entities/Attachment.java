package com.example.babatopecustomersupport.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Arrays;
@Entity
@Table(name="attachments")
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;
    private long ticketId;
    private long attachmentId;
    private String name;
    private byte[] contents;

    @Id
    @Column(name="attachment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public long getId() {
        return attachmentId;
    }

    public void setId(long attachmentId){
        this.attachmentId = attachmentId;
    }

    @Basic
    @Column(name="ticket_id")
    public long getTicketId(){
        return ticketId;
    }

    public void setTicketId(long ticketId){
        this.ticketId = ticketId;
    }

    @Basic
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Lob
    public byte[] getContents(){
        return contents;
    }
    public void setContents(byte[] contents){
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Image{" +
                " name='" + name + '\'' +
                ", contents=" + Arrays.toString(contents) +
                '}';
    }

}
