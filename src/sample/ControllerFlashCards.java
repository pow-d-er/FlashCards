package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ControllerFlashCards {

    @FXML private Label word,translation;
    @FXML private Button TopButton,BotButton,RightButton,LeftButton;
    private static String name;
    private ArrayList<String> listOfWords;
    private ArrayList<String> listOfTranslations;
    private int points = 0;

    public static void setNameOfLanguage(String nameOfLanguage) {
        ControllerFlashCards.nameOfLanguage = nameOfLanguage;
    }

    private static String nameOfLanguage;

    public static void setTranslationList(ArrayList<String> translationList) {
        TranslationList = translationList;
    }

    private static ArrayList<String> TranslationList;
    private int i;

    public static void getName(String n){
        name = n;
    }



    @FXML public void initialize(){

        i = 1;


        File file = new File("./resources/" + name + ".txt");
        listOfWords = new ArrayList<>();
        listOfTranslations = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                listOfWords.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File("./resources/" + name + "_translate.txt");

        try (Scanner scanner = new Scanner(file)) {

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                listOfTranslations.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        LeftButton.setText(TranslationList.get(7));
        TopButton.setText(TranslationList.get(8));
        RightButton.setText(TranslationList.get(9));
        BotButton.setText(TranslationList.get(10));

        word.setText(listOfWords.get(0));
        translation.setText(listOfTranslations.get(0));
    }




    public void handleBackButton(Event event) throws Exception {
        Controller.setNameOfLanguage(nameOfLanguage);
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,"sample.fxml");

    }

    public void handleRightButton(Event event) throws Exception{
        points++;
        if (i < listOfWords.size()) {
            word.setText(listOfWords.get(i));
            translation.setText(listOfTranslations.get(i));
            i++;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(TranslationList.get(11));
            alert.setHeaderText(TranslationList.get(25) + points + "/" + listOfWords.size());
            alert.showAndWait();
            RightButton.setDisable(true);
            LeftButton.setDisable(true);
        }
    }

    public void handleLeftButton(){
        LeftButton.setVisible(false);
        RightButton.setVisible(false);
        BotButton.setVisible(true);


        translation.setVisible(true);
    }

    public void handleBotButton(){

         if (i < listOfWords.size()) {
            word.setText(listOfWords.get(i));
            translation.setText(listOfTranslations.get(i));
            i++;

            LeftButton.setVisible(true);
            RightButton.setVisible(true);
            BotButton.setVisible(false);

            translation.setVisible(false);
        }
        else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle(TranslationList.get(11));
             alert.setHeaderText(TranslationList.get(25) + points + "/" + listOfWords.size());
             alert.showAndWait();
             BotButton.setDisable(true);
         }
    }

}
