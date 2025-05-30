// src/main/java/com/todoapp/controller/DashboardController.java
package com.todoapp.controller;

import com.todoapp.dto.DashboardDto;
import com.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
  private final TodoService svc;

  @GetMapping
  public DashboardDto dashboard() {
    return svc.dashboard();
  }
}
