package com.mryisstiv.dao.impl;

import com.mryisstiv.dao.UsuarioDAO;
import com.mryisstiv.modelo.Rol;
import com.mryisstiv.modelo.Usuario;
import com.mryisstiv.utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de UsuarioDAO usando JDBC
 * Esta clase contiene los métodos reales que hablan con MySQL
 * @author Ana Maria Quimbayo
 * @version 1.0
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    
    private Connection conexion;
    
    /**
     * Método para INSERTAR un nuevo usuario en la base de datos
     * (Botón rojo del control remoto 📺)
     */
    @Override
    public boolean insert(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, apellidos, cedula, correo, telefono, contrasena, id_rol, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            // 1. Obtener la conexión (encender la tele)
            conexion = Conexion.obtenerConexion();
            
            // 2. Preparar la consulta (como escribir el mensaje antes de enviarlo)
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            // 3. Llenar los espacios (?) con los datos del usuario
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getCedula());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getContrasena());
            stmt.setInt(7, usuario.getRol().getId_rol());
            stmt.setBoolean(8, usuario.isActivo());
            
            // 4. Ejecutar la consulta (enviar la señal infrarroja 📡)
            int filasAfectadas = stmt.executeUpdate();
            
            // 5. Si se afectó al menos 1 fila, significa que se insertó
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar usuario: " + e.getMessage());
            throw e;
        } finally {
            // 6. Cerrar la conexión (apagar la tele)
            Conexion.cerrarConexion(conexion);
        }
    }
    
    /**
     * Método para CONSULTAR un usuario por su ID
     * (Botón verde del control remoto 📺)
     */
    @Override
    public Usuario select(int id) throws SQLException {
        String sql = "SELECT u.*, r.nombre_rol FROM usuario u INNER JOIN rol r ON u.id_rol = r.id_rol WHERE u.id_usuario = ?";
        Usuario usuario = null;
        
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            // Si encontró un usuario, lo convertimos en objeto Java
            if (rs.next()) {
                usuario = mapearResultSetAUsuario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al consultar usuario: " + e.getMessage());
            throw e;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        
        return usuario;
    }
    
    /**
     * Método para CONSULTAR usuario por correo y contraseña (LOGIN)
     * (Botón verde especial 🔑)
     */
    @Override
    public Usuario selectByCorreoContrasena(String correo, String contrasena) throws SQLException {
        String sql = "SELECT u.*, r.nombre_rol FROM usuario u INNER JOIN rol r ON u.id_rol = r.id_rol WHERE u.correo = ? AND u.contrasena = ? AND u.activo = true";
        Usuario usuario = null;
        
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = mapearResultSetAUsuario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error en login: " + e.getMessage());
            throw e;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        
        return usuario;
    }
    
    /**
     * Método para ACTUALIZAR un usuario existente
     * (Botón amarillo del control remoto )
     */
    @Override
    public boolean update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, apellidos=?, cedula=?, correo=?, telefono=?, contrasena=?, id_rol=?, activo=? WHERE id_usuario=?";
        
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getCedula());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getContrasena());
            stmt.setInt(7, usuario.getRol().getId_rol());
            stmt.setBoolean(8, usuario.isActivo());
            stmt.setInt(9, usuario.getId_usuario());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar usuario: " + e.getMessage());
            throw e;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
    }
    
    /**
     * Método para ELIMINAR un usuario por su ID
     * (Botón azul del control remoto 📺)
     */
    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar usuario: " + e.getMessage());
            throw e;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
    }
    
    /**
     * Método para CONSULTAR TODOS los usuarios
     * (Botón verde de "ver todos los canales" 📺)
     */
    @Override
    public List<Usuario> selectAll() throws SQLException {
        String sql = "SELECT u.*, r.nombre_rol FROM usuario u INNER JOIN rol r ON u.id_rol = r.id_rol ORDER BY u.id_usuario";
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            conexion = Conexion.obtenerConexion();
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            // Recorrer todos los resultados y convertirlos en objetos Usuario
            while (rs.next()) {
                Usuario usuario = mapearResultSetAUsuario(rs);
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al consultar todos los usuarios: " + e.getMessage());
            throw e;
        } finally {
            Conexion.cerrarConexion(conexion);
        }
        
        return usuarios;
    }
    
    /**
     * Método auxiliar para convertir un ResultSet (datos de MySQL) 
     * en un objeto Usuario de Java
     * (Es como traducir de "idioma MySQL" a "idioma Java")
     */
    private Usuario mapearResultSetAUsuario(ResultSet rs) throws SQLException {
        // Primero creamos el Rol
        Rol rol = new Rol();
        rol.setId_rol(rs.getInt("id_rol"));
        rol.setNombre_rol(rs.getString("nombre_rol"));
        
        // Luego creamos el Usuario
        Usuario usuario = new Usuario();
        usuario.setId_usuario(rs.getInt("id_usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setCedula(rs.getString("cedula"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setTelefono(rs.getString("telefono"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setRol(rol);
        usuario.setActivo(rs.getBoolean("activo"));
        usuario.setFecha_registro(rs.getTimestamp("fecha_registro"));
        
        return usuario;
    }
}