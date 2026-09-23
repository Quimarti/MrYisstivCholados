package dao;

import modelo.Rol;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Rol.
 * Toda la lógica de acceso a datos vive en dao.impl.RolDAOImpl (JDBC + PreparedStatement).
 */
public interface RolDAO {
    boolean insertar(Rol objeto) throws Exception;
    List<Rol> consultarTodos() throws Exception;
    Rol consultarPorId(int id) throws Exception;
    boolean actualizar(Rol objeto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
