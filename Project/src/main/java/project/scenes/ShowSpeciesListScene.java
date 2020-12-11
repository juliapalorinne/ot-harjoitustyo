package project.scenes;

import javafx.geometry.Insets;
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
    
    public ShowSpeciesListScene(SpeciesService speciesService, ObservationTableScene observationTable) {
        this.speciesService = speciesService;
        this.showOneSpecies = new ShowOneSpeciesScene(speciesService);
        this.observationTable = observationTable;
    } 

    public Scene speciesScene(Stage stage) throws Exception {
        VBox speciesPane = inputWindow.createNewWindow();
        Label label = inputWindow.createBigLabel("List of all species", 300);
        speciesPane.getChildren().addAll(label);
        HBox infoBox = inputWindow.infoBox("Double click species to open or modify.");
        speciesPane.getChildren().addAll(infoBox);
        listView = listSpecies(speciesPane);
        
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { 
                Clicked(stage);
            }
        });
        
        HBox messageBox = inputWindow.observationBoxRow();
        messageBox.getChildren().addAll(successMessage());
        speciesPane.getChildren().addAll(messageBox);
        
        Label newLabel = inputWindow.createBigLabel("Add new species", 300);
        speciesPane.getChildren().addAll(newLabel);
        TextField englishNameInput = inputWindow.createInputField(speciesPane, "English name");
        TextField scientificNameInput = inputWindow.createInputField(speciesPane, "Scientific name");
        TextField finnishNameInput = inputWindow.createInputField(speciesPane, "Finnish name");
        TextField abbreviationInput = inputWindow.createInputField(speciesPane, "Abbreviation");
        
        Button createNewSpeciesButton = inputWindow.createButton("Create");

        createNewSpeciesButton.setOnAction(e-> {
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
                        stage.setScene(observationTable.observationScene(stage));
                    } else {
                        successMessage.setText("Error while creating new species.");
                    }
                } catch (Exception ex) {
                    successMessage.setText("Something went wrong! Try again.");
                }
            }
        });  
        
        returnButton().setOnAction(e -> {
            try {
                stage.setScene(observationTable.observationScene(stage));
            } catch (Exception ex) {
                successMessage.setText("Something went wrong! Try again.");
            }
        });
        
        speciesPane.getChildren().addAll(createNewSpeciesButton, returnButton());
        Scene newSpeciesScene = new Scene(speciesPane, 500, 600);
        return newSpeciesScene;
    }
    
    private void Clicked(Stage stage) {
        try {
            Species s = (Species) listView.getSelectionModel().getSelectedItem();
            stage.setScene(showOneSpecies.speciesScene(stage, this, s));
        } catch(Exception e) {
            successMessage.setText("Could not open the species. Try again!");
        }
    }
    
}
