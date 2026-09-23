package com.mryisstiv.dao.impl;

import com.mryisstiv.modelo.Rol;
import com.mryisstiv.modelo.Usuario;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Clase para probar que el UsuarioDAO funciona correctamente
 * @author Ana Maria Quimbayo
 */
public class TestUsuarioDAO {
    
    public static void main(String[] args) {
        // Creamos el objeto DAO
        UsuarioDAOImpl dao = new UsuarioDAOImpl();
        
        System.out.println("=== PRUEBA 1: INSERTAR USUARIO ===");
        try {
            // Crear un rol (Cliente = id_rol 1)
            Rol rol = new Rol();
            rol.setId_rol(1);
            rol.setNombre_rol("Cliente");
            
            // Crear un usuario de prueba
            Usuario usuario = new Usuario();
            usuario.setNombre("Ana");
            usuario.setApellidos("Quimbayo");
            usuario.setCedula("1234567890");
            usuario.setCorreo("ana@yisstiv.com");
            usuario.setTelefono("3001234567");
            usuario.setContrasena("Ana12345");
            usuario.setRol(rol);
            usuario.setActivo(true);
            usuario.setFecha_registro(new Timestamp(System.currentTimeMillis()));
            
            // Intentar insertar
            boolean resultado = dao.insert(usuario);
            
            if (resultado) {
                System.out.println("✅ Usuario insertado correctamente!");
            } else {
                System.out.println("❌ No se pudo insertar el usuario");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        System.out.println("\n=== PRUEBA 2: CONSULTAR TODOS LOS USUARIOS ===");
        try {
            List<Usuario> usuarios = dao.selectAll();
            System.out.println("Total de usuarios encontrados: " + usuarios.size());
            
            for (Usuario u : usuarios) {
                System.out.println("- " + u.getNombre() + " " + u.getApellidos() 
                                 + " (" + u.getCorreo() + ") - Rol: " + u.getRol().getNombre_rol());
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        System.out.println("\n=== PRUEBA 3: LOGIN (correo + contraseña) ===");
        try {
            Usuario usuarioLogin = dao.selectByCorreoContrasena("ana@yisstiv.com", "Ana12345");
            
            if (usuarioLogin != null) {
                System.out.println("✅ Login exitoso! Bienvenida: " + usuarioLogin.getNombre());
                System.out.println("   Rol: " + usuarioLogin.getRol().getNombre_rol());
            } else {
                System.out.println("❌ Login fallido: correo o contraseña incorrectos");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
