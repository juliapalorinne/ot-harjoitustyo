
package project.domain;

import project.domain.*;
import project.dao.ObservationDao;
import java.time.*;
import java.util.Date;
import java.util.List;


public class DisplayableObservation {
    private int id;
    private String species;
    private int individuals;
    private String place;
    private LocalDate date;
    private LocalTime time;
    private String info;
    
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
   
    public void setId(int id) {
        this.id = id;
    }
    
    public void setSpecies(String species) {
        this.species = species;
    }
    
    public void setIndividuals(int individuals) {
        this.individuals = individuals;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public int getIndividuals() {
        return individuals;
    }
        
    public String getPlace() {
        return place;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public LocalTime getTime() {
        return time;
    }
    
    public String getInfo() {
        return info;
    }
    
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DisplayableObservation)) {
            return false;
        }

        DisplayableObservation other = (DisplayableObservation) object;
        return id == other.id;
    }



}
