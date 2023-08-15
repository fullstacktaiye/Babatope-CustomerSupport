package com.example.babatopecustomersupport.site;

import com.example.babatopecustomersupport.entities.TicketEntity;
import org.springframework.stereotype.Repository;
@Repository
public class DefaultTicketRepository extends GenericJpaRepository<Long, TicketEntity> implements TicketRepository {

}
