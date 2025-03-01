package org.juancarlos.biblioteca.conexion;

import org.basex.api.client.ClientSession;
import org.juancarlos.biblioteca.exception.RepositoryException;

/**
 * Clase para gestionar la conexión con la base de datos BaseX.
 */
public class BaseXConnection {

    /**
     * Establece y devuelve una nueva sesión de conexión con BaseX.
     *
     * @return Una instancia de {@link ClientSession} con la conexión activa.
     * @throws RepositoryException Si ocurre un error al conectar con BaseX.
     */
    public static ClientSession obtenerConexion() throws RepositoryException {
        try {
            // Establecer conexión con BaseX, ajustar los parámetros según sea necesario
            return new ClientSession("localhost", 1984, "user", "user");
        } catch (Exception e) {
            throw new RepositoryException("Error al conectar con la base de datos BaseX", e);
        }
    }
}
