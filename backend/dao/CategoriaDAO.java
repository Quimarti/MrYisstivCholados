package dao;

import modelo.Categoria;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Categoria.
 * Toda la lógica de acceso a datos vive en dao.impl.CategoriaDAOImpl (JDBC + PreparedStatement).
 */
public interface CategoriaDAO {
    boolean insertar(Categoria objeto) throws Exception;
    List<Categoria> consultarTodos() throws Exception;
    Categoria consultarPorId(int id) throws Exception;
    boolean actualizar(Categoria objeto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
