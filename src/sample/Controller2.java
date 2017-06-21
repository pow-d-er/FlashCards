package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Controller2{
    private String name;
    @FXML private ListView listView;
    @FXML private Button BackButton, ChooseButton, DeleteButton, CreateNewButton, ModifyButton;

    public static void setNameOfLanguage(String nameOfLanguage) {
        Controller2.nameOfLanguage = nameOfLanguage;
    }

    private static String nameOfLanguage;

    public static void setListOfTranslations(ArrayList<String> listOfTranslations) {
        Controller2.listOfTranslations = listOfTranslations;

    }

    public static void setObservableList(ObservableList observableList) {
        Controller2.observableList = observableList;
    }

    private static ObservableList observableList;

    static private ArrayList<String> listOfTranslations;

    @FXML public void initialize(){

        listView.setItems(observableList);
        BackButton.setText(listOfTranslations.get(3));
        CreateNewButton.setText(listOfTranslations.get(4));
        ChooseButton.setText(listOfTranslations.get(5));
        DeleteButton.setText(listOfTranslations.get(6));
        ModifyButton.setText(listOfTranslations.get(21));

    }

    public void handleButtonBack(Event event) throws Exception{
        Controller.setNameOfLanguage(nameOfLanguage);
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,"sample.fxml");
    }

    public void handleButtonDelete(Event event) throws Exception{

        if (!listView.getItems().isEmpty()) {
            String name = listView.getSelectionModel().getSelectedItems().get(0).toString();
            File file = new File("./resources/" + name + ".txt");
           listView.getItems().remove(listView.getSelectionModel().getSelectedItems().get(0));
           file.delete();

            file = new File("./resources/" + name + "_translate.txt");
            file.delete();
        }
    }

    public void handleButtonCreateNew(Event event) throws Exception {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,"CreatingNewSet.fxml");
    }

    public void handleButtonChoose(Event event) throws Exception{
        if(!listView.getSelectionModel().isEmpty()) {
            name = listView.getSelectionModel().getSelectedItems().get(0).toString();
            ControllerFlashCards.getName(name);
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.ChangingScene(event,"FlashCards.fxml");

        }
    }

    public void handleModifyButton(ActionEvent event) throws Exception{
        ControllerModifySet.setWhichScene("sample2.fxml");
        ControllerModifySet.setName(listView.getSelectionModel().getSelectedItem().toString());
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,"ModifySet.fxml");
    }
}
