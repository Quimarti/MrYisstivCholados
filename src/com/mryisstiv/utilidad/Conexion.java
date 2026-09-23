package com.mryisstiv.utilidad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL
 * @author Ana Maria Quimbayo
 * @version 1.0
 */
public class Conexion {
    
    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/mryisstiv_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Método para obtener la conexión a la base de datos
     * @return Connection - Objeto de conexión
     * @throws SQLException - Si hay error en la conexión
     */
    public static Connection obtenerConexion() throws SQLException {
        Connection conexion = null;
        
        try {
            // Cargar el driver JDBC
            Class.forName(DRIVER);
            
            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            
            System.out.println("✅ Conexión exitosa a la base de datos mryisstiv_db");
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: No se encontró el driver JDBC");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return conexion;
    }
    
    /**
     * Método para cerrar la conexión
     * @param conexion - La conexión a cerrar
     */
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("🔒 Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Método main para probar la conexión
     */
    public static void main(String[] args) {
        Connection conn = null;
        
        try {
            System.out.println("🔄 Intentando conectar a la base de datos...");
            conn = Conexion.obtenerConexion();
            
            if (conn != null) {
                System.out.println("🎉 ¡La conexión funcionó correctamente!");
                System.out.println("📊 Base de datos: mryisstiv_db");
                System.out.println("🔗 URL: " + conn.getMetaData().getURL());
            } else {
                System.out.println("❌ No se pudo establecer la conexión");
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error de SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexion(conn);
        }
    }
}