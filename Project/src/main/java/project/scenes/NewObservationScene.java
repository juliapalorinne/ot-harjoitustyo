package project.scenes;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.StoreableObservationService;
import project.domain.Place;
import project.domain.PlaceService;
import project.domain.Species;
import project.domain.SpeciesService;

public class NewObservationScene extends LoggedInScene {
    private final Button createNewObsButton;
    private ListView<Species> speciesList;
    private TextField individualInput;
    private ListView<Place> placeList;
    private DatePicker datePicker;
    private TextField timeInput;
    private TextField infoInput;
    
    public NewObservationScene(StoreableObservationService observationService, SpeciesService speciesService, 
            PlaceService placeService) {
        this.observationService = observationService;
        this.speciesService = speciesService;
        this.placeService = placeService;
        this.createNewObsButton = inputWindow.createButton("Add new observation");
    } 

    public Scene createNewObservationScene(Stage stage, ObservationTableScene observationTable) throws Exception {
        Scene newObservationScene = new Scene(observationInput(stage, observationTable), 800, 700);
        return newObservationScene;
    }
    
    
    private VBox observationInput(Stage stage, ObservationTableScene observationTable) throws Exception {
        VBox newObsPane = inputWindow.createNewWindow();
        setInputFields(newObsPane);
        
        createNewObsButton.setOnAction(e-> {
           createNewObservation(stage, observationTable);
        }); 
        
        returnButton.setOnAction(e -> {
            returnToObservationTable(stage, observationTable);
        });
        
        addNewSpeciesButton.setOnAction(e -> {
            addNewSpecies(stage, observationTable);
        });
        
        addNewPlaceButton.setOnAction(e -> {
            addNewPlace(stage, observationTable);
        });
        
        newObsPane.getChildren().addAll(successMessage, createNewObsButton, returnButton);
        return newObsPane;
    }
    
    private void setInputFields(VBox newObsPane) throws Exception {
        Label label = inputWindow.createBigLabel("Create new observation", 400);
        newObsPane.getChildren().addAll(label);
        speciesList = showSpeciesInputAboveTheList(newObsPane);
        individualInput = inputWindow.createInputField(newObsPane, "Individuals");
        placeList = showPlaceInputAboveTheList(newObsPane);
        setTimeInput(newObsPane);
        infoInput = inputWindow.createBigInputField(newObsPane, "Additional info");
        newObsPane.getChildren().addAll(privacyButtons());
    }
    
    private void setTimeInput(VBox newObsPane) {
        HBox datePane = new HBox(10);
        datePicker = inputWindow.createDatePicker(datePane, "Date");
        
        HBox timePane = new HBox(10);
        timePane.setPadding(new Insets(10));
        timeInput = inputWindow.createTextField(150);
        Label timeLabel = inputWindow.createSmallLabel("Time (hh:mm)", 150);
        timePane.getChildren().addAll(timeLabel, timeInput);
        datePane.getChildren().addAll(timePane);
        
        newObsPane.getChildren().addAll(datePane);
    }
    
    private void createNewObservation(Stage stage, ObservationTableScene observationTable) {
        successMessage.setText("");
        LocalDate date = datePicker.getValue();
        LocalTime time = null;
        try {
            time = LocalTime.parse(timeInput.getText());
        } catch (Exception ex) {
            successMessage.setText("Give the time in the correct form (hh:mm)");
        }
        
        String info = infoInput.getText();
        int individuals = getIndividualInput(individualInput);

        Species species = speciesList.getSelectionModel().getSelectedItem();
        Place place = placeList.getSelectionModel().getSelectedItem();
        int privacy = getPrivacyInput();

        if (checkObservationInputValidity(individuals, privacy, time, info)) {
            if (observationService.createObservation(species, individuals, place, date, time, info, privacy)) {
                try {
                    resetInputFields();
                    observationTable.setObservationScene(stage);
                } catch (Exception ex) {
                    successMessage.setText("Something went wrong! Try again.");
                }
            } else {
                successMessage.setText("Invalid input! Try again.");
            }
        }
    }
    
    private void returnToObservationTable(Stage stage, ObservationTableScene observationTable) {
        successMessage.setText("");
        try {
            observationTable.setObservationScene(stage);
        } catch (Exception ex) {
            successMessage.setText("Could not return. Try again.");
        }
    }
    
    private void addNewPlace(Stage stage, ObservationTableScene observationTable) {
        successMessage.setText("");
        try {
            NewPlaceScene newPlace = new NewPlaceScene(placeService);
            newPlace.setNewPlaceScene(stage, createNewObservationScene(stage, observationTable));
        } catch (Exception ex) {
            successMessage.setText("Could not open new place form. Try again.");
        }
    }
    
    private void addNewSpecies(Stage stage, ObservationTableScene observationTable) {
        successMessage.setText("");
        try {
            NewSpeciesScene newSpecies = new NewSpeciesScene(speciesService);
            newSpecies.setNewSpeciesScene(stage, createNewObservationScene(stage, observationTable));
        } catch (Exception ex) {
            successMessage.setText("Could not open new species form. Try again.");
        }
    }
    
    private void resetInputFields() throws Exception {
        individualInput.setText("");
        timeInput.setText("");
        infoInput.setText("");
    }
    
    
    
    
}
