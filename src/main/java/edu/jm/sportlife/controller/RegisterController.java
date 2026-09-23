package main.java.edu.jm.sportlife.controller;

import main.java.edu.jm.sportlife.model.Usuario;
import main.java.edu.jm.sportlife.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public RegisterController() {
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void handleRegister() {
        String nombre = txtFieldNombre.getText().trim();
        String apellido = txtFieldApellido.getText().trim();
        String email = txtFieldEmail.getText().trim();
        String pass = txtFieldPass.getText();
        String confirmPass = txtFieldConfirmPass.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        if (!email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            mostrarAlerta("Correo inválido", "Ingresa un correo electrónico válido.", Alert.AlertType.WARNING);
            return;
        }

        if (!pass.equals(confirmPass)) {
            mostrarAlerta("Contraseñas distintas", "La contraseña y su confirmación no coinciden.", Alert.AlertType.WARNING);
            return;
        }

        if (pass.length() < 6) {
            mostrarAlerta("Contraseña débil", "La contraseña debe tener al menos 6 caracteres.", Alert.AlertType.WARNING);
            return;
        }

        try {
            if (usuarioRepository.existeEmail(email)) {
                mostrarAlerta("Correo en uso", "Ya existe una cuenta registrada con ese correo.", Alert.AlertType.ERROR);
                return;
            }

            Usuario nuevo = new Usuario(nombre, apellido, email, pass);
            usuarioRepository.registrar(nuevo);

            mostrarAlerta("Registro exitoso", "Tu cuenta fue creada correctamente. Ahora puedes iniciar sesión.", Alert.AlertType.INFORMATION);
            limpiarFormulario();

            if (sceneManager != null) {
                sceneManager.showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo completar el registro: " + e.getMessage(), Alert.AlertType.ERROR);
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
            mostrarAlerta("Error", "No se pudo volver al login.", Alert.AlertType.ERROR);
        }
    }

    private void limpiarFormulario() {
        txtFieldNombre.clear();
        txtFieldApellido.clear();
        txtFieldEmail.clear();
        txtFieldPass.clear();
        txtFieldConfirmPass.clear();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
