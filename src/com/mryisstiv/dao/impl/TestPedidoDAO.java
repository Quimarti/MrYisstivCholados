package com.mryisstiv.dao.impl;

import com.mryisstiv.modelo.Pedido;
import com.mryisstiv.modelo.Usuario;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TestPedidoDAO {

    public static void main(String[] args) {
        PedidoDAOImpl dao = new PedidoDAOImpl();

        System.out.println("--- PRUEBA 1: CREAR UN NUEVO PEDIDO ---");
        try {
            // Simulamos que el usuario con ID 1 (Ana) hace el pedido
            Usuario usuario = new Usuario();
            usuario.setId_usuario(1); 
            
            Pedido pedido = new Pedido();
            pedido.setUsuario(usuario);
            pedido.setId_direccion(1); // Direccion de prueba
            pedido.setId_metodo_pago(1); // 1 = Efectivo (Contra entrega)
            pedido.setSubtotal(new BigDecimal("24000.00")); // 2 cholados a 12000
            pedido.setValor_domicilio(new BigDecimal("5000.00"));
            pedido.setTotal(new BigDecimal("29000.00")); // 24000 + 5000
            pedido.setEstado("Pendiente");
            pedido.setObservaciones("Sin cebolla, por favor");
            pedido.setFecha_pedido(new Timestamp(new Date().getTime()));
            
            if (dao.insert(pedido)) {
                System.out.println("Pedido creado con exito! ID del pedido: " + pedido.getId_pedido());
            } else {
                System.out.println("Error al crear el pedido.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n--- PRUEBA 2: VER TODOS LOS PEDIDOS ---");
        try {
            List<Pedido> lista = dao.selectAll();
            System.out.println("Total pedidos en el sistema: " + lista.size());
            for (Pedido p : lista) {
                System.out.println("- Pedido #" + p.getId_pedido() + 
                                 " | Total: $" + p.getTotal() + 
                                 " | Estado: " + p.getEstado() + 
                                 " | Pago: " + p.getId_metodo_pago());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}