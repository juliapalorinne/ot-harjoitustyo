package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import project.domain.User;


public class UserDatabaseDao implements UserDao {

    private String databaseAddress;

    public UserDatabaseDao(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    
    @Override
    public void addUser(User user) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        createSchemaIfNotExists(conn);

        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO User (username, name, password) "
                + "VALUES (?,?,?)");

        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getPassword());
        stmt.execute();

        conn.close();
    }

    
    @Override
    public List<User> getAllUsers() throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<User> users = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM User");
            users = createListFromResult(result);
        } catch (Exception e) {
            System.out.println("Database is empty.");
        }
        conn.close();

        return users;
    }
    
    
    @Override
    public void modifyUser(String id, String username, String name, String password) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        
        try {
            if (!username.isEmpty()) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE User SET username = ? WHERE id = ?");
                stmt.setString(1, username);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }

            if (!name.isEmpty()) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE User SET name = ? WHERE id = ?");
                stmt.setString(1, name);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }

            if (!password.isEmpty()) { 
                PreparedStatement stmt = conn.prepareStatement("UPDATE User SET password = ? WHERE id = ?");
                stmt.setString(1, password);
                stmt.setInt(2, Integer.parseInt(id));
                stmt.executeUpdate();
            }
        } catch (Exception e) {
        }
        conn.close();
    }
    

    @Override
    public List<User> searchUsers(String searchTerm, String searchField) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<User> users = new ArrayList<>();
        try {
            String stmt = createStatementByField(searchField);
            PreparedStatement p = conn.prepareStatement(stmt);
            p.setString(1, searchTerm);

            ResultSet result = p.executeQuery();
            users = createListFromResult(result);
        } catch (Exception e) {

        }
        return users;
    }
    
    
    @Override
    public User findUserById(String id) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(id));
            
            ResultSet result = stmt.executeQuery();
            users = createListFromResult(result);
        } catch (Exception e) {
        }
        
        if (users.size() == 1) {
            return users.get(0);
        }
        return null;
    }

    
    @Override
    public User findUserByName(String name, String searchField) throws Exception {
        Connection conn = DriverManager.getConnection(databaseAddress);
        List<User> users = new ArrayList<>();
        try {
            String stmt = createStatementByField(searchField);
            PreparedStatement p = conn.prepareStatement(stmt);
            p.setString(1, name);
            
            ResultSet result = p.executeQuery();
            users = createListFromResult(result);
        } catch (Exception e) {
        }
        
        if (users.size() == 1) {
            return users.get(0);
        }
        return null;
    }


    

    @Override
    public void removeUser(String id) throws Exception {

        Connection conn = DriverManager.getConnection(databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User WHERE id = ?");
        stmt.setInt(1, Integer.parseInt(id));
        stmt.executeUpdate();
        conn.close();
    }
    
    

    

    /**
     * Creates User table if it doesn't exist.
     */
    public void createSchemaIfNotExists(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        try {
            stmt.execute(
                    "CREATE TABLE User (id INTEGER PRIMARY KEY, username, name, password)");
        } catch (Exception e) {
            
        }

    }
    
        
    private String createStatementByField(String searchField) {
        StringBuilder stmt = new StringBuilder();
        stmt.append("SELECT * FROM User WHERE ");
        if (searchField.equals("username")) {
            stmt.append("username = ?");
        }
        if (searchField.equals("name")) {
            stmt.append("name = ?");
        }
        
        return stmt.toString();
    }
    
    
    private List<User> createListFromResult(ResultSet result) throws Exception {
        List<User> users = new ArrayList<>();
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


}
