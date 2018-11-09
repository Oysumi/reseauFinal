package reseau.clientsServeurs;

import reseau.couches.Application;
import reseau.Message;
import reseau.Machine;
import reseau.tables.DNS;
import reseau.adresses.Adresse;

/**
 * @author martine
 */
public class ServeurDNS extends Application {
    
    protected DNS tableDNS ;

    /**
     * @param port le port dans la couche transport
     */
    public ServeurDNS(int port, Machine mach, Application client, DNS dns) {
        super(port, mach, client);
        tableDNS = dns ;
    }

    /**
     * @param message entier à convertir en * 2
     * @return entier * 2
     */
    @Override
    protected Message traiter(Message message) {
        System.out.println("Je consulte ma table DNS ...");
        System.out.println("======================================================================================");
        String nomMachine = message.extraireChaine();

        if (nomMachine==null) throw new IllegalArgumentException("Impossible de convertir le message en chaine de caractère");
        
        Adresse res = tableDNS.getAdresse(nomMachine);

        if (res==null) throw new IllegalArgumentException("Adresse null");

        return new Message(res);
    }
}
