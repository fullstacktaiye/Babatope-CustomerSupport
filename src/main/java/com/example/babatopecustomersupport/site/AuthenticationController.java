package com.example.babatopecustomersupport.site;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Hashtable;
import java.util.Map;

@Controller
public class AuthenticationController {
    public static final Map<String, String> userDB = new Hashtable<>();
    static {
        userDB.put("Taiye", "admin123");
        userDB.put("JJ", "password123");
        userDB.put("Song", "whiteseas");
    }

    @RequestMapping("logout")
    public View logout(HttpSession session) {
        session.invalidate();

        return new RedirectView("login", true, false);
    }

    @GetMapping("login")
    public ModelAndView loginForm(Model model, HttpSession session) {
        if (session.getAttribute("username") != null) {
            return new ModelAndView(new RedirectView("TicketServlet", true, false));
        }
        model.addAttribute("loginFailed", false);
        return new ModelAndView("login", "loginForm", new LoginForm());
    }



    @PostMapping("login")
    public ModelAndView loginCheck(@ModelAttribute("loginForm")LoginForm form,
                                   Model model,
                                   HttpSession session,
                                   HttpServletRequest request) {
        String action = form.getAction();
        String username = form.getUsername();
        String password = form.getPassword();

        if ("signup".equals(action)) {
            if (username != null && password != null && !userDB.containsKey(username)) {
                userDB.put(username, password);
                session.setAttribute("username", username);
                request.changeSessionId();
                return new ModelAndView(new RedirectView("TicketServlet", true, false));
            } else {
                model.addAttribute("signupFailed", true);
                model.addAttribute("loginForm", form);
                return new ModelAndView("signup");
            }
        } else {
            if (session.getAttribute("username") != null) {
                return new ModelAndView(new RedirectView("TicketServlet", true, false));
            }

            if (username == null || password == null || !userDB.containsKey(username) ||
                    !password.equals(userDB.get(username))) {
                model.addAttribute("loginFailed", true);
                model.addAttribute("loginForm", form);
                return new ModelAndView("login");
            }

            session.setAttribute("username", username);
            if ("Taiye".equals(username)) {
                session.setAttribute("role", "admin");
            } else {
                session.setAttribute("role", "user");
            }
            request.changeSessionId();
            return new ModelAndView(new RedirectView("TicketServlet", true, false));
        }
    }

    public static class LoginForm {
        private String action;
        private String username;
        private String password;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
