package modelo;

import java.time.LocalDate;

/**
 * Entidad RESERVA (HU-08): reservar mesa/espacio en la heladería.
 */
public class Reserva {
    private int idReserva;
    private int idUsuario;
    private LocalDate fecha;
    private String hora;
    private int personas;
    private String estado; // Pendiente, Confirmada, Cancelada
    private String observaciones;

    public Reserva() {}

    public Reserva(int idReserva, int idUsuario, LocalDate fecha, String hora, int personas, String estado, String observaciones) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.hora = hora;
        this.personas = personas;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public int getPersonas() { return personas; }
    public void setPersonas(int personas) { this.personas = personas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public boolean programarReserva() {
        if (personas <= 0 || fecha == null || hora == null) return false;
        this.estado = "Pendiente";
        return true;
    }

    public boolean cancelarReserva() {
        this.estado = "Cancelada";
        return true;
    }

    public String confirmarReserva() {
        this.estado = "Confirmada";
        return "Reserva #" + idReserva + " confirmada para " + personas + " personas el " + fecha + " a las " + hora;
    }
}
