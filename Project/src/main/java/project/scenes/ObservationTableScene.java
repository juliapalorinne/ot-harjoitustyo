package project.scenes;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.DisplayableObservation;
import project.domain.DisplayableObservationService;
import project.domain.StoreableObservationService;
import project.domain.PlaceService;
import project.domain.SpeciesService;

public class ObservationTableScene extends LoggedInScene {
    
    private TableView table;
    private final Button addButton;
    private final Button searchButton;
    private final Button logoutButton;
    
    
    public ObservationTableScene(StoreableObservationService observationService,
            SpeciesService speciesService, PlaceService placeService) {
        
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService);
        this.observations = FXCollections.observableArrayList();
        addButton = inputWindow.createButton("Add new observation");
        searchButton = inputWindow.createButton("Search observations");
        logoutButton = inputWindow.createButton("Logout");
        this.showOne = new ShowOneObservationScene(observationService, speciesService, placeService);
    }  
    
    
    public Scene observationScene(Stage stage) throws Exception {
        Scene observationScene = new Scene(createTable(stage), 800, 600);
        return observationScene;
    }
    
    
    private VBox createTable(Stage stage) throws Exception {
        HBox infoBox = inputWindow.infoBox("Double click observation to open or modify. " + successMessage().getText());
        table = new TableView();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        redrawObservationList();
        table.setItems(observations);
        
        ArrayList<TableColumn> columns = displayObsService.getColumns();
        
        table.getColumns().addAll(columns);
        
        Label label = inputWindow.createBigLabel("Observations", 200);

        table.setOnMouseClicked((MouseEvent event) -> {
            Clicked(stage);
        });
        
        VBox vbox = inputWindow.createNewWindow();
        vbox.getChildren().addAll(menu(), label, infoBox, table, searchObservations(), createObservation());
        VBox.setVgrow(table, Priority.ALWAYS);
        return vbox;
    }  
    
    private void Clicked(Stage stage) {
        try {
            DisplayableObservation o = (DisplayableObservation) table.getSelectionModel().getSelectedItem();
            stage.setScene(showOne.showOneScene(stage, this, o));
        } catch(Exception e) {
            successMessage.setText("Could not open the observation. Try again!");
            e.printStackTrace();
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
        searchForm.getChildren().addAll(spacer, searchButton());
        return searchForm;
    }
    
    
}
