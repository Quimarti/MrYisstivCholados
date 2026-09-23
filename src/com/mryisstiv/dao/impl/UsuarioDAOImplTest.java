package com.mryisstiv.dao.impl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.mryisstiv.modelo.Rol;
import com.mryisstiv.modelo.Usuario;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class UsuarioDAOImplTest {
    
    private UsuarioDAOImpl dao;
    private Rol rol;
    
    @Before
    public void setUp() {
        dao = new UsuarioDAOImpl();
        rol = new Rol();
        rol.setId_rol(1);
    }
    
    @Test
    public void testInsert() throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellidos("Perez");
        usuario.setCedula("9876543210");
        usuario.setCorreo("juan@test.com");
        usuario.setTelefono("3009999999");
        usuario.setContrasena("Juan123");
        usuario.setRol(rol);
        usuario.setActivo(true);
        usuario.setFecha_registro(new Timestamp(new Date().getTime()));
        
        boolean resultado = dao.insert(usuario);
        assertTrue("El usuario debería insertarse correctamente", resultado);
    }
    
    @Test
    public void testSelect() throws SQLException {
        Usuario usuario = dao.select(1);
        assertNotNull("Debería encontrar el usuario con ID 1", usuario);
        assertEquals("Ana", usuario.getNombre());
    }
    
    @Test
    public void testSelectAll() throws SQLException {
        List<Usuario> usuarios = dao.selectAll();
        assertNotNull("La lista no debería ser null", usuarios);
        assertTrue("Debería haber al menos 1 usuario", usuarios.size() >= 1);
    }
    
    @Test
    public void testLogin() throws SQLException {
        Usuario usuario = dao.selectByCorreoContrasena("ana@yisstiv.com", "Ana12345");
        assertNotNull("El login debería funcionar con credenciales correctas", usuario);
        assertEquals("Ana", usuario.getNombre());
    }
}