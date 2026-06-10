package com.alpefesekerci.trello_clone_app.dto.request;

import com.alpefesekerci.trello_clone_app.entity.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
}
