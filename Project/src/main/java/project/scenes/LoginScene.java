package project.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.PlaceService;
import project.domain.SpeciesService;
import project.domain.StoreableObservationService;
import project.domain.UserService;
import project.ui.InputWindow;

public class LoginScene {
    private final InputWindow inputWindow = new InputWindow();
    private final StoreableObservationService observationService;
    private final UserService userService;
    private final PlaceService placeService;
    private final SpeciesService speciesService;
    private TextField usernameInput;
    private TextField passwordInput;
    private final Button loginButton;
    private final Button createButton;
    private final Label loginMessage;
    
    public LoginScene(UserService userService, StoreableObservationService observationService) {
        this.observationService = observationService;
        this.userService = userService;
        this.speciesService = new SpeciesService();
        this.placeService = new PlaceService();
        this.loginButton = inputWindow.createButton("Login");
        this.createButton = inputWindow.createButton("Create new user");
        this.loginMessage = inputWindow.createErrorMessage("");
    }
    
    public void setLoginScene(Stage stage) {
        userService.logout();
        Scene loginScene = new Scene(loginScene(stage), 400, 250);
        stage.setScene(loginScene);
    }

    private VBox loginScene(Stage stage) {
        ObservationTableScene observationTable = new ObservationTableScene(observationService, speciesService, placeService, userService, this);
        VBox loginPane = inputWindow.createNewWindow();
        setInputFields(loginPane);
        
        loginButton.setOnAction(e -> {
            logIn(stage, observationTable);
        });
        
        loginButton.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loginButton.fire();
            }
        });
        
        createButton.setOnAction(e -> {
            createNewUser(stage);
        });  
        
        loginPane.getChildren().addAll(loginMessage, loginButton, createButton);
        return loginPane;
    }
    
    private void setInputFields(VBox loginPane) {
        usernameInput = inputWindow.createInputField(loginPane, "Username");
        passwordInput = inputWindow.createInputField(loginPane, "Password");
    }
    
    private void logIn(Stage stage, ObservationTableScene observationTable) {
        loginMessage.setText("");
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        try {
            if (userService.login(username, password)){
                loginMessage.setText("");
                usernameInput.setText("");
                passwordInput.setText("");
                observationTable.setObservationScene(stage);
            } else {
                loginMessage.setText("User does not exist.");
            }
        } catch (Exception ex) {
            loginMessage.setText("Something went wrong! Try again.");
        }
    }
    
    private void createNewUser(Stage stage) {
        usernameInput.setText("");
        passwordInput.setText("");
        NewUserScene newUser = new NewUserScene(inputWindow, userService);
        newUser.setNewUserScene(stage, this);
    }
}
