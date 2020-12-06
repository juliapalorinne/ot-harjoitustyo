package project.dao;

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


public class ObservationDatabaseDao implements ObservationDao {

    private String databaseAddress;

    public ObservationDatabaseDao(String databaseAddress) {
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

        stmt.close();
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
    public void modifyObservation(int id, int species, int individuals, int place, String date, String time, String info, String username) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        
        try {
            if (species != 0) { 
                createModifyStatement("species", Integer.toString(species), id, conn);
            }
            if (individuals != 0) { 
                createModifyStatement("individuals", Integer.toString(individuals), id, conn);
            }
            if (place != 0) { 
                createModifyStatement("place", Integer.toString(place), id, conn);
            }
            if (!date.isEmpty()) { 
                createModifyStatement("date", date, id, conn);
            }
            if (!time.isEmpty()) { 
                createModifyStatement("time", time, id, conn);
            }
            if (!info.isEmpty()) { 
                createModifyStatement("info", info, id, conn);
            }
            if (!username.isEmpty()) { 
                createModifyStatement("user", username, id, conn);
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
            if (searchField.equals("species") || searchField.equals("place")) {
                p.setInt(1, Integer.parseInt(searchTerm));
            } else {
                p.setString(1, searchTerm);
            }

            ResultSet result = p.executeQuery();
            observations = createListFromResult(result);
        } catch (Exception e) {

        }
        conn.close();
        return observations;
    }
    
    
    @Override
    public Observation findObservationById(int id) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Observation> observations = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Observation WHERE id = ?");
            stmt.setInt(1, id);
            
            ResultSet result = stmt.executeQuery();
            observations = createListFromResult(result);
        } catch (Exception e) {
        }
        conn.close();
        if (observations.size() == 1) {
            return observations.get(0);
        }
        return null;
    }
    

    @Override
    public void removeObservation(int id) throws Exception {

        Connection conn = DriverManager.getConnection(databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Observation WHERE id = ?");
        stmt.setInt(1, id);
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
        if (searchField.equals("date")) {
            stmt.append("date = ?");
        }
        if (searchField.equals("user")) {
            stmt.append("user = ?");
        }
        return stmt.toString();
    }
    
    private void createModifyStatement(String field, String newInfo, int id, Connection conn) throws Exception {
        StringBuilder stmt = new StringBuilder();
        stmt.append("UPDATE Observation SET ").append(field).append(" = ? WHERE id = ?");
        String s = stmt.toString();
        PreparedStatement p = conn.prepareStatement(s);
        p.setString(1, newInfo);
        p.setInt(2, id);        
        p.executeUpdate();
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
