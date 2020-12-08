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
import project.domain.StoreableObject;
import project.domain.StoreableObservation;

/**
 * ObservationsDatabaseDao Class. Used to access Observations in the database.
 */
public class ObservationDatabaseDao extends DatabaseDao implements ObservationDao {

    
    /**
     * Sets database address in ObservationsDatabaseDao.
     * @param databaseAddress the address of the database
     */
    public ObservationDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        this.tableName = "Observation";
    }
    
    
    /**
     * Creates Observation table if it doesn't exist.
     * @param conn the database connection
     * @throws SQLException Accessing database failed.
     */
    public void createSchemaIfNotExists(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        try {
            stmt.execute(
                    "CREATE TABLE Observation (id INTEGER PRIMARY KEY, species, individuals, place, date, time, info, user)");
        } catch (SQLException e) {
        }

    }
    
    
    /**
     * Adds new Observation.
     * @param observation new StoreableObservation
     * @throws Exception Adding to database failed.
     */
    @Override
    public void addObservation(StoreableObservation observation) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            createSchemaIfNotExists(conn);
            
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Observation (species, individuals, place, date, time, info, user) "
                            + "VALUES (?,?,?,?,?,?,?)")) {
                stmt.setInt(1, observation.getSpeciesId());
                stmt.setInt(2, observation.getIndividuals());
                stmt.setInt(3, observation.getPlaceId());
                stmt.setString(4, observation.getDate().toString());
                stmt.setString(5, observation.getTime().toString());
                stmt.setString(6, observation.getInfo());
                stmt.setString(7, observation.getUserId());
                stmt.execute();
            }
        }
    }

    
    /**
     * Finds an Observation by id and modifies it.
     * @param id the Observation id
     * @param species the id of the Species of the Observation
     * @param individuals the number of individuals
     * @param place the id of the Place of the Observation
     * @param date the date of the Observation
     * @param time the time of the Observation (hh:mm)
     * @param info additional info
     * @param username username of the User
     * @throws Exception Accessing database failed.
     */
    @Override
    public void modifyObservation(int id, int species, int individuals, int place, String date, String time, String info, String username) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
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
        }
    }
    

    
    /**
     * Creates an Observation from a database search result and lists them as StoreableObjects.
     * @param result the database query result
     */
    @Override
    protected List<StoreableObject> createListFromResult(ResultSet result) throws Exception {
        List<StoreableObject> observations = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            int speciesId = result.getInt("species");
            int individuals = result.getInt("individuals");
            int placeId = result.getInt("place");
            String date = result.getString("date");
            String time = result.getString("time");
            String info = result.getString("info");
            String username = result.getString("user");

            StoreableObservation observation = new StoreableObservation(id, speciesId, individuals, placeId, LocalDate.parse(date), LocalTime.parse(time), info, username);
            observations.add(observation);
        }
        return observations;
    }
    
    
    /**
     * Returns an Observation if found by id from the database.
     * @param id the id of the wanted Observation
     * @throws Exception Searching database failed.
     */
    @Override
    public StoreableObservation findObservationById(int id) throws Exception {
        return (StoreableObservation) findById(id);
    }
    
    
    /**
     * Returns all Observations in the database.
     * @throws Exception Searching database failed.
     */
    @Override
    public List<StoreableObservation> getAllObservations() throws Exception {
        return convertToObservations(getAll());
    }
    
    
    /**
     * Returns Observations with searchTerm in searchField.
     * @param searchTerm searched term
     * @param searchField the field to search
     * @throws Exception Searching database failed.
     */
    @Override
    public List<StoreableObservation> searchObservations(String searchTerm, String searchField) throws Exception {
        List<StoreableObject> observations = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            observations = new ArrayList<>();
            try {
                String stmt = createSearchOneStatementByField(searchField);
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
        }
        return convertToObservations(observations);
    }
    
    
    /**
     * Converts returned StorableObjects to Observations.
     * @param objects the list of objects
     */
    private List<StoreableObservation> convertToObservations(List<StoreableObject> objects) {
        List<StoreableObservation> obs = new ArrayList<>();
        objects.forEach((o) -> {
            obs.add((StoreableObservation) o);
        });
        return obs;
    }
    
    
    
    
    
//    protected List<StoreableObservation> createObservationListFromResult(ResultSet result) throws Exception {
//        List<StoreableObservation> observations = new ArrayList<>();
//        while (result.next()) {
//            int id = result.getInt("id");
//            int speciesId = result.getInt("species");
//            int individuals = result.getInt("individuals");
//            int placeId = result.getInt("place");
//            String date = result.getString("date");
//            String time = result.getString("time");
//            String info = result.getString("info");
//            String username = result.getString("user");
//
//            StoreableObservation observation = new StoreableObservation(id, speciesId, individuals, placeId, LocalDate.parse(date), LocalTime.parse(time), info, username);
//            observations.add(observation);
//        }
//        return observations;
//    }
    
    

}
