package project.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.Species;
import project.domain.SpeciesService;

public class ShowSpeciesListScene extends LoggedInScene {
    private ListView<Species> listView;
    private final ShowOneSpeciesScene showOneSpecies;
    private final ObservationTableScene observationTable;
    private final Button createNewSpeciesButton;
    
    private TextField englishNameInput;
    private TextField scientificNameInput;
    private TextField finnishNameInput;
    private TextField abbreviationInput;
    
    public ShowSpeciesListScene(SpeciesService speciesService, ObservationTableScene observationTable) {
        this.speciesService = speciesService;
        this.showOneSpecies = new ShowOneSpeciesScene(speciesService);
        this.observationTable = observationTable;
        this.createNewSpeciesButton = inputWindow.createButton("Create");
    } 

    public void setSpeciesListScene(Stage stage) throws Exception {
        Scene newSpeciesScene = new Scene(speciesScene(stage), 500, 600);
        stage.setScene(newSpeciesScene);
    }
    
    private VBox speciesScene(Stage stage) throws Exception {
        VBox speciesPane = inputWindow.createNewWindow();
        
        setListView(speciesPane, stage);
        setInputFields(speciesPane);
        
        createNewSpeciesButton.setOnAction(e-> {
            createNewSpecies(stage);
        });  
        
        returnButton.setOnAction(e -> {
            returnToObservationTable(stage);
        });
        
        speciesPane.getChildren().addAll(createNewSpeciesButton, returnButton);
        
        return speciesPane;
    }
    
    
    private void setListView(VBox speciesPane, Stage stage) throws Exception {
        Label label = inputWindow.createBigLabel("List of all species", 300);
        speciesPane.getChildren().addAll(label);
        HBox infoBox = inputWindow.infoBox("Double click species to open or modify.");
        speciesPane.getChildren().addAll(infoBox);
        listView = listSpecies(speciesPane);
        
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { 
                clicked(stage);
            }
        });
        
        HBox messageBox = inputWindow.observationBoxRow();
        messageBox.getChildren().addAll(successMessage);
        speciesPane.getChildren().addAll(messageBox);
    }
    
    private void clicked(Stage stage) {
        try {
            Species s = (Species) listView.getSelectionModel().getSelectedItem();
            showOneSpecies.setSpeciesScene(stage, this, s);
        } catch(Exception e) {
            successMessage.setText("Could not open the species. Try again!");
        }
    }
    
    private void setInputFields(VBox speciesPane) {
        Label newLabel = inputWindow.createBigLabel("Add new species", 300);
        speciesPane.getChildren().addAll(newLabel);
        englishNameInput = inputWindow.createInputField(speciesPane, "English name");
        scientificNameInput = inputWindow.createInputField(speciesPane, "Scientific name");
        finnishNameInput = inputWindow.createInputField(speciesPane, "Finnish name");
        abbreviationInput = inputWindow.createInputField(speciesPane, "Abbreviation");
    }
    
    private void createNewSpecies(Stage stage) {
        String englishName = englishNameInput.getText();
        String scientificName = scientificNameInput.getText();
        String finnishName = finnishNameInput.getText();
        String abbreviation = abbreviationInput.getText();

        if (englishName.length() < 2 || scientificName.length() < 6) {
            successMessage.setText("One of the names is too short.");
        } else {
            try {
                if (speciesService.createSpecies(englishName, scientificName, finnishName, abbreviation)) {
                    successMessage.setText("");
                    observationTable.setObservationScene(stage);
                } else {
                    successMessage.setText("Error while creating new species.");
                }
            } catch (Exception ex) {
                successMessage.setText("Something went wrong! Try again.");
            }
        }
    }
    
    private void returnToObservationTable(Stage stage) {
        try {
            observationTable.setObservationScene(stage);
        } catch (Exception ex) {
            successMessage.setText("Something went wrong! Try again.");
        }
    }
    
    
}
