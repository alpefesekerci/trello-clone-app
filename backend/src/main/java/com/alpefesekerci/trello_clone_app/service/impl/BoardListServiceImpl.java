package com.alpefesekerci.trello_clone_app.service.impl;

import com.alpefesekerci.trello_clone_app.dto.request.BoardListRequest;
import com.alpefesekerci.trello_clone_app.dto.response.BoardListResponse;
import com.alpefesekerci.trello_clone_app.dto.response.TaskResponse;
import com.alpefesekerci.trello_clone_app.entity.Board;
import com.alpefesekerci.trello_clone_app.entity.BoardList;
import com.alpefesekerci.trello_clone_app.entity.Task;
import com.alpefesekerci.trello_clone_app.exception.ResourceNotFoundException;
import com.alpefesekerci.trello_clone_app.repository.BoardListRepository;
import com.alpefesekerci.trello_clone_app.repository.BoardRepository;
import com.alpefesekerci.trello_clone_app.service.BoardListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Pano listeleri (Kolonlar) için iş mantığını yürüten servis katmanı.
 * Bu sınıf sadece listeleri değil, aynı zamanda listelerin bağlı olduğu Panoların (Board)
 * geçerliliğini de kontrol ederek veri bütünlüğünü sağlar.
 */
@Service
@RequiredArgsConstructor
public class BoardListServiceImpl implements BoardListService {

    private final BoardListRepository boardListRepository;
    private final BoardRepository boardRepository;

    @Override
    public BoardListResponse createList(Long boardId, BoardListRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board", boardId));

        BoardList boardList = BoardList.builder()
                .name(request.getName())
                .position(request.getPosition())
                .board(board)
                .build();

        BoardList savedList = boardListRepository.save(boardList);
        return convertToResponse(savedList);
    }

    @Override
    public List<BoardListResponse> getListsByBoardId(Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new ResourceNotFoundException("Board", boardId);
        }

        return boardListRepository.findByBoardIdOrderByPositionAsc(boardId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BoardListResponse getListById(Long id) {
        BoardList boardList = boardListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liste", id));

        return convertToResponse(boardList);
    }

    @Override
    public BoardListResponse updateList(Long id, BoardListRequest request) {
        BoardList boardList = boardListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liste", id));

        boardList.setName(request.getName());
        boardList.setPosition(request.getPosition());

        BoardList updatedList = boardListRepository.save(boardList);
        return convertToResponse(updatedList);
    }

    @Override
    public void deleteList(Long id) {
        BoardList boardList = boardListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liste", id));

        boardListRepository.delete(boardList);
    }

    // --- MAPPING (DÖNÜŞÜM) METOTLARI ---

    /**
     * Veritabanı modelini güvenli Response DTO'ya çevirir.
     * Sonsuz döngü (Infinite Recursion) oluşmasını engellemek için, listenin içindeki
     * görevleri Entity yerine DTO (TaskResponse) olarak haritalar.
     */
    private BoardListResponse convertToResponse(BoardList boardList) {
        List<TaskResponse> taskResponses = boardList.getTasks().stream()
                .map(this::convertTaskToResponse)
                .collect(Collectors.toList());

        return BoardListResponse.builder()
                .id(boardList.getId())
                .name(boardList.getName())
                .position(boardList.getPosition())
                .tasks(taskResponses)
                .build();
    }

    /**
     * Görev nesnelerini Controller'a taşınabilir hale getirir.
     */
    private TaskResponse convertTaskToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .position(task.getPosition())
                .build();
    }
}
