package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public abstract class Observation extends StoreableObject {
    
    protected int individuals;
    protected LocalDate date;
    protected LocalTime time;
    protected String info;
    
    public Observation() {
        
    }

    public void setIndividuals(int individuals) {
        this.individuals = individuals;
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
    
    public int getIndividuals() {
        return individuals;
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
        if (!(object instanceof Observation)) {
            return false;
        }

        Observation other = (Observation) object;
        return id == other.getId();
    }
    
}
