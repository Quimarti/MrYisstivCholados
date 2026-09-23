package dao.impl;

import dao.CarritoDAO;
import modelo.Carrito;
import utilidad.Conexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOImpl implements CarritoDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Carrito c) throws Exception {
        String sql = "INSERT INTO carrito (id_usuario, fecha, estado) VALUES (?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getIdUsuario());
            ps.setTimestamp(2, Timestamp.valueOf(c.getFecha() != null ? c.getFecha() : LocalDateTime.now()));
            ps.setString(3, c.getEstado());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Carrito> consultarTodos() throws Exception {
        List<Carrito> lista = new ArrayList<>();
        String sql = "SELECT * FROM carrito";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    /** Carrito activo del usuario (uno solo por convención de negocio). */
    public Carrito consultarActivoPorUsuario(int idUsuario) throws Exception {
        String sql = "SELECT * FROM carrito WHERE id_usuario = ? AND estado = 'Activo' ORDER BY fecha DESC LIMIT 1";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public Carrito consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM carrito WHERE id_carrito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Carrito c) throws Exception {
        String sql = "UPDATE carrito SET estado = ? WHERE id_carrito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getEstado());
            ps.setInt(2, c.getIdCarrito());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM carrito WHERE id_carrito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Carrito mapear(ResultSet rs) throws SQLException {
        return new Carrito(
                rs.getInt("id_carrito"),
                rs.getInt("id_usuario"),
                rs.getTimestamp("fecha").toLocalDateTime(),
                rs.getString("estado")
        );
    }
}
