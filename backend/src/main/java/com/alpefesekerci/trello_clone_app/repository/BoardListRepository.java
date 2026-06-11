package com.alpefesekerci.trello_clone_app.repository;

import com.alpefesekerci.trello_clone_app.entity.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {

    List<BoardList> findByBoardIdOrderByPositionAsc(Long boardId);
}
