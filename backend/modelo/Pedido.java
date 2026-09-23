package modelo;

import java.time.LocalDateTime;

/**
 * Entidad PEDIDO (HU-06, HU-07, HU-14, HU-17). El estado recorre:
 * Confirmado -> Preparando -> Listo -> En camino -> Entregado (o Cancelado).
 */
public class Pedido {
    private int idPedido;
    private int idUsuario;
    private int idDireccion;
    private LocalDateTime fecha;
    private double subtotal;
    private double total;
    private String estado;
    private String observaciones;

    public Pedido() {}

    public Pedido(int idPedido, int idUsuario, int idDireccion, LocalDateTime fecha,
                   double subtotal, double total, String estado, String observaciones) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.idDireccion = idDireccion;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.total = total;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdDireccion() { return idDireccion; }
    public void setIdDireccion(int idDireccion) { this.idDireccion = idDireccion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    /** HU-06: valida los datos mínimos y deja el pedido en estado "Confirmado". */
    public boolean procesarPedido() {
        if (idUsuario <= 0 || idDireccion <= 0 || total <= 0) return false;
        this.fecha = LocalDateTime.now();
        this.estado = "Confirmado";
        return true;
    }

    public boolean cancelarPedido() {
        if ("Entregado".equals(this.estado)) return false; // ya entregado, no se cancela
        this.estado = "Cancelado";
        return true;
    }

    public String generarFactura() {
        return "Factura pedido #" + idPedido + " — Subtotal: $" + subtotal + " — Total: $" + total + " — Estado: " + estado;
    }
}
