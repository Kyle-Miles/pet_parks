package com.promineotech.petpark.dao;

import com.promineotech.petpark.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AmenityDao extends JpaRepository<Amenity, Long> {
    Set<Amenity> findAllByAmenityIn(Set<String> amenities);
}
