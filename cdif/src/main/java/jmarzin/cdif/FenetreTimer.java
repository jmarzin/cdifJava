package jmarzin.cdif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class FenetreTimer extends JFrame implements ActionListener
{
    private boolean visuLance;
    private JLabel message;
    private JLabel texte;
    private JTextField code;
    private Timer timer;
    private JButton bouton;
    private Parametres parametres;
    private LecteurCodes lecteurCodes;
    private AppliVisu appliVisu;
    
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
    private void fin()
    {
        if (visuLance)
        {
            appliVisu.ferme();
        }
        System.exit(0);
    }
    
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

    private void decompte()
    {
        parametres.setDelai(parametres.getDelai() - 1000);
        if(parametres.getDelai() <= 0)
        {
            this.dispose();
            fin();
        }
        else if(appliVisu.estFermee())
        {
            System.exit(0);
        }
        else if(parametres.getDelai() <= 60000)
        {
            this.message.setForeground(Color.red);
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
        this.message.setText(duree(parametres.getDelai()));
    }
        
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
