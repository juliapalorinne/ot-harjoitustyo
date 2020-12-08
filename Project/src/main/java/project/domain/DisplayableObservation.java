
package project.domain;

import project.domain.*;
import project.dao.ObservationDao;
import java.time.*;
import java.util.Date;
import java.util.List;


public class DisplayableObservation extends Observation {
    
    private String species;
    private String place;
    
    public DisplayableObservation() {
        
    }

    public DisplayableObservation(int id, String species, int individuals, String place, LocalDate date, LocalTime time, String info) {
        this.id = id;
        this.species = species;
        this.individuals = individuals;
        this.place = place;
        this.date = date;
        this.time = time;
        this.info = info;
    }

    
    public void setSpecies(String species) {
        this.species = species;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    
    public String getSpecies() {
        return species;
    }
   
    public String getPlace() {
        return place;
    }
}
