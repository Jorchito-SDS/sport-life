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
            if (sceneManager != null) {
                sceneManager.showInfoAlert("Campos Vacíos", "Atención", "Complete todos los campos obligatorios.", Alert.AlertType.WARNING);
            }
            return;
        }

        try {
            String codigo = txtCodigo.getText();
            String descripcion = txtDescripcion.getText();
            String deporte = cmbDeporte.getValue();
            boolean techada = chkTechada.isSelected();
            double precio = Double.parseDouble(txtPrecioHora.getText());

            if (instalacionSeleccionada == null) {
                Instalacion nueva = new Instalacion(codigo, descripcion, deporte, techada, precio);
                instalacionRepository.guardar(nueva);
            } else {
                instalacionSeleccionada.setCodigo(codigo);
                instalacionSeleccionada.setDescripcion(descripcion);
                instalacionSeleccionada.setDeporte(deporte);
                instalacionSeleccionada.setTechada(techada);
                instalacionSeleccionada.setPrecioHora(precio);
                instalacionRepository.actualizar(instalacionSeleccionada);
            }

            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarInstalacion() {
        if (instalacionSeleccionada == null) return;
        try {
            instalacionRepository.eliminar(instalacionSeleccionada.getIdInstalacion());
            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
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