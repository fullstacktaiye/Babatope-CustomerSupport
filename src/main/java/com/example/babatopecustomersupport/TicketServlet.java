package com.example.babatopecustomersupport;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;

@WebServlet(name = "TicketServlet", value = "/TicketServlet")
@MultipartConfig(fileSizeThreshold = 5_242_880, maxFileSize = 20_971_520L, maxRequestSize = 41_943_040L)
public class TicketServlet extends HttpServlet {
    private Map<Integer, Ticket> ticketMap;
    private int ticketCounter;

    @Override
    public void init() throws ServletException {
        super.init();

        ticketMap = new HashMap<>();
        ticketCounter = 1;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String action = request.getParameter("action");

        if (action == null){
            listTickets(request, response);
        } else if (action.equals("view")){
            viewTicket(request, response);
        } else if (action.equals("download")){
            downloadTicket(request,response);
        } else if (action.equals("createTicketForm")) {
            createTicketForm(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String action = request.getParameter("action");

        if (action.equals("create")){
            createTicket(request, response);
        }
    }

    private void listTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Heading and link to create a ticket
        out.println("<html><body><h2>Tickets</h2>");
        out.println("<a href=\"TicketServlet?action=createTicketForm\">Create Ticket</a><br><br>");

        // List out the tickets
        if (ticketMap.size() == 0) {
            out.println("There are no tickets yet...");
        } else {
            for (int id : ticketMap.keySet()) {
                Ticket ticket = ticketMap.get(id);
                out.println("Ticket #" + id);
                out.println(": <a href=\"TicketServlet?action=view&id=" + id + "\">");
                out.println(ticket.getSubject() + "</a><br>");
            }
        }
        out.println("</body></html>");

    }

    private void viewTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        int ticketId = Integer.parseInt(idString);

        Ticket ticket = getTicket(ticketId);

        PrintWriter out = response.getWriter();
        out.println("<html><body><h2>Ticket Details</h2>");
        out.println("<h3>" + ticket.getSubject() + "</h3>");
        out.println("<p>Customer Name: " + ticket.getCustomerName() + "</p>");
        out.println("<p>Body: " + ticket.getBody() + "</p>");

        Map<Integer, Attachment> attachments = ticket.getAttachments();
        if (!attachments.isEmpty()) {
            out.println("<h4>Attachments:</h4>");
            for (int attachmentId : attachments.keySet()) {
                Attachment attachment = attachments.get(attachmentId);
                out.println("<a href=\"TicketServlet?action=download&ticketId=" + ticketId +
                        "&attachmentId=" + attachmentId + "\">" + attachment.getName() + "</a><br>");
            }
        }

        out.println("<a href=\"TicketServlet\">Return to ticket list</a>");
        out.println("</body></html>");
    }

    private void createTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(request.getParameter("customerName"));
        ticket.setSubject(request.getParameter("subject"));
        ticket.setBody(request.getParameter("body"));

        Part filepart = request.getPart("attachment");
        if (filepart != null){
            Attachment attachment = processAttachment(filepart);
            if (attachment != null){
                int attachmentId = findAvailableAttachmentId(ticket);
                ticket.addAttachment(attachmentId, attachment);
            }
        }

        // add and synchronize
        int ticketId;
        synchronized (this){
            ticketId = this.ticketCounter++;
            ticketMap.put(ticketId, ticket);
        }

        response.sendRedirect("TicketServlet?action=view&id=" + ticketId);
    }

    private void createTicketForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        out.println("<html><body><h2>Create Ticket</h2>");
        out.println("<form action=\"TicketServlet\" method=\"post\" enctype=\"multipart/form-data\">");
        out.println("<input type=\"hidden\" name=\"action\" value=\"create\">");
        out.println("<label for=\"customerName\">Customer Name:</label><br>");
        out.println("<input type=\"text\" id=\"customerName\" name=\"customerName\"><br><br>");
        out.println("<label for=\"subject\">Subject:</label><br>");
        out.println("<input type=\"text\" id=\"subject\" name=\"subject\"><br><br>");
        out.println("<label for=\"body\">Body:</label><br>");
        out.println("<textarea id=\"body\" name=\"body\"></textarea><br><br>");
        out.println("<label for=\"attachment\">Attachment:</label><br>");
        out.println("<input type=\"file\" id=\"attachment\" name=\"attachment\"><br><br>");
        out.println("<input type=\"submit\" value=\"Create Ticket\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    private void downloadTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int attachmentId = Integer.parseInt(request.getParameter("attachmentId"));

        Ticket ticket = getTicket(ticketId);
        Attachment attachment = ticket.getAttachmentByIndex(attachmentId);

        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");
        response.getOutputStream().write(attachment.getContents());
    }

    private Attachment processAttachment(Part filePart) throws IOException {
        String fileName = filePart.getSubmittedFileName();
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while((bytesRead = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, bytesRead);
        }

        Attachment attachment = new Attachment();
        attachment.setName(fileName);
        attachment.setContents(outputStream.toByteArray());

        return attachment;
    }

    private Ticket getTicket(int ticketId){
        return ticketMap.get(ticketId);
    }

    private int findAvailableAttachmentId(Ticket ticket) {
        int maxId = 0;
        for (int attachmentId : ticket.getAttachments().keySet()) {
            maxId = Math.max(maxId, attachmentId);
        }
        return maxId + 1;
    }
}
