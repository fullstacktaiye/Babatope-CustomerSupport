package com.example.babatopecustomersupport.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public ModelAndView showSignup() {
        // Forward the request to the signup page
        return new ModelAndView("signup");
    }
}
