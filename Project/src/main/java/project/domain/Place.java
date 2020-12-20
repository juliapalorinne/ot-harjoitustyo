package project.domain;

/**
 * Place class.
 */
public class Place extends StoreableObject {
    private String country;
    private String city;
    private String spot;
    private String type;

    /**
     * Creates a new Place.
     */
    public Place() {
        
    }
    
    /**
     * Creates a new Place with id.
     * 
     * @param id id
     * @param country the country where the place is
     * @param city the city where the place is
     * @param spot the exact spot
     * @param type the type of the place
     */
    public Place(int id, String country, String city, String spot, String type) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.spot = spot;
        this.type = type;
    }
    
    /**
     * Creates a new Place.
     * 
     * @param country the country where the place is
     * @param city the city where the place is
     * @param spot the exact spot
     * @param type the type of the place
     */
    public Place(String country, String city, String spot, String type) {
        this.country = country;
        this.city = city;
        this.spot = spot;
        this.type = type;
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
    
    @Override
    public String toString() {
        return this.country + ", " + this.city + ", " + this.spot + " (" + this.type + ")";
    }



}
