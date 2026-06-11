package com.alpefesekerci.trello_clone_app.controller;

import com.alpefesekerci.trello_clone_app.dto.request.BoardListRequest;
import com.alpefesekerci.trello_clone_app.dto.response.BoardListResponse;
import com.alpefesekerci.trello_clone_app.service.BoardListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Liste (BoardList) işlemleri için dış dünyaya açılan REST API Controller'ı.
 */
@RestController
@RequiredArgsConstructor
public class BoardListController {

    private final BoardListService boardListService;

    @PostMapping("/api/boards/{boardId}/lists")
    public ResponseEntity<BoardListResponse> createList(@PathVariable Long boardId,
                                                        @Valid @RequestBody BoardListRequest request) {
        BoardListResponse response = boardListService.createList(boardId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/boards/{boardId}/lists")
    public ResponseEntity<List<BoardListResponse>> getListsByBoardId(@PathVariable Long boardId) {
        List<BoardListResponse> lists = boardListService.getListsByBoardId(boardId);
        return ResponseEntity.ok(lists);
    }

    @PutMapping("/api/lists/{id}")
    public ResponseEntity<BoardListResponse> updateList(@PathVariable Long id,
                                                        @Valid @RequestBody BoardListRequest request) {
        BoardListResponse updatedList = boardListService.updateList(id, request);
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/api/lists/{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long id) {
        boardListService.deleteList(id);
        return ResponseEntity.noContent().build();
    }
}
