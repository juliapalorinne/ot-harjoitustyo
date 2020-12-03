
package project.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.domain.Observation;
import project.domain.ObservationService;
import project.domain.Place;
import project.domain.PlaceService;
import project.domain.Species;
import project.domain.SpeciesService;

public class NewObservation {
    private ObservationService observationService;
    private SpeciesService speciesService;
    private PlaceService placeService;
    private InputWindow inputWindow;
    private Button returnButton;
    private Button addNewSpeciesButton;
    private Button addNewPlaceButton;
    
    
    public NewObservation(ObservationService observationService, SpeciesService speciesService, 
            PlaceService placeService, InputWindow inputWindow) {
        this.observationService = observationService;
        this.speciesService = speciesService;
        this.placeService = placeService;
        this.inputWindow = inputWindow;
        this.returnButton = inputWindow.createButton("Return");
        this.addNewSpeciesButton = inputWindow.createButton("Add new Species");
        this.addNewPlaceButton = inputWindow.createButton("Add new Place");
    } 

    
    public Scene createNewObservation(Stage stage, ObservationTable observationTable) throws Exception {
        VBox newObsPane = inputWindow.createNewWindow();
        Species species = listSpecies(newObsPane).getSelectionModel().getSelectedItem();
        TextField newIndividualInput = inputWindow.createInputField(newObsPane, "Individuals");
        Place place = listPlaces(newObsPane).getSelectionModel().getSelectedItem();
        DatePicker datePicker = inputWindow.createDatePicker(newObsPane, "Date");
        TextField newTimeInput = inputWindow.createInputField(newObsPane, "Time (hh:mm)");
        TextField newInfoInput = inputWindow.createBigInputField(newObsPane, "Additional info");

        Label observationCreationMessage = new Label();
        Button createNewObsButton = inputWindow.createButton("Add new observation");
        createNewObsButton.setPadding(new Insets(10));
        
        
        createNewObsButton.setOnAction(e-> {
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(newTimeInput.getText());
            String info = newInfoInput.getText();
            int individuals = Integer.parseInt(newIndividualInput.getText());
            
            
            if (observationService.createObservation(species, individuals, place, date, time, info)) {
                newIndividualInput.setText("");
                newTimeInput.setText("");
                newInfoInput.setText("");
                try {
                    stage.setScene(observationTable.observationScene());
                } catch (Exception ex) {
                    Logger.getLogger(NewObservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
 
        });  
        
        
        newObsPane.getChildren().addAll(observationCreationMessage, createNewObsButton, returnButton);
        Scene newObservationScene = new Scene(newObsPane, 750, 650);
        return newObservationScene;
    }
    
    public Button returnButton() {
        return this.returnButton;
    }
    
    public Button addNewSpeciesButton() {
        return this.addNewSpeciesButton;
    }
    
    public Button addNewPlaceButton() {
        return this.addNewPlaceButton;
    }
    
    
    public ListView<Species> listSpecies(VBox pane) throws Exception {
        List<Species> speciesList = speciesService.getAllSpecies();
        ObservableList<Species> observableSpeciesList = FXCollections.observableArrayList();
        speciesList.forEach(species -> {
            observableSpeciesList.add(species);
        });
        
        ListView<Species> listView = new ListView<>();
        listView.setItems(observableSpeciesList);
        listView.setPrefWidth(400);
        
        HBox speciesPane = new HBox(10);
        Label speciesLabel = new Label("Species");
        inputWindow.setLabelStyle(speciesLabel);
        speciesPane.getChildren().addAll(speciesLabel, listView, addNewSpeciesButton);
        pane.getChildren().addAll(speciesPane);
        return listView;
    }

    
    public ListView<Place> listPlaces(VBox pane) throws Exception {
        List<Place> placeList = placeService.getAllPlaces();
        ObservableList<Place> observablePlaceList = FXCollections.observableArrayList();
        placeList.forEach(place -> {
            observablePlaceList.add(place);
        });
        
        ListView<Place> listView = new ListView<>();
        listView.setItems(observablePlaceList);
        listView.setPrefWidth(400);
        
        HBox placePane = new HBox(10);
        Label placeLabel = new Label("Place");
        inputWindow.setLabelStyle(placeLabel);
        placePane.getChildren().addAll(placeLabel, listView, addNewPlaceButton);
        pane.getChildren().addAll(placePane);
        return listView;
    }
}
