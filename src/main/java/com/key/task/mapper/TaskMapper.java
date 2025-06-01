package com.key.task.mapper;

import com.key.task.entity.Task;
import com.key.task.model.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task product);

    Task toEntity(TaskDto productDto);
}