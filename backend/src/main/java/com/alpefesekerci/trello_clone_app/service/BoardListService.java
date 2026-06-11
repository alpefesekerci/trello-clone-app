package com.alpefesekerci.trello_clone_app.service;

import com.alpefesekerci.trello_clone_app.dto.request.BoardListRequest;
import com.alpefesekerci.trello_clone_app.dto.response.BoardListResponse;

import java.util.List;

public interface BoardListService {

    BoardListResponse createList(Long boardId, BoardListRequest request);

    List<BoardListResponse> getListsByBoardId(Long boardId);

    BoardListResponse getListById(Long id);

    BoardListResponse updateList(Long id, BoardListRequest request);

    void deleteList(Long id);
}
