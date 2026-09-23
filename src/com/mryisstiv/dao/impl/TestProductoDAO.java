package com.mryisstiv.dao.impl;

import com.mryisstiv.modelo.Categoria;
import com.mryisstiv.modelo.Producto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TestProductoDAO {

    public static void main(String[] args) {
        ProductoDAOImpl dao = new ProductoDAOImpl();

        System.out.println("--- PRUEBA 1: INSERTAR PRODUCTO ---");
        try {
            Categoria cat = new Categoria();
            cat.setId_categoria(1);
            
            Producto p = new Producto();
            p.setNombre("Cholado MR YISSTIV Especial");
            p.setDescripcion("Delicioso cholado con frutas frescas y leche condensada");
            p.setPrecio(new BigDecimal("12000.00"));
            p.setStock(50);
            p.setImagen("cholado_especial.jpg");
            p.setCategoria(cat);
            p.setFavorito_semana(true);
            p.setActivo(true);
            
            if (dao.insert(p)) {
                System.out.println("Producto insertado con exito!");
            } else {
                System.out.println("Error al insertar.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n--- PRUEBA 2: VER TODOS LOS PRODUCTOS ---");
        try {
            List<Producto> lista = dao.selectAll();
            System.out.println("Total productos: " + lista.size());
            for (Producto prod : lista) {
                System.out.println("- " + prod.getNombre() + 
                                 " | Precio: " + prod.getPrecio() + 
                                 " | Categoria: " + prod.getCategoria().getNombre_categoria());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}