package modelo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad CARRITO (HU-05): carrito de compras de un usuario. Las líneas
 * (DetalleCarrito) se manejan mediante DetalleCarritoDAO; los métodos de
 * negocio reciben la lista actual para mantener la clase desacoplada de JDBC.
 */
public class Carrito {
    private int idCarrito;
    private int idUsuario;
    private LocalDateTime fecha;
    private String estado; // Activo, Convertido, Vaciado

    public Carrito() {}

    public Carrito(int idCarrito, int idUsuario, LocalDateTime fecha, String estado) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdCarrito() { return idCarrito; }
    public void setIdCarrito(int idCarrito) { this.idCarrito = idCarrito; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    /** Agrega un producto usando el DAO de detalle (persiste la línea). */
    public void agregarProducto(dao.DetalleCarritoDAO detalleDAO, int idProducto, int cantidad, double precioUnit) throws Exception {
        DetalleCarrito detalle = new DetalleCarrito(0, this.idCarrito, idProducto, cantidad, precioUnit);
        detalleDAO.insertar(detalle);
    }

    public void eliminarProducto(dao.DetalleCarritoDAO detalleDAO, int idDetalleCarrito) throws Exception {
        detalleDAO.eliminar(idDetalleCarrito);
    }

    public double calcularTotal(List<DetalleCarrito> items) {
        double total = 0;
        for (DetalleCarrito item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void vaciarCarrito(dao.DetalleCarritoDAO detalleDAO, List<DetalleCarrito> items) throws Exception {
        for (DetalleCarrito item : items) {
            detalleDAO.eliminar(item.getIdDetalleCarrito());
        }
        this.estado = "Vaciado";
    }
}
