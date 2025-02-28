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

public class XMLParser {

    // Convertir XML a lista de objetos Libro
    public static List<Libro> parsearLibrosXML(String xml) throws RepositoryException {
        xml = xml.replaceAll("[\n" + "\r" + "\t]", "").trim();
        List<Libro> libros = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);

            NodeList generos = doc.getElementsByTagName("genero");
            for (int i = 0; i < generos.getLength(); i++) {
                Element generoElement = (Element) generos.item(i);
                String tipoGenero = generoElement.getAttribute("tipo");

                // Obtener solo los libros dentro del género actual
                NodeList librosNodos = generoElement.getElementsByTagName("libro");
                for (int j = 0; j < librosNodos.getLength(); j++) {
                    Element libroElement = (Element) librosNodos.item(j);

                    int id = Integer.parseInt(libroElement.getAttribute("id"));
                    String titulo = getTagValue("titulo", libroElement);
                    String autor = getTagValue("autor", libroElement);
                    int anio = Integer.parseInt(getTagValue("anio", libroElement));

                    // Agregar libro al listado con el género correspondiente
                    libros.add(new Libro(id, titulo, autor, anio, tipoGenero));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RepositoryException("Error al procesar el XML de los libros", e);
        }
        return libros;
    }


    // Función para obtener el valor de una etiqueta por nombre
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
