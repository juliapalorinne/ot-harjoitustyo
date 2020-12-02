
package project.ui;

import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private NewUser newUser;
    private ObservationTable observationTable;
    private NewObservation newObservation;
    
    private Scene observationScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene newObservationScene;
    
    
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
        newUser = new NewUser(inputWindow, userService);
    }

    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        VBox loginPane = inputWindow.createNewWindow();
        TextField usernameInput = inputWindow.createInputField(loginPane, "Username");
        TextField passwordInput = inputWindow.createInputField(loginPane, "Password");
        
        Label loginMessage = new Label();
        Button loginButton = inputWindow.createButton("Login");
        Button createButton = inputWindow.createButton("Create new user");
        loginButton.setOnAction(e->{
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if ( userService.login(username, password)){
                loginMessage.setText("");
                primaryStage.setScene(observationScene);  
                usernameInput.setText("");
                passwordInput.setText("");
            } else {
                loginMessage.setText("user does not exist");
                loginMessage.setTextFill(Color.RED);
            }      
        });  
        
        createButton.setOnAction(e->{
            usernameInput.setText("");
            passwordInput.setText("");
            primaryStage.setScene(newUserScene);   
        });  
        
        loginPane.getChildren().addAll(loginMessage, loginButton, createButton);       
        
        loginScene = new Scene(loginPane, 400, 250);
        
        
        
        
        // new createNewUserScene
        
        newUserScene = newUser.newUserScene(primaryStage, loginScene);
        newUser.returnButton().setOnAction(e-> {
            primaryStage.setScene(loginScene);
        });
        
        
        
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
        newObservation.returnButton().setOnAction(e-> {
            primaryStage.setScene(observationScene);
        });        
        
        
        
        // seutp primary stage
        
        primaryStage.setTitle("Observations");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("closing");
            System.out.println(userService.getLoggedUser());
            if (userService.getLoggedUser() != null) {
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
