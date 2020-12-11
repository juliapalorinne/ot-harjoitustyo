package project.scenes;

import java.util.Arrays;
import project.domain.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.ui.InputWindow;


public abstract class LoggedInScene {
    protected InputWindow inputWindow = new InputWindow();
    protected ShowOneObservationScene showOne;
    
    protected DisplayableObservationService displayObsService;
    protected SpeciesService speciesService;
    protected PlaceService placeService;
    protected Label successMessage;
    protected ObservableList<DisplayableObservation> observations;
    protected Button returnButton;
    protected Button addNewSpeciesButton;
    protected Button addNewPlaceButton;
    protected TextField speciesInput;
    protected TextField placeInput;
    protected ToggleGroup selectPrivacy;
    
    public int smallInputSize = 50;
    public int bigInputSize = 200;
    
    
    public LoggedInScene() {
        this.successMessage = inputWindow.createErrorMessage("");
        this.addNewSpeciesButton = inputWindow.createButton("Add new Species");
        this.addNewPlaceButton = inputWindow.createButton("Add new Place");
        this.returnButton = inputWindow.createButton("Return");
    }
    
    
    protected void redrawObservationList() throws Exception {
        displayObsService.redrawObservationList();
        List<DisplayableObservation> obs = displayObsService.getAll();
        obs.stream().filter((o) -> (!observations.contains(o))).forEachOrdered((o) -> {
            observations.add(o);
        });
    }


    protected ObservableList<Species> getObservableSpeciesList() throws Exception {
        List<Species> speciesList = speciesService.getAllSpecies();
        ObservableList<Species> observableSpeciesList = FXCollections.observableArrayList();
        speciesList.forEach(species -> {
            observableSpeciesList.add(species);
        });
        
        return observableSpeciesList;
    }
    
    protected ObservableList<Place> getObservablePlaceList() throws Exception {
        List<Place> placeList = placeService.getAllPlaces();
        ObservableList<Place> observablePlaceList = FXCollections.observableArrayList();
        placeList.forEach(place -> {
            observablePlaceList.add(place);
        });
        return observablePlaceList;
    }
    
    protected ListView<Species> listSpecies(VBox pane) throws Exception {
        ListView<Species> listView = new ListView<>();
        listView.setItems(getObservableSpeciesList());
        listView.setPrefWidth(500);
        
        HBox speciesPane = new HBox(10);
        speciesPane.getChildren().addAll(listView);
        pane.getChildren().addAll(speciesPane);
        return listView;
    }

    
    protected ListView<Place> listPlaces(VBox pane) throws Exception {
        ListView<Place> listView = new ListView<>();
        listView.setItems(getObservablePlaceList());
        listView.setPrefWidth(500);
        
        HBox placePane = new HBox(10);
        placePane.getChildren().addAll(listView);
        pane.getChildren().addAll(placePane);
        return listView;
    }
    
    
    protected ListView<Species> searchSpecies(VBox pane) throws Exception {
        speciesInput = new TextField();
        ListView<Species> speciesList = new ListView<>();
        
        FilteredList<Species> flSpecies = new FilteredList<>(getObservableSpeciesList(),s -> true);
        speciesList.setItems(flSpecies);
        
        speciesInput.setOnKeyReleased(keyEvent -> {
            try {   
                flSpecies.setPredicate(s -> s.toString().toLowerCase().contains(speciesInput.getText().toLowerCase().trim()));
            } catch (Exception ex) {
                successMessage.setText("Something went wrong when creating the species list.");
            }
        });
        return speciesList;
    }
    
    protected ListView<Place> searchPlace(VBox pane) throws Exception {
        placeInput = new TextField();
        ListView<Place> placeList = new ListView<>();
        
        FilteredList<Place> flPlace = new FilteredList<>(getObservablePlaceList(),p -> true);
        placeList.setItems(flPlace);
        
        placeInput.setOnKeyReleased(keyEvent -> {
            try {   
                flPlace.setPredicate(p -> p.toString().toLowerCase().contains(placeInput.getText().toLowerCase().trim()));
            } catch (Exception ex) {
                successMessage.setText("Something went wrong when creating the place list.");
            }
        });
        return placeList;
    }
    
    protected ListView<Species> showSpeciesInputAboveTheList(VBox pane) throws Exception {
        HBox speciesPane = new HBox(10);
        speciesPane.setPadding(new Insets(10, 0, 0, 10));
        ListView<Species> speciesList = searchSpecies(pane);
        speciesList.setPrefWidth(500);
        speciesInput.setPrefWidth(400);
        Label label = inputWindow.createSmallLabel("Type and select species", 200);
        speciesPane.getChildren().addAll(label, speciesInput, addNewSpeciesButton());
        pane.getChildren().addAll(speciesPane, speciesList);
        return speciesList;
    }

