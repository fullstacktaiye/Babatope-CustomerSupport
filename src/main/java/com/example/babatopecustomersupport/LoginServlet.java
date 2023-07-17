package com.example.babatopecustomersupport;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

@WebServlet(name="loginServlet", value="/login")
public class LoginServlet extends HttpServlet {
    public static final Map<String, String> userDB = new Hashtable<>();
    static {
        userDB.put("Taiye", "admin123");
        userDB.put("JJ", "password123");
        userDB.put("Song", "whiteseas");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // if logout exists, log us out
        if(request.getParameter("logout") != null) {
            session.invalidate(); // logs us out
            response.sendRedirect("login");
            return;
        }        // check if logged in - then go to main page
        else if (session.getAttribute("username") != null) {
            response.sendRedirect("TicketServlet");
            return;
        }

        // not logged in, go to login page - initial login page
        request.setAttribute("loginFailed", false);
        request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ("signup".equals(action)) {
            // Handle signup
            if (username != null && password != null && !LoginServlet.userDB.containsKey(username)) {
                LoginServlet.userDB.put(username, password);
                session.setAttribute("username", username);
                request.changeSessionId(); // protects against session fixation attacks
                response.sendRedirect("TicketServlet");
            } else {
                // Handle signup failure (e.g., username already exists)
                request.setAttribute("signupFailed", true);
                request.getRequestDispatcher("/WEB-INF/jsp/view/signup.jsp").forward(request, response);
            }
        } else {
            // Handle login
            if (session.getAttribute("username") != null) {
                response.sendRedirect("TicketServlet");
                return;
            }

            // check bad values/can't login
            if (username == null || password == null || !LoginServlet.userDB.containsKey(username) ||
                    !password.equals(LoginServlet.userDB.get(username))) {
                request.setAttribute("loginFailed", true);
                request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(request, response);
            }
            // login is successful
            else {
                session.setAttribute("username", username);
                if ("Taiye".equals(username)) {  // replace "Taiye" with your username
                    session.setAttribute("role", "admin");
                } else {
                    session.setAttribute("role", "user");
                }
                request.changeSessionId(); // protects against session fixation attacks
                response.sendRedirect("TicketServlet");
            }

        }
    }
}
