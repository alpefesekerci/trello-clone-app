package com.alpefesekerci.trello_clone_app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardListRequest {

    @NotBlank(message = "Liste adı boş olamaz")
    private String name;

    @NotNull(message = "Pozisyon boş olamaz")
    private Integer position;
}
