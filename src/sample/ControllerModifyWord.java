package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ControllerModifyWord {

    @FXML private Button saveButton;
    @FXML private TextField textWord, textTranslation;
    private static String wordToChange, translationToChange;

    public static void setNameOfLanguage(String nameOfLanguage) {
        ControllerModifyWord.nameOfLanguage = nameOfLanguage;
    }

    private static String nameOfLanguage;


    public static void setWordAndTranslation(String wordToChange, String translationToChange){
        ControllerModifyWord.wordToChange = wordToChange;
        ControllerModifyWord.translationToChange = translationToChange;
    }

    @FXML public void initialize(){
        textWord.setText(wordToChange);
        textTranslation.setText(translationToChange);

    }

    public void handleCancelButton(){
        Stage stage = (Stage) textWord.getScene().getWindow();
        stage.close();
    }

    public void handleSaveButton(){
        if (!textWord.equals(wordToChange)) ControllerModifySet.setChangedWord(textWord.getText());
        if (!textTranslation.equals(translationToChange)) ControllerModifySet.setChangedTranslation(textTranslation.getText());
       Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

}
