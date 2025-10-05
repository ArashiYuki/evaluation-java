package fr.mns.todolist.service;

import fr.mns.todolist.persistence.model.Task;
import fr.mns.todolist.persistence.model.User;
import fr.mns.todolist.persistence.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> findAllByOwner(User user) {
        return taskRepository.findAllByOwner(user);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setOwner(updatedTask.getOwner());
                    task.setCompleted(updatedTask.getCompleted());
                    return taskRepository.save(task);
                });
    }

    public void toggleCompletion(Long taskId) {
        taskRepository.findById(taskId).ifPresent(task -> {
            task.setCompleted(task.getCompleted() == null || !task.getCompleted());
            taskRepository.save(task);
        });
    }
}
