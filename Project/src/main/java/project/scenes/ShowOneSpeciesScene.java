package project.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.Species;
import project.domain.SpeciesService;

public class ShowOneSpeciesScene extends LoggedInScene {
    private final Button modifyButton;
    private TextField englishNameInput;
    private TextField scientificNameInput;
    private TextField finnishNameInput;
    private TextField abbreviationInput;
    
    public ShowOneSpeciesScene(SpeciesService speciesService) {
        this.speciesService = speciesService;
        modifyButton = inputWindow.createButton("Modify");
    } 
    
    public void setSpeciesScene(Stage stage, ShowSpeciesListScene speciesList, Species species) throws Exception  {
        Scene newSpeciesScene = new Scene(speciesScene(stage, speciesList, species), 500, 600);
        stage.setScene(newSpeciesScene);
    }
    
    private VBox speciesScene(Stage stage, ShowSpeciesListScene speciesList, Species species) throws Exception {
        VBox speciesPane = inputWindow.createNewWindow();
        setLabels(speciesPane, species);
        setInputFields(speciesPane, species);

        modifyButton.setOnAction(e-> {
            modifySpecies(stage, speciesList, species);
        });  
        
        returnButton.setOnAction(e -> {
            returnToSpeciesList(stage, speciesList);
        });
        
        speciesPane.getChildren().addAll(modifyButton, returnButton);
        return speciesPane;
    }
    
    private void setLabels(VBox speciesPane, Species species) {
        Label label = inputWindow.createBigLabel("Show this species", 300);
        Label newLabel = inputWindow.createBigLabel("Modify this species", 300);
        HBox messageBox = inputWindow.observationBoxRow();
        messageBox.getChildren().addAll(successMessage);
        speciesPane.getChildren().addAll(label, showSpecies(species), messageBox, newLabel);
    }
    
    private VBox showSpecies(Species s) {
        VBox vbox = inputWindow.ShowOneBox();
        
        HBox englishNameBox = inputWindow.observationBoxRow();
        Label englishName = inputWindow.createSmallLabel("English name", 150);
        Label getEnglishName = new Label(s.getEnglishName());
        englishNameBox.getChildren().addAll(englishName, getEnglishName);
        
        HBox scientificNameBox = inputWindow.observationBoxRow();
        Label scientificName = inputWindow.createSmallLabel("Scientific name", 150);
        Label getScientificName = new Label(s.getScientificName());
        scientificNameBox.getChildren().addAll(scientificName, getScientificName);
        
        HBox finnishNameBox = inputWindow.observationBoxRow();
        Label finnishName = inputWindow.createSmallLabel("Finnish name", 150);
        Label getFinnishName = new Label(s.getFinnishName());
        finnishNameBox.getChildren().addAll(finnishName, getFinnishName);
        
        HBox abbreviationBox = inputWindow.observationBoxRow();
        Label abbreviations = inputWindow.createSmallLabel("Abbreviations", 150);
        Label getAbbreviations = new Label((s.getAbbreviation()));
        abbreviationBox.getChildren().addAll(abbreviations, getAbbreviations);
        
        vbox.getChildren().addAll(englishNameBox, scientificNameBox, finnishNameBox, abbreviationBox);
        return vbox;
    }
    
    private void setInputFields(VBox speciesPane, Species species) {
        englishNameInput = inputWindow.createInputField(speciesPane, "English name");
        englishNameInput.setText(species.getEnglishName());
        scientificNameInput = inputWindow.createInputField(speciesPane, "Scientific name");
        scientificNameInput.setText(species.getScientificName());
        finnishNameInput = inputWindow.createInputField(speciesPane, "Finnish name");
        finnishNameInput.setText(species.getFinnishName());
        abbreviationInput = inputWindow.createInputField(speciesPane, "Abbreviation");
        abbreviationInput.setText(species.getAbbreviation());
    }
    
    private void modifySpecies(Stage stage, ShowSpeciesListScene speciesList, Species species) {
        String englishName = englishNameInput.getText();
        String scientificName = scientificNameInput.getText();
        String finnishName = finnishNameInput.getText();
        String abbreviation = abbreviationInput.getText();

        if (englishName.length() < 2 || scientificName.length() < 6) {
            successMessage.setText("One of the names is too short.");
        } else {
            try {
                if (speciesService.modifySpecies(species.getId(), englishName, scientificName, finnishName, abbreviation)) {
                    successMessage.setText("");
                    speciesList.setSpeciesListScene(stage);
                } else {
                    successMessage.setText("Error while modifying the species.");
                }
            } catch (Exception ex) {
                successMessage.setText("Something went wrong! Try again.");
            }
        }
    }
    
    private void returnToSpeciesList(Stage stage, ShowSpeciesListScene speciesList) {
        try {
            speciesList.setSpeciesListScene(stage);
        } catch (Exception ex) {
            successMessage.setText("Could not return to species list. Try again.");
        }
    }    
}
