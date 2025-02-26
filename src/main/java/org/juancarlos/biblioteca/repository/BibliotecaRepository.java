package org.juancarlos.biblioteca.repository;

import org.basex.api.client.ClientSession;
import org.juancarlos.biblioteca.conexion.BaseXConnection;
import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.juancarlos.biblioteca.utils.QueryBuilder;
import org.juancarlos.biblioteca.utils.XMLParser;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaRepository {

    private static final String COLLECTION_NAME = "biblioteca";
    private final ClientSession SESSION;

    // Constructor
    public BibliotecaRepository() throws RepositoryException {
        try {
            // Obtener la sesión de BaseX desde la clase BaseXConnection
            this.SESSION = BaseXConnection.obtenerConexion();
            // Abrir la colección
            SESSION.execute("OPEN " + COLLECTION_NAME);
        } catch (Exception e) {
            throw new RepositoryException("Error al conectar con la base de datos " + COLLECTION_NAME, e);
        }
    }

    // Crear libro
    public void crearLibro(Libro libro) throws RepositoryException {
        try {
            // Obtener el último id del XML
            String queryId = QueryBuilder.getQuery();
            String resultadoId = SESSION.execute(queryId);
            String query = QueryBuilder.getInsertQuery(libro, resultadoId);
            SESSION.execute("XQUERY " + query);

        } catch (Exception e) {
            throw new RepositoryException("Error al insertar el libro en la base de datos", e);
        }
    }

    // Mostrar libros
    public void consultarLibros() throws RepositoryException {
        try {
            // Ejecuta una consulta XQUERY que obtiene todos los nodos de la colección
            String query = QueryBuilder.getQuerylibro();
            String resultado = SESSION.execute(query);

            // Procesar el XML resultante y lo convierte en una lista de objetos Libro
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta de libros", e);
        }
    }

    // Mostrar libros por titulo
    public void consultarLibrosTitulo(String buscarTitulo) throws RepositoryException {
        try {
            // Consulta XQuery para buscar libros cuyo título coincida
            String query = QueryBuilder.getQueryByTitulo(buscarTitulo);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por titulo", e);
        }
    }

    // Mostrar libros por Autor
    public void consultarLibrosAutor(String buscarAutor) throws RepositoryException {
        try {
            // Consulta XQuery para buscar libros cuyo autor coincida
            String query = QueryBuilder.getQueryByAutor(buscarAutor);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por autor", e);
        }
    }

    // Mostrar libros por Genero
    public void consultarLibrosGenero(String buscarGenero) throws RepositoryException {
        try {
            // Consulta XQuery para buscar libros cuyo autor coincida
            String query = QueryBuilder.getQueryByGeneno(buscarGenero);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por género", e);
        }
    }
    // Filtrar libros por anio
    public void filtrarLibrosAnio(int anio, String mayorOMenor) throws RepositoryException {
        try {
            String query;
            if (mayorOMenor.equalsIgnoreCase("mayor")) {
                query = QueryBuilder.getQueryByAnioMayor(anio);
            } else {
                query = QueryBuilder.getQueryByAnioMenor(anio);
            }
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);
        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por año", e);
        }
    }
}
