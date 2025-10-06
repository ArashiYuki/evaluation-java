package fr.mns.todolist.controller;

import fr.mns.todolist.persistence.model.Task;
import fr.mns.todolist.persistence.model.User;
import fr.mns.todolist.persistence.repository.UserRepository;
import fr.mns.todolist.service.TaskService;
import fr.mns.todolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TaskService taskService;
    private final UserRepository userRepository;

    public UserController(UserService userService, TaskService taskService, UserRepository userRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/"})
    public String list(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> connectedUser = userRepository.findByUsername(username);

        if (connectedUser.isEmpty()) {;
            return "redirect:/login";
        }

        User user = userService.findById(id);
        model.addAttribute("user", user);
        List<Task> tasks = taskService.findAllByOwner(user);
        model.addAttribute("tasks", tasks);

        boolean isAdmin = connectedUser.get().getRole().equals("ROLE_ADMIN");
        boolean isSelf = connectedUser.get().getId().equals(user.getId());

        if (isSelf) {
            model.addAttribute("self", true);
        } else {
            model.addAttribute("self", false);
            if (!isAdmin) {
                return "redirect:/users/" + connectedUser.get().getId();
            }
        }
        return "users/details";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
         userService.deleteUserById(id);
        return "redirect:/users";
    }
}
