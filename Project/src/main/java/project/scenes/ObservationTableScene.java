
package project.scenes;

import project.domain.DisplayableObservation;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.domain.DisplayableObservationService;
import project.domain.Observation;
import project.domain.ObservationService;
import project.domain.PlaceService;
import project.domain.SpeciesService;

public class ObservationTableScene {
    private DisplayableObservationService displayObsService;
    private TableView table;
    private Button addButton;
    private Button searchButton;
    private Button logoutButton;
    
    private ObservableList<DisplayableObservation> observations;
    
    
    public ObservationTableScene(ObservationService observationService, SpeciesService speciesService, PlaceService placeService) {
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService);
        this.observations = FXCollections.observableArrayList();
        addButton = new Button("Add new observation");
        searchButton = new Button("Search observations");
        logoutButton = new Button("Logout");
    }  
    
    
    public Scene observationScene() throws Exception {
        Scene observationScene = new Scene(createTable(), 800, 600);
        return observationScene;
    }
    
    
    private VBox createTable() throws Exception {
        TableView table = new TableView();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        redrawObservationList();
        
        table.setItems(observations);
        
        ArrayList<TableColumn> columns = displayObsService.getColumns();
        table.getColumns().addAll(columns);
        
        
        Label label = new Label("Observations");
        label.setFont(new Font("Arial", 20));

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #FFB6C1;");
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(menu(), label, table, searchObservations(), createObservation());
        vbox.setVgrow(table, Priority.ALWAYS);
        return vbox;
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
    
    
    private HBox createObservation() {
        HBox createForm = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        createForm.getChildren().addAll(spacer, addButton());
        return createForm;
    }
    
    private HBox menu() {
        HBox menuPane = new HBox(10);
        Label menuLabel = new Label("Menu");
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        menuPane.getChildren().addAll(menuLabel, menuSpacer, logoutButton); 
        return menuPane;
    }
    
    public Button addButton() {
        return this.addButton;
    }
    
    public Button searchButton() {
        return this.searchButton;
    }
    
    public Button logoutButton() {
        return this.logoutButton;
    }    
    
    
    public HBox searchObservations() {
        HBox searchForm = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        searchForm .getChildren().addAll(spacer, searchButton());
        return searchForm;
    }
    
    
}
