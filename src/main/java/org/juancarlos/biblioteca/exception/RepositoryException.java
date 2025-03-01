package org.juancarlos.biblioteca.exception;

/**
 * Excepción personalizada utilizada para manejar errores específicos relacionados con el repositorio de datos.
 * Esta clase extiende {@link Exception} y permite encapsular errores que ocurren en el contexto de la gestión de datos.
 * --
 * Puede usarse para proporcionar información detallada sobre problemas durante el acceso a los datos,
 * como al procesar archivos XML o al interactuar con la base de datos.
 */
public class RepositoryException extends Exception {

    /**
     * Constructor que crea una nueva instancia de {@link RepositoryException} con un mensaje de error específico
     * y la causa que provocó el error.
     *
     * @param message El mensaje de error que describe el motivo de la excepción.
     * @param cause   La causa subyacente de la excepción (un {@link Throwable} que causó este error).
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
