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


public class PlaceDatabaseDao implements PlaceDao {

    private String databaseAddress;

    public PlaceDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    
    @Override
    public void addPlace(Place place) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        createSchemaIfNotExists(conn);

        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Place (country, city, spot, type) "
                + "VALUES (?,?,?,?)");

        stmt.setString(1, place.getCountry());
        stmt.setString(2, place.getCity());
        stmt.setString(3, place.getSpot());
        stmt.setString(4, place.getType());
        stmt.execute();
        
        stmt.close();
        conn.close();
    }

    
    @Override
    public List<Place> getAllPlaces() throws Exception {

        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Place> places = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM Place");
            places = createListFromResult(result);
        } catch (Exception e) {
            System.out.println("Database is empty.");
        }
        conn.close();

        return places;
    }
    
    
    @Override
    public void modifyPlace(int id, String country, String city, String spot, String type) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        
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
        conn.close();
    }
    

    @Override
    public List<Place> searchPlaces(String searchTerm, String searchField) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Place> places = new ArrayList<>();
        try {
            String stmt = createStatementByField(searchField);
            PreparedStatement p = conn.prepareStatement(stmt);
            p.setString(1, searchTerm);

            ResultSet result = p.executeQuery();
            places = createListFromResult(result);
        } catch (Exception e) {

        }
        conn.close();
        return places;
    }
    
    
    @Override
    public Place findPlaceById(int id) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Place> places = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Place WHERE id = ?");
            stmt.setInt(1, id);
            
            ResultSet result = stmt.executeQuery();
            places = createListFromResult(result);
        } catch (Exception e) {
        }
        conn.close();
        
        if (places.size() == 1) {
            return places.get(0);
        }
        return null;
    }

    
    @Override
    public Place findPlaceByName(String name, String searchField) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Place> places = new ArrayList<>();
        try {
            String stmt = createStatementByField(searchField);
            PreparedStatement p = conn.prepareStatement(stmt);
            p.setString(1, name);
            
            ResultSet result = p.executeQuery();
            places = createListFromResult(result);
        } catch (Exception e) {
        }
        conn.close();
        
        if (places.size() == 1) {
            return places.get(0);
        }
        return null;
    }


    @Override
    public void removePlace(int id) throws Exception {

        Connection conn = DriverManager.getConnection(databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Place WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        conn.close();
    }
    

    

    /**
     * Creates Place table if it doesn't exist.
     */
    public void createSchemaIfNotExists(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        try {
            stmt.execute(
                    "CREATE TABLE Place (id INTEGER PRIMARY KEY, country, city, spot, type)");
            System.out.println("Table Place created");
        } catch (Exception e) {
            
        }

    }
    
    private void createModifyStatement(String searchField, String searchTerm, int id, Connection conn) throws Exception {
        StringBuilder stmt = new StringBuilder();
        stmt.append("UPDATE Place SET ").append(searchField).append(" = ? WHERE id = ?");
        String s = stmt.toString();
        PreparedStatement p = conn.prepareStatement(s);
        p.setString(1, searchTerm);
        p.setInt(2, id);        
        p.executeUpdate();
    }
        
    private String createStatementByField(String searchField) {
        StringBuilder stmt = new StringBuilder();
        stmt.append("SELECT * FROM Place WHERE ");
        if (searchField.equals("country")) {
            stmt.append("country = ?");
        }
        if (searchField.equals("city")) {
            stmt.append("city = ?");
        }
        if (searchField.equals("spot")) {
            stmt.append("spot = ?");
        }
        if (searchField.equals("type")) {
            stmt.append("type = ?");
        }
        return stmt.toString();
    }
    
    
    private List<Place> createListFromResult(ResultSet result) throws Exception {
        List<Place> places = new ArrayList<>();
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


}
