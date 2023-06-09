package com.promineotech.petpark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Contributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contributorId;
    private String contributorName;

    @Column(unique = true)
    private String contributorEmail;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "contributor", cascade = CascadeType.ALL)
    private Set <PetPark> petParks = new HashSet<>();


}
