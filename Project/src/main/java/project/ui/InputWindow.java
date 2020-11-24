
package project.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InputWindow {


    
    public TextField createInputField(VBox pane, String name) {
        HBox newPane = new HBox(10);
        newPane.setPadding(new Insets(10));
        TextField newInput = new TextField();
        Label newLabel = new Label(name);
        setLabelStyle(newLabel);
        newLabel.setPrefWidth(100);
        newPane.getChildren().addAll(newLabel, newInput);
        pane.getChildren().addAll(newPane);
        return newInput;
    }
    
    
    
    public void setLabelStyle(Label label) {
        label.setFont(Font.font("Arial", 14));
        label.setStyle("-fx-font-weight: bold;");
    }    

}
