package com.alpefesekerci.trello_clone_app.service;

import com.alpefesekerci.trello_clone_app.dto.request.TaskRequest;
import com.alpefesekerci.trello_clone_app.dto.response.TaskResponse;
import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);

    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);
}
