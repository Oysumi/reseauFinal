package reseau.clientsServeurs;

import reseau.couches.Application;
import reseau.couches.Transport;
import reseau.Message;
import reseau.adresses.Adresse;
import reseau.Machine;

/**
 * @author martine
 */
public class ClientDNS extends Application {

    protected Adresse serveurDNS ;
    protected int portDNS = 365 ;

    public ClientDNS(int port, Machine mach, Adresse dns) {
        super(port, mach) ;
        serveurDNS = dns ;
    }
    
    public void sendMessage(String nomDest, int portDest, Message message) {
        // On efface le résultat de la précédente requête
        resultat = null ;

        // Afficher une trace de l'envoi
        System.out.println("Je suis "+getNom()+" et j'envoie "+message.size()+" octets.");

        // On récupère l'adresse IP de la machine passée en paramètre
        Message nomDestMess = new Message(nomDest);
        this.sendMessage(serveurDNS, portDNS, nomDestMess);
        Message res = this.getResultat();

        if (res==null){
            System.out.println("Je ne connais pas cette machine.");
            System.out.println("======================================================================================") ;
        } else {
            Adresse dest = res.extraireAdresse(4); // Une adresse IP est sur 4 octets
            // Transmettre à la couche Transport
            ((Transport)moinsUn).sendMessage(this.getPort(), dest, portDest, message);
        }
    }

    public void receiveMessage(Adresse source, int portSource, Message message) {
        System.out.println("Je suis "+getNom()+" et je reçois "+message);
        System.out.println("======================================================================================") ;
        resultat = message ;
    }  

}