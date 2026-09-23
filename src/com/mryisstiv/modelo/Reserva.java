package com.mryisstiv.modelo;

import java.sql.Timestamp;

/**
 * Clase que representa una reserva de mesa o producto
 */
public class Reserva {
    
    private int id_reserva;
    private Usuario usuario;
    private String tipo_reserva; // Mesa, Producto especial, Evento
    private Timestamp fecha_reserva;
    private String estado; // Pendiente, Confirmada, Cancelada
    private String observaciones;
    
    public Reserva() {
    }
    
    // Getters y Setters
    public int getId_reserva() { return id_reserva; }
    public void setId_reserva(int id_reserva) { this.id_reserva = id_reserva; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public String getTipo_reserva() { return tipo_reserva; }
    public void setTipo_reserva(String tipo_reserva) { this.tipo_reserva = tipo_reserva; }
    
    public Timestamp getFecha_reserva() { return fecha_reserva; }
    public void setFecha_reserva(Timestamp fecha_reserva) { this.fecha_reserva = fecha_reserva; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}