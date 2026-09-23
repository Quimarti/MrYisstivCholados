package com.mryisstiv.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Clase que representa un pedido realizado por un cliente
 */
public class Pedido {
    
    private int id_pedido;
    private Usuario usuario;
    private int id_direccion; // Usamos el ID directo para simplificar
    private int id_metodo_pago; // Usamos el ID directo (1=Efectivo, 2=Nequi, etc.)
    private Timestamp fecha_pedido;
    private BigDecimal subtotal;
    private BigDecimal valor_domicilio;
    private BigDecimal total;
    private String estado; // Pendiente, Enviado, Entregado
    private String observaciones;
    
    public Pedido() {
    }
    
    // Getters y Setters (Te los pongo en bloque para ir más rápido)
    public int getId_pedido() { return id_pedido; }
    public void setId_pedido(int id_pedido) { this.id_pedido = id_pedido; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public int getId_direccion() { return id_direccion; }
    public void setId_direccion(int id_direccion) { this.id_direccion = id_direccion; }
    
    public int getId_metodo_pago() { return id_metodo_pago; }
    public void setId_metodo_pago(int id_metodo_pago) { this.id_metodo_pago = id_metodo_pago; }
    
    public Timestamp getFecha_pedido() { return fecha_pedido; }
    public void setFecha_pedido(Timestamp fecha_pedido) { this.fecha_pedido = fecha_pedido; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public BigDecimal getValor_domicilio() { return valor_domicilio; }
    public void setValor_domicilio(BigDecimal valor_domicilio) { this.valor_domicilio = valor_domicilio; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
