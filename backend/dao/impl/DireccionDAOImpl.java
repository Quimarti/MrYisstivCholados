package dao.impl;

import dao.DireccionDAO;
import modelo.Direccion;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DireccionDAOImpl implements DireccionDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Direccion d) throws Exception {
        try (Connection con = conexion.obtenerConexion()) {
            // HU-10: si es la primera dirección del usuario, queda como principal
            if (contarDirecciones(con, d.getIdUsuario()) == 0) d.setEsPrincipal(true);
            String sql = "INSERT INTO direccion (id_usuario, direccion, ciudad, telefono, es_principal) VALUES (?,?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, d.getIdUsuario());
                ps.setString(2, d.getDireccion());
                ps.setString(3, d.getCiudad());
                ps.setString(4, d.getTelefono());
                ps.setBoolean(5, d.isEsPrincipal());
                return ps.executeUpdate() > 0;
            }
        }
    }

    private int contarDirecciones(Connection con, int idUsuario) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM direccion WHERE id_usuario = ?")) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    @Override
    public List<Direccion> consultarTodos() throws Exception {
        List<Direccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM direccion";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public List<Direccion> consultarPorUsuario(int idUsuario) throws Exception {
        List<Direccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM direccion WHERE id_usuario = ?";
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
    public Direccion consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM direccion WHERE id_direccion = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Direccion d) throws Exception {
        String sql = "UPDATE direccion SET direccion=?, ciudad=?, telefono=? WHERE id_direccion=?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getDireccion());
            ps.setString(2, d.getCiudad());
            ps.setString(3, d.getTelefono());
            ps.setInt(4, d.getIdDireccion());
            return ps.executeUpdate() > 0;
        }
    }

    /** HU-10: marca una dirección como principal y desmarca las demás del mismo usuario. */
    @Override
    public boolean marcarPrincipal(int idDireccion, int idUsuario) throws Exception {
        try (Connection con = conexion.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psReset = con.prepareStatement("UPDATE direccion SET es_principal = FALSE WHERE id_usuario = ?");
                 PreparedStatement psSet = con.prepareStatement("UPDATE direccion SET es_principal = TRUE WHERE id_direccion = ?")) {
                psReset.setInt(1, idUsuario);
                psReset.executeUpdate();
                psSet.setInt(1, idDireccion);
                int filas = psSet.executeUpdate();
                con.commit();
                return filas > 0;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM direccion WHERE id_direccion = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Direccion mapear(ResultSet rs) throws SQLException {
        return new Direccion(
                rs.getInt("id_direccion"),
                rs.getInt("id_usuario"),
                rs.getString("direccion"),
                rs.getString("ciudad"),
                rs.getString("telefono"),
                rs.getBoolean("es_principal")
        );
    }
}
