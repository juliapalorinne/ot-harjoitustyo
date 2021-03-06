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
import project.domain.UserService;

public class ObservationTableScene extends LoggedInScene {
    private TableView table;
    private final Button showAllObservationsButton;
    private final Button addButton;
    private final Button searchButton;
    private final Button listSpeciesButton;
    private final Button listPlacesButton;
    private final Button logoutButton;
    
    private final LoginScene loginScene;
    
    public ObservationTableScene(StoreableObservationService observationService,
            SpeciesService speciesService, PlaceService placeService, UserService userService, LoginScene scene) {
        
        this.speciesService = speciesService;
        this.placeService = placeService;
        this.observationService = observationService;
        this.userService = userService;
        this.loginScene = scene;
        this.observations = FXCollections.observableArrayList();
        showAllObservationsButton = inputWindow.createButton("Show observations by all users");
        addButton = inputWindow.createButton("Add new observation");
        searchButton = inputWindow.createButton("Search observations");
        logoutButton = inputWindow.createButton("Logout");
        listSpeciesButton = inputWindow.createButton("Show species list");
        listPlacesButton = inputWindow.createButton("Show place list");
    }  
    
    
    public void setObservationScene(Stage stage) throws Exception {
        Scene observationScene = new Scene(createTableView(stage), 800, 650);
        stage.setScene(observationScene);
    }
    
    
    private VBox createTableView(Stage stage) throws Exception {
        displayObsService = new DisplayableObservationService(observationService, speciesService, placeService, userService);
        HBox infoBox = inputWindow.infoBox("Double click observation to open or modify.");
        table = new TableView();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        redrawObservationList();
        table.setItems(observations);
        
        ArrayList<TableColumn> columns = displayObsService.getColumns();
        table.getColumns().addAll(columns);

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { 
                clicked(stage);
            }
        });
        
        showAllObservationsButton.setOnAction(e-> {
            showAllObservations(stage);
        });
        
        listSpeciesButton.setOnAction(e-> {
            showSpeciesList(stage);
        });
        
        listPlacesButton.setOnAction(e-> {
            showPlaceList(stage);
        });
        
        addButton.setOnAction(e -> {
            addNewObservation(stage);
        });
        
        searchButton.setOnAction(e -> {
            searchObservations(stage);
        });  
        
        logoutButton.setOnAction(e -> {
            logOut(stage);
        });        
        
        VBox vbox = inputWindow.createNewWindow();
        vbox.getChildren().addAll(labelBar(), infoBox, successMessage, table, showAllObservationsButton, searchObservation(), createObservation());
        VBox.setVgrow(table, Priority.ALWAYS);
        return vbox;
    } 
    
    private void clicked(Stage stage) {
        try {
            successMessage.setText("");
            DisplayableObservation o = (DisplayableObservation) table.getSelectionModel().getSelectedItem();
            ShowOneObservationScene showOne = new ShowOneObservationScene(observationService, speciesService, placeService);
            showOne.setShowOneScene(stage, o, this);
        } catch(Exception e) {
            successMessage.setText("Could not open the observation. Try again!");
        }
    }
    
    private void showAllObservations(Stage stage) {
        try {
            successMessage.setText("");
            ShowAllObservationsScene showAll = new ShowAllObservationsScene(observationService, speciesService, placeService, userService);
            showAll.setAllUsersScene(stage, this);
        } catch (Exception ex) {
            successMessage.setText("Could not open the observations by all users. Try again!");
        }
    }
    
    private void showSpeciesList(Stage stage) {
        try {
            successMessage.setText("");
            ShowSpeciesListScene speciesList = new ShowSpeciesListScene(speciesService, this);
            speciesList.setSpeciesListScene(stage);
        } catch (Exception ex) {
            successMessage.setText("Could not open the species list. Try again!");
        }
    }
    
    private void showPlaceList(Stage stage) {
        try {
            successMessage.setText("");
            ShowPlaceListScene showPlaces = new ShowPlaceListScene(placeService, this);
            stage.setScene(showPlaces.placeScene(stage));
        } catch (Exception ex) {
            successMessage.setText("Could not open the place list. Try again!");
        }
    }
    
    private void addNewObservation(Stage stage) {
        try {
            successMessage.setText("");
            NewObservationScene newScene = new NewObservationScene(observationService, speciesService, placeService);
            stage.setScene(newScene.createNewObservationScene(stage, this));
        } catch (Exception ex) {
            successMessage.setText("Could not open the add observation view. Try again!");
        }
    }
    
    private void searchObservations(Stage stage) {
        try {
            successMessage.setText("");
            SearchScene searchScene = new SearchScene(observationService, speciesService, placeService, userService);
            searchScene.setSearchScene(stage, this);
        } catch (Exception ex) {
            successMessage.setText("Could not open the search view. Try again!");
        }
    }
    
    private HBox labelBar() {
        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Label label = inputWindow.createBigLabel("Observations", 200);
        menuPane.getChildren().addAll(label, menuSpacer, logoutButton); 
        return menuPane;
    }
    
    private HBox searchObservation() {
        HBox searchForm = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        searchForm.getChildren().addAll(listPlacesButton, spacer, searchButton);
        return searchForm;
    }
    
    private HBox createObservation() {
        HBox createForm = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        createForm.getChildren().addAll(listSpeciesButton, spacer, addButton);
        return createForm;
    }
    
    private void logOut(Stage stage) {
        try {
            successMessage.setText("");
            loginScene.setLoginScene(stage);
        } catch (Exception ex) {
            successMessage.setText("Could not log out. Try again!");
        }
    }
}
