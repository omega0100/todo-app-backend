package com.todoapp.service;

import com.todoapp.dto.DashboardDto;
import com.todoapp.dto.TodoDto;
import com.todoapp.exception.ResourceNotFoundException;
import com.todoapp.model.Todo;
import com.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
  private final TodoRepository repo;

  public List<TodoDto> findAll() {
    return repo.findAll().stream()
               .map(TodoDto::fromEntity)
               .collect(Collectors.toList());
  }

  public TodoDto findById(Long id) {
    return repo.findById(id)
               .map(TodoDto::fromEntity)
               .orElseThrow(() -> new ResourceNotFoundException("Todo not found: " + id));
  }

  public TodoDto create(Todo todo) {
    return TodoDto.fromEntity(repo.save(todo));
  }

  public TodoDto update(Long id, Todo changes) {
    Todo updated = repo.findById(id)
      .map(existing -> {
        existing.setTitle(changes.getTitle());
        existing.setDescription(changes.getDescription());
        existing.setDueDate(changes.getDueDate());
        existing.setCompleted(changes.isCompleted());
        existing.setPriority(changes.getPriority());
        existing.setTags(changes.getTags());
        return repo.save(existing);
      })
      .orElseThrow(() -> new ResourceNotFoundException("Todo not found: " + id));

    return TodoDto.fromEntity(updated);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) {
      throw new ResourceNotFoundException("Todo not found: " + id);
    }
    repo.deleteById(id);
  }

  /**
   * All pending tasks due within the next 7 days.
   */
  public List<TodoDto> upcoming() {
    LocalDateTime cutoff = LocalDateTime.now().plusDays(7);
    return repo.findUpcomingTasks(cutoff).stream()
               .map(TodoDto::fromEntity)
               .collect(Collectors.toList());
  }

  /**
   * Build the dashboard snapshot, grouping only _pending_ tasks by priority.
   */
  public DashboardDto dashboard() {
    long total     = repo.count();
    long completed = repo.countCompletedTasks();
    long pending   = repo.countPendingTasks();
    long overdue   = repo.findAll().stream()
                         .filter(t -> !t.isCompleted() && t.getDueDate().isBefore(LocalDateTime.now()))
                         .count();

    Map<String,Long> byPri = repo.countPendingByPriority().stream()
      .collect(Collectors.toMap(
        row -> ((Todo.Priority)row[0]).name(),
        row -> (Long)row[1]
      ));

    int weeklyProgress = total == 0
      ? 0
      : (int)((completed * 100) / total);

    DashboardDto dto = new DashboardDto();
    dto.setTotalTasks(total);
    dto.setCompletedTasks(completed);
    dto.setPendingTasks(pending);
    dto.setOverdueTasks(overdue);
    dto.setTasksByPriority(byPri);
    dto.setWeeklyProgress(weeklyProgress);
    dto.setUpcomingTasks(upcoming());
    return dto;
  }
}
