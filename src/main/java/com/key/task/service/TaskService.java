package com.key.task.service;

import com.key.task.model.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TaskService {
    Page<TaskDto> getAllTasks(Pageable pageable);
    Page<TaskDto> getAllTasksToDo(Pageable pageable);
    TaskDto getTaskById(Long id);
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTaskStatus(Long id);
}
