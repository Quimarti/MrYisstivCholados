package dao.impl;

import dao.FavoritoDAO;
import modelo.Favorito;
import utilidad.Conexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAOImpl implements FavoritoDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Favorito f) throws Exception {
        String sql = "INSERT INTO favorito (id_usuario, id_producto, fecha_agregado, semana) VALUES (?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, f.getIdUsuario());
            ps.setInt(2, f.getIdProducto());
            ps.setTimestamp(3, Timestamp.valueOf(f.getFechaAgregado() != null ? f.getFechaAgregado() : LocalDateTime.now()));
            ps.setString(4, f.getSemana());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Favorito> consultarTodos() throws Exception {
        List<Favorito> lista = new ArrayList<>();
        String sql = "SELECT * FROM favorito";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public List<Favorito> consultarPorUsuario(int idUsuario) throws Exception {
        List<Favorito> lista = new ArrayList<>();
        String sql = "SELECT * FROM favorito WHERE id_usuario = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean esFavorito(int idUsuario, int idProducto) throws Exception {
        String sql = "SELECT COUNT(*) FROM favorito WHERE id_usuario = ? AND id_producto = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public Favorito consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM favorito WHERE id_favorito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Favorito f) throws Exception {
        String sql = "UPDATE favorito SET semana = ? WHERE id_favorito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getSemana());
            ps.setInt(2, f.getIdFavorito());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM favorito WHERE id_favorito = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Favorito mapear(ResultSet rs) throws SQLException {
        Timestamp fecha = rs.getTimestamp("fecha_agregado");
        return new Favorito(
                rs.getInt("id_favorito"),
                rs.getInt("id_usuario"),
                rs.getInt("id_producto"),
                fecha != null ? fecha.toLocalDateTime() : null,
                rs.getString("semana")
        );
    }
}
