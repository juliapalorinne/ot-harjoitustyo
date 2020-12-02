package project.dao;

import project.dao.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import project.domain.Observation;


public class TestObservationDatabaseDao implements ObservationDao {

    private String databaseAddress;

    public TestObservationDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    
    @Override
    public void addObservation(Observation observation) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        createSchemaIfNotExists(conn);

        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Observation (species, individuals, place, date, time, info, user) "
                + "VALUES (?,?,?,?,?,?,?)");

        stmt.setInt(1, observation.getSpeciesId());
        stmt.setInt(2, observation.getIndividuals());
        stmt.setInt(3, observation.getPlaceId());
        stmt.setString(4, observation.getDate().toString());
        stmt.setString(5, observation.getTime().toString());
        stmt.setString(6, observation.getInfo());
        stmt.setString(7, observation.getUserId());
        stmt.execute();

        conn.close();
    }

    
    @Override
    public List<Observation> getAllObservations() throws Exception {

        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Observation> observations = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM Observation");
            observations = createListFromResult(result);
        } catch (Exception e) {
            System.out.println("Database is empty.");
        }
        conn.close();

        return observations;
    }
    
    
    @Override
    public void modifyObservation(String id, int species, int individuals, int place, String date, String time, String info, String username) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        
        try {
            if (species != 0) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE Observation SET species = ? WHERE id = ?");
                stmt.setInt(1, species);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }

            if (individuals != 0) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE Observation SET individuals = ? WHERE id = ?");
                stmt.setInt(1, individuals);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }
            
            if (place != 0) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE Observation SET place = ? WHERE id = ?");
                stmt.setInt(1, place);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }
            
            if (!date.isEmpty()) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE Observation SET date = ? WHERE id = ?");
                stmt.setString(1, date);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }
            
            if (!time.isEmpty()) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE Observation SET time = ? WHERE id = ?");
                stmt.setString(1, time);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }
            
            if (!info.isEmpty()) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE Observation SET info = ? WHERE id = ?");
                stmt.setString(1, info);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }
            
            if (!username.isEmpty()) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE Observation SET user = ? WHERE id = ?");
                stmt.setString(1, username);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }
        } catch (Exception e) {
        }
        conn.close();
    }
    

    @Override
    public List<Observation> searchObservations(String searchTerm, String searchField) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Observation> observations = new ArrayList<>();
        try {
            String stmt = createStatementByField(searchField);
            PreparedStatement p = conn.prepareStatement(stmt);
            p.setString(1, searchTerm);

            ResultSet result = p.executeQuery();
            observations = createListFromResult(result);
        } catch (Exception e) {

        }
        return observations;
    }
    
    
    @Override
    public Observation findObservationById(String id) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Observation> observations = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Observation WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            
            ResultSet result = stmt.executeQuery();
            observations = createListFromResult(result);
        } catch (Exception e) {
        }
        
        if (observations.size() == 1) {
            return observations.get(0);
        }
        return null;
    }
    

    @Override
    public void removeObservation(String id) throws Exception {

        Connection conn = DriverManager.getConnection(databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Observation WHERE id = ?");
        stmt.setInt(1, Integer.parseInt(id));
        stmt.executeUpdate();
        conn.close();
    }
    
    

    

    /**
     * Creates Observation table if it doesn't exist.
     */
    public void createSchemaIfNotExists(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        try {
            stmt.execute(
                    "CREATE TABLE Observation (id INTEGER PRIMARY KEY, species, individuals, place, date, time, info, user)");
        } catch (Exception e) {
            System.out.println("Database schema already exists.");
        }

    }
    
        
    private String createStatementByField(String searchField) {
        StringBuilder stmt = new StringBuilder();
        stmt.append("SELECT * FROM Observation WHERE ");
        if (searchField.equals("species")) {
            stmt.append("species = ?");
        }
        if (searchField.equals("place")) {
            stmt.append("place = ?");
        }
        if(searchField.equals("date")){
            stmt.append("date = ?");
        }
        if (searchField.equals("user")) {
            stmt.append("user = ?");
        }
        return stmt.toString();
    }
    
    
    private List<Observation> createListFromResult(ResultSet result) throws Exception {
        List<Observation> observations = new ArrayList<>();
        while (result.next()) {
                int id = result.getInt("id");
                int speciesId = result.getInt("species");
                int individuals = result.getInt("individuals");
                int placeId = result.getInt("place");
                String date = result.getString("date");
                String time = result.getString("time");
                String info = result.getString("info");
                String username = result.getString("user");
                
                Observation observation = new Observation(id, speciesId, individuals, placeId, LocalDate.parse(date), LocalTime.parse(time), info, username);
                observations.add(observation);
            }
        return observations;
    }


}
