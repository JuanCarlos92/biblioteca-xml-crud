package org.juancarlos.biblioteca.ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FIleChooser {
    public static JFileChooser getJFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo XML");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos XML", "xml"));
        return fileChooser;
    }
}
