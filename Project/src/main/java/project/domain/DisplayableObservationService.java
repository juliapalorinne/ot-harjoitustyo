
package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class DisplayableObservationService {
    private List<DisplayableObservation> obsList;
    
    private ObservationService observationService;
    private SpeciesService speciesService;
    private PlaceService placeService;
    
    public DisplayableObservationService(ObservationService o, SpeciesService s, PlaceService p) {
        obsList = new ArrayList<>();
        this.observationService = o;
        this.speciesService = s;
        this.placeService = p;
    }
    
    public void addObservation(Observation obs) throws Exception {
        DisplayableObservation d = new DisplayableObservation();
        d.setId(obs.getId());
        d.setSpecies(speciesService.getSpeciesById(obs.getSpeciesId()).toString());
        d.setPlace(placeService.getPlaceById(obs.getPlaceId()).toString());
        d.setIndividuals(obs.getIndividuals());
        d.setDate(obs.getDate());
        d.setTime(obs.getTime());
        d.setInfo(obs.getInfo());
        if (!obsList.contains(d)) {
            obsList.add(d);
        }
    }
    
    
    public List<DisplayableObservation> getAll() throws Exception {
        return obsList;
    }
    
    
    public void redrawObservationList() throws Exception {
        List<Observation> observationlist = observationService.getAll();
        observationlist.forEach(obs-> {
            try {
                addObservation(obs);
            } catch (Exception ex) {
                Logger.getLogger(DisplayableObservationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });     
    }
    
    public List<DisplayableObservation> getObservationsBySearchTerm(String searchTerm) throws Exception {
        List<DisplayableObservation> searchResult = new ArrayList<>();
        return searchResult;
    }
    
    public DisplayableObservation getOne(int id) throws Exception {
        for (DisplayableObservation d : obsList) {
            if (d.getId() == id) {
                return d;
            }
        }
        return new DisplayableObservation();
    }
    
    
    public List<DisplayableObservation> filterByPlace(String searchTerm) throws Exception {
        String s = searchTerm.toLowerCase();
        List<DisplayableObservation> obs = getAll();
        List<DisplayableObservation> result = new ArrayList<>();
        
        obs.stream()
                .filter(o -> o.getPlace().toLowerCase().contains(s))
                .forEach(o -> result.add(o));
        
        return result;
    }
    
    public List<DisplayableObservation> filterBySpecies(String searchTerm) throws Exception {
        String s = searchTerm.toLowerCase();
        List<DisplayableObservation> obs = getAll();
        List<DisplayableObservation> result = new ArrayList<>();
        
        for (DisplayableObservation d : obs) {
            if (d.getSpecies().toLowerCase().contains(s)) {
                System.out.println(d.getSpecies() + "added!");
                result.add(d);
            }
        }
        
        return result;
    }
    
    public ArrayList<TableColumn> getColumns() {
        TableColumn<DisplayableObservation, String> speciesCol = new TableColumn("Species");
        speciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
        speciesCol.setMaxWidth( 1f * Integer.MAX_VALUE * 30 );
        
        TableColumn<DisplayableObservation, Integer> individualCol = new TableColumn("Individuals");
        individualCol.setCellValueFactory(new PropertyValueFactory<>("individuals"));
        individualCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );
        
        TableColumn<DisplayableObservation, String> placeCol = new TableColumn("Place");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        placeCol.setMaxWidth( 1f * Integer.MAX_VALUE * 35 );
        
        TableColumn<DisplayableObservation, Date> dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );
        
        TableColumn<DisplayableObservation, LocalTime> timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );
        
        TableColumn<DisplayableObservation, String> infoCol = new TableColumn("Info");
        infoCol.setCellValueFactory(new PropertyValueFactory<>("info"));
        infoCol.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );
        
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
