package Modelo;

import java.time.LocalDate;

public class Tarea {

    private int id;
    private String titulo;
    private String descripcion;
    private int prioridad;
    private boolean estado;
    private LocalDate fechaCreacion;

    public Tarea() {
    }

    public Tarea(int id, String titulo, String descripcion, int prioridad, boolean estado, LocalDate fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Tarea(String titulo, String descripcion, int prioridad, boolean estado, LocalDate fechaCreacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPrioridad() { return prioridad; }

    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

    public boolean isEstado() { return estado; }

    public void setEstado(boolean estado) { this.estado = estado; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }

    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @Override
    public String toString() {
        return "Tarea{id=" + id + ", titulo='" + titulo + "', descripcion='" + descripcion +
                "', prioridad=" + prioridad + ", estado=" + (estado ? "Completada" : "Pendiente") +
                ", fechaCreacion=" + fechaCreacion + '}';
    }
}