
package project.domain;

import project.dao.ObservationDao;
import java.time.*;
import java.util.Date;
import java.util.List;


public class StoreableObservation extends Observation {
    
    private int speciesId;
    private int placeId;
    private String userId;
    
    public StoreableObservation() {
        
    }

    public StoreableObservation(int id, int species, int individuals, int place, LocalDate date, LocalTime time, String info, String username) {
        this.id = id;
        this.speciesId = species;
        this.individuals = individuals;
        this.placeId = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.userId = username;
    }
    
    public StoreableObservation(int species, int individuals, int place, LocalDate date, LocalTime time, String info, String username) {
        this.speciesId = species;
        this.individuals = individuals;
        this.placeId = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.userId = username;
    }

    
    public void setSpeciesId(int species) {
        this.speciesId = species;
    }
    
    public void setPlace(int place) {
        this.placeId = place;
    }

    public void setUser(String username) {
        this.userId = username;
    }

    
    public int getSpeciesId() {
        return speciesId;
    }
        
    public int getPlaceId() {
        return placeId;
    }
    
    public String getUserId() {
        return userId;
    }

}
