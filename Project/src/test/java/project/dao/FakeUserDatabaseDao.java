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
        List<User> foundUsers = new ArrayList<>();
        
        for (User u : users) {
            if (id == u.getId()) {
                foundUsers.add(u);
            }
        }
        
        if (foundUsers.size() == 1) {
            return foundUsers.get(0);
        }
        return null;
    }

    
    @Override
    public User findUserByName(String name, String searchField) throws Exception {
        List<User> foundUsers = new ArrayList<>();
        if (searchField.equals("username")) {
            for (User u : users) {
                if (name.equals(u.getUsername())) {
                    foundUsers.add(u);
                }
            }
        }
        
        if (foundUsers.size() == 1) {
            return foundUsers.get(0);
        }
        return null;
    }


    

    @Override
    public void remove(int id) throws Exception {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (id == u.getId()) {
                users.remove(u);
            }
        }
    }
    
    


}
