package Vista;

import Controlador.TareasController;
import Modelo.Tarea;
import Utilidades.EntradaDatos;

import java.util.List;
import java.util.Scanner;

public class MenuTareas {

    private Scanner sc;
    private TareasController tareasController;

    public MenuTareas() {
        sc = new Scanner(System.in);
        tareasController = new TareasController();
    }

    public void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("\n====== MENÚ DE TAREAS ======");
            System.out.println("1. Crear tarea");
            System.out.println("2. Borrar tarea");
            System.out.println("3. Modificar tarea");
            System.out.println("4. Listar tareas");
            System.out.println("5. Buscar tarea por ID");
            System.out.println("6. Buscar tarea por título");
            System.out.println("7. Buscar tarea por prioridad");
            System.out.println("8. Buscar tarea por estado");
            System.out.println("9. Buscar tarea por fecha de creación");
            System.out.println("0. Salir");
            System.out.println("==========================");

            opcion = EntradaDatos.leerEntero(sc, "Elige una opción: ");

            switch (opcion) {
                case 1 -> crearTarea();
                case 2 -> borrarTarea();
                case 3 -> modificarTarea();
                case 4 -> listarTareas();
                case 5 -> buscarPorId();
                case 6 -> buscarPorTitulo();
                case 7 -> buscarPorPrioridad();
                case 8 -> buscarPorEstado();
                case 9 -> buscarPorFechaCreacion();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
    }

