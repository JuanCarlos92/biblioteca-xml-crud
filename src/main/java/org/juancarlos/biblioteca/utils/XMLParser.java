package org.juancarlos.biblioteca.utils;

import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para analizar archivos XML y convertirlos en objetos Java.
 * En particular, se utiliza para convertir un XML de libros en una lista de objetos {@link Libro}.
 */
public class XMLParser {

    /**
     * Convierte una cadena XML que contiene una lista de libros en una lista de objetos {@link Libro}.
     * Los libros están organizados por géneros dentro del XML.
     *
     * @param xml La cadena XML que contiene la información de los libros.
     * @return Una lista de objetos {@link Libro} con la información extraída del XML.
     * @throws RepositoryException Si ocurre algún error al procesar el XML.
     */
    public static List<Libro> parsearLibrosXML(String xml) throws RepositoryException {
        // Elimina saltos de línea, retorno de carro y tabulaciones del XML
        xml = xml.replaceAll("[\n" + "\r" + "\t]", "").trim();
        List<Libro> libros = new ArrayList<>();

        try {
            // Crea una fábrica y un constructor de documentos para analizar el XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);

            // Obtiene todos los elementos de género en el XML
            NodeList generos = doc.getElementsByTagName("genero");
            for (int i = 0; i < generos.getLength(); i++) {
                Element generoElement = (Element) generos.item(i);
                String tipoGenero = generoElement.getAttribute("tipo");

                // Obtener solo los libros dentro del género actual
                NodeList librosNodos = generoElement.getElementsByTagName("libro");
                for (int j = 0; j < librosNodos.getLength(); j++) {
                    Element libroElement = (Element) librosNodos.item(j);

                    // Extrae los datos del libro: id, título, autor y año
                    int id = Integer.parseInt(libroElement.getAttribute("id"));
                    String titulo = getTagValue("titulo", libroElement);
                    String autor = getTagValue("autor", libroElement);
                    int anio = Integer.parseInt(getTagValue("anio", libroElement));

                    // Crea un objeto Libro y lo agrega a la lista
                    libros.add(new Libro(id, titulo, autor, anio, tipoGenero));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RepositoryException("Error al procesar el XML de los libros", e);
        }
        return libros;
    }

    /**
     * Obtiene el valor de una etiqueta dentro de un elemento XML.
     *
     * @param tag     El nombre de la etiqueta cuya información se desea obtener.
     * @param element El elemento XML que contiene la etiqueta.
     * @return El valor de la etiqueta, o {@code null} si no se encuentra.
     */
    private static String getTagValue(String tag, Element element) {
        // Obtiene los nodos correspondientes al nombre de la etiqueta
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            // Retorna el contenido de la primera coincidencia
            return nodeList.item(0).getTextContent();
        }
        return null; // Si no hay etiqueta, retorna null
    }
}
