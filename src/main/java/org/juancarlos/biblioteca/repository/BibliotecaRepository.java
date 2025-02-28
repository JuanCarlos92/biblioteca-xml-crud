package org.juancarlos.biblioteca.repository;

import org.basex.api.client.ClientSession;
import org.juancarlos.biblioteca.conexion.BaseXConnection;
import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;

import javax.swing.*;

import org.juancarlos.biblioteca.utils.QueryBuilder;
import org.juancarlos.biblioteca.utils.XMLParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.juancarlos.biblioteca.ui.FIleChooser.getJFileChooser;

public class BibliotecaRepository {

    private static final String COLLECTION_BASE = "biblioteca";
    private final ClientSession SESSION;

    // Constructor
    public BibliotecaRepository() throws RepositoryException {
        try {
            // Obtener la sesión de BaseX desde la clase BaseXConnection
            this.SESSION = BaseXConnection.obtenerConexion();
            // Abrir la colección
            SESSION.execute("OPEN " + COLLECTION_BASE);
        } catch (Exception e) {
            throw new RepositoryException("Error al conectar con la base de datos " + COLLECTION_BASE, e);
        }
    }

    // Crear coleccion
    public void crearColeccion(String tipoGenero) throws RepositoryException {
        try {
            String queryVerificar = QueryBuilder.getVerificarGenero(tipoGenero);
            String resultado = SESSION.execute(queryVerificar);

            if (Integer.parseInt(resultado.trim()) > 0) {
                System.out.println("La colección ya existe");
                return;
            }
            String queryInsertColeccion = QueryBuilder.getInsertColeccion(tipoGenero);
            SESSION.execute(queryInsertColeccion);
            System.out.println("✅ ✅ Colección creada con éxito! ✅ ✅ ");
        } catch (Exception e) {
            throw new RepositoryException("Error al crear la nueva coleccion", e);
        }
    }

    // Eliminar coleccion
    public void eliminarColeccion(String tipoGenero) throws RepositoryException {
        try {
            String queryVerificar = QueryBuilder.getVerificarGenero(tipoGenero);
            String resultado = SESSION.execute(queryVerificar);

            if (Integer.parseInt(resultado.trim()) == 0) {
                System.out.println("Esta colección no existe");
                return;
            }
            String queryDeleteColeccion = QueryBuilder.getDeleteColeccion(tipoGenero);
            SESSION.execute(queryDeleteColeccion);
            System.out.println("✅ ✅ Colección eliminada con éxito! ✅ ✅ ");
        } catch (Exception e) {
            throw new RepositoryException("Error al eliminar la coleccion", e);
        }
    }

    public void consultarColecciones() throws RepositoryException {
        try {
            String query = QueryBuilder.getListColeccion();
            String resultado = SESSION.execute(query);

            // Verificar si el resultado no está vacío
            if (resultado != null && !resultado.trim().isEmpty()) {
                // Convertir el resultado en una lista separando por saltos de línea o espacios
                List<String> generos = new ArrayList<>(Arrays.asList(resultado.split("\n")));

                System.out.println("📖  LISTA DE COLECCIONES  📖");
                // Mostrar los géneros en consola numerados
                int index = 1;
                for (String genero : generos) {
                    System.out.println(index + ". " + genero.trim());
                    index++;
                }
            } else {
                System.out.println("No se encontraron géneros en la biblioteca.");
            }

        } catch (Exception e) {
            throw new RepositoryException("Error al consultar las colecciones", e);
        }

    }

