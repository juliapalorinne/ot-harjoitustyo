
package project.domain;

public class User {
    private String name;
    private String username;
    private String password;
    

    public User(String username, String name, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }    
    
    public void setUser(String name) {
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
