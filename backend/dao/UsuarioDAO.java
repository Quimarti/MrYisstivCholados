package dao;

import modelo.Usuario;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Usuario.
 * Toda la lógica de acceso a datos vive en dao.impl.UsuarioDAOImpl (JDBC + PreparedStatement).
 */
public interface UsuarioDAO {
    boolean insertar(Usuario objeto) throws Exception;
    List<Usuario> consultarTodos() throws Exception;
    Usuario consultarPorId(int id) throws Exception;
    boolean actualizar(Usuario objeto) throws Exception;
    boolean eliminar(int id) throws Exception;

    // HU-01/HU-02: validar unicidad de cédula/correo y login flexible
    Usuario consultarPorCedula(String cedula) throws Exception;
    Usuario consultarPorCorreo(String correo) throws Exception;

    // HU-13: búsqueda para el panel de administración de usuarios
    List<Usuario> buscar(String texto) throws Exception;
    boolean cambiarRol(int idUsuario, int idRol) throws Exception;
    boolean alternarActivo(int idUsuario) throws Exception;
}
