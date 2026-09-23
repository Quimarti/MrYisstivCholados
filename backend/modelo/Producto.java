package modelo;

/**
 * Entidad PRODUCTO (HU-03, HU-04, HU-11, HU-15). Incluye "stock" para
 * soportar HU-15 (Gestionar inventario), aunque el diagrama original solo
 * dibuja los métodos actualizarStock()/consultarDisponibilidad().
 */
public class Producto {
    private int idProducto;
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagen;
    private boolean esFavorito; // "Favorito de la semana" (HU-04)
    private int stock;

    public Producto() {}

    public Producto(int idProducto, int idCategoria, String nombre, String descripcion,
                     double precio, String imagen, boolean esFavorito, int stock) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.esFavorito = esFavorito;
        this.stock = stock;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public boolean isEsFavorito() { return esFavorito; }
    public void setEsFavorito(boolean esFavorito) { this.esFavorito = esFavorito; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    /** HU-15: descuenta stock al confirmar un pedido; no baja de 0. */
    public boolean actualizarStock(int cantidadVendida) {
        if (cantidadVendida > this.stock) return false;
        this.stock = Math.max(0, this.stock - cantidadVendida);
        return true;
    }

    /** HU-15: valida disponibilidad antes de agregar al carrito/pedido. */
    public boolean consultarDisponibilidad(int cantidadSolicitada) {
        return this.stock >= cantidadSolicitada;
    }
}
