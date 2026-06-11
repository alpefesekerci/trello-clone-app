package com.alpefesekerci.trello_clone_app.service;

import com.alpefesekerci.trello_clone_app.dto.request.BoardRequest;
import com.alpefesekerci.trello_clone_app.dto.response.BoardResponse;

import java.util.List;

public interface BoardService {

    BoardResponse createBoard(BoardRequest request);

    List<BoardResponse> getAllBoards();

    BoardResponse getBoardById(Long id);

    BoardResponse updateBoard(Long id, BoardRequest request);

    void deleteBoard(Long id);
}
