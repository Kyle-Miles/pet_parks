package com.promineotech.petpark.service;

import com.promineotech.petpark.controller.model.ContributorData;
import com.promineotech.petpark.dao.PetParkDao;
import com.promineotech.petpark.controller.model.PetParkData;
import com.promineotech.petpark.dao.AmenityDao;
import com.promineotech.petpark.dao.ContributorDao;
import com.promineotech.petpark.entity.Amenity;
import com.promineotech.petpark.entity.Contributor;
import com.promineotech.petpark.entity.PetPark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ParkService {

    @Autowired
    private ContributorDao contributorDao;

    @Autowired
    private AmenityDao amenityDao;

    @Autowired
    private PetParkDao petParkDao;

    @Transactional(readOnly = false)
    public ContributorData insertContributor(ContributorData contributorData) {
        Long contributorId = contributorData.getContributorId();
        Contributor contributor = findOrCreateContributor(contributorId, contributorData.getContributorEmail());

        setFieldsInContributor(contributor, contributorData);
        return new ContributorData(contributorDao.save(contributor));
    }

    private void setFieldsInContributor(Contributor contributor, ContributorData contributorData) {
        contributor.setContributorEmail(contributorData.getContributorEmail());
        contributor.setContributorName(contributorData.getContributorName());
    }

    private Contributor findOrCreateContributor(Long contributorId, String contributorEmail) {
        Contributor contributor;

        if(Objects.isNull(contributorId)) {
            Optional <Contributor> opContrib = contributorDao.findByContributorEmail(contributorEmail);
            contributor = new Contributor();

            if(opContrib.isPresent()) {
                throw new DuplicateKeyException("Contributor with email " + contributorEmail + " already exists.");
            }
        } else {
            contributor = findContributorById(contributorId);
        }
        return contributor;
    }

    private Contributor findContributorById(Long contributorId) {

        return contributorDao.findById(contributorId)
                .orElseThrow(() -> new NoSuchElementException(
                "Contributor with ID = " + contributorId + " was not found."
        ));
    }

    @Transactional
    public List<ContributorData> retrieveAllContributors() {
//        List <Contributor> contributors = contributorDao.findAll();
//        List<ContributorData> response = new LinkedList<>();
//
//        for (Contributor contributor : contributors) {
//            response.add(new ContributorData(contributor));
//        }
//
//        return response;
        return contributorDao.findAll()
                .stream()
                .map(ContributorData::new)
                .toList();
    }

    @Transactional(readOnly = false)
    public ContributorData retrieveContributorById(Long contributorId) {
        Contributor contributor = findContributorById(contributorId);
        return new ContributorData(contributor);
    }

    @Transactional(readOnly = false)
    public void deleteContributorById(Long contributorId) {
        Contributor contributor = findContributorById(contributorId);
        contributorDao.delete(contributor);
    }

    @Transactional(readOnly = false)
    public PetParkData savePetPark(Long contributorId, PetParkData petParkData) {
        Contributor contributor= findContributorById(contributorId);

        Set<Amenity> amenities = amenityDao.findAllByAmenityIn(petParkData.getAmenities());

        PetPark petPark = findOrCreatePetPark(petParkData.getPetParkId());

        setPetParkFields(petPark, petParkData);

        petPark.setContributor(contributor);
        contributor.getPetParks().add(petPark);

        for (Amenity amenity : amenities) {
            amenity.getPetParks().add(petPark);
            petPark.getAmenities().add(amenity);
        }

        PetPark dbPetPark = petParkDao.save(petPark);

        return new PetParkData(dbPetPark);
    }

    private void setPetParkFields(PetPark petPark, PetParkData petParkData) {
        petPark.setCountry(petParkData.getCountry());
        petPark.setDirections(petParkData.getDirections());
        petPark.setGeoLocation(petParkData.getGeoLocation());
        petPark.setParkName(petParkData.getParkName());
        petPark.setStateOrProvince(petParkData.getStateOrProvince());
    }

    private PetPark findOrCreatePetPark(Long petParkId) {

        PetPark petPark;

        if (Objects.isNull(petParkId)) {
            petPark = new PetPark();
        } else {
            petPark = findPetParkById(petParkId);
        }

        return petPark;
    }

    private PetPark findPetParkById(Long petParkId) {

        return petParkDao.findById(petParkId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Pet Park with ID=" + petParkId + " does not exist"));
    }

    @Transactional(readOnly = true)
    public PetParkData retrievePetParkById(Long contributorId, Long petParkId) {
        findContributorById(contributorId);
        PetPark petPark = findPetParkById(petParkId);

        if (petPark.getContributor().getContributorId() != contributorId) {
            throw new IllegalStateException("Pet park with ID=" + petParkId
                    + " is not owned by contributor with ID=" + contributorId);
        }

        return new PetParkData(petPark);
    }
}
