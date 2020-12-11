package project.domain;

import java.time.*;


public class StoreableObservation extends Observation {
    
    private int speciesId;
    private int placeId;
    private int privacy;
    private String userId;
    
    public StoreableObservation() {
        
    }

    public StoreableObservation(int id, int species, int individuals, int place, LocalDate date, 
            LocalTime time, String info, int privacy, String username) {
        this.id = id;
        this.speciesId = species;
        this.individuals = individuals;
        this.placeId = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.privacy = privacy;
        this.userId = username;
    }
    
    public StoreableObservation(int species, int individuals, int place, LocalDate date, 
            LocalTime time, String info, int privacy, String username) {
        this.speciesId = species;
        this.individuals = individuals;
        this.placeId = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.privacy = privacy;
        this.userId = username;
    }

    
    public void setSpeciesId(int species) {
        this.speciesId = species;
    }
    
    public void setPlace(int place) {
        this.placeId = place;
    }
    
    
    public void setPrivacy(int p) {
        this.privacy = p;
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
    
    public int getPrivacy() {
        return privacy;
    }
    
    public String getUserId() {
        return userId;
    }

}
