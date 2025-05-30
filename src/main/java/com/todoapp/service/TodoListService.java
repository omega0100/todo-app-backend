// src/main/java/com/todoapp/service/TodoListService.java
package com.todoapp.service;

import com.todoapp.exception.ResourceNotFoundException;
import com.todoapp.model.TodoList;
import com.todoapp.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoListService {
  private final TodoListRepository repo;

  public List<TodoList> findAll() {
    return repo.findAll();
  }

  public TodoList findById(Long id) {
    return repo.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("List not found: " + id));
  }

  public TodoList create(TodoList input) {
    return repo.save(input);
  }

  public TodoList update(Long id, TodoList changes) {
    return repo.findById(id)
      .map(existing -> {
        existing.setName(changes.getName());
        existing.setDescription(changes.getDescription());
        return repo.save(existing);
      })
      .orElseThrow(() -> new ResourceNotFoundException("List not found: " + id));
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) {
      throw new ResourceNotFoundException("List not found: " + id);
    }
    repo.deleteById(id);
  }
}
