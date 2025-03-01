package org.juancarlos.biblioteca.ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Clase para crear un {@link JFileChooser} configurado para seleccionar archivos XML.
 * --
 * Esta clase proporciona un methods estático que devuelve una instancia de {@link JFileChooser}
 * configurada con un título específico, un filtro de archivos para solo permitir archivos XML
 * y un modo de selección de solo archivos.
 */
public class FIleChooser {
    /**
     * Crea y configura un {@link JFileChooser} para seleccionar archivos XML.
     *
     * @return Una instancia de {@link JFileChooser} configurada con el filtro de archivos XML.
     */
    public static JFileChooser getJFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo XML");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos XML", "xml"));
        return fileChooser;
    }
}
