package com.dankook.cloudcomputing.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", columnDefinition = "MEDIUMTEXT")
    private String image;

    private Long userId;

    @Builder
    public Image(String query, String image, Long userId) {
        this.query = query;
        this.image = image;
        this.userId = userId;
    }

}
