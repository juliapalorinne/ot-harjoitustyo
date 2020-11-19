
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.dao.FileObservationDao;
import project.dao.FileUserDao;
import project.domain.Observation;
import project.domain.ObservationService;
import project.domain.UserService;

public class ProjectUi extends Application {
    private ObservationService observationService;
    private UserService userService;
    private WindowService windowService;
    
    
    private Scene observationScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene newObservationScene;
    
    
    private VBox observationNodes;
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
        windowService = new WindowService();
    }

    
    public Node createObservationNode(Observation obs) {
        HBox box = new HBox(10);
        Label label  = new Label(obs.getSpecies());
        label.setMinHeight(28);
                
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().addAll(label, spacer);
        return box;
    }

    
    public void redrawObservationList() {
        observationNodes.getChildren().clear();     

        List<Observation> obses = observationService.getAll();
        obses.forEach(obs->{
            observationNodes.getChildren().add(createObservationNode(obs));
        });     
    }



    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        VBox loginPane = new VBox(10);
        loginPane.setStyle("-fx-background-color: #90EE90;");
        loginPane.setPadding(new Insets(10));
        TextField usernameInput = windowService.createInputField(loginPane, "Username");
        TextField passwordInput = windowService.createInputField(loginPane, "Password");
        
        Label loginMessage = new Label();
        Button loginButton = new Button("Login");
        Button createButton = new Button("Create new user");
        loginButton.setOnAction(e->{
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            menuLabel.setText(username + " logged in...");
            if ( userService.login(username, password)){
                loginMessage.setText("");
                redrawObservationList();
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
        TextField newUsernameInput = windowService.createInputField(newUserPane, "Username");
        TextField newNameInput = windowService.createInputField(newUserPane, "Name");
        TextField newPasswordInput = windowService.createInputField(newUserPane, "Password");
        
        
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
       
        newUserScene = new Scene(newUserPane, 600, 400);
        
        
        
        
        // main scene
        
        ScrollPane obsScollbar = new ScrollPane();       
        BorderPane mainPane = new BorderPane(obsScollbar);
        observationScene = new Scene(mainPane, 600, 600);
                
        HBox menuPane = new HBox(10);    
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Button logoutButton = new Button("Logout");      
        menuPane.getChildren().addAll(menuLabel, menuSpacer, logoutButton);
        logoutButton.setOnAction(e->{
            userService.logout();
            primaryStage.setScene(loginScene);
        });        
        
        HBox createForm = new HBox(10);    
        Button createObs = new Button("Create new");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        createForm.getChildren().addAll(spacer, createObs);
        
        createObs.setOnAction(e->{
            primaryStage.setScene(newObservationScene);   
        });  
        
        
        observationNodes = new VBox(10);
        observationNodes.setMaxWidth(280);
        observationNodes.setMinWidth(280);
        redrawObservationList();
        
        obsScollbar.setContent(observationNodes);
        mainPane.setBottom(createForm);
        mainPane.setTop(menuPane);
        
        
        
        // new createNewObsScene

        
        VBox newObsPane = new VBox(10);
        TextField newSpeciesInput = windowService.createInputField(newObsPane, "Species");
        TextField newIndividualInput = windowService.createInputField(newObsPane, "Number of individuals");
        TextField newPlaceInput = windowService.createInputField(newObsPane, "Place");
        TextField newDateInput = windowService.createInputField(newObsPane, "Date (dd/mm/yyyy)");
        TextField newTimeInput = windowService.createInputField(newObsPane, "Time (hh:mm)");
        TextField newInfoInput = windowService.createInputField(newObsPane, "Additional info");

        Label obsCreationMessage = new Label();
        Button createNewObsButton = new Button("create");
        createNewObsButton.setPadding(new Insets(10));

        createNewObsButton.setOnAction(e->{
            String species = newSpeciesInput.getText();
            String place = newPlaceInput.getText();
            String d = newDateInput.getText();
            SimpleDateFormat dformatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = dformatter.parse(d);
            } catch (ParseException ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            LocalTime time = LocalTime.parse(newTimeInput.getText());
            String info = newInfoInput.getText();
            int individuals = Integer.parseInt(newIndividualInput.getText());
            
            if ( species.length()<3 || place.length()<2 ) {
                userCreationMessage.setText("Species or place too short");
                userCreationMessage.setTextFill(Color.RED);
            } else if (observationService.createObservation(species, individuals, place, date, time, info)){
                obsCreationMessage.setText("New observation created");                
                loginMessage.setText("Observations saved");
                loginMessage.setTextFill(Color.YELLOW);
                primaryStage.setScene(observationScene);      
            }
 
        });  
        
        newObsPane.getChildren().addAll(userCreationMessage, createNewObsButton);
       
        newObservationScene = new Scene(newObsPane, 600, 400);    
        
        
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
      System.out.println("sovellus sulkeutuu");
    }     
    
    
    
    public static void main(String[] args) {
        launch(args);
    }    
}
