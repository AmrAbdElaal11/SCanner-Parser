package com.example.compiler_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.compiler_gui.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable{
   Scanner s=new Scanner();
   Parser p = new Parser();
    String fileText="";
    StringBuffer string1= new StringBuffer();
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
            //filetextarea=readfile
            fileText=ReadFile.readFile(f,string1);
            fileTextArea.setText(string1.toString());
            srcCodeCheckBox.isSelected();

        }
    }
    @FXML
    private void onParseButtonClick(ActionEvent event) throws FileNotFoundException {


        if(srcCodeCheckBox.isSelected()&& !string1.isEmpty()){
          s.setCode(fileText);
          p.program();
            //creating the image object
            InputStream stream = new FileInputStream("out.png");
            Image image = new Image(stream);
            //Creating the image view
            ImageView imageView = new ImageView();
            //Setting image to the image view
            imageView.setImage(image);
            //Setting the image view parameters
            imageView.setX(10);
            imageView.setY(10);
            imageView.setFitWidth(575);
            imageView.setPreserveRatio(true);
            //Setting the Scene object
            Group root = new Group(imageView);
            Scene scene = new Scene(root, 595, 370);
            Stage newWindow = new Stage();
            newWindow.setTitle("Graphiz tree");




//Set view in window
            newWindow.setScene(scene);
//Launch
            newWindow.show();

        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}