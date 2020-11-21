
package project.ui;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.domain.Observation;
import project.domain.ObservationService;

public class ObservationTable {
    private ObservationService observationService;
    
    private TableView table = new TableView();
//    private VBox observationNodes;
    
    private ObservableList<Observation> observations;
    
    
    public ObservationTable(ObservationService observationService) {
        this.observationService = observationService;
        this.observations = FXCollections.observableArrayList();
    }  
    
    
    
    public void createTable(Scene scene) {
        
        final Label label = new Label("Observations");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn speciesCol = new TableColumn("Species");
        speciesCol.setCellValueFactory(new PropertyValueFactory<Observation, String>("species"));
        
        TableColumn individualCol = new TableColumn("Individuals");
        individualCol.setCellValueFactory(new PropertyValueFactory<Observation, String>("individuals"));
        
        TableColumn placeCol = new TableColumn("Place");
        placeCol.setCellValueFactory(new PropertyValueFactory<Observation, String>("place"));
        
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<Observation, Date>("date"));
        
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<Observation, LocalTime>("species"));
        
        TableColumn infoCol = new TableColumn("Info");
        infoCol.setCellValueFactory(new PropertyValueFactory<Observation, String>("info"));
        
        table.getColumns().addAll(speciesCol, individualCol, placeCol, 
                dateCol, timeCol, infoCol);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
    }  
    
    
    
//    public Node createObservationNode(Observation obs) {
//        HBox box = new HBox(10);
//        Label label  = new Label(obs.getSpecies());
//        label.setMinHeight(28);
//                
//        Region spacer = new Region();
//        HBox.setHgrow(spacer, Priority.ALWAYS);
//        box.setPadding(new Insets(0,5,0,5));
//        
//        box.getChildren().addAll(label, spacer);
//        return box;
//    }

    
    public void redrawObservationList() {
        List<Observation> observations = observationService.getAll();
        observations.forEach(obs->{
            observations.add(obs);
        });     
    }
}
