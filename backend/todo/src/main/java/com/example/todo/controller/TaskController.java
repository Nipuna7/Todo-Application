package com.example.todo.controller;

import com.example.todo.model.TaskModel;
import com.example.todo.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskModel task) {
        try {
            TaskModel savedTask = taskService.addTask(task);
            return ResponseEntity.ok("Task added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding task");
        }
    }

    // Get latest 5 tasks
    @GetMapping("/latest")
    public ResponseEntity<List<TaskModel>> getLatestTasks() {
        List<TaskModel> tasks = taskService.getLatestTasks();
        return ResponseEntity.ok(tasks);
    }

    // Mark task as done
    @PutMapping("/done/{id}")
    public ResponseEntity<?> markTaskAsDone(@PathVariable Long id) {
        try {
            TaskModel task = taskService.markTaskAsDone(id);
            return ResponseEntity.ok("Task marked as done!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete task
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
