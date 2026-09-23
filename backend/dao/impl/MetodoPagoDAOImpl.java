package dao.impl;

import dao.MetodoPagoDAO;
import modelo.MetodoPago;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAOImpl implements MetodoPagoDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(MetodoPago m) throws Exception {
        String sql = "INSERT INTO metodo_pago (id_pedido, tipo_tarjeta) VALUES (?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, m.getIdPedido());
            ps.setString(2, m.getTipoTarjeta());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<MetodoPago> consultarTodos() throws Exception {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM metodo_pago";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public MetodoPago consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM metodo_pago WHERE id_metodo_pago = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(MetodoPago m) throws Exception {
        String sql = "UPDATE metodo_pago SET tipo_tarjeta = ? WHERE id_metodo_pago = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getTipoTarjeta());
            ps.setInt(2, m.getIdMetodoPago());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM metodo_pago WHERE id_metodo_pago = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private MetodoPago mapear(ResultSet rs) throws SQLException {
        return new MetodoPago(rs.getInt("id_metodo_pago"), rs.getInt("id_pedido"), rs.getString("tipo_tarjeta"));
    }
}
