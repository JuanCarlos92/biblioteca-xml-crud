package org.juancarlos.biblioteca.utils;

import org.juancarlos.biblioteca.model.Libro;

import java.util.List;

public class QueryBuilder {
    public static String getInsertLibro(Libro libro, String resultadoId) {
        int ultimoId = (resultadoId.isEmpty()) ? 0 : Integer.parseInt(resultadoId.trim());
        int nuevoId = ultimoId + 1;

        return "XQUERY insert node " +
                " <libro id='" + nuevoId + "'>" +
                " <titulo>" + libro.getTitulo() + "</titulo>" +
                " <autor>" + libro.getAutor() + "</autor>" +
                " <anio>" + libro.getAnio() + "</anio>" +
                " </libro> into //biblioteca/genero[@tipo='" + libro.getGenero() + "']";

    }

    public static void getListLibros(List<Libro> libros) {
        for (Libro libro : libros) {
            System.out.println("ID: " + libro.getId());
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Año: " + libro.getAnio());
            System.out.println("Género: " + libro.getGenero());
            System.out.println("---------------------------------");
        }
    }

    public static String getListColeccion() {
        return "XQUERY for $g in //biblioteca/genero return data($g/@tipo)";
    }

    public static String getVerificarGenero(String tipoGenero) {
        return "XQUERY count(//genero[@tipo='" + tipoGenero + "'])";
    }

    public static String getInsertColeccion(String tipoGenero) {
        return "XQUERY insert node <genero tipo='" + tipoGenero + "'/> into //biblioteca";
    }

    public static String getDeleteColeccion(String tipoGenero) {
        return "XQUERY delete node collection('biblioteca')/biblioteca/genero[@tipo = '" + tipoGenero + "']";
    }

    public static String getIDLibro() {
        return "XQUERY max(for $v in collection('biblioteca')/biblioteca/genero/libro/@id return xs:int($v))";
    }

    public static String getAllBiblioteca() {
        return "XQUERY for $v in collection('biblioteca') return $v";
    }

    public static String getQueryTitulo(String titulo) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//genero/libro " +
                "where $v/titulo = '" + titulo + "' " +
                "return <genero tipo='{$v/../@tipo}'>{$v}</genero> " +
                "}</biblioteca>";
    }

    public static String getQueryAutor(String autor) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//genero/libro " +
                "where $v/autor = '" + autor + "' " +
                "return <genero tipo='{$v/../@tipo}'>{$v}</genero> " +
                "}</biblioteca>";
    }

    public static String getQueryAnioMayor(int anio) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//genero/libro " +
                "where $v/anio > " + anio +
                " return <genero tipo='{$v/../@tipo}'>{$v}</genero> " +
                "}</biblioteca>";
    }

    public static String getQueryAnioMenor(int anio) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//genero/libro " +
                "where $v/anio < " + anio +
                " return <genero tipo='{$v/../@tipo}'>{$v}</genero> " +
                "}</biblioteca>";
    }

    public static String getActualizarLibro(int id, String titulo, String autor, int anio) {
        return "for $libro in collection('biblioteca')//libro " +
                "where $libro/@id = '" + id + "' " +
                "return (" +
                "  replace value of node $libro/titulo with '" + titulo + "', " +
                "  replace value of node $libro/autor with '" + autor + "', " +
                "  replace value of node $libro/anio with " + anio + " " +
                ")";
    }

    public static String getEliminarLibro(int id) {
        return "XQUERY delete node collection('biblioteca')//libro[@id = '" + id + "']";
    }

}
