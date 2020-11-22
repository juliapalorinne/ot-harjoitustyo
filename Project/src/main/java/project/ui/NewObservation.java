
package project.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.domain.Observation;
import project.domain.ObservationService;

public class NewObservation {
    private ObservationService observationService;
    private InputWindowService windowService;
    
    
    public NewObservation(ObservationService observationService, InputWindowService windowService) {
        this.observationService = observationService;
        this.windowService = windowService;
    } 

    
    public Scene createNewObservation(Scene scene, ObservationTable observationTable, Scene newScene) {
        VBox newObsPane = new VBox(10);
        TextField newSpeciesInput = windowService.createInputField(newObsPane, "Species");
        TextField newIndividualInput = windowService.createInputField(newObsPane, "Individuals");
        TextField newPlaceInput = windowService.createInputField(newObsPane, "Place");
        TextField newDateInput = windowService.createInputField(newObsPane, "Date (dd/mm/yyyy)");
        TextField newTimeInput = windowService.createInputField(newObsPane, "Time (hh:mm)");
        TextField newInfoInput = windowService.createInputField(newObsPane, "Additional info");
        
        Label obsCreationMessage = new Label();
        Button createNewObsButton = new Button("Create");
        createNewObsButton.setPadding(new Insets(10));
        
        
        createNewObsButton.setOnAction(e->{
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
            
            if ( species.length()<3 || place.length()<2 ) {
                observationCreationMessage.setText("Species or place too short");
                observationCreationMessage.setTextFill(Color.RED);
            } else if (observationService.createObservation(species, individuals, place, date, time, info)){
                obsCreationMessage.setText("New observation created");                
                loginMessage.setText("Observations saved");
                loginMessage.setTextFill(Color.YELLOW);
                observationTable.createTable(scene, newScene);
                
                newObsPane.getChildren().addAll(observationCreationMessage);
            }
 
        });  
        newObsPane.getChildren().addAll(createNewObsButton);
        Scene newObservationScene = new Scene(newObsPane, 600, 500);
        return newObservationScene;
    }  

}
