package reseau.clientsServeurs ;

import reseau.couches.Application;
import reseau.Message;
import reseau.Machine;

public class ServeurMaj extends Application {
    
    protected Application client ;

    /**
     * @param port le port dans la couche transport
     */
    public ServeurMaj(int port, Machine mach, Application clientDNS) {
        super(port, mach, clientDNS);
    }

    /**
     * @param message chaine de caractère à transformer en majuscules
     * @return chaine de caractère en majuscules
     */
    @Override
    protected Message traiter(Message message) {
        System.out.println("Je transforme la chaîne de caractère en majuscules...");
        System.out.println("======================================================================================") ;
        /**
         * Pour passer de minuscule à majuscule en ascii, il suffit de soustraire 32 à chaque octet
         */
        Message res = new Message(message) ; // copie profonde
        int size = res.size() - 1 ; // on retire un pour penser en terme d'indice de tableau
        res.augmenter(-32, 97, 122) ;
        return res;
    }

}
