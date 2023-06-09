package com.promineotech.petpark.controller.model;

import com.promineotech.petpark.entity.Amenity;
import com.promineotech.petpark.entity.Contributor;
import com.promineotech.petpark.entity.GeoLocation;
import com.promineotech.petpark.entity.PetPark;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PetParkData {
    private Long petParkId;
    private String parkName;
    private String directions;
    private String stateOrProvince;
    private String country;
    private GeoLocation geoLocation;
    private PetParkContributor contributor;
    private Set<String> amenities = new HashSet<>();

    public PetParkData(PetPark petPark) {
        petParkId = petPark.getPetParkId();
        parkName = petPark.getParkName();
        directions = petPark.getDirections();
        stateOrProvince = petPark.getStateOrProvince();
        country = petPark.getCountry();
        geoLocation =petPark.getGeoLocation();
        contributor = new PetParkContributor(petPark.getContributor());

        for (Amenity amenity : petPark.getAmenities()) {
            amenities.add(amenity.getAmenity());
        }
    }
    @Data
    @NoArgsConstructor
    public static class PetParkContributor {
        private Long contributorId;
        private String contributorName;
        private String contributorEmail;

        public PetParkContributor(Contributor contributor) {
            contributorId = contributor.getContributorId();
            contributorName = contributor.getContributorName();
            contributorEmail =contributor.getContributorEmail();
        }
    }
}
