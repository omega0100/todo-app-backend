package com.todoapp.dto;

import com.todoapp.model.Todo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TodoDto {
  private Long id;
  private String title;
  private String description;
  private boolean completed;
  private String priority;
  private LocalDateTime dueDate;
  private List<String> tags;

  public static TodoDto fromEntity(Todo t) {
    TodoDto dto = new TodoDto();
    dto.id          = t.getId();
    dto.title       = t.getTitle();
    dto.description = t.getDescription();
    dto.completed   = t.isCompleted();
    dto.priority    = t.getPriority().name();
    dto.dueDate     = t.getDueDate();
    dto.tags        = t.getTags();
    return dto;
  }
}
