
package project.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.domain.ObservationService;

public class NewObservation {
    private ObservationService observationService;
    private InputWindow inputWindow;
    private Button returnButton;
    
    
    public NewObservation(ObservationService observationService, InputWindow inputWindow) {
        this.observationService = observationService;
        this.inputWindow = inputWindow;
        returnButton = inputWindow.createButton("Return");
    } 

    
    public Scene createNewObservation(Stage stage, ObservationTable observationTable) {
        VBox newObsPane = inputWindow.createNewWindow();
        TextField newSpeciesInput = inputWindow.createInputField(newObsPane, "Species");
        TextField newIndividualInput = inputWindow.createInputField(newObsPane, "Individuals");
        TextField newPlaceInput = inputWindow.createInputField(newObsPane, "Place");
        DatePicker datePicker = inputWindow.createDatePicker(newObsPane, "Date");
        TextField newTimeInput = inputWindow.createInputField(newObsPane, "Time (hh:mm)");
        TextField newInfoInput = inputWindow.createBigInputField(newObsPane, "Additional info");

        Label observationCreationMessage = new Label();
        Button createNewObsButton = inputWindow.createButton("Add new observation");
        createNewObsButton.setPadding(new Insets(10));
        
        
        createNewObsButton.setOnAction(e-> {
            String species = newSpeciesInput.getText();
            String place = newPlaceInput.getText();
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(newTimeInput.getText());
            String info = newInfoInput.getText();
            int individuals = Integer.parseInt(newIndividualInput.getText());
            
            
            if (species.length() < 3 || place.length() < 2) {
                observationCreationMessage.setText("Species or place too short");
                observationCreationMessage.setTextFill(Color.RED);
            } else if (observationService.createObservation(species, individuals, place, date, time, info)) {
                newSpeciesInput.setText("");
                newIndividualInput.setText("");
                newPlaceInput.setText("");
                newTimeInput.setText("");
                newInfoInput.setText("");
                stage.setScene(observationTable.observationScene());
            }
 
        });  
        
        
        newObsPane.getChildren().addAll(observationCreationMessage, createNewObsButton, returnButton);
        Scene newObservationScene = new Scene(newObsPane, 600, 500);
        return newObservationScene;
    }
    
    public Button returnButton() {
        return this.returnButton;
    }

}
