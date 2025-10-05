package fr.mns.todolist.persistence.repository;

import fr.mns.todolist.persistence.model.Task;
import fr.mns.todolist.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOwner(User owner);
}