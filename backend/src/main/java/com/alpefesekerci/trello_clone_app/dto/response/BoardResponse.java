package com.alpefesekerci.trello_clone_app.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class BoardResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<BoardListResponse> lists;
}
