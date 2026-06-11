package com.alpefesekerci.trello_clone_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Panonun oluşturulma zamanını denetim amacıyla tutuyoruz.
    private LocalDateTime createdAt;

    /** Pano silindiğinde veya listeden çıkarıldığında
    * altındaki tüm listelerin de veritabanından temizlenmesini garanti eder.
    * UI tarafında sürükle-bırak işlemleri için
    * listeler her zaman 'position' değerine göre sıralı getirilir.
     */
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    @Builder.Default
    private List<BoardList> lists = new ArrayList<>();

    /**
     * Entity veritabanına ilk kez kaydedilmeden hemen önce tetiklenir.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
