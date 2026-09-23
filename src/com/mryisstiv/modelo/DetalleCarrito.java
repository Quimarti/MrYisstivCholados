package com.mryisstiv.modelo;

/**
 * Clase que representa un producto específico dentro del carrito
 */
public class DetalleCarrito {
    
    private int id_detalle_carrito;
    private Carrito carrito; // A qué carrito pertenece
    private Producto producto; // Qué producto es
    private int cantidad; // Cuántas unidades lleva
    
    public DetalleCarrito() {
    }
    
    public DetalleCarrito(int id_detalle_carrito, Carrito carrito, Producto producto, int cantidad) {
        this.id_detalle_carrito = id_detalle_carrito;
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    // Getters y Setters
    public int getId_detalle_carrito() { return id_detalle_carrito; }
    public void setId_detalle_carrito(int id_detalle_carrito) { this.id_detalle_carrito = id_detalle_carrito; }
    
    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }
    
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}