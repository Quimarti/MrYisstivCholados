package dao.impl;

import dao.CategoriaDAO;
import modelo.Categoria;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Categoria objeto) throws Exception {
        String sql = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getDescripcion());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Categoria> consultarTodos() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public Categoria consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM categoria WHERE id_categoria = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Categoria objeto) throws Exception {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id_categoria = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getDescripcion());
            ps.setInt(3, objeto.getIdCategoria());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        // HU-12: no eliminar categorías que tengan productos asociados
        String verificar = "SELECT COUNT(*) FROM producto WHERE id_categoria = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement psVerificar = con.prepareStatement(verificar)) {
            psVerificar.setInt(1, id);
            try (ResultSet rs = psVerificar.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) return false;
            }
        }
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Categoria mapear(ResultSet rs) throws SQLException {
        return new Categoria(rs.getInt("id_categoria"), rs.getString("nombre"), rs.getString("descripcion"));
    }
}
