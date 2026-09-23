package dao.impl;

import dao.ProductoDAO;
import modelo.Producto;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Producto p) throws Exception {
        String sql = "INSERT INTO producto (id_categoria, nombre, descripcion, precio, imagen, es_favorito, stock) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdCategoria());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.isEsFavorito());
            ps.setInt(7, p.getStock());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Producto> consultarTodos() throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public List<Producto> consultarPorCategoria(int idCategoria) throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE id_categoria = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public Producto consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Producto p) throws Exception {
        String sql = "UPDATE producto SET id_categoria=?, nombre=?, descripcion=?, precio=?, imagen=?, es_favorito=?, stock=? WHERE id_producto=?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdCategoria());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.isEsFavorito());
            ps.setInt(7, p.getStock());
            ps.setInt(8, p.getIdProducto());
            return ps.executeUpdate() > 0;
        }
    }

    /** HU-15: actualiza únicamente el stock (usado al confirmar un pedido o desde el panel de inventario). */
    @Override
    public boolean actualizarStock(int idProducto, int nuevoStock) throws Exception {
        String sql = "UPDATE producto SET stock = ? WHERE id_producto = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Math.max(0, nuevoStock));
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id_producto"),
                rs.getInt("id_categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getString("imagen"),
                rs.getBoolean("es_favorito"),
                rs.getInt("stock")
        );
    }
}
