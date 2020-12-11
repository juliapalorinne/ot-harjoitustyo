package project.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.SpeciesService;

public class NewSpeciesScene extends LoggedInScene {
    
    public NewSpeciesScene(SpeciesService speciesService) {
        this.speciesService = speciesService;
    } 

    public Scene newSpeciesScene(Stage stage, Scene newObservationScene) {
        VBox newSpeciesPane = inputWindow.createNewWindow();
        TextField englishNameInput = inputWindow.createInputField(newSpeciesPane, "English name");
        TextField scientificNameInput = inputWindow.createInputField(newSpeciesPane, "Scientific name");
        TextField finnishNameInput = inputWindow.createInputField(newSpeciesPane, "Finnish name");
        TextField abbreviationInput = inputWindow.createInputField(newSpeciesPane, "Abbreviation");
        
        Label speciesCreationMessage = new Label();
        
        Button createNewSpeciesButton = inputWindow.createButton("Create");
        createNewSpeciesButton.setPadding(new Insets(10));

        createNewSpeciesButton.setOnAction(e-> {
            String englishName = englishNameInput.getText();
            String scientificName = scientificNameInput.getText();
            String finnishName = finnishNameInput.getText();
            String abbreviation = abbreviationInput.getText();
   
            if (englishName.length() < 2 || scientificName.length() < 6) {
                speciesCreationMessage.setText("One of the names is too short.");
            } else {
                try {
                    if (speciesService.createSpecies(englishName, scientificName, finnishName, abbreviation)) {
                        speciesCreationMessage.setText("");
                        stage.setScene(newObservationScene);
                    } else {
                        speciesCreationMessage.setText("Error while creating new species.");
                    }
                } catch (Exception ex) {
                    successMessage.setText("Something went wrong! Try again.");
                }
            }
        });  
        
        newSpeciesPane.getChildren().addAll(speciesCreationMessage, createNewSpeciesButton, returnButton);
        Scene newSpeciesScene = new Scene(newSpeciesPane, 400, 400);
        return newSpeciesScene;
    }
}
