package com.mryisstiv.dao.impl;

import com.mryisstiv.dao.PedidoDAO;
import com.mryisstiv.modelo.Pedido;
import com.mryisstiv.modelo.Usuario;
import com.mryisstiv.utilidad.Conexion;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    
    private Connection conexion;

    @Override
    public boolean insert(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (id_usuario, id_direccion, id_metodo_pago, subtotal, valor_domicilio, total, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, pedido.getUsuario().getId_usuario());
            stmt.setInt(2, pedido.getId_direccion());
            stmt.setInt(3, pedido.getId_metodo_pago());
            stmt.setBigDecimal(4, pedido.getSubtotal());
            stmt.setBigDecimal(5, pedido.getValor_domicilio());
            stmt.setBigDecimal(6, pedido.getTotal());
            stmt.setString(7, pedido.getEstado());
            stmt.setString(8, pedido.getObservaciones());
            
            int filasAfectadas = stmt.executeUpdate();
            
            // Obtener el ID generado del nuevo pedido
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    pedido.setId_pedido(rs.getInt(1));
                }
            }
            
            return filasAfectadas > 0;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
    }

    @Override
    public Pedido select(int id) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        Pedido pedido = null;
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pedido = mapearResultSetAPedido(rs);
            }
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        return pedido;
    }

    @Override
    public List<Pedido> selectAll() throws SQLException {
        String sql = "SELECT * FROM pedido ORDER BY fecha_pedido DESC";
        List<Pedido> pedidos = new ArrayList<>();
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pedidos.add(mapearResultSetAPedido(rs));
            }
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        return pedidos;
    }

    @Override
    public List<Pedido> selectByUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE id_usuario = ? ORDER BY fecha_pedido DESC";
        List<Pedido> pedidos = new ArrayList<>();
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pedidos.add(mapearResultSetAPedido(rs));
            }
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        return pedidos;
    }

    @Override
    public boolean update(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedido SET id_direccion=?, id_metodo_pago=?, subtotal=?, valor_domicilio=?, total=?, estado=?, observaciones=? WHERE id_pedido=?";
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            stmt.setInt(1, pedido.getId_direccion());
            stmt.setInt(2, pedido.getId_metodo_pago());
            stmt.setBigDecimal(3, pedido.getSubtotal());
            stmt.setBigDecimal(4, pedido.getValor_domicilio());
            stmt.setBigDecimal(5, pedido.getTotal());
            stmt.setString(6, pedido.getEstado());
            stmt.setString(7, pedido.getObservaciones());
            stmt.setInt(8, pedido.getId_pedido());
            
            return stmt.executeUpdate() > 0;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
    }

    // Método auxiliar para convertir ResultSet a objeto Pedido
    private Pedido mapearResultSetAPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId_pedido(rs.getInt("id_pedido"));
        
        Usuario usuario = new Usuario();
        usuario.setId_usuario(rs.getInt("id_usuario"));
        pedido.setUsuario(usuario);
        
        pedido.setId_direccion(rs.getInt("id_direccion"));
        pedido.setId_metodo_pago(rs.getInt("id_metodo_pago"));
        pedido.setFecha_pedido(rs.getTimestamp("fecha_pedido"));
        pedido.setSubtotal(rs.getBigDecimal("subtotal"));
        pedido.setValor_domicilio(rs.getBigDecimal("valor_domicilio"));
        pedido.setTotal(rs.getBigDecimal("total"));
        pedido.setEstado(rs.getString("estado"));
        pedido.setObservaciones(rs.getString("observaciones"));
        
        return pedido;
    }
}