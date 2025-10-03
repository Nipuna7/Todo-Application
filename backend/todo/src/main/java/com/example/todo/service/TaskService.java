package com.example.todo.service;

import com.example.todo.model.TaskModel;
import com.example.todo.repo.TaskRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    // Add task
    public TaskModel addTask(TaskModel task) {

        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return taskRepo.save(task);
    }

    // Get latest 5 tasks
    public List<TaskModel> getLatestTasks() {
        return taskRepo.findLatest5Tasks();
    }

    // Mark task as done
    public TaskModel markTaskAsDone(Long id) {
        TaskModel task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDone(true);
        return taskRepo.save(task);
    }

    // Delete task
    public void deleteTask(Long id) {
        if (!taskRepo.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepo.deleteById(id);
    }

}
