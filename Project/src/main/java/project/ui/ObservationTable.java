
package project.ui;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
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
import project.domain.Observation;
import project.domain.ObservationService;
import project.domain.PlaceService;
import project.domain.SpeciesService;

public class ObservationTable {
    private ObservationService observationService;   
    private SpeciesService speciesService;
    private PlaceService placeService;
    private TableView table;
    private Button addButton;
    private Button logoutButton;
    
    private ObservableList<Observation> observations;
    
    
    public ObservationTable(ObservationService observationService, SpeciesService speciesService, PlaceService placeService) {
        this.observationService = observationService;
        this.speciesService = speciesService;
        this.placeService = placeService;
        this.observations = FXCollections.observableArrayList();
        addButton = new Button("Add new observation");
        logoutButton = new Button("Logout");
    }  
    
    
    
    public VBox createTable() throws Exception {
        table = new TableView();
        Label label = new Label("Observations");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
        redrawObservationList();
 
        TableColumn<Observation, String> speciesCol = new TableColumn("Species");
        speciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
        
        TableColumn<Observation, Integer> individualCol = new TableColumn("Individuals");
        individualCol.setCellValueFactory(new PropertyValueFactory<>("individuals"));
        
        TableColumn<Observation, String> placeCol = new TableColumn("Place");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        
        TableColumn<Observation, Date> dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn<Observation, LocalTime> timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        
        TableColumn<Observation, String> infoCol = new TableColumn("Info");
        infoCol.setCellValueFactory(new PropertyValueFactory<>("info"));
        
        table.setItems(observations);
        table.getColumns().addAll(speciesCol, individualCol, placeCol, 
                dateCol, timeCol, infoCol);
 
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #FFB6C1;");
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(menu(), label, table, createObservation());
        
        return vbox;
    }  
    

    
    public void redrawObservationList() throws Exception {
        List<Observation> observationlist = observationService.getAll();
        observationlist.forEach(obs-> {
            if (!observations.contains(obs)) {
                observations.add(obs);
            }
        });     
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
    
    public Button logoutButton() {
        return this.logoutButton;
    }    
    
    public Scene observationScene() throws Exception {
        Scene observationScene = new Scene(new Group());
        ((Group) observationScene.getRoot()).getChildren().addAll(createTable());
        return observationScene;
    }
}
