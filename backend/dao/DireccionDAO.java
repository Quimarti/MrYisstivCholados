package dao;

import modelo.Direccion;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Direccion.
 * Toda la lógica de acceso a datos vive en dao.impl.DireccionDAOImpl (JDBC + PreparedStatement).
 */
public interface DireccionDAO {
    boolean insertar(Direccion objeto) throws Exception;
    List<Direccion> consultarTodos() throws Exception;
    Direccion consultarPorId(int id) throws Exception;
    boolean actualizar(Direccion objeto) throws Exception;
    boolean eliminar(int id) throws Exception;

    // HU-10
    List<Direccion> consultarPorUsuario(int idUsuario) throws Exception;
    boolean marcarPrincipal(int idDireccion, int idUsuario) throws Exception;
}
