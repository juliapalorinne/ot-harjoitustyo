
package project.ui;

import project.scenes.NewSpeciesScene;
import project.scenes.ObservationTableScene;
import project.scenes.NewUserScene;
import project.scenes.NewPlaceScene;
import project.scenes.NewObservationScene;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.domain.StoreableObservationService;
import project.domain.PlaceService;
import project.domain.SpeciesService;
import project.domain.UserService;
import project.scenes.SearchScene;


public class ProjectUi extends Application {
    private StoreableObservationService observationService;
    private UserService userService;
    private PlaceService placeService;
    private SpeciesService speciesService;
    
    private InputWindow inputWindow;
    private NewUserScene newUser;
    private ObservationTableScene observationTable;
    private NewObservationScene newObservation;
    private NewPlaceScene newPlace;
    private NewSpeciesScene newSpecies;
    private SearchScene searchWindow;
    
    private Scene observationScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene newObservationScene;
    
    
    @Override
    public void init() throws Exception {
        observationService = new StoreableObservationService();
        userService = new UserService(observationService);
        speciesService = new SpeciesService();
        placeService = new PlaceService();
        inputWindow = new InputWindow();
        observationTable = new ObservationTableScene(observationService, speciesService, placeService);
        newObservation = new NewObservationScene(observationService, speciesService, placeService, inputWindow); 
        newUser = new NewUserScene(inputWindow, userService);
        newSpecies = new NewSpeciesScene(inputWindow, speciesService);
        newPlace = new NewPlaceScene(inputWindow, placeService);
        searchWindow = new SearchScene(inputWindow, observationService, speciesService, placeService);
        
        
//        addSpeciesListToDatabase();  // Tästä voi lisätä aluksi muutamankymmentä lintua tietokantaan.
//        addPlaceListToDatabase();    // Ja tästä vastaavasti joitakin paikkoja.
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
                    usernameInput.setText("");
                    passwordInput.setText("");
                    getMainView(primaryStage);
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
    
    public void getMainView(Stage stage) throws Exception {
        observationScene = observationTable.observationScene();        
        stage.setScene(observationScene);
        
        observationTable.logoutButton().setOnAction(e->{
            userService.logout();
            try {
                start(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
        observationTable.addButton().setOnAction(e->{
            try {
                getNewObservationView(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        observationTable.searchButton().setOnAction(e->{
            try {
                getSearchView(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });  
    }
    
    public void getNewObservationView(Stage stage) throws Exception {
        newObservationScene = newObservation.createNewObservation(stage, observationTable);
        stage.setScene(newObservationScene);
        
        newObservation.returnButton().setOnAction(e-> {
            try {
                getMainView(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        newObservation.addNewSpeciesButton().setOnAction(e-> {
            try {
                getNewSpeciesView(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
        newObservation.addNewPlaceButton().setOnAction(e-> {
            try {
                getNewPlaceView(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
        
    }
    
    public void getNewSpeciesView(Stage stage) throws Exception {
        Scene newSpeciesScene = newSpecies.newSpeciesScene(stage, newObservationScene);
        stage.setScene(newSpeciesScene);

        newSpecies.returnButton().setOnAction(e-> {
            try {
                getNewObservationView(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
    public void getNewPlaceView(Stage stage) throws Exception {
        Scene newPlaceScene = newPlace.newPlaceScene(stage, newObservationScene);
        stage.setScene(newPlaceScene);

        newPlace.returnButton().setOnAction(e-> {
            try {
                getNewObservationView(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
    public void getSearchView(Stage stage) throws Exception {
        Scene searchScene = searchWindow.searchScene(stage);
        stage.setScene(searchScene);

        searchWindow.returnButton().setOnAction(e-> {
            try {
                getMainView(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProjectUi.class.getName()).log(Level.SEVERE, null, ex);
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
        speciesService.createSpecies("Common loon", "Gavia immer", "amerikanjääkuikka", "gavimm");
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
    
    private void addPlaceListToDatabase() {
        placeService.createPlace("Finland", "Espoo", "Laajalahti, Maari", "birding tower");
        placeService.createPlace("Finland", "Espoo", "Nuuksio", "forest");
        placeService.createPlace("Finland", "Lammi", "Evo", "old forest");
        placeService.createPlace("Finland", "Hanko", "Halias", "bird station");
        placeService.createPlace("Finland", "Hanko", "Tvärminne", "biological station");
        placeService.createPlace("Finland", "Helsinki", "Fastholma", "birding tower");
        placeService.createPlace("Finland", "Helsinki", "Kivinokka", "birding tower");
        placeService.createPlace("Finland", "Helsinki", "Lammassaari", "birding tower");
        placeService.createPlace("Finland", "Helsinki", "Pornaistenniemi", "birding tower");
        placeService.createPlace("Finland", "Kirkkonummi", "Porkkala", "seaside");
        placeService.createPlace("Finland", "Kuopio", "Kallavesi", "lake");
        placeService.createPlace("Finland", "Parainen", "Jurmo", "bird station");
        placeService.createPlace("Finland", "Parainen", "Utö", "island");
        placeService.createPlace("Finland", "Sipoo", "Sipoonkorpi", "forest");
        placeService.createPlace("", "", "", "");
        
    }
}
