package com.mryisstiv.dao;

import com.mryisstiv.modelo.Producto;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para gestionar el acceso a datos de la tabla Producto
 * @author Ana Maria Quimbayo
 */
public interface ProductoDAO {
    
    boolean insert(Producto producto) throws SQLException;
    
    Producto select(int id) throws SQLException;
    
    List<Producto> selectAll() throws SQLException;
    
    // Método extra para buscar por categoría (muy útil para el catálogo)
    List<Producto> selectByCategoria(int idCategoria) throws SQLException;
    
    boolean update(Producto producto) throws SQLException;
    
    boolean delete(int id) throws SQLException;
}
