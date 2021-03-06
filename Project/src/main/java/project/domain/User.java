package project.domain;

/**
 * User class.
 */
public class User extends StoreableObject {
    
    private String name;
    private String username;
    private String password;
    
    /**
     * Creates User with id, username, name and password.
     * 
     * @param id id
     * @param username username
     * @param name name
     * @param password password
     */
    public User(int id, String username, String name, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Creates User with username, name and password.
     * 
     * @param username username
     * @param name name
     * @param password password
     */
    public User(String username, String name, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }    
    
    public void setName(String name) {
        this.name = name;
    }    
    
    public void setPassword(String password) {
        this.password = password;
    }    
    
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        
        User other = (User) object;
        return username.equals(other.username);
    }
}
