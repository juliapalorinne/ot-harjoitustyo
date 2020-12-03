
package project.ui;


import java.util.logging.Level;
import java.util.logging.Logger;
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
import project.domain.ObservationService;
import project.domain.PlaceService;
import project.domain.SpeciesService;
import project.domain.UserService;


public class ProjectUi extends Application {
    private ObservationService observationService;
    private UserService userService;
    private PlaceService placeService;
    private SpeciesService speciesService;
    
    private InputWindow inputWindow;
    private NewUser newUser;
    private ObservationTable observationTable;
    private NewObservation newObservation;
    private NewPlace newPlace;
    private NewSpecies newSpecies;
    
    private Scene observationScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene newObservationScene;
    private Scene newPlaceScene;
    private Scene newSpeciesScene;
    
    
    @Override
    public void init() throws Exception {
        observationService = new ObservationService();
        userService = new UserService(observationService);
        speciesService = new SpeciesService();
        placeService = new PlaceService();
        inputWindow = new InputWindow();
        observationTable = new ObservationTable(observationService);
        newObservation = new NewObservation(observationService, placeService, speciesService, inputWindow); 
        newUser = new NewUser(inputWindow, userService);
        newSpecies = new NewSpecies(inputWindow, speciesService);
        newPlace = new NewPlace(inputWindow, placeService);
        
//        addSpeciesListToDatabase();
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
            try {
                if (userService.login(username, password)){
                    loginMessage.setText("");
                    primaryStage.setScene(observationScene);
                    usernameInput.setText("");
                    passwordInput.setText("");
                } else {
                    loginMessage.setText("user does not exist");
                    loginMessage.setTextFill(Color.RED);      
                }
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
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
        newObservation.addNewSpeciesButton().setOnAction(e-> {
            primaryStage.setScene(newSpeciesScene);
        });        
        newObservation.addNewPlaceButton().setOnAction(e-> {
            primaryStage.setScene(newPlaceScene);
        });        
        
        
        
        // new addNewSpeciesScene
        
        newSpeciesScene = newSpecies.newSpeciesScene(primaryStage, newObservationScene);
        newSpecies.returnButton().setOnAction(e-> {
            primaryStage.setScene(newObservationScene);
        });
        
        
        // new addNewPlaceScene
        
        newPlaceScene = newPlace.newPlaceScene(primaryStage, newObservationScene);
        newPlace.returnButton().setOnAction(e-> {
            primaryStage.setScene(newObservationScene);
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
    
    
    private void addSpeciesListToDatabase() {
        speciesService.createSpecies("Bar-headed goose", "Anser indicus", "tiibetinhanhi", "ansind");
        speciesService.createSpecies("Tundra bean-goose", "Anser serrirostris", "tundrahanhi", "ansser");
        speciesService.createSpecies("Mute swan", "Cygnus olor", "kyhmyjoutsen", "cygolo");
        speciesService.createSpecies("Northern shoveler", "Spatula clypeata", "lapasorsa", "spacly");
        speciesService.createSpecies("Gadwall", "Mareca strepera", "harmaasorsa", "marstr");
        speciesService.createSpecies("Eurasian wigeon", "Mareca penelope", "haapana", "marpen");
        speciesService.createSpecies("Mallard", "Anas platyrhynchos", "sinisorsa", "anapla");
        speciesService.createSpecies("Northern pintail", "Anas acuta", "jouhisorsa", "anaacu");
        speciesService.createSpecies("Black grouse", "Tetrao tetrix", "teeri", "tetrix");
        speciesService.createSpecies("Hazel grouse", "Tetrastes bonasia", "pyy", "tetbon");
        speciesService.createSpecies("Red-necked grebe", "Podiceps grisegena", "härkälintu", "podgri");
        speciesService.createSpecies("Common wood-pigeon", "Columba palumbus", "sepelkyyhky", "colpal");
        speciesService.createSpecies("Common swift", "Apus apus", "tervapääsky", "apuapu");
        speciesService.createSpecies("Eurasian moorhen", "Gallinula chloropus", "liejukana", "galchl");
        speciesService.createSpecies("Common crane", "Grus grus", "kurki", "grugru");
        speciesService.createSpecies("European golden-plover", "Pluvialis apricaria", "kapustarinta", "pluapr");
        speciesService.createSpecies("Little ringed plover", "Charadrius dubius", "pikkutylli", "chadub");
        speciesService.createSpecies("Eurasian curlew", "Numenius arquata", "kuovi", "numarq");
        speciesService.createSpecies("Eurasian woodcock", "Scolopax rusticola", "lehtokurppa", "scorus");
        speciesService.createSpecies("Black-headed gull", "Chroicocephalus ridibundus", "naurulokki", "larrid");
        speciesService.createSpecies("Arctic tern", "Sterna paradisaea", "lapintiira", "steaea");
        speciesService.createSpecies("Arctic loon", "Gavia arctica", "kuikka", "gavarc");
        speciesService.createSpecies("Gray heron", "Ardea cinerea", "harmaahaikara", "ardcin");
        speciesService.createSpecies("Golden eagle", "Aquila chrysaetos", "maakotka", "aquchr");
        speciesService.createSpecies("White-tailed eagle", "Haliaeetus albicilla", "merikotka", "halalb");
        speciesService.createSpecies("Northern hawk owl", "Surnia ulula", "hiiripöllö", "surulu");
        speciesService.createSpecies("Tawny owl", "Strix aluco", "lehtopöllö", "stralu");
        speciesService.createSpecies("Great spotted woodpecker", "Dendrocopos major", "käpytikka", "denmaj");
        speciesService.createSpecies("Eurasian kestrel", "Falco tinnunculus", "tuulihaukka", "faltin");
        speciesService.createSpecies("Red-backed shrike", "Lanius collurio", "pikkulepinkäinen", "lancol");
        speciesService.createSpecies("Eurasian magpie", "Pica pica", "harakka", "picpic");
        speciesService.createSpecies("Common raven", "Corvus corax", "korppi", "corrax");
        speciesService.createSpecies("Crested tit", "Parus cristatus", "töyhtötiainen", "parcri");
        speciesService.createSpecies("Great tit", "Parus major", "talitiainen", "parmaj");
        speciesService.createSpecies("Wood lark", "Lullula arborea", "kangaskiuru", "lularb");
        speciesService.createSpecies("Bearded reedling", "Panurus biarmicus", "viiksitimali", "panbia");
        speciesService.createSpecies("Icterine warbler", "Hippolais icterina", "kultarinta", "hipict");
        speciesService.createSpecies("Common grasshopper-warbler", "Locustella naevia", "pensassirkkalintu", "locnae");
        speciesService.createSpecies("Barn swallow", "Hirundo rustica", "haarapääsky", "hirrus");
        speciesService.createSpecies("Common house-martin", "Delichon urbicum", "räystäspääsky", "delurb");
        speciesService.createSpecies("Willow warbler", "Phylloscopus trochilus", "pajulintu", "phytro");
        speciesService.createSpecies("Common chiffchaff", "Phylloscopus collybita", "tiltaltti", "phycol");
        speciesService.createSpecies("Lesser whitethroat", "Sylvia curruca", "hernekerttu", "sylcur");
        speciesService.createSpecies("Goldcrest", "Regulus regulus", "hippiäinen", "regreg");
        speciesService.createSpecies("Eurasian nuthatch", "Sitta europaea", "pähkinänakkeli", "siteur");
        speciesService.createSpecies("Eurasian treecreeper", "Certhia familiaris", "puukiipijä", "cerfam");
        speciesService.createSpecies("Eurasian wren", "Troglodytes troglodytes", "peukaloinen", "trotro");
        speciesService.createSpecies("White-throated dipper", "Cinclus cinclus", "koskikara", "cincin");
        speciesService.createSpecies("European starling", "Sturnus vulgaris", "kottarainen", "stuvul");
        speciesService.createSpecies("Song thrush", "Turdus philomelos", "laulurastas", "turphi");
        speciesService.createSpecies("Bluethroat", "Luscinia svecica", "sinirinta", "lussve");
        speciesService.createSpecies("Whinchat", "Saxicola rubetra", "pensastasku", "saxrub");
        speciesService.createSpecies("", "", "", "");
        
        
        
    }
}
