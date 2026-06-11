package com.alpefesekerci.trello_clone_app.service.impl;

import com.alpefesekerci.trello_clone_app.dto.request.BoardRequest;
import com.alpefesekerci.trello_clone_app.dto.response.BoardListResponse;
import com.alpefesekerci.trello_clone_app.dto.response.BoardResponse;
import com.alpefesekerci.trello_clone_app.dto.response.TaskResponse;
import com.alpefesekerci.trello_clone_app.entity.Board;
import com.alpefesekerci.trello_clone_app.entity.BoardList;
import com.alpefesekerci.trello_clone_app.entity.Task;
import com.alpefesekerci.trello_clone_app.exception.ResourceNotFoundException;
import com.alpefesekerci.trello_clone_app.repository.BoardRepository;
import com.alpefesekerci.trello_clone_app.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    // Bağımlılıkların (Dependency) 'final' olarak tanımlanması ve @RequiredArgsConstructor kullanılması,
    // Spring'in Constructor Injection yapmasını zorunlu kılar. Bu da sınıfı test edilebilir ve thread-safe yapar.
    private final BoardRepository boardRepository;

    @Override
    public BoardResponse createBoard(BoardRequest request) {
        Board board = Board.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Board savedBoard = boardRepository.save(board);
        return convertToResponse(savedBoard);
    }

    @Override
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", id));

        return convertToResponse(board);
    }

    @Override
    public BoardResponse updateBoard(Long id, BoardRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", id));

        board.setName(request.getName());
        board.setDescription(request.getDescription());

        Board updatedBoard = boardRepository.save(board);
        return convertToResponse(updatedBoard);
    }

    @Override
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", id));

        boardRepository.delete(board);
    }

    // --- MAPPING (DÖNÜŞÜM) METOTLARI ---

    /**
     * Veritabanı modelini (Board), API yanıt modeline (BoardResponse) dönüştürür.
     * MİMARİ NOT: Infinite Recursion (Sonsuz Döngü) Çözümü.
     * Board -> BoardList -> Task ilişkilerinde nesnelerin birbirini sürekli çağırmasını engellemek için,
     * her alt koleksiyon kendi ilgili DTO sınıfına haritalanır (Map edilir).
     */
    private BoardResponse convertToResponse(Board board) {
        List<BoardListResponse> listResponses = board.getLists().stream()
                .map(this::convertListToResponse)
                .collect(Collectors.toList());

        return BoardResponse.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .createdAt(board.getCreatedAt())
                .lists(listResponses)
                .build();
    }

    /**
     * BoardList Entity'sini DTO'ya dönüştürür.
     */
    private BoardListResponse convertListToResponse(BoardList boardList) {
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
     * Task Entity'sini DTO'ya dönüştürür. (Silsilenin en alt basamağı)
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
