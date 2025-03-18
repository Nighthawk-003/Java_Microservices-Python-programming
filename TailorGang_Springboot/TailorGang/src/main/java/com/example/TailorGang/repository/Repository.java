package com.example.TailorGang.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TailorGang.model.Model;

public interface Repository extends JpaRepository<Model, Long> {
  List<Model> findByPrice(float price);
}

