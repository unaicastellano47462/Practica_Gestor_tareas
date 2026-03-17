package DAO;

import Modelo.Tarea;
import Utilidades.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareasDAO {

    public void insertar(Tarea tarea) throws SQLException {
        String sql = "INSERT INTO tareas (titulo, descripcion, prioridad, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tarea.getTitulo());
            ps.setString(2, tarea.getDescripcion());
            ps.setInt(3, tarea.getPrioridad());
            ps.setBoolean(4, tarea.isEstado());
            ps.setDate(5, java.sql.Date.valueOf(tarea.getFechaCreacion()));
            ps.executeUpdate();
        }
    }

    public int borrar(int id) throws SQLException {
        String sql = "DELETE FROM tareas WHERE id = ?";
        int n = 0;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            n = ps.executeUpdate();
        }
        return n;
    }

    public void modificar(Tarea tarea) throws SQLException {
        String sql = "UPDATE tareas SET titulo = ?, descripcion = ?, prioridad = ?, estado = ?, fecha_creacion = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tarea.getTitulo());
            ps.setString(2, tarea.getDescripcion());
            ps.setInt(3, tarea.getPrioridad());
            ps.setBoolean(4, tarea.isEstado());
            ps.setDate(5, java.sql.Date.valueOf(tarea.getFechaCreacion()));
            ps.setInt(6, tarea.getId());
            ps.executeUpdate();
        }
    }

    public Tarea buscarPorId(int id) {
        String sql = "SELECT * FROM tareas WHERE id = ?";
        Tarea tarea = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tarea = mapearTarea(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
        return tarea;
    }

    public List<Tarea> listarTareas() {
        String sql = "SELECT * FROM tareas";
        return ejecutarConsultaLista(sql, null);
    }

    public List<Tarea> buscarPorTitulo(String titulo) {
        String sql = "SELECT * FROM tareas WHERE titulo LIKE ?";
        return ejecutarConsultaLista(sql, "%" + titulo + "%");
    }

    public List<Tarea> buscarPorPrioridad(int prioridad) {
        String sql = "SELECT * FROM tareas WHERE prioridad = ?";
        return ejecutarConsultaLista(sql, prioridad);
    }

    public List<Tarea> buscarPorEstado(boolean estado) {
        String sql = "SELECT * FROM tareas WHERE estado = ?";
        return ejecutarConsultaLista(sql, estado);
    }

    public List<Tarea> buscarPorFechaCreacion(String fecha) {
        String sql = "SELECT * FROM tareas WHERE fecha_creacion = ?";
        return ejecutarConsultaLista(sql, java.sql.Date.valueOf(fecha));
    }

    private List<Tarea> ejecutarConsultaLista(String sql, Object parametro) {
        List<Tarea> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (parametro != null) {
                if (parametro instanceof String) ps.setString(1, (String) parametro);
                else if (parametro instanceof Integer) ps.setInt(1, (Integer) parametro);
                else if (parametro instanceof Boolean) ps.setBoolean(1, (Boolean) parametro);
                else if (parametro instanceof java.sql.Date) ps.setDate(1, (java.sql.Date) parametro);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearTarea(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
        return lista;
    }

    private Tarea mapearTarea(ResultSet rs) throws SQLException {
        Tarea t = new Tarea();
        t.setId(rs.getInt("id"));
        t.setTitulo(rs.getString("titulo"));
        t.setDescripcion(rs.getString("descripcion"));
        t.setPrioridad(rs.getInt("prioridad"));
        t.setEstado(rs.getBoolean("estado"));
        t.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
        return t;
    }
}