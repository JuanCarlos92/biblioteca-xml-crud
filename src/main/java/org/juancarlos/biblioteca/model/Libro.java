package org.juancarlos.biblioteca.model;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anio;
    private String genero;

    // Constructor
    public Libro(int id, String titulo, String autor, int anio, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.genero = genero;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    // Convertir el objeto a un formato XML
    public String toXml() {
        return "<libro id=\"" + id + "\">" +
                "<titulo>" + titulo + "</titulo>" +
                "<autor>" + autor + "</autor>" +
                "<anio>" + anio + "</anio>" +
                "<genero>" + genero + "</genero>" +
                "</libro>";
    }
}
