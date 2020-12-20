package project.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.domain.UserService;
import project.ui.InputWindow;

public class NewUserScene {
    private final InputWindow inputWindow;
    private final UserService userService;
    private final Button returnButton;
    private final Button createNewUserButton;
    private TextField newUsernameInput;
    private TextField newNameInput;
    private TextField newPasswordInput;
    private final Label userCreationMessage;
    
    public NewUserScene(InputWindow inputWindow, UserService userService) {
        this.inputWindow = inputWindow;
        this.userService = userService;
        this.returnButton = inputWindow.createButton("Return");
        this.createNewUserButton = inputWindow.createButton("Create");
        this.userCreationMessage = inputWindow.createErrorMessage("");
    } 
    
    public void setNewUserScene(Stage stage, LoginScene loginScene) {
        Scene newUserScene = new Scene(newUserScene(stage, loginScene), 400, 300);
        stage.setScene(newUserScene);
    }

    private VBox newUserScene(Stage stage, LoginScene loginScene) {
        VBox newUserPane = inputWindow.createNewWindow();
        setInputFields(newUserPane);
        
        createNewUserButton.setOnAction(e -> {
            createNewUser(stage, loginScene);
        });  
        
        returnButton.setOnAction(e -> {
            returnToLoginScene(stage, loginScene);
        });
        
        newUserPane.getChildren().addAll(userCreationMessage, createNewUserButton, returnButton);
        return newUserPane;
    }
    
    private void setInputFields(VBox newUserPane) {
        newUsernameInput = inputWindow.createInputField(newUserPane, "Username");
        newNameInput = inputWindow.createInputField(newUserPane, "Name");
        newPasswordInput = inputWindow.createInputField(newUserPane, "Password");   
    }
    
    private void createNewUser(Stage stage, LoginScene loginScene) {
        String username = newUsernameInput.getText();
        String name = newNameInput.getText();
        String password = newPasswordInput.getText();

        if (checkUsername(username) && checkName(name) && checkPassword(password)) {
            try {
                if (userService.createUser(username, name, password)) {
                    userCreationMessage.setText("");
                    loginScene.setLoginScene(stage);
                } else {
                    userCreationMessage.setText("Username already in use.");
                }
            } catch (Exception ex) {
                userCreationMessage.setText("Something went wrong! Try again.");
            }
        }
    }

    private boolean checkUsername(String username) {
        if (username.length() < 2) {
            userCreationMessage.setText("Username is too short.");
            return false;
        } else if (username.length() > 30) {
            userCreationMessage.setText("Username is too long.");
            return false;
        } else if (!validateTextInput(username)) {
            userCreationMessage.setText("Username contains forbidden characters.");
            return false;
        }
        return true;
    }

    private boolean checkName(String name) {
        if (name.length() < 2) {
            userCreationMessage.setText("Name is too short.");
            return false;
        } else if (name.length() > 50) {
            userCreationMessage.setText("Name is too long.");
            return false;
        } else if (!validateTextInput(name)) {
            userCreationMessage.setText("Name contains forbidden characters.");
            return false;
        }
        return true;
    }
    
    private boolean checkPassword(String password) {
        if (password.length() < 8) {
            userCreationMessage.setText("Password is too short.");
            return false;
        } else if (password.length() > 30) {
            userCreationMessage.setText("Password is too long.");
            return false;
        } else if (!password.matches(".*[a-zA-Z].*")) {
            userCreationMessage.setText("Password has to contain letters.");
            return false;
        }
        return true;
    }
    
    private boolean validateTextInput(String input) {
        String[] forbiddenCharacters = new String[]{"?", "*", "<", ">", "[", "]", "'", "&", "="};
        
        for (String c : forbiddenCharacters) {
            if (input.contains(c)) {
                return false;
            }
        }
        return true;
    }
    
    private void returnToLoginScene(Stage stage, LoginScene loginScene) {
        try {
            userCreationMessage.setText("");
            loginScene.setLoginScene(stage);
        } catch (Exception ex) {
            userCreationMessage.setText("Could not return to login view. Try again.");
        }
    }
}
