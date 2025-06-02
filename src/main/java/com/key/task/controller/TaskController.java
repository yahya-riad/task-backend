package com.key.task.controller;

import com.key.task.model.TaskDto;
import com.key.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;


    @GetMapping
    public Page<TaskDto> getTasks(
            @PageableDefault(size = 10, sort = "label", direction = Sort.Direction.ASC) Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @GetMapping("/to-do")
    public Page<TaskDto> getTasksNotCompleted(
            @PageableDefault(size = 10, sort = "label", direction = Sort.Direction.ASC) Pageable pageable) {
        return taskService.getAllTasksToDo(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.createTask(taskDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id) {
        TaskDto updatedTask = taskService.updateTaskStatus(id);
        return ResponseEntity.ok(updatedTask);
    }

}
