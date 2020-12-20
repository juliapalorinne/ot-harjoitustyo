package project.scenes;

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
    private final Button modifyButton;
    private TextField newCountryInput;
    private TextField newCityInput;
    private TextField newSpotInput;
    private TextField newTypeInput;
    
    public ShowOnePlaceScene(PlaceService placeService) {
        this.placeService = placeService;
        modifyButton = inputWindow.createButton("Modify");
    } 
    
    public void setOnePlaceScene(Stage stage, ShowPlaceListScene placeList, Place place) throws Exception {
        Scene newPlaceScene = new Scene(placeScene(stage, placeList, place), 500, 600);
        stage.setScene(newPlaceScene);
    }
    
    private VBox placeScene(Stage stage, ShowPlaceListScene placeList, Place place) throws Exception {
        VBox placePane = inputWindow.createNewWindow();
        setLabels(placePane, place);
        setInputFields(placePane, place);

        modifyButton.setOnAction(e-> {
            modifyPlace(stage, placeList, place);
        });  
        
        returnButton.setOnAction(e -> {
            returnToPlaceList(stage, placeList);
        });
        
        placePane.getChildren().addAll(modifyButton, returnButton);
        return placePane;
    }
    
    private void setLabels(VBox placePane, Place place) {
        Label label = inputWindow.createBigLabel("Show this places", 300);
        Label newLabel = inputWindow.createBigLabel("Modify place", 300);
        HBox messageBox = inputWindow.observationBoxRow();
        messageBox.getChildren().addAll(successMessage);
        placePane.getChildren().addAll(label, showPlace(place), messageBox, newLabel);
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

    private void setInputFields(VBox placePane, Place place) {
        newCountryInput = inputWindow.createInputField(placePane, "Country");
        newCountryInput.setText(place.getCountry());
        newCityInput = inputWindow.createInputField(placePane, "City");
        newCityInput.setText(place.getCity());
        newSpotInput = inputWindow.createInputField(placePane, "Spot");
        newSpotInput.setText(place.getSpot());
        newTypeInput = inputWindow.createInputField(placePane, "Type");
        newTypeInput.setText(place.getType());
    }

    private void modifyPlace(Stage stage, ShowPlaceListScene placeList, Place place) {
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
    }
    
    private void returnToPlaceList(Stage stage, ShowPlaceListScene placeList) {
        try {
            stage.setScene(placeList.placeScene(stage));
        } catch (Exception ex) {
            successMessage.setText("Could not return to place list. Try again.");
        }
    }
}
