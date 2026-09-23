package dao;

import modelo.Carrito;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Carrito.
 * Toda la lógica de acceso a datos vive en dao.impl.CarritoDAOImpl (JDBC + PreparedStatement).
 */
public interface CarritoDAO {
    boolean insertar(Carrito objeto) throws Exception;
    List<Carrito> consultarTodos() throws Exception;
    Carrito consultarPorId(int id) throws Exception;
    boolean actualizar(Carrito objeto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
