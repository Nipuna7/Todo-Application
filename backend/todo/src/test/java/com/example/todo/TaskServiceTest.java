package com.example.todo;

import com.example.todo.model.TaskModel;
import com.example.todo.repo.TaskRepo;
import com.example.todo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskService taskService;

    // -------------------- addTask Tests --------------------
    @Test
    void testAddTask_Success() {
        TaskModel task = new TaskModel();
        task.setTitle("Test Task");
        task.setDescription("This is a test task");

        TaskModel savedTask = new TaskModel(1L, "Test Task", "This is a test task", false, task.getCreatedAt());

        when(taskRepo.save(task)).thenReturn(savedTask);

        TaskModel result = taskService.addTask(task);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Task", result.getTitle());
        assertFalse(result.isDone());
        assertNotNull(result.getCreatedAt());

        verify(taskRepo, times(1)).save(task);
    }

    @Test
    void testAddTask_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask(null);
        });
        assertEquals("Task cannot be null", exception.getMessage());
    }

    @Test
    void testAddTask_EmptyTitle() {
        TaskModel task = new TaskModel();
        task.setTitle(" ");
        task.setDescription("Some description");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask(task);
        });
        assertEquals("Task title cannot be empty", exception.getMessage());
    }

    @Test
    void testAddTask_EmptyDescription() {
        TaskModel task = new TaskModel();
        task.setTitle("Title");
        task.setDescription(" ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask(task);
        });
        assertEquals("Description cannot be empty", exception.getMessage());
    }



    // -------------------- deleteTask Tests --------------------
    @Test
    void testDeleteTask_Success() {
        when(taskRepo.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepo).deleteById(1L);

        assertDoesNotThrow(() -> taskService.deleteTask(1L));

        verify(taskRepo, times(1)).existsById(1L);
        verify(taskRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepo.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.deleteTask(1L);
        });
        assertEquals("Task not found", exception.getMessage());
    }
}
