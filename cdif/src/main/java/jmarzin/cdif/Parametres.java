/*
 * Copyright (c) 2018 DGFiP - Tous droits réservés
 */
package jmarzin.cdif;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class Parametres gère les paramètres du minuteur
 */
public class Parametres
{
    
    /** rep instal. Répertoire où le minuteur a été installé*/
    String repInstal = "";
    
    /**
     * Accesseur de l attribut rep instal.
     *
     * @return rep instal
     */
    public String getRepInstal()
    {
        return repInstal;
    }
    
    /** rep util. Répertoire d'accuei de l'utilisateur du minuteur
     * c'est dans ce répertoire qu'est installé le fichier de paramétrage
    */
    String repUtil = System.getProperty("user.home");
    
    /**
     * Accesseur de l attribut rep util.
     *
     * @return rep util
     */
    public String getRepUtil()
    {
        return repUtil;
    }
    
    /** programme. Nom du programme visuDGFiP à lancer*/
    String programme = "";
    
    /**
     * Accesseur de l attribut programme.
     *
     * @return programme
     */
    public String getProgramme()
    {
        return programme;
    }
    
    /** delai arret. Durée au-delà de laquelle le programme de consultation
     *  est arrêté
     */
    private int delaiArret = 600000;
    
    /**
     * Accesseur de l attribut delai arret.
     *
     * @return delai arret
     */
    public int getDelaiArret()
    {
        return delaiArret;
    }
    
    /**
     * Modificateur de l attribut delai arret.
     *
     * @param delai le nouveau delai arret
     */
    public void setDelaiArret(int delai)
    {
        this.delaiArret = delai;
    }
    
    /** delai bip. Délai en deça duquel un son alerte l'utilisateur
     *  qu'il arrive en fin de consultation
     */
    private int delaiBip = 60000;
    
    /**
     * methode Bip : elle indique s'il faut ou non émettre un bip.
     *
     * @return true, si c'est vrai
     */
    public boolean bip()
    {
        return delaiArret < delaiBip;
    }
    
    /** delai rouge. Délai en deça duquel l'affichage du temps restant
     *  passe du vert au rouge
     */
    private int delaiRouge = 60000;
    
    /**
     * methode Rouge : elle indique s'il faut ou non afficher le délai
     * restant en rouge.
     *
     * @return true, si c'est vrai
     */
    public boolean rouge()
    {
        return delaiArret < delaiRouge;
    }
    
    /**
     * Instanciation de parametres.
     * Lecture et interprétation des paramètres indiqués dans le fichier
     * visuDGFiP.params. Celui-ci doit être encodé en UTF-8.
     */
    Parametres()
    {
        BufferedReader br = null;
        try
        {
            FileInputStream is = new FileInputStream(repUtil+"\\visuDGFiP.params");
            InputStreamReader isr = new InputStreamReader(is, "UTF8");
            br = new BufferedReader(isr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                String[] elements = sCurrentLine.split("=");
                if(elements[0].contains("répertoire d'installation"))
                {
                    this.repInstal = elements[1];
                }
                else if(elements[0].contains("programme à lancer"))
                {
                    this.programme = elements[1];
                }
                else if(elements[0].contains("temps alloué en minutes"))
                {
                    this.delaiArret = Integer.parseInt(elements[1]) * 60000;
                }
                else if(elements[0].contains("secondes avant bip"))
                {
                    this.delaiBip = Integer.parseInt(elements[1]) * 1000;
                }
                else if(elements[0].contains("secondes avant rouge"))
                {
                    this.delaiRouge = Integer.parseInt(elements[1]) * 1000;
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
