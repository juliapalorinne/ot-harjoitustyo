package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import project.dao.DatabaseDao;


public abstract class StoringService {
    
    protected DatabaseDao database;
    
    
    public StoringService() {
        
    }

    public void setDatabase(DatabaseDao database) {
        this.database = database;
    }
    
    public void remove(int id) throws Exception {
        database.remove(id);
    }
    
    public StoreableObject getById(int id) throws Exception {
        StoreableObject object = database.findById(id);
        return object;
    }
    
    public StoreableObject getByName(String name, String searchField) throws Exception {
        StoreableObject object = database.findByName(name, searchField);
        return object;
    }
    
    public List<StoreableObject> getAll() throws Exception {
        return database.getAll();
    }
    
    public List<StoreableObject> getBySearchTerm(String searchTerm, String searchField) throws Exception {
        return database.search(searchTerm, searchField);
    }
    
}
