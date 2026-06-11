package com.alpefesekerci.trello_clone_app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {

    @NotBlank(message = "Board adı boş olamaz")
    private String name;

    private String description;
}
