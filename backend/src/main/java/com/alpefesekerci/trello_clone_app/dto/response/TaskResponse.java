package com.alpefesekerci.trello_clone_app.dto.response;

import com.alpefesekerci.trello_clone_app.entity.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
}
