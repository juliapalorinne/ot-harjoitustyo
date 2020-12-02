
package project.domain;

import project.dao.ObservationDao;

public class Place {
    private int id;
    private String country;
    private String city;
    private String spot;
    private String type;


    public Place(int id, String country, String city, String spot, String type) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.spot = spot;
        this.type = type;
    }

    public Place(String country, String city, String spot, String type) {
        this.country = country;
        this.city = city;
        this.spot = spot;
        this.type = type;
    }    
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public int getId() {
        return id;
    }
    
    public String getCountry() {
        return country;
    }
        
    public String getCity() {
        return city;
    }
    
    public String getSpot() {
        return spot;
    }
    
    public String getType() {
        return type;
    }
    
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Place)) {
            return false;
        }

        Place other = (Place) object;
        return id == other.id;
    }



}
