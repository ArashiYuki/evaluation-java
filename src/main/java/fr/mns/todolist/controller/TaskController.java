package fr.mns.todolist.controller;

import fr.mns.todolist.persistence.model.Task;
import fr.mns.todolist.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping({"", "/"})
    public String list(Model model) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
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
