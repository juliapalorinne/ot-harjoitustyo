package project.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.PlaceService;

public class NewPlaceScene extends LoggedInScene {
    private final Button createNewPlaceButton;
    private TextField newCountryInput;
    private TextField newCityInput;
    private TextField newSpotInput;
    private TextField newTypeInput;
            
            
    public NewPlaceScene(PlaceService placeService) {
        this.placeService = placeService;
        createNewPlaceButton = inputWindow.createButton("Create");
    } 
    
    public void setNewPlaceScene(Stage stage, Scene newObservationScene) {
        Scene newPlaceScene = new Scene(newPlaceScene(stage, newObservationScene), 400, 400);
        stage.setScene(newPlaceScene);
    }

    private VBox newPlaceScene(Stage stage, Scene newObservationScene) {
        VBox newPlacePane = inputWindow.createNewWindow();
        setInputFields(newPlacePane);
        
        createNewPlaceButton.setOnAction(e -> {
            createNewPlace(stage, newObservationScene);
        });  
        
        returnButton.setOnAction(e -> {
            returnToNewObservation(stage, newObservationScene);
        });
        
        newPlacePane.getChildren().addAll(successMessage, createNewPlaceButton, returnButton);
        
        return newPlacePane;
    }
    
    private void setInputFields(VBox placePane) {
        Label newLabel = inputWindow.createBigLabel("Add new place", 300);
        placePane.getChildren().addAll(newLabel);
        newCountryInput = inputWindow.createInputField(placePane, "Country");
        newCityInput = inputWindow.createInputField(placePane, "City");
        newSpotInput = inputWindow.createInputField(placePane, "Spot");
        newTypeInput = inputWindow.createInputField(placePane, "Type");
    }
    
    private void createNewPlace(Stage stage, Scene newObservationScene) {
        String country = newCountryInput.getText();
        String city = newCityInput.getText();
        String spot = newSpotInput.getText();
        String type = newTypeInput.getText();

        if (country.length() < 3 || city.length() < 3 || spot.length() < 3) {
            successMessage.setText("Country, city or spot is too short");
        } else {
            try {
                if (placeService.createPlace(country, city, spot, type)) {
                    successMessage.setText("");
                    stage.setScene(newObservationScene);
                } else {
                    successMessage.setText("Error while creating new place.");
                }
            } catch (Exception ex) {
                successMessage.setText("Something went wrong! Try again.");
            }
        }
    }
    
    private void returnToNewObservation(Stage stage, Scene newObservationScene) {
        try {
            stage.setScene(newObservationScene);
        } catch (Exception ex) {
            successMessage.setText("Could not return. Try again.");
        }
    }
    
}
