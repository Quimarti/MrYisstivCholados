package dao;

import modelo.DetallePedido;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad DetallePedido.
 * Toda la lógica de acceso a datos vive en dao.impl.DetallePedidoDAOImpl (JDBC + PreparedStatement).
 */
public interface DetallePedidoDAO {
    boolean insertar(DetallePedido objeto) throws Exception;
    List<DetallePedido> consultarTodos() throws Exception;
    DetallePedido consultarPorId(int id) throws Exception;
    boolean actualizar(DetallePedido objeto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
