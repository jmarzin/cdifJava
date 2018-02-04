/*
 * Copyright (c) 2018 DGFiP - Tous droits réservés
 */
package jmarzin.cdif;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

/**
 * Class AppliVisu : représente l'application de consultation du cadastre.
 */
public class AppliVisu
{
    
    /**
     * methode Ferme : tue la tâche de consultation du cadastre.
     */
    public void ferme()
    {
        try
        {
            Runtime.getRuntime().exec("Taskkill /IM VisDGI.exe /F");
        }
        catch (IOException e){}        
    }
    
    /**
     * methode Est fermee : indique si l'application de consultation du cadastre
     * est ouverte.
     *
     * @return true, si c'est vrai
     */
    public boolean estFermee()
    {
        String line = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("tasklist /fi \"imagename eq VisDGI.exe\"");
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String sline;
            while((sline=input.readLine()) != null) {
                line += sline + "\n";
            }
            pr.waitFor();
        } catch(Exception e) {
            new SortieSurErreur(e, "Une erreur inattendue est survenue");
        }
        return line.contains(": aucune t");
    }
    
    /**
     * Instanciation de appli visu. La création de l'objet copie de fichier d'identification
     * dans le répertoire d'accueil de l'utilisateur et lance l'application de consultation.
     *
     * @param parametres : paramètres de la consultation tels qu'ils figurent
     * dans le fichier visuDGFiP.params
     */
    AppliVisu(Parametres parametres)
    {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try 
        {
            sourceChannel = new FileInputStream(parametres.getRepInstal() + "\\calls.cmd").getChannel();
            destChannel = new FileOutputStream(parametres.getRepUtil() + "\\call.cmd").getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }
        catch (FileNotFoundException e)
        {
            new SortieSurErreur(e, "Fichier " + parametres.getRepUtil() + "\\calls.cmd absent");
        }
        catch (IOException e)
        {
            new SortieSurErreur(e, "Problème à la copie du fichier calls.cmd");
        }
        finally
        {
            try
            {
                sourceChannel.close();
                destChannel.close();
            }
            catch (IOException e){}
        }
        try
        {
            Runtime.getRuntime().exec(parametres.getProgramme() + " " + parametres.getRepUtil() + "\\call.cmd");
        }
        catch (IOException e)
        {
            new SortieSurErreur(e, "Problème au lancement du programme");
        }
    }
}
