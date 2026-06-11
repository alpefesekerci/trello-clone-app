package com.alpefesekerci.trello_clone_app.controller;

import com.alpefesekerci.trello_clone_app.dto.request.BoardRequest;
import com.alpefesekerci.trello_clone_app.dto.response.BoardResponse;
import com.alpefesekerci.trello_clone_app.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Pano (Board) işlemleri için dış dünyaya açılan REST API Controller'ı.
 * İstemciden gelen HTTP isteklerini karşılar, veriyi doğrular
 * ve iş mantığını yürütmesi için Service katmanına devreder.
 */

@RestController
@RequestMapping("/api/boards") // REST standartları gereği rotalar çoğul isimlerle (boards) adlandırılır.
@RequiredArgsConstructor
public class BoardController {

    // Bağımlılığın somut sınıfa (Impl) değil arayüze (Interface) yapılması gevşek bağlılığı (Loose Coupling) sağlar.
    // 'final' kullanımı ile constructor injection yapılarak sınıf thread-safe hale getirilir.
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardRequest request) {
        BoardResponse response = boardService.createBoard(request);
        // Yeni bir kaynak (resource) oluşturulduğunda standart 200 OK yerine 201 CREATED dönmek best-practice'tir.
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        List<BoardResponse> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long id) {
        BoardResponse board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @Valid @RequestBody BoardRequest request) {
        BoardResponse updatedBoard = boardService.updateBoard(id, request);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        // İşlem başarılı olduğunda geriye bir body (veri) dönülmediği için istemciye
        // "İşlem başarılı ama içerik yok" anlamına gelen 204 durumu dönülür.
        return ResponseEntity.noContent().build();
    }
}
