package modelo;

/**
 * Entidad DIRECCION (HU-10): direcciones de entrega guardadas por el cliente.
 */
public class Direccion {
    private int idDireccion;
    private int idUsuario;
    private String direccion;
    private String ciudad;
    private String telefono;
    private boolean esPrincipal;

    public Direccion() {}

    public Direccion(int idDireccion, int idUsuario, String direccion, String ciudad, String telefono, boolean esPrincipal) {
        this.idDireccion = idDireccion;
        this.idUsuario = idUsuario;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.esPrincipal = esPrincipal;
    }

    public int getIdDireccion() { return idDireccion; }
    public void setIdDireccion(int idDireccion) { this.idDireccion = idDireccion; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(boolean esPrincipal) { this.esPrincipal = esPrincipal; }

    public boolean actualizarDireccion(String nuevaDireccion, String nuevaCiudad, String nuevoTelefono) {
        if (nuevaDireccion == null || nuevaDireccion.isEmpty()) return false;
        this.direccion = nuevaDireccion;
        this.ciudad = nuevaCiudad;
        this.telefono = nuevoTelefono;
        return true;
    }
}
