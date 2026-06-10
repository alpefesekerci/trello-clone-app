package com.alpefesekerci.trello_clone_app.dto.request;

import com.alpefesekerci.trello_clone_app.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    @NotBlank(message = "Başlık boş olamaz")
    private String title;

    private String description;

    @NotNull(message = "Durum boş olamaz")
    private TaskStatus status;
}

