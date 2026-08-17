package com.example.demo.repository;

import com.example.demo.entity.JPromotion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<JPromotion, String> {
  Optional<JPromotion> findByName(String name);

  List<JPromotion> findByNameContainingIgnoreCase(String name);
}
