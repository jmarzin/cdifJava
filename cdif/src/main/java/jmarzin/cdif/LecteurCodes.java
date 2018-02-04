/*
 * Copyright (c) 2018 DGFiP - Tous droits réservés
 */
package jmarzin.cdif;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class LecteurCodes : gère les codes de session.
 */
public class LecteurCodes
{
    
    /** fichier codes. */
    private String fichierCodes;
    
    /** liste codes. */
    private List<String> listeCodes = new ArrayList<String>();
    
    /**
     * methode Consomme : vérifie si le code figure parmi les cent premiers codes
     * du fichier des codes. Si le code est trouvé, il est supprimé de la liste 
     * et du fichier des codes.
     *
     * @param code : code fourni par l'utilisateur
     * @return true, si le code est valide
     */
    public boolean consomme(String code)
    {
        int index = listeCodes.indexOf(code);
        if(index >= 0 && index < 100)
        {
            listeCodes.remove(index);
            try {
                FileOutputStream outputStream = new FileOutputStream(fichierCodes);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                for(String ligne : listeCodes)
                {
                    bufferedWriter.write(ligne);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            } catch (IOException e) {
                new SortieSurErreur(e, "Erreur à l'écriture du fichier des codes");
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Instanciation de lecteur codes.
     *
     * @param fichierCodes : nom du fichier des codes
     */
    LecteurCodes(String fichierCodes)
    {
        this.fichierCodes = fichierCodes;
        BufferedReader br = null;
        try
        {
            FileInputStream is = new FileInputStream(fichierCodes);
            InputStreamReader isr = new InputStreamReader(is);
            br = null;
            br = new BufferedReader(isr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                listeCodes.add(sCurrentLine);
            }
        }
        catch (Exception e)
        {
            new SortieSurErreur(e ,"Fichier des codes " + fichierCodes + " absent ou corrompu");
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (IOException e){}
        }

    }
}
