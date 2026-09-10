package main.java.edu.jm.sportlife.util;

import main.java.edu.jm.sportlife.controller.LoginController;
import main.java.edu.jm.sportlife.controller.RegisterController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/LoginView.fxml"));
        Parent root = loader.load();
        
        LoginController controller = loader.getController();
        if (controller != null) {
            controller.setSceneManager(this);
        }

        stage.setScene(new Scene(root));
        stage.setTitle("SportLife - Iniciar Sesión");
        stage.show();
    }

    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/RegisterView.fxml"));
        Parent root = loader.load();

        // Vincula SceneManager al RegisterController
        RegisterController controller = loader.getController();
        if (controller != null) {
            controller.setSceneManager(this);
        }

        stage.setScene(new Scene(root));
        stage.setTitle("SportLife - Registro");
        stage.show();
    }

    public void showDashBoardView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/InstalacionView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("SportLife - Panel de Instalaciones");
        stage.show();
    }

    public void showInfoAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}