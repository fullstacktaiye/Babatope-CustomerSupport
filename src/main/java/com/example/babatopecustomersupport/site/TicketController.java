    package com.example.babatopecustomersupport.site;

    import com.example.babatopecustomersupport.entities.Attachment;
    import org.springframework.stereotype.Controller;
    import jakarta.inject.Inject;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    import org.springframework.web.servlet.ModelAndView;
    import org.springframework.web.servlet.View;
    import org.springframework.web.servlet.view.RedirectView;

    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.servlet.http.HttpSession;
    import java.io.IOException;
    import java.util.HashMap;
    import java.util.Map;

    @Controller
    @RequestMapping("TicketServlet")
    public class TicketController {
    //    private volatile int ticketCounter = 1;
    //    private Map<Integer, Ticket> ticketMap = new HashMap<>();

        @Inject TicketService ticketService;
        @RequestMapping(value = {"list", ""})
        public String listTickets (Model model, HttpSession session) {
            model.addAttribute("ticketDB", ticketService.getAllTickets());
            model.addAttribute("role", session.getAttribute("role"));
            System.out.println(ticketService.getAllTickets());
            return "listTickets";
        }

        @GetMapping("create")
        public ModelAndView createTicketForm() {
            return new ModelAndView("ticketForm", "ticket", new TicketForm());
        }

        @PostMapping("create")
        public View createTicket(@ModelAttribute("ticket")TicketForm form) throws IOException {
            Ticket ticket = new Ticket();
            ticket.setCustomerName(form.getCustomerName());
            ticket.setSubject(form.getSubject());
            ticket.setBody(form.getBody());

            MultipartFile file = form.getAttachment();
            Attachment attachment = new Attachment();
            attachment.setName(file.getOriginalFilename());
            attachment.setContents(file.getBytes());
            if ((attachment.getName() != null && attachment.getName().length() > 0) ||
                    (attachment.getContents() != null && attachment.getContents().length > 0)) {
                ticket.setAttachment(attachment);
            }

    //        int id;
    //        synchronized(this) {
    //            id = this.ticketCounter++;
    //            ticketMap.put(id, ticket);
    //        }

            ticketService.save(ticket);

            return new RedirectView("view/"+ticket.getId(), true, false);
        }

        @GetMapping("view/{ticketId}")
        public ModelAndView viewTicket(Model model, @PathVariable("ticketId")int ticketId) {
    //        Ticket ticket = ticketMap.get(ticketId);
            Ticket ticket = ticketService.getTicket(ticketId); // get the blog
            if (ticket == null) {
                return new ModelAndView(new RedirectView("ticketServlet/list", true, false));
            }

            model.addAttribute("ticketId", ticketId);
            model.addAttribute("ticket", ticket);

            System.out.println(ticket);

            return new ModelAndView("viewTicket");
        }

        @GetMapping("{ticketId}/attachment/{attachment:.+}")
        public void downloadAttachment(@PathVariable("ticketId")int ticketId, @PathVariable("attachment")String name, HttpServletResponse response) throws IOException {
    //        Ticket ticket = ticketMap.get(ticketId);
            Ticket ticket = ticketService.getTicket(ticketId);
            if (ticket == null) {
                response.sendRedirect("listTickets");
                return;
            }

            Attachment attachment = ticket.getAttachment();
            if (attachment == null) {
                response.sendRedirect("listTickets");
                return;
            }

            response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
            response.setContentType("application/octet-stream");
            response.getOutputStream().write(attachment.getContents());
        }

        public static class TicketForm {
            private String customerName;
            private String subject;
            private String body;
            private MultipartFile attachment;

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public MultipartFile getAttachment() {
                return attachment;
            }

            public void setAttachment(MultipartFile attachment) {
                this.attachment = attachment;
            }
        }



    }
