package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import project.domain.Place;
import project.domain.StoreableObject;


public class PlaceDatabaseDao extends DatabaseDao implements PlaceDao {

    /**
     *
     * @param databaseAddress
     */
    public PlaceDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        this.tableName = "Place";
    }
    
    /**
     * Creates Place table if it doesn't exist.
     * @param conn
     * @throws java.sql.SQLException
     */
    public void createSchemaIfNotExists(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            stmt.execute(
                    "CREATE TABLE Place (id INTEGER PRIMARY KEY, country, city, spot, type)");            
        } catch (SQLException e) {
            
        }

    }
    
    
    @Override
    public void addPlace(Place place) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            createSchemaIfNotExists(conn);
            
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Place (country, city, spot, type) "
                            + "VALUES (?,?,?,?)")) {
                stmt.setString(1, place.getCountry());
                stmt.setString(2, place.getCity());
                stmt.setString(3, place.getSpot());
                stmt.setString(4, place.getType());
                stmt.execute();
            }
        }
    }

    
    @Override
    public void modifyPlace(int id, String country, String city, String spot, String type) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            try {
                if (!country.isEmpty()) {
                    createModifyStatement("country", country, id, conn);
                }
                
                if (!city.isEmpty()) {
                    createModifyStatement("city", city, id, conn);
                }
                
                if (!spot.isEmpty()) {
                    createModifyStatement("spot", spot, id, conn);
                }
                
                if (!type.isEmpty()) {
                    createModifyStatement("type", type, id, conn);
                }
            } catch (Exception e) {
            }
        }    }
    

    
    
    /**
     * Creates Place from database search result and lists them as StoreableObjects.
     * @param result
     * @throws Exception
     */
    @Override
    protected List<StoreableObject> createListFromResult(ResultSet result) throws Exception {
        List<StoreableObject> places = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String country = result.getString("country");
            String city = result.getString("city");
            String spot = result.getString("spot");
            String type = result.getString("type");

            Place place = new Place(id, country, city, spot, type);
            places.add(place);
        }
        return places;
    }

    
    @Override
    public Place findPlaceById(int id) throws Exception {
        return (Place) findById(id);
    }

    @Override
    public Place findPlaceByName(String name, String searchField) throws Exception {
        return (Place) findByName(name, searchField);
    }

    @Override
    public List<Place> getAllPlaces() throws Exception {
        return convertToPlaces(getAll());
    }

    @Override
    public List<Place> searchPlaces(String searchTerm, String searchField) throws Exception {
        return convertToPlaces(search(searchTerm, searchField));
    }

    
    private List<Place> convertToPlaces(List<StoreableObject> objects) {
        List<Place> places = new ArrayList<>();
        objects.forEach((o) -> {
            places.add(Place) o);
        });
        return places;
    }

}
