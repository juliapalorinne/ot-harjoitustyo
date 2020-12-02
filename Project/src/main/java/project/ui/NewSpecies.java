
package project.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.domain.SpeciesService;

public class NewSpecies {
    private InputWindow inputWindow;
    private SpeciesService speciesService;
    private Button returnButton;
    
    public NewSpecies(InputWindow inputWindow, SpeciesService speciesService) {
        this.inputWindow = inputWindow;
        this.speciesService = speciesService;
        returnButton = inputWindow.createButton("Return");
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
   
            if (englishName.length() < 2 || scientificName.length() < 2) {
                speciesCreationMessage.setText("One of the names too short");
                speciesCreationMessage.setTextFill(Color.BLUE);              
            } else try {
                if (speciesService.createSpecies(englishName, scientificName, finnishName, abbreviation)) {
                    speciesCreationMessage.setText("");
                    stage.setScene(newObservationScene);
                } else {
                    speciesCreationMessage.setText("Error while creating new species");
                    speciesCreationMessage.setTextFill(Color.BLUE);        
                }
            } catch (Exception ex) {
                Logger.getLogger(NewSpecies.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        });  
        
        newSpeciesPane.getChildren().addAll(speciesCreationMessage, createNewSpeciesButton, returnButton);
        Scene newSpeciesScene = new Scene(newSpeciesPane, 400, 400);
        return newSpeciesScene;
    }
    
    public Button returnButton() {
        return this.returnButton;
    }
}
