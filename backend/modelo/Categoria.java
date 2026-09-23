package modelo;

import java.util.List;

/**
 * Entidad CATEGORIA (HU-03, HU-12): agrupa los productos del catálogo
 * (Cholados, Helados, Postres, Combos, etc.).
 */
public class Categoria {
    private int idCategoria;
    private String nombre;
    private String descripcion;

    public Categoria() {}

    public Categoria(int idCategoria, String nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Lista los productos de esta categoría. La implementación real consulta
     * ProductoDAOImpl.consultarTodos() y filtra por idCategoria; se deja como
     * firma en el modelo para respetar el diagrama de clases.
     */
    public List<Producto> listarProductos(dao.ProductoDAO productoDAO) throws Exception {
        List<Producto> todos = productoDAO.consultarTodos();
        todos.removeIf(p -> p.getIdCategoria() != this.idCategoria);
        return todos;
    }
}
