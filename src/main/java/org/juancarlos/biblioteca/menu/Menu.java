package org.juancarlos.biblioteca.menu;

import org.juancarlos.biblioteca.controller.BibliotecaController;
import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;

import java.util.Scanner;

public class Menu {

    // Inicia la ejecución del menú principal
    public void iniciar() throws RepositoryException {
        Scanner sc = new Scanner(System.in);
        BibliotecaRepository repository = new BibliotecaRepository();
        BibliotecaController bibliotecaController = new BibliotecaController(sc, repository);

        boolean salir = false;

        while (!salir) {
            EnumMenu.mostrarOpcionesMenu();
            EnumMenu opcionPrincipal = validarOpcionPrincipal(sc);

            if (opcionPrincipal == null) {
                System.out.println("Opción no válida.");
                continue;
            }

            switch (opcionPrincipal) {
                case CREAR_LIBRO:
                    crearLibro(sc, bibliotecaController);
                    break;
                case CONSULTAR_LIBROS:
                    consultarLibros(bibliotecaController);
                    break;
                case ELIMINAR_LIBRO:
                    eliminarLibro(sc, bibliotecaController);
                    break;
                case ACTUALIZAR_LIBRO:
                    actualizarLibro(sc, bibliotecaController);
                    break;
                case SALIR:
                    salir = true;
                    break;
            }
        }
        sc.close();
    }

    // Solicita al usuario que seleccione una opción del menú principal
    private EnumMenu validarOpcionPrincipal(Scanner sc) {
        System.out.print("Selecciona una opción: ");
        int codigo = leerEntero(sc);
        return EnumMenu.obtenerPorCodigo(codigo);
    }

    // Lee un valor entero introducido por el usuario mediante Scanner.
    private int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Ingrese un número válido:");
            sc.next();
        }
        return sc.nextInt();
    }

    // Crear un nuevo libro
    private void crearLibro(Scanner sc, BibliotecaController bibliotecaController) {
        System.out.println("Crear un nuevo libro");

        System.out.print("Introduce el título del libro: ");
        String titulo = sc.nextLine();

        System.out.print("Introduce el autor del libro: ");
        String autor = sc.nextLine();

        System.out.print("Introduce el año de publicación: ");
        int anio = leerEntero(sc);

        System.out.print("Introduce el género del libro: ");
        String genero = sc.nextLine();

        System.out.print("Introduce el precio del libro: ");
        double precio = sc.nextDouble();
        sc.nextLine(); // Limpiar el buffer

        bibliotecaController.crearLibro(titulo, autor, anio, genero, precio);
    }

    // Consultar todos los libros
    private void consultarLibros(BibliotecaController bibliotecaController) {
        System.out.println("Consultando todos los libros:");
        bibliotecaController.consultarLibros();
    }

    // Eliminar un libro por ID
    private void eliminarLibro(Scanner sc, BibliotecaController bibliotecaController) {
        System.out.print("Introduce el ID del libro a eliminar: ");
        int id = leerEntero(sc);
        bibliotecaController.eliminarLibro(id);
    }

    // Actualizar un libro
    private void actualizarLibro(Scanner sc, BibliotecaController bibliotecaController) {
        System.out.print("Introduce el ID del libro a actualizar: ");
        int id = leerEntero(sc);
        System.out.println("Actualizando libro con ID: " + id);

        System.out.print("Introduce el nuevo título (o deja vacío para no cambiarlo): ");
        String titulo = sc.nextLine();

        System.out.print("Introduce el nuevo autor (o deja vacío para no cambiarlo): ");
        String autor = sc.nextLine();

        System.out.print("Introduce el nuevo año (o deja vacío para no cambiarlo): ");
        String anioInput = sc.nextLine();
        Integer anio = anioInput.isEmpty() ? null : Integer.parseInt(anioInput);

        System.out.print("Introduce el nuevo género (o deja vacío para no cambiarlo): ");
        String genero = sc.nextLine();

        System.out.print("Introduce el nuevo precio (o deja vacío para no cambiarlo): ");
        String precioInput = sc.nextLine();
        Double precio = precioInput.isEmpty() ? null : Double.parseDouble(precioInput);

        bibliotecaController.actualizarLibro(id, titulo, autor, anio, genero, precio);
    }
}