    // Crear libro con fichero file.xml
    public void cargarYAgregarLibroXML(String tipoGenero) throws RepositoryException, IOException {
        String queryVerificar = QueryBuilder.getVerificarGenero(tipoGenero);
        String resultado = SESSION.execute(queryVerificar);

        if (Integer.parseInt(resultado.trim()) == 0) {
            System.out.println("Esta colección no existe, primero crea la coleccion con ese género");
            return;
        }

        // Si el género existe o se crea, entonces proceder a mostrar el JFileChooser
        JFileChooser fileChooser = getJFileChooser();

        int resultadoFile = fileChooser.showOpenDialog(null);
        if (resultadoFile == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            System.out.println("📂 Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());

            try {
                // Leer contenido del archivo seleccionado
                String contenidoXML = new String(Files.readAllBytes(archivoSeleccionado.toPath()));

                // Asegurar que el XML está dentro de <biblioteca>
                // String query = "<biblioteca><genero tipo='"+ tipoGenero +"'>" + contenidoXML + "</genero></biblioteca>";

                // Obtener el ID máximo actual
                String resultadoId = SESSION.execute(QueryBuilder.getIDLibro());
                int ultimoId = resultadoId.trim().isEmpty() ? 0 : Integer.parseInt(resultadoId.trim());
                ultimoId ++;

                // Asignar ID a cada libro dentro del XML
                contenidoXML = agregarIdAContenidoXML(contenidoXML, ultimoId);

                SESSION.execute("XQUERY insert node " + contenidoXML + "into //biblioteca/genero[@tipo='" + tipoGenero + "']");

                System.out.println("✅ Archivo XML añadido correctamente a la base de datos.");

            } catch (Exception e) {
                throw new RepositoryException("Error al leer el archivo XML", e);

            }
        } else {
            System.out.println("⚠️ No se seleccionó ningún archivo.");
        }
    }

    // Añadir el ID al contenido XML antes de parsearlo
    private String agregarIdAContenidoXML(String contenidoXML, int ultimoId) {
        contenidoXML = contenidoXML.replaceAll("(<libro>)", "<libro id=\"" + ultimoId + "\">");

        return contenidoXML;
    }

    // Crear libro
    public void crearLibro(Libro libro, String tipoGenero) throws RepositoryException {
        try {
            String queryVerificar = QueryBuilder.getVerificarGenero(tipoGenero);
            String resultado = SESSION.execute(queryVerificar);

            if (Integer.parseInt(resultado.trim()) == 0) {
                System.out.println("Esta colección no existe, primero crea la coleccion con ese género");
                return;
            }
            // Obtener el último id del XML
            String queryId = QueryBuilder.getIDLibro();
            String resultadoId = SESSION.execute(queryId);

            String query = QueryBuilder.getInsertLibro(libro, resultadoId);
            SESSION.execute(query);
            System.out.println("✅ ✅ Libro creado con éxito! ✅ ✅ ");

        } catch (Exception e) {
            throw new RepositoryException("Error al insertar el libro en la base de datos", e);
        }
    }

    // Mostrar libros
    public void consultarLibros() throws RepositoryException {
        try {
            String query = QueryBuilder.getAllBiblioteca();
            String resultado = SESSION.execute(query);

            // Procesar el XML resultante y lo convierte en una lista de objetos Libro
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListLibros(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta de libros", e);
        }
    }

    // Mostrar libros por titulo
    public void consultarLibrosTitulo(String buscarTitulo) throws RepositoryException {
        try {
            String query = QueryBuilder.getQueryTitulo(buscarTitulo);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListLibros(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por titulo", e);
        }
    }

    // Mostrar libros por Autor
    public void consultarLibrosAutor(String buscarAutor) throws RepositoryException {
        try {
            String query = QueryBuilder.getQueryAutor(buscarAutor);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListLibros(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por autor", e);
        }
    }

    // Filtrar libros por anio
    public void filtrarLibrosAnio(int anio, String mayorOMenor) throws RepositoryException {
        try {
            String query;
            if (mayorOMenor.equalsIgnoreCase("mayor")) {
                query = QueryBuilder.getQueryAnioMayor(anio);
            } else {
                query = QueryBuilder.getQueryAnioMenor(anio);
            }
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListLibros(libros);
        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por año", e);
        }
    }

    public List<Libro> consultarNombreLibro() throws RepositoryException {
        try {
            String query = QueryBuilder.getAllBiblioteca();
            String resultado = SESSION.execute(query);
            return XMLParser.parsearLibrosXML(resultado);
        } catch (IOException e) {
            throw new RepositoryException("Error al obtener la lista de libros", e);
        }
    }

    public void actualizarLibro(Libro libro) throws RepositoryException {
        try {
            String query = QueryBuilder.getActualizarLibro(libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getAnio());
            SESSION.execute("XQUERY " + query);

        } catch (IOException e) {
            throw new RepositoryException("Error al actualizar el libro", e);
        }
    }

    public void eliminarLibro(Libro libro) throws RepositoryException {
        try {
            String query = QueryBuilder.getEliminarLibro(libro.getId());
            SESSION.execute(query);
        } catch (IOException e) {
            throw new RepositoryException("Error al eliminar el libro", e);
        }
    }
}


