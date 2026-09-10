package main.java.edu.jm.sportlife.controller;

import main.java.edu.jm.sportlife.config.DataBaseConnection;
import main.java.edu.jm.sportlife.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class LoginController {

    @FXML private TextField txtFieldEmail;
    @FXML private PasswordField txtFieldPass;

    private SceneManager sceneManager;

    // Constructor vacío exigido por JavaFX
    public LoginController() {
    }

    // Permite inyectar SceneManager desde fuera si es necesario
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void handleLogin() {
        String email = txtFieldEmail != null ? txtFieldEmail.getText() : "";
        String pass = txtFieldPass != null ? txtFieldPass.getText() : "";

        if (email.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor ingresa correo y contraseña.", Alert.AlertType.WARNING);
            return;
        }

        // Simulación o navegación al Dashboard
        try {
            mostrarAlerta("Éxito", "Bienvenido al sistema.", Alert.AlertType.INFORMATION);
            if (sceneManager != null) {
                sceneManager.showDashBoardView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la pantalla principal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleGoToRegister() {
        try {
            if (sceneManager != null) {
                sceneManager.showRegisterView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de registro.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleTestDataBaseConnection() {
        try {
            Connection conn = DataBaseConnection.getConnectionDataBase();
            if (conn != null && !conn.isClosed()) {
                mostrarAlerta("Conexión Exitosa", "Conexión a la base de datos establecida correctamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error de Conexión", "No se pudo conectar a la base de datos.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de Conexión", "Detalle: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}