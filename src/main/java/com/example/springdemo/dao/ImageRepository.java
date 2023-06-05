package com.example.springdemo.dao;

import com.example.springdemo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	Optional<Image> findByImageNameAndUserName(String fileName, String userName);
}
