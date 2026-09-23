package modelo;

import java.time.LocalDateTime;

/**
 * Entidad FAVORITO (HU-09): productos que un cliente marcó como favoritos.
 * El campo "semana" se usa para el badge "Favorito de la semana" (HU-04),
 * marcado por el equipo (admin/trabajador) sobre un producto puntual.
 */
public class Favorito {
    private int idFavorito;
    private int idUsuario;
    private int idProducto;
    private LocalDateTime fechaAgregado;
    private String semana; // ej. "2026-W38" cuando aplica como "favorito de la semana"

    public Favorito() {}

    public Favorito(int idFavorito, int idUsuario, int idProducto, LocalDateTime fechaAgregado, String semana) {
        this.idFavorito = idFavorito;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.fechaAgregado = fechaAgregado;
        this.semana = semana;
    }

    public int getIdFavorito() { return idFavorito; }
    public void setIdFavorito(int idFavorito) { this.idFavorito = idFavorito; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public LocalDateTime getFechaAgregado() { return fechaAgregado; }
    public void setFechaAgregado(LocalDateTime fechaAgregado) { this.fechaAgregado = fechaAgregado; }

    public String getSemana() { return semana; }
    public void setSemana(String semana) { this.semana = semana; }

    public boolean agregarFavorito() {
        this.fechaAgregado = LocalDateTime.now();
        return true;
    }

    public boolean eliminarFavorito() {
        return true; // la eliminación real la hace FavoritoDAOImpl.eliminar(id)
    }
}
