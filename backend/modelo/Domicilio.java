package modelo;

import java.time.LocalDateTime;

/**
 * Entidad DOMICILIO (HU-17 / HU-18): seguimiento de la entrega asignada
 * a un domiciliario para un pedido específico.
 */
public class Domicilio {
    private int idDomicilio;
    private int idPedido;
    private String domiciliario; // cédula o id del usuario con rol domiciliario
    private String estado;       // Asignado, En camino, Entregado
    private LocalDateTime fechaEntrega;
    private double propina;

    public Domicilio() {}

    public Domicilio(int idDomicilio, int idPedido, String domiciliario, String estado, LocalDateTime fechaEntrega, double propina) {
        this.idDomicilio = idDomicilio;
        this.idPedido = idPedido;
        this.domiciliario = domiciliario;
        this.estado = estado;
        this.fechaEntrega = fechaEntrega;
        this.propina = propina;
    }

    public int getIdDomicilio() { return idDomicilio; }
    public void setIdDomicilio(int idDomicilio) { this.idDomicilio = idDomicilio; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public String getDomiciliario() { return domiciliario; }
    public void setDomiciliario(String domiciliario) { this.domiciliario = domiciliario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public double getPropina() { return propina; }
    public void setPropina(double propina) { this.propina = propina; }

    /** HU-17: el admin/trabajador asigna el domiciliario a un pedido. */
    public void asignarDomiciliario(String documentoDomiciliario) {
        this.domiciliario = documentoDomiciliario;
        this.estado = "Asignado";
    }

    /** HU-17: el domiciliario actualiza el estado ("En camino", "Entregado"). */
    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        if ("Entregado".equalsIgnoreCase(nuevoEstado)) {
            this.fechaEntrega = LocalDateTime.now();
        }
    }
}
