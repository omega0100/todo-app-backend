// src/main/java/com/todoapp/repository/TodoListRepository.java
package com.todoapp.repository;

import com.todoapp.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> { }
