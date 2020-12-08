package project.domain;


public abstract class StoreableObject {
    
    protected int id;
    
    
    public StoreableObject() {
        
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
}
