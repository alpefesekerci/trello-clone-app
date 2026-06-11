package com.alpefesekerci.trello_clone_app.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BoardListResponse {
    private Long id;
    private String name;
    private Integer position;
    private List<TaskResponse> tasks;
}
