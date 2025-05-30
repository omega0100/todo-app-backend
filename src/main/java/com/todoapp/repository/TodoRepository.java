package com.todoapp.repository;

import com.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("SELECT t FROM Todo t WHERE t.dueDate <= :cutoff AND t.completed = false ORDER BY t.dueDate ASC")
    List<Todo> findUpcomingTasks(@Param("cutoff") LocalDateTime cutoff);

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.completed = true")
    long countCompletedTasks();

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.completed = false")
    long countPendingTasks();

    @Query("SELECT t.priority, COUNT(t) FROM Todo t WHERE t.completed = false GROUP BY t.priority")
    List<Object[]> countPendingByPriority();
}
