package com.example.babatopecustomersupport.site;
import com.example.babatopecustomersupport.entities.TicketEntity;
import com.example.babatopecustomersupport.entities.Attachment;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultTicketService implements TicketService {
    @Inject TicketRepository ticketRepo;
    @Inject AttachmentRepository attachmentRepo;

    @Override
    public List<Ticket> getAllTickets(){
        List<Ticket> list = new ArrayList<>();
        ticketRepo.getAll().forEach(e -> list.add(this.convert(e)));
        return list;
    }

    @Override
    public Ticket getTicket(long id) {
        TicketEntity entity = ticketRepo.get(id);
        return entity == null ? null : this.convert(entity);
    }

    private Ticket convert(TicketEntity entity){
        Ticket ticket = new Ticket();
        ticket.setId(entity.getId());
        ticket.setCustomerName(entity.getCustomerName());
        ticket.setSubject(entity.getSubject());
        ticket.setBody(entity.getBody());
        // Look up attachment
        ticket.setAttachment(attachmentRepo.getByTicketId(entity.getId()));

        return ticket;
    }

    @Override
    @Transactional
    public void save(Ticket ticket) {
        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());
        entity.setCustomerName(ticket.getCustomerName());
        entity.setSubject(ticket.getSubject());
        entity.setBody(ticket.getBody());

        if (ticket.getId() < 1) { // doesn't have an id which means not updating
            // add to the repo
            ticketRepo.add(entity);
            ticket.setId(entity.getId()); // get the id from the entity to use in the Controller to actually view the blog
            // add image?
            if (ticket.hasAttachment()) {
                ticket.getAttachment().setTicketId(entity.getId());
                attachmentRepo.add(ticket.getAttachment());
            }
        }
        else { // just editing the blog so update it on the DB
            ticketRepo.update(entity);
        }

    }

    @Override
    @Transactional
    public void deleteTicket(long id) {
        ticketRepo.deleteById(id);
    }

}
