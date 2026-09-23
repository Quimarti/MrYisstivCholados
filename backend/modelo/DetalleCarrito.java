package modelo;

/**
 * Entidad DETALLE_CARRITO (HU-05): línea de producto dentro del carrito
 * de un usuario, antes de convertirse en un pedido.
 */
public class DetalleCarrito {
    private int idDetalleCarrito;
    private int idCarrito;
    private int idProducto;
    private int cantidad;
    private double precioUnit;

    public DetalleCarrito() {}

    public DetalleCarrito(int idDetalleCarrito, int idCarrito, int idProducto, int cantidad, double precioUnit) {
        this.idDetalleCarrito = idDetalleCarrito;
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
    }

    public int getIdDetalleCarrito() { return idDetalleCarrito; }
    public void setIdDetalleCarrito(int idDetalleCarrito) { this.idDetalleCarrito = idDetalleCarrito; }

    public int getIdCarrito() { return idCarrito; }
    public void setIdCarrito(int idCarrito) { this.idCarrito = idCarrito; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnit() { return precioUnit; }
    public void setPrecioUnit(double precioUnit) { this.precioUnit = precioUnit; }

    public double calcularSubtotal() {
        return cantidad * precioUnit;
    }
}
