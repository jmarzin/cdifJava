/*
 * Copyright (c) 2018 DGFiP - Tous droits réservés
 */
package jmarzin.cdif;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;

/**
 * Class SortieSurErreur gère la sortie du programme après affichage de
 * l'erreur et de l'exception concernée.
 */
public class SortieSurErreur
{
    
    /**
     * Instanciation de sortie sur erreur : affiche la fenêtre d'erreur et ferme
     * l'application.
     *
     * @param e : exception levée
     * @param message : message d'erreur à affichier
     */
    SortieSurErreur(Exception e, String message)
    {
        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        writer.print(message + "\n");
        e.printStackTrace(writer);
        JOptionPane.showMessageDialog(null, out.toString());
        System.exit(-1);
    }
}