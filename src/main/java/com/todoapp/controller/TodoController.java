// src/main/java/com/todoapp/controller/TodoController.java
package com.todoapp.controller;

import com.todoapp.dto.TodoDto;
import com.todoapp.model.Todo;
import com.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
  private final TodoService svc;

  @GetMapping
  public List<TodoDto> all() {
    return svc.findAll();
  }

  @GetMapping("/{id}")
  public TodoDto one(@PathVariable Long id) {
    return svc.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TodoDto create(@RequestBody Todo input) {
    return svc.create(input);
  }

  @PutMapping("/{id}")
  public TodoDto update(@PathVariable Long id, @RequestBody Todo changes) {
    return svc.update(id, changes);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    svc.delete(id);
  }
}
