package project.domain;

import java.time.*;

/**
 * StorableObservation class. Extends abstract class Observation.
 * To store the Observations in the database.
 */
public class StoreableObservation extends Observation {
    private int speciesId;
    private int placeId;
    private int privacy;
    private String userId;
    
    /**
     * Creates a new StorableObservation.
     */
    public StoreableObservation() {
        
    }

    /**
     * Creates a new StorableObservation.
     * 
     * @param id id
     * @param species the Species id
     * @param individuals the number of individuals
     * @param place the Place id
     * @param date date 
     * @param time time
     * @param info additional info
     * @param privacy 0 for public, 1 for private Observation
     * @param username the username of the User
     */
    public StoreableObservation(int id, int species, int individuals, int place, LocalDate date, 
            LocalTime time, String info, int privacy, String username, LocalDateTime savingTime) {
        this.id = id;
        this.speciesId = species;
        this.individuals = individuals;
        this.placeId = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.privacy = privacy;
        this.userId = username;
        this.savingTime = savingTime;
    }
    
    /**
     * Creates a new StorableObservation.
     * 
     * @param species the Species id
     * @param individuals the number of individuals
     * @param place the Place id
     * @param date date 
     * @param time time (hh:dd)
     * @param info additional info
     * @param privacy 0 for public, 1 for private Observation
     * @param username the username of the User
     */
    public StoreableObservation(int species, int individuals, int place, LocalDate date, 
            LocalTime time, String info, int privacy, String username, LocalDateTime savingTime) {
        this.speciesId = species;
        this.individuals = individuals;
        this.placeId = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.privacy = privacy;
        this.userId = username;
        this.savingTime = savingTime;
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
