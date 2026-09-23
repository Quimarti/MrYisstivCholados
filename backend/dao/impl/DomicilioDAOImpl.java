package dao.impl;

import dao.DomicilioDAO;
import modelo.Domicilio;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDAOImpl implements DomicilioDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Domicilio d) throws Exception {
        String sql = "INSERT INTO domicilio (id_pedido, domiciliario, estado, fecha_entrega, propina) VALUES (?,?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdPedido());
            ps.setString(2, d.getDomiciliario());
            ps.setString(3, d.getEstado());
            ps.setTimestamp(4, d.getFechaEntrega() != null ? Timestamp.valueOf(d.getFechaEntrega()) : null);
            ps.setDouble(5, d.getPropina());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Domicilio> consultarTodos() throws Exception {
        List<Domicilio> lista = new ArrayList<>();
        String sql = "SELECT * FROM domicilio";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    /** HU-18: pedidos asignados a un domiciliario específico. */
    public List<Domicilio> consultarPorDomiciliario(String documentoDomiciliario) throws Exception {
        List<Domicilio> lista = new ArrayList<>();
        String sql = "SELECT * FROM domicilio WHERE domiciliario = ? AND estado <> 'Entregado'";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, documentoDomiciliario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public Domicilio consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM domicilio WHERE id_domicilio = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Domicilio d) throws Exception {
        String sql = "UPDATE domicilio SET domiciliario=?, estado=?, fecha_entrega=?, propina=? WHERE id_domicilio=?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getDomiciliario());
            ps.setString(2, d.getEstado());
            ps.setTimestamp(3, d.getFechaEntrega() != null ? Timestamp.valueOf(d.getFechaEntrega()) : null);
            ps.setDouble(4, d.getPropina());
            ps.setInt(5, d.getIdDomicilio());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM domicilio WHERE id_domicilio = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Domicilio mapear(ResultSet rs) throws SQLException {
        Timestamp fechaEntrega = rs.getTimestamp("fecha_entrega");
        return new Domicilio(
                rs.getInt("id_domicilio"),
                rs.getInt("id_pedido"),
                rs.getString("domiciliario"),
                rs.getString("estado"),
                fechaEntrega != null ? fechaEntrega.toLocalDateTime() : null,
                rs.getDouble("propina")
        );
    }
}
