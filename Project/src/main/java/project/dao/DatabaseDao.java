package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import project.domain.*;
import java.util.List;

/**
 * DatabaseDao SuperClass. Used to access StorableObjects in the database.
 */
public abstract class DatabaseDao {
    
    protected String databaseAddress;
    protected String tableName;
    
    
    public DatabaseDao() {
        
    }
    
    
    /**
     * Deletes a StorableObject from the database.
     * @param id
     * @throws Exception
     */
    public void remove(int id) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
     
    
    /**
     * Searched database to find a StorableObject by id.
     * @param id
     * @throws Exception
     */
    public StoreableObject findById(int id) throws Exception {
        List<StoreableObject> objects;
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            objects = new ArrayList<>();
            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
                stmt.setInt(1, id);
                
                ResultSet result = stmt.executeQuery();
                objects = createListFromResult(result);
            } catch (Exception e) {
            }
        }
        
        if (objects.size() == 1) {
            return objects.get(0);
        }
        return null;
    }
    
    
    /**
     * Searched database by name and returns a StorableObject.
     * SearchField specifies the name field to search.
     * @param name
     * @param searchField
     * @throws Exception
     */
    public StoreableObject findByName(String name, String searchField) throws Exception {
        List<StoreableObject> objects;
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            objects = new ArrayList<>();
            try {
                String stmt = createSearchOneStatementByField(searchField);
                PreparedStatement p = conn.prepareStatement(stmt);
                p.setString(1, name);
                
                ResultSet result = p.executeQuery();
                objects  = createListFromResult(result);
            } catch (Exception e) {
            }
        }
        
        if (objects.size() == 1) {
            return objects.get(0);
        }
        return null;
    }
    

    /**
     * Returns all StorableObjects in the database.
     * @throws Exception
     */
    public List<StoreableObject> getAll() throws Exception {
        List<StoreableObject> objects;
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            objects  = new ArrayList<>();
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName);
                objects  = createListFromResult(result);
            } catch (Exception e) {
                System.out.println("Database is empty.");
            }
        }

        return objects ;
    }
    
    
    /**
     * Returns all StorableObjects with searchTerm in searchField.
     * @param searchTerm
     * @param searchField
     * @throws Exception
     */
    public List<StoreableObject> search(String searchTerm, String searchField) throws Exception {
        List<StoreableObject> objects;
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            objects = new ArrayList<>();
            try {
                String stmt = createSearchAllStatementByField(searchField);
                PreparedStatement p = conn.prepareStatement(stmt);
                String s = "%" + searchTerm + "%";
                p.setString(1, s);
                
                ResultSet result = p.executeQuery();
                objects = createListFromResult(result);
            } catch (Exception e) {
                
            }
        }
        return objects;
    }

    
    protected List<StoreableObject> createListFromResult(ResultSet result) throws Exception {
        List<StoreableObject> o = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            StoreableObject object = findById(id);
            o.add(object);
        }
        return o;
    }
    
    
    /**
     * Creates an SQL statement wiht searchField to search for one StorableObject.
     * @param searchField
     */
    protected String createSearchOneStatementByField(String searchField) {
        StringBuilder stmt = new StringBuilder();
        StringBuilder append = stmt.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(searchField).append(" = ?");
        return append.toString();
    }
    
    
    /**
     * Creates an SQL statement with searchField to search for multiple StorableObjects.
     * @param searchField
     */
    protected String createSearchAllStatementByField(String searchField) {
        StringBuilder stmt = new StringBuilder();
        StringBuilder append = stmt.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(searchField).append(" LIKE ?");
        return append.toString();
    }
    
    
    /**
     * Creates an SQL statement to modify a StorableObject.
     * Inserts the searchTerm in the searchField.
     * @param searchTerm
     * @param searchField
     */
    protected void createModifyStatement(String searchField, String searchTerm, int id, Connection conn) throws Exception {
        StringBuilder stmt = new StringBuilder();
        StringBuilder append = stmt.append("UPDATE ").append(tableName).append(" SET ").append(searchField).append(" = ? WHERE id = ?");
        String s = append.toString();
        PreparedStatement p = conn.prepareStatement(s);
        p.setString(1, searchTerm);
        p.setInt(2, id);        
        p.executeUpdate();
    }
}
