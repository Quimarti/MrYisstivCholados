package dao;

import modelo.MetodoPago;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad MetodoPago.
 * Toda la lógica de acceso a datos vive en dao.impl.MetodoPagoDAOImpl (JDBC + PreparedStatement).
 */
public interface MetodoPagoDAO {
    boolean insertar(MetodoPago objeto) throws Exception;
    List<MetodoPago> consultarTodos() throws Exception;
    MetodoPago consultarPorId(int id) throws Exception;
    boolean actualizar(MetodoPago objeto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
