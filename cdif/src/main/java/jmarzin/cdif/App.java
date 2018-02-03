package jmarzin.cdif;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Parametres parametres = new Parametres();
        LecteurCodes lecteurCodes = new LecteurCodes(parametres.getRepInstal() + "\\codes_cdif.txt");
        new FenetreTimer(parametres, lecteurCodes);
    }
}
