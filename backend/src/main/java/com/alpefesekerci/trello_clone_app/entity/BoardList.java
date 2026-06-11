package com.alpefesekerci.trello_clone_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_lists") // Veritabanı isimlendirme standartları (snake_case ve çoğul) için özel tablo adı belirlendi.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Sürükle-bırak mantığında listelerin ekrandaki soldan sağa dizilim sırasını belirleyen hayati alan.
    @Column(nullable = false)
    private Integer position;

    /**
     * MİMARİ NOT: N+1 Sorgu Problemini Önleme
     * FetchType.LAZY kullanılarak, bir liste veritabanından çekildiğinde bağlı olduğu tüm Panonun (Board)
     * gereksiz yere çekilmesi (EAGER) engellenmiştir. Sadece boardList.getBoard() çağrıldığında pano sorgulanır.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "boardList", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    @Builder.Default // Builder deseni kullanılırken listenin null kalmasını engeller, boş liste ile başlatır.
    private List<Task> tasks = new ArrayList<>();
}
