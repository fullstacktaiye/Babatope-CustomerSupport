package com.example.babatopecustomersupport.site;

import com.example.babatopecustomersupport.site.Attachment;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
public class Ticket {
    private String customerName;
    private String subject;
    private String body;
    private Map<Integer, Attachment> attachments;

    public Ticket(){
        attachments = new HashMap<>();
    }

    public Ticket(String customerName, String subject, String body, Map<Integer, Attachment> attachments){
        this.customerName = customerName;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments;
    }

    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public Map<Integer, Attachment> getAttachments(){
        return attachments;
    }

    public void setAttachments(Map<Integer, Attachment> attachments){
        this.attachments = attachments;
    }

    public void addAttachment(int index, Attachment attachment){
        attachments.put(index, attachment);
    }

    public int getNumberOfAttachments(){
        return attachments.size();
    }

    public Attachment getAttachmentByIndex(int index){
        return attachments.get(index);
    }

    public List<Attachment> getAllAttachments(){
        return List.copyOf(attachments.values());
    }

}
