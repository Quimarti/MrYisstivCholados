package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad USUARIO. Cubre HU-01 (registro), HU-02 (login) y HU-13
 * (gestión de usuarios: activar/desactivar, cambiar rol).
 */
public class Usuario {
    private int idUsuario;
    private int idRol;
    private String nombres;
    private String apellidos;
    private String cedula;
    private LocalDate fechaNacimiento;
    private String correo;
    private String contrasena;
    private boolean activo;
    private LocalDateTime fechaRegistro;

    public Usuario() {}

    public Usuario(int idUsuario, int idRol, String nombres, String apellidos, String cedula,
                    LocalDate fechaNacimiento, String correo, String contrasena,
                    boolean activo, LocalDateTime fechaRegistro) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.contrasena = contrasena;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    /**
     * HU-01: validación básica antes de insertar (delegada normalmente al
     * DAO/servicio; se deja aquí como regla de negocio mínima del modelo).
     */
    public boolean registrar() {
        return correo != null && correo.contains("@")
                && contrasena != null && contrasena.length() >= 8
                && cedula != null && !cedula.isEmpty();
    }

    /** HU-02: valida credenciales contra los datos actuales del objeto. */
    public boolean iniciarSesion(String correoIngresado, String contrasenaIngresada) {
        return activo && this.correo.equalsIgnoreCase(correoIngresado)
                && this.contrasena.equals(contrasenaIngresada);
    }

    public boolean actualizarPerfil(String nombres, String apellidos, String correo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        return true;
    }
}
