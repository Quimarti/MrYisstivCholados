package dao.impl;

import dao.PedidoDAO;
import modelo.Pedido;
import utilidad.Conexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Pedido p) throws Exception {
        String sql = "INSERT INTO pedido (id_usuario, id_direccion, fecha, subtotal, total, estado, observaciones) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdUsuario());
            ps.setInt(2, p.getIdDireccion());
            ps.setTimestamp(3, Timestamp.valueOf(p.getFecha() != null ? p.getFecha() : LocalDateTime.now()));
            ps.setDouble(4, p.getSubtotal());
            ps.setDouble(5, p.getTotal());
            ps.setString(6, p.getEstado());
            ps.setString(7, p.getObservaciones());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Pedido> consultarTodos() throws Exception {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido ORDER BY fecha DESC";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public List<Pedido> consultarPorUsuario(int idUsuario) throws Exception {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE id_usuario = ? ORDER BY fecha DESC";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public Pedido consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Pedido p) throws Exception {
        String sql = "UPDATE pedido SET id_direccion=?, subtotal=?, total=?, estado=?, observaciones=? WHERE id_pedido=?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdDireccion());
            ps.setDouble(2, p.getSubtotal());
            ps.setDouble(3, p.getTotal());
            ps.setString(4, p.getEstado());
            ps.setString(5, p.getObservaciones());
            ps.setInt(6, p.getIdPedido());
            return ps.executeUpdate() > 0;
        }
    }

    /** HU-14/HU-17: cambia solo el estado (usado por admin/trabajador y por el domiciliario). */
    @Override
    public boolean cambiarEstado(int idPedido, String nuevoEstado) throws Exception {
        String sql = "UPDATE pedido SET estado = ? WHERE id_pedido = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Pedido mapear(ResultSet rs) throws SQLException {
        return new Pedido(
                rs.getInt("id_pedido"),
                rs.getInt("id_usuario"),
                rs.getInt("id_direccion"),
                rs.getTimestamp("fecha").toLocalDateTime(),
                rs.getDouble("subtotal"),
                rs.getDouble("total"),
                rs.getString("estado"),
                rs.getString("observaciones")
        );
    }
}
