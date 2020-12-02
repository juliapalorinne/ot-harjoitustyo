
package project.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.domain.PlaceService;

public class NewPlace {
    private InputWindow inputWindow;
    private PlaceService placeService;
    private Button returnButton;
    
    public NewPlace(InputWindow inputWindow, PlaceService placeService) {
        this.inputWindow = inputWindow;
        this.placeService = placeService;
        returnButton = inputWindow.createButton("Return");
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
   
            if (country.length() < 2 || city.length() < 2) {
                placeCreationMessage.setText("Country or city too short");
                placeCreationMessage.setTextFill(Color.BLUE);              
            } else try {
                if (placeService.createPlace(country, city, spot, type)) {
                    placeCreationMessage.setText("");
                    stage.setScene(newObservationScene);
                } else {
                    placeCreationMessage.setText("Error while creating new place");
                    placeCreationMessage.setTextFill(Color.BLUE);        
                }
            } catch (Exception ex) {
                Logger.getLogger(NewPlace.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        });  
        
        newPlacePane.getChildren().addAll(placeCreationMessage, createNewPlaceButton, returnButton);
        Scene newPlaceScene = new Scene(newPlacePane, 400, 400);
        return newPlaceScene;
    }
    
    public Button returnButton() {
        return this.returnButton;
    }
}
