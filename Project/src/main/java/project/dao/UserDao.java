
package project.dao;

import java.util.List;
import project.domain.User;

/**
 * UserDao Interface.
 */
public interface UserDao {
    
    
    /**
     * Adds new User to database.
     * 
     * @param user new User
     * 
     * @throws Exception Adding to database failed.
     */
    public void addUser(User user) throws Exception;
    
    
    /**
     * Finds User by id and modifies it.
     * If any of the fields is left empty, it is not modified.
     * 
     * @param id the User id
     * @param username an unique username
     * @param name name
     * @param password password
     * 
     * @throws Exception Accessing database failed.
     */
    public void modifyUser(int id, String username, String name, String password) throws Exception;
    
    
    /**
     * Deletes an User from the database.
     * 
     * @param id the id of the User
     * 
     * @throws Exception Deleting from database failed.
     */
    public void remove(int id) throws Exception;

    
    /**
     * Returns an User if found by id from the database.
     * 
     * @param id the User id
     * 
     * @return the User
     * 
     * @throws Exception Searching database failed.
     */
    public User findUserById(int id) throws Exception;
    
    
    /**
     * Returns an User if found by name from the database.
     * SearchField specifies the name field to search.
     * 
     * @param name searched name
     * @param searchField the field to search
     * 
     * @return the User
     * 
     * @throws Exception Searching database failed.
     */
    public User findUserByName(String name, String searchField) throws Exception;
    
    
    /**
     * Returns all Users in the database.
     * 
     * @throws Exception Searching database failed.
     * 
     * @return the list of Users
     */
    public List<User> getAllUsers() throws Exception;
    
    
    /**
     * Searched database to find all Users by name.
     * SearchField specifies the name field to search.
     * 
     * @param name searched name
     * @param searchField the field to search
     * 
     * @return the list of Users
     * 
     * @throws Exception Searching database failed.
     */
    public List<User> searchUsers(String name, String searchField) throws Exception;
    
    
}
