/*
 * Copyright (c) 2018 DGFiP - Tous droits réservés
 */
package jmarzin.cdif;

/**
 * Application minuteur de consultation du cadastre 
 */
public class App 
{
    
    /**
     * Methode main Le principe est le suivant : - on remet un code au demandeur - celui-ci
     * lance le programme qui lui demande son code - à la fin du temps octroyé pour la 
     * consultation l'application est automattquement fermée.
     *
     * @param args aucun argument attendu.
     */
    public static void main( String[] args )
    {
        // lecture des paramètres
        Parametres parametres = new Parametres();
        // lecture des codes
        LecteurCodes lecteurCodes = new LecteurCodes(parametres.getRepInstal() + "\\codes_cdif.txt");
        // lancement du dialogue
        new FenetreTimer(parametres, lecteurCodes);
    }
}
