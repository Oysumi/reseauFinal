package reseau.clientsServeurs;

import reseau.couches.Application;
import reseau.Message;
import reseau.Machine;

/**
 * @author martine
 */
public class ServeurFoisDeux extends Application {
    
    /**
     * @param port le port dans la couche transport
     */
    public ServeurFoisDeux(int port, Machine mach) {
        super(port, mach);
    }

    /**
     * @param message entier à convertir en * 2
     * @return entier * 2
     */
    @Override
    protected Message traiter(Message message) {
        System.out.println("Je calcule * 2 ...\n");
        int entier = message.extraireEntier(0) ;
        int res = entier * 2 ;
        return new Message(res);
    }

}
