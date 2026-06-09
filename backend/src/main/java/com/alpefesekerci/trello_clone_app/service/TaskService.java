package com.alpefesekerci.trello_clone_app.service;

import com.alpefesekerci.trello_clone_app.entity.Task;
import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task updateTask(Long id, Task taskDetails);

    void deleteTask(Long id);
}
