package org.juancarlos.biblioteca.conexion;

import org.basex.api.client.ClientSession;
import org.juancarlos.biblioteca.exception.RepositoryException;

public class BaseXConnection {

    // Metodo para obtener una nueva sesión de BaseX
    public static ClientSession obtenerConexion() throws RepositoryException {
        try {
            // Establecer conexión con BaseX, debes ajustar los parámetros si es necesario
            ClientSession session = new ClientSession("localhost", 1984, "user", "user");
            return session;
        } catch (Exception e) {
            throw new RepositoryException("Error al conectar con la base de datos BaseX", e);
        }
    }
}
