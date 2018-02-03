package jmarzin.cdif;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parametres
{
    String repInstal = "";
    public String getRepInstal()
    {
        return repInstal;
    }
    String repUtil = System.getProperty("user.home");
    public String getRepUtil()
    {
        return repUtil;
    }
    String programme = "";
    public String getProgramme()
    {
        return programme;
    }
    private int delai = 0;
    public int getDelai()
    {
        return delai;
    }
    public void setDelai(int delai)
    {
        this.delai = delai;
    }
    Parametres()
    {
        BufferedReader br = null;
        try
        {
            FileInputStream is = new FileInputStream(repUtil+"\\parametres.txt");
            InputStreamReader isr = new InputStreamReader(is, "UTF8");
            br = new BufferedReader(isr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                String[] elements = sCurrentLine.split("=");
                if(elements[0].contains("répertoire d'installation"))
                {
                    this.repInstal = elements[1];
                }
                else if(elements[0].contains("répertoire utilisateur"))
                {
                    this.repUtil = elements[1];
                }
                else if(elements[0].contains("programme à lancer"))
                {
                    this.programme = elements[1];
                }
                else if(elements[0].contains("temps alloué"))
                {
                    this.setDelai(Integer.parseInt(elements[1]) * 60000);
                }
                else
                {
                    throw new IllegalStateException();
                }
            }
        }
        catch (Exception e)
        {
            new SortieSurErreur(e, "Fichier des parametres " + repUtil + "\\parametres.txt\n" +
                "absent ou corrompu ou non codé ANSI");
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (IOException e)
            {
            }
        }

    }
}
