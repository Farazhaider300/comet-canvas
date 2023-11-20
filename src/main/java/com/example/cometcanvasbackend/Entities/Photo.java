package com.example.cometcanvasbackend.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PHOTO")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "DESCRIPTION")
    private String description;

    @Lob
    @JsonIgnore
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "USER_ID") // Name of the foreign key column in the PHOTO table
    @JsonIgnore // Exclude user information from JSON output
    private Users user;

    public Photo() {

    }

    public Photo(String title, String author, String description, byte[] imageData) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageData = imageData;
    }

    public Photo(Long id, String base64Image) {
        this.id = id;
        this.imageData = base64Image.getBytes();
    }
}
