
package project.scenes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.domain.UserService;
import project.ui.InputWindow;

public class NewUserScene {
    private InputWindow inputWindow;
    private UserService userService;
    private Button returnButton;
    
    public NewUserScene(InputWindow inputWindow, UserService userService) {
        this.inputWindow = inputWindow;
        this.userService = userService;
        returnButton = inputWindow.createButton("Return");
    } 

    public Scene newUserScene(Stage stage, Scene loginScene) {
        VBox newUserPane = inputWindow.createNewWindow();
        TextField newUsernameInput = inputWindow.createInputField(newUserPane, "Username");
        TextField newNameInput = inputWindow.createInputField(newUserPane, "Name");
        TextField newPasswordInput = inputWindow.createInputField(newUserPane, "Password");
        
        Label userCreationMessage = new Label();
        
        Button createNewUserButton = inputWindow.createButton("Create");
        createNewUserButton.setPadding(new Insets(10));

        createNewUserButton.setOnAction(e-> {
            String username = newUsernameInput.getText();
            String name = newNameInput.getText();
            String password = newPasswordInput.getText();
   
            if (username.length() < 2 || name.length() < 2) {
                userCreationMessage.setText("Username or name too short");
                userCreationMessage.setTextFill(Color.RED);              
            } else try {
                if (userService.createUser(username, name, password)) {
                    userCreationMessage.setText("");
                    
//                Label loginMessage = new Label();
//                loginMessage.setText("New user created");
//                loginMessage.setTextFill(Color.GREEN);

stage.setScene(loginScene);
                } else {
                    userCreationMessage.setText("Username already in use");
                    userCreationMessage.setTextFill(Color.RED);        
                }
            } catch (Exception ex) {
                Logger.getLogger(NewUserScene.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        });  
        
        newUserPane.getChildren().addAll(userCreationMessage, createNewUserButton, returnButton);
        Scene newUserScene = new Scene(newUserPane, 400, 300);
        return newUserScene;
    }
    
    public Button returnButton() {
        return this.returnButton;
    }
}
