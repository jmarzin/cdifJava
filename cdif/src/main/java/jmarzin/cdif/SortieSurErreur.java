package jmarzin.cdif;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

public class SortieSurErreur
{
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