package com.example.todo.repo;

import com.example.todo.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<TaskModel,Long> {

    @Query(value = "SELECT * FROM task ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<TaskModel> findLatest5Tasks();
}
