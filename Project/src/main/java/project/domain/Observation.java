
package project.domain;

import project.dao.ObservationDao;
import java.time.*;
import java.util.Date;
import java.util.List;


public class Observation {
    private Long id;
    private String species;
    private int individuals;
    private String place;
    private Date date;
    private LocalTime time;
    private String info;
    private User user;
    
    public Observation() {
    }

    public Observation(Long id, String species, int individuals, String place, Date date, LocalTime time, String info, User user) {
        this.id = id;
        this.species = species;
        this.individuals = individuals;
        this.place = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.user = user;
    }
    
    public Observation(String species, int individuals, String place, Date date, LocalTime time, String info, User user) {
        this.species = species;
        this.individuals = individuals;
        this.place = place;
        this.date = date;
        this.time = time;
        this.info = info;
        this.user = user;
    }
   
    public void setId(Long id) {
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    public Long getId() {
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
    
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Observation)) {
            return false;
        }

        Observation other = (Observation) object;
        return id.equals(other.id);
    }



}
