package org.juancarlos.biblioteca.service;

import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;

import java.util.List;
import java.util.Scanner;

public class BibliotecaService {
    private BibliotecaRepository repository;

    // Constructor con inyección de dependencia
    public BibliotecaService(BibliotecaRepository repository) {
        this.repository = repository;
    }

    // Crear un libro
    public void crearLibro(Scanner sc) {
        System.out.println("📖  AÑADIR LIBROS  📖");

        System.out.print("Introduce el título del libro: ");
        String titulo = sc.nextLine();

        System.out.print("Introduce el autor del libro: ");
        String autor = sc.nextLine();

        int anio = 0;
        anio = getAnio(sc, anio);

        System.out.print("Introduce el género del libro: ");
        String genero = sc.nextLine();

        Libro libro = new Libro(0, titulo, autor, anio, genero);

        try {
            repository.crearLibro(libro);
            System.out.println("✅ ✅ Libro creado con éxito! ✅ ✅ ");
        } catch (Exception e) {
            System.err.println("❌ Error al crear el libro: " + e.getMessage());
        }
    }

    // Consultar todos los libros
    public void consultarLibros() {
        try {
            System.out.println("📖  LISTA DE LIBROS  📖");
            repository.consultarLibros();

        } catch (RepositoryException e) {
            System.err.println("❌ Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
        }
    }

    // Consultar libros por titulo
    public void consultarLibrosTitulo(Scanner sc) {
        try {
            System.out.print("Introduce el título del libro a buscar: ");
            String buscarTitulo = sc.nextLine();

            System.out.println("📖  LISTA DE LIBROS POR TÍTULO 📖");
            repository.consultarLibrosTitulo(buscarTitulo);

        } catch (RepositoryException e) {
            System.err.println("❌ Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
        }
    }

    // Consultar libros por autor
    public void consultarLibrosAutor(Scanner sc) {
        try {
            System.out.print("Introduce el autor del libro a buscar: ");
            String buscarAutor = sc.nextLine();

            System.out.println("📖  LISTA DE LIBROS POR AUTOR 📖");
            repository.consultarLibrosAutor(buscarAutor);

        } catch (RepositoryException e) {
            System.err.println("❌ Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
        }
    }

    // Consultar libros por genero
    public void consultarLibrosGenero(Scanner sc) {
        try {
            System.out.print("Introduce el género del libro a buscar: ");
            String buscarGenero = sc.nextLine();

            System.out.println("📖  LISTA DE LIBROS POR GENERO 📖");
            repository.consultarLibrosGenero(buscarGenero);

        } catch (RepositoryException e) {
            System.err.println("❌ Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
        }
    }

    //filtrar libros por anio
    public void filtrarLibrosAnio(Scanner sc) {
        int anio = 0;
        anio = getAnio(sc, anio);
        System.out.print("¿Quieres mostrar los libros mayor a esa fecha o menor?: ");
        String mayorOMenor = sc.nextLine();

        try {
            System.out.println("📖  LISTA DE LIBROS CON EL FILTRO APLICADO 📖");
            repository.filtrarLibrosAnio(anio, mayorOMenor);

        } catch (Exception e) {
            System.err.println("❌ Error al aplicar el filtro indicado: " + e.getMessage());
        }
    }

    //actualizar libro
    public void actualizarLibro(Scanner sc) {
        try {
            // Obtener y mostrar los libros disponibles
            List<Libro> libros = repository.consultarlibroParaActualizar();
            if (libros.isEmpty()) {
                System.out.println("⚠️ No hay libros disponibles.");
                return;
            }

            System.out.println("📚 LISTA DE LIBROS DISPONIBLES 📚");
            for (Libro libro : libros) {
                System.out.println("ID: " + libro.getId() + " | Título: " + libro.getTitulo());
            }

            // Pedir el ID del libro a actualizar
            System.out.print("Ingrese el ID del libro a actualizar: ");
            int idLibro = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer del scanner

            // Buscar el libro
            Libro libroActualizar = libros.stream()
                    .filter(libro -> libro.getId() == idLibro)  // Aquí comparamos directamente como enteros
                    .findFirst()
                    .orElse(null);

            if (libroActualizar == null) {
                System.out.println("❌ No se encontró un libro con el ID proporcionado.");
                return;
            }

            // Pedir los datos a actualizar
            System.out.println("📖 ACTUALIZAR LIBRO (Deja en blanco para no modificar)");

            System.out.print("Nuevo título: ");
            String nuevoTitulo = sc.nextLine().trim();
            if (nuevoTitulo.isEmpty()) nuevoTitulo = libroActualizar.getTitulo();

            System.out.print("Nuevo autor: ");
            String nuevoAutor = sc.nextLine().trim();
            if (nuevoAutor.isEmpty()) nuevoAutor = libroActualizar.getAutor();

            System.out.print("Nuevo año: ");
            String nuevoAnioStr = sc.nextLine().trim();
            int nuevoAnio = nuevoAnioStr.isEmpty() ? libroActualizar.getAnio() : Integer.parseInt(nuevoAnioStr);

            System.out.print("Nuevo género: ");
            String nuevoGenero = sc.nextLine().trim();
            if (nuevoGenero.isEmpty()) nuevoGenero = libroActualizar.getGenero();

            // Crear un nuevo objeto Libro con los datos actualizados
            Libro libroModificado = new Libro(
                    libroActualizar.getId(),
                    nuevoTitulo,
                    nuevoAutor,
                    nuevoAnio,
                    nuevoGenero
            );
            repository.actualizarLibro(libroModificado);

            System.out.println("✅ Libro actualizado correctamente.");

        } catch (Exception e) {
            System.err.println("❌ Error al actualizar el libro: " + e.getMessage());
        }
    }

    public void eliminarLibro(Scanner sc) {
        try {
            // Obtener y mostrar los libros disponibles
            List<Libro> libros = repository.consultarlibroParaActualizar();
            if (libros.isEmpty()) {
                System.out.println("⚠️ No hay libros disponibles.");
                return;
            }

            System.out.println("📚 LISTA DE LIBROS DISPONIBLES 📚");
            for (Libro libro : libros) {
                System.out.println("ID: " + libro.getId() + " | Título: " + libro.getTitulo());
            }

            // Pedir el ID del libro a eliminar
            System.out.print("Ingrese el ID del libro a eliminar: ");
            while (!sc.hasNextInt()) {
                System.out.println("❌ Entrada inválida. Introduzca un número válido.");
                sc.next(); // Limpiar entrada incorrecta
            }
            int idLibro = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer del scanner

            // Buscar el libro
            Libro libroEliminar = libros.stream()
                    .filter(libro -> libro.getId() == idLibro)
                    .findFirst()
                    .orElse(null);

            if (libroEliminar == null) {
                System.out.println("❌ No se encontró un libro con el ID proporcionado.");
                return;
            }

            // Confirmación antes de eliminar
            System.out.println("📖 ¿Está seguro de que desea eliminar el libro: " + libroEliminar.getTitulo() + "? (S/N)");
            String confirmacion = sc.nextLine().trim().toLowerCase();

            if (!confirmacion.equals("s")) {
                System.out.println("🚫 Operación cancelada.");
                return;
            }

            // Eliminar libro
            repository.eliminarLibro(libroEliminar);
            System.out.println("✅ Libro eliminado con éxito.");

        } catch (Exception e) {
            System.err.println("❌ Error al eliminar el libro: " + e.getMessage());
        }
    }


    private int getAnio(Scanner sc, int anio) {
        boolean anioValido = false;
        while (!anioValido) {
            System.out.print("Introduce el año de publicación (máximo 4 cifras): ");
            if (sc.hasNextInt()) {
                anio = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea
                if (anio > 0 && anio < 10000) {
                    anioValido = true;
                } else {
                    System.out.println("❌ Error: El año debe tener como máximo 4 cifras");
                }
            } else {
                System.out.println("❌ Error: Debes ingresar un número válido.");
                sc.next(); // Descartar la entrada incorrecta
            }
        }
        return anio;
    }
}
