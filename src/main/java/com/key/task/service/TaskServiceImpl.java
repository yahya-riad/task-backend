package com.key.task.service;

import com.key.task.entity.Task;
import com.key.task.mapper.TaskMapper;
import com.key.task.model.TaskDto;
import com.key.task.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    @NonNull private final TaskRepository taskRepository;
    @NonNull private final TaskMapper taskMapper;


    @Override
    public Page<TaskDto> getAllTasks(Pageable pageable) {
        log.info(" get all tasks ");
        return taskRepository.findAll(pageable)
                .map(taskMapper::toDto);
    }

    @Override
    public Page<TaskDto> getAllTasksToDo(Pageable pageable) {
        log.info(" get all tasks to do ");
        return taskRepository.findAllByIsCompletedFalse(pageable)
                .map(taskMapper::toDto);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        log.info(" get task [taskId: {} ]: ", id);
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        requireNonNull(taskDto);
        log.info(" create new task ");
        return saveTask(taskMapper.toEntity(taskDto));
    }

    @Override
    public TaskDto updateTaskStatus(Long id) {
        log.info("Mark task as completed [id: {}]", id);
        var task = taskRepository.findById(id)
                .map(Task::markAsCompleted)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        return saveTask(task);
    }

    private TaskDto saveTask(Task task) {
        var taskEntity = taskRepository.save(task);
        return taskMapper.toDto(taskEntity);
    }
}
