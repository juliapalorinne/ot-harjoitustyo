package project.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InputWindow {

    public VBox createNewWindow() {
        VBox pane = new VBox(10);
        pane.setStyle("-fx-border-width: 3px;"
                + "-fx-border-color: #330066;"
                + "-fx-background-color: linear-gradient(from 25% 0% to 100% 75%, #FF69B4, #663399)");
//        pane.setStyle("-fx-font-weight: bold; -fx-text-fill: #F0F8FF;");
        pane.setPadding(new Insets(10));
        pane.setSpacing(5);
        return pane;
    }
    
    public VBox observationBox() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-width: 1px; -fx-border-color: #330066; "
                + "-fx-background-color: linear-gradient(to top right, #e6ccff, #bf80ff);");
        return vbox;
    }
    
    public HBox observationBoxRow() {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(5, 10, 5, 20));
        return hbox;
    }
    
    public HBox infoBox(String text) {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
        hbox.setStyle("-fx-border-width: 1px; -fx-border-color:  #330066; "
                + "-fx-background-color: linear-gradient(to bottom right, #ffb3ec,  #ffb3e6);");
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 16));
        hbox.getChildren().addAll(label);
        return hbox;
    }
    
    public TextField createTextField(int width) {
        TextField newInput = new TextField();
        newInput.setStyle("-fx-background-radius: 5px;");
        newInput.setPrefWidth(width);
        return newInput;
    }
    
    public TextField createInputField(VBox pane, String name) {
        HBox newPane = new HBox(10);
        newPane.setPadding(new Insets(10));
        TextField newInput = new TextField();
        newInput.setStyle("-fx-background-radius: 5px;");
        
        Label newLabel = createSmallLabel(name, 150);
        
        newPane.getChildren().addAll(newLabel, newInput);
        pane.getChildren().addAll(newPane);
        return newInput;
    }
    
    public TextField createBigInputField(VBox pane, String name) {
        HBox newPane = new HBox(10);
        newPane.setPadding(new Insets(10));
        TextField newInput = new TextField();
        newInput.setStyle("-fx-background-radius: 5px;");
        newInput.setPrefSize(400, 60);
        
        Label newLabel = createSmallLabel(name, 150);
        
        newPane.getChildren().addAll(newLabel, newInput);
        pane.getChildren().addAll(newPane);
        return newInput;
    }
    
    
    /**
     * Create DatePicker
     * @param pane in which the DatePicker is added
     * @param name the text label shown beside the DatePicker
     * @return created DatePicker
     */
    public DatePicker createDatePicker(HBox pane, String name) {
        HBox newPane = new HBox(10);
        newPane.setPadding(new Insets(10));
        DatePicker datePicker = new DatePicker();
        
        Label newLabel = createSmallLabel(name, 150);
        
        newPane.getChildren().addAll(newLabel, datePicker);
        pane.getChildren().addAll(newPane);
        return datePicker;
    }
    
    
    /**
     * Create purple Button with light blue text
     * @param name the name shown on the Button
     * @return created Button
     */
    public Button createButton(String name) {
        Button button = new Button(name);
        button.setStyle("-fx-background-color: linear-gradient(to bottom right, #4B0082, #330066); "
                + "-fx-text-fill: #F0F8FF; -fx-background-radius: 5px;"
                + "-fx-border-width: 1px; -fx-border-color: #ffccdd;"
                + "-fx-border-radius: 5px;");
        button.setPadding(new Insets(5, 20, 5, 10));
        return button;
    }
    
    
    /**
     * Create purple ChoiceBox with light blue text
     * @return created ChoiceBox
     */
    public ChoiceBox<String> createChoiceBox() {
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setStyle("-fx-background-color: linear-gradient(to top right, #e6ccff, #cc99ff);"
                + "-fx-background-radius: 5px;");
        choiceBox.setPadding(new Insets(5, 20, 5, 10));
        return choiceBox;
    }

    
    /**
     * Set label style for input Labels
     * @param label the Label to style
     * @param width the size of the Label
     */
    public Label createSmallLabel(String text, double width) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 15));
        label.setStyle("-fx-font-weight: bold;");
        label.setPrefWidth(width);
        return label;
    }    

    /**
     * Set label style for input Labels
     * @param text the label text
     * @param width the size of the Label
     */
    public Label createBigLabel(String text, double width) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 20));
        label.setStyle("-fx-font-weight: bold;");
        label.setPrefWidth(width);
        return label;
    }    
    
    

}
