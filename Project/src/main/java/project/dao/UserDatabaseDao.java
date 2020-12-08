package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import project.domain.StoreableObject;
import project.domain.User;

/**
 * UserDatabaseDao Class. Used to access Users in the database.
 */
public class UserDatabaseDao extends DatabaseDao implements UserDao {

    public UserDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        this.tableName = "User";
    }
    
    
    /**
     * Creates User table if it doesn't exist.
     * @param conn
     * @throws java.sql.SQLException
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
     * Adds new user.
     * @param user
     * @throws Exception
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
     * Finds user by id and modifies it.
     * If any field is left empty, it is not modified.
     * @param id
     * @param username
     * @param name
     * @param password
     * @throws Exception
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
        }    }
    
    
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

    @Override
    public User findUserById(int id) throws Exception {
        return (User) findById(id);
    }

    @Override
    public User findUserByName(String name, String searchField) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return convertToUsers(getAll());
    }

    @Override
    public List<User> searchUsers(String name, String searchField) throws Exception {
        return convertToUsers(search(name, searchField));
    }
    
    
    
    private List<User> convertToUsers(List<StoreableObject> objects) {
        List<User> users = new ArrayList<>();
        objects.forEach((o) -> {
            users.add((User) o);
        });
        return users;
    }


}
