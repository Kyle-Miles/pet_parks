package com.promineotech.petpark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class PetPark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petParkId;
    private String parkName;
    private String directions;
    private String stateOrProvince;
    private String country;

    @Embedded
    private GeoLocation geoLocation;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contributor_id", nullable = false)
    private Contributor contributor;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "pet_park_amenity",
    joinColumns = @JoinColumn(name = "pet_park_id"),
    inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set <Amenity> amenities = new HashSet<>();
}
