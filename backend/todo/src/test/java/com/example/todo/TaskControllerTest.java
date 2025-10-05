package com.example.todo;

import com.example.todo.controller.TaskController;
import com.example.todo.model.TaskModel;
import com.example.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test addTask() success
    @Test
    void testAddTask_Success() {
        // Arrange
        TaskModel task = new TaskModel();
        task.setTitle("Test Task");
        task.setDescription("This is a test task");

        when(taskService.addTask(any(TaskModel.class))).thenReturn(task);

        // Act
        ResponseEntity<?> response = taskController.addTask(task);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task added successfully!", response.getBody());
        verify(taskService, times(1)).addTask(any(TaskModel.class));
    }

    // Test addTask() with invalid input (missing title)
    @Test
    void testAddTask_InvalidInput() {
        // Arrange
        TaskModel task = new TaskModel();
        task.setDescription("Missing title");

        when(taskService.addTask(any(TaskModel.class)))
                .thenThrow(new IllegalArgumentException("Title cannot be empty"));

        // Act
        ResponseEntity<?> response = taskController.addTask(task);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Title cannot be empty", response.getBody());
        verify(taskService, times(1)).addTask(any(TaskModel.class));
    }

    // Test deleteTask() success
    @Test
    void testDeleteTask_Success() {
        // Arrange
        Long taskId = 1L;
        doNothing().when(taskService).deleteTask(taskId);

        // Act
        ResponseEntity<?> response = taskController.deleteTask(taskId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task deleted successfully!", response.getBody());
        verify(taskService, times(1)).deleteTask(taskId);
    }

    // Test deleteTask() failure (task not found)
    @Test
    void testDeleteTask_NotFound() {
        // Arrange
        Long taskId = 1L;
        doThrow(new RuntimeException("Task not found")).when(taskService).deleteTask(taskId);

        // Act
        ResponseEntity<?> response = taskController.deleteTask(taskId);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Task not found", response.getBody());
        verify(taskService, times(1)).deleteTask(taskId);
    }
}
