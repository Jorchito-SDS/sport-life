package main.java.edu.jm.sportlife.repository;

import main.java.edu.jm.sportlife.config.DataBaseConnection;
import main.java.edu.jm.sportlife.model.Instalacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstalacionRepository {

    public ObservableList<Instalacion> findAll() throws Exception {
        String sql = "SELECT id_instalacion, codigo, descripcion, deporte, techada, precio_hora FROM instalaciones;";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            ObservableList<Instalacion> list = FXCollections.observableArrayList();

            while (rs.next()) {
                list.add(new Instalacion(
                        rs.getInt("id_instalacion"),
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getString("deporte"),
                        rs.getBoolean("techada"),
                        rs.getDouble("precio_hora")
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta: " + e.getMessage());
        }
    }

    public void guardar(Instalacion i) throws Exception {
        String sql = "INSERT INTO instalaciones (codigo, descripcion, deporte, techada, precio_hora) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstmt.setString(1, i.getCodigo());
            pstmt.setString(2, i.getDescripcion());
            pstmt.setString(3, i.getDeporte());
            pstmt.setBoolean(4, i.isTechada());
            pstmt.setDouble(5, i.getPrecioHora());
            pstmt.executeUpdate();
        }
    }

    public void actualizar(Instalacion i) throws Exception {
        String sql = "UPDATE instalaciones SET codigo=?, descripcion=?, deporte=?, techada=?, precio_hora=? WHERE id_instalacion=?";
        try (PreparedStatement pstmt = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstmt.setString(1, i.getCodigo());
            pstmt.setString(2, i.getDescripcion());
            pstmt.setString(3, i.getDeporte());
            pstmt.setBoolean(4, i.isTechada());
            pstmt.setDouble(5, i.getPrecioHora());
            pstmt.setInt(6, i.getIdInstalacion());
            pstmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM instalaciones WHERE id_instalacion=?";
        try (PreparedStatement pstmt = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}