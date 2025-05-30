// src/main/java/com/todoapp/controller/TodoListController.java
package com.todoapp.controller;

import com.todoapp.model.TodoList;
import com.todoapp.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class TodoListController {
  private final TodoListService svc;

  @GetMapping
  public List<TodoList> all() { return svc.findAll(); }

  @GetMapping("/{id}")
  public TodoList one(@PathVariable Long id) { return svc.findById(id); }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TodoList create(@RequestBody TodoList input) {
    return svc.create(input);
  }

  @PutMapping("/{id}")
  public TodoList update(@PathVariable Long id, @RequestBody TodoList changes) {
    return svc.update(id, changes);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    svc.delete(id);
  }
}
