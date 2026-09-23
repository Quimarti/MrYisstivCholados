package dao;

import modelo.Reserva;
import java.util.List;

/**
 * Interfaz Repository/DAO para la entidad Reserva.
 * Toda la lógica de acceso a datos vive en dao.impl.ReservaDAOImpl (JDBC + PreparedStatement).
 */
public interface ReservaDAO {
    boolean insertar(Reserva objeto) throws Exception;
    List<Reserva> consultarTodos() throws Exception;
    Reserva consultarPorId(int id) throws Exception;
    boolean actualizar(Reserva objeto) throws Exception;
    boolean eliminar(int id) throws Exception;

    // HU-08: "Mis reservas" del cliente
    List<Reserva> consultarPorUsuario(int idUsuario) throws Exception;
}
