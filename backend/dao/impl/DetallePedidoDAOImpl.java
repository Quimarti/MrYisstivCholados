package dao.impl;

import dao.DetallePedidoDAO;
import modelo.DetallePedido;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAOImpl implements DetallePedidoDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(DetallePedido d) throws Exception {
        String sql = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unit) VALUES (?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdPedido());
            ps.setInt(2, d.getIdProducto());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnit());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<DetallePedido> consultarTodos() throws Exception {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_pedido";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    /** Líneas de un pedido específico (usado para generar factura / historial). */
    public List<DetallePedido> consultarPorPedido(int idPedido) throws Exception {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_pedido WHERE id_pedido = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public DetallePedido consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM detalle_pedido WHERE id_detalle_pedido = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(DetallePedido d) throws Exception {
        String sql = "UPDATE detalle_pedido SET cantidad=?, precio_unit=? WHERE id_detalle_pedido=?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getCantidad());
            ps.setDouble(2, d.getPrecioUnit());
            ps.setInt(3, d.getIdDetallePedido());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM detalle_pedido WHERE id_detalle_pedido = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private DetallePedido mapear(ResultSet rs) throws SQLException {
        return new DetallePedido(
                rs.getInt("id_detalle_pedido"),
                rs.getInt("id_pedido"),
                rs.getInt("id_producto"),
                rs.getInt("cantidad"),
                rs.getDouble("precio_unit")
        );
    }
}
