package project.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.PlaceService;

public class NewPlaceScene extends LoggedInScene {
    
    public NewPlaceScene(PlaceService placeService) {
        this.placeService = placeService;
    } 

    public Scene newPlaceScene(Stage stage, Scene newObservationScene) {
        VBox newPlacePane = inputWindow.createNewWindow();
        TextField newCountryInput = inputWindow.createInputField(newPlacePane, "Country");
        TextField newCityInput = inputWindow.createInputField(newPlacePane, "City");
        TextField newSpotInput = inputWindow.createInputField(newPlacePane, "Spot");
        TextField newTypeInput = inputWindow.createInputField(newPlacePane, "Type");
        
        Label placeCreationMessage = new Label();
        
        Button createNewPlaceButton = inputWindow.createButton("Create");
        createNewPlaceButton.setPadding(new Insets(10));

        createNewPlaceButton.setOnAction(e-> {
            String country = newCountryInput.getText();
            String city = newCityInput.getText();
            String spot = newSpotInput.getText();
            String type = newTypeInput.getText();
   
            if (country.length() < 3 || city.length() < 3 || spot.length() < 3) {
                placeCreationMessage.setText("Country, city or spot is too short");
            } else {
                try {
                    if (placeService.createPlace(country, city, spot, type)) {
                        placeCreationMessage.setText("");
                        stage.setScene(newObservationScene);
                    } else {
                        placeCreationMessage.setText("Error while creating new place.");
                    }
                } catch (Exception ex) {
                    successMessage.setText("Something went wrong! Try again.");
                }
            }
 
        });  
        
        newPlacePane.getChildren().addAll(placeCreationMessage, createNewPlaceButton, returnButton);
        Scene newPlaceScene = new Scene(newPlacePane, 400, 400);
        return newPlaceScene;
    }
    
}
