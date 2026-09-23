package com.mryisstiv.modelo;

import java.sql.Timestamp;

/**
 * Clase que representa el carrito de compras de un usuario
 */
public class Carrito {
    
    private int id_carrito;
    private Usuario usuario; // Relación con el usuario dueño del carrito
    private Timestamp fecha_creacion;
    
    public Carrito() {
    }
    
    public Carrito(int id_carrito, Usuario usuario, Timestamp fecha_creacion) {
        this.id_carrito = id_carrito;
        this.usuario = usuario;
        this.fecha_creacion = fecha_creacion;
    }
    
    // Getters y Setters
    public int getId_carrito() { return id_carrito; }
    public void setId_carrito(int id_carrito) { this.id_carrito = id_carrito; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public Timestamp getFecha_creacion() { return fecha_creacion; }
    public void setFecha_creacion(Timestamp fecha_creacion) { this.fecha_creacion = fecha_creacion; }
}