package com.key.task.model;

import lombok.Builder;

@Builder
public record TaskDto(
        Long id,
        String label,
        String description,
        Boolean isCompleted
        ) {
}
