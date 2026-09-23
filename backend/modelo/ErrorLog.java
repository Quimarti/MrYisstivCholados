package modelo;

import java.time.LocalDateTime;

/**
 * Entidad ERROR_LOG (HU-19): registra automáticamente los fallos del
 * sistema sin interrumpir la experiencia del usuario.
 */
public class ErrorLog {
    private int idError;
    private int idUsuario;
    private LocalDateTime fecha;
    private String tipoError;
    private String mensaje;
    private String pantalla;

    public ErrorLog() {}

    public ErrorLog(int idError, int idUsuario, LocalDateTime fecha, String tipoError, String mensaje, String pantalla) {
        this.idError = idError;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.tipoError = tipoError;
        this.mensaje = mensaje;
        this.pantalla = pantalla;
    }

    public int getIdError() { return idError; }
    public void setIdError(int idError) { this.idError = idError; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getTipoError() { return tipoError; }
    public void setTipoError(String tipoError) { this.tipoError = tipoError; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getPantalla() { return pantalla; }
    public void setPantalla(String pantalla) { this.pantalla = pantalla; }

    /** Marca la fecha actual; el guardado real lo hace ErrorLogDAOImpl. */
    public void registrarError() {
        this.fecha = LocalDateTime.now();
    }
}
