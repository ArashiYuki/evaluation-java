package fr.mns.todolist.controller;

import fr.mns.todolist.persistence.model.User;
import fr.mns.todolist.persistence.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class MainController {
    private final UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public RedirectView root() {
        return new RedirectView("/users");
    }

    @GetMapping("/home")
    public RedirectView home(Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (user.isEmpty()) return new RedirectView("/login");

        if (user.get().getRole().equals("ROLE_ADMIN")) {
            return new RedirectView("/users");
        } else {
            return new RedirectView("/users/" + user.get().getId());
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
