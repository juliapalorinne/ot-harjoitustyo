package project.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.Place;
import project.domain.PlaceService;

public class ShowOnePlaceScene extends LoggedInScene {
    
    public ShowOnePlaceScene(PlaceService placeService) {
        this.placeService = placeService;
    } 
    
    private VBox showPlace(Place p) {
        VBox vbox = inputWindow.ShowOneBox();
        
        HBox countryBox = inputWindow.observationBoxRow();
        Label country = inputWindow.createSmallLabel("Country", 150);
        Label getCountry = new Label(p.getCountry());
        countryBox.getChildren().addAll(country, getCountry);
        
        HBox cityBox = inputWindow.observationBoxRow();
        Label place = inputWindow.createSmallLabel("City", 150);
        Label getPlace = new Label(p.getCity());
        cityBox.getChildren().addAll(place, getPlace);
        
        HBox spotBox = inputWindow.observationBoxRow();
        Label spot = inputWindow.createSmallLabel("Individuals", 150);
        Label getSpot = new Label(p.getSpot());
        spotBox.getChildren().addAll(spot, getSpot);
        
        HBox typeBox = inputWindow.observationBoxRow();
        Label type = inputWindow.createSmallLabel("Privacy", 150);
        Label getType = new Label(p.getType());
        typeBox.getChildren().addAll(type, getType);
        vbox.getChildren().addAll(countryBox , cityBox, spotBox, typeBox);
        return vbox;
    }

    public Scene placeScene(Stage stage, ShowPlaceListScene placeList, Place place) throws Exception {
        VBox placePane = inputWindow.createNewWindow();
        Label label = inputWindow.createBigLabel("Show this places", 300);
        Label newLabel = inputWindow.createBigLabel("Modify place", 300);
        HBox messageBox = inputWindow.observationBoxRow();
        messageBox.getChildren().addAll(successMessage);
        placePane.getChildren().addAll(label, showPlace(place), messageBox, newLabel);
        
        TextField newCountryInput = inputWindow.createInputField(placePane, "Country");
        newCountryInput.setText(place.getCountry());
        TextField newCityInput = inputWindow.createInputField(placePane, "City");
        newCityInput.setText(place.getCity());
        TextField newSpotInput = inputWindow.createInputField(placePane, "Spot");
        newSpotInput.setText(place.getSpot());
        TextField newTypeInput = inputWindow.createInputField(placePane, "Type");
        newTypeInput.setText(place.getType());
        
        Button modifyButton = inputWindow.createButton("Modify");
        modifyButton.setPadding(new Insets(10));

        modifyButton.setOnAction(e-> {
            String country = newCountryInput.getText();
            String city = newCityInput.getText();
            String spot = newSpotInput.getText();
            String type = newTypeInput.getText();
   
            if (country.length() < 3 || city.length() < 3 || spot.length() < 3) {
                successMessage.setText("Country, city or is too short.");
            } else {
                try {
                    if (placeService.modifyPlace(place.getId(), country, city, spot, type)) {
                        successMessage.setText("");
                        stage.setScene(placeList.placeScene(stage));
                    } else {
                        successMessage.setText("Error while modifying the place.");
                    }
                } catch (Exception ex) {
                    successMessage.setText("Something went wrong! Try again.");
                }
            }
 
        });  
        
        returnButton().setOnAction(e -> {
            try {
                stage.setScene(placeList.placeScene(stage));
            } catch (Exception ex) {
                successMessage.setText("Something went wrong! Try again.");
            }
        });
        
        placePane.getChildren().addAll(modifyButton, returnButton);
        Scene newPlaceScene = new Scene(placePane, 500, 600);
        return newPlaceScene;
    }

}
