package com.key.task.service;

import com.key.task.entity.Task;
import com.key.task.mapper.TaskMapper;
import com.key.task.model.TaskDto;
import com.key.task.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        task = Task.builder()
                .id(1L)
                .label("Label Task")
                .description("Test Task")
                .isCompleted(false)
                .build();

        taskDto = TaskDto.builder()
                .id(1L)
                .label("Label Task")
                .description("Test Task")
                .isCompleted(false)
                .build();
    }

    @Test
    void shouldReturnAllTasks() {
        var pageable = PageRequest.of(0, 10);
        var taskPage = new PageImpl<>(List.of(task));
        when(taskRepository.findAll(pageable)).thenReturn(taskPage);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        var result = taskService.getAllTasks(pageable);

        assertThat(result.getContent()).containsExactly(taskDto);
        verify(taskRepository).findAll(pageable);
    }

    @Test
    void shouldReturnAllTasksToDo() {
        var pageable = PageRequest.of(0, 10);
        var taskPage = new PageImpl<>(List.of(task));
        when(taskRepository.findAllByIsCompletedFalse(pageable)).thenReturn(taskPage);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        var result = taskService.getAllTasksToDo(pageable);

        assertThat(result.getContent()).containsExactly(taskDto);
        verify(taskRepository).findAllByIsCompletedFalse(pageable);
    }

    @Test
    void shouldReturnTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        var result = taskService.getTaskById(1L);

        assertThat(result).isEqualTo(taskDto);
        verify(taskRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenTaskNotFoundById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    void shouldCreateTask() {
        when(taskMapper.toEntity(taskDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        var result = taskService.createTask(taskDto);

        assertThat(result).isEqualTo(taskDto);
        verify(taskRepository).save(task);
    }

    @Test
    void shouldUpdateTaskStatus() {

        var completedTask = Task.builder()
                .id(1L)
                .label("Label Task")
                .description("Test Task")
                .isCompleted(true)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(completedTask);
        when(taskMapper.toDto(completedTask)).thenReturn(taskDto);

        var result = taskService.updateTaskStatus(1L);

        assertThat(result).isEqualTo(taskDto);
        verify(taskRepository).save(any(Task.class));
    }
}
