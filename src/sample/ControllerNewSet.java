package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ControllerNewSet {
    @FXML TextField textField;
    private Label label;
    @FXML private Label giveNameLabel;
    @FXML private Button BackButton, NextButton;

    public static void setNameOfLanguage(String nameOfLanguage) {
        ControllerNewSet.nameOfLanguage = nameOfLanguage;
    }

    private static String nameOfLanguage;

    public static void setListOfTranslations(ArrayList<String> listOfTranslations) {
        ControllerNewSet.listOfTranslations = listOfTranslations;
    }

    @FXML public void initialize(){
        giveNameLabel.setText(listOfTranslations.get(13));
        BackButton.setText(listOfTranslations.get(14));
        NextButton.setText(listOfTranslations.get(15));
    }

    private static ArrayList<String> listOfTranslations;

    public void handleBackButton(ActionEvent event) throws Exception{
        Controller.setNameOfLanguage(nameOfLanguage);
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,"sample.fxml");
    }



    public void handleNextButton(ActionEvent event) throws Exception{
        ControllerModifySet.setWhichScene("CreatingNewSet.fxml");
        if (!textField.getText().isEmpty() && !textField.getText().contains(".")) {
            try {
                File file = new File("./resources/" + textField.getText() + ".txt");
                if(!file.exists()) {
                    PrintWriter writer = new PrintWriter("./resources/" + textField.getText() + ".txt", "UTF-8");
                    writer.close();

                    ControllerModifySet.setName(textField.getText());

                    SceneChanger sceneChanger = new SceneChanger();
                    sceneChanger.ChangingScene(event,"ModifySet.fxml");

                    ControllerModifySet.setNameOfSet(textField.getText());

                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("There is a set named " + textField.getText());
                    alert.showAndWait();
                }



            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(listOfTranslations.get(16));
            alert.showAndWait();
        }


    }

}
