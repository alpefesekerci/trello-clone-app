package com.alpefesekerci.trello_clone_app.controller;

import com.alpefesekerci.trello_clone_app.dto.request.MoveTaskRequest;
import com.alpefesekerci.trello_clone_app.dto.request.TaskRequest;
import com.alpefesekerci.trello_clone_app.dto.response.TaskResponse;
import com.alpefesekerci.trello_clone_app.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Görev (Kart/Task) işlemleri için API uç noktalarını yöneten Controller sınıfı.
 * BoardListController'da olduğu gibi, bu sınıfta da hiyerarşik (sub-resource) ve
 * düz (flat) URL yapıları, kaynakların bağımlılık durumuna göre bilinçli olarak harmanlanmıştır.
 */
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/api/lists/{listId}/tasks")
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long listId,
                                                   @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(listId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/lists/{listId}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByListId(@PathVariable Long listId) {
        List<TaskResponse> tasks = taskService.getTasksByListId(listId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/api/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @Valid @RequestBody TaskRequest request) {
        TaskResponse updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/api/tasks/{id}/move")
    public ResponseEntity<TaskResponse> moveTask(@PathVariable Long id,
                                                 @Valid @RequestBody MoveTaskRequest request) {
        TaskResponse movedTask = taskService.moveTask(id, request);
        return ResponseEntity.ok(movedTask);
    }

    @DeleteMapping("/api/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
