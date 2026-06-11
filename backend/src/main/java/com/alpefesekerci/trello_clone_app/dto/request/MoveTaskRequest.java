package com.alpefesekerci.trello_clone_app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveTaskRequest {

    @NotNull(message = "Hedef liste ID boş olamaz")
    private Long targetListId;

    @NotNull(message = "Pozisyon boş olamaz")
    private Integer position;
}
