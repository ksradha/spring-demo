package com.example.springdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_image_t")
public class Image {

    @Id
    @GeneratedValue
    @Column(name="image_id")
    private int imageId;
    @Column(name="image_name")
    private String imageName;
    @Column(name="image_type")
    private String imageType;
    @Lob
    @Column(name="image_data")
    private byte[] imageData;
    @Column(name="user_name")
    private String userName;

}
