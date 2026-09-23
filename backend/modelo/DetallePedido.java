package modelo;

/**
 * Entidad DETALLE_PEDIDO (HU-06): línea de un pedido (producto + cantidad
 * + precio unitario al momento de la compra).
 */
public class DetallePedido {
    private int idDetallePedido;
    private int idPedido;
    private int idProducto;
    private int cantidad;
    private double precioUnit;

    public DetallePedido() {}

    public DetallePedido(int idDetallePedido, int idPedido, int idProducto, int cantidad, double precioUnit) {
        this.idDetallePedido = idDetallePedido;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
    }

    public int getIdDetallePedido() { return idDetallePedido; }
    public void setIdDetallePedido(int idDetallePedido) { this.idDetallePedido = idDetallePedido; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

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
