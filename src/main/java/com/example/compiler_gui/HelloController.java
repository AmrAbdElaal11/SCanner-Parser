package com.example.compiler_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable{
   Scanner s=new Scanner();
   Parser p = new Parser();
    String fileText="";
    @FXML
    private Button openBtn;
    @FXML
    private Button parseBtn;
    @FXML
    private Button showTreeBtn;
    @FXML
    private TextArea fileTextArea;
    @FXML
    private CheckBox srcCodeCheckBox;


    @FXML
    private void onOpenButtonClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if (f!=null){
            fileText = Util.readSrcFile(f);
            if(fileText.isEmpty() || fileText.isBlank()){
                popOutError("Empty File!!");
            }
            else {
                fileTextArea.clear();
                fileTextArea.setText(fileText);
            }
        }
    }
    @FXML
    private void onParseButtonClick(ActionEvent event) throws FileNotFoundException {
        fileText = fileTextArea.getText();
        if(fileText.isEmpty() || fileText.isBlank()){
            popOutError("Empty input!!");
            return;
        }
        if(srcCodeCheckBox.isSelected()){
            fileText = Util.replaceNewLines(fileText);
            s.setCode(fileText);
        }
        else{
            Util.parseTokenTextInput(fileText);
        }
        try{
            p.program();
            InputStream stream = new FileInputStream("out.png");
            Image image = new Image(stream);
            //Creating the image view
            ImageView imageView = new ImageView();
            //Setting image to the image view
            imageView.setImage(image);
            //Setting the image view parameters
            imageView.setX(0);
            imageView.setY(0);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(imageView);
            scrollPane.setFitToHeight(true);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(scrollPane , Util.min(screenBounds.getWidth() , image.getWidth() + 10) , Util.min(screenBounds.getHeight() - 50 , image.getHeight() + 10)  );
            Stage newWindow = new Stage();
            newWindow.setTitle("generated syntax tree");
            newWindow.setScene(scene);
            newWindow.show();
        }catch(ParsingException e){
            popOutError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void popOutError(String errorMessage){
        Stage popupwindow = new Stage();
        popupwindow.setTitle("Error!");
        Label label1 = new Label(errorMessage);
        Button button1 = new Button("Close");
        button1.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}