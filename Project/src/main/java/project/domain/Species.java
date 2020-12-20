package project.domain;


public class Species extends StoreableObject {
    private String englishName;
    private String scientificName;
    private String finnishName;
    private String abbreviation;

    
    /**
     * Creates a new Species.
     * 
     * @param id id
     * @param englishName the English name
     * @param scientificName the scientific name
     * @param finnishName the Finnish name
     * @param abbreviation 3+3 abbreviation
     */
    public Species(int id, String englishName, String scientificName, String finnishName, String abbreviation) {
        this.id = id;
        this.englishName = englishName;
        this.scientificName = scientificName;
        this.finnishName = finnishName;
        this.abbreviation = abbreviation;
    }
    

    /**
     * Creates a new Species.
     * 
     * @param englishName the English name
     * @param scientificName the scientific name
     * @param finnishName the Finnish name
     * @param abbreviation 3+3 abbreviation
     */
    public Species(String englishName, String scientificName, String finnishName, String abbreviation) {
        this.englishName = englishName;
        this.scientificName = scientificName;
        this.finnishName = finnishName;
        this.abbreviation = abbreviation;
    }    
    
    
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
    
    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }
    
    public void setFinnishName(String finnishName) {
        this.finnishName = finnishName;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    
    
    public String getEnglishName() {
        return englishName;
    }
        
    public String getScientificName() {
        return scientificName;
    }
    
    public String getFinnishName() {
        return finnishName;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
    
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Species)) {
            return false;
        }

        Species other = (Species) object;
        return id == other.id;
    }

    
    @Override
    public String toString() {
        return this.englishName + " (" + this.finnishName + ", " + this.scientificName + ")";
    }
    
    
    /**
     * Returns the Species as text.
     * 
     * @return the Species
     */
    public String toStringWithAbbreviation() {
        return this.englishName + " (" + this.finnishName + ", " + this.scientificName +  ", " + this.abbreviation + ")";
    }


}
