package sample;


import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneChanger
{
    public void ChangingScene(Event event, String name) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
        Parent home_page_parent = fxmlLoader.load();
        Stage ChooseSetStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene ChooseSetScene = new Scene(home_page_parent);
        ChooseSetStage.setScene(ChooseSetScene);
    }
}
