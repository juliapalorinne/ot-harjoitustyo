
package project.domain;

import project.dao.ObservationDao;
import java.time.*;
import java.util.Date;
import java.util.List;


public class Observation {
    private int id;
    private int speciesId;
    private int individuals;
    private int placeId;
    private LocalDate date;
    private LocalTime time;
    private String info;
    private String userId;
    
    public Observation() {
    }

    public Observation(int id, int species, int individuals, int place, LocalDate date, LocalTime time, String info, String username) {
        this.id = id;
        this.speciesId = species;
        this.individuals = individuals;
        this.placeId = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.userId = username;
    }
    
    public Observation(int species, int individuals, int place, LocalDate date, LocalTime time, String info, String username) {
        this.speciesId = species;
        this.individuals = individuals;
        this.placeId = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.userId = username;
    }
   
    public void setId(int id) {
        this.id = id;
    }
    
    public void setSpeciesId(int species) {
        this.speciesId = species;
    }
    
    public void setIndividuals(int individuals) {
        this.individuals = individuals;
    }
    
    public void setPlace(int place) {
        this.placeId = place;
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

    public void setUser(String username) {
        this.userId = username;
    }

    
    public int getId() {
        return id;
    }
    
    public int getSpeciesId() {
        return speciesId;
    }
    
    public int getIndividuals() {
        return individuals;
    }
        
    public int getPlaceId() {
        return placeId;
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
    
    public String getUserId() {
        return userId;
    }
    
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Observation)) {
            return false;
        }

        Observation other = (Observation) object;
        return id == other.id;
    }



}
