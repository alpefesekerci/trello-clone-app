package com.alpefesekerci.trello_clone_app.service.impl;

import com.alpefesekerci.trello_clone_app.dto.request.MoveTaskRequest;
import com.alpefesekerci.trello_clone_app.dto.request.TaskRequest;
import com.alpefesekerci.trello_clone_app.dto.response.TaskResponse;
import com.alpefesekerci.trello_clone_app.entity.BoardList;
import com.alpefesekerci.trello_clone_app.entity.Task;
import com.alpefesekerci.trello_clone_app.exception.ResourceNotFoundException;
import com.alpefesekerci.trello_clone_app.repository.BoardListRepository;
import com.alpefesekerci.trello_clone_app.repository.TaskRepository;
import com.alpefesekerci.trello_clone_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    // Hem görevlerin kendisini yönetmek hem de eklenecekleri/taşınacakları
    // listeleri doğrulamak için her iki repository de enjekte edilmiştir.
    private final TaskRepository taskRepository;
    private final BoardListRepository boardListRepository;

    @Override
    public TaskResponse createTask(Long boardListId, TaskRequest request) {
        BoardList boardList = boardListRepository.findById(boardListId)
                .orElseThrow(() -> new ResourceNotFoundException("Liste", boardListId));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .position(request.getPosition())
                .boardList(boardList)
                .build();

        Task savedTask = taskRepository.save(task);
        return convertToResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getTasksByListId(Long boardListId) {
        if (!boardListRepository.existsById(boardListId)) {
            throw new ResourceNotFoundException("Liste", boardListId);
        }

        return taskRepository.findByBoardListIdOrderByPositionAsc(boardListId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Görev", id));

        return convertToResponse(task);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Görev", id));

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setPosition(request.getPosition());

        Task updatedTask = taskRepository.save(existingTask);
        return convertToResponse(updatedTask);
    }

    @Override
    public TaskResponse moveTask(Long id, MoveTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Görev", id));

        BoardList targetList = boardListRepository.findById(request.getTargetListId())
                .orElseThrow(() -> new ResourceNotFoundException("Hedef liste", request.getTargetListId()));

        task.setBoardList(targetList);
        task.setPosition(request.getPosition());

        Task movedTask = taskRepository.save(task);
        return convertToResponse(movedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Görev", id));

        taskRepository.delete(existingTask);
    }

    // --- MAPPING (DÖNÜŞÜM) METOTLARI ---

    /**
     * Task Entity'sini DTO'ya dönüştürür.
     * Hiyerarşinin son noktası olduğu için iç içe (nested) dönüşüm metotları çağırmasına gerek yoktur.
     */
    private TaskResponse convertToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .position(task.getPosition())
                .build();
    }
}
