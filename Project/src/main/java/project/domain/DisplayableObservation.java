package project.domain;

import java.time.*;

public class DisplayableObservation extends Observation {
    
    private String species;
    private String fullSpecies;
    private String place;
    private boolean privacy;
    
    public DisplayableObservation() {
        
    }

    public DisplayableObservation(int id, String species, int individuals, String place, LocalDate date, LocalTime time, String info, boolean privacy) {
        this.id = id;
        this.species = species;
        this.individuals = individuals;
        this.place = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.privacy = privacy;
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
}
