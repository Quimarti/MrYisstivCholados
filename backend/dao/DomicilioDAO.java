package dao;

import modelo.Domicilio;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Domicilio.
 * Toda la lógica de acceso a datos vive en dao.impl.DomicilioDAOImpl (JDBC + PreparedStatement).
 */
public interface DomicilioDAO {
    boolean insertar(Domicilio objeto) throws Exception;
    List<Domicilio> consultarTodos() throws Exception;
    Domicilio consultarPorId(int id) throws Exception;
    boolean actualizar(Domicilio objeto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
