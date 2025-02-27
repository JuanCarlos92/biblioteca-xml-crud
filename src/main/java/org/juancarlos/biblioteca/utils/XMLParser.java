package org.juancarlos.biblioteca.utils;

import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

            NodeList nodeList = doc.getElementsByTagName("libro");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Obtener el ID como atributo
                    int id = Integer.parseInt(element.getAttribute("id"));
                    String titulo = getTagValue("titulo", element);
                    String autor = getTagValue("autor", element);
                    int anio = Integer.parseInt(getTagValue("anio", element));
                    String genero = getTagValue("genero", element);

                    libros.add(new Libro(id, titulo, autor, anio, genero));
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
