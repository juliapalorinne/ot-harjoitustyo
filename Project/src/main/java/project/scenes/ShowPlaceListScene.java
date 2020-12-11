package project.scenes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.domain.Place;
import project.domain.PlaceService;
import project.ui.ProjectUi;

public class ShowPlaceListScene extends LoggedInScene {
    private ListView<Place> listView;
    private final ShowOnePlaceScene showOnePlace;
    private final ObservationTableScene observationTable;
    
    public ShowPlaceListScene(PlaceService placeService, ObservationTableScene observationTable) {
        this.placeService = placeService;
        this.showOnePlace = new ShowOnePlaceScene(placeService);
        this.observationTable = observationTable;
    } 

    public Scene placeScene(Stage stage) throws Exception {
        VBox placePane = inputWindow.createNewWindow();
        Label label = inputWindow.createBigLabel("List of all places", 300);
        placePane.getChildren().addAll(label);
        HBox infoBox = inputWindow.infoBox("Double click place to open or modify.");
        placePane.getChildren().addAll(infoBox);
        listView = listPlaces(placePane);
        
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { 
                Clicked(stage);
            }
        });
        
        HBox messageBox = inputWindow.observationBoxRow();
        messageBox.getChildren().addAll(successMessage());
        placePane.getChildren().addAll(messageBox);
        
        Label newLabel = inputWindow.createBigLabel("Add new place", 300);
        placePane.getChildren().addAll(newLabel);
        TextField newCountryInput = inputWindow.createInputField(placePane, "Country");
        TextField newCityInput = inputWindow.createInputField(placePane, "City");
        TextField newSpotInput = inputWindow.createInputField(placePane, "Spot");
        TextField newTypeInput = inputWindow.createInputField(placePane, "Type");
        
        
        Button createNewPlaceButton = inputWindow.createButton("Create");

        createNewPlaceButton.setOnAction(e -> {
            String country = newCountryInput.getText();
            String city = newCityInput.getText();
            String spot = newSpotInput.getText();
            String type = newTypeInput.getText();
   
            if (country.length() < 3 || city.length() < 3 || spot.length() < 3) {
                successMessage.setText("Country, city or is too short.");
            } else {
                try {
                    if (placeService.createPlace(country, city, spot, type)) {
                        successMessage.setText("");
                        stage.setScene(observationTable.observationScene(stage));
                    } else {
                        successMessage.setText("Error while creating new place.");
                    }
                } catch (Exception ex) {
                    successMessage.setText("Something went wrong! Try again.");
                }
            }
 
        });  
        
        returnButton().setOnAction(e -> {
            try {
                stage.setScene(observationTable.observationScene(stage));
            } catch (Exception ex) {
                successMessage.setText("Something went wrong! Try again.");
            }
        });
        
        placePane.getChildren().addAll(createNewPlaceButton, returnButton);
        Scene newPlaceScene = new Scene(placePane, 500, 600);
        return newPlaceScene;
    }
    
    private void Clicked(Stage stage) {
        try {
            Place p = (Place) listView.getSelectionModel().getSelectedItem();
            stage.setScene(showOnePlace.placeScene(stage, this, p));
        } catch(Exception e) {
            successMessage.setText("Could not open the observation. Try again!");
            e.printStackTrace();
        }
    }
    
}
