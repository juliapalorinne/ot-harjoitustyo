
package project.scenes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.domain.DisplayableObservation;
import project.domain.DisplayableObservationService;
import project.domain.StoreableObservationService;
import project.domain.PlaceService;
import project.domain.SpeciesService;
import project.ui.InputWindow;

public class SearchScene {
    private InputWindow inputWindow;
    private DisplayableObservationService displayObsService;
    private SpeciesService speciesService;
    private PlaceService placeService;
    private Button returnButton;
    
    private ObservableList<DisplayableObservation> observations;
    
    public SearchScene(InputWindow inputWindow, StoreableObservationService observationService, 
            SpeciesService speciesService, PlaceService placeService) {
        this.inputWindow = inputWindow;
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService);
        this.observations = FXCollections.observableArrayList();
        this.speciesService = speciesService;
        this.placeService = placeService;
        returnButton = inputWindow.createButton("Return");
    } 

    public Scene searchScene(Stage stage) throws Exception {
        Scene searchScene = new Scene(createTable(), 600, 600);
        return searchScene;
    }
    
    private VBox createTable() throws Exception {
        VBox vbox = inputWindow.createNewWindow();
        TextField speciesInput = inputWindow.createInputField(vbox, "Search by species");
        TextField placeInput = inputWindow.createInputField(vbox, "Search by place");
        
        TableView table = new TableView();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        redrawObservationList();
        table.setItems(observations);
        
        Button searchButton = inputWindow.createButton("Search");
        searchButton.setPadding(new Insets(10));

        searchButton.setOnAction(e-> {
            String searchSpecies = speciesInput.getText();
            String searchPlace = placeInput.getText();
            try {
                table.setItems(filteredBySpeciesAndPlace(searchSpecies, searchPlace));
            } catch (Exception ex) {
                Logger.getLogger(SearchScene.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        ArrayList<TableColumn> columns = displayObsService.getColumns();
        table.getColumns().addAll(columns);
        
        Label label = new Label("Search observations");
        label.setFont(new Font("Arial", 20));

        
        vbox.getChildren().addAll(label, table, searchButton, returnButton);
        vbox.setVgrow(table, Priority.ALWAYS);
        return vbox;
    }  
    
    
    
    public Button returnButton() {
        return this.returnButton;
    }

    private void redrawObservationList() throws Exception {
        displayObsService.redrawObservationList();
        List<DisplayableObservation> obs = displayObsService.getAll();
        for (DisplayableObservation o : obs) {
            if (!observations.contains(o)) {
                observations.add(o);
            }
        }
    }
    
    private ObservableList<DisplayableObservation> filteredBySpeciesAndPlace(String species, String place) throws Exception {
        redrawObservationList();
        
        if (species.isEmpty() && place.isEmpty()) {
            return observations;
        }
        if (species.isEmpty()) {
            return FXCollections.observableArrayList(displayObsService.filterByPlace(place));
        }
        
        if (place.isEmpty()) {
            return FXCollections.observableArrayList(displayObsService.filterBySpecies(species));
        }
        
        return FXCollections.observableArrayList(displayObsService.filterBySpeciesAndPlace(species, place));
    }
   
    
   
}
