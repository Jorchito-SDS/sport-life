package main.java.edu.jm.sportlife;
import main.java.edu.jm.sportlife.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showLoginView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}