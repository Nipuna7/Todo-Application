package com.example.todo.repo;

import com.example.todo.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<TaskModel,Long> {
}
