package main.java.edu.jm.sportlife.controller;

import main.java.edu.jm.sportlife.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML private TextField txtFieldNombre;
    @FXML private TextField txtFieldApellido;
    @FXML private TextField txtFieldEmail;
    @FXML private PasswordField txtFieldPass;
    @FXML private PasswordField txtFieldConfirmPass;

    private SceneManager sceneManager;

    public RegisterController() {
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void handleRegister() {
        String nombre = txtFieldNombre != null ? txtFieldNombre.getText() : "";
        String apellido = txtFieldApellido != null ? txtFieldApellido.getText() : "";
        String email = txtFieldEmail != null ? txtFieldEmail.getText() : "";
        String pass = txtFieldPass != null ? txtFieldPass.getText() : "";
        String confirmPass = txtFieldConfirmPass != null ? txtFieldConfirmPass.getText() : "";

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor complete todos los campos.", Alert.AlertType.WARNING);
            return;
        }

        if (!pass.equals(confirmPass)) {
            mostrarAlerta("Contraseña no coincide", "Las contraseñas ingresadas no son iguales.", Alert.AlertType.ERROR);
            return;
        }

        try {
            mostrarAlerta("Éxito", "Usuario registrado correctamente.", Alert.AlertType.INFORMATION);
            if (sceneManager != null) {
                sceneManager.showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver al Login: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleGoToLogin() {
        try {
            if (sceneManager != null) {
                sceneManager.showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de Login.", Alert.AlertType.ERROR);
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