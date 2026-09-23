package dao;

import modelo.Producto;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Producto.
 * Toda la lógica de acceso a datos vive en dao.impl.ProductoDAOImpl (JDBC + PreparedStatement).
 */
public interface ProductoDAO {
    boolean insertar(Producto objeto) throws Exception;
    List<Producto> consultarTodos() throws Exception;
    Producto consultarPorId(int id) throws Exception;
    boolean actualizar(Producto objeto) throws Exception;
    boolean eliminar(int id) throws Exception;

    // HU-15: inventario
    List<Producto> consultarPorCategoria(int idCategoria) throws Exception;
    boolean actualizarStock(int idProducto, int nuevoStock) throws Exception;
}
