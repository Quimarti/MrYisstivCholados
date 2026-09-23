package dao;

import modelo.Pedido;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Pedido.
 * Toda la lógica de acceso a datos vive en dao.impl.PedidoDAOImpl (JDBC + PreparedStatement).
 */
public interface PedidoDAO {
    boolean insertar(Pedido objeto) throws Exception;
    List<Pedido> consultarTodos() throws Exception;
    Pedido consultarPorId(int id) throws Exception;
    boolean actualizar(Pedido objeto) throws Exception;
    boolean eliminar(int id) throws Exception;

    // HU-06/HU-14/HU-18
    List<Pedido> consultarPorUsuario(int idUsuario) throws Exception;
    boolean cambiarEstado(int idPedido, String nuevoEstado) throws Exception;
}
