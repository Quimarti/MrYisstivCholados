package dao;

import modelo.DetalleCarrito;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad DetalleCarrito.
 * Toda la lógica de acceso a datos vive en dao.impl.DetalleCarritoDAOImpl (JDBC + PreparedStatement).
 */
public interface DetalleCarritoDAO {
    boolean insertar(DetalleCarrito objeto) throws Exception;
    List<DetalleCarrito> consultarTodos() throws Exception;
    DetalleCarrito consultarPorId(int id) throws Exception;
    boolean actualizar(DetalleCarrito objeto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
