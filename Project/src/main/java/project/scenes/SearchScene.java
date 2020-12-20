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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.DisplayableObservation;
import project.domain.DisplayableObservationService;
import project.domain.StoreableObservationService;
import project.domain.PlaceService;
import project.domain.SpeciesService;

public class SearchScene extends LoggedInScene {
    private ChoiceBox<String> selection;
    private TableView table;
    private TextField textInput;

    public SearchScene(StoreableObservationService observationService, 
            SpeciesService speciesService, PlaceService placeService) throws Exception {
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService);
        this.observations = FXCollections.observableArrayList();
        this.speciesService = speciesService;
        this.placeService = placeService;
    } 

    public void setSearchScene(Stage stage, ObservationTableScene observationTable) throws Exception {
        Scene searchScene = new Scene(createSearchView(stage, observationTable), 800, 600);
        stage.setScene(searchScene);
    }
    
    private VBox createSearchView(Stage stage, ObservationTableScene observationTable) throws Exception {
        VBox vbox = inputWindow.createNewWindow();
        textInput = createSearchBar(vbox);        
        table = createTableView(vbox);

        FilteredList<DisplayableObservation> flObs = new FilteredList(observations, p -> true);
        table.setItems(flObs);
        
        textInput.setOnKeyReleased(keyEvent -> {
            selectSearchField(flObs);
        });

        selection.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textInput.setText("");
                flObs.setPredicate(null);
            }
        });
        
        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { 
                clicked(stage, observationTable);
            }
        });
        
        ArrayList<TableColumn> columns = displayObsService.getColumns();
        table.getColumns().addAll(columns);
        
        
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
        selection.getItems().addAll("Species", "Place", "Date", "Time", "Info");
        selection.setValue("Species");
        
        Label smallLabel = inputWindow.createSmallLabel("Search", 100);
        
        TextField textInput = inputWindow.createTextField(300);
        
        searchBar.getChildren().addAll(smallLabel, selection, textInput);
        vbox.getChildren().addAll(label, searchBar);
        
        return textInput;
    }
    
    private TableView createTableView(VBox vbox) throws Exception {
        table = new TableView();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);        
        redrawObservationList();
        table.setItems(observations);
        return table;
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
        }
    }
    
    private void clicked(Stage stage, ObservationTableScene observationTable) {
        try {
            DisplayableObservation o = (DisplayableObservation) table.getSelectionModel().getSelectedItem();
            ShowOneObservationScene showOne = new ShowOneObservationScene(observationService, speciesService, placeService);
            showOne.setShowOneScene(stage, o, observationTable);
        } catch(Exception e) {
            successMessage.setText("Could not open the observation. Try again!");
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
