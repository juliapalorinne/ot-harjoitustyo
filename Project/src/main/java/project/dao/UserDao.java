
package project.dao;

import java.util.List;
import project.domain.User;

public interface UserDao {
    
    public void addUser(User user) throws Exception;
    
    public void removeUser(String id) throws Exception;
    
    public void modifyUser(String id, String username, String name, String password) throws Exception;

    public User findUserById(String id) throws Exception;
    
    public User findUserByName(String name, String searchField) throws Exception;
    
    public List<User> getAllUsers() throws Exception;
    
    public List<User> searchUsers(String name, String searchField) throws Exception;
    
    
}
