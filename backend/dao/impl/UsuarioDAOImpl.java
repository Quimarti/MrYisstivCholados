package dao.impl;

import dao.UsuarioDAO;
import modelo.Usuario;
import utilidad.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Usuario u) throws Exception {
        String sql = "INSERT INTO usuario (id_rol, nombres, apellidos, cedula, fecha_nacimiento, correo, contrasena, activo, fecha_registro) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getIdRol());
            ps.setString(2, u.getNombres());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getCedula());
            ps.setDate(5, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
            ps.setString(6, u.getCorreo());
            ps.setString(7, u.getContrasena());
            ps.setBoolean(8, u.isActivo());
            ps.setTimestamp(9, Timestamp.valueOf(u.getFechaRegistro() != null ? u.getFechaRegistro() : LocalDateTime.now()));
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Usuario> consultarTodos() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public Usuario consultarPorId(int id) throws Exception {
        return consultarUno("SELECT * FROM usuario WHERE id_usuario = ?", id);
    }

    @Override
    public Usuario consultarPorCedula(String cedula) throws Exception {
        String sql = "SELECT * FROM usuario WHERE cedula = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public Usuario consultarPorCorreo(String correo) throws Exception {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public List<Usuario> buscar(String texto) throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nombres LIKE ? OR apellidos LIKE ? OR cedula LIKE ? OR correo LIKE ?";
        String comodin = "%" + texto + "%";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, comodin);
            ps.setString(2, comodin);
            ps.setString(3, comodin);
            ps.setString(4, comodin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Usuario u) throws Exception {
        String sql = "UPDATE usuario SET id_rol=?, nombres=?, apellidos=?, cedula=?, fecha_nacimiento=?, correo=?, contrasena=?, activo=? WHERE id_usuario=?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getIdRol());
            ps.setString(2, u.getNombres());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getCedula());
            ps.setDate(5, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
            ps.setString(6, u.getCorreo());
            ps.setString(7, u.getContrasena());
            ps.setBoolean(8, u.isActivo());
            ps.setInt(9, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean cambiarRol(int idUsuario, int idRol) throws Exception {
        String sql = "UPDATE usuario SET id_rol = ? WHERE id_usuario = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean alternarActivo(int idUsuario) throws Exception {
        String sql = "UPDATE usuario SET activo = NOT activo WHERE id_usuario = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Usuario consultarUno(String sql, int id) throws Exception {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Date fechaNac = rs.getDate("fecha_nacimiento");
        Timestamp fechaReg = rs.getTimestamp("fecha_registro");
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getInt("id_rol"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("cedula"),
                fechaNac != null ? fechaNac.toLocalDate() : null,
                rs.getString("correo"),
                rs.getString("contrasena"),
                rs.getBoolean("activo"),
                fechaReg != null ? fechaReg.toLocalDateTime() : null
        );
    }
}
