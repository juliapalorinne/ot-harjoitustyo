package project.dao;

import project.dao.*;
import java.util.ArrayList;
import java.util.List;
import project.domain.User;


public class FakeUserDatabaseDao implements UserDao {

    private List<User> users;
    private int nextId;
    
    public FakeUserDatabaseDao() {
        this.users = new ArrayList<>();
        nextId = 1;
    }
    
    
    @Override
    public void addUser(User user) throws Exception {
        user.setId(nextId);
        users.add(user);
        nextId++;
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
