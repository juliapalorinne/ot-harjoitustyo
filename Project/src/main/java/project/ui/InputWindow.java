
package project.ui;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.domain.Place;
import project.domain.Species;

public class InputWindow {

    public VBox createNewWindow() {
        VBox pane = new VBox(10);
        pane.setStyle("-fx-border-style: groove; -fx-border-width: 10px;"
                + "-fx-border-color: #000000;");
        pane.setStyle("-fx-background-color: #FFB6C1;");
        pane.setPadding(new Insets(10));
        return pane;
    }
    
    public TextField createInputField(VBox pane, String name) {
        HBox newPane = new HBox(10);
        newPane.setPadding(new Insets(10));
        TextField newInput = new TextField();
        newInput.setStyle("-fx-background-radius: 10px;");
        
        Label newLabel = new Label(name);
        setLabelStyle(newLabel);
        
        newPane.getChildren().addAll(newLabel, newInput);
        pane.getChildren().addAll(newPane);
        return newInput;
    }
    
    public TextField createBigInputField(VBox pane, String name) {
        HBox newPane = new HBox(10);
        newPane.setPadding(new Insets(10));
        TextField newInput = new TextField();
        newInput.setPrefSize(200, 60);
        
        Label newLabel = new Label(name);
        setLabelStyle(newLabel);
        
        newPane.getChildren().addAll(newLabel, newInput);
        pane.getChildren().addAll(newPane);
        return newInput;
    }
    
    public DatePicker createDatePicker(VBox pane, String name) {
        HBox newPane = new HBox(10);
        newPane.setPadding(new Insets(10));
        DatePicker datePicker = new DatePicker();
        
        Label newLabel = new Label(name);
        setLabelStyle(newLabel);
        
        newPane.getChildren().addAll(newLabel, datePicker);
        pane.getChildren().addAll(newPane);
        return datePicker;
    }
    
    
    public Button createButton(String name) {
        Button button = new Button(name);
        button.setStyle("-fx-background-color: #6A5ACD; -fx-font-weight: bold;"
                + "-fx-text-fill: #F0F8FF; -fx-background-radius: 10px;");
        return button;
    }
    
    
    public void setLabelStyle(Label label) {
        label.setFont(Font.font("Arial", 14));
        label.setStyle("-fx-font-weight: bold;");
        label.setPrefWidth(150);
    }    

    
//    public ListView<Object> createListView(VBox pane, List<Object> list, String name) {
//        ObservableList<Object> observableList = FXCollections.observableArrayList();
//        list.forEach(object -> {
//            observableList.add(object);
//        });
//        
//        ListView<Object> listView = new ListView<>();
//        listView.setItems(observableList);
//        
//        HBox newPane = new HBox(10);
//        Label newLabel = new Label(name);
//        setLabelStyle(newLabel);
//        
//        newPane.getChildren().addAll(newLabel, listView); 
//        pane.getChildren().addAll(newPane);
//        return listView;
//    }
}
