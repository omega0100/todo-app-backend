package com.todoapp.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DashboardDto {
  private long totalTasks;
  private long completedTasks;
  private long pendingTasks;
  private long overdueTasks;
  private int weeklyProgress;         
  private Map<String,Long> tasksByPriority;
  private List<TodoDto> upcomingTasks;
}
