package project.domain;

import java.time.*;

/**
 * DisplayableObservation class. Extends abstract class Observation.
 * To show the Observations in the application.
 */
public class DisplayableObservation extends Observation {
    
    private String species;
    private String fullSpecies;
    private String place;
    private boolean privacy;
    private String user;
    
    /**
     * Creates a new DisplayableObservation.
     */
    public DisplayableObservation() {
        
    }

    /**
     * Creates a new DisplayableObservation.
     * 
     * @param id id
     * @param species the Species as text
     * @param individuals the number of individuals
     * @param place the Place as text
     * @param date date
     * @param time time
     * @param info the additional info
     * @param privacy 0 for public, 1 for private Observation
     * @param user the name of the User
     * @param savingTime date and time of saving the Observation
     */
    public DisplayableObservation(int id, String species, int individuals, String place, LocalDate date, 
            LocalTime time, String info, boolean privacy, String user, LocalDateTime savingTime) {
        this.id = id;
        this.species = species;
        this.individuals = individuals;
        this.place = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.privacy = privacy;
        this.user = user;
        this.savingTime = savingTime;
    }

    
    public void setSpecies(String species) {
        this.species = species;
    }
    
    public void setFullSpecies(String species) {
        this.fullSpecies = species;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    
    public void setPrivacy(boolean p) {
        this.privacy = p;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public String getFullSpecies() {
        return fullSpecies;
    }
   
    public String getPlace() {
        return place;
    }
    
    public boolean getPrivacy() {
        return privacy;
    }
    
    public String getUser() {
        return user;
    }
}
