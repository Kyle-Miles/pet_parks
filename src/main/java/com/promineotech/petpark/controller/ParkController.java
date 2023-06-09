package com.promineotech.petpark.controller;

import com.promineotech.petpark.controller.model.ContributorData;
import com.promineotech.petpark.controller.model.PetParkData;
import com.promineotech.petpark.service.ParkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pet_park")
@Slf4j
public class ParkController {

    @Autowired
    private ParkService parkService;

    @PostMapping("/contributor")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ContributorData insertContributor(
            @RequestBody ContributorData contributorData) {
        log.info("Creating contributor {}", contributorData);

        return parkService.insertContributor(contributorData);
    }

    @PutMapping("/contributor/{contributorId}")
    public ContributorData insertContributor( @PathVariable Long contributorId,
            @RequestBody ContributorData contributorData) {
        contributorData.setContributorId(contributorId);
        log.info("Updating contributor {}", contributorData);

        return parkService.insertContributor(contributorData);
    }

    @GetMapping("/contributor")
    public List <ContributorData> retrieveAllContributors(){
        log.info("Retrieve all contributors called");
        return parkService.retrieveAllContributors();
    }

    @GetMapping("/contributor/{contributorId}")
    public ContributorData retrieveContributorById(@PathVariable Long contributorId){
        log.info("Retrieving contributor with ID={}",contributorId);
        return parkService.retrieveContributorById(contributorId);
    }

    @DeleteMapping("/contributor")
    public void deleteAllContributors() {
        log.info("Attempting to delete all contributors");
        throw new UnsupportedOperationException("Deleting all contributors is not allowed");
    }

    @DeleteMapping ("/contributor/{contributorId}")
    public Map<String, String> deleteContributorById(@PathVariable Long contributorId){
        log.info("Deleting contributor with ID={}",contributorId);
        parkService.deleteContributorById(contributorId);
        return Map.of("message", "Deletion of contributor with ID=" + contributorId + " was successful.");
    }

    @PostMapping("/contributor/{contributorId}/park")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetParkData insertPetPark(@PathVariable Long contributorId
            , @RequestBody PetParkData petParkData) {
        log.info("Creating park {} for contributor with ID={}", petParkData, contributorId);

        return parkService.savePetPark(contributorId, petParkData);
    }

    @PutMapping("/contributor/{contributorId}/park/{parkId}")
    public PetParkData updatePetPark(@PathVariable Long contributorId
            , @PathVariable Long parkId
            , @RequestBody PetParkData petParkData) {
        petParkData.setPetParkId(parkId);
        log.info("Creating park {} for contributor with ID={}", petParkData, contributorId);

        return parkService.savePetPark(contributorId, petParkData);
    }

    @GetMapping("/contributor/{contributorId}/park/{parkId}")
    public PetParkData retrievePetParkById(@PathVariable Long contributorId
            , @PathVariable Long parkId) {
        log.info("Retrieving pet park with ID={} for contributor with ID={}", parkId, contributorId);

        return parkService.retrievePetParkById(contributorId, parkId);

    }
}
