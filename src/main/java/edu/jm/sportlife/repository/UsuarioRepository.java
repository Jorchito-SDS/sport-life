package main.java.edu.jm.sportlife.repository;

import main.java.edu.jm.sportlife.config.DataBaseConnection;
import main.java.edu.jm.sportlife.model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

    /**
     * Verifica si ya existe un usuario con ese correo (para evitar duplicados).
     */
    public boolean existeEmail(String email) throws Exception {
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";
        try (PreparedStatement pstmt = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar correo: " + e.getMessage());
        }
    }

    /**
     * Inserta un nuevo usuario. Lanza excepción si el correo ya existe (constraint UNIQUE).
     */
    public void registrar(Usuario u) throws Exception {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstmt.setString(1, u.getNombre());
            pstmt.setString(2, u.getApellido());
            pstmt.setString(3, u.getEmail());
            pstmt.setString(4, u.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                throw new RuntimeException("Ya existe un usuario registrado con ese correo.");
            }
            throw new RuntimeException("Error al registrar usuario: " + e.getMessage());
        }
    }

    /**
     * Busca un usuario por correo y contraseña. Devuelve null si no coincide.
     */
    public Usuario login(String email, String password) throws Exception {
        String sql = "SELECT id_usuario, nombre, apellido, email, password FROM usuarios WHERE email = ? AND password = ?";
        try (PreparedStatement pstmt = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al iniciar sesión: " + e.getMessage());
        }
    }
}
