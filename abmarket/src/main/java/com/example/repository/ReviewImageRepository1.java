package com.example.repository;

import com.example.entity.ReviewImageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository1 extends JpaRepository<ReviewImageEntity, Long> {

}
