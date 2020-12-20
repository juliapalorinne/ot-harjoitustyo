package project.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.SpeciesService;

public class NewSpeciesScene extends LoggedInScene {
    private final Button createNewSpeciesButton;
    private TextField englishNameInput;
    private TextField scientificNameInput;
    private TextField finnishNameInput;
    private TextField abbreviationInput;
    
    public NewSpeciesScene(SpeciesService speciesService) {
        this.speciesService = speciesService;
        createNewSpeciesButton = inputWindow.createButton("Create");
    }
    
    public void setNewSpeciesScene(Stage stage, Scene newObservationScene) {
        Scene newSpeciesScene = new Scene(newSpeciesScene(stage, newObservationScene), 400, 400);
        stage.setScene(newSpeciesScene);
    }

    private VBox newSpeciesScene(Stage stage, Scene newObservationScene) {
        VBox newSpeciesPane = inputWindow.createNewWindow();
        setInputFields(newSpeciesPane);

        createNewSpeciesButton.setOnAction(e -> {
            createNewSpecies(stage, newObservationScene);
        });
        
        returnButton.setOnAction(e -> {
            returnToNewObservation(stage, newObservationScene);
        });
        
        newSpeciesPane.getChildren().addAll(successMessage, createNewSpeciesButton, returnButton);
        return newSpeciesPane;
    }
    
    private void setInputFields(VBox speciesPane) {
        Label newLabel = inputWindow.createBigLabel("Add new species", 300);
        speciesPane.getChildren().addAll(newLabel);
        englishNameInput = inputWindow.createInputField(speciesPane, "English name");
        scientificNameInput = inputWindow.createInputField(speciesPane, "Scientific name");
        finnishNameInput = inputWindow.createInputField(speciesPane, "Finnish name");
        abbreviationInput = inputWindow.createInputField(speciesPane, "Abbreviation");
    }
    
    private void createNewSpecies(Stage stage, Scene newObservationScene) {
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
                    stage.setScene(newObservationScene);
                } else {
                    successMessage.setText("Error while creating new species.");
                }
            } catch (Exception ex) {
                successMessage.setText("Something went wrong! Try again.");
            }
        }
    }
    
    private void returnToNewObservation(Stage stage, Scene newObservationScene) {
        try {
            stage.setScene(newObservationScene);
        } catch (Exception ex) {
            successMessage.setText("Could not return. Try again.");
        }
    }
    
}
