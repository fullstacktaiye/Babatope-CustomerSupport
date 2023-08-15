package com.example.babatopecustomersupport.site;
import com.example.babatopecustomersupport.entities.Attachment;
import org.springframework.stereotype.Repository;
@Repository
public class DefaultAttachmentRepository extends GenericJpaRepository<Long, Attachment> implements AttachmentRepository{

    @Override
    public Attachment getByTicketId(Long attachmentId) {
        try {
            return this.entityManager.createQuery("SELECT i FROM Attachment i WHERE i.attachmentId = :id", Attachment.class).setParameter("id", attachmentId).getSingleResult();
        }
        catch(Exception e) {
            return null;
        }
    }
}


