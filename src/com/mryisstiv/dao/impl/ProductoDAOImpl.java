package com.mryisstiv.dao.impl;

import com.mryisstiv.dao.ProductoDAO;
import com.mryisstiv.modelo.Categoria;
import com.mryisstiv.modelo.Producto;
import com.mryisstiv.utilidad.Conexion;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de ProductoDAO usando JDBC
 * @author Ana Maria Quimbayo
 */
public class ProductoDAOImpl implements ProductoDAO {
    
    private Connection conexion;

    @Override
    public boolean insert(Producto producto) throws SQLException {
        String sql = "INSERT INTO producto (nombre, descripcion, precio, stock, imagen, id_categoria, favorito_semana, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setBigDecimal(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setString(5, producto.getImagen());
            stmt.setInt(6, producto.getCategoria().getId_categoria());
            stmt.setBoolean(7, producto.isFavorito_semana());
            stmt.setBoolean(8, producto.isActivo());
            
            return stmt.executeUpdate() > 0;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
    }

    @Override
    public Producto select(int id) throws SQLException {
        String sql = "SELECT p.*, c.nombre_categoria FROM producto p INNER JOIN categoria c ON p.id_categoria = c.id_categoria WHERE p.id_producto = ?";
        Producto producto = null;
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                producto = mapearResultSetAProducto(rs);
            }
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        return producto;
    }

    @Override
    public List<Producto> selectAll() throws SQLException {
        String sql = "SELECT p.*, c.nombre_categoria FROM producto p INNER JOIN categoria c ON p.id_categoria = c.id_categoria ORDER BY p.id_producto";
        List<Producto> productos = new ArrayList<>();
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(mapearResultSetAProducto(rs));
            }
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        return productos;
    }

    @Override
    public List<Producto> selectByCategoria(int idCategoria) throws SQLException {
        String sql = "SELECT p.*, c.nombre_categoria FROM producto p INNER JOIN categoria c ON p.id_categoria = c.id_categoria WHERE p.id_categoria = ? AND p.activo = true";
        List<Producto> productos = new ArrayList<>();
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idCategoria);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(mapearResultSetAProducto(rs));
            }
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        return productos;
    }

    @Override
    public boolean update(Producto producto) throws SQLException {
        String sql = "UPDATE producto SET nombre=?, descripcion=?, precio=?, stock=?, imagen=?, id_categoria=?, favorito_semana=?, activo=? WHERE id_producto=?";
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setBigDecimal(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setString(5, producto.getImagen());
            stmt.setInt(6, producto.getCategoria().getId_categoria());
            stmt.setBoolean(7, producto.isFavorito_semana());
            stmt.setBoolean(8, producto.isActivo());
            stmt.setInt(9, producto.getId_producto());
            
            return stmt.executeUpdate() > 0;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
    }

    // Método auxiliar para traducir de MySQL a Java
    private Producto mapearResultSetAProducto(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId_categoria(rs.getInt("id_categoria"));
        categoria.setNombre_categoria(rs.getString("nombre_categoria"));

        Producto producto = new Producto();
        producto.setId_producto(rs.getInt("id_producto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setStock(rs.getInt("stock"));
        producto.setImagen(rs.getString("imagen"));
        producto.setCategoria(categoria);
        producto.setFavorito_semana(rs.getBoolean("favorito_semana"));
        producto.setActivo(rs.getBoolean("activo"));
        
        return producto;
    }
}