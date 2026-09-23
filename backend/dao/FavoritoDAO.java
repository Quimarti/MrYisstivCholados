package dao;

import modelo.Favorito;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Favorito.
 * Toda la lógica de acceso a datos vive en dao.impl.FavoritoDAOImpl (JDBC + PreparedStatement).
 */
public interface FavoritoDAO {
    boolean insertar(Favorito objeto) throws Exception;
    List<Favorito> consultarTodos() throws Exception;
    Favorito consultarPorId(int id) throws Exception;
    boolean actualizar(Favorito objeto) throws Exception;
    boolean eliminar(int id) throws Exception;

    // HU-09: favoritos de un usuario / alternar favorito
    List<Favorito> consultarPorUsuario(int idUsuario) throws Exception;
    boolean esFavorito(int idUsuario, int idProducto) throws Exception;
}
