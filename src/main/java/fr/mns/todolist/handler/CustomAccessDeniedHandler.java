package fr.mns.todolist.handler;

import fr.mns.todolist.persistence.model.User;
import fr.mns.todolist.persistence.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final UserRepository userRepository;

    public CustomAccessDeniedHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            Optional<User> user = userRepository.findByUsername(principal.getName());

            if (user.isPresent()) {
                Long userId = user.get().getId();
                response.sendRedirect("/users/" + userId);
                return;
            }
        }

        // Sinon redirection vers login
        response.sendRedirect("/login");
    }
}