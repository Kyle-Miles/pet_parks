package com.promineotech.petpark.dao;

import com.promineotech.petpark.entity.PetPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetParkDao extends JpaRepository<PetPark, Long> {
}
