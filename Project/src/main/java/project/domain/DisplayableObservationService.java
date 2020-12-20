package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Provides methods for handling DisplayableObservations.
 */
public class DisplayableObservationService {
    
    /**
    * Accessing the Species database.
    */
    private final SpeciesService speciesService;
    
    /**
    * Accessing the Place database.
    */
    private final PlaceService placeService;
    
    /**
    * Accessing the Observation database with links to Places and Species.
    */
    private final StoreableObservationService observationService;
    
    private List<DisplayableObservation> obsList;
    
    
    /**
     * Starts DisplayableObservationService.
     * 
     * @param o StoreableObservationService
     * @param s SpeciesService
     * @param p PlaceService
     */
    public DisplayableObservationService(StoreableObservationService o, SpeciesService s, PlaceService p) {
        obsList = new ArrayList<>();
        this.observationService = o;
        this.speciesService = s;
        this.placeService = p;
    }
    
    
    /**
     * Tries to create a new DisplayableObservation.
     * 
     * @param obs StoreableObservation
     * 
     * @throws Exception Creating Observation failed.
     */
    public void addObservation(StoreableObservation obs) throws Exception {
        DisplayableObservation d = new DisplayableObservation();
        d.setId(obs.getId());
        d.setSpecies(speciesService.getSpeciesById(obs.getSpeciesId()).toString());
        d.setFullSpecies(speciesService.getSpeciesById(obs.getSpeciesId()).toStringWithAbbreviation());
        d.setPlace(placeService.getPlaceById(obs.getPlaceId()).toString());
        d.setIndividuals(obs.getIndividuals());
        d.setDate(obs.getDate());
        d.setTime(obs.getTime());
        d.setInfo(obs.getInfo());
        if (obs.getPrivacy() == 1) {
            d.setPrivacy(true);     // Observation is private
        } else {
            d.setPrivacy(false);    // Observation is public
        }
        d.setSavingTime(obs.getSavingTime());
        obsList.add(d);
    }
    
    
    /**
     * Gets the list of all DisplayableObservations.
     * 
     * @return the list of Observations
     * 
     * @throws Exception Getting the Observation list failed.
     */
    public List<DisplayableObservation> getAll() throws Exception {
        return obsList;
    }
    
    
    /**
     * Redraw the list of DisplayableObservations.
     * Get the list of StorableObservations and converts them to DisplayableObservations.
     * 
     * @throws Exception Getting the Observation list failed.
     */
    public void redrawObservationList() throws Exception {
        List<StoreableObservation> observationlist = observationService.getAll();
        obsList = new ArrayList<>();
        observationlist.forEach((StoreableObservation obs) -> {
            try {
                addObservation(obs);
            } catch (Exception ex) {
                Logger.getLogger(DisplayableObservationService.class.getName()).log(Level.SEVERE, null, ex);
            }

        });     
    }
    
    
    /**
     * Gets a DisplayableObservation by id.
     * 
     * @param id id
     * 
     * @return the DisplayableObservation
     * 
     * @throws Exception 
     */
    public DisplayableObservation getOne(int id) throws Exception {
        for (DisplayableObservation d : obsList) {
            if (d.getId() == id) {
                return d;
            }
        }
        return new DisplayableObservation();
    }
    
    
    /**
     * Filters DisplayableObservations by Place.
     * 
     * @param searchTerm the search term
     * 
     * @return the list of DisplayableObservations
     * 
     * @throws Exception Filtering Observations failed.
     */
    public List<DisplayableObservation> filterByPlace(String searchTerm) throws Exception {
        String s = searchTerm.toLowerCase();
        List<DisplayableObservation> obs = getAll();
        List<DisplayableObservation> result = new ArrayList<>();
        
        obs.stream()
                .filter(d -> d.getPlace().toLowerCase().contains(s))
                .forEach(d -> result.add(d));
        
        return result;
    }
    
    
    /**
     * Filters DisplayableObservations by Species.
     * 
     * @param searchTerm the search term
     * 
     * @return the list of DisplayableObservations
     * 
     * @throws Exception Filtering Observations failed.
     */
    public List<DisplayableObservation> filterBySpecies(String searchTerm) throws Exception {
        String s = searchTerm.toLowerCase();
        List<DisplayableObservation> obs = getAll();
        List<DisplayableObservation> result = new ArrayList<>();
        
        obs.stream()
                .filter(d -> d.getSpecies().toLowerCase().contains(s))
                .forEach(d -> result.add(d));
        
        return result;
    }
    
    
    /**
     * Filters DisplayableObservations by Species and Place.
     * 
     * @param species the Species as text
     * @param place the Place as text
     * 
     * @return the list of DisplayableObservations
     * 
     * @throws Exception Filtering Observations failed.
     */
    public List<DisplayableObservation> filterBySpeciesAndPlace(String species, String place) throws Exception {
        List<DisplayableObservation> sList = filterBySpecies(species);
        List<DisplayableObservation> result = new ArrayList<>();
        String p = place.toLowerCase();
        for (DisplayableObservation d : sList) {
            if (d.getPlace().toLowerCase().contains(p)) {
                result.add(d);
            }
        }
        return result;
    }
    
    
    /**
     * Creates a list of columns to show DisplayableObservations in a table.
     * 
     * @return the list of columns
     */
    public ArrayList<TableColumn> getColumns() {
        TableColumn<DisplayableObservation, String> speciesCol = new TableColumn("Species");
        speciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
        speciesCol.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        
        TableColumn<DisplayableObservation, Integer> individualCol = new TableColumn("Individuals");
        individualCol.setCellValueFactory(new PropertyValueFactory<>("individuals"));
        individualCol.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        
        TableColumn<DisplayableObservation, String> placeCol = new TableColumn("Place");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        placeCol.setMaxWidth(1f * Integer.MAX_VALUE * 35);
        
        TableColumn<DisplayableObservation, String> dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setMaxWidth(1f * Integer.MAX_VALUE * 10);
        
        TableColumn<DisplayableObservation, LocalTime> timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeCol.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        
        TableColumn<DisplayableObservation, String> infoCol = new TableColumn("Info");
        infoCol.setCellValueFactory(new PropertyValueFactory<>("info"));
        infoCol.setMaxWidth(1f * Integer.MAX_VALUE * 15);
        
        ArrayList<TableColumn> list = new ArrayList<>();
        list.add(speciesCol);
        list.add(individualCol);
        list.add(placeCol);
        list.add(dateCol);
        list.add(timeCol);
        list.add(infoCol);
        
        return list;
    }
}
