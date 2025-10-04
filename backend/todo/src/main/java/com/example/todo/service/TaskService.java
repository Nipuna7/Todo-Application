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

    // add task
    public TaskModel addTask(TaskModel task) {
        validateTask(task);
        return taskRepo.save(task);
    }

    // get latest 5 tasks
    public List<TaskModel> getLatestTasks() {
        return taskRepo.findLatest5Tasks();
    }

    // mark task as done
    public TaskModel markTaskAsDone(Long id) {
        TaskModel task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDone(true);
        return taskRepo.save(task);
    }

    // delete task
    public void deleteTask(Long id) {
        if (!taskRepo.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepo.deleteById(id);
    }

    //use SRP and OCP principle
    private void validateTask(TaskModel task){
        if(task == null){
            throw new IllegalArgumentException("Task cannot be null");
        }
        if(task.getTitle() == null || task.getTitle().trim().isEmpty()){
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        if (task.getDescription() == null || task.getDescription().trim().isEmpty()){
            throw new IllegalArgumentException("Description cannot be empty");
        }
    }


}
