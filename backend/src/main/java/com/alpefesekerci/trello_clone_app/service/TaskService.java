package com.alpefesekerci.trello_clone_app.service;

import com.alpefesekerci.trello_clone_app.dto.request.MoveTaskRequest;
import com.alpefesekerci.trello_clone_app.dto.request.TaskRequest;
import com.alpefesekerci.trello_clone_app.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(Long boardListId, TaskRequest request);

    List<TaskResponse> getTasksByListId(Long boardListId);

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, TaskRequest request);

    TaskResponse moveTask(Long id, MoveTaskRequest request);

    void deleteTask(Long id);
}
