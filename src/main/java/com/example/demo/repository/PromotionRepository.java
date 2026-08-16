package com.example.demo.repository;

import com.example.demo.entity.JPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<JPromotion, String> {
    Optional<JPromotion> findByName(String name);
}