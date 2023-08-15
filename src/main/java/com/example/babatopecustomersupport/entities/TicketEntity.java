package com.example.babatopecustomersupport.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@Entity
@Table(name="Tickets")
public class TicketEntity implements Serializable{
    private static final long serialVersionUID = 1L; // unique id for serializable version
    private long id; // primary key
    private String customerName;
    private String subject;
    private String body;

    @Id
    @Column(name="ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    @Basic
    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    @Basic
    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }
}
