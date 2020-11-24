
package project.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.domain.ObservationService;

public class NewObservation {
    private ObservationService observationService;
    private InputWindow inputWindow;
    
    
    public NewObservation(ObservationService observationService, InputWindow inputWindow) {
        this.observationService = observationService;
        this.inputWindow = inputWindow;
    } 

    
    public Scene createNewObservation(Stage stage, ObservationTable observationTable) {
        VBox newObsPane = new VBox(10);
        TextField newSpeciesInput = inputWindow.createInputField(newObsPane, "Species");
        TextField newIndividualInput = inputWindow.createInputField(newObsPane, "Individuals");
        TextField newPlaceInput = inputWindow.createInputField(newObsPane, "Place");
        TextField newDateInput = inputWindow.createInputField(newObsPane, "Date (dd/mm/yyyy)");
        TextField newTimeInput = inputWindow.createInputField(newObsPane, "Time (hh:mm)");
        TextField newInfoInput = inputWindow.createInputField(newObsPane, "Additional info");
        
        Label obsCreationMessage = new Label();
        Button createNewObsButton = new Button("Add new observation");
        createNewObsButton.setPadding(new Insets(10));
        
        
        createNewObsButton.setOnAction(e-> {
            String species = newSpeciesInput.getText();
            String place = newPlaceInput.getText();
            String d = newDateInput.getText();
            SimpleDateFormat dformatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = dformatter.parse(d);
            } catch (ParseException ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            LocalTime time = LocalTime.parse(newTimeInput.getText());
            String info = newInfoInput.getText();
            int individuals = Integer.parseInt(newIndividualInput.getText());
            
            Label observationCreationMessage = new Label();
            Label loginMessage = new Label();
            
            if (species.length() < 3 || place.length() < 2) {
                observationCreationMessage.setText("Species or place too short");
                observationCreationMessage.setTextFill(Color.RED);
            } else if (observationService.createObservation(species, individuals, place, date, time, info)) {
                obsCreationMessage.setText("New observation created");                
                loginMessage.setText("Observations saved");
                loginMessage.setTextFill(Color.YELLOW);
                stage.setScene(observationTable.observationScene());
                newObsPane.getChildren().addAll(observationCreationMessage);
            }
 
        });  
        newObsPane.getChildren().addAll(obsCreationMessage, createNewObsButton);
        Scene newObservationScene = new Scene(newObsPane, 600, 500);
        return newObservationScene;
    }  
    
    public Boolean newObservationCreated() {
        
        return false;
    }

}
