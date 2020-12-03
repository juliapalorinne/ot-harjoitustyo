package project.dao;

import project.dao.*;
import java.util.ArrayList;
import java.util.List;
import project.domain.User;


public class FakeUserDatabaseDao implements UserDao {

    private List<User> users;
    
    public FakeUserDatabaseDao() {
        this.users = new ArrayList<>();
    }
    
    
    @Override
    public void addUser(User user) throws Exception {
        int id = users.size() + 1;
        user.setId(id);
        users.add(user);
    }

    
    @Override
    public List<User> getAllUsers() throws Exception {
        return users;
    }
    
    
    @Override
    public void modifyUser(int id, String username, String name, String password) throws Exception {
        
    }
    

    @Override
    public List<User> searchUsers(String searchTerm, String searchField) throws Exception {
        List<User> wantedUsers = new ArrayList<>();
        return wantedUsers;
    }
    
    
    @Override
    public User findUserById(int id) throws Exception {
        List<User> users = new ArrayList<>();
        
        if (users.size() == 1) {
            return users.get(0);
        }
        return null;
    }

    
    @Override
    public User findUserByName(String name, String searchField) throws Exception {
        List<User> users = new ArrayList<>();
        
        
        if (users.size() == 1) {
            return users.get(0);
        }
        return null;
    }


    

    @Override
    public void removeUser(int id) throws Exception {
        users.remove(id);
    }
    
    


}
