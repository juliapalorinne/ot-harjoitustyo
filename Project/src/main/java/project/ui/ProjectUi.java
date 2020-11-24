
package project.ui;

import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.dao.FileObservationDao;
import project.dao.FileUserDao;
import project.domain.ObservationService;
import project.domain.UserService;

public class ProjectUi extends Application {
    private ObservationService observationService;
    private UserService userService;
    
    private InputWindow inputWindow;
    private ObservationTable observationTable;
    private NewObservation newObservation;
    
    private Scene observationScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene newObservationScene;
    
    
    
    private Label menuLabel = new Label();
    
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        String userFile = properties.getProperty("userFile");
        String obsFile = properties.getProperty("observationFile");
            
        FileUserDao userDao = new FileUserDao(userFile);
        FileObservationDao obsDao = new FileObservationDao(obsFile, userDao);
        observationService = new ObservationService(obsDao);
        userService = new UserService(userDao, observationService);
        inputWindow = new InputWindow();
        observationTable = new ObservationTable(observationService);
        newObservation = new NewObservation(observationService, inputWindow); 
    }

    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        VBox loginPane = new VBox(10);
        loginPane.setStyle("-fx-background-color: #90EE90;");
        loginPane.setPadding(new Insets(10));
        TextField usernameInput = inputWindow.createInputField(loginPane, "Username");
        TextField passwordInput = inputWindow.createInputField(loginPane, "Password");
        
        Label loginMessage = new Label();
        Button loginButton = new Button("Login");
        Button createButton = new Button("Create new user");
        loginButton.setOnAction(e->{
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            menuLabel.setText(username + " logged in...");
            if ( userService.login(username, password)){
                loginMessage.setText("");
                primaryStage.setScene(observationScene);  
                usernameInput.setText("");
            } else {
                loginMessage.setText("user does not exist");
                loginMessage.setTextFill(Color.RED);
            }      
        });  
        
        createButton.setOnAction(e->{
            usernameInput.setText("");
            primaryStage.setScene(newUserScene);   
        });  
        
        loginPane.getChildren().addAll(loginMessage, loginButton, createButton);       
        
        loginScene = new Scene(loginPane, 300, 250);
        
        
        
        
        // new createNewUserScene
        
        VBox newUserPane = new VBox(10);
        newUserPane.setStyle("-fx-background-color: #FFB6C1;");
        TextField newUsernameInput = inputWindow.createInputField(newUserPane, "Username");
        TextField newNameInput = inputWindow.createInputField(newUserPane, "Name");
        TextField newPasswordInput = inputWindow.createInputField(newUserPane, "Password");
        
        
        Label userCreationMessage = new Label();
        
        Button createNewUserButton = new Button("create");
        createNewUserButton.setPadding(new Insets(10));

        createNewUserButton.setOnAction(e->{
            String username = newUsernameInput.getText();
            String name = newNameInput.getText();
            String password = newPasswordInput.getText();
   
            if ( username.length()<2 || name.length()<2 ) {
                userCreationMessage.setText("Username or name too short");
                userCreationMessage.setTextFill(Color.RED);              
            } else if ( userService.createUser(username, name, password) ){
                userCreationMessage.setText("");                
                loginMessage.setText("New user created");
                loginMessage.setTextFill(Color.GREEN);
                primaryStage.setScene(loginScene);      
            } else {
                userCreationMessage.setText("Username already in use");
                userCreationMessage.setTextFill(Color.RED);        
            }
 
        });  
        
        newUserPane.getChildren().addAll(userCreationMessage, createNewUserButton);
        newUserScene = new Scene(newUserPane, 400, 300);
        
        
        
        
        // main scene

        observationScene = observationTable.observationScene();        
        observationTable.logoutButton().setOnAction(e->{
            userService.logout();
            primaryStage.setScene(loginScene);
        });        
        observationTable.addButton().setOnAction(e->{
            primaryStage.setScene(newObservationScene);   
        });  

        
        
        // new createNewObservationScene
        
        newObservationScene = newObservation.createNewObservation(primaryStage, observationTable);
        
        
        // seutp primary stage
        
        primaryStage.setTitle("Observations");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("closing");
            System.out.println(userService.getLoggedUser());
            if (userService.getLoggedUser()!=null) {
                e.consume();   
            }
            
        });
    }
    
    

    @Override
    public void stop() {
      // tee lopetustoimenpiteet täällä
      System.out.println("app is closing");
    }     
    
    
    
    public static void main(String[] args) {
        launch(args);
    }    
}
