
package project.domain;

public class User {
    
    private int id;
    private String name;
    private String username;
    private String password;
    

    public User(int id, String username, String name, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    
    public User(String username, String name, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
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
    
    public int getId() {
        return id;
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
