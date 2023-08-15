package com.example.babatopecustomersupport.site;

import com.example.babatopecustomersupport.entities.Attachment;

public interface AttachmentRepository extends GenericRepository<Long, Attachment>{
    Attachment getByTicketId(Long ticketId);
}

