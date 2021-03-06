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
import project.domain.StoreableObject;

/**
 * SpeciesDatabaseDao Class. Used to access Species in the database.
 */
public class SpeciesDatabaseDao extends DatabaseDao implements SpeciesDao {

    
    /**
     * Sets database address in SpeciesDatabaseDao.
     * 
     * @param databaseAddress the address of the database
     */
    public SpeciesDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        this.tableName = "Species";
    }
    
    
    /**
     * Creates Species table if it doesn't exist.
     * 
     * @param conn the database connection
     * 
     * @throws SQLException Accessing database failed.
     */
    public void createSchemaIfNotExists(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            stmt.execute(
                    "CREATE TABLE Species (id INTEGER PRIMARY KEY, englishName, scientificName, finnishName, abbreviation)");
        } catch (SQLException e) {
            
        }
    }
    
    @Override
    public void addSpecies(Species species) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            createSchemaIfNotExists(conn);            
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Species (englishName, scientificName, finnishName, abbreviation) "
                            + "VALUES (?,?,?,?)")) {
                stmt.setString(1, species.getEnglishName());
                stmt.setString(2, species.getScientificName());
                stmt.setString(3, species.getFinnishName());
                stmt.setString(4, species.getAbbreviation());
                stmt.execute();
            }
            conn.close();
        }
    }
    
    @Override
    public void modifySpecies(int id, String englishName, String scientificName, String finnishName, String abbreviation) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
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
    }
    
    @Override
    protected List<StoreableObject> createListFromResult(ResultSet result) throws Exception {
        List<StoreableObject> speciesList = new ArrayList<>();
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
    
    @Override
    public Species findSpeciesById(int id) throws Exception {
        return (Species) findById(id);
    }
    
    @Override
    public Species findSpeciesByName(String name, String searchField) throws Exception {
        return (Species) findByName(name, searchField);
    }
    
    @Override
    public List<Species> getAllSpecies() throws Exception {
        return convertToSpecies(getAll());
    }
    
    @Override
    public List<Species> searchSpecies(String searchTerm, String searchField) throws Exception {
        return convertToSpecies(search(searchTerm, searchField));
    }

    
    /**
     * Converts returned StorableObjects to Species.
     * 
     * @return a list of Species
     * 
     * @param objects the list of objects
     */
    private List<Species> convertToSpecies(List<StoreableObject> objects) {
        List<Species> species = new ArrayList<>();
        objects.forEach((o) -> {
            species.add((Species) o);
        });
        return species;
    }

}
