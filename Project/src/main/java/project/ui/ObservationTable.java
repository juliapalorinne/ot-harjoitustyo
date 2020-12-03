
package project.ui;

import project.domain.DisplayableObservation;
import java.time.LocalTime;
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

public class ObservationTable {
    private DisplayableObservationService displayObsService;
    private TableView table;
    private Button addButton;
    private Button searchButton;
    private Button logoutButton;
    
    private ObservableList<DisplayableObservation> observations;
    
    
    public ObservationTable(ObservationService observationService, SpeciesService speciesService, PlaceService placeService) {
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService);
        this.observations = FXCollections.observableArrayList();
        addButton = new Button("Add new observation");
        searchButton = new Button("Search observations");
        logoutButton = new Button("Logout");
    }  
    
    
    public Scene observationScene() throws Exception {
        redrawObservationList();
        Scene observationScene = new Scene(createTable(), 800, 600);
        return observationScene;
    }
    
    
    public VBox createTable() throws Exception {
        table = new TableView();
        Label label = new Label("Observations");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        redrawObservationList();
 
        TableColumn<DisplayableObservation, String> speciesCol = new TableColumn("Species");
        speciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
        speciesCol.setMaxWidth( 1f * Integer.MAX_VALUE * 30 );
        
        TableColumn<DisplayableObservation, Integer> individualCol = new TableColumn("Individuals");
        individualCol.setCellValueFactory(new PropertyValueFactory<>("individuals"));
        individualCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );
        
        TableColumn<DisplayableObservation, String> placeCol = new TableColumn("Place");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        placeCol.setMaxWidth( 1f * Integer.MAX_VALUE * 35 );
        
        TableColumn<DisplayableObservation, Date> dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );
        
        TableColumn<DisplayableObservation, LocalTime> timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeCol.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );
        
        TableColumn<DisplayableObservation, String> infoCol = new TableColumn("Info");
        infoCol.setCellValueFactory(new PropertyValueFactory<>("info"));
        infoCol.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );
        
        table.setItems(observations);
        table.getColumns().addAll(speciesCol, individualCol, placeCol, 
                dateCol, timeCol, infoCol);
 
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #FFB6C1;");
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(menu(), label, table, searchObservations(), createObservation());
        vbox.setVgrow(table, Priority.ALWAYS);
        return vbox;
    }  
    
    public void redrawObservationList() throws Exception {
        displayObsService.redrawObservationList();
        List<DisplayableObservation> obs = displayObsService.getAll();
        for (DisplayableObservation o : obs) {
            if (!observations.contains(o)) {
                observations.add(o);
            }
        }
    }
    
    
    public HBox createObservation() {
        HBox createForm = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        createForm.getChildren().addAll(spacer, addButton());
        return createForm;
    }
    
    public HBox menu() {
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
