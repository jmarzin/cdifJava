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

import javax.swing.JOptionPane;

public class LecteurCodes
{
    private String fichierCodes;
    private List<String> listeCodes = new ArrayList<String>();
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
