
package project.scenes;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.domain.DisplayableObservation;
import project.domain.DisplayableObservationService;
import project.domain.ObservationService;
import project.domain.Place;
import project.domain.PlaceService;
import project.domain.Species;
import project.domain.SpeciesService;
import project.ui.InputWindow;

public class SearchObservationScene {
    private InputWindow inputWindow;
    private DisplayableObservationService displayObsService;
    private SpeciesService speciesService;
    private PlaceService placeService;
    private Button returnButton;
    
    private ObservableList<DisplayableObservation> observations;
    
    public SearchObservationScene(InputWindow inputWindow, ObservationService observationService, 
            SpeciesService speciesService, PlaceService placeService) {
        this.inputWindow = inputWindow;
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService);
        this.observations = FXCollections.observableArrayList();
        this.speciesService = speciesService;
        this.placeService = placeService;
        returnButton = inputWindow.createButton("Return");
    } 

    public Scene searchScene(Stage stage) throws Exception {
        VBox searchPane = inputWindow.createNewWindow();
        
        TextField speciesInput = inputWindow.createInputField(searchPane, "Search by species");
        HBox speciesList = listSpecies(searchPane);
        
        TextField placeInput = inputWindow.createInputField(searchPane, "Search by place");
        ListView<Place> placeList = listPlaces(searchPane);
        DatePicker datePicker = inputWindow.createDatePicker(searchPane, "Date");
        
        
        Button searchButton = inputWindow.createButton("Search");
        searchButton.setPadding(new Insets(10));

        searchButton.setOnAction(e-> {
            LocalDate date = datePicker.getValue();
            
            String species = speciesInput.getText();
            String place = placeInput.getText();
//            Species species = speciesList.getSelectionModel().getSelectedItem();
//            Place place = placeList.getSelectionModel().getSelectedItem();
            try {
                speciesService.getSpeciesBySearchTerm(species, "englishName");
                placeService.getPlaceBySearchTerm(place, "spot");
            } catch (Exception ex) {
                Logger.getLogger(SearchObservationScene.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
//        FilteredList<DisplayableObservation> filteredObservations = new FilteredList(observations, p -> true);
//        ChoiceBox<String> choiceBox = new ChoiceBox();
//        choiceBox.getItems().addAll("English name", "Scientific name", "Finnish name");
//        choiceBox.setValue("English name");
//
//        TextField textField = new TextField();
//        textField.setPromptText("Search here!");
//        textField.setOnKeyReleased(keyEvent ->
//        {
//            switch (choiceBox.getValue())
//            {
//                case "English name":
//                    filteredObservations.setPredicate(p -> p.getEnglishName().toLowerCase().contains(textField.getText().toLowerCase().trim()));
//                    break;
//                case "Scientific name":
//                    filteredObservations.setPredicate(p -> p.getScientificName().toLowerCase().contains(textField.getText().toLowerCase().trim()));
//                    break;
//                case "Finnish name":
//                    filteredObservations.setPredicate(p -> p.getFinnishName().toLowerCase().contains(textField.getText().toLowerCase().trim()));
//                    break;
//            }
//        });
//
//        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
//        {//reset table and textfield when new choice is selected
//            if (newVal != null)
//            {
//                textField.setText("");
//                flPerson.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
//            }
//        });
//        HBox hBox = new HBox(choiceBox, textField);//Add choiceBox and textField to hBox
//        hBox.setAlignment(Pos.CENTER);//Center HBox
        
        searchPane.getChildren().addAll(searchButton, returnButton);
        Scene searchScene = new Scene(searchPane, 600, 600);
        return searchScene;
    }
    
    
    public Button returnButton() {
        return this.returnButton;
    }
    
    public HBox listSpecies(VBox pane) throws Exception {
        List<Species> speciesList = speciesService.getAllSpecies();
        ObservableList<Species> observableSpeciesList = FXCollections.observableArrayList();
        speciesList.forEach(species -> {
            observableSpeciesList.add(species);
        });
        
        FilteredList<Species> filteredSpecies = new FilteredList(observableSpeciesList, p -> true);
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("English name", "Scientific name", "Finnish name");
        choiceBox.setValue("English name");

        TextField textField = new TextField();
        textField.setPromptText("Search by species");
        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue())
            {
                case "English name":
                    filteredSpecies.setPredicate(p -> p.getEnglishName().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Scientific name":
                    filteredSpecies.setPredicate(p -> p.getScientificName().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Finnish name":
                    filteredSpecies.setPredicate(p -> p.getFinnishName().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
            }
        });

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textField.setText("");
                filteredSpecies.setPredicate(null);
            }
        });
        
        
        HBox speciesPane = new HBox(choiceBox, textField);
//        speciesPane.setAlignment(Pos.CENTER);
        Label speciesLabel = new Label("Species");
        inputWindow.setLabelStyle(speciesLabel);
        
        speciesPane.getChildren().addAll(speciesLabel, choiceBox, textField);
        pane.getChildren().addAll(speciesPane);
        return speciesPane;
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
        placePane.getChildren().addAll(placeLabel, listView);
        pane.getChildren().addAll(placePane);
        return listView;
    }
}
