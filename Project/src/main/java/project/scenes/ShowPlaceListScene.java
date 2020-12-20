package project.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.Place;
import project.domain.PlaceService;

public class ShowPlaceListScene extends LoggedInScene {
    private ListView<Place> listView;
    private final ShowOnePlaceScene showOnePlace;
    private final ObservationTableScene observationTable;
    
    private TextField newCountryInput;
    private TextField newCityInput;
    private TextField newSpotInput;
    private TextField newTypeInput;
    
    public ShowPlaceListScene(PlaceService placeService, ObservationTableScene observationTable) {
        this.placeService = placeService;
        this.showOnePlace = new ShowOnePlaceScene(placeService);
        this.observationTable = observationTable;
    } 

    public Scene placeScene(Stage stage) throws Exception {
        VBox placePane = inputWindow.createNewWindow();
        setListView(placePane, stage);
        setInputFields(placePane);
        
        Button createNewPlaceButton = inputWindow.createButton("Create");

        createNewPlaceButton.setOnAction(e -> {
            createNewPlace(stage);
        });  
        
        returnButton.setOnAction(e -> {
            returnToObservationTable(stage);
        });
        
        placePane.getChildren().addAll(createNewPlaceButton, returnButton);
        Scene newPlaceScene = new Scene(placePane, 500, 600);
        return newPlaceScene;
    }
    
    private void setListView(VBox placePane, Stage stage) throws Exception {
        Label label = inputWindow.createBigLabel("List of all places", 300);
        placePane.getChildren().addAll(label);
        HBox infoBox = inputWindow.infoBox("Double click place to open or modify.");
        placePane.getChildren().addAll(infoBox);
        listView = listPlaces(placePane);
        
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { 
                clicked(stage);
            }
        });
        
        HBox messageBox = inputWindow.observationBoxRow();
        messageBox.getChildren().addAll(successMessage);
        placePane.getChildren().addAll(messageBox);
    }
    
    private void clicked(Stage stage) {
        try {
            Place p = (Place) listView.getSelectionModel().getSelectedItem();
            showOnePlace.setOnePlaceScene(stage, this, p);
        } catch(Exception e) {
            successMessage.setText("Could not open the place. Try again!");
        }
    }
    
    private void setInputFields(VBox placePane) {
        Label newLabel = inputWindow.createBigLabel("Add new place", 300);
        placePane.getChildren().addAll(newLabel);
        newCountryInput = inputWindow.createInputField(placePane, "Country");
        newCityInput = inputWindow.createInputField(placePane, "City");
        newSpotInput = inputWindow.createInputField(placePane, "Spot");
        newTypeInput = inputWindow.createInputField(placePane, "Type");
        
    }
    
    private void createNewPlace(Stage stage) {
        String country = newCountryInput.getText();
        String city = newCityInput.getText();
        String spot = newSpotInput.getText();
        String type = newTypeInput.getText();

        if (country.length() < 3 || city.length() < 3 || spot.length() < 3) {
            successMessage.setText("Country, city or spot is too short.");
        } else {
            try {
                if (placeService.createPlace(country, city, spot, type)) {
                    successMessage.setText("");
                    observationTable.setObservationScene(stage);
                } else {
                    successMessage.setText("Error while creating new place.");
                }
            } catch (Exception ex) {
                successMessage.setText("Something went wrong! Try again.");
            }
        }
    }
    
    private void returnToObservationTable(Stage stage) {
        try {
            observationTable.setObservationScene(stage);
        } catch (Exception ex) {
            successMessage.setText("Something went wrong! Try again.");
        }
    }
}
