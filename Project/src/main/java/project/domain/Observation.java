
package project.domain;

import project.dao.ObservationDao;
import java.time.*;
import java.util.Date;
import java.util.List;


public class Observation {
    private int id;
    private final String species;
    private final String place;
    private final Date date;
    private final LocalTime time;
    private final String info;
    private User user;
    

    public Observation(int id, String species, String place, Date date, LocalTime time, String info, User user) {
        this.id = id;
        this.species = species;
        this.place = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.user = user;
    }
    
    public Observation(String species, String place, Date date, LocalTime time, String info, User user) {
        this.species = species;
        this.place = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.user = user;
    }
   
    public void setId(int id) {
        this.id = id;
    }
   
    public int getId() {
        return id;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public String getPlace() {
        return place;
    }
    
    public Date getDate() {
        return date;
    }
    
    public LocalTime getTime() {
        return time;
    }
    
    public String getInfo() {
        return info;
    }
    
    public User getUser() {
        return user;
    }
    
    
    public boolean equals(Object object) {
        if (!(object instanceof Observation)) {
            return false;
        }

        Observation other = (Observation) object;
        return id == other.id;
    }



}
