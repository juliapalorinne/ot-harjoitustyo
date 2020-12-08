package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import project.domain.StoreableObject;
import project.domain.User;

/**
 * UserDatabaseDao Class. Used to access Users in the database.
 */
public class UserDatabaseDao extends DatabaseDao implements UserDao {

    /**
     * Sets database address in UserDatabaseDao.
     * @param databaseAddress the address of the database
     */
    public UserDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        this.tableName = "User";
    }
    
    
    /**
     * Creates User table if it doesn't exist.
     * @param conn the database connection
     * @throws SQLException Accessing database failed.
     */
    public void createSchemaIfNotExists(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            stmt.execute(
                    "CREATE TABLE User (id INTEGER PRIMARY KEY, username, name, password)");
        } catch (SQLException e) {
            
        }
    }
    
    
    /**
     * Adds new User to database.
     * @param user new User
     * @throws Exception Adding to database failed.
     */
    @Override
    public void addUser(User user) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            createSchemaIfNotExists(conn);            
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO User (username, name, password) "
                            + "VALUES (?,?,?)")) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getName());
                stmt.setString(3, user.getPassword());
                stmt.execute();
            }
        }
    }

    
    /**
     * Finds User by id and modifies it.
     * If any of the fields is left empty, it is not modified.
     * @param id the User id
     * @param username an unique username
     * @param name name
     * @param password password
     * @throws Exception Accessing database failed.
     */
    @Override
    public void modifyUser(int id, String username, String name, String password) throws Exception {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            try {
                if (!username.isEmpty()) {
                    createModifyStatement("username", username, id, conn);
                }
                if (!name.isEmpty()) {
                    createModifyStatement("name", name, id, conn);
                }
                if (!password.isEmpty()) {
                    createModifyStatement("password", password, id, conn);
                }
            } catch (Exception e) {
            }
        }
    }
    
    
     /**
     * Creates an User from a database search result and lists them as StoreableObjects.
     * @param result the database query result
     * @throws Exception Accessing database failed.
     */
    @Override
    protected List<StoreableObject> createListFromResult(ResultSet result) throws Exception {
        List<StoreableObject> users = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String username = result.getString("username");
            String name = result.getString("name");
            String password = result.getString("password");

            User user = new User(id, username, name, password);
            users.add(user);
        }
        return users;
    }

    
    /**
     * Returns an User if found by id from the database.
     * @param id the User id
     * @throws Exception Searching database failed.
     */
    @Override
    public User findUserById(int id) throws Exception {
        return (User) findById(id);
    }

    
    /**
     * Returns an User if found by name from the database.
     * SearchField specifies the name field to search.
     * @param name searched name
     * @param searchField the field to search
     * @throws Exception Searching database failed.
     */
    @Override
    public User findUserByName(String name, String searchField) throws Exception {
        return (User) findByName(name, searchField);
    }

    
    /**
     * Returns all Users in the database.
     * @throws Exception Searching database failed.
     */
    @Override
    public List<User> getAllUsers() throws Exception {
        return convertToUsers(getAll());
    }

    
    /**
     * Searched database to find all Users by name.
     * SearchField specifies the name field to search.
     * @param name searched name
     * @param searchField the field to search
     * @throws Exception Searching database failed.
     */
    @Override
    public List<User> searchUsers(String name, String searchField) throws Exception {
        return convertToUsers(search(name, searchField));
    }
    
    
    /**
     * Converts returned StorableObjects to Users.
     * @param objects the list of objects
     */
    private List<User> convertToUsers(List<StoreableObject> objects) {
        List<User> users = new ArrayList<>();
        objects.forEach((o) -> {
            users.add((User) o);
        });
        return users;
    }


}
