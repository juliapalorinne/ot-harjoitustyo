package project.scenes;

import java.util.ArrayList;
import javafx.collections.FXCollections;
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
    private final Button listSpeciesButton;
    private final Button listPlacesButton;
    private final Button logoutButton;
    private final ShowSpeciesListScene showSpecies;
    private final ShowPlaceListScene showPlaces;
    
    
    public ObservationTableScene(StoreableObservationService observationService,
            SpeciesService speciesService, PlaceService placeService) {
        
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService);
        this.observations = FXCollections.observableArrayList();
        addButton = inputWindow.createButton("Add new observation");
        searchButton = inputWindow.createButton("Search observations");
        logoutButton = inputWindow.createButton("Logout");
        listSpeciesButton = inputWindow.createButton("Show species list");
        listPlacesButton = inputWindow.createButton("Show place list");
        showSpecies = new ShowSpeciesListScene(speciesService, this);
        showPlaces = new ShowPlaceListScene(placeService, this);
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

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { 
                Clicked(stage);
            }
        });
        
        listSpeciesButton().setOnAction(e-> {
            try {
                stage.setScene(showSpecies.speciesScene(stage));
            } catch (Exception ex) {
                successMessage.setText("Could not open the species list. Try again!");
            }
        });
        
        listPlacesButton().setOnAction(e-> {
            try {
                stage.setScene(showPlaces.placeScene(stage));
            } catch (Exception ex) {
                successMessage.setText("Could not open the place list. Try again!");
            }
        });
        
        VBox vbox = inputWindow.createNewWindow();
        vbox.getChildren().addAll(labelBar(), infoBox, table, searchObservations(), createObservation());
        VBox.setVgrow(table, Priority.ALWAYS);
        return vbox;
    }  
    
    private void Clicked(Stage stage) {
        try {
            DisplayableObservation o = (DisplayableObservation) table.getSelectionModel().getSelectedItem();
            stage.setScene(showOne.showOneScene(stage, this, o));
        } catch(Exception e) {
            successMessage.setText("Could not open the observation. Try again!");
        }
    }
    
    
    private HBox createObservation() {
        HBox createForm = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        createForm.getChildren().addAll(listSpeciesButton(), spacer, addButton());
        return createForm;
    }
    
    public HBox searchObservations() {
        HBox searchForm = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        searchForm.getChildren().addAll(listPlacesButton(), spacer, searchButton());
        return searchForm;
    }
    
    private HBox labelBar() {
        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Label label = inputWindow.createBigLabel("Observations", 200);
        menuPane.getChildren().addAll(label, menuSpacer, logoutButton()); 
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
    
    public Button listSpeciesButton() {
        return this.listSpeciesButton;
    } 
    
    public Button listPlacesButton() {
        return this.listPlacesButton;
    } 
    
    
    
    
    
}
