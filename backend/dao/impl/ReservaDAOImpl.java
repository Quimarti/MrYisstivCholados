package dao.impl;

import dao.ReservaDAO;
import modelo.Reserva;
import utilidad.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOImpl implements ReservaDAO {
    private final Conexion conexion = new Conexion();

    @Override
    public boolean insertar(Reserva r) throws Exception {
        String sql = "INSERT INTO reserva (id_usuario, fecha, hora, personas, estado, observaciones) VALUES (?,?,?,?,?,?)";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getIdUsuario());
            ps.setDate(2, Date.valueOf(r.getFecha()));
            ps.setString(3, r.getHora());
            ps.setInt(4, r.getPersonas());
            ps.setString(5, r.getEstado());
            ps.setString(6, r.getObservaciones());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Reserva> consultarTodos() throws Exception {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public List<Reserva> consultarPorUsuario(int idUsuario) throws Exception {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE id_usuario = ?";
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
    public Reserva consultarPorId(int id) throws Exception {
        String sql = "SELECT * FROM reserva WHERE id_reserva = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public boolean actualizar(Reserva r) throws Exception {
        String sql = "UPDATE reserva SET fecha=?, hora=?, personas=?, estado=?, observaciones=? WHERE id_reserva=?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(r.getFecha()));
            ps.setString(2, r.getHora());
            ps.setInt(3, r.getPersonas());
            ps.setString(4, r.getEstado());
            ps.setString(5, r.getObservaciones());
            ps.setInt(6, r.getIdReserva());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM reserva WHERE id_reserva = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Reserva mapear(ResultSet rs) throws SQLException {
        return new Reserva(
                rs.getInt("id_reserva"),
                rs.getInt("id_usuario"),
                rs.getDate("fecha").toLocalDate(),
                rs.getString("hora"),
                rs.getInt("personas"),
                rs.getString("estado"),
                rs.getString("observaciones")
        );
    }
}
