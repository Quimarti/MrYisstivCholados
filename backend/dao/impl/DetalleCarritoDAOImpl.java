package dao.impl;

import dao.DetalleCarritoDAO;
import modelo.DetalleCarrito;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleCarritoDAOImpl implements DetalleCarritoDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(DetalleCarrito d) throws Exception {
        String sql = "INSERT INTO detalle_carrito (id_carrito, id_producto, cantidad, precio_unit) VALUES (?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdCarrito());
            ps.setInt(2, d.getIdProducto());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnit());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<DetalleCarrito> consultarTodos() throws Exception {
        List<DetalleCarrito> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_carrito";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    /** HU-05: líneas del carrito de un usuario, para pintar el resumen. */
    public List<DetalleCarrito> consultarPorCarrito(int idCarrito) throws Exception {
        List<DetalleCarrito> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_carrito WHERE id_carrito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public DetalleCarrito consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM detalle_carrito WHERE id_detalle_carrito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(DetalleCarrito d) throws Exception {
        String sql = "UPDATE detalle_carrito SET cantidad = ? WHERE id_detalle_carrito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getCantidad());
            ps.setInt(2, d.getIdDetalleCarrito());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM detalle_carrito WHERE id_detalle_carrito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private DetalleCarrito mapear(ResultSet rs) throws SQLException {
        return new DetalleCarrito(
                rs.getInt("id_detalle_carrito"),
                rs.getInt("id_carrito"),
                rs.getInt("id_producto"),
                rs.getInt("cantidad"),
                rs.getDouble("precio_unit")
        );
    }
}
