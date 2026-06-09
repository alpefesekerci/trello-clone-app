package com.alpefesekerci.trello_clone_app.service.impl;

import org.springframework.stereotype.Service;
import com.alpefesekerci.trello_clone_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import com.alpefesekerci.trello_clone_app.repository.TaskRepository;
import com.alpefesekerci.trello_clone_app.entity.Task;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TaskServiceImpl implements TaskService {
     private final TaskRepository taskRepository;

     @Override
    public Task createTask(Task task) {
         return taskRepository.save(task);
     }

     @Override
     public List<Task> getAllTasks() {
         return taskRepository.findAll();
     }

     @Override
     public Task getTaskById(Long id) {
         return taskRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Görev bulunamadı! ID: " + id));
     }

     @Override
    public Task updateTask(Long id, Task taskDetails) {
         Task existingTask = taskRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Görev bulunamadı! ID: " + id));
         existingTask.setTitle(taskDetails.getTitle());
         existingTask.setDescription(taskDetails.getDescription());
         existingTask.setStatus(taskDetails.getStatus());
         return taskRepository.save(existingTask);
     }

     @Override
    public void deleteTask(Long id) {
         Task existingTask = taskRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Görev bulunamadı! ID: " + id));

         taskRepository.delete(existingTask);
     }

}
