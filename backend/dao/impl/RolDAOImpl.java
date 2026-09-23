package dao.impl;

import dao.RolDAO;
import modelo.Rol;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAOImpl implements RolDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Rol objeto) throws Exception {
        String sql = "INSERT INTO rol (nombre_rol, descripcion) VALUES (?, ?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, objeto.getNombreRol());
            ps.setString(2, objeto.getDescripcion());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Rol> consultarTodos() throws Exception {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM rol";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public Rol consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM rol WHERE id_rol = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Rol objeto) throws Exception {
        String sql = "UPDATE rol SET nombre_rol = ?, descripcion = ? WHERE id_rol = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, objeto.getNombreRol());
            ps.setString(2, objeto.getDescripcion());
            ps.setInt(3, objeto.getIdRol());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM rol WHERE id_rol = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Rol mapear(ResultSet rs) throws SQLException {
        return new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"), rs.getString("descripcion"));
    }
}
