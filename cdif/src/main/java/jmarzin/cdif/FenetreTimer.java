/*
 * Copyright (c) 2018 DGFiP - Tous droits réservés
 */
package jmarzin.cdif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

/**
 * Class FenetreTimer gère la fenêtre d'échange avec l'utilisateur.
 */
@SuppressWarnings("serial")
public class FenetreTimer extends JFrame implements ActionListener
{
    
    /** visu lance. */
    private boolean visuLance;
    
    /** message. */
    private JLabel message;
    
    /** texte. */
    private JLabel texte;
    
    /** code. */
    private JTextField code;
    
    /** timer. */
    private Timer timer;
    
    /** bouton. */
    private JButton bouton;
    
    /** parametres. */
    private Parametres parametres;
    
    /** lecteur codes. */
    private LecteurCodes lecteurCodes;
    
    /** appli visu. */
    private AppliVisu appliVisu;
    
    /**
     * methode Duree : fournit la durée restante en clair.
     *
     * @param delai : le délai en millisecondes
     * @return string
     */
    private String duree(int delai)
    {
        String chaine = "Il vous reste ";
        if (delai >= 60000) {
          int minutes = delai / 60000;
          chaine += minutes + " minute";
          if (minutes > 1) chaine += "s";
        }
        int secondes = (delai % 60000)/1000;
        if (secondes > 0) {
          chaine += " " + secondes + " seconde";
          if (secondes > 1) chaine += "s";
        }
        return chaine;
    }
    
    /**
     * methode Fin : fin du programme, soit après fermeture de la fenêtre utilisateur,
     * soit à la fin du temps alloué.
     */
    private void fin()
    {
        if (visuLance)
        {
            appliVisu.ferme();
        }
        System.exit(0);
    }
    
    /**
     * methode Traite : vérifie le code fourni, lance l'application
     * de consultation du cadastre s'il est valide et lance le compte
     * à rebours du temps alloué.
     */
    private void traite()
    {
        if (!lecteurCodes.consomme(code.getText()))
        {
            message.setForeground(Color.red);
            message.setText("Code erroné ou déjà consommé");
            java.awt.Toolkit.getDefaultToolkit().beep();
            return;
        }
        message.setForeground(Color.green);
        message.setText(" ");
        this.appliVisu = new AppliVisu(parametres);
        this.visuLance = true;
        texte.setText(" ");
        bouton.setEnabled(false);
        code.setEditable(false);
        timer = new Timer(1000, this);
        timer.start(); 
    }

    /**
     * methode Decompte : réduit le temps alloué d'une seconde et déclenche la fin
     * du traitement si le temps est écoulé. Si l'application de consultation a été
     * fermée, l'application est fermée. Le temps restant est affiché à l'utilisateur,
     * avec une signalétique particulière paramétrable quand l'échéance approche (texte
     * en rouge et émission d'un bip).
     */
    private void decompte()
    {
        parametres.setDelaiArret(parametres.getDelaiArret() - 1000);
        if(parametres.getDelaiArret() <= 0)
        {
            this.dispose();
            fin();
        }
        else if(appliVisu.estFermee())
        {
            System.exit(0);
        }
        if(parametres.rouge())
        {
            this.message.setForeground(Color.red);
        }
        if(parametres.bip())
        {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
        this.message.setText(duree(parametres.getDelaiArret()));
    }
        
    /**
     * Instanciation de fenetre timer.
     *
     * @param parametres : les paramètres de l'application
     * @param lecteurCodes : les codes disponibles lus dans le fichier des codes
     */
    public FenetreTimer(Parametres parametres, LecteurCodes lecteurCodes)
    {
        this.parametres = parametres;
        this.lecteurCodes = lecteurCodes;
        this.visuLance = false;
        this.setSize(250, 90);
        this.setTitle("Consultation");
        this.setLocation(1100, 0);
        texte = new JLabel("Veuillez saisir le code qu'on vous a remis");
        code = new JTextField("", 5);
        bouton = new JButton("Cliquer pour lancer");
        message = new JLabel(" ");
        message.setForeground(Color.green);
        
        this.add(texte, BorderLayout.NORTH);
        this.add(code, BorderLayout.WEST);
        this.add(bouton, BorderLayout.CENTER);
        this.add(message, BorderLayout.SOUTH);
        
        WindowListener close = new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                fin();
            }
        };
        addWindowListener(close);
        bouton.addActionListener(this);
        
        this.setVisible(true);
    }

    /** 
     * Si c'est le bouton qui active l'action, le traitement est lancé. Sinon, c'est
     * à dire si c'est le timer, on décompte une seconde.
     * {@inheritDoc}
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource().equals(bouton))
        {
            traite();
        }
        else
       {
            decompte();
       }
    }
}
