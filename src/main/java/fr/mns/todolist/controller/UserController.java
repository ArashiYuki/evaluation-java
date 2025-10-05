package fr.mns.todolist.controller;

import fr.mns.todolist.persistence.model.Task;
import fr.mns.todolist.persistence.model.User;
import fr.mns.todolist.service.TaskService;
import fr.mns.todolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping({"", "/"})
    public String list(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        List<Task> tasks = taskService.findAllByOwner(user);
        model.addAttribute("tasks", tasks);
        return "users/details";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
         userService.deleteUserById(id);
        return "redirect:/users";
    }
}
