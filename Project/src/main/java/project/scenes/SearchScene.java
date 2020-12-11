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
import project.ui.InputWindow;

public class SearchScene extends LoggedInScene {
    private ChoiceBox<String> selection;

    public SearchScene(InputWindow inputWindow, StoreableObservationService observationService, 
            SpeciesService speciesService, PlaceService placeService) throws Exception {
        this.inputWindow = inputWindow;
        this.displayObsService = new DisplayableObservationService(observationService, speciesService, placeService);
        this.observations = FXCollections.observableArrayList();
        this.speciesService = speciesService;
        this.placeService = placeService;
    } 

    public Scene searchScene(Stage stage) throws Exception {
        Scene scene = new Scene(createSearchView(), 800, 600);
        stage.setScene(scene);
        return scene;
    }
    
    private VBox createSearchView() throws Exception {
        VBox vbox = inputWindow.createNewWindow();
        TextField textInput = createSearchBar(vbox);        
        TableView table = createTableView(vbox);

        FilteredList<DisplayableObservation> flObs = new FilteredList(observations, p -> true);
        table.setItems(flObs);
        
        textInput.setOnKeyReleased(keyEvent -> {
            if (selection.getValue().equals("Species")) {
                flObs.setPredicate(o -> o.getSpecies().toLowerCase().contains(textInput.getText().toLowerCase().trim()));
            } else if (selection.getValue().equals("Place")) {
                flObs.setPredicate(o -> o.getPlace().toLowerCase().contains(textInput.getText().toLowerCase().trim()));   
            } else if (selection.getValue().equals("Date")) {
                flObs.setPredicate(o -> o.getDate().toString().contains(textInput.getText().toLowerCase().trim()));
            } else if (selection.getValue().equals("Timr")) {
                flObs.setPredicate(o -> o.getTime().toString().contains(textInput.getText().toLowerCase().trim()));
            } else if (selection.getValue().equals("Info")) {
                flObs.setPredicate(o -> o.getInfo().toLowerCase().contains(textInput.getText().toLowerCase().trim()));
            }
        });

        selection.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null)
            {
                textInput.setText("");
                flObs.setPredicate(null);
            }
        });
        
        ArrayList<TableColumn> columns = displayObsService.getColumns();
        table.getColumns().addAll(columns);
        
        vbox.getChildren().addAll(table, returnButton);
        VBox.setVgrow(table, Priority.ALWAYS);
        return vbox;
    }
    
    public TextField createSearchBar(VBox vbox) {
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
    
    public TableView createTableView(VBox vbox) throws Exception {
        TableView table = new TableView();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);        
        redrawObservationList();
        table.setItems(observations);
        return table;
    }
            
   
}
