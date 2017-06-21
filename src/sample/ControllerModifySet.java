package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.lang.model.element.Name;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class ControllerModifySet {

    private ObservableList observableList = FXCollections.observableArrayList();

    public static void setNameOfSet(String nameOfSet) {
        ControllerModifySet.nameOfSet = nameOfSet;
    }

    private static String nameOfSet;

    @FXML private Label NameOfSet, newWordLabel, newTranslationLabel, listLabel;
    @FXML private TextField NewWord, NewTranslation;
    @FXML private ListView listView;
    @FXML private Button BackButton, ModifyButton, DeleteButton, FinishButton, newFlashCardButton;

    public static void setNameOfLanguage(String nameOfLanguage) {
       ControllerModifySet.nameOfLanguage = nameOfLanguage;
    }

    private static String nameOfLanguage;


    public static void setChangedWord(String changedWord) {
        ControllerModifySet.changedWord = changedWord;
    }

    private static String changedWord;

    public static void setChangedTranslation(String changedTranslation) {
        ControllerModifySet.changedTranslation = changedTranslation;
    }

    private static String changedTranslation;


    public static void setName(String name) {
        ControllerModifySet.name = name;
    }

    public static void setWhichScene(String whichScene) {
        ControllerModifySet.whichScene = whichScene;
    }

    private static String whichScene;
    private static String name;


    @FXML public void initialize(){
        NameOfSet.setText(nameOfSet);
        observableList.clear();
        listView.getItems().clear();
        NameOfSet.setText(name);

        File file = new File("./resources/" + name + ".txt");

        try (Scanner scanner = new Scanner(file)) {

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                observableList.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setItems(observableList);

        newWordLabel.setText(listOfTranslations.get(17));
        newTranslationLabel.setText(listOfTranslations.get(18));
        newFlashCardButton.setText(listOfTranslations.get(19));
        BackButton.setText(listOfTranslations.get(20));
        ModifyButton.setText(listOfTranslations.get(21));
        DeleteButton.setText(listOfTranslations.get(22));
        FinishButton.setText(listOfTranslations.get(23));
        listLabel.setText(listOfTranslations.get(24));
    }


    public static void setListOfTranslations(ArrayList<String> listOfTranslations) {
        ControllerModifySet.listOfTranslations = listOfTranslations;
    }

    private static ArrayList<String> listOfTranslations;

    public void handleButtonBack(ActionEvent event) throws Exception{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("If you click OK you wouldn't save this set. Click FINISH to save it.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            Controller.setNameOfLanguage(nameOfLanguage);

            if(whichScene.equals("CreatingNewSet.fxml")){
                File file = new File("./resources/" + name + ".txt");
                if(file.exists()) Files.delete(file.toPath());
                file = new File("./resources/" + name + "_translate.txt");
                if(file.exists()) Files.delete(file.toPath());
            }


            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.ChangingScene(event,whichScene);
        }


    }

    public void handleButtonModify(ActionEvent event) throws Exception {
        String readWord = null, readTranslation = null;
        changedWord = null;
        changedTranslation = null;

        for (int i = 0; i < observableList.size(); i++) {
            if (observableList.get(i).equals(listView.getSelectionModel().getSelectedItem())) {
                File file = new File("./resources/" + name + ".txt");

                try (Scanner scanner = new Scanner(file)) {

                    for (int j = 0; j <= i; j++)
                        readWord = scanner.nextLine();

                    scanner.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                File file2 = new File("./resources/" + name + "_translate.txt");
                try (Scanner scanner = new Scanner(file2)) {

                    for (int j = 0; j <= i; j++)
                        readTranslation = scanner.nextLine();

                    scanner.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                ControllerModifyWord.setWordAndTranslation(readWord, readTranslation);

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyWord.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Modify Word");
                    stage.setScene(new Scene(root, 450, 150));
                    stage.initOwner(listView.getScene().getWindow());
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (!changedWord.isEmpty() && !changedWord.equals(readWord)) {
                    ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

                    for (int x = 0; x < fileContent.size(); x++) {
                        if (fileContent.get(x).equals(readWord)) {
                            fileContent.set(x, changedWord);
                            break;
                        }
                    }

                    Files.write(file.toPath(), fileContent, StandardCharsets.UTF_8);
                    observableList.set(i,changedWord);

                }

                if (!changedTranslation.isEmpty() && !changedTranslation.equals(readTranslation)) {
                    ArrayList<String> fileContent2 = new ArrayList<>(Files.readAllLines(file2.toPath(), StandardCharsets.UTF_8));

                    for (int x = 0; x < fileContent2.size(); x++) {
                        if (fileContent2.get(x).equals(readTranslation)) {
                            fileContent2.set(x, changedTranslation);
                            break;
                        }
                    }

                    Files.write(file2.toPath(), fileContent2, StandardCharsets.UTF_8);


                }

                listView.setItems(observableList);
            }

        }

    }

    public void handleButtonDelete() throws Exception{
        int x;

        File file = new File("./resources/" + name + ".txt");
        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(),StandardCharsets.UTF_8));

        for(x = 0; x<fileContent.size(); x++) {
            if (fileContent.get(x).equals(listView.getSelectionModel().getSelectedItem().toString())) {
                fileContent.remove(x);
                Files.write(file.toPath(), fileContent, StandardCharsets.UTF_8);
            }

        }

        observableList.clear();
        observableList.addAll(fileContent);
        listView.setItems(observableList);

        file = new File("./resources/" + name + "_translate.txt");
        fileContent = new ArrayList<>(Files.readAllLines(file.toPath(),StandardCharsets.UTF_8));
        fileContent.remove(x);
        Files.write(file.toPath(),fileContent,StandardCharsets.UTF_8);
    }


    public void handleButtonNewFlashCard() throws Exception{

        File word = new File("./resources/" + name + ".txt");
        File translation = new File("./resources/" + name + "_translate.txt");
        boolean trueOrFalse = false;
                if (word.exists()) trueOrFalse = true;

            ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(word.toPath()));

                    if (!fileContent.contains(NewWord.getText())) {

                        PrintWriter writer = new PrintWriter(new FileWriter(word, trueOrFalse));
                        writer.write(NewWord.getText() + "\n");
                        writer.close();

                        writer = new PrintWriter(new FileWriter(translation, trueOrFalse));
                        writer.write(NewTranslation.getText() + "\n");
                        writer.close();
                        observableList.add(NewWord.getText());
                        listView.setItems(observableList);
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("Words on the list cannot be duplicate!");
                        alert.showAndWait();
                    }
    }

    public void handleButtonFinish(ActionEvent event) throws Exception{
        if(whichScene.equals("CreatingNewSet.fxml")) whichScene = "sample.fxml";
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.ChangingScene(event,whichScene);
    }

}
