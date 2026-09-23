package main.java.edu.jm.sportlife.controller;

import main.java.edu.jm.sportlife.model.Instalacion;
import main.java.edu.jm.sportlife.repository.InstalacionRepository;
import main.java.edu.jm.sportlife.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;

public class InstalacionController implements Initializable {

    private InstalacionRepository instalacionRepository;
    private SceneManager sceneManager;

    @FXML private TextField txtCodigo, txtDescripcion, txtPrecioHora;
    @FXML private ComboBox<String> cmbDeporte;
    @FXML private CheckBox chkTechada;

    @FXML private TableView<Instalacion> tvInstalacion;
    @FXML private TableColumn<Instalacion, Integer> tvColumnIdInstalacion;
    @FXML private TableColumn<Instalacion, String> tvColumnCodigo;
    @FXML private TableColumn<Instalacion, String> tvColumnDescripcion;
    @FXML private TableColumn<Instalacion, String> tvColumnDeporte;
    @FXML private TableColumn<Instalacion, String> tvColumnTechada;
    @FXML private TableColumn<Instalacion, Double> tvColumnPrecioHora;

    private Instalacion instalacionSeleccionada = null;

    public InstalacionController() {
    }

    public InstalacionController(InstalacionRepository instalacionRepository, SceneManager sceneManager) {
        this.instalacionRepository = instalacionRepository;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (instalacionRepository == null) {
            instalacionRepository = new InstalacionRepository();
        }
        
        if (cmbDeporte != null) {
            cmbDeporte.setItems(FXCollections.observableArrayList("Fútbol", "Tenis", "Básquetbol", "Vólibol", "Pádel", "Natación"));
        }

        configurarTabla();
        cargarDatos();

        if (tvInstalacion != null) {
            tvInstalacion.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    instalacionSeleccionada = newSel;
                    txtCodigo.setText(newSel.getCodigo());
                    txtDescripcion.setText(newSel.getDescripcion());
                    cmbDeporte.setValue(newSel.getDeporte());
                    chkTechada.setSelected(newSel.isTechada());
                    txtPrecioHora.setText(String.valueOf(newSel.getPrecioHora()));
                }
            });
        }
    }

    private void configurarTabla() {
        if (tvColumnIdInstalacion != null) tvColumnIdInstalacion.setCellValueFactory(new PropertyValueFactory<>("idInstalacion"));
        if (tvColumnCodigo != null) tvColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        if (tvColumnDescripcion != null) tvColumnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        if (tvColumnDeporte != null) tvColumnDeporte.setCellValueFactory(new PropertyValueFactory<>("deporte"));
        if (tvColumnTechada != null) tvColumnTechada.setCellValueFactory(new PropertyValueFactory<>("techadaTexto"));
        if (tvColumnPrecioHora != null) tvColumnPrecioHora.setCellValueFactory(new PropertyValueFactory<>("precioHora"));
    }

    private void cargarDatos() {
        try {
            if (tvInstalacion != null) {
                tvInstalacion.setItems(instalacionRepository.findAll());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void guardarInstalacion() {
        if (txtCodigo == null || txtCodigo.getText().isEmpty() || txtDescripcion.getText().isEmpty() || 
            txtPrecioHora.getText().isEmpty() || cmbDeporte.getValue() == null) {
            alertar("Campos vacíos", "Completa todos los campos obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        String codigo = txtCodigo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String deporte = cmbDeporte.getValue();
        boolean techada = chkTechada.isSelected();
        double precio;

        try {
            precio = Double.parseDouble(txtPrecioHora.getText().trim());
        } catch (NumberFormatException e) {
            alertar("Dato inválido", "El precio por hora debe ser un número (ej: 50.00).", Alert.AlertType.WARNING);
            return;
        }

        if (precio <= 0) {
            alertar("Dato inválido", "El precio por hora debe ser mayor a cero.", Alert.AlertType.WARNING);
            return;
        }

        try {
            if (instalacionSeleccionada == null) {
                Instalacion nueva = new Instalacion(codigo, descripcion, deporte, techada, precio);
                instalacionRepository.guardar(nueva);
                alertar("Instalación guardada", "La instalación \"" + codigo + "\" se registró correctamente.", Alert.AlertType.INFORMATION);
            } else {
                instalacionSeleccionada.setCodigo(codigo);
                instalacionSeleccionada.setDescripcion(descripcion);
                instalacionSeleccionada.setDeporte(deporte);
                instalacionSeleccionada.setTechada(techada);
                instalacionSeleccionada.setPrecioHora(precio);
                instalacionRepository.actualizar(instalacionSeleccionada);
                alertar("Instalación actualizada", "Los cambios se guardaron correctamente.", Alert.AlertType.INFORMATION);
            }

            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")
                    ? "Ya existe una instalación registrada con el código \"" + codigo + "\"."
                    : "No se pudo guardar la instalación: " + e.getMessage();
            alertar("Error al guardar", msg, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void eliminarInstalacion() {
        if (instalacionSeleccionada == null) {
            alertar("Sin selección", "Selecciona una instalación de la tabla para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas eliminar la instalación \"" + instalacionSeleccionada.getCodigo() + "\"?");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                try {
                    instalacionRepository.eliminar(instalacionSeleccionada.getIdInstalacion());
                    alertar("Instalación eliminada", "El registro se eliminó correctamente.", Alert.AlertType.INFORMATION);
                    limpiarFormulario();
                    cargarDatos();
                } catch (Exception e) {
                    e.printStackTrace();
                    alertar("Error al eliminar", "No se pudo eliminar la instalación: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void alertar(String titulo, String contenido, Alert.AlertType tipo) {
        if (sceneManager != null) {
            sceneManager.showInfoAlert(titulo, null, contenido, tipo);
        } else {
            Alert alert = new Alert(tipo);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(contenido);
            alert.showAndWait();
        }
    }

    @FXML
    private void limpiarFormulario() {
        if (txtCodigo != null) txtCodigo.clear();
        if (txtDescripcion != null) txtDescripcion.clear();
        if (txtPrecioHora != null) txtPrecioHora.clear();
        if (chkTechada != null) chkTechada.setSelected(false);
        if (cmbDeporte != null) cmbDeporte.getSelectionModel().clearSelection();
        if (tvInstalacion != null) tvInstalacion.getSelectionModel().clearSelection();
        instalacionSeleccionada = null;
    }
}