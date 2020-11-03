package project.domain;

import java.util.ArrayList;
import java.util.List;
import project.dao.UserDao;

public class FakeUserDao implements UserDao {
    List<User> users = new ArrayList<>();

    public FakeUserDao() {
        users.add(new User("test_user", "Test User", "erinomainensalalause"));
    }
    
    @Override
    public User findByUsername(String username) {
        return users.stream().filter(u->u.getUsername().equals(username)).findFirst().orElse(null);
    }
    
    @Override
    public User create(User user) {
        users.add(user);
        return user;
    } 
    
    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
