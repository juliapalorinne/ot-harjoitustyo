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
import project.domain.DisplayableObservation;
import project.domain.StoreableObservationService;
import project.domain.Place;
import project.domain.PlaceService;
import project.domain.Species;
import project.domain.SpeciesService;

public class ShowOneObservationScene extends LoggedInScene {
    private final Button modifyButton;
    private ListView<Species> speciesList;
    private TextField individualInput;
    private ListView<Place> placeList;
    private DatePicker datePicker;
    private TextField timeInput;
    private TextField infoInput;
    
    public ShowOneObservationScene(StoreableObservationService observationService, SpeciesService speciesService, 
            PlaceService placeService) {
        this.observationService = observationService;
        this.speciesService = speciesService;
        this.placeService = placeService;
        this.modifyButton = inputWindow.createButton("Modify observation");
    } 

    public void setShowOneScene(Stage stage, DisplayableObservation o, ObservationTableScene observationTable) throws Exception {
        Scene newObservationScene = new Scene(observationInput(stage, o, observationTable), 800, 700);
        stage.setScene(newObservationScene);
    }
    
    private VBox observationInput(Stage stage, DisplayableObservation o, ObservationTableScene observationTable) throws Exception {
        VBox pane = inputWindow.createNewWindow();
        setLabels(pane, o);
        setInputFields(pane, o);
        
        modifyButton.setOnAction(e-> {
            modifyObservation(stage, o, observationTable);
        }); 
        
        returnButton.setOnAction(e -> {
            returnToObservationTable(stage, observationTable);
        });
        
        pane.getChildren().addAll(successMessage, modifyButton, returnButton);
        return pane;
    }
    
    private void setLabels(VBox pane, DisplayableObservation o) {
        Label label = inputWindow.createBigLabel("View and modify observation", 400);
        HBox infoBox = inputWindow.infoBox("Submit to modify this observation. Remember to choose the species and place from the lists.");
        HBox messageBox = inputWindow.observationBoxRow();
        messageBox.getChildren().addAll(successMessage);
        pane.getChildren().addAll(label, showObservation(o),infoBox, messageBox);
    }
    
    private VBox showObservation(DisplayableObservation o) {
        VBox vbox = inputWindow.ShowOneBox();
        HBox speciesBox = showSpecies(o);
        HBox placeBox = showPlace(o);
        HBox timeBox = showTime(o);
        HBox infoBox = showInfo(o);
        vbox.getChildren().addAll(speciesBox , placeBox, timeBox, infoBox);
        return vbox;
    }
    
    private HBox showSpecies(DisplayableObservation o) {
        HBox speciesBox = inputWindow.observationBoxRow();
        Label species = inputWindow.createSmallLabel("Species", 150);
        Label getSpecies = new Label(o.getSpecies());
        speciesBox.getChildren().addAll(species, getSpecies);
        return speciesBox;
    }
    
    private HBox showPlace(DisplayableObservation o) {
        HBox placeBox = inputWindow.observationBoxRow();
        Label place = inputWindow.createSmallLabel("Place", 150);
        Label getPlace = new Label(o.getPlace());
        placeBox.getChildren().addAll(place, getPlace);
        return placeBox;
    }
    
    private HBox showTime(DisplayableObservation o) {
        HBox timeBox = inputWindow.observationBoxRow();
        Label individuals = inputWindow.createSmallLabel("Individuals", 150);
        Label getIndividuals = new Label(Integer.toString(o.getIndividuals()));
        getIndividuals.setPrefWidth(100);
        Label date = inputWindow.createSmallLabel("Date", 60);
        Label getDate = new Label(o.getDate().toString());
        getDate.setPrefWidth(100);
        Label time = inputWindow.createSmallLabel("Time", 60);
        Label getTime = new Label(o.getTime().toString());
        timeBox.getChildren().addAll(individuals, getIndividuals, date, getDate, time, getTime);
        return timeBox;
    }
    
    private HBox showInfo(DisplayableObservation o) {
        HBox infoBox = inputWindow.observationBoxRow();
        Label privacy = inputWindow.createSmallLabel("Privacy", 150);
        Label getPrivacy = new Label(privacyToString(o.getPrivacy()));
        getPrivacy.setPrefWidth(100);
        Label info = inputWindow.createSmallLabel("Additional info", 150);
        Label getInfo = new Label(o.getInfo());
        infoBox.getChildren().addAll(privacy, getPrivacy, info, getInfo);
        return infoBox;
    }
    
    private void setInputFields(VBox pane, DisplayableObservation o) throws Exception {
        speciesList = showSpeciesInputAndListSideBySide(pane);
        speciesInput.setText(o.getSpecies());
        placeList = showPlaceInputAndListSideBySide(pane);
        placeInput.setText(o.getPlace());
        setTimeInput(pane, o);
        setIndividualAndPrivacyInput(pane, o);
        infoInput = inputWindow.createBigInputField(pane, "Additional info");
        infoInput.setText(o.getInfo());
    }
    
    private void setIndividualAndPrivacyInput(VBox pane, DisplayableObservation o) {
        HBox individualsAndPrivacy = new HBox(10);
        individualsAndPrivacy.setPadding(new Insets(10));Label label = inputWindow.createSmallLabel("Individuals", 150);
        individualInput = inputWindow.createTextField(100);
        individualInput.setText("" + o.getIndividuals());
        individualsAndPrivacy.getChildren().addAll(label, individualInput, privacyButtons());
        pane.getChildren().addAll(individualsAndPrivacy);
    }
    
    private void setTimeInput(VBox pane, DisplayableObservation o) {
        HBox datePane = new HBox(10);
        datePicker = inputWindow.createDatePicker(datePane, "Date");
        datePicker.setValue(o.getDate());
        HBox timePane = new HBox(10);
        timePane.setPadding(new Insets(10));
        timeInput = inputWindow.createTextField(150);
        timeInput.setText(o.getTime().toString());
        Label timeLabel = inputWindow.createSmallLabel("Time (hh:mm)", 150);
        timePane.getChildren().addAll(timeLabel, timeInput);
        datePane.getChildren().addAll(timePane);
        pane.getChildren().addAll(datePane);
    }

    private void modifyObservation(Stage stage, DisplayableObservation o, ObservationTableScene observationTable) {
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
            try {
                if (observationService.modifyObservation(o.getId(), species, individuals, place, date.toString(), time.toString(), info, privacy)) {
                    try {
                        observationTable.setObservationScene(stage);
                    } catch (Exception ex) {
                        successMessage.setText("Something went wrong! Try again.");
                    }
                } else {
                    successMessage.setText("Invalid input! Try again.");
                }
            } catch (Exception ex) {
                successMessage.setText("Could not modify observation. Try again.");
            }
        }
    }
    
    private void returnToObservationTable(Stage stage, ObservationTableScene observationTable) {
        try {
            observationTable.setObservationScene(stage);
        } catch (Exception ex) {
            successMessage.setText("Could not return. Try again.");
        }
}
}
