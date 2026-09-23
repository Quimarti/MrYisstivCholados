package dao.impl;

import dao.ErrorLogDAO;
import modelo.ErrorLog;
import utilidad.Conexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** HU-19: Registro de errores del sistema. */
public class ErrorLogDAOImpl implements ErrorLogDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(ErrorLog e) throws Exception {
        String sql = "INSERT INTO error_log (id_usuario, fecha, tipo_error, mensaje, pantalla) VALUES (?,?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (e.getIdUsuario() > 0) ps.setInt(1, e.getIdUsuario()); else ps.setNull(1, Types.INTEGER);
            ps.setTimestamp(2, Timestamp.valueOf(e.getFecha() != null ? e.getFecha() : LocalDateTime.now()));
            ps.setString(3, e.getTipoError());
            ps.setString(4, e.getMensaje());
            ps.setString(5, e.getPantalla());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<ErrorLog> consultarTodos() throws Exception {
        List<ErrorLog> lista = new ArrayList<>();
        String sql = "SELECT * FROM error_log ORDER BY fecha DESC";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public ErrorLog consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM error_log WHERE id_error = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(ErrorLog e) throws Exception {
        // El registro de errores es de solo escritura/lectura; no se edita.
        return false;
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM error_log WHERE id_error = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private ErrorLog mapear(ResultSet rs) throws SQLException {
        Object idUsuarioObj = rs.getObject("id_usuario");
        return new ErrorLog(
                rs.getInt("id_error"),
                idUsuarioObj != null ? rs.getInt("id_usuario") : 0,
                rs.getTimestamp("fecha").toLocalDateTime(),
                rs.getString("tipo_error"),
                rs.getString("mensaje"),
                rs.getString("pantalla")
        );
    }
}
