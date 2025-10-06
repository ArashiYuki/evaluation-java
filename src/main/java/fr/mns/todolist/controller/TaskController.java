package fr.mns.todolist.controller;

import fr.mns.todolist.persistence.model.Task;
import fr.mns.todolist.persistence.model.User;
import fr.mns.todolist.persistence.repository.TaskRepository;
import fr.mns.todolist.persistence.repository.UserRepository;
import fr.mns.todolist.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, UserRepository userRepository, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping({"", "/"})
    public String list(Model model) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/add")
    public String showAddTaskForm() {
        return "tasks/add";
    }

    @PostMapping("/add")
    public String addTask(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) boolean completed,
            Principal principal, RedirectAttributes redirectAttributes) {

        String username = principal.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Utilisateur non trouvé.");
            return "redirect:/login";
        }

        User user = optionalUser.get();

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);
        task.setOwner(user);

        taskRepository.save(task);

        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        return "tasks/details";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/completion")
    public String updateCompleted(@PathVariable Long id) {
        taskService.toggleCompletion(id);
        return "redirect:/tasks/" + id;
    }
}