    public void crearTarea() {
        System.out.println("\n--- CREAR NUEVA TAREA ---");
        try {
            String titulo =  EntradaDatos.leerTexto(sc, "Introduce el título de la tarea: ", ".+");
            if (titulo != null) {
                String descripcion = EntradaDatos.leerTexto(sc, "Introduce la descripción de la tarea: ", ".+");
                if (descripcion != null) {

                    System.out.println("Analizando descripción con Inteligencia Artificial...");
                    int prioridadSugerida = Utilidades.ServicioIA.obtenerPrioridad(descripcion);
                    System.out.println("-> La IA ha sugerido una prioridad de: " + prioridadSugerida);

                    int prioridadFinal = EntradaDatos.leerEntero(sc, "Introduce la prioridad definitiva (1-5) [Puedes usar la sugerida u otra]: ");

                    while (prioridadFinal < 1 || prioridadFinal > 5) {
                        System.out.println("Error: La prioridad debe estar entre 1 y 5.");
                        prioridadFinal = EntradaDatos.leerEntero(sc, "Introduce la prioridad definitiva (1-5): ");
                    }

                    int estadoInput = EntradaDatos.leerEntero(sc, "¿La tarea está completada? (1 para Completada, 0 para Pendiente): ");
                    if (estadoInput == 1 || estadoInput == 0) {
                        boolean estado = (estadoInput == 1);
                        String fechaCreacion = EntradaDatos.leerTexto(sc, "Introduce la fecha de creación (YYYY-MM-DD): ", "^\\d{4}-\\d{2}-\\d{2}$");

                        if (fechaCreacion != null) {
                            tareasController.crearTarea(titulo, descripcion, prioridadFinal, estado, fechaCreacion);
                            System.out.println("Tarea creada correctamente.");
                        }
                    } else {
                        System.out.println("Estado inválido.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error creando la tarea: " + e.getMessage());
        }
    }

    public void borrarTarea() {
        System.out.println("\n--- BORRAR TAREA ---");
        try {
            int id = EntradaDatos.leerEntero(sc, "Introduce el ID de la tarea a borrar: ");
            if (id != -1) {
                int n = tareasController.borrarTarea(id);
                if (n == 1) {
                    System.out.println("Tarea borrada correctamente.");
                } else {
                    System.out.println("No se encontró ninguna tarea con el ID proporcionado.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al borrar la tarea: " + e.getMessage());
        }
    }

    private Tarea buscarPorId() {
        System.out.println("\n--- BUSCAR TAREA POR ID ---");
        int id = EntradaDatos.leerEntero(sc, "Introduce el ID a buscar: ");
        if (id != -1) {
            Tarea tarea = tareasController.buscarPorId(id);
            if (tarea != null) {
                System.out.println("Tarea encontrada: " + tarea);
            } else {
                System.out.println("No se encontró ninguna tarea con el ID proporcionado.");
            }
            return tarea;
        }
        return null;
    }

    private void modificarTarea() {
        System.out.println("\n--- MODIFICAR TAREA ---");
        Tarea tarea = buscarPorId();
        if (tarea != null) {
            System.out.println("Introduce los nuevos datos (o vuelve a escribir los mismos si no cambian):");
            String nuevoTitulo = EntradaDatos.leerTexto(sc, "Introduce el nuevo título: ", ".+");
            int nuevaPrioridad = EntradaDatos.leerEntero(sc, "Introduce la nueva prioridad (1-5): ");

            if (nuevoTitulo != null && (nuevaPrioridad >= 1 && nuevaPrioridad <= 5)) {
                tareasController.modificarTarea(tarea, nuevoTitulo, nuevaPrioridad);
                System.out.println("Tarea modificada correctamente.");
            } else {
                System.out.println("Datos inválidos. Modificación cancelada.");
            }
        }
    }

    private void listarTareas() {
        System.out.println("\n--- LISTADO DE TAREAS ---");
        List<Tarea> tareas = tareasController.listarTareas();

        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
        } else {
            for (Tarea t : tareas) {
                System.out.println(t);
            }
        }
    }

    private void buscarPorTitulo() {
        System.out.println("\n--- BUSCAR TAREAS POR TÍTULO ---");
        String titulo = EntradaDatos.leerTexto(sc, "Introduce el título (o palabra clave) a buscar: ", ".+");
        if (titulo != null) {
            List<Tarea> tareas = tareasController.buscarPorTitulo(titulo);
            if (tareas.isEmpty()) {
                System.out.println("No se encontraron tareas con ese título.");
            } else {
                for (Tarea t : tareas) {
                    System.out.println(t);
                }
            }
        }
    }

    private void buscarPorPrioridad() {
        System.out.println("\n--- BUSCAR TAREAS POR PRIORIDAD ---");
        int prioridad = EntradaDatos.leerEntero(sc, "Introduce la prioridad a buscar (1-5): ");
        if (prioridad != -1) {
            List<Tarea> tareas = tareasController.buscarPorPrioridad(prioridad);
            if (tareas.isEmpty()) {
                System.out.println("No se encontraron tareas con esa prioridad.");
            } else {
                for (Tarea t : tareas) {
                    System.out.println(t);
                }
            }
        }
    }

    private void buscarPorEstado() {
        System.out.println("\n--- BUSCAR TAREAS POR ESTADO ---");
        int estadoInput = EntradaDatos.leerEntero(sc, "Introduce el estado a buscar (1 para Completada, 0 para Pendiente): ");
        if (estadoInput != -1) {
            boolean estado = (estadoInput == 1);
            List<Tarea> tareas = tareasController.buscarPorEstado(estado);
            if (tareas.isEmpty()) {
                System.out.println("No se encontraron tareas en ese estado.");
            } else {
                for (Tarea t : tareas) {
                    System.out.println(t);
                }
            }
        }
    }

    private void buscarPorFechaCreacion() {
        System.out.println("\n--- BUSCAR TAREAS POR FECHA ---");
        String fecha = EntradaDatos.leerTexto(sc, "Introduce la fecha a buscar (YYYY-MM-DD): ", "^\\d{4}-\\d{2}-\\d{2}$");
        if (fecha != null) {
            List<Tarea> tareas = tareasController.buscarPorFechaCreacion(fecha);
            if (tareas.isEmpty()) {
                System.out.println("No se encontraron tareas creadas en esa fecha.");
            } else {
                for (Tarea t : tareas) {
                    System.out.println(t);
                }
            }
        }
    }
}