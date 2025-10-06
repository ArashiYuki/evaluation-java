package fr.mns.todolist.controller;

import fr.mns.todolist.persistence.model.User;
import fr.mns.todolist.persistence.repository.UserRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserRepository userRepository;

    public GlobalControllerAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser(Principal principal) {
        if (principal == null) return null;

        return userRepository.findByUsername(principal.getName())
                .orElse(null);
    }
}