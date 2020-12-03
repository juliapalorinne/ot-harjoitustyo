
package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import project.dao.ObservationDao;
import project.dao.ObservationDatabaseDao;
import project.dao.UserDao;

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
        return new DisplayableObservation();
    }
    
}
