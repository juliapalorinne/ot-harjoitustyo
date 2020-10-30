
package project.dao;

import java.util.List;
import project.domain.User;

public interface UserDao {
    
    
    User create(User user) throws Exception;

    User findByUsername(String username);

    User findByName(String name);
    
    List<User> getAll();
    
    
}
