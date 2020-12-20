package project.scenes;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.DisplayableObservation;
import project.domain.DisplayableObservationService;
import project.domain.StoreableObservationService;
import project.domain.PlaceService;
import project.domain.SpeciesService;
import project.domain.UserService;

public class ShowAllObservationsScene extends LoggedInScene {
    private ChoiceBox<String> selection;
    private TableView table;
    private TextField textInput;

    public ShowAllObservationsScene(StoreableObservationService observationService, 
            SpeciesService speciesService, PlaceService placeService, UserService userService) throws Exception {
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService, userService);
        this.observations = FXCollections.observableArrayList();
        this.observationsByAllUsers = FXCollections.observableArrayList();
        this.speciesService = speciesService;
        this.placeService = placeService;
        this.userService = userService;
    } 

    public void setAllUsersScene(Stage stage, ObservationTableScene observationTable) throws Exception {
        Scene allUsersScene = new Scene(createObservationView(stage, observationTable), 800, 600);
        stage.setScene(allUsersScene);
    }
    
    private VBox createObservationView(Stage stage, ObservationTableScene observationTable) throws Exception {
        VBox vbox = inputWindow.createNewWindow();
        textInput = createSearchBar(vbox);        
        createTableView();

        FilteredList<DisplayableObservation> flObs = new FilteredList(observationsByAllUsers, p -> true);
        table.setItems(flObs);
        
        ArrayList<TableColumn> columns = displayObsService.getColumnsWithAllUsers();
        table.getColumns().addAll(columns);
        
        textInput.setOnKeyReleased(keyEvent -> {
            selectSearchField(flObs);
        });

        selection.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textInput.setText("");
                flObs.setPredicate(null);
            }
        });
        
        returnButton.setOnAction(e -> {
            returnToObservationTable(stage, observationTable);
        });
        
        vbox.getChildren().addAll(table, returnButton);
        VBox.setVgrow(table, Priority.ALWAYS);
        return vbox;
    }
    
    private TextField createSearchBar(VBox vbox) {
        HBox searchBar = new HBox(10);
        searchBar.setPadding(new Insets(10));
        Label label = inputWindow.createBigLabel("Search observations", 200);
        selection = inputWindow.createChoiceBox();
        selection.getItems().addAll("Species", "Place", "Date", "Time", "Info", "User");
        selection.setValue("Species");
        
        Label smallLabel = inputWindow.createSmallLabel("Search", 100);
        
        textInput = inputWindow.createTextField(300);
        
        searchBar.getChildren().addAll(smallLabel, selection, textInput);
        vbox.getChildren().addAll(label, searchBar);
        
        return textInput;
    }
    
    private void createTableView() throws Exception {
        table = new TableView();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        redrawObservationListOfAllUsers();
        table.setItems(observationsByAllUsers);
    }
    
    private void selectSearchField(FilteredList<DisplayableObservation> flObs) {
        if (selection.getValue().equals("Species")) {
            flObs.setPredicate(o -> o.getFullSpecies().toLowerCase().contains(textInput.getText().toLowerCase().trim()));
        } else if (selection.getValue().equals("Place")) {
            flObs.setPredicate(o -> o.getPlace().toLowerCase().contains(textInput.getText().toLowerCase().trim()));   
        } else if (selection.getValue().equals("Date")) {
            flObs.setPredicate(o -> o.getDate().toString().contains(textInput.getText().toLowerCase().trim()));
        } else if (selection.getValue().equals("Time")) {
            flObs.setPredicate(o -> o.getTime().toString().contains(textInput.getText().toLowerCase().trim()));
        } else if (selection.getValue().equals("Info")) {
            flObs.setPredicate(o -> o.getInfo().toLowerCase().contains(textInput.getText().toLowerCase().trim()));
        } else if (selection.getValue().equals("User")) {
            flObs.setPredicate(o -> o.getUser().toLowerCase().contains(textInput.getText().toLowerCase().trim()));
        }
    }
    
    private void returnToObservationTable(Stage stage, ObservationTableScene observationTable) {
        try {
            observationTable.setObservationScene(stage);
        } catch (Exception ex) {
            successMessage.setText("Something went wrong! Try again.");
        }
}
    
    
}
