package Controlador;

import Modelo.Tarea;
import DAO.TareasDAO;

import java.time.LocalDate;
import java.util.List;

public class TareasController {

    private TareasDAO tareasDAO;

    public TareasController() {
        tareasDAO = new TareasDAO();
    }

    public void crearTarea(String titulo, String descripcion, int prioridad, boolean estado, String fechaCreacion) {
        try {
            Tarea tarea = new Tarea(titulo, descripcion, prioridad, estado, LocalDate.parse(fechaCreacion));
            tareasDAO.insertar(tarea);
        } catch (Exception e) {
            System.out.println("Error en controlador al crear: " + e.getMessage());
        }
    }

    public int borrarTarea(int id) throws Exception {
        return tareasDAO.borrar(id);
    }

    public void modificarTarea(Tarea tarea, String nuevoTitulo, int nuevaPrioridad) {
        try {
            tarea.setTitulo(nuevoTitulo);
            tarea.setPrioridad(nuevaPrioridad);
            tareasDAO.modificar(tarea);
        } catch (Exception e) {
            System.out.println("Error en controlador al modificar: " + e.getMessage());
        }
    }

    public Tarea buscarPorId(int id) {
        return tareasDAO.buscarPorId(id);
    }

    public List<Tarea> listarTareas() {
        return tareasDAO.listarTareas();
    }

    public List<Tarea> buscarPorTitulo(String titulo) {
        return tareasDAO.buscarPorTitulo(titulo);
    }

    public List<Tarea> buscarPorPrioridad(int prioridad) {
        return tareasDAO.buscarPorPrioridad(prioridad);
    }

    public List<Tarea> buscarPorEstado(boolean estado) {
        return tareasDAO.buscarPorEstado(estado);
    }

    public List<Tarea> buscarPorFechaCreacion(String fecha) {
        return tareasDAO.buscarPorFechaCreacion(fecha);
    }
}