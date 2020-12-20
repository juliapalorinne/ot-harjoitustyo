package project.domain;

import java.time.LocalDateTime;

/**
 * StoreableObject abstract class.
 */
public abstract class StoreableObject {
    
    protected int id;
    protected LocalDateTime savingTime;
    
    /**
     * Creates a new StoreableObject.
     */
    public StoreableObject() {
        
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setSavingTime(LocalDateTime savingTime) {
        this.savingTime = savingTime;
    }
    
    public int getId() {
        return id;
    }
    
    public LocalDateTime getSavingTime() {
        return savingTime;
    }
}