    protected ListView<Place> showPlaceInputAboveTheList(VBox pane) throws Exception {
        HBox placePane = new HBox(10);
        placePane.setPadding(new Insets(10, 0, 0, 10));
        ListView<Place> placeList = searchPlace(pane);
        placeList.setPrefWidth(500);
        placeInput.setPrefWidth(400);
        Label label = inputWindow.createSmallLabel("Type and select place", 200);
        placePane.getChildren().addAll(label, placeInput, addNewPlaceButton());
        pane.getChildren().addAll(placePane, placeList);
        return placeList;
    }
    
    protected ListView<Species> showSpeciesInputAndListSideBySide(VBox pane) throws Exception {
        VBox labelColumn = new VBox();
        HBox speciesPane = new HBox(10);
        speciesPane.setPadding(new Insets(10, 0, 0, 0));
        ListView<Species> speciesList = searchSpecies(pane);
        speciesList.setPrefWidth(400);
        speciesInput.setPrefWidth(200);
        Label label = inputWindow.createSmallLabel("Type and select species", 200);
        labelColumn.getChildren().addAll(label, speciesInput);
//        labelColumn.setAlignment(Pos.BOTTOM_LEFT);
//        label.setAlignment(Pos.BOTTOM_LEFT);
        speciesPane.getChildren().addAll(labelColumn, speciesList, addNewSpeciesButton());
        pane.getChildren().addAll(label, speciesPane);
        return speciesList;
    }
    
    protected ListView<Place> showPlaceInputAndListSideBySide(VBox pane) throws Exception {
        VBox labelColumn = new VBox();
        HBox placePane = new HBox(10);
        placePane.setPadding(new Insets(10, 0, 0, 0));
        ListView<Place> placeList = searchPlace(pane);
        placeList.setPrefWidth(400);
        placeInput.setPrefWidth(200);
        Label label = inputWindow.createSmallLabel("Type and select place", 200);
        labelColumn.getChildren().addAll(label, placeInput);
        placePane.getChildren().addAll(labelColumn, placeList, addNewPlaceButton());
        pane.getChildren().addAll(label, placePane);
        return placeList;
    }
    
    protected HBox privacyButtons() {
        HBox selectPane = new HBox(10);
        selectPane.setPadding(new Insets(10));
        selectPrivacy = new ToggleGroup(); 
        RadioButton rb1 = new RadioButton("Public");
        rb1.setSelected(true);
        rb1.setToggleGroup(selectPrivacy); 
        RadioButton rb2 = new RadioButton("Private");
        rb2.setToggleGroup(selectPrivacy);
        
        Label selectLabel = inputWindow.createSmallLabel("This observation is ", 200);
        selectPane.getChildren().addAll(selectLabel, rb1, rb2);
        return selectPane;
    }
    
    public Button returnButton() {
        return this.returnButton;
    }
    
    public Button addNewSpeciesButton() {
        return this.addNewSpeciesButton;
    }
    
    public Button addNewPlaceButton() {
        return this.addNewPlaceButton;
    }
    
    public Label successMessage() {
        return this.successMessage;
    }
    
    
    protected boolean checkIfInputIsNumber(String input) {
        try {
            int number = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            successMessage.setText("Input is not a number.");
            return false;
        }
        return true;
    }
    
    protected boolean checkSmallInputLength(String input) {
        return input.length() <= smallInputSize;
    }
    
    protected boolean checkBigInputLength(String input) {
        return input.length() <= bigInputSize;
    }
    
    protected boolean checkCharacterValidity(String input) {
        for (String c : forbiddenCharacters()) {
            if (input.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public String[] forbiddenCharacters() {
        return new String[]{"?", "*", "%", "="};
    }
    
    protected boolean checkObservationInputValidity(int individuals, int privacy, String info) {
        if (individuals < 0) {
            successMessage.setText("Number of individuals must be at least 0.");
            return false;
        }
        if (privacy < 0) {
            successMessage.setText("Set observation private or public.");
            return false;
        }
        if (!checkCharacterValidity(info)) {
            successMessage.setText("Info contains forbidden characters. " + Arrays.toString(forbiddenCharacters()));
            return false;
        }
        if (!checkBigInputLength(info)) {
            successMessage.setText("Info contains too many characters. Max number of characters is " + bigInputSize + ".");
            return false;
        }
        return true;
    }
    
    protected int getIndividualInput(TextField newIndividualInput) {
        int individuals = -1;
        if (checkIfInputIsNumber(newIndividualInput.getText())) {
            individuals = Integer.parseInt(newIndividualInput.getText());
        } else {
            successMessage.setText("Give a valid number of individuals.");
        }
        return individuals;
    }
    
    protected int getPrivacyInput() {
        RadioButton selectedButton = (RadioButton) selectPrivacy.getSelectedToggle();
        String privacyText = selectedButton.getText();
        
        int privacy = -1;
        if (privacyText.equals("Public")) {
            privacy = 0;
        } else if (privacyText.equals("Private")) {
            privacy = 1;
        }
        return privacy;
    }
    
}
