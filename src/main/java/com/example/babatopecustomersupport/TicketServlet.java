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
        request.setAttribute("ticketMap", ticketMap);
        request.setAttribute("role", request.getSession().getAttribute("role"));
        request.getRequestDispatcher("/WEB-INF/jsp/view/listTickets.jsp").forward(request, response);

    }

    private void viewTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        int ticketId = Integer.parseInt(idString);

        Ticket ticket = getTicket(ticketId);

        request.setAttribute("ticketId", ticketId);
        request.setAttribute("ticket", ticket);
        request.setAttribute("role", request.getSession().getAttribute("role"));

        request.getRequestDispatcher("/WEB-INF/jsp/view/viewTicket.jsp").forward(request, response);

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
        request.getRequestDispatcher("/WEB-INF/jsp/view/ticketForm.jsp").forward(request, response);
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
