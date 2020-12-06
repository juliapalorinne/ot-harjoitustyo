package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import project.domain.Species;


public class SpeciesDatabaseDao implements SpeciesDao {

    private String databaseAddress;

    public SpeciesDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    
    @Override
    public void addSpecies(Species species) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        createSchemaIfNotExists(conn);

        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Species (englishName, scientificName, finnishName, abbreviation) "
                + "VALUES (?,?,?,?)");

        stmt.setString(1, species.getEnglishName());
        stmt.setString(2, species.getScientificName());
        stmt.setString(3, species.getFinnishName());
        stmt.setString(4, species.getAbbreviation());
        stmt.execute();

        stmt.close();
        conn.close();
    }

    
    @Override
    public List<Species> getAllSpecies() throws Exception {

        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Species> speciesList = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM Species");
            speciesList = createListFromResult(result);
        } catch (Exception e) {
            System.out.println("Database is empty.");
        }
        conn.close();

        return speciesList;
    }
    
    
    @Override
    public void modifySpecies(int id, String englishName, String scientificName, String finnishName, String abbreviation) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        
        try {
            if (!englishName.isEmpty()) {
                createModifyStatement("englishName", englishName, id, conn);
            }

            if (!scientificName.isEmpty()) { 
                createModifyStatement("scientificName", scientificName, id, conn);
            }

            if (!finnishName.isEmpty()) {
                createModifyStatement("finnishName", finnishName, id, conn);
            }
            
            if (!abbreviation.isEmpty()) {
                createModifyStatement("abbreviation", abbreviation, id, conn);
            }
        } catch (Exception e) {
        }
        conn.close();
    }
    

    @Override
    public List<Species> searchSpecies(String searchTerm, String searchField) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Species> speciesList = new ArrayList<>();
        try {
            String stmt = createSearchAllStatementByField(searchField);
            PreparedStatement p = conn.prepareStatement(stmt);
            String s = "%" + searchTerm + "%";
            p.setString(1, s);

            ResultSet result = p.executeQuery();
            speciesList = createListFromResult(result);
        } catch (Exception e) {

        }
        conn.close();
        return speciesList;
    }
    
    
    @Override
    public Species findSpeciesById(int id) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Species> speciesList = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Species WHERE id = ?");
            stmt.setInt(1, id);
            
            ResultSet result = stmt.executeQuery();
            speciesList = createListFromResult(result);
        } catch (Exception e) {
        }
        conn.close();
        
        if (speciesList.size() == 1) {
            return speciesList.get(0);
        }
        return null;
    }

    
    @Override
    public Species findSpeciesByName(String name, String searchField) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<Species> speciesList = new ArrayList<>();
        try {
            String stmt = createSearchOneStatementByField(searchField);
            PreparedStatement p = conn.prepareStatement(stmt);
            p.setString(1, name);
            
            ResultSet result = p.executeQuery();
            speciesList = createListFromResult(result);
        } catch (Exception e) {
        }
        conn.close();
        
        if (speciesList.size() == 1) {
            return speciesList.get(0);
        }
        return null;
    }


    

    @Override
    public void removeSpecies(int id) throws Exception {

        Connection conn = DriverManager.getConnection(databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Species WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        conn.close();
    }
    
    

    

    /**
     * Creates Species table if it doesn't exist.
     */
    public void createSchemaIfNotExists(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        try {
            stmt.execute(
                    "CREATE TABLE Species (id INTEGER PRIMARY KEY, englishName, scientificName, finnishName, abbreviation)");
        } catch (Exception e) {
            
        }

    }
    
    private void createModifyStatement(String searchField, String searchTerm, int id, Connection conn) throws Exception {
        StringBuilder stmt = new StringBuilder();
        stmt.append("UPDATE Species SET ").append(searchField).append(" = ? WHERE id = ?");
        String s = stmt.toString();
        PreparedStatement p = conn.prepareStatement(s);
        p.setString(1, searchTerm);
        p.setInt(2, id);        
        p.executeUpdate();
    }
    
    private String createSearchAllStatementByField(String searchField) {
        StringBuilder stmt = new StringBuilder();
        stmt.append("SELECT * FROM Species WHERE ");
        if (searchField.equals("englishName")) {
            stmt.append("englishName LIKE ?");
        }
        if (searchField.equals("scientificName")) {
            stmt.append("scientificName LIKE ?");
        }
        if (searchField.equals("finnishName")) {
            stmt.append("finnishName LIKE ?");
        }
        if (searchField.equals("abbreviation")) {
            stmt.append("abbreviation LIKE ?");
        }
        return stmt.toString();
    }
    
    private String createSearchOneStatementByField(String searchField) {
        StringBuilder stmt = new StringBuilder();
        stmt.append("SELECT * FROM Species WHERE ");
        if (searchField.equals("englishName")) {
            stmt.append("englishName = ?");
        }
        if (searchField.equals("scientificName")) {
            stmt.append("scientificName = ?");
        }
        if (searchField.equals("finnishName")) {
            stmt.append("finnishName = ?");
        }
        if (searchField.equals("abbreviation")) {
            stmt.append("abbreviation = ?");
        }
        return stmt.toString();
    }
    
    private List<Species> createListFromResult(ResultSet result) throws Exception {
        List<Species> speciesList = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String englishName = result.getString("englishName");
            String scientificName = result.getString("scientificName");
            String finnishName = result.getString("finnishName");
            String abbreviation = result.getString("abbreviation");

            Species species = new Species(id, englishName, scientificName, finnishName, abbreviation);
            speciesList.add(species);
        }
        return speciesList;
    }


}
