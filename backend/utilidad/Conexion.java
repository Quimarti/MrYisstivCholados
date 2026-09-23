package utilidad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria encargada de abrir la conexión JDBC hacia la base de
 * datos MySQL "mryisstiv_db". Centraliza la URL y las credenciales para
 * que todas las clases *DAOImpl la reutilicen (patrón Repository/DAO).
 *
 * Requiere agregar el driver mysql-connector-java-8.0.33.jar en backend/lib/
 * y añadirlo al classpath del proyecto (IntelliJ: File > Project Structure > Libraries).
 */
public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/mryisstiv_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    /**
     * Abre y devuelve una nueva conexión JDBC. Cada DAOImpl debe cerrar la
     * conexión (o usar try-with-resources) al finalizar su operación.
     */
    public Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL. Verifica mysql-connector-java en backend/lib/", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
