package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sun.misc.IOUtils;

import javax.swing.*;
import java.lang.Object;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Controller{

    public static final ObservableList observableList = FXCollections.observableArrayList();

    @FXML private Button b1,b2,b3;
    @FXML private ComboBox selectLanguage;
    private static ArrayList<String> listOfTranslations;
    private ArrayList<String> list;
    private String name;
    private File[] listOfFiles;

    public static void setNameOfLanguage(String nameOfLanguage) {
         Controller.nameOfLanguage = nameOfLanguage;
    }

    private static String nameOfLanguage = "English";

    private static void setControllersLanguage(){
        Controller2.setNameOfLanguage(nameOfLanguage);
        ControllerModifyWord.setNameOfLanguage(nameOfLanguage);
        ControllerModifySet.setNameOfLanguage(nameOfLanguage);
        ControllerFlashCards.setNameOfLanguage(nameOfLanguage);
        ControllerNewSet.setNameOfLanguage(nameOfLanguage);

        Controller2.setListOfTranslations(listOfTranslations);
        ControllerModifySet.setListOfTranslations(listOfTranslations);
        ControllerFlashCards.setTranslationList(listOfTranslations);
        ControllerNewSet.setListOfTranslations(listOfTranslations);

    }


    @FXML public void initialize(){
        getTranslationArray(nameOfLanguage);
        File folder = new File("./resources/" );
        listOfFiles = folder.listFiles();
        list = new ArrayList<>();
        String[] listOfStrings = {"translate", "French", "English", "Deutsch"};
        int x;
        for (int i = 0; i < listOfFiles.length; i++) {
            x = 0;
            for(int j = 0; j<listOfStrings.length; j++) {
                if(listOfFiles[i].getName().contains(listOfStrings[j])) x = 1;
            }
            if(x == 0) list.add(listOfFiles[i].getName().replace(".txt", ""));
        }

        selectLanguage.getSelectionModel().select(nameOfLanguage);

        setControllersLanguage();

    }

    public void handleSelect(){


            getTranslationArray(selectLanguage.getSelectionModel().getSelectedItem().toString());
            b1.setText(listOfTranslations.get(0));
            b2.setText(listOfTranslations.get(1));
            b3.setText(listOfTranslations.get(2));
            nameOfLanguage = selectLanguage.getSelectionModel().getSelectedItem().toString();
        setControllersLanguage();

    }



    public void handleButtonFirstClicked(ActionEvent event) throws Exception {

        observableList.clear();

        if(!list.isEmpty()) {
            observableList.addAll(list);
            Controller2.setObservableList(observableList);
        }

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,"sample2.fxml");

    }

    public void handleButtonSecondClicked(ActionEvent event) throws Exception{

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,"CreatingNewSet.fxml");

    }

    public void handleButtonThirdClicked(ActionEvent event) throws Exception{
        int randomNum = ThreadLocalRandom.current().nextInt(0,list.size());
        name = list.get(randomNum);
        ControllerFlashCards.getName(name);
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,"FlashCards.fxml");
    }

    public void getTranslationArray(String name){


        File file = new File("./resources/" + name + "/" + name + "Translation.txt");
        listOfTranslations = new ArrayList<>();


        try (Scanner scanner = new Scanner(file)) {

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                listOfTranslations.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        b1.setText(listOfTranslations.get(0));
        b2.setText(listOfTranslations.get(1));
        b3.setText(listOfTranslations.get(2));

        setControllersLanguage();
    }

}
