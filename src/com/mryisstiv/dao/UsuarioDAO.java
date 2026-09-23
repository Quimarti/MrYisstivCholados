package com.mryisstiv.dao;

import com.mryisstiv.modelo.Usuario;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para gestionar el acceso a datos de la tabla Usuario
 * @author Ana Maria Quimbayo
 * @version 1.0
 */
public interface UsuarioDAO {
    
    /**
     * Insertar un nuevo usuario en la base de datos
     * @param usuario - Objeto Usuario a insertar
     * @return boolean - true si se insertó correctamente
     * @throws SQLException - Si hay error en la base de datos
     */
    boolean insert(Usuario usuario) throws SQLException;
    
    /**
     * Consultar un usuario por su ID
     * @param id - ID del usuario
     * @return Usuario - Objeto Usuario encontrado
     * @throws SQLException - Si hay error en la base de datos
     */
    Usuario select(int id) throws SQLException;
    
    /**
     * Consultar un usuario por correo y contraseña (para login)
     * @param correo - Correo del usuario
     * @param contrasena - Contraseña del usuario
     * @return Usuario - Objeto Usuario encontrado o null
     * @throws SQLException - Si hay error en la base de datos
     */
    Usuario selectByCorreoContrasena(String correo, String contrasena) throws SQLException;
    
    /**
     * Actualizar un usuario existente
     * @param usuario - Objeto Usuario con los datos actualizados
     * @return boolean - true si se actualizó correctamente
     * @throws SQLException - Si hay error en la base de datos
     */
    boolean update(Usuario usuario) throws SQLException;
    
    /**
     * Eliminar un usuario por su ID
     * @param id - ID del usuario a eliminar
     * @return boolean - true si se eliminó correctamente
     * @throws SQLException - Si hay error en la base de datos
     */
    boolean delete(int id) throws SQLException;
    
    /**
     * Consultar todos los usuarios
     * @return List<Usuario> - Lista de todos los usuarios
     * @throws SQLException - Si hay error en la base de datos
     */
    List<Usuario> selectAll() throws SQLException;
}