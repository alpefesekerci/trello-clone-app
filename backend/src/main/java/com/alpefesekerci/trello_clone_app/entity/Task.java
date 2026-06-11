package com.alpefesekerci.trello_clone_app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer position;

    /**
     * N+1 sorgu problemini ve performans darboğazlarını önlemek için LAZY fetch stratejisi kullanılmıştır.
     * Sadece task.getBoardList() çağrıldığında liste verisi veritabanından çekilir.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_list_id", nullable = false)
    private BoardList boardList;
}
