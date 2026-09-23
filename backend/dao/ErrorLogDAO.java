package dao;

import modelo.ErrorLog;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad ErrorLog.
 * Toda la lógica de acceso a datos vive en dao.impl.ErrorLogDAOImpl (JDBC + PreparedStatement).
 */
public interface ErrorLogDAO {
    boolean insertar(ErrorLog objeto) throws Exception;
    List<ErrorLog> consultarTodos() throws Exception;
    ErrorLog consultarPorId(int id) throws Exception;
    boolean actualizar(ErrorLog objeto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
