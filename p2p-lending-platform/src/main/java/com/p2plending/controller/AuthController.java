package com.p2plending.controller;

import com.p2plending.entity.Role;
import com.p2plending.entity.User;
import com.p2plending.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Spring MVC Controller (Module 4) that renders JSP views (Module 2)
 * and uses HttpSession (Module 1: Session Tracking) to keep the user
 * logged in across requests - no JWT needed for the server-rendered pages.
 */
@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam Role role,
                            Model model) {
        try {
            userService.register(name, email, password, role);
            model.addAttribute("success", "Registration successful. Please login.");
            return "login";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                         @RequestParam String password,
                         HttpSession session,
                         Model model) {
        Optional<User> userOpt = userService.authenticate(email, password);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }

        User user = userOpt.get();
        session.setAttribute("loggedInUserId", user.getId());
        session.setAttribute("loggedInUserName", user.getName());
        session.setAttribute("loggedInUserRole", user.getRole().name());

        switch (user.getRole()) {
            case BORROWER: return "redirect:/borrower/dashboard";
            case LENDER: return "redirect:/lender/dashboard";
            case ADMIN: return "redirect:/admin/dashboard";
            default: return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
